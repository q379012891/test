package com.blgroup.jkl.bean;

import java.util.List;

public class BrandDto {
	
	private  String   tagName;
	private  List<QueryBrandReqDto>   queryBrandReqDto  ;
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public List<QueryBrandReqDto> getQueryBrandReqDto() {
		return queryBrandReqDto;
	}
	public void setQueryBrandReqDto(List<QueryBrandReqDto> queryBrandReqDto) {
		this.queryBrandReqDto = queryBrandReqDto;
	}
	

}
