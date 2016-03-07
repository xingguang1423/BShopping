package com.xing.bshopping.entity;

/**
 * 商品信息表 -- goodsinfo_tab
 * 
 * @author dandan
 * 
 */
@SuppressWarnings("serial")
public class GoodsInfo extends BaseEntity{

	private int goodsId; // 唯一标志
	private String goodsName; // 商品名称
	private String goodsContent; // 商品内容
	private float goodsPrice; // 商品价格
	private float goodsShopPrice; // 门市价
	private Boolean isGoodsBooking; //商品是否可预约
	private String goodsImgUrl; //商品的图片地址
	
	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}

	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}

	public Boolean getIsGoodsBooking() {
		return isGoodsBooking;
	}

	public void setIsGoodsBooking(Boolean isGoodsBooking) {
		this.isGoodsBooking = isGoodsBooking;
	}

	public int getGoodsId() {
		return goodsId;
	}

	

	public GoodsInfo(int goodsId, String goodsName, String goodsContent,
			float goodsPrice, float goodsShopPrice, Boolean isGoodsBooking,
			String goodsImgUrl) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsContent = goodsContent;
		this.goodsPrice = goodsPrice;
		this.goodsShopPrice = goodsShopPrice;
		this.isGoodsBooking = isGoodsBooking;
		this.goodsImgUrl = goodsImgUrl;
	}

	@Override
	public String toString() {
		return "GoodsInfo [goodsId=" + goodsId + ", goodsName=" + goodsName
				+ ", goodsContent=" + goodsContent + ", goodsPrice="
				+ goodsPrice + ", goodsShopPrice=" + goodsShopPrice
				+ ", isGoodsBooking=" + isGoodsBooking + ", goodsImgUrl="
				+ goodsImgUrl + "]";
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public float getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public float getGoodsShopPrice() {
		return goodsShopPrice;
	}

	public void setGoodsShopPrice(float goodsShopPrice) {
		this.goodsShopPrice = goodsShopPrice;
	}


	public GoodsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
