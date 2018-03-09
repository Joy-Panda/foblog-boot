package studio.baxia.fo.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import studio.baxia.fo.common.TreeInfo;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by FirePan on 2016/10/11.
 * 文章类别实体信息.
 * 注意：限制目录级别为两层
 */
@Table(name = "t_category")
@Data
public class Category extends TreeInfo {
    @NotEmpty(message = "名称不能为空")
    private String name;//名称
    @NotEmpty(message = "描述不能为空")
    private String description;
    private Date updateTime;
    private String code;
    /**
     * 类别状态
     */
    private boolean status;
}
