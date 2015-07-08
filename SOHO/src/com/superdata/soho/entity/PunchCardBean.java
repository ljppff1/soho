/**
 * @Title PunchCardBean.java
 * @Package com.superdata.soho.entity
 * @author Administrator
 * @date 2014年7月15日 下午3:11:03
 * @version V1.0
 */
package com.superdata.soho.entity;

import java.io.Serializable;

/**
 * 打卡实体
 * @ClassName PunchCardBean
 * @author Administrator
 * @date 2014年7月15日 下午3:11:03
 *
 */
public class PunchCardBean implements Serializable{

	/**
	 * @Fields serialVersionUID : 版本
	 */
	private static final long serialVersionUID = 1L;
	private String typeId;
	private String checkTime;
	private String later;
	private String early;
	private String miss;
	private String amOn;
	private String amOff;
	private String pmOn;
	private String pmOff;
	private String companyId;
	private String endTime;
	private String empId;
	private String checkTypeId;
	private String mode;
	private String regId;
	private String regTime;
	private String regFlag;
	private String length;
	private String address;
	private String id;
	private String startTime;
	private String userName;
	private String typeName;
	
	
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
	 * @return typeId
	 */
	public String getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId 要设置的 typeId
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return checkTime
	 */
	public String getCheckTime() {
		return checkTime;
	}
	/**
	 * @param checkTime 要设置的 checkTime
	 */
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	/**
	 * @return later
	 */
	public String getLater() {
		return later;
	}
	/**
	 * @param later 要设置的 later
	 */
	public void setLater(String later) {
		this.later = later;
	}
	/**
	 * @return early
	 */
	public String getEarly() {
		return early;
	}
	/**
	 * @param early 要设置的 early
	 */
	public void setEarly(String early) {
		this.early = early;
	}
	/**
	 * @return miss
	 */
	public String getMiss() {
		return miss;
	}
	/**
	 * @param miss 要设置的 miss
	 */
	public void setMiss(String miss) {
		this.miss = miss;
	}
	/**
	 * @return amOn
	 */
	public String getAmOn() {
		return amOn;
	}
	/**
	 * @param amOn 要设置的 amOn
	 */
	public void setAmOn(String amOn) {
		this.amOn = amOn;
	}
	/**
	 * @return amOff
	 */
	public String getAmOff() {
		return amOff;
	}
	/**
	 * @param amOff 要设置的 amOff
	 */
	public void setAmOff(String amOff) {
		this.amOff = amOff;
	}
	/**
	 * @return pmOn
	 */
	public String getPmOn() {
		return pmOn;
	}
	/**
	 * @param pmOn 要设置的 pmOn
	 */
	public void setPmOn(String pmOn) {
		this.pmOn = pmOn;
	}
	/**
	 * @return pmOff
	 */
	public String getPmOff() {
		return pmOff;
	}
	/**
	 * @param pmOff 要设置的 pmOff
	 */
	public void setPmOff(String pmOff) {
		this.pmOff = pmOff;
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
	 * @return endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime 要设置的 endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	 * @return checkTypeId
	 */
	public String getCheckTypeId() {
		return checkTypeId;
	}
	/**
	 * @param checkTypeId 要设置的 checkTypeId
	 */
	public void setCheckTypeId(String checkTypeId) {
		this.checkTypeId = checkTypeId;
	}
	/**
	 * @return mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode 要设置的 mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * @return regTime
	 */
	public String getRegTime() {
		return regTime;
	}
	/**
	 * @param regTime 要设置的 regTime
	 */
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	/**
	 * @return regFlag
	 */
	public String getRegFlag() {
		return regFlag;
	}
	/**
	 * @param regFlag 要设置的 regFlag
	 */
	public void setRegFlag(String regFlag) {
		this.regFlag = regFlag;
	}
	/**
	 * @return length
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length 要设置的 length
	 */
	public void setLength(String length) {
		this.length = length;
	}
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
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime 要设置的 startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
}
