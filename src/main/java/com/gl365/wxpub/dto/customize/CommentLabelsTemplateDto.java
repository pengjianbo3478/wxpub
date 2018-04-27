package com.gl365.wxpub.dto.customize;

import java.io.Serializable;

public class CommentLabelsTemplateDto implements Serializable {

	private static final long serialVersionUID = 7245194020696192967L;

	private String id;

	private Integer baseId;

	private Integer category;

	private Integer industry;

	/**
	 * 模板级别名称-级联
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}
}