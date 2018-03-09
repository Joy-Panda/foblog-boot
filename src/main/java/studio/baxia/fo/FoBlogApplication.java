package studio.baxia.fo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Administrator on 2018/1/9.
 */
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "studio.baxia.fo.dao")
@EnableTransactionManagement
public class FoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoBlogApplication.class, args);
    }

}
