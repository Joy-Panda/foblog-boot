package studio.baxia.fo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Pan on 2016/12/27.
 */
@Table(name = "t_project")
@Data
public class Project extends BaseId {
    private String  name;
    private String  introduction;
    private Date    pubTime;
    private Integer hits;
    private String  downUrl;
    private String  articleUrl;
    private Boolean status;
    private Date    updateTime;

}
