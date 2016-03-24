package com.xing.bshopping.entity;

/**
 * 会员信息表 -- custominfo_tab
 * 
 * @author dandan
 * 
 */
public class CustomInfo extends BaseEntity{

	private int cId; // 唯一标志
	private String cName; // 用户名
	private String cPassword; // 密码
	private String cPhone; // 手机号
	private String cImgUrl; // 用户头像的图片地址

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}

	public String getcPhone() {
		return cPhone;
	}

	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}

	public String getcImgUrl() {
		return cImgUrl;
	}

	public void setcImgUrl(String cImgUrl) {
		this.cImgUrl = cImgUrl;
	}

	@Override
	public String toString() {
		return "CustomInfo [cId=" + cId + ", cName=" + cName + ", cPassword="
				+ cPassword + ", cPhone=" + cPhone + ", cImgUrl=" + cImgUrl
				+ "]";
	}

	public CustomInfo(int cId, String cName, String cPassword, String cPhone,
			String cImgUrl) {
		super();
		this.cId = cId;
		this.cName = cName;
		this.cPassword = cPassword;
		this.cPhone = cPhone;
		this.cImgUrl = cImgUrl;
	}

	public CustomInfo() {
		super();
	}

}
