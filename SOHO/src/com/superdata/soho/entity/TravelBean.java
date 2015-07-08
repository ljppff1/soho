/**
 * @Title TravelBean.java
 * @Package com.superdata.soho.entity
 * @author Administrator
 * @date 2014年8月7日 上午8:54:58
 * @version V1.0
 */
package com.superdata.soho.entity;

import java.io.Serializable;

/**
 * @ClassName TravelBean
 * @author Administrator
 * @date 2014年8月7日 上午8:54:58
 *
 */
public class TravelBean implements Serializable{

	/**
	 * @Fields serialVersionUID 用一句话描述这个变量表示什么
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String StartTime;
	private String EndTime;
	private String typeName;
	private int TotalTime;
	private String RecordApproval;
	private String RecordReason;
	private String code;//请假单号
	private String id;
	private String route;//出差路线
	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName 要设置的 userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return startTime
	 */
	public String getStartTime() {
		return StartTime;
	}
	/**
	 * @param startTime 要设置的 startTime
	 */
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	/**
	 * @return endTime
	 */
	public String getEndTime() {
		return EndTime;
	}
	/**
	 * @param endTime 要设置的 endTime
	 */
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	/**
	 * @return typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName 要设置的 typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return totalTime
	 */
	public int getTotalTime() {
		return TotalTime;
	}
	/**
	 * @param totalTime 要设置的 totalTime
	 */
	public void setTotalTime(int totalTime) {
		TotalTime = totalTime;
	}
	/**
	 * @return recordApproval
	 */
	public String getRecordApproval() {
		return RecordApproval;
	}
	/**
	 * @param recordApproval 要设置的 recordApproval
	 */
	public void setRecordApproval(String recordApproval) {
		RecordApproval = recordApproval;
	}
	/**
	 * @return recordReason
	 */
	public String getRecordReason() {
		return RecordReason;
	}
	/**
	 * @param recordReason 要设置的 recordReason
	 */
	public void setRecordReason(String recordReason) {
		RecordReason = recordReason;
	}
	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code 要设置的 code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return route
	 */
	public String getRoute() {
		return route;
	}
	/**
	 * @param route 要设置的 route
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	

}
