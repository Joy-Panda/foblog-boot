package studio.baxia.fo.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Pan on 2016/12/1.
 */
@Table(name = "t_friendlink")
@Data
public class Friendlink extends BaseId {
    private String name;//网站名称
    private String website;//网站地址
    private String description;//网站介绍
    private Integer hits;//点击量
    private Integer priority;//优先级
    private String webAuthorEmail;//网站作者联系邮箱
    private String webAuthorName;//网站作者名称
    private Date addTime;
    private Date updateTime;

}
