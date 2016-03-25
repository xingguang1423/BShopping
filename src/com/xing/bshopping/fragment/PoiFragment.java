package com.xing.bshopping.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.xing.bshopping.BaseFragment;
import com.xing.bshopping.R;
import com.xing.bshopping.activity.PoiDailActivity;
import com.xing.bshopping.activity.SearchActivity;
import com.xing.bshopping.activity.TuanDailActivity;
import com.xing.bshopping.adapter.BusinessInfoAdapter;
import com.xing.bshopping.dao.BusinessInfoDao;
import com.xing.bshopping.entity.BusinessInfo;
import com.xing.bshopping.entity.GoodsInfo;
import com.xing.bshopping.utils.TitleBuilder;
import com.xing.bshopping.utils.ToastUtils;
import com.xing.bshopping.widget.ExpandTabView;
import com.xing.bshopping.widget.MeiTuanListView;
import com.xing.bshopping.widget.MeiTuanListView.OnMeiTuanRefreshListener;
import com.xing.bshopping.widget.ViewCity;
import com.xing.bshopping.widget.ViewClassify;
import com.xing.bshopping.widget.ViewSort;

@SuppressLint("ResourceAsColor")
public class PoiFragment extends BaseFragment implements
		OnMeiTuanRefreshListener {

	private View view;

	// ==================下拉刷新的动画==================
	private static MeiTuanListView mListView;
	
	// =======================商家列表的listview======================
	private static BusinessInfoAdapter businessInfoAdapter;
	private List<BusinessInfo> businessInfos;
	private BusinessInfoDao businessInfoDao;
	private ImageView imageView_No_poi_search;
	
	
	//刷新
	private final static int REFRESH_COMPLETE = 0;
	
	
	
	/**
	 * 判断是否到达底部
	 */
//	private boolean isLastRow = false;
//
//	private static View footView;
//
//	private final static int REFRESH_FOOTER = 1;
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
				businessInfoAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				break;
			default:
				break;
			}
		}
	}

	
	
	//===================二级菜单================
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewClassify viewClassify;
	private ViewCity viewCity;
	private ViewSort viewRight;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initView();
		
		initVaule();
		initListener();


		return view;
	}
	

	private void initView() {
		view = View.inflate(activity, R.layout.frag_poi, null);
		
		new TitleBuilder(view)
		.setLeftText("全部商家")
		.setRightImage2(R.drawable.food_ic_actionbar_ic_map)
		.setRightImage1(R.drawable.food_ic_actionbar_search)
		.setBgColor(R.color.white)
		.setLeftTextColor(R.color.black)
		.build();
		

		//===================二级菜单================
		expandTabView = (ExpandTabView) view.findViewById(R.id.expandtab_view);
		viewClassify = new ViewClassify(activity);
		viewCity = new ViewCity(activity);
		viewRight = new ViewSort(activity);
		
		
		
		//================================================
		View headerView = View.inflate(activity,
				R.layout.include_seller_header, null);
		
		
		// =================下拉刷新的动画=================
		imageView_No_poi_search = (ImageView) view.findViewById(R.id.imageView_No_poi_search);
		
		mListView = (MeiTuanListView) view.findViewById(R.id.listview);
		

		mListView.addHeaderView(headerView);
		
		businessInfos = new ArrayList<BusinessInfo>();
		businessInfoDao = new BusinessInfoDao(activity);
		
//		businessInfos.add(new BusinessInfo(1, "4D特效影院",
//						"13106975707 ",
//						"观众可以通过视觉、嗅觉、听觉和触觉多重身体感官体验电影带来的全新娱乐效果，惊险、紧张刺激。 \n\n"+
//						"畅影时光4D电影全部自主研发，环境特效与影片内容更是恰到好处，影片数量多，效果好，更新快为加盟者开启财富之路。",
//						"广州畅影时光信息科技",
//						"电影", 
//						"http://www.feelboxes.com/uploads/allimg/151113/2015111901.jpg"));
//		
//		businessInfos.add(new BusinessInfo(2, "畅影时光5D动感影院",
//				"13106975707 ",
//				"畅影时光5D影院是什么？ \n\n"+
//				"5D影院=3D电影+动感座椅+环境特效，是当下最流行的展现动态电影的影院。以超现实的视觉感受配以特殊的、刺激性的效果同步表现，以仿真的场景与特别的机关设置来模仿实际发生的事件，在产生呼之欲出、栩栩如生的立体画面的同时，随着剧情变化，模拟了电闪雷鸣、风霜雨雪、爆炸冲击等多种特技效果，将视觉、听觉、嗅觉、触觉和动感完美地融为一体，再加入剧情式互动游戏，并充分利用互动道具，从而使观众参与其中并全身心地融入到剧情之中，体验虚幻仿真、惊心动魄的冒险旅行。 \n\n"+
//				"\n\n5大环境特效：下雨、下雪、泡泡、烟雾、闪电（刮风）\n\n" +
//				"畅影时光5D影院组成：\n\n" +
//				"一、系统组成1、环境特效\n\n" +
//				"环境特效主要是为配合电影画面而做的特效，比如，在观看5D电影时，如果影片内在播放下雨的场面，我们所做的特效，能观众感到有雨淋在身上，电影中刮起了风，观众便同步感觉到有风吹来，电影中起了雾，观众也感觉到有雾在身边弥漫……环境特效系统的设备主要包括泡泡机、喷水机、吹风机、雪花机、烟雾机、闪频机、空压机，他们能营造出下雨、刮风、下雪、烟雾、闪电等多种感觉。\n\n" +
//				"2、座椅特效\n\n" +
//				"座椅特效系统，能让观众感受到颠簸、震颤、撞车、急转弯等效果。在观众观看冒险片、恐怖片的时候，座椅特效体现的尤其明显。比如，观众正在看过山车的影片，座椅能让观众感到起起伏伏、上下翻飞，在电影这个虚拟的世界里穿山越岭，在室内便体验到在崇山峻岭中飞速穿梭的刺激感。\n\n" +
//				"3、影音特效\n\n" +
//				"畅影时光的影音特效系统包括金属屏幕、投影机、机架(带偏正镜、片)、播放服务器、控制机柜、银幕、低音音箱、主音箱、环绕音箱、功放等设备。\n\n" +
//				"软件系统包括影片特效动作编辑器、影片播放器、同步控制系统、动作特效系统和环境特效系统。设备主要包括高配置计算机、显示器、机柜等。", 
//				"广州畅影时光信息科技", 
//				"电影", 
//				"http://www.feelboxes.com/uploads/allimg/141114/5d004.png"));
//		
//		businessInfos.add(new BusinessInfo(3, "宝马专业冷餐茶歇服务自助餐",
//				"13106975707 ",
//				"深圳冷餐会，汽车试驾冷餐服务公司，廷府外宴深圳分公司是深圳东莞等各地方最为专业冷餐会、茶歇、自助餐外卖、酒会的外宴公司。专业专注汽车新闻发布会、车展、汽车上市、汽车4s店周年庆的客户接待活动。我们为您量身定制冷餐会、茶歇、蛋糕甜点、水果、茶水咖啡外卖外送服务。" +
//				"部分合作品牌：\n\n" +
//				" 宝马、奔驰、兰博基尼、雷克萨斯、凯迪拉克、英菲尼迪、斯柯达、斯巴鲁、奥迪、大众、马自达、丰田、讴歌、雪铁龙、别克、东风日产等众多著名汽车品牌提供了100%满意的冷餐外送服务！\n\n" +
//				"  我们的用心，您的放心，我们的整套服务，让您省事省心省力更省钱。我们的优质现场服务，让您满意100%。！\n\n" +
//				  "  深圳冷餐会茶歇酒会自助餐外卖著名领先品牌—廷府外宴 \n\n" +
//				  "  深圳廷府外宴，带您走进移动美味时代！\n\n" +
//				  "  移动美味，廷府宴会 （Experience the value of delicious） \n\n" +
//					"联系方式:\n\n" +
//					"电话：戴生15361519938\n\n" +
//					"QQ：473202148 ", 
//					"廷府外宴深圳分公司", 
//					"自助餐", 
//				"http://img3.douban.com/view/note/large/public/p14051531.jpg"));
//		
//		businessInfos.add(new BusinessInfo(4, "欧美模特专业摄影写真",
//				"13106975707 ",
//				"欧美模特摄影写真" +
//						"QQ：473202148 ", 
//						"欧美模特摄影写真", 
//						"摄影写真", 
//				"http://imgtest1.china-designer.com/exhibition/UploadNew/201211/water111108/20121101143606352352.jpg"));
//
//		
//		businessInfos.add(new BusinessInfo(5, "浦江游览龙船票",
//				"13106975707 ",
//				"上海浦江夜游现已成为上海最有特色的旅游项目。游览过程中可看到横跨浦江两岸的杨浦大桥、南浦大桥和上海东方明珠广播电视塔等。两座大桥，象两条巨龙横卧于黄浦江上，中间是东方明珠电视塔，正好构成了一幅“二龙戏珠”的巨幅画卷，而上海浦江西岸一幢幢风格迥异充满浓郁异国色彩的万国建筑与浦东东岸一幢幢拔地而起高耸云间的现代建筑相映成辉，令人目不暇接。\n\n" +
//				"让您领略上海百年来的历史变迁缩影到东方之都共同谱写的辉煌。让您感受到美景和美食带来的视觉和味觉双重享受。\n\n", 
//				"浦江黄浦区中山东二路153号", 
//				"本地/周边游", 
//				"http://pmoefb368.pic22.websiteonline.cn/upload/img_0808_4by5.jpg"));
//		
//		
//		businessInfos.add(new BusinessInfo(6, "游轮婚礼-全球通号",
//				"13106975707 ",
//				"上海浦江夜游现已成为上海最有特色的旅游项目。游览过程中可看到横跨浦江两岸的杨浦大桥、南浦大桥和上海东方明珠广播电视塔等。两座大桥，象两条巨龙横卧于黄浦江上，中间是东方明珠电视塔，正好构成了一幅“二龙戏珠”的巨幅画卷，而上海浦江西岸一幢幢风格迥异充满浓郁异国色彩的万国建筑与浦东东岸一幢幢拔地而起高耸云间的现代建筑相映成辉，令人目不暇接。\n\n" +
//				"让您领略上海百年来的历史变迁缩影到东方之都共同谱写的辉煌。让您感受到美景和美食带来的视觉和味觉双重享受。\n\n", 
//				" 特惠套餐   79800元（含10桌婚宴及婚庆）", 
//				"鲜花婚庆", 
//				"http://pmoefb368.pic22.websiteonline.cn/upload/ftt3.jpg"));
//		
//		businessInfos.add(new BusinessInfo(7, "豪华婚礼-鲜花婚庆",
//				"13106975707 ",
//				"", 
//				"特惠套餐   9800元（含8桌婚宴及婚庆）",
//				"鲜花婚庆", 
//				"http://image78.360doc.com/DownloadImg/2014/09/2120/45480371_9.jpg"));
//		
//		businessInfos.add(new BusinessInfo(8, "华承户外俱乐部",
//				"13106975707 ",
//				"", 
//				"仅售780元，价值1100元暑假特惠真人CS体验券！",
//				"真人CS", 
//				"http://e.hiphotos.baidu.com/bainuo/crop%3D0%2C115%2C1000%2C606%3Bw%3D470%3Bq%3D89/sign=cdecb222010828387c42865485a98530/29381f30e924b8994392600568061d950a7bf665.jpg"));
//		
//		businessInfos.add(new BusinessInfo(9, "海谊烧烤场CS",
//				"13106975707 ",
//				"", 
//				"超带感的故事情节、紧张刺激的逃脱体验！",
//				"真人CS", 
//				"http://e.hiphotos.baidu.com/nuomi/wh%3D470%2C285/sign=56e37498a486c91708565a3dfe0d5cfd/64380cd7912397dd626d08645a82b2b7d0a287be.jpg"));
//		
//		businessInfos.add(new BusinessInfo(10, "盒战—跨界真人CS",
//				"13106975707 ",
//				"", 
//				"室内跨界真人CS！紧张刺激的逃脱体验！",
//				"真人CS", 
//				"http://e.hiphotos.baidu.com/bainuo/crop%3D0%2C140%2C4928%2C2984%3Bw%3D720%3Bq%3D99/sign=aa4eaeed292eb938f82220b2e852a904/9825bc315c6034a8a076158dcd13495409237622.jpg"));
//		
//		
//		businessInfos.add(new BusinessInfo(11, "六和门诊医疗美容+spa服务",
//				"13106975707 ",
//				"", 
//				"深层嫩肤，高效美肤，先进快捷，舒适无创，服务极好！",
//				"美容SPA", 
//				"http://s1.nuomi.bdimg.com/upload/deal/2014/2/V_L/655218-1393401034962.jpg"));
//		
//		businessInfos.add(new BusinessInfo(12, "花千树美容纤体spa会所",
//				"13106975707 ",
//				"", 
//				"宠爱自己的肌肤，释放轻松心情，让美丽从内而外，彰显魅力姿态！",
//				"美容SPA", 
//				"http://s0.nuomi.bdimg.com/upload/deal/2013/10/V_L/455618-1383275399877.jpg"));
//		
//		
//		businessInfos.add(new BusinessInfo(13, "感恩美容中心+spa服务",
//				"13106975707 ",
//				"", 
//				"最高价值888元拱北店美容套餐！节假日通用！！",
//				"美容SPA", 
//				"http://e.hiphotos.baidu.com/bainuo/wh%3D720%2C436/sign=23947564d509b3deebeaec6ffe8f40b5/3b87e950352ac65c65e2b47ffff2b21192138ad3.jpg"));
		
		
//		businessInfoDao.addBusinessInfos(businessInfos);
		businessInfoDao.showAllBusinessInfos(businessInfos);
		businessInfoAdapter = new BusinessInfoAdapter(activity, businessInfos);

		mListView.setAdapter(businessInfoAdapter);
		mListView.setOnMeiTuanRefreshListener(this);
		

	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					
					businessInfoAdapter.addItem(new BusinessInfo(101, "贵族世家牛排",
							"13106975707","", "招牌经典牛排双人套餐！免费提双人热菜、水果沙拉无限量自助！", "西餐", 
							"http://e.hiphotos.baidu.com/bainuo/crop%3D0%2C0%2C470%2C285%3Bw%3D470%3Bq%3D79/sign=1e26dc36273fb80e189e3b970be1031e/a8773912b31bb051e7d6632f307adab44bede0c3.jpg"));
					
					businessInfoAdapter.addItem(new BusinessInfo(102, "平沙上岛咖啡",
							"13106975707","", "喷香诱人，口齿留香，精选食材，绿色健康，美味可口，环境舒适，服务热情!!", "西餐", 
							"http://e.hiphotos.baidu.com/nuomi/wh%3D470%2C285/sign=fcb7074d61d0f703e6e79dd83fca7d0f/38dbb6fd5266d0167f80cf72942bd40734fa35ca.jpg"));
					
					businessInfoAdapter.addItem(new BusinessInfo(103, "船长西餐厅",
							"13106975707","", "长隆横琴湾酒店船长西餐厅，盛宴之扒双人套餐!!", "西餐", 
							"http://e.hiphotos.baidu.com/bainuo/crop%3D0%2C21%2C690%2C418%3Bw%3D470%3Bq%3D79/sign=2a4a3698a4cc7cd9ee626e9904310d0d/dc54564e9258d109353b84c7d658ccbf6d814dee.jpg"));
					
					
					mInterHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	
private void initVaule() {
		
		mViewArray.add(viewClassify);
		mViewArray.add(viewCity);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("全部分类");
		mTextArray.add("全城");
		mTextArray.add("智能排序");
		
		expandTabView.setValue(mTextArray, mViewArray);
		expandTabView.setTitle(viewClassify.getShowText(), 0);
		expandTabView.setTitle(viewCity.getShowText(), 1);
		expandTabView.setTitle(viewRight.getShowText(), 2);
		
	}

	private void initListener() {
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BusinessInfo businessInfo = businessInfoAdapter.getItem(position-2);
//				ToastUtils.showToast(activity, "商家信息-->"+businessInfo, 1);
				System.out.println("商家信息-->"+businessInfo);
				Intent intent = new Intent(activity, PoiDailActivity.class);
				intent.putExtra("businessInfo", businessInfo);
				startActivity(intent);
								
			}
		});
		
		
		viewClassify.setOnSelectListener(new ViewClassify.OnSelectListener() {


			@Override
			public void getValue(String showText) {
				onRefresh(viewClassify, showText);
				
			}
		});
		
		viewCity.setOnSelectListener(new ViewCity.OnSelectListener() {
			
			@Override
			public void getValue(String showText) {
				
				onRefresh(viewCity,showText);
				
			}
		});
		
		viewRight.setOnSelectListener(new ViewSort.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewRight, showText);
			}
		});
		
	}
	
	private void onRefresh(View view, String showText) {
		
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		
		//获取到二级菜单里的数据
		Toast.makeText(activity, showText, Toast.LENGTH_SHORT).show();

		if (showText == "全部分类") {
			
			businessInfos =  businessInfoDao.selectBusinessInfosByType("");
			
			mListView.setVisibility(View.VISIBLE);
			imageView_No_poi_search.setVisibility(View.GONE);
			businessInfoAdapter = new  BusinessInfoAdapter(activity, businessInfos);
			mListView.setAdapter(businessInfoAdapter);
			mListView.setOnMeiTuanRefreshListener(this);
		}else{
			businessInfos =  businessInfoDao.selectBusinessInfosByType(showText);
			
			if (businessInfos != null && businessInfos.size() > 0) {
				mListView.setVisibility(View.VISIBLE);
				imageView_No_poi_search.setVisibility(View.GONE);
				businessInfoAdapter = new  BusinessInfoAdapter(activity, businessInfos);
				mListView.setAdapter(businessInfoAdapter);
				mListView.setOnMeiTuanRefreshListener(this);
				
			}else {
				mListView.setVisibility(View.GONE);
				imageView_No_poi_search.setVisibility(View.VISIBLE);
			}
			
		}
		
		
		
		
		
	}
	
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	

}
