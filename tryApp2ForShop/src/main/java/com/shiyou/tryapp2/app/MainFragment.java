package com.shiyou.tryapp2.app;

import android.annotation.SuppressLint;
import android.extend.ErrorInfo;
import android.extend.app.BaseFragment;
import android.extend.loader.BaseParser.DataFrom;
import android.extend.loader.BitmapLoader.DecodeMode;
import android.extend.util.AndroidUtils;
import android.extend.util.LogUtil;
import android.extend.util.ResourceUtil;
import android.extend.util.ViewTools;
import android.extend.widget.ExtendImageView;
import android.extend.widget.MenuBar;
import android.extend.widget.MenuBar.OnMenuListener;
import android.extend.widget.MenuView;
import android.extend.widget.adapter.AbsAdapterItem;
import android.extend.widget.adapter.BaseGridAdapter;
import android.extend.widget.adapter.ScrollGridView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiyou.tryapp2.Config;
import com.shiyou.tryapp2.RequestManager;
import com.shiyou.tryapp2.RequestManager.RequestCallback;
import com.shiyou.tryapp2.app.login.LoginFragment;
import com.shiyou.tryapp2.app.login.LoginHelper;
import com.shiyou.tryapp2.app.product.BrowseHistoryFragment;
import com.shiyou.tryapp2.app.product.MainIndexFragment;
import com.shiyou.tryapp2.app.product.MainWebFragment;
import com.shiyou.tryapp2.app.product.OtherIndexFragment;
import com.shiyou.tryapp2.app.product.ProductDetailsFragment;
import com.shiyou.tryapp2.app.product.SettingFragment;
import com.shiyou.tryapp2.data.response.BaseResponse;
import com.shiyou.tryapp2.data.response.ShopLogoAndADResponse;
import com.shiyou.tryapp2.data.response.ShoppingcartListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shiyou.tryapp2.app.product.MainIndexFragment.getPicturePixel;


public class MainFragment extends BaseFragment {
	public static MainFragment instance = null;

	private View menubar_layout;
	private View setting_show;
	private MenuBar mMenuBar;
	private ExtendImageView mLogoImageView;
	private ShopLogoAndADResponse mShopLogoAndADResponse;
	private TextView mShoppingcartNum;
	private ImageView mCustomized;
	private ImageView mOther;
	private LinearLayout mMainPage;

	private ImageView mLogo;
	private ImageView mgia;
	private ImageView mTrain;

	// private View fragmentC, fragmentC1, fragmentC2, fragmentC3;
	// private int fragmentCID, fragmentC1ID, fragmentC2ID, fragmentC3ID;
	// private BaseFragment fragment1, fragment2, fragment3;
	// private MainIndexFragment mMainIndexFragment;
	// private OtherIndexFragment mOtherIndexFragment;
	// private MainWebFragment mSearchFragment;

	private String mGoodsId;
	private String mGoodsTag;
	private String token;
	private BaseGridAdapter mAdapter;
	// private boolean mGoodsIsShop;

	// public MainIndexFragment mFragment1 = new MainIndexFragment();
	// public MainRecommendWebFragment mFragment1 = new MainRecommendWebFragment(Config.WebProductCategory, 0);
	// public MainRecommendWebFragment mFragment2 = new MainRecommendWebFragment(Config.WebShoppingCart, 0);
	// public MainRecommendWebFragment mFragment3 = new MainRecommendWebFragment(Config.WebSearch, 0);
	// public MainRecommendWebFragment mFragment4 = new MainRecommendWebFragment(Config.WebOrder, 0);

	@SuppressLint("ValidFragment")
	public MainFragment(String goodsId, String tag) {
		this.mGoodsId = goodsId;
		this.mGoodsTag = tag;
		instance = this;
	}

	public MainFragment(){}

	// @Override
	// public void onCreate(Bundle savedInstanceState)
	// {
	// super.onCreate(savedInstanceState);
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mLayoutResID = ResourceUtil.getLayoutId(getContext(), "main_layout");
		View view = super.onCreateView(inflater, container, savedInstanceState);
		AndroidUtils.MainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				LoginFragment loginFragment=new LoginFragment();
				loginFragment.getToken();
			}
		},720000);
		int id = ResourceUtil.getId(getContext(), "menubar_layout");
		menubar_layout = view.findViewById(id);

		id=ResourceUtil.getId(getContext(),"setting_show");
		setting_show=view.findViewById(id);

//		id = ResourceUtil.getId(getContext(), "menu_container");
//		ScrollGridView menu_container = (ScrollGridView)view.findViewById(id);
//		menu_container.setNumColumns(3);
//		int space = AndroidUtils.dp2px(getContext(), 40);
//		menu_container.setVerticalDividerWidth(space);
//		menu_container.setHorizontalDividerHeight(space);
//		mAdapter = new BaseGridAdapter<AbsAdapterItem>();
//		menu_container.setAdapter(mAdapter);

		// fragmentCID = ResourceUtil.getId(getContext(), "fragment_container");
		// fragmentC = view.findViewById(fragmentCID);
		// fragmentC1ID = ResourceUtil.getId(getContext(), "fragment_container1");
		// fragmentC1 = view.findViewById(fragmentC1ID);
		// fragmentC2ID = ResourceUtil.getId(getContext(), "fragment_container2");
		// fragmentC2 = view.findViewById(fragmentC2ID);
		// fragmentC3ID = ResourceUtil.getId(getContext(), "fragment_container3");
		// fragmentC3 = view.findViewById(fragmentC3ID);

		// fragment1 = new ContainerFragment();
		// fragment2 = new ContainerFragment();
		// fragment3 = new ContainerFragment();
		// add(this, fragmentC1ID, fragment1, FragmentTransaction.TRANSIT_NONE, false);
		// add(this, fragmentC2ID, fragment2, FragmentTransaction.TRANSIT_NONE, false);
		// add(this, fragmentC3ID, fragment3, FragmentTransaction.TRANSIT_NONE, false);

		id=ResourceUtil.getId(getContext(),"mainPage");
		mMainPage= (LinearLayout) view.findViewById(id);

		id=ResourceUtil.getId(getContext(),"gia");
		mgia= (ImageView) view.findViewById(id);
		mgia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMainPage.setVisibility(View.GONE);
				setting_show.setVisibility(View.VISIBLE);
				menubar_layout.setVisibility(View.VISIBLE);
				url=Config.BasePrefix+"/addons/ewei_shop/template/pad/default/api/new-gia.html?isFromOrder=false";

				MainFragment.instance.addFragmentToCurrent(new MainWebFragment(url,0), false);
			}
		});
		id=ResourceUtil.getId(getContext(),"train");
		mTrain= (ImageView) view.findViewById(id);
		mTrain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				mMainPage.setVisibility(View.GONE);
				setting_show.setVisibility(View.VISIBLE);
				menubar_layout.setVisibility(View.VISIBLE);
				url="http://www.zsmtvip.com/web/index.php?c=article&a=notice-show&do=detail&id=3";

				MainFragment.instance.addFragmentToCurrent(new MainWebFragment(url,0), false);
			}
		});

		id=ResourceUtil.getId(getContext(),"customized");
		mCustomized= (ImageView) view.findViewById(id);
		mCustomized.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				setCurrentMenuImpl(6);

			}
		});


		id=ResourceUtil.getId(getContext(),"other");
		mOther= (ImageView) view.findViewById(id);
		mOther.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentMenuImpl(1);

			}
		});




		id = ResourceUtil.getId(getContext(), "menubar");
		mMenuBar = (MenuBar) view.findViewById(id);
		mMenuBar.setOnMenuListener(new OnMenuListener() {
			@Override
			public void onMenuUnSelected(MenuBar menuBar, MenuView menuView, int menuIndex) {
			}

			@Override
			public void onMenuSelected(MenuBar menuBar, MenuView menuView, int menuIndex) {
				LogUtil.d(TAG, "onMenuSelected: " + menuIndex);
//				int menuIndex2;
//				if(menuIndex==1){
//					menuIndex2=2;
//				}else if(menuIndex==2){
//					menuIndex2=1;
//				}else {
//					menuIndex2=menuIndex;
//				}
				setCurrentMenuImpl(menuIndex);
			}
		});

		mMenuBar.setCurrentMenu(6);

		id = ResourceUtil.getId(getContext(), "setting");
		final View setting = view.findViewById(id);
		setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AndroidUtils.isFastClick())
					return;
				// try
				// {
				// PackageInfo pi = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
				// String url = Config.WebLoginOut + "?version=" + pi.versionName + "&key="
				// + LoginHelper.getUserKey(getContext());
				// FragmentActivity.launchMe(getActivity(), new MainWebFragment(url, 2));
				// }
				// catch (NameNotFoundException e)
				// {
				// e.printStackTrace();
				// }
				FragmentActivity.launchMe(getActivity(), new SettingFragment());
			}
		});

		id = ResourceUtil.getId(getContext(), "shopping");
		final View shopping = view.findViewById(id);
		shopping.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AndroidUtils.isFastClick())
					return;
				mMainPage.setVisibility(View.GONE);
//				url=Config.BasePrefix+"/addons/ewei_shop/template/pad/default/shop/new-cart.html";
//				replace(instance, new MainWebFragment(url, 0), false);
				setCurrentMenu(2);
			}
		});

		id = ResourceUtil.getId(getContext(), "dingdan");
		final View dingdan = view.findViewById(id);
		dingdan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AndroidUtils.isFastClick())
					return;
				mMainPage.setVisibility(View.GONE);
//				url=Config.BasePrefix+"/addons/ewei_shop/template/pad/default/shop/new-order.html";
//				replace(instance, new MainWebFragment(url, 0), false);
				setCurrentMenu(3);
			}
		});


		id = ResourceUtil.getId(getContext(), "shoucang");
		final View shoucang = view.findViewById(id);
		shoucang.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AndroidUtils.isFastClick())
					return;
//				mMainPage.setVisibility(View.GONE);
//				url=Config.BasePrefix+"/addons/ewei_shop/template/pad/default/shop/new-collection.html";
//				replace(instance, new MainWebFragment(url, 0), false);
				setCurrentMenu(4);
			}
		});




		id=ResourceUtil.getId(getContext(),"logo");
		mLogo= (ImageView) view.findViewById(id);

//		Bitmap bitmap= ((BitmapDrawable)mLogo.getDrawable()).getBitmap();
//		ArrayList<Integer> picturePixel= getPicturePixel(bitmap);
//		HashMap<Integer,Integer> color2=new HashMap<>();
//		for (Integer color:picturePixel){
//			if (color2.containsKey(color)){
//				Integer integer = color2.get(color);
//				integer++;
//				color2.remove(color);
//				color2.put(color,integer);
//
//			}else{
//				color2.put(color,1);
//			}
//		}
//		//挑选数量最多的颜色
//		Iterator iter = color2.entrySet().iterator();
//		int count=0;
//		int color=0;
//		while (iter.hasNext()) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			int value = (int) entry.getValue();
//			if (count<value){
//				count=value;
//				color= (int) entry.getKey();
//			}
//
//		}
//		mLogoShow.setBackgroundColor(color);

		id = ResourceUtil.getId(getActivity(), "main_tryon");
		mLogoImageView = (ExtendImageView) view.findViewById(id);
		mLogoImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMainPage.setVisibility(View.VISIBLE);
//				replace(instance, new MainFragment(), false);
			}
		});
//		mLogoImageView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// if (AndroidUtils.isFastClick())
//				// return;
//				// backToHomepage();
//				// if (mGoodsTag.equals(Define.TAG_RING))
//				// MainActivity.launchTryonScene(getActivity(), mGoodsId);
//				// else
//				// MainActivity.launchTryonSceneWithCoupleRing(getActivity(), mGoodsId);
//			}
//		});
		ensureShopLogo();


		id = ResourceUtil.getId(getContext(), "shoppingcart_num");
		mShoppingcartNum = (TextView) view.findViewById(id);
		mShoppingcartNum.setVisibility(View.GONE);
		updateShoppingcartNumImpl();

		// adapterScaled
		menubar_layout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
									   int oldRight, int oldBottom) {
				LogUtil.v(TAG, "menubar_layout onLayoutChange: " + v);
				if (menubar_layout.getWidth() == 0 || menubar_layout.getHeight() == 0)
					return;
				menubar_layout.removeOnLayoutChangeListener(this);

				float scaled = MainActivity.scaled;

				ViewTools.adapterViewWidth(menubar_layout, scaled);

				ViewTools.adapterViewHeight(mLogoImageView, scaled);
				ViewTools.adapterViewPadding(mLogoImageView, scaled);

				int count = mMenuBar.getMenuCount();
				for (int i = 0; i < count; i++) {
					MenuView menu = mMenuBar.getMenu(i);
					ViewTools.adapterViewHeight(menu, scaled);
					ViewTools.adapterViewPadding(menu, scaled);
				}

				ViewTools.adapterViewSize(mShoppingcartNum, scaled);
				ViewTools.adapterViewMargin(mShoppingcartNum, scaled);
				ViewTools.adapterTextViewTextSize(mShoppingcartNum, MainActivity.fontScaled);

				ViewTools.adapterViewHeight(setting, scaled);
				ViewTools.adapterViewPadding(setting, scaled);
			}
		});



		return view;
	}

	// @Override
	// public void onActivityCreated(@Nullable Bundle savedInstanceState)
	// {
	// super.onActivityCreated(savedInstanceState);
	// }

	@Override
	public void onDestroy() {
		super.onDestroy();

		instance = null;
	}

	private void ensureShopLogo() {
		Request request=new Request.Builder().url("https://api.i888vip.com/members/me?token="+ Config.token).addHeader("accept","application/vnd.zltech.shop.v1+json").get().build();
		OkHttpClient okHttpClient=new OkHttpClient();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				showToast("没有头像");
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				final  String json=response.body().string();
				AndroidUtils.MainHandler.post(new Runnable() {
					@Override
					public void run() {
						int start=json.indexOf("logo");
						int end=json.lastIndexOf("}");
						String jpg=json.substring(start+7,end-2).replace("\\/","/");
						mLogoImageView.setImageDataSource(jpg,
								0, DecodeMode.FIT_WIDTH);
						mLogoImageView.startImageLoad(false);
//						Request request1=new Request.Builder().url(url).build();
//						OkHttpClient okHttpClient1=new OkHttpClient();
//						okHttpClient.newCall(request).enqueue(new Callback() {
//							@Override
//							public void onFailure(Call call, IOException e) {
//
//							}
//
//							@Override
//							public void onResponse(Call call, Response response) throws IOException {
//								byte[] Picture = response.body().bytes();
//								//通过imageview，设置图片
//                                Bitmap bitmap=BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
//								mLogoImageView.setImageBitmap(BitmapFactory.decodeByteArray(Picture, 0, Picture.length));
//							}
//						});
					}
				});
			}
		});



	}

	private void updateShoppingcartNumText(final int num) {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (num > 0) {
					mShoppingcartNum.setText("" + num);
//					mShoppingcartNum.setVisibility(View.VISIBLE);
				} else
					mShoppingcartNum.setVisibility(View.GONE);
			}
		});
	}

	private void updateShoppingcartNumImpl() {
		RequestManager.loadShoppingcartList(getContext(), LoginHelper.getUserKey(getContext()), new RequestCallback() {
			@Override
			public void onRequestResult(int requestCode, long taskId, BaseResponse response, DataFrom from) {
				if (response.resultCode == BaseResponse.RESULT_OK) {
					ShoppingcartListResponse sclResponse = (ShoppingcartListResponse) response;
					int num = 0;
					if (sclResponse.datas != null && sclResponse.datas.list != null)
						num = sclResponse.datas.list.length;
					updateShoppingcartNumText(num);
				} else {
					// showToast(response.error);
					updateShoppingcartNumText(0);
				}
			}

			@Override
			public void onRequestError(int requestCode, long taskId, ErrorInfo error) {
				// updateShoppingcartNum();
				updateShoppingcartNumText(0);
			}
		});
	}

	public void updateShoppingcartNum() {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (isResumed()) {
					LogUtil.d(TAG, "updateShoppingcartNum...");
					updateShoppingcartNumImpl();
				} else
					AndroidUtils.MainHandler.postDelayed(this, 50L);
			}
		});
	}
	public void updateShoppingcartNum(final int num) {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (isResumed()) {
					LogUtil.d(TAG, "updateShoppingcartNum...");
					updateShoppingcartNumText(num);
				} else
					AndroidUtils.MainHandler.postDelayed(this, 50L);
			}
		});
	}

	public void doRefresh() {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (isResumed()) {
					LogUtil.d(TAG, "doRefresh...");
					ensureShopLogo();
					updateShoppingcartNumImpl();
				} else
					AndroidUtils.MainHandler.postDelayed(this, 50L);
			}
		});
	}

	// private void ensureMainIndexFragment()
	// {
	// if (mMainIndexFragment == null)
	// {
	// mMainIndexFragment = new MainIndexFragment();
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (fragment1.isResumed())
	// {
	// add(fragment1, mMainIndexFragment, false);
	// }
	// else
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 50L);
	// }
	// }
	// });
	// }
	// }

	// private void ensureOtherIndexFragment()
	// {
	// if (mOtherIndexFragment == null)
	// {
	// mOtherIndexFragment = new OtherIndexFragment();
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (fragment2.isResumed())
	// {
	// add(fragment2, mOtherIndexFragment, false);
	// }
	// else
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 50L);
	// }
	// }
	// });
	// }
	// }

	// private void ensureSearchFragment()
	// {
	// if (mSearchFragment == null)
	// {
	// mSearchFragment = new MainWebFragment(Config.WebSearch, 0);
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (fragment3.isResumed())
	// {
	// add(fragment3, mSearchFragment, false);
	// }
	// else
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 50L);
	// }
	// }
	// });
	// }
	// }
	Handler handler;
	//获取toKen
//	public String getToken(){
//		handler=new Handler();
//
//		FormBody formBody=new FormBody.Builder().add("username",LoginHelper.getUserName(getContext())).add("password",LoginHelper.getUserPassword(getContext())).build();
//		Request request=new Request.Builder().url("https://api.i888vip.com/login").addHeader("Accept","application/vnd.zltech.shop.v1+json").post(formBody).build();
//		OkHttpClient okHttpClient=new OkHttpClient();
//		okHttpClient.newCall(request).enqueue(new Callback() {
//			private String token;
//
//			@Override
//			public void onFailure(Call call, IOException e) {
//				System.out.println(e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				String all = response.body().string();
//				int i = all.indexOf("access_token");
//				int j = all.indexOf("token_type");
//				Log.d(TAG, "onResponse: all=" + all);
//				Log.d(TAG, "onResponse: i=" + i);
//				Log.d(TAG, "onResponse: j=" + j);
//				try {
//					token = all.substring(i + 15, j - 3);
//					handler.post(new Runnable() {
//						@Override
//						public void run() {
////									int id=ResourceUtil.getId(getContext(),"test_token");
////									WebView webView=(WebView) getView().findViewById(id);
////									webView.loadUrl("http://www.zsa888.com/addons/ewei_shop/template/pad/default/shop/getToken.html?token="+token);
//							Config.token=token;
////									replace(instance, new MainWebFragment("http://www.zsa888.com/addons/ewei_shop/template/pad/default/shop/getToken.html?token="+token, 0,true), false);
//
////									Log.d(TAG, "run: textToken="+textView.getText());
////                                	OkHttpClient okHttpClient1=new OkHttpClient();
////									FormBody formBody=new FormBody.Builder().add("token",token).build();
////									final Request request=new Request.Builder().url("http://www.zsa888.com/addons/ewei_shop/template/pad/default/shop/getToken.html").post(formBody).build();
////									OkHttpClient okHttpClient=new OkHttpClient();
////									okHttpClient.newCall(request).enqueue(new Callback() {
////										@Override
////										public void onFailure(Call call, IOException e) {
////											Log.d(TAG, "onFailure: 222执行");
////										}
////
////										@Override
////										public void onResponse(Call call, Response response) throws IOException {
////											String token=response.body().string();
////
////											Log.d(TAG, "onResponse: 222执行 token="+token);
////										}
////									});
////                                    Log.d(TAG, "run: token222="+token);
////									int id=ResourceUtil.getId(getContext(),"test_token");
////									WebView webView= (WebView) getView().findViewById(id);
////									webView.setWebViewClient(new WebViewClient());
////									webView.evaluateJavascript("http://www.zsa888.com/addons/ewei_shop/template/pad/default/shop/getToken.html?token=" + token, new ValueCallback<String>() {
////										@Override
////										public void onReceiveValue(String value) {
////											Log.d(TAG, "onReceiveValue: value="+value);
////										}
////									});
//						}
//					});
//				}catch (Exception e){
//
//				}
//			}
//		});
//			RequestManager.getToken(getContext(), LoginHelper.getUserName(getCo ntext()), LoginHelper.getUserPassword(getContext()), new RequestCallback() {
//				@Override
//				public void onRequestError(int requestCode, long taskId, ErrorInfo error) {
//

	//				}
//
//				@Override
//				public void onRequestResult(int requestCode, long taskId, BaseResponse response, DataFrom from) {
//					TokenResponse tokenResponse=(TokenResponse)response;
//					token=tokenResponse.tokenInfo.token;
//
//					Log.d(TAG, "onRequestResult: token="+token);
//				}
//			});
//		return  Config.token;
//	}
	String url;
	private static long lastClickTime;
	private void setCurrentMenuImpl(final int index) {
		// fragmentC1.setVisibility(View.INVISIBLE);
		// fragmentC2.setVisibility(View.INVISIBLE);
		// fragmentC3.setVisibility(View.INVISIBLE);
		// fragmentC.setVisibility(View.INVISIBLE);
		// if (ProductDetailsFragment.instance != null)
		// ProductDetailsFragment.instance.onBackPressed();
		long curClickTime = System.currentTimeMillis();
		Log.d(TAG, "run: curClickTime="+curClickTime);
		Log.d(TAG, "run: lastClickTime="+lastClickTime);
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (popBackStackInclusive()) {
					AndroidUtils.MainHandler.postDelayed(this, 300L);
					return;
				}



				switch (index) {
					case 0://主页
						mMainPage.setVisibility(View.VISIBLE);
						menubar_layout.setVisibility(View.GONE);
						setting_show.setVisibility(View.GONE);
//						replace(instance, new MainFragment(), false);

						break;
//					case 0:// 定制
//						// fragmentC1.setVisibility(View.VISIBLE);
//						// ensureMainIndexFragment();
//						// fragment1.popBackStackInclusive();
//						mMainPage.setVisibility(View.GONE);
//						replace(instance, new MainIndexFragment(), false);
//						break;
					case 1:// 其他（配货）
						// fragmentC2.setVisibility(View.VISIBLE);
						// ensureOtherIndexFragment();
						// fragment2.popBackStackInclusive();
						mMainPage.setVisibility(View.GONE);
						menubar_layout.setVisibility(View.VISIBLE);
						setting_show.setVisibility(View.VISIBLE);
						setCurrentMenu(7);
						replace(instance, new OtherIndexFragment(), false);
						break;
					case 2:// 购物车
//						 fragmentC.setVisibility(View.VISIBLE);
						mMainPage.setVisibility(View.GONE);
						menubar_layout.setVisibility(View.VISIBLE);
//						url = Config.WebShoppingCart + "?key=" + LoginHelper.getUserKey(getContext());
						setting_show.setVisibility(View.VISIBLE);
						url=Config.BasePrefix+"/addons/ewei_shop/template/pad/default/shop/new-cart.html";
//						url="https://www.baidu.com";
						replace(instance, new MainWebFragment(url, 0), false);

						updateShoppingcartNum();

//						int id=ResourceUtil.getId(getContext(),"test_token");
//						WebView webView= (WebView) getView().findViewById(id);
//						webView.setVisibility(View.VISIBLE);
//						RequestManager.getToken(getContext(), LoginHelper.getUserName(getContext()), LoginHelper.getUserPassword(getContext()), new RequestCallback() {
//							@Override
//							public void onRequestError(int requestCode, long taskId, ErrorInfo error) {
//
//							}
//
//							@Override
//							public void onRequestResult(int requestCode, long taskId, BaseResponse response, DataFrom from) {
//								TokenResponse tokenResponse=(TokenResponse)response;
//								 token=tokenResponse.tokenInfo.token;
//								Log.d(TAG, "onRequestResult: 执行");
//								Log.d(TAG, "onRequestResult: token="+token);
//							}
//						});
//						WebViewFragment.instance.getToken();
						Log.d(TAG, "run: 执行完毕");
						break;
					case 3:// 订单
						// fragmentC.setVisibility(View.VISIBLE);
//						url = Config.WebOrder + "?key=" + LoginHelper.getUserKey(getContext());
						mMainPage.setVisibility(View.GONE);
						setting_show.setVisibility(View.VISIBLE);
						menubar_layout.setVisibility(View.VISIBLE);
						url=Config.BasePrefix+"/addons/ewei_shop/template/pad/default/shop/new-order.html";
						replace(instance, new MainWebFragment(url, 0), false);
						break;
					case 4:// 收藏
						// fragmentC3.setVisibility(View.VISIBLE);
//						url = Config.WebSearch + "?key=" + LoginHelper.getUserKey(getContext());
						mMainPage.setVisibility(View.GONE);
						menubar_layout.setVisibility(View.VISIBLE);
						setting_show.setVisibility(View.VISIBLE);
						url=Config.BasePrefix+"/addons/ewei_shop/template/pad/default/shop/new-collection.html";
						replace(instance, new MainWebFragment(url, 0), false);
						// ensureSearchFragment();
						// fragment3.popBackStackInclusive();
						break;
					case 5:// 足迹
						// fragmentC.setVisibility(View.VISIBLE);
//						mMainPage.setVisibility(View.GONE);
//						replace(instance, new BrowseHistoryFragment(), false);
						menubar_layout.setVisibility(View.VISIBLE);
						setting_show.setVisibility(View.VISIBLE);
						FragmentActivity.launchMe(getActivity(), new SettingFragment());
						break;
					case 6:
						mMainPage.setVisibility(View.GONE);
						menubar_layout.setVisibility(View.VISIBLE);
						setting_show.setVisibility(View.VISIBLE);
						setCurrentMenu(7);
						replace(instance, new MainIndexFragment(), false);
						break;
                    case  7:
                        break;
				}

				invalidateMenuBar();
			}
		});
	}

	@Override
	public boolean onBackPressed() {
		invalidateMenuBar();
		// if (fragmentC1.getVisibility() == View.VISIBLE)
		// {
		// return fragment1.onBackPressed();
		// }
		// else if (fragmentC2.getVisibility() == View.VISIBLE)
		// {
		// return fragment2.onBackPressed();
		// }
		// else if (fragmentC3.getVisibility() == View.VISIBLE)
		// {
		// return fragment3.onBackPressed();
		// }
		// else if (fragmentC.getVisibility() == View.VISIBLE)
		// {
		// return super.onBackPressed();
		// }
		return super.onBackPressed();
	}

	// @Override
	// public boolean popBackStackInclusive()
	// {
	// boolean result = (fragment1.popBackStackInclusive() || fragment2.popBackStackInclusive()
	// || fragment3.popBackStackInclusive() || super.popBackStackInclusive());
	// return result;
	// }

	public void setCurrentMenu(final int index) {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				LogUtil.v(TAG, "setCurrentFragment: " + index + "; " + isResumed());
				if (isResumed()) {
					mMenuBar.setCurrentMenu(index);
				} else
					AndroidUtils.MainHandler.postDelayed(this, 50L);
			}
		});
	}

	// String Carurl;
	// public void setCurrentFragmentCar(String url1)
	// {
	// Carurl = url1;
	// if (Carurl == null)
	// {
	// Carurl = Config.WebShoppingCart;
	// }
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (isResumed())
	// {
	// mFragment2 = new MainRecommendWebFragment(Carurl, 0);
	// // WebInterfaceFragment.instance.refresh();
	// replace(MainFragment.instance, MainFragment.instance.fragmentC2ID, mFragment2, false);
	// }
	// else
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 100L);
	// }
	// }
	// });
	// }


	// public void setCurrentFragmentToWeb(final String url)
	// {
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (isResumed())
	// {
	// mMenuBar.setCurrentMenu(0);
	// if (ProductDetailsFragment.instance != null)
	// ProductDetailsFragment.instance.onBackPressed();
	// if (getChildFragmentManager().getBackStackEntryCount() > 0)
	// {
	// getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// }
	// else
	// {
	// add(MainFragment.instance, MainFragment.instance.fragmentC1ID, new MainRecommendWebFragment(
	// url, 1), true);
	// }
	// }
	// else
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 100L);
	// }
	// }
	// });
	// }

	// public void setCurrentFragmentToProductDetail(final String goodsId, final String tag, final boolean isShop)
	// {
	// if (isShop)
	// {
	// mGoodsId = goodsId;
	// mGoodsTag = tag;
	// }
	// mGoodsIsShop = isShop;
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (isResumed())
	// {
	// if (mMenuBar.getCurrentMenuIdx() != 0)
	// {
	// mMenuBar.setCurrentMenu(0);
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// return;
	// }
	// if (ProductDetailsFragment.instance != null)
	// ProductDetailsFragment.instance.onBackPressed();
	// if (getChildFragmentManager().getBackStackEntryCount() > 0)
	// {
	// getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// }
	// else
	// {
	// add(MainFragment.instance, MainFragment.instance.fragmentC1ID, new ProductDetailsFragment(
	// mGoodsTag, mGoodsId, mGoodsIsShop), true);
	// }
	// }
	// else
	// AndroidUtils.MainHandler.postDelayed(this, 100L);
	// }
	// });
	// }

	// public void setCurrentFragmentToProductList(final String ccate, final boolean isShop)
	// {
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (isResumed())
	// {
	// mMenuBar.setCurrentMenu(0);
	// if (ProductDetailsFragment.instance != null)
	// ProductDetailsFragment.instance.onBackPressed();
	// if (getChildFragmentManager().getBackStackEntryCount() > 0)
	// {
	// getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// }
	// else
	// {
	// add(MainFragment.instance, MainFragment.instance.fragmentC1ID, new ProductListFragment(ccate,
	// isShop), true);
	// }
	// }
	// else
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 100L);
	// }
	// }
	// });
	// }

	public void backToHomepage() {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (isResumed()) {
					if (ProductDetailsFragment.instance != null)
						ProductDetailsFragment.instance.onBackPressed();
					setCurrentMenu(0);
					popBackStackInclusive();
				} else {
					AndroidUtils.MainHandler.postDelayed(this, 50L);
				}
			}
		});
	}

	public void addFragmentToMain(final Fragment fragment, final boolean backToRoot) {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (isResumed()) {
					if (mMenuBar.getCurrentMenuIdx() != 0) {
						setCurrentMenu(0);
						if (canPopBackStack()) {
							AndroidUtils.MainHandler.postDelayed(this, 300L);
						} else {
							AndroidUtils.MainHandler.post(this);
						}
						return;
					} else if (backToRoot && popBackStackInclusive()) {
						// if (ProductDetailsFragment.instance != null)
						// ProductDetailsFragment.instance.onBackPressed();
						AndroidUtils.MainHandler.postDelayed(this, 300L);
						return;
					}
					// if (fragment1.isResumed())
					// {
					add(instance, fragment, true);
					invalidateMenuBar();
					return;
					// }
				}
				AndroidUtils.MainHandler.postDelayed(this, 50L);
			}
		});
	}

	// public void addFragmentToOther(final Fragment fragment, final boolean backToRoot)
	// {
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (isResumed())
	// {
	// if (mMenuBar.getCurrentMenuIdx() != 5)
	// {
	// setCurrentMenu(5);
	// if (fragment2.canPopBackStack())
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// }
	// else
	// {
	// AndroidUtils.MainHandler.post(this);
	// }
	// return;
	// }
	// else if (backToRoot && fragment2.popBackStackInclusive())
	// {
	// if (ProductDetailsFragment.instance != null)
	// ProductDetailsFragment.instance.onBackPressed();
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// return;
	// }
	// if (fragment2.isResumed())
	// {
	// add(fragment2, fragment, true);
	// invalidateMenuBar();
	// return;
	// }
	// }
	// AndroidUtils.MainHandler.postDelayed(this, 50L);
	// }
	// });
	// }

	// public void addFragmentToSearch(final Fragment fragment, final boolean backToRoot)
	// {
	// AndroidUtils.MainHandler.post(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// if (isResumed())
	// {
	// if (mMenuBar.getCurrentMenuIdx() != 2)
	// {
	// setCurrentMenu(2);
	// if (fragment3.canPopBackStack())
	// {
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// }
	// else
	// {
	// AndroidUtils.MainHandler.post(this);
	// }
	// return;
	// }
	// else if (backToRoot && fragment3.popBackStackInclusive())
	// {
	// if (ProductDetailsFragment.instance != null)
	// ProductDetailsFragment.instance.onBackPressed();
	// AndroidUtils.MainHandler.postDelayed(this, 300L);
	// return;
	// }
	// if (fragment3.isResumed())
	// {
	// add(fragment3, fragment, true);
	// invalidateMenuBar();
	// return;
	// }
	// }
	// AndroidUtils.MainHandler.postDelayed(this, 50L);
	// }
	// });
	// }

	public void addFragmentToCurrent(final Fragment fragment, final boolean backToRoot) {
		// switch (mMenuBar.getCurrentMenuIdx())
		// {
		// case 0:
		// addFragmentToMain(fragment, backToRoot);
		// break;
		// case 2:
		// addFragmentToSearch(fragment, backToRoot);
		// break;
		// case 5:
		// addFragmentToOther(fragment, backToRoot);
		// break;
		// default:
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				if (isResumed()) {
					if (backToRoot && popBackStackInclusive()) {
						// if (ProductDetailsFragment.instance != null)
						// ProductDetailsFragment.instance.onBackPressed();
						AndroidUtils.MainHandler.postDelayed(this, 300L);
						return;
					}
					add(instance, fragment, true);
					invalidateMenuBar();
					return;
				}
				AndroidUtils.MainHandler.postDelayed(this, 50L);
			}
		});
		// break;
		// }
	}

	// public void addWebFragmentToMain(String url, boolean backToRoot)
	// {
	// addFragmentToMain(new MainWebFragment(url, 1), backToRoot);
	// }
	//
	// public void addWebFragmentToOther(String url, boolean backToRoot)
	// {
	// addFragmentToOther(new MainWebFragment(url, 1), backToRoot);
	// }
	//
	// public void addWebFragmentToSearch(String url, boolean backToRoot)
	// {
	// addFragmentToSearch(new MainWebFragment(url, 1), backToRoot);
	// }

	public void addWebFragmentToCurrent(String url, boolean backToRoot) {
		addFragmentToCurrent(new MainWebFragment(url, 1), backToRoot);
	}

	public void addProductDetailFragmentToMain(String goodsId, String tag, boolean isShop, boolean hasModelInfo,
											   boolean backToRoot) {
		if (isShop) {
			mGoodsId = goodsId;
			mGoodsTag = tag;
		}
		// mGoodsIsShop = isShop;
		addFragmentToMain(new ProductDetailsFragment(tag, goodsId, isShop, hasModelInfo), backToRoot);
	}

	// public void addProductDetailFragmentToOther(String goodsId, String tag, boolean isShop, boolean backToRoot)
	// {
	// if (isShop)
	// {
	// mGoodsId = goodsId;
	// mGoodsTag = tag;
	// }
	// // mGoodsIsShop = isShop;
	// addFragmentToOther(new ProductDetailsFragment(tag, goodsId, isShop), backToRoot);
	// }
	//
	// public void addProductDetailFragmentToSearch(String goodsId, String tag, boolean isShop, boolean backToRoot)
	// {
	// if (isShop)
	// {
	// mGoodsId = goodsId;
	// mGoodsTag = tag;
	// }
	// // mGoodsIsShop = isShop;
	// addFragmentToSearch(new ProductDetailsFragment(tag, goodsId, isShop), backToRoot);
	// }

	public void addProductDetailFragmentToCurrent(String goodsId, String tag, boolean isShop, boolean hasModelInfo,
												  float[] weightRange, int[] priceRange, boolean backToRoot) {
		if (isShop) {
			mGoodsId = goodsId;
			mGoodsTag = tag;
		}
		// mGoodsIsShop = isShop;
		addFragmentToCurrent(new ProductDetailsFragment(tag, goodsId, isShop, hasModelInfo, weightRange, priceRange),
				backToRoot);
	}
	public void addProductDetailFragmentToCurrent(String goodsId, String tag, boolean isShop, boolean hasModelInfo,
												  float[] weightRange, int[] priceRange, boolean backToRoot,String url) {
		if (isShop) {
			mGoodsId = goodsId;
			mGoodsTag = tag;
		}
		// mGoodsIsShop = isShop;
		addFragmentToCurrent(new ProductDetailsFragment(tag, goodsId, isShop, hasModelInfo, weightRange, priceRange,url),
				backToRoot);
	}



	public void addProductDetailFragmentToCurrent(String goodsId, String tag, boolean isShop, boolean hasModelInfo,
												  boolean backToRoot) {
		if (isShop) {
			mGoodsId = goodsId;
			mGoodsTag = tag;
		}
		// mGoodsIsShop = isShop;
		addFragmentToCurrent(new ProductDetailsFragment(tag, goodsId, isShop, hasModelInfo,0), backToRoot);
	}

	public void addProductDetailFragmentToCurrent(String goodsId, String tag, boolean isShop, boolean hasModelInfo,
												  boolean backToRoot,String url) {
		Log.d(TAG, "addProductDetailFragmentToCurrent: 执行  tag="+tag);
		if (isShop) {
			mGoodsId = goodsId;
			mGoodsTag = tag;
		}
		// mGoodsIsShop = isShop;
		addFragmentToCurrent(new ProductDetailsFragment(tag, goodsId, isShop, hasModelInfo,url), backToRoot);
		Log.d(TAG, "addProductDetailFragmentToCurrent: 执行完毕");
	}

	public void invalidateMenuBarOnce() {
		AndroidUtils.MainHandler.post(new Runnable() {
			@Override
			public void run() {
				menubar_layout.invalidate();
			}
		});
	}

	public void invalidateMenuBar() {
		invalidateMenuBar(20L, 20);
	}

	int invalidateCount;

	private void invalidateMenuBar(final long delayMillis, final int numOfInvalidate) {
		invalidateCount = 0;
		AndroidUtils.MainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				menubar_layout.invalidate();
				invalidateCount++;
				LogUtil.i(TAG, "invalidateMenuBar: " + invalidateCount);
				if (invalidateCount < numOfInvalidate) {
					AndroidUtils.MainHandler.postDelayed(this, delayMillis);
				}
			}
		}, delayMillis);
	}
}