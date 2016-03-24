package com.xing.bshopping.entity;

/**
 * 商家信息表  -- businessinfo_tab
 * @author dandan
 */
@SuppressWarnings("serial")
public class BusinessInfo extends BaseEntity {

	private int bId; // 唯一标志
	private String bName; // 商家名
	private String bPhone; // 商家电话
	private String bContentInfo; // 商家明细
	private String bAddress; // 商家地址
	private String bType; // 商家类型
	private String bImgUrl; //商家的图片地址
	
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getbPhone() {
		return bPhone;
	}
	public void setbPhone(String bPhone) {
		this.bPhone = bPhone;
	}
	public String getbContentInfo() {
		return bContentInfo;
	}
	public void setbContentInfo(String bContentInfo) {
		this.bContentInfo = bContentInfo;
	}
	public String getbAddress() {
		return bAddress;
	}
	public void setbAddress(String bAddress) {
		this.bAddress = bAddress;
	}
	public String getbType() {
		return bType;
	}
	public void setbType(String bType) {
		this.bType = bType;
	}
	public String getbImgUrl() {
		return bImgUrl;
	}
	public void setbImgUrl(String bImgUrl) {
		this.bImgUrl = bImgUrl;
	}
	@Override
	public String toString() {
		return "BusinessInfo [bId=" + bId + ", bName=" + bName + ", bPhone="
				+ bPhone + ", bContentInfo=" + bContentInfo + ", bAddress="
				+ bAddress + ", bType=" + bType + ", bImgUrl=" + bImgUrl + "]";
	}
	public BusinessInfo(int bId, String bName, String bPhone,
			String bContentInfo, String bAddress, String bType, String bImgUrl) {
		super();
		this.bId = bId;
		this.bName = bName;
		this.bPhone = bPhone;
		this.bContentInfo = bContentInfo;
		this.bAddress = bAddress;
		this.bType = bType;
		this.bImgUrl = bImgUrl;
	}
	public BusinessInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
