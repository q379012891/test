package com.blgroup.jkl.bean;

import java.util.Date;

import com.bailian.pagehelper.PageInfo;


public class QueryBrandReqDto extends PageInfo {
	private String isPage="1";//是否分页,0-不分页，1分页


  
  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.BRAND_ID
   *
   * @mbggenerated
   */
  private Long brandId;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.BRAND_CN_NAME
   *
   * @mbggenerated
   */
  private String brandCnName;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.BRAND_EN_NAME
   *
   * @mbggenerated
   */
  private String brandEnName;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.BRAND_DESC
   *
   * @mbggenerated
   */
  private String brandDesc;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.IS_SHOW
   *
   * @mbggenerated
   */
  private String isShow;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.STATE
   *
   * @mbggenerated
   */
  private String state;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.CREATE_DATE
   *
   * @mbggenerated
   */
  private Date createDate;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.UPDATE_DATE
   *
   * @mbggenerated
   */
  private Date updateDate;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.CREATE_USER
   *
   * @mbggenerated
   */
  private String createUser;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.UPDATE_USER
   *
   * @mbggenerated
   */
  private String updateUser;

  /**
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column t_b2b_brand_base.IS_DELETED
   *
   * @mbggenerated
   */
  private String isDeleted;

  
  /**
   * 图片地址
   * */
  private String brandUrl;
  /**
   * 标签名称
   * */
  private String tagName;
  /**
   * 标签名称(当前)
   * */
  private String tagNames;

/**
   * 标签id
   * */
  private String tagId;
  
  /**
   * 开始时间
   * */
  private String startTime;
  /**
   * 结束时间
   * */
  private String endTime;
  
  
  
  
  
  


public String getStartTime() {
	return startTime;
}

public void setStartTime(String startTime) {
	this.startTime = startTime;
}

public String getEndTime() {
	return endTime;
}

public void setEndTime(String endTime) {
	this.endTime = endTime;
}

public String getBrandUrl() {
	return brandUrl;
}

public void setBrandUrl(String brandUrl) {
	this.brandUrl = brandUrl;
}

public String getTagName() {
	return tagName;
}

public void setTagName(String tagName) {
	this.tagName = tagName;
}

/**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.BRAND_ID
   *
   * @return the value of t_b2b_brand_base.BRAND_ID
   *
   * @mbggenerated
   */
  public Long getBrandId() {
      return brandId;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.BRAND_ID
   *
   * @param brandId the value for t_b2b_brand_base.BRAND_ID
   *
   * @mbggenerated
   */
  public void setBrandId(Long brandId) {
      this.brandId = brandId;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.BRAND_CN_NAME
   *
   * @return the value of t_b2b_brand_base.BRAND_CN_NAME
   *
   * @mbggenerated
   */
  public String getBrandCnName() {
      return brandCnName;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.BRAND_CN_NAME
   *
   * @param brandCnName the value for t_b2b_brand_base.BRAND_CN_NAME
   *
   * @mbggenerated
   */
  public void setBrandCnName(String brandCnName) {
      this.brandCnName = brandCnName == null ? null : brandCnName.trim();
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.BRAND_EN_NAME
   *
   * @return the value of t_b2b_brand_base.BRAND_EN_NAME
   *
   * @mbggenerated
   */
  public String getBrandEnName() {
      return brandEnName;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.BRAND_EN_NAME
   *
   * @param brandEnName the value for t_b2b_brand_base.BRAND_EN_NAME
   *
   * @mbggenerated
   */
  public void setBrandEnName(String brandEnName) {
      this.brandEnName = brandEnName == null ? null : brandEnName.trim();
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.BRAND_DESC
   *
   * @return the value of t_b2b_brand_base.BRAND_DESC
   *
   * @mbggenerated
   */
  public String getBrandDesc() {
      return brandDesc;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.BRAND_DESC
   *
   * @param brandDesc the value for t_b2b_brand_base.BRAND_DESC
   *
   * @mbggenerated
   */
  public void setBrandDesc(String brandDesc) {
      this.brandDesc = brandDesc == null ? null : brandDesc.trim();
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.IS_SHOW
   *
   * @return the value of t_b2b_brand_base.IS_SHOW
   *
   * @mbggenerated
   */
  public String getIsShow() {
      return isShow;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.IS_SHOW
   *
   * @param isShow the value for t_b2b_brand_base.IS_SHOW
   *
   * @mbggenerated
   */
  public void setIsShow(String isShow) {
      this.isShow = isShow == null ? null : isShow.trim();
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.STATE
   *
   * @return the value of t_b2b_brand_base.STATE
   *
   * @mbggenerated
   */
  public String getState() {
      return state;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.STATE
   *
   * @param state the value for t_b2b_brand_base.STATE
   *
   * @mbggenerated
   */
  public void setState(String state) {
      this.state = state == null ? null : state.trim();
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.CREATE_DATE
   *
   * @return the value of t_b2b_brand_base.CREATE_DATE
   *
   * @mbggenerated
   */
  public Date getCreateDate() {
      return createDate;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.CREATE_DATE
   *
   * @param createDate the value for t_b2b_brand_base.CREATE_DATE
   *
   * @mbggenerated
   */
  public void setCreateDate(Date createDate) {
      this.createDate = createDate;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.UPDATE_DATE
   *
   * @return the value of t_b2b_brand_base.UPDATE_DATE
   *
   * @mbggenerated
   */
  public Date getUpdateDate() {
      return updateDate;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.UPDATE_DATE
   *
   * @param updateDate the value for t_b2b_brand_base.UPDATE_DATE
   *
   * @mbggenerated
   */
  public void setUpdateDate(Date updateDate) {
      this.updateDate = updateDate;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.CREATE_USER
   *
   * @return the value of t_b2b_brand_base.CREATE_USER
   *
   * @mbggenerated
   */
  public String getCreateUser() {
      return createUser;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.CREATE_USER
   *
   * @param createUser the value for t_b2b_brand_base.CREATE_USER
   *
   * @mbggenerated
   */
  public void setCreateUser(String createUser) {
      this.createUser = createUser == null ? null : createUser.trim();
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.UPDATE_USER
   *
   * @return the value of t_b2b_brand_base.UPDATE_USER
   *
   * @mbggenerated
   */
  public String getUpdateUser() {
      return updateUser;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.UPDATE_USER
   *
   * @param updateUser the value for t_b2b_brand_base.UPDATE_USER
   *
   * @mbggenerated
   */
  public void setUpdateUser(String updateUser) {
      this.updateUser = updateUser == null ? null : updateUser.trim();
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method returns the value of the database column t_b2b_brand_base.IS_DELETED
   *
   * @return the value of t_b2b_brand_base.IS_DELETED
   *
   * @mbggenerated
   */
  public String getIsDeleted() {
      return isDeleted;
  }

  /**
   * This method was generated by MyBatis Generator.
   * This method sets the value of the database column t_b2b_brand_base.IS_DELETED
   *
   * @param isDeleted the value for t_b2b_brand_base.IS_DELETED
   *
   * @mbggenerated
   */
  public void setIsDeleted(String isDeleted) {
      this.isDeleted = isDeleted == null ? null : isDeleted.trim();
  }
  
  



	public String getIsPage() {
		return isPage;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	public String getTagNames() {
		return tagNames;
	}
	
	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	@Override
	public String toString() {
		return "QueryBrandReqDto [isPage=" + isPage + ", brandId=" + brandId + ", brandCnName=" + brandCnName
				+ ", brandEnName=" + brandEnName + ", brandDesc=" + brandDesc + ", isShow=" + isShow + ", state="
				+ state + ", createDate=" + createDate + ", updateDate=" + updateDate + ", createUser=" + createUser
				+ ", updateUser=" + updateUser + ", isDeleted=" + isDeleted + ", brandUrl=" + brandUrl + ", tagName="
				+ tagName + ", tagNames=" + tagNames + ", tagId=" + tagId + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}
	
	

}
