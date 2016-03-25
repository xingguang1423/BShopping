package com.xing.bshopping.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.activity.SearchActivity;
import com.xing.bshopping.activity.TuanDailActivity;
import com.xing.bshopping.adapter.ClassesAdapter;
import com.xing.bshopping.adapter.ClassesPageAdapter;
import com.xing.bshopping.adapter.GoodsInfoAdapter;
import com.xing.bshopping.dao.GoodsInfoDao;
import com.xing.bshopping.entity.GoodsInfo;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.utils.ToastUtils;
import com.xing.bshopping.widget.MeiTuanListView;
import com.xing.bshopping.widget.MeiTuanListView.OnMeiTuanRefreshListener;
import com.zxing.activity.CaptureActivity;

public class HomeFragment extends BaseFragment implements
		OnMeiTuanRefreshListener {

	private View view;

	// ==================分类的tab================
	private LinearLayout view_pager_content;
	private LinearLayout viewGroup;

	private List<Map<String, Object>> listView;

	private int next = 0;
	private ViewPager adViewPager;
	private ClassesPageAdapter classesPageAdapter;
	private ImageView[] imageViews;
	private ImageView imageView;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private List<View> gridViewlist = new ArrayList<View>();

	// ==================下拉刷新的动画==================
	private static MeiTuanListView mListView;

	// =======================商品列表的listview======================
	private static GoodsInfoAdapter goodsInfoAdapter;
	private List<GoodsInfo> goodsInfoList = null;

	// private List<String> mDatas;
	// private static ArrayAdapter<String> mAdapter;
	private final static int REFRESH_COMPLETE = 0;
	
	/**
	 * mInterHandler运行在主线程，因为setOnRefreshComplete需要改变ui，必须在主线程去改变ui
	 * 所以在handleMessage中调用mListView.setOnRefreshComplete();
	 */
	private InterHandler mInterHandler = new InterHandler();

	private static class InterHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mListView.setOnRefreshComplete();
				goodsInfoAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				break;

			default:
				break;
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = View.inflate(activity, R.layout.frag_home, null);

		// initCategory();
		initView();

		new TitleBuilder(view).setLeftText("珠海")
				.setLeftOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ToastUtils.showToast(activity, "珠海", 1);
					}
				}).setTitleImage(R.drawable.home_top_search)
				.setRightImage1(R.drawable.actionbar_icon_msg)
				.setRightImage2(R.drawable.actionbar_icon_scan)
				.setRightOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
//						ToastUtils.showToast(activity, "Geek!!", 1);
						
						Intent intent = new Intent(activity, CaptureActivity.class);
						startActivityForResult(intent, 0);
						
						

					}
				}, R.id.titlebar_iv2_right)
				.setRightOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ToastUtils.showToast(activity, "Dan!!", 1);
					}
				}, R.id.titlebar_iv1_right)
				.setTitleOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						Intent intent = new Intent(activity,
								SearchActivity.class);
						startActivity(intent);

					}
				}).build();

		return view;
	}

	private void initView() {

		// =================下拉刷新的动画=================
		mListView = (MeiTuanListView) view
				.findViewById(R.id.listview_like_shop);

		View HeaderView = View.inflate(activity, R.layout.home_gridview_class,
				null);
		View FooterView = View.inflate(activity, R.layout.include_footer_view,
				null);
		mListView.addHeaderView(HeaderView);
		mListView.addFooterView(FooterView);

		goodsInfoList = new ArrayList<GoodsInfo>();

		GoodsInfoDao goodsInfoDao = new GoodsInfoDao(activity);
		
		
//		goodsInfoList.add(new GoodsInfo(1, "珠海长隆横琴湾酒店",
//				"[香洲]香洲区横琴新区富祥湾","2015.3.3 至 2016.4.8", 2563f, 2500f, 1,
//				"http://pavo.elongstatic.com/i/Hotel795_325/00006gl3.jpg", 
//				"酒店电话 0756-2998888 艺龙电话预订：4009-333-333\n\n" +
//				"上网服务 公共区域提供免费WiFi\n\n" +
//				"停车场 酒店提供免费停车位\n\n" +
//				"开业时间 酒店开业时间 2014年\n\n" +
//				"酒店设施  免费Wifi、免费停车、游泳池、健身房、商务中心、会议室、中餐厅、西餐厅、叫车服务、房间消毒、叫醒服务、洗衣服务、送餐服务、旅游服务、专职行李员、行李寄存、票务服务、外币兑换服务、有电梯、自助取款机、免费旅游交通图、茶室、商品部、安全消防系统、大堂吧、公共区域闭路电视监控系统、残障人客房、咖啡厅、酒吧、电子结账系统、无烟楼层、无障碍通道、行政酒廊、24小时热水、宴会厅、桑拿浴室、美容美发室、SPA、按摩保健、海边娱乐\n\n" +
//				"酒店简介  长隆横琴湾酒店，中国最大的海洋生态主题酒店，由世界顶尖的建筑设计师、室内设计师及园林景观设计师倾力合作完成，总建筑面积达30万平方米，于2014年年初启幕开业。 酒店拥有1888间宽敞豪华的客套房及9间风格各异的餐厅和酒吧，提供中式、西式、日式及亚洲特色美食的餐饮体验。奢华的宴会及会议场地拥有一个3000平方米的超大无柱宴会厅，辅以一个1200平方米宴会厅，以及26间设备先进的多功能厅。移步酒店外围，更可享受园林景观、海豚池、室外泳池、人造沙滩、室内水上乐园，及海天一色的壮丽美景。一条近1000米长的景观河接连着酒店与长隆海洋王国，客人可泛舟其中，往返两地。 即日起至2014年8月28日，酒店联手长隆国际马戏城，为客人带来汇集精彩娱乐表演，特色风味美食与原装进口啤酒于一体的全新广场文化——马戏小镇。 【温馨提示】1.请用入住客人的名字预订客房 2.酒店入住时间为15:00可接受的信用卡\n\n"));
//		
//		
//		
//		goodsInfoList.add(new GoodsInfo(2, "爱琴海海鲜烧烤家常菜",
//				"北海道大虾  38元/只代金券1张，可叠加  ","2016.3.1 至 2016.8.2", 38f, 2f, 1,
//				"http://p1.meituan.net/deal/4d89032d109bdb46c94868b0d87db35d480935.jpg", 
//				"（原产地，日本北海道）。\n\n" +
//				"新鲜口感鲜美，嫩滑无比，规格大，品相好。本店采用中式热炒凉拌，鲜美无比。\n" +
//				"体被蓝褐色横斑花纹，尾尖为鲜艳的蓝色。额角微呈正弯弓形，上缘8～10齿，下缘1～2齿。第一触角鞭甚短，短于头胸甲的1/2。第一对步足无座节刺，雄虾交接器中叶顶端有非常粗大的突起，雌交接器呈长圆柱形。成熟虾雌大于雄。\n" +
//				"体长8-10 cm。额角齿式8-10/1-2。具额胃脊，后端双叉型。额角侧沟长，伸至头胸甲后缘附近；额角后脊的中央沟长于头胸甲长的1/2。尾节具3对活动刺。雌性交接器囊状，前端开口，有一圆突；雄性交接器中叶突出，并向腹面弯折。体表具土黄色和蓝色相间的鲜明横斑，尾肢具棕色横带。\n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(3, "陈记煌汁焖锅（斗门店）",
//				"哈尔滨鳇鱼 1489元/斤代金券1张，可叠加  ","2016.3.1 至 2016.5.1", 1489f, 296f, 0,
//				"http://img0.imgtn.bdimg.com/it/u=42234473,4047686317&fm=11&gp=0.jpg", 
//				"原产地：哈尔滨天山湖\n\n" +
//				"陈记煌焖锅来自于传统，不拘泥于传统，而是与时俱进，继往开来，酿制新配方，应用新工具，创造了新模式，新潮流，新感觉，新味道，满足了人们的新吃法。开拓了新市场\n\n" +
//				"山西省太原市开化寺街42号时代广场巴黎春天百货太原店6层。\n\n" +
//				"皇记煌三汁焖锅是位于山西省太原市开化寺街42号时代广场巴黎春天百货太原店6层的一家川菜店。\n\n" +
//				"非常珍稀，每天空运到珠海，确保可以吃到新鲜的鳇鱼，本店做法：鱼头1个、豆腐2方块、葱2根、香菜2棵、姜1块、油20ml、盐1茶匙、鸡精1/2茶匙、酒10ml\n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(4, "珠海市假期旅行社历险游",
//				"朝鲜军区一周历险游   45000元  ","2016.3.1 至 2016.5.1", 45000f, 690f, 0,
//				"http://news.wy000.com/uploadfile/article/2014312041168585910.jpg", 
//				"香洲区迎宾南路拱北口岸   58公里\n\n" +
//				"近距离与死神接触，包含避弹衣一套，一周体验，刺激无比。\n\n45000元，刷爆你的朋友圈，你还等什么？" +
//				"<朝鲜历险记> 真人实拍，谍战剧连载完！不一样的游记 \n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(5, "三灶古玩城",
//				"周恩来亲自签名 ","2016.3.1 至 2016.6.1", 89500f, 480f, 0,
//				"http://img.365art.com/news/2014/05/24/201405240942482.jpg", 
//				"金湾区三灶镇唐人街88号    43公里\n\n" +
//				"无与伦比的尊贵，财富的象征。非常罕见,你还等什么？" +
//				"<朝鲜历险记> 真人实拍，谍战剧连载完！不一样的游记 \n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(5, "阳光时代户外协会",
//				"骑马走珠海大道一圈 ","2016.3.1 至 2016.4.1", 4500f, 2300f, 1,
//				"http://image.zh51home.com/news/FTBPictures/Pictures/68541/DSC00593.jpg", 
//				"你试过在城市里骑马吗？尊贵指数超保时捷911，回头率爆表，仅需4500元\n\n限定人次100次，先到先得。我公司会提供专门的安全保护。\n\n" +
//				"无与伦比的尊贵，财富的象征。你还等什么？ \n\n"));
//		
//
//		goodsInfoList.add(new GoodsInfo(6, "珠海长隆企鹅酒店",
//				"[湾仔口岸]横琴新区富祥湾","2015.3.3 至 2016.7.4", 24900f, 2300f, 0,
//				"http://pavo.elongstatic.com/i/Hotel795_325/0004kcru.jpg", 
//				"酒店电话  0756-2993366 艺龙电话预订：4009-333-333\n\n" +
//				"上网服务 公共区域提供免费WiFi\n\n" +
//				"停车场 酒店提供免费停车位\n\n" +
//				"开业时间 酒店开业时间 2015年\n\n" +
//				"酒店设施  免费Wifi、免费停车、会议室、中餐厅、西餐厅、接机服务（收费）、叫车服务、婚宴服务、叫醒服务、洗衣服务、旅游服务、租车服务、行李寄存、票务服务、有电梯、自助取款机、安全消防系统、大堂吧、残障人客房、咖啡厅、酒吧、无烟楼层、24小时热水、宴会厅、足浴/足疗\n\n" +
//				"极地房-套票 房间36㎡ | 大/双床 | 可住：   | 楼层：1层-15层 | 免费WIFI[免费]\n\n" +
//				"企鹅套房-套票 房间55㎡ | 大床 | 可住：   | 楼层：1层-15层 | 免费WIFI[免费]\n\n" +
//				"酒店简介  长隆横琴湾酒店，中国最大的海洋生态主题酒店，由世界顶尖的建筑设计师、室内设计师及园林景观设计师倾力合作完成，总建筑面积达30万平方米，于2014年年初启幕开业。 酒店拥有1888间宽敞豪华的客套房及9间风格各异的餐厅和酒吧，提供中式、西式、日式及亚洲特色美食的餐饮体验。奢华的宴会及会议场地拥有一个3000平方米的超大无柱宴会厅，辅以一个1200平方米宴会厅，以及26间设备先进的多功能厅。移步酒店外围，更可享受园林景观、海豚池、室外泳池、人造沙滩、室内水上乐园，及海天一色的壮丽美景。一条近1000米长的景观河接连着酒店与长隆海洋王国，客人可泛舟其中，往返两地。 即日起至2014年8月28日，酒店联手长隆国际马戏城，为客人带来汇集精彩娱乐表演，特色风味美食与原装进口啤酒于一体的全新广场文化——马戏小镇。 【温馨提示】1.请用入住客人的名字预订客房 2.酒店入住时间为15:00可接受的信用卡\n\n"));
//		
//		
//		goodsInfoList.add(new GoodsInfo(7, "珠海长隆马戏酒店",
//				"[香洲]横琴新区富祥湾","2016.1.1 至 2016.4.8", 15170f, 1500f, 1,
//				"http://pavo.elongstatic.com/i/Hotel795_325/0000C3U7.jpg", 
//				"酒店电话  0756-2993399 艺龙电话预订：4009-333-333\n\n" +
//				"上网服务 公共区域提供免费WiFi\n\n" +
//				"停车场  酒店提供免费停车位\n\n" +
//				"开业时间 酒店开业时间 2015年\n\n" +
//				"酒店设施  免费Wifi、免费停车、会议室、中餐厅、西餐厅、接机服务（收费）、叫车服务、多种语言服务人员、婚宴服务、提供发票、叫醒服务、洗衣服务、专职行李员、行李寄存、有电梯、自助取款机、免费旅游交通图、商品部、安全消防系统、大堂吧、公共区域闭路电视监控系统、残障人客房、咖啡厅、酒吧、电子结账系统、无烟楼层、无障碍通道、24小时热水、宴会厅\n\n" +
//				"酒店简介 马戏酒店以典型欧洲小镇为蓝本，以颜色各异的建筑单体和造型独特的钟塔，打造充满欧洲小镇特色的异国风情 马戏酒店坐落在马戏城西南侧，毗邻现有的马戏剧院，是国内最大的马戏主题酒店。酒店拥有700间马戏主题客房，两大别具特色的主题餐厅，水岸式休闲娱乐商业街区，汇集了包括港式茶餐厅，咖啡面包馆，四大主题餐吧等特色风味餐饮，更有马戏特色礼品荟，为您带来一站式度假新体验\n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(8, "泰国曼谷芭提雅6日游",
//				"一晚入住华美达或同级酒店 ， 全程无自费 ，赠送三合一， 广起港止","2016.2.1 至 2016.4.8", 29990f, 2800f, 1,
//				"http://s3.lvjs.com.cn//uploads/pc/place2/2015-05-06/316717a0-80b2-4619-8685-822185cb4604.jpg", 
//				"【酒店升级】曼谷1晚升级华美达或同级别酒店\n\n" +
//				"【经典双自助餐组合】曼谷彩虹大厦享用76层自助餐，kingpower免税店享用自助餐\n\n" +
//				"【双游船】昭帕耶公主号畅游湄南河，东方公主号玩转暹罗湾畅游金沙岛，水上活动应有尽有\n\n" +
//				"英国宝妮小马俱乐部】身高70-130公分迷你宝马，更小更可爱。\n\n" +
//				"D1第1天 深圳-香港/泰国\n\n" +
//				"1、全天 飞机 \n" +
//				"2、全天 早餐、中餐、晚餐 \n" +
//				"早餐:敬请自理，中餐:敬请自理，晚餐:敬请自理 \n" +
//				"3、 入住酒店：其他，Best Western Plus Grand Howard或Eastin Makasan Hotel Bangkok或Golden Tulip Sovereign Hotel或Hi Residence或Chor Cher标准双人间 \n\n" +
//				"4、 集合方式1. 指定时间广州市区指定地点集合乘车前往深圳皇岗口岸与领队会合。然后乘车直接前往香港机场（广州到深圳是安排专车接送，如果广州出发人数不足4人，烦请客人自行购买车票到深圳集中地，凭发票领队当场为客人报销车费，报销上限是200元/人，谢谢各位贵宾的谅解和配合） \n\n" +
//				"---- 集合方式2. 自行前往广州莲花港码头先换船票再换登机牌托运行李，搭乘快船前往香港机场（广州番禺发船每天3班分别为09:10/13:50/16:20，根据航班时间来订船 \n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(9, "企鹅酒店2人自由行特卖",
//				"游长隆海洋王国乐园（特权：享2日内多次往返）＋看国际马戏＋入住企鹅酒店","2015.3.6 至 2016.3.7", 2498f,2380f, 0,
//				"http://s3.lvjs.com.cn/uploads/pc/place2/2015-11-26/9fa5e1f2-085b-43b8-91ef-f39d35c1b5e0.jpg", 
//				"大小儿童的美妙世界---企鹅陪您乐翻天 珠海长 隆企鹅酒店是目前全球至大的企鹅极地主题酒店，大堂与主题客房巧妙运动冷暖色调搭配，配合呆萌企鹅形象、开阔明亮的玻璃设计，让宾客仿佛置身在冰川地带！ 酒店紧邻海洋王国园区，快捷通道步行1分钟即可抵达入口广场，只需5分钟车程便可抵达横琴口岸，方便快捷，是家人朋友享受海洋王国精彩与欢乐的主题式度假 酒店。n\n" +
//				"清新典雅-极地房、暖意十足-温带房、趣味别致-探险房，可爱缤纷-企鹅家庭房等，不同主题满足不同需求；家庭房内更配备儿童床、儿童座椅等用品，尽显贴心服务。烟花观景房视野直面海洋王国烟花表演，客人足不出户，在房间就能欣赏精彩荟萃的烟花表演，给旅程再添一份惊喜与快乐！ n\n" +
//				"全球海洋盛宴——珠海长隆海洋王国n\n" +
//				"珠 海长隆海洋王国位于珠海横琴长隆国际海洋度假区内，是长隆集团运用积累了20多年的经验，运用高科技和长隆特有的创意，全面整合珍稀的海洋动物、游乐设 备 和新奇的大型演艺；总面积为130多万平方米，囊括机动游戏、珍稀动物展馆、大型剧场表演、餐饮、购物等多种综合娱乐.n\n" +
//				"圣诞期间，海洋王国鲸鲨馆将带来圣诞老人与鲸鲨共舞的演出，热烈浓郁的圣诞气氛，从进入海洋大街前已经扑面而来！高达5米的圣诞老人，在海洋王国大门迎接大家的光临，海洋大街两旁的圣诞花藤和璀璨的圣诞灯饰带您进入浓浓的圣诞氛围，夜晚更有灿烂的星星灯如同繁星闪耀。\n\n" +
//				"1、全天 飞机 \n" +
//				"2、全天 早餐、中餐、晚餐 \n" +
//				"早餐:敬请自理，中餐:敬请自理，晚餐:敬请自理 \n" +
//				"3、 入住酒店：其他，Best Western Plus Grand Howard或Eastin Makasan Hotel Bangkok或Golden Tulip Sovereign Hotel或Hi Residence或Chor Cher标准双人间 \n\n" +
//				"4、 集合方式1. 指定时间广州市区指定地点集合乘车前往深圳皇岗口岸与领队会合。然后乘车直接前往香港机场（广州到深圳是安排专车接送，如果广州出发人数不足4人，烦请客人自行购买车票到深圳集中地，凭发票领队当场为客人报销车费，报销上限是200元/人，谢谢各位贵宾的谅解和配合） \n\n" +
//				"---- 集合方式2. 自行前往广州莲花港码头先换船票再换登机牌托运行李，搭乘快船前往香港机场（广州番禺发船每天3班分别为09:10/13:50/16:20，根据航班时间来订船 \n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(10, "珠海粤海酒店 Guangdong Hotel",
//				"游长隆海洋王国乐园（特权：享2日内多次往返）＋看国际马戏＋入住企鹅酒店","2015.1.1 至 2016.5.7", 376f,360f, 1,
//				"http://pavo.elongstatic.com/i/Hotel795_325/000056lE.jpg", 
//				"酒店电话 \n" +
//				"0756-2882222 艺龙电话预订：4009-333-333 \n" +
//				"上网服务 公共区域提供免费WiFi \n" +
//				"停车场 酒店提供免费停车位\n" +
//				"开业时间 酒店开业时间 2006年 新近装修时间	2013 年 \n" +
//				"酒店设施 免费Wifi、免费停车、商务中心、会议室、西餐厅、叫车服务、管家服务、洗衣服务、送餐服务、租车服务、行李寄存、票务服务、有电梯、公共区域闭路电视监控系统、大堂免费报纸、无烟楼层、无障碍通道、24小时热水、棋牌室、桑拿浴室、足浴/足疗、篮球场\n" +
//				"酒店服务 棋牌 桑拿 \n" +
//				"酒店简介 珠海君临酒店拥有各式山景房108间，酒店设有中西餐厅、棋牌室、商务中心、桑拿健康中心、篮球场等. 酒店座落于青山环绕的香洲隧道北翠微东路68号，与常年拥翠的板障山为邻.板障山隧道北，与拱北关口咫尺之程，距九洲港码头、各长途车站十分钟车程，珠海国际机场30分钟车程，周边旅游景点遍布，如圆明新园，梦幻水城、中药谷，白莲洞公园。 【温馨提示】酒店提供免费一次性洗漱用品，免费提供2支矿泉水，所有房间都有WIFI,房间内电话任意拔打市话及国内长途。\n" +
//				"可接受的信用卡 \n" +
//				"---- 集合方式. 自行前往广州莲花港码头先换船票再换登机牌托运行李，搭乘快船前往香港机场（广州番禺发船每天3班分别为09:10/13:50/16:20，根据航班时间来订船 \n\n"));
//		
//		goodsInfoList.add(new GoodsInfo(11, "珠海长隆企鹅酒店2人1晚-温馨自驾游",
//				"2日多次畅游海洋王国主题乐园＋企鹅宝宝相伴享自助晚餐＋入住长隆企鹅酒店。","2016.3.1 至 2016.5.7", 18900f,1679f, 1,
//				"http://s1.lvjs.com.cn//uploads/pc/place2/2015-11-26/1203fdb1-7a3d-4ead-b1b1-7d200eaa5d9b.jpg", 
//				"酒店电话 \n" +
//				"0756-2882222 艺龙电话预订：4009-333-333 \n" +
//				"D1第1天 各地--珠海长隆 \n" +
//				"1、各自所在城市出发，前往海滨花园城市——珠海。  \n" +
//				"2、下午约15：00抵达长隆企鹅酒店（酒店于15：00开始办理入住），客人凭所有入住人的身份证、驴妈妈短信于酒店前台办理入住手续并取票。 \n" +
//				"用餐：早餐(自理) 中餐(自理) 晚餐(自理)  \n" +
//				"住宿：含住宿（其他 珠海长隆企鹅酒店） \n" +
//				"交通：无 \n" +
//				"D2第2天 珠海长隆--温馨的家 \n" +
//				"1、美美的睡了一觉，可自费享用企鹅酒店自助早餐。  \n\n" +
//				"2、中午12：00前办理退房手续，返回温馨的家，祝旅途愉快！ \n" +
//				"用餐：早餐(自理) 中餐(自理) 晚餐(自理)  \n" +
//				"住宿：敬请自理 \n" +
//				"交通：无 \n" +
//				"1、客房入店时间为入住当天下午15：00或以后，离店时间为次日中午12：00前，请留意！  \n" +
//				"2、套餐内不含早餐，游客可自费前往帝企鹅餐厅用餐。  \n" +
//				"3、 酒 店要求实际入住人姓名必须与下单游客姓名一致；如入住当天变更姓名，酒店将视为新订单需按门市价格重新收费，并且原订单自动无效但酒店仍然会收取原订 单 全额费用；如客人预订成功后需变更入住人名字，请至少提前三天致电驴妈妈客服热线1010-6060告知，逾期酒店不接受任何更改，所以请客人们在预 订时 尽量的提供正确的游玩人信息，避免发生不必要的损失，感谢支持！  \n" + 
//				"4、关于自助餐劵的用餐时间说明：『自助晚餐』则仅限入住当日晚上用餐，预订成功后用餐时间不允更改。  \n" +
//				"5、关于酒店房型说明：  \n" +
//				"温带房：均有双床房、大床房2种床型，酒店将随机安排。如游客对房间床型有要求请在预订时致电1010-6060驴妈妈客服做备注，酒店将按当时的房态情况尽力为您安排，最终请以入住当天酒店安排的情况为准，如有不便敬请见谅！\n\n"));
		
		
//		goodsInfoDao.addGoodsInfos(goodsInfoList);

		goodsInfoDao.showAllGoodsInfos(goodsInfoList);

		// mAdapter = new ArrayAdapter<String>(activity,
		// android.R.layout.simple_list_item_1, mDatas);
		goodsInfoAdapter = new GoodsInfoAdapter(activity, goodsInfoList);
		mListView.setAdapter(goodsInfoAdapter);
		mListView.setOnMeiTuanRefreshListener(this);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				GoodsInfo goodsInfo = goodsInfoAdapter.getItem(position - 2);
				Intent intent = new Intent(activity, TuanDailActivity.class);
				intent.putExtra("goodsInfo", goodsInfo);
				startActivity(intent);

				// ToastUtils.showToast(activity, goodsInfo+"", 1);
			}

		});

		// ================左右滑动的分类tab================

		view_pager_content = (LinearLayout) view
				.findViewById(R.id.view_pager_content);
		viewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);

		listView = new ArrayList<Map<String, Object>>();
		// 创建ViewPager
		adViewPager = new ViewPager(activity);

		// 获取屏幕像素相关信息
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 根据屏幕信息设置ViewPager容器的宽高
		adViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				dm.heightPixels));

		// 将ViewPager容器设置到布局文件父容器中
		view_pager_content.addView(adViewPager);

		getClassesView();

		// 小圆点
		initCirclePoint();

		adViewPager.setAdapter(classesPageAdapter);
		adViewPager.setOnPageChangeListener(new AdPageChangeListener());

	}

	/**
	 * ViewPager 页面改变监听器
	 */
	private final class AdPageChangeListener implements OnPageChangeListener {

		/**
		 * 页面滚动状态发生改变的时候触发
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		/**
		 * 页面滚动的时候触发
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		/**
		 * 页面选中的时候触发
		 */
		@Override
		public void onPageSelected(int arg0) {

			// 获取当前显示的页面是哪个页面
			System.out.println("onPageSelected");
			atomicInteger.getAndSet(arg0);

			// 重新设置原点布局集合
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.mtadvert_indicator_selected);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.mtadvert_indicator_normal);
				}
			}

		}

	}

	/**
	 * 小圆点
	 */
	private void initCirclePoint() {

		imageViews = new ImageView[gridViewlist.size()];
		for (int i = 0; i < gridViewlist.size(); i++) {
			// 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
			imageView = new ImageView(activity);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			layoutParams.setMargins(10, 0, 10, 0);
			imageView.setLayoutParams(layoutParams);
			imageViews[i] = imageView;

			// 初始值, 默认第0个选中
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.mtadvert_indicator_selected);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.mtadvert_indicator_normal);
			}
			// 将小圆点放入到布局中
			viewGroup.addView(imageViews[i]);

		}

	}

	private void getClassesView() {

		int[] drawableView = { R.drawable.ic_category_0,
				R.drawable.ic_category_1, R.drawable.ic_category_2,
				R.drawable.ic_category_3, R.drawable.ic_category_4,
				R.drawable.ic_category_6, R.drawable.ic_category_5,
				R.drawable.ic_category_10, R.drawable.ic_category_11,
				R.drawable.ic_category_14, R.drawable.ic_category_7,
				R.drawable.ic_category_9, R.drawable.ic_category_12,
				R.drawable.ic_category_13, R.drawable.ic_category_8,
				R.drawable.ic_category_16

		};

		String[] titleView = { "美食", "电影", "酒店", "KTV", "今日新单", "周边游", "代金券",
				"休闲娱乐", "丽人", "购物", "小吃快餐", "生活服务", "足疗按摩", "旅游", "蛋糕甜点",
				"全部分类"

		};

		for (int i = 0; i < drawableView.length; i++) {
			Map<String, Object> mapView = new HashMap<String, Object>();
			mapView.put("image", drawableView[i]);
			mapView.put("title", titleView[i]);
			listView.add(mapView);
		}

		getGridView();

	}

	private void getGridView() {

		boolean bool = true;

		while (bool) {
			int result = next + 10;

			if (listView.size() != 0 && result < listView.size()) {

				GridView gridView = new GridView(activity);
				gridView.setNumColumns(5);
				gridView.setSelector(R.color.bg_gray);
				gridView.setPadding(0, 10, 0, 0);
				gridView.setHorizontalSpacing(10);
				gridView.setVerticalSpacing(10);

				List<Map<String, Object>> gridlist = new ArrayList<Map<String, Object>>();
				for (int i = next; i < result; i++) {
					gridlist.add(listView.get(i));
				}

				ClassesAdapter classesAdapter = new ClassesAdapter(activity,
						gridlist);

				gridView.setAdapter(classesAdapter);
				next = result;
				gridViewlist.add(gridView);

			} else if (result - listView.size() < 10) {

				System.out.println("剩余多少" + (result - listView.size()));
				List<Map<String, Object>> gridlist = new ArrayList<Map<String, Object>>();
				for (int i = next; i < listView.size(); i++) {
					gridlist.add(listView.get(i));
				}

				GridView gridView = new GridView(activity);
				gridView.setNumColumns(5);
				gridView.setSelector(R.color.bg_gray);
				gridView.setPadding(0, 10, 0, 0);

				ClassesAdapter myAdapter = new ClassesAdapter(activity,
						gridlist);
				gridView.setAdapter(myAdapter);
				next = listView.size() - 1;
				gridViewlist.add(gridView);
				bool = false;

			} else {
				System.out.println("执行了这句话");
				bool = false;
			}
			classesPageAdapter = new ClassesPageAdapter(gridViewlist);
		}
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					goodsInfoList.add(0, new GoodsInfo(100, "珠海星城大酒店",
											"[吉大商业街,免税,吉大百货] 吉大区景山路88号(珠海国税局南行约220米)","2015.3.3 至 2016.4.8", 3960f, 420f, 0,
											"http://pavo.elongstatic.com/i/Hotel795_325/0000kiZe.jpg", 
											"酒店电话 0756-3220844 艺龙电话预订：4009-333-333\n\n" +
											"上网服务 公共区域提供免费WiFi\n\n" +
											"停车场 酒店提供免费停车位\n\n" +
											"开业时间 酒店开业时间 2006年 新近装修时间	2009 年\n\n" +
											"酒店设施 免费Wifi、免费停车、健身房、商务中心、会议室、西餐厅、接机服务（收费）、叫车服务、房间消毒、管家服务、婚宴服务、叫醒服务、洗衣服务、送餐服务、旅游服务、租车服务、专职行李员、擦鞋服务、行李寄存、票务服务、外币兑换服务、有电梯、前台保险柜、免费旅游交通图、安全消防系统、大堂吧、公共区域闭路电视监控系统、咖啡厅、酒吧、电子结账系统、大堂免费报纸、无烟楼层、无障碍通道、行政酒廊、24小时热水、大堂提供上网电脑、宴会厅、足浴/足疗、按摩保健\n\n" +
											"酒店服务 健身房、大堂吧\n\n" +
											"酒店简介 珠海星城大酒店是由深圳星城酒店管理公司经营管理"));
																	
																	
					goodsInfoList.add(0, new GoodsInfo(101, "珠海铂濠假日酒店",
							"[前山]香洲区前山镇前山路32号(心海洲对面)","2014.6.27 至 2016.7.28（周末、法定节假日通用）", 19600f, 1860f, 1,
							"http://pavo.elongstatic.com/i/Hotel795_325/0000qoVA.jpg",
							"店电话 0756-3871228 艺龙电话预订：4009-333-333\n\n" +
							"上网服务 公共区域提供免费WiFi\n\n" +
							"停车场 酒店提供免费停车位\n\n" +
							"开业时间 酒店开业时间 2014年 新近装修时间	2014 年\n\n" +
							"酒店设施 免费Wifi、免费停车、接机服务（收费）、叫车服务、房间消毒、叫醒服务、送餐服务、旅游服务、行李寄存、票务服务、有电梯、自助取款机、免费旅游交通图、安全消防系统、大堂吧、公共区域闭路电视监控系统、电子结账系统、大堂免费报纸、无障碍通道、24小时热水、棋牌室、卡拉OK厅\n\n" +
							"酒店简介 铂濠假日酒店2014年10月全新开业，拥有客房60多间，配备全层无线wifi上网，使您倍感安全、方便。拥有大型免费停车场，交通方便。下榻铂濠假日酒店，安宁、舒适、方便、实惠，是商务、旅游、度假、休闲、娱乐的最佳选择。\n\n" +
							"可接受的信用卡 标准大床房(无窗)\n\n" +
							"房间18-25㎡ | 大床 | 可住：   | 楼层：4层 | 免费WIFI[免费]\n\n" +
							"产品名称	供应商	早餐	取消规则	日均价\n\n" +
							"不含早-艺龙专享（预付） 7.3 	艺龙	无早	不可取消	¥178 ¥130			预订	 \n\n" +
							"艺龙独家专享（预付） 7.9 	艺龙	无早	不可取消	¥178 ¥140	\n\n" +
							"免费提供500个停车位\n\n"));
					
					goodsInfoList.add(0, new GoodsInfo(102, "珠海来魅力假日酒店",
							"[拱北商业区]拱北区围基路32号(拱北口岸出门即是)","2014.6.27 至 2016.7.28", 6810f, 596f, 1,
							"http://pavo.elongstatic.com/i/Hotel795_325/00007g1V.jpg",
								"无需预约，消费高峰时可能需要等位\n\n" +
									"酒店电话 0756-8338888 艺龙电话预订：4009-333-333\n\n" +
									"上网服务 公共区域提供免费WiFi\n\n" +
									"停车场 酒店提供免费停车位\n\n" +
									"酒店设施 免费Wifi、免费停车、游泳池、健身房、商务中心、会议室、中餐厅、西餐厅、邮政服务、叫车服务、房间消毒、租借笔记本电脑、多种语言服务人员、自行车租借服务、管家服务、婚宴服务、叫醒服务、洗衣服务、送餐服务、旅游服务、租车服务、专职行李员、擦鞋服务、行李寄存、票务服务、外币兑换服务、有电梯、前台保险柜、自助取款机、免费旅游交通图、茶室、商品部、安全消防系统、大堂吧、公共区域闭路电视监控系统、咖啡厅、酒吧、电子结账系统、大堂免费报纸、无烟楼层、行政酒廊、24小时热水、大堂提供上网电脑、宴会厅、棋牌室、桑拿浴室、美容美发室、卡拉OK厅、SPA、足浴/足疗、按摩保健、夜总会、休闲会所、歌舞厅\n\n" +
										"酒店服务 室外游泳池、健身房、麻将房、桑拿、水疗、SPA\n\n" +
										"酒店简介 ◆与澳门隔海相望 出门即是拱北口岸 经典华贵 　　珠海来魅力假日酒店座落于珠海拱北口岸地处最繁华的商业地段，与澳门隔海相望，距拱北口岸一步之遥，并与口岸地下商场一脉相连。汽车总站近在咫尺，距珠海机场30分钟左右车程，距九州港码头10分钟左右车程，交通四通八达，令观光、购物或进行商贸活动时，倍觉从容。 　　◆设计风格时尚典雅 独具匠心 感受来魅力假日的独特风格 　　珠海来魅力假日酒店拥有295间舒适、温馨的豪华客房和套房，全开放式的浴室,全部配备先进的电子设备，包括宽带互联网、卫星电视、国内/国际长途电话、保险箱等等，配备西餐厅、健身房、康乐中心等餐饮娱乐设施，让您在繁忙的工作之余身心得到放松，是您来珠海旅行度假或商务访问的理想下榻之所。 　　酒店开业时间2009年9月29日，楼高23层，客房总数295间（套）。 酒店退房时间在中午12:00前，入住在14:00后。\n\n" +
										"可接受的信用卡\n\n" +
									"免费提供500个停车位\n\n"));
					
					goodsInfoList.add(0, new GoodsInfo(103, "珠海畔山海逸度假酒店 ",
							"仅售332.00元！[拱北商业区]香洲区九洲大道兰埔路圆明新园西侧(梦幻水城旁","2014.6.27 至 2016.7.28", 680f, 596f, 0,
							"http://pavo.elongstatic.com/i/Hotel795_325/0000dLcv.jpg",
							"酒店电话 0756-8593888-8058 艺龙电话预订：4009-333-333\n\n" +
							"网服务 公共区域提供免费WiFi\n\n" +
							"停车场 酒店提供免费停车位\n\n" +
							"开业时间 酒店开业时间 2011年\n\n" +
							"酒店设施 免费Wifi、免费停车、游泳池、商务中心、会议室、西餐厅、医疗支援、叫醒服务、洗衣服务、送餐服务、旅游服务、专职行李员、行李寄存、外币兑换服务、前台保险柜、免费旅游交通图、酒吧、大堂免费报纸、24小时热水、桑拿浴室、美容美发室、按摩保健\n\n" +
							"酒店服务 桑拿按摩、室外游泳池\n\n" +
							"酒店简介 珠海畔山海逸度假酒店位处珠海市商贸中心的拱北地区，坐落于风景优美的大石林山麓旁，毗邻古埃及风韵的情景式水上乐园-梦幻水城，与国家4A旅游景点皇国园林-圆明新园相望，距珠海长隆只需25分钟车程，九州港客运码头、拱北出入境口岸只需7分钟车程，前台轻轨仅需5分钟车程，交通便捷，适合度假及商贸之旅，是一家按国际四星级标准全新装修的圆林式豪华型商务度假酒店，共设有47间豪华数码E房，16栋独立别墅，客房设计简洁优雅，家私典雅考究，客房均配有19寸液晶电脑、免费上网、拥有3000多部不断更新国内国际大片！\n\n" +
							"豪华spa房 房间38㎡ | 大床 | 可住：   | 楼层：3层 | 免费WIFI[免费]\n\n" +
							"产品名称	供应商	早餐	取消规则	日均价	 	 	 	 \n\n" +
							"不含早	艺龙	无早	免费取消	¥624	668返44		预订	 \n\n" +
							"单早（优质-预付） 	艺龙	单早	不可取消	¥644			预订	 \n\n" +
							"含单早 	艺龙	单早	免费取消	¥652	696返44		预订	 \n\n" +
							"不含早（优质-预付）	艺龙	无早	不可取消	¥668	\n\n" +
							"免费提供400个停车位\n\n"));

					mInterHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			String randnumber = data.getExtras().getString("result").trim();
			
//			ToastUtils.showToast(activity, randnumber, 1);
			
//			Uri uri = Uri.parse(randnumber);
//	        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//	        startActivity(intent);    
			
			GoodsInfoDao goodsInfoDao = new GoodsInfoDao(activity);
			
			GoodsInfo goodsInfo = goodsInfoDao.selectGoodsInfosById(Integer.parseInt(randnumber));
			Intent intent = new Intent(activity, TuanDailActivity.class);
			intent.putExtra("goodsInfo", goodsInfo);
			startActivity(intent);
			
		}
	}
	
}
