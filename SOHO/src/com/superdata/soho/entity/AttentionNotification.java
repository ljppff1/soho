package com.superdata.soho.entity;

public class AttentionNotification {
	/*
	 * 消息列表
	 * 
	 */
     public AttentionNotification(String theme, String remark, String sendName,
			String receiveId,String sendID,String data,String preContent) {
		super();
		this.theme = theme;
		this.remark = remark;
		this.sendName = sendName;
		this.receiveId = receiveId;
		this.sendID =sendID;
		this.data =data;
		this.preContent =preContent;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	private String theme;
     private String remark;
     private String sendName;
	private String receiveId;
	/**
	 * @return the preContent
	 */
	public String getPreContent() {
		return preContent;
	}
	/**
	 * @param preContent the preContent to set
	 */
	public void setPreContent(String preContent) {
		this.preContent = preContent;
	}
	private String preContent;
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	private String data;
	public String getSendID() {
		return sendID;
	}
	public void setSendID(String sendID) {
		this.sendID = sendID;
	}
	private String sendID;
}
