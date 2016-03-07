package com.xing.bshopping.entity;

public class MoreItem {

	private boolean isShowTopTopDivider;
	private String leftText;
	private int rightImg;
	private String rightText;
	
	
	public boolean isShowTopTopDivider() {
		return isShowTopTopDivider;
	}
	public void setShowTopTopDivider(boolean isShowTopTopDivider) {
		this.isShowTopTopDivider = isShowTopTopDivider;
	}
	public String getLeftText() {
		return leftText;
	}
	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}
	public int getRightImg() {
		return rightImg;
	}
	public void setRightImg(int rightImg) {
		this.rightImg = rightImg;
	}
	public String getRightText() {
		return rightText;
	}
	public void setRightText(String rightText) {
		this.rightText = rightText;
	}
	@Override
	public String toString() {
		return "MoreItem [isShowTopTopDivider=" + isShowTopTopDivider
				+ ", leftText=" + leftText + ", rightImg=" + rightImg
				+ ", rightText=" + rightText + "]";
	}
	public MoreItem(boolean isShowTopTopDivider, String leftText, int rightImg,
			String rightText) {
		super();
		this.isShowTopTopDivider = isShowTopTopDivider;
		this.leftText = leftText;
		this.rightImg = rightImg;
		this.rightText = rightText;
	}
	public MoreItem() {
		super();
	}
	
	
}
