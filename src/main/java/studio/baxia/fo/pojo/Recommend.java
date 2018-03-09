package studio.baxia.fo.pojo;

import lombok.Data;

import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Pan on 2016/12/20.
 */
@Table(name = "t_recommend")
@Data
public class Recommend extends BaseId {
    private String title;
    private String articleUrl;
    private String summary;
    @OrderBy("desc")
    private Date   pubTime;;
    private Integer hits;
    private Date    updateTime;
}