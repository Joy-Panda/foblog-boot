package studio.baxia.fo.vo;

import lombok.Data;
import studio.baxia.fo.pojo.Tag;

import java.util.Date;

@Data
public class TagVo extends Tag {
	private Integer counts;
	private Date updateTime;

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	
	
}
