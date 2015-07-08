/**
 * @Title AddressInfo.java
 * @Package com.superdata.soho.entity
 * @author Administrator
 * @date 2014年8月18日 下午4:37:48
 * @version V1.0
 */
package com.superdata.soho.entity;

import java.io.Serializable;

/**
 * 位置信息
 * @ClassName AddressInfo
 * @author Administrator
 * @date 2014年8月18日 下午4:37:48
 * 
 */
public class AddressInfo implements Serializable {

	/**
	 * @Fields serialVersionUID 用一句话描述这个变量表示什么
	 */
	private static final long serialVersionUID = 1L;

	private String address;

	private String id;

	private String typeName;
	private String companyId;
	private Double latitude;
	private Double longitude;
	private String empId;
	private String empName;
	private String remark;
	private String custName;
	private String regName;
	private String trackType;
	private String trackDate;
	private String customerId;
	private String isReg;
	private String regId;
	private String regDate;
	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address 要设置的 address
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId 要设置的 companyId
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude 要设置的 latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude 要设置的 longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return empId
	 */
	public String getEmpId() {
		return empId;
	}
	/**
	 * @param empId 要设置的 empId
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	/**
	 * @return empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName 要设置的 empName
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark 要设置的 remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName 要设置的 custName
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return regName
	 */
	public String getRegName() {
		return regName;
	}
	/**
	 * @param regName 要设置的 regName
	 */
	public void setRegName(String regName) {
		this.regName = regName;
	}
	/**
	 * @return trackType
	 */
	public String getTrackType() {
		return trackType;
	}
	/**
	 * @param trackType 要设置的 trackType
	 */
	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}
	/**
	 * @return trackDate
	 */
	public String getTrackDate() {
		return trackDate;
	}
	/**
	 * @param trackDate 要设置的 trackDate
	 */
	public void setTrackDate(String trackDate) {
		this.trackDate = trackDate;
	}
	/**
	 * @return customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId 要设置的 customerId
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return isReg
	 */
	public String getIsReg() {
		return isReg;
	}
	/**
	 * @param isReg 要设置的 isReg
	 */
	public void setIsReg(String isReg) {
		this.isReg = isReg;
	}
	/**
	 * @return regId
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId 要设置的 regId
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate 要设置的 regDate
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
