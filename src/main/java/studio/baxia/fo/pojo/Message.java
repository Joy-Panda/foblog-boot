package studio.baxia.fo.pojo;

import lombok.Data;
import studio.baxia.fo.common.CommonConstant;
import studio.baxia.fo.common.TreeInfo;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by FirePan on 2016/10/11.
 * 文章留言实体信息.
 * 注意：评论这里采用廖雪峰老师的博客的方式，分块，最多两层。(舍弃)
 */
@Table(name = "t_message")
@Data
public class Message extends TreeInfo {
    private Integer articleId;//评论的文章id
    private Integer blockId;//所在评论的文章的评论区的第几块区域id
    private Integer parentId = CommonConstant.MESSAGE_DEFAULT_PARENT_ID;//父id
    private String content;//内容
    private Integer userType;//留言作者类别（author作者，guest访客）
    private Integer authorId;//作者id
    private Date pubTime;//评论时间
    private Date    updateTime;

}
