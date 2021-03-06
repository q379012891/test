package com.blgroup.jkl.bean;

import java.util.Date;
import java.util.List;

public class PurchaseOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.ORDER_ID
     *
     * @mbggenerated
     */
    private Long orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.PUR_ID
     *
     * @mbggenerated
     */
    private Long purId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.PUR_NAME
     *
     * @mbggenerated
     */
    private String purName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.PUR_PHONE
     *
     * @mbggenerated
     */
    private String purPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.APPOINTMENT_TIME
     *
     * @mbggenerated
     */
    private Date appointmentTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.STATE
     *
     * @mbggenerated
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.SOURCE_CODE
     *
     * @mbggenerated
     */
    private String sourceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.CREATE_DATE
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.UPDATE_DATE
     *
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.CREATE_USER
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.UPDATE_USER
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_b2b_purchase_order.IS_DELETED
     *
     * @mbggenerated
     */
    private String isDeleted;
    
    
    private List<PurchaseOrderDetail> purchaseOrderDetailList;
    
    private List<PurchaseApplyGoods> purchaseApplyGoodsList;
    
    private String trandMode;
    
    private String submitUser;
    
    private String purCompany;
    
    
    
  

	public String getPurCompany() {
		return purCompany;
	}

	public void setPurCompany(String purCompany) {
		this.purCompany = purCompany;
	}

	public String getTrandMode() {
		return trandMode;
	}

	public void setTrandMode(String trandMode) {
		this.trandMode = trandMode;
	}

	public String getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}

	public List<PurchaseApplyGoods> getPurchaseApplyGoodsList() {
		return purchaseApplyGoodsList;
	}

	public void setPurchaseApplyGoodsList(List<PurchaseApplyGoods> purchaseApplyGoodsList) {
		this.purchaseApplyGoodsList = purchaseApplyGoodsList;
	}

	public List<PurchaseOrderDetail> getPurchaseOrderDetailList() {
		return purchaseOrderDetailList;
	}

	public void setPurchaseOrderDetailList(List<PurchaseOrderDetail> purchaseOrderDetailList) {
		this.purchaseOrderDetailList = purchaseOrderDetailList;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.ORDER_ID
     *
     * @return the value of t_b2b_purchase_order.ORDER_ID
     *
     * @mbggenerated
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.ORDER_ID
     *
     * @param orderId the value for t_b2b_purchase_order.ORDER_ID
     *
     * @mbggenerated
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.PUR_ID
     *
     * @return the value of t_b2b_purchase_order.PUR_ID
     *
     * @mbggenerated
     */
    public Long getPurId() {
        return purId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.PUR_ID
     *
     * @param purId the value for t_b2b_purchase_order.PUR_ID
     *
     * @mbggenerated
     */
    public void setPurId(Long purId) {
        this.purId = purId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.PUR_NAME
     *
     * @return the value of t_b2b_purchase_order.PUR_NAME
     *
     * @mbggenerated
     */
    public String getPurName() {
        return purName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.PUR_NAME
     *
     * @param purName the value for t_b2b_purchase_order.PUR_NAME
     *
     * @mbggenerated
     */
    public void setPurName(String purName) {
        this.purName = purName == null ? null : purName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.PUR_PHONE
     *
     * @return the value of t_b2b_purchase_order.PUR_PHONE
     *
     * @mbggenerated
     */
    public String getPurPhone() {
        return purPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.PUR_PHONE
     *
     * @param purPhone the value for t_b2b_purchase_order.PUR_PHONE
     *
     * @mbggenerated
     */
    public void setPurPhone(String purPhone) {
        this.purPhone = purPhone == null ? null : purPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.APPOINTMENT_TIME
     *
     * @return the value of t_b2b_purchase_order.APPOINTMENT_TIME
     *
     * @mbggenerated
     */
    public Date getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.APPOINTMENT_TIME
     *
     * @param appointmentTime the value for t_b2b_purchase_order.APPOINTMENT_TIME
     *
     * @mbggenerated
     */
    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.STATE
     *
     * @return the value of t_b2b_purchase_order.STATE
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.STATE
     *
     * @param state the value for t_b2b_purchase_order.STATE
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.SOURCE_CODE
     *
     * @return the value of t_b2b_purchase_order.SOURCE_CODE
     *
     * @mbggenerated
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.SOURCE_CODE
     *
     * @param sourceCode the value for t_b2b_purchase_order.SOURCE_CODE
     *
     * @mbggenerated
     */
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.CREATE_DATE
     *
     * @return the value of t_b2b_purchase_order.CREATE_DATE
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.CREATE_DATE
     *
     * @param createDate the value for t_b2b_purchase_order.CREATE_DATE
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.UPDATE_DATE
     *
     * @return the value of t_b2b_purchase_order.UPDATE_DATE
     *
     * @mbggenerated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.UPDATE_DATE
     *
     * @param updateDate the value for t_b2b_purchase_order.UPDATE_DATE
     *
     * @mbggenerated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.CREATE_USER
     *
     * @return the value of t_b2b_purchase_order.CREATE_USER
     *
     * @mbggenerated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.CREATE_USER
     *
     * @param createUser the value for t_b2b_purchase_order.CREATE_USER
     *
     * @mbggenerated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.UPDATE_USER
     *
     * @return the value of t_b2b_purchase_order.UPDATE_USER
     *
     * @mbggenerated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.UPDATE_USER
     *
     * @param updateUser the value for t_b2b_purchase_order.UPDATE_USER
     *
     * @mbggenerated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_b2b_purchase_order.IS_DELETED
     *
     * @return the value of t_b2b_purchase_order.IS_DELETED
     *
     * @mbggenerated
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_b2b_purchase_order.IS_DELETED
     *
     * @param isDeleted the value for t_b2b_purchase_order.IS_DELETED
     *
     * @mbggenerated
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}