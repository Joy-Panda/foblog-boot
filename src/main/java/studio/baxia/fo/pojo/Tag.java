package studio.baxia.fo.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by FirePan on 2016/10/11. 文章标签实体信息.
 */
@Table(name = "t_tag")
@Data
public class Tag extends BaseId {
    private String name; // 标签名称
	private Date updateTime;

	public Tag() {
		super();
	}

	public Tag(String name) {
		this.name = name;
	}


}
