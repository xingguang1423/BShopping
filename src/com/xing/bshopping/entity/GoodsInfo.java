package com.xing.bshopping.entity;

/**
 * 商品信息表 -- goodsinfo_tab
 * 
 * @author dandan
 * 
 */
@SuppressWarnings("serial")
public class GoodsInfo extends BaseEntity {

	private int goodsId; // 唯一标志
	private String goodsName; // 商品名称
	private String goodsContent; // 商品内容
	private String goodsValidity; // 商品的有效期
	private float goodsPrice; // 商品价格
	private float goodsShopPrice; // 门市价
	private int isGoodsBooking; // 商品是否可预约 1是0否
	private String goodsImgUrl; // 商品的图片地址
	private String goodsNotes; // 商品的购买须知
	
	
	public String getGoodsValidity() {
		return goodsValidity;
	}

	public void setGoodsValidity(String goodsValidity) {
		this.goodsValidity = goodsValidity;
	}

	

	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}

	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}


	@Override
	public String toString() {
		return "GoodsInfo [goodsId=" + goodsId + ", goodsName=" + goodsName
				+ ", goodsContent=" + goodsContent + ", goodsValidity="
				+ goodsValidity + ", goodsPrice=" + goodsPrice
				+ ", goodsShopPrice=" + goodsShopPrice + ", isGoodsBooking="
				+ isGoodsBooking + ", goodsImgUrl=" + goodsImgUrl
				+ ", goodsNotes=" + goodsNotes + "]";
	}


	
	public GoodsInfo(int goodsId, String goodsName, String goodsContent,
			String goodsValidity, float goodsPrice, float goodsShopPrice,
			int isGoodsBooking, String goodsImgUrl, String goodsNotes) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsContent = goodsContent;
		this.goodsValidity = goodsValidity;
		this.goodsPrice = goodsPrice;
		this.goodsShopPrice = goodsShopPrice;
		this.isGoodsBooking = isGoodsBooking;
		this.goodsImgUrl = goodsImgUrl;
		this.goodsNotes = goodsNotes;
	}


	public int getIsGoodsBooking() {
		return isGoodsBooking;
	}

	public void setIsGoodsBooking(int isGoodsBooking) {
		this.isGoodsBooking = isGoodsBooking;
	}

	public String getGoodsNotes() {
		return goodsNotes;
	}

	public void setGoodsNotes(String goodsNotes) {
		this.goodsNotes = goodsNotes;
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
