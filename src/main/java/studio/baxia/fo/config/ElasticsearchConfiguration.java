package studio.baxia.fo.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2018/1/10.
 */
@Configuration
@Slf4j
public class ElasticsearchConfiguration implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    private TransportClient transportClient;
    private PreBuiltTransportClient preBuiltTransportClient;

    @Override
    public void destroy() throws Exception {
        try {
            log.info("Closing elasticSearch client");
            if (transportClient != null) {
                transportClient.close();
            }
        } catch (final Exception e) {
            log.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public TransportClient getObject() throws Exception {
        return transportClient;
    }

    @Override
    public Class<TransportClient> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    private TransportClient buildClient() {
        try {
            preBuiltTransportClient = new PreBuiltTransportClient(settings());

            String InetSocket[] = clusterNodes.split(":");
            String address = InetSocket[0];
            Integer port = Integer.valueOf(InetSocket[1]);
            transportClient = preBuiltTransportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address), port));

        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        return transportClient;
    }

    /**
     * 初始化默认的client
     */
    private Settings settings() {
//		Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true).build();
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        return settings;
    }
}
