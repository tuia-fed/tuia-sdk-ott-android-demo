# tuia-sdk-ott-android-demo
## 1. 准备  
### 1.1 合作方媒体在 推啊媒体平台 注册账号  
### 1.2 在后台获取 appKey、appSecret 等参数  
### 1.3 申请广告位ID  

## 2. SDK包导入  
### 2.1 项目的build.gradle文件中添加  
```
buildscript {
    repositories {
        google()
        //必须添加
        jcenter()
        //必须添加 
        maven { url "https://jitpack.io" }
        //选择添加（如果依赖导不下来可以添加这个仓库地址）
        maven { url "https://dl.bintray.com/sunjiangrong/maven" }
    }
    
dependencies {
    classpath 'com.android.tools.build:gradle:3.2.1'
    }
}

allprojects {
        repositories {
            google()
            //必须添加
            jcenter()
            //必须添加
            maven { url "https://jitpack.io" }
            //选择添加（如果依赖导不下来可以添加这个仓库地址）
            maven { url "https://dl.bintray.com/sunjiangrong/maven" }
        }
}
```
### 2.2 app下的build.gradle添加：(最小支持minSdkVersion 14)(重要)  
### 设置 gradle 编译选项，这块可以根据⾃己对平台的选择进行合理配置。  
```
android {  
    compileSdkVersion 28  
    defaultConfig {  
        //根据自己项目配置需要配置  
        ndk {  
            abiFilters 'armeabi-v7a','x86','arm64-v8a','x86_64','armeabi'  
        }  
    packagingOptions {  
        doNotStrip "*/armeabi-v7a/*.so"  
        doNotStrip "*/x86/*.so"  
        doNotStrip "*/arm64-v8a/*.so"  
        doNotStrip "*/x86_64/*.so"  
        doNotStrip "armeabi.so"  
    }  
}  
```
### 关于获取设备唯一标识符OAID操作(如果项目本身没有引用OAID的aar包):  
### 把 miit_mdid_1.0.13.aar 拷⻉到项目的 libs ⽬录，并设置依赖如下  
```
dependencies {  
    implementation ('com.tuia:sdk_ott:1.0.2.0'){  
    	transitive = true  
    }  
    implementation files('libs/miit_mdid_1.0.13.aar')  
}  
```
  
## 3 AndroidManifest配置  
### 3.1 权限  
### (sdk内部已经处理相关权限问题，如果遇到冲突咨询对应开发即可)  
```
<uses-permission android:name="android.permission.INTERNET"/>
```
### 3.2 设置meta-data
```
<!-- 推啊appkey和appSecret从媒体管理后台获取 咨询运营人员 记得替换成自己的-->
    <!-- 推啊appkey-->
    <meta-data
        android:name="TUIA_APPKEY"
        android:value="kEzAJT4iRMMag29Z7yWcJGfcVgG" />
    <!-- 推啊appSecret -->
    <meta-data
        android:name="TUIA_APPSECRET"
        android:value="3Wq4afvvdPhyHBR29LgRwEC16gkrrFXZ5BRoL2E" />
```
        
## 4 运行环境配置  
### 本SDK可运行于Android4.0(API Level 14) 及以上版本。  
```
<uses-sdk android:minSdkVersion="14" android:targetSdkVersion="26" />
```

## 5 混淆(混淆规则1.3.0.0及以上版本修改)  
### V1.3.0.0及以上版本  
```
-ignorewarnings
-dontwarn com.lechuan.midunovel.**
-keep class com.lechuan.midunovel.** { *; }
```

### V1.3.0.0以下版本
```
-ignorewarnings
-dontwarn com.lechuan.midunovel.view.**
-keep class com.lechuan.midunovel.view.** { *; }
```

## 6 初始化SDK
### 6.1 初始化(重要)
### 在自定义的Application 的onCreate方法中，调用以下方法：（详细内容请参考demo中的代码示例）
```
//基础SDK初始化
FoxSDK.init(this);
```

## 7 加载广告（选择合适的广告类型）
### 7.1 开屏广告接入
### （开屏广告嵌入代码说明（详见demo SplashOTTActivity代码））
### 第一步：xml中引入布局
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/fl_splash_container">
</FrameLayout>
```
### 第二步：代码引入实例
```
public class SplashOTTActivity extends AppCompatActivity implements FoxNativeSplashHolder.LoadSplashAdListener {

    private FrameLayout flContainer;

    private FoxNativeSplashHolder foxNativeSplashHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ott);

        flContainer = findViewById(R.id.fl_splash_container);

        // 创建 开屏广告加载holder
        foxNativeSplashHolder = FoxNativeAdHelper.getNativeSplashHolder();

        int slotId = getIntent().getIntExtra("slotId", 0);
        // 加载广告素材
        foxNativeSplashHolder.loadSplashAd(slotId, TAConfig.USER_ID, this);
    }

    @Override
    protected void onDestroy() {
        if (foxNativeSplashHolder != null){
            foxNativeSplashHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Time out => " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSplashAdSuccess(FoxSplashAd foxSplashAd) {
        // 对请求成功的广告进行设置
        View view = null;
        if (foxSplashAd != null){
            foxSplashAd.setScaleType(ImageView.ScaleType.FIT_XY);
            view = foxSplashAd.getView();
        }

        if (view != null && !SplashOTTActivity.this.isFinishing()){
            flContainer.removeAllViews();
            flContainer.addView(view);
        }else {
            finish();
        }
    }

    @Override
    public void onTimeOver() {
        Toast.makeText(this, "Time over => " + getString(R.string.toast_time_over), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoadSuccess() {
        Toast.makeText(this, "Load success => " + getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestAdFailed(int i, String s) {
        Toast.makeText(this, "Request failed => " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(this, "Load failed => " + getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSkipClick() {
        Toast.makeText(this, "Skip click => " + getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onAdClick() {
        Toast.makeText(this, "Ad click => " + getString(R.string.toast_click), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdExposure() {
        Toast.makeText(this, "Ad exposure => " + getString(R.string.toast_exposure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdActivityClose(String s) {
        finish();
    }
}
```
### 7.2 横幅广告接入
### （横幅广告嵌入代码说明（详见demo CrossBannerOTTActivity代码））
### 第一步：xml中引入布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:clipChildren="false"
    android:id="@+id/ll_cross_banner_container">

    <com.mediamain.android.view.FoxStreamerView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/sv_cross_banner"
        android:layout_margin="10dp"/>
</LinearLayout>
```
### 第二步：代码引入示例
```
public class CrossBannerOTTActivity extends AppCompatActivity {

    private LinearLayout llContainer;
    private FoxStreamerView foxStreamerView1;
    private FoxStreamerView foxStreamerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_banner_ott);

        llContainer = findViewById(R.id.ll_cross_banner_container);
        foxStreamerView1 = findViewById(R.id.sv_cross_banner);
        initStreamView();

        int slotId = getIntent().getIntExtra("slotId", 0);
        foxStreamerView1.loadAd(slotId);
        foxStreamerView2.loadAd(slotId, TAConfig.USER_ID);
    }

    private void initStreamView(){
        if (foxStreamerView1 == null){
            return;
        }

        // 布局中添加广告
        foxStreamerView1.setAdListener(new FoxListener() {
            @Override
            public void onReceiveAd() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 Receive ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 receive failed, message=" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadFailed() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 load failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCloseClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 click close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 click ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdExposure() {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 ad exposure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdActivityClose(String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view1 activity close", Toast.LENGTH_SHORT).show();
            }
        });

        // 动态添加广告
        foxStreamerView2 = new FoxStreamerView(this, null);
        foxStreamerView2.setFoxWidth(1000);
        foxStreamerView2.setFoxHeight(500);
        llContainer.addView(foxStreamerView2);

        foxStreamerView2.setAdListener(new FoxListener() {
            @Override
            public void onReceiveAd() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 Receive ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 receive failed, message=" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadFailed() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 load failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCloseClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 click close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 click ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdExposure() {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 ad exposure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdActivityClose(String s) {
                Toast.makeText(CrossBannerOTTActivity.this, "view2 activity close", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 及时销毁控件
        if (foxStreamerView1 != null){
            foxStreamerView1.destroy();
        }
        if (foxStreamerView2 != null){
            foxStreamerView2.destroy();
        }

        super.onDestroy();
    }
}
```
### 7.3 浮标/icon广告接入
### （浮标广告嵌入代码说明（详见demo FloatingOTTActivity代码））
### 第一步：xml中引入布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:clipChildren="false"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/ll_floating_container">

    <com.mediamain.android.view.FoxWallView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:fox_layout_height="150"
        app:fox_layout_width="150"
        android:layout_margin="10dp"
        app:wall_shape="circular"
        android:id="@+id/wv_floating_circle"/>
</LinearLayout >
```
### 第二步：代码引入示例
```
public class FloatingOTTActivity extends AppCompatActivity {

    private LinearLayout llContainer;
    private FoxWallView wvCircle;
    private FoxWallView wvSquare;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_ott);

        llContainer = findViewById(R.id.ll_floating_container);
        wvCircle = findViewById(R.id.wv_floating_circle);

        slotId = getIntent().getIntExtra("slotId", 0);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (wvCircle != null){
            wvCircle.destroy();
        }

        if (wvSquare != null){
            wvSquare.destroy();
        }
    }

    private void initView() {
        // 动态添加广告
        wvSquare = new FoxWallView(this, 0);
        wvSquare.setFoxWidth(200);
        wvSquare.setFoxHeight(200);
        llContainer.addView(wvSquare);

        // 加载广告id
        wvCircle.loadAd(slotId);
        wvSquare.loadAd(slotId, TAConfig.USER_ID);
    }
}
```
### 7.4 弹窗广告接入
### （普通插屏广告嵌入代码说明（详见demo InsertOTTActivity代码））
### 第一步：xml中引入布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:padding="10dp"
        android:textSize="24dp"
        android:text="弹出广告"
        android:id="@+id/bt_insert"/>
</RelativeLayout>
```
### 第二步：代码直接引入示例
```
public class InsertOTTActivity extends AppCompatActivity {

    private Button btn;
    private FoxTbScreen tbScreen;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ott);
        btn = findViewById(R.id.bt_insert);

        slotId = getIntent().getIntExtra("slotId", 0);

        initListener();

        // 初始化焦点位置
        btn.requestFocus();
    }

    private void initListener(){
        btn.setOnClickListener(v -> {
            // 弹窗广告绑定当前activity
            tbScreen = new FoxTbScreen(InsertOTTActivity.this);
            // 设置按返回键可关闭弹窗广告（option）
            tbScreen.setBackEnable(true);
            // 加载广告id
            tbScreen.loadAd(slotId, TAConfig.USER_ID);
        });

        btn.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                TADemoAnimationUtil.enlarge(v, 1.2F, 300);
            }else {
                TADemoAnimationUtil.shrink(v, 1.2F, 300);
            }
        });
    }
}
```
### 7.5 Banner广告接入
### （Banner广告嵌入代码说明（详见demo BannerOTTActivity代码））
### 第一步：xml中引入布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:clipChildren="false"
    android:orientation="vertical">

    <com.mediamain.android.view.FoxWallView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:focusable="true"
        android:id="@+id/wv_banner"/>
</LinearLayout>
```
### 第二步：代码引入示例
```
public class BannerOTTActivity extends AppCompatActivity {

    private FoxWallView wv;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ott);

        slotId = getIntent().getIntExtra("slotId", 0);

        wv = findViewById(R.id.wv_banner);

        // 加载广告id
        wv.loadAd(slotId);
        // 初始化焦点位置
        wv.requestFocus();
    }

    @Override
    protected void onDestroy() {
        if (wv != null){
            wv.destroy();
        }

        super.onDestroy();
    }
}
```
### 7.6 自定义广告接入（支持tab页或其他特殊场景）
### （自定义广告嵌入代码说明（详见demo CustomOTTActivity代码））
### ** 注意：自定义广告需要单独调用 广告曝光 和 广告点击 事件**
### 第一步：xml中引入布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="jump"
        android:textColor="@color/white"
        android:textSize="24sp"
        android:layout_centerInParent="true"
        android:id="@+id/bt_custom"/>
</RelativeLayout>
```
### 第二步：代码直接引入示例
```
public class CustomOTTActivity extends AppCompatActivity {

    private Button bt;

    private FoxCustomerTm customerTm;

    private int slotId;
    private String url = "baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ott);
        bt = findViewById(R.id.bt_custom);

        customerTm = new FoxCustomerTm(this);
        initListener();

        slotId = getIntent().getIntExtra("slotId", 0);
        bt.requestFocus();
    }

    private void initListener(){
        customerTm.setAdListener(new FoxNsTmListener() {
            @Override
            public void onReceiveAd(String s) {
                // 素材曝光调用
                customerTm.adExposed();
                Toast.makeText(CustomOTTActivity.this, CustomOTTActivity.this.getResources().getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(CustomOTTActivity.this, CustomOTTActivity.this.getResources().getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdActivityClose(String s) {
                Toast.makeText(CustomOTTActivity.this, CustomOTTActivity.this.getResources().getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
            }
        });

        bt.setOnClickListener(v -> {
            // 素材点击调用
            customerTm.adClicked();

            if (slotId > 0){
                // 加载广告id
                customerTm.loadAd(slotId);
            }else {
                // 打开自定义活动页
                customerTm.openFoxActivity(url);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (customerTm != null){
            customerTm.destroy();
        }

        super.onDestroy();
    }
}
```
### 第三步：广告曝光的时候必须调用
```
customerTm.adExposed();
```
### 第四步：广告点击的时候必须调用
```
customerTm.adClicked();
```
### 第五步：关于自定义广告素材活动加载
```
onReceiveAd(String result) ——> result数据结构
{
    "activityUrl" : "活动链接",
    "imageUrl" : "素材图片链接",
    "reportClickUrl" : "素材图片点击曝光链接",
    "reportExposureUrl" : "素材图片加载成功曝光链接",
    "extTitle" : "标题",
    "extDesc" : "描述",
}
```
### 使用方式(推荐使用)：
#### 活动加载交给SDK处理：
##### 1.素材展示媒体自己加载并在加载成功时调用素材曝光mOxCustomerTm.adExposed()，
##### 2.素材点击请调用mOxCustomerTm.adClicked()，同时传入返回的活动链接url调用
```
if(mOxCustomerTm!=null && mDataBean!=null && !TextUtil.isEmpty(mDataBean.getActivityUrl())){
    // mDataBean通过result转换而来
    mOxCustomerTm.openFoxActivity(mDataBean.getActivityUrl());
}
```
### 7.7 原生插屏接入（支持滚金蛋、大富翁等活动玩法）
### (原生插屏广告嵌入代码说明(详见demo NativeInsertOTT1Activity代码))
### 关于AndroidX开发的项目:支持包请使用androidx.appcompat:appcompat:1.2.0-alpha02
### 原因:https://stackoverflow.com/questions/41025200/android-view-inflateexception-error-inflating-class-android-webkit-webview?answertab=active#tab-top
### 第一步：代码直接引入示例
```
public class NativeInsertOTT1Activity extends AppCompatActivity {

    private Ad ad;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_insert_ott1);

        slotId = getIntent().getIntExtra("slotId", 0);
        initAd();
    }

    private void initAd(){
        ad = new Ad(slotId + "", TAConfig.USER_ID, "");
        ad.init(this, null, Ad.AD_URL_NEW, new AdCallBack() {
            @Override
            public void onReceiveAd() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(int i, String s) {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onActivityClose() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onActivityShow() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_exposure), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardClose() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_reward_close), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardShow() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_reward_show), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrizeClose() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_prize_close), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrizeShow() {
                Toast.makeText(NativeInsertOTT1Activity.this,
                        NativeInsertOTT1Activity.this.getResources().getString(R.string.toast_prize_show), Toast.LENGTH_SHORT).show();
            }
        });
        ad.loadAd(this, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsume = false;
        if (ad != null){
            // 获取广告是否允许返回键退出
            isConsume = ad.onKeyBack(keyCode, event);
        }

        if (!isConsume){
            return super.onKeyDown(keyCode, event);
        }
        return isConsume;
    }

    @Override
    protected void onDestroy() {
        if (ad != null){
            ad.destroy();
        }

        super.onDestroy();
    }
}
```
### AdCallBack的注释如下
```
public interface AdCallBack {
    /**
    * 活动弹窗关闭回调 只针对弹窗类型广告有效
    */
    void onReceiveAd()
    /**
    * 活动弹窗关闭回调 只针对弹窗类型广告有效
    */
    void onFailedToReceiveAd()
    /**
    * 活动弹窗关闭回调 只针对弹窗类型广告有效
    */
    void onActivityClose();

    /**
    * 活动弹窗show回调 只针对弹窗类型广告有效
    */
    void onActivityShow();

    /**
    * 奖励弹窗关闭回调
    */
    void onRewardClose();

    /**
    * 奖励弹窗show回调
    */
    void onRewardShow();

    /**
    * 我的奖品页弹窗关闭回调
    */
    void onPrizeClose();

    /**
    * 我的奖品页弹窗show回调
    */
    void onPrizeShow();
}
```
### AdCallBack的默认实现为DefaultAdCallBack，已在sdk中实现可以直接调用。
|参数名|必填|类型|默认值|描述|
|-|-|-|-|-|
|appKey|是|string|000000|系统分配的加密字段|
|slotId|是|string（可空）|000000|广告位id(需根据该id后台配置活动)|
|userId|否|string|deviceId|媒体端对接虚拟奖品是需要设置,禁止以固定值|
|deviceId|否|string|UUID.randomUUID|用户唯一身份标识|
|activity|是|Activity|展示互动广告的activity|
|viewGroup|否|FrameLayout|null|展示互动广告的viewGroup,viewGroup为空的情况下即为插屏广告否则即为嵌入式广告|
|AdCallBack|否|AdCallBack|null|null|
### 第二步: 设置soltId (不是必须的 slotId可以在new Ad()时初始化，resetSlotId()方法的作用主要是方便某些媒体需要根据后端接口动态配置广告位Id)
```
if(ad!=null){
    ad.resetSlotId(slotId)
}
```
### 第三步: 点击某个按钮展示广告或者进入页面直接展现广告
```
if(ad!=null){
    ad.loadAd(activity, false);
}
```
### 接口说明
#### 初始化Ad对象，slotId申请广告位id 为必填项
#### 假如初始化slotId为空，必须在展现广告前通过resetSlotId方法设置广告位id
#### userId当用户需要对接虚拟奖品并且有用户体系时必传(发奖的唯一标识符)
#### useLoading 可以控制loading样式展示  Ad.AD_NEW_LOADING_HIDE和 Ad.AD_NEW_LOADING_SHOw
#### 其中viewGroup可以为空，假如viewGroup为空则sdk默认展现dialog类型广告，否则展现嵌入在你指定布局文件中的嵌入式广告。AdCallBack则为弹窗的行为回调包括show和dismiss
```
new Ad(appKey,slotId) 
new Ad(appKey,slotId,userId) 
new Ad(appKey,slotId,userId,deviceId)
new Ad(appKey,slotId,userId,deviceId,useLoading)
```
### appKey和slotId请联系推啊对接人员获取。
```
if(ad!=null){
    ad.init(activity,viewGroup, Ad.AD_URL_NEW,AdCallBack)
}
```
### show 展示互动广告
```
if(ad!=null){
    ad.show();
}
```
### hide 隐藏互动广告 只针对插屏广告有效
```
if(ad!=null){
    ad.hide();
}
```
### 弹窗广告的返回拦截
```
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    boolean isConsume = false;
    if (ad != null) {
        isConsume = ad.onKeyBack(keyCode, event);
    }
    if (!isConsume) {
        return super.onKeyDown(keyCode, event);
    }
    return isConsume;
}
```
### 广告的资源回收
#### 建议在activity销毁时进行资源回收 否则有可能会造成内存泄漏
```
@Override
protected void onDestroy() {
    super.onDestroy();
    if(ad!=null){
      ad.destroy();  
    }
}
```
### 7.8 信息流接入
### （信息流广告嵌入代码说明（详见demo InfoStreamOTTActivity））
### 第一步：xml中引入布局
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:clipChildren="false">

    <ListView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:clipChildren="false"
        android:id="@+id/lv_info_stream"/>
</RelativeLayout>
```
### 第二步：代码引入示例
```
public class InfoStreamOTTActivity extends AppCompatActivity implements FoxTempletInfoFeedHolder.LoadInfoAdListener {

    private ListView lv;

    private FoxTempletInfoFeedHolder foxTempletInfoFeedHolder;
    private AdListAdapter adapter;

    private List<DemoBean> dataList = new ArrayList<>();
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_stream_ott);
        lv = findViewById(R.id.lv_info_stream);

        slotId = getIntent().getIntExtra("slotId", 0);
        // 创建信息流holder
        foxTempletInfoFeedHolder = FoxNativeAdHelper.getFoxTempletInfoFeedHolder();

        initView();
    }

    private void initView() {
        adapter = new AdListAdapter();

        lv.setAdapter(adapter);
        // 加载广告id
        foxTempletInfoFeedHolder.loadInfoAd(slotId, this);
        lv.requestFocus();
    }

    @Override
    protected void onDestroy() {
        // 销毁holder
        if (foxTempletInfoFeedHolder != null){
            foxTempletInfoFeedHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "error = " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void infoAdSuccess(List<IFoxTempletInfoFeedAd> list) {
        if (list == null || list.size() == 0 || adapter == null){
            return;
        }

        // 插入广告到listview中
        dataList.add(new DemoBean(null));
        for (IFoxTempletInfoFeedAd ad : list){
            // 对信息流广告属性设置
            ad.setImageSize(200, 200);
            ad.setTextColor(R.color.yellow);
            ad.setTextSize(20);
            dataList.add(new DemoBean(ad));
        }
        dataList.add(new DemoBean(null));

        adapter.setDataList(dataList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReceiveAd() {
        Toast.makeText(this, getString(R.string.toast_receive_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedToReceiveAd(int i, String s) {
        Toast.makeText(this, getString(R.string.toast_receive_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(this, getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCloseClick() {
        Toast.makeText(this, getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClick() {
        Toast.makeText(this, getString(R.string.toast_click), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdExposure() {
        Toast.makeText(this, getString(R.string.toast_exposure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdActivityClose(String s) {
        Toast.makeText(this, getString(R.string.toast_time_over), Toast.LENGTH_SHORT).show();
    }
}
```
### 7.9 文字链接入
### （信息流广告嵌入代码说明（详见demo TempletFeedListActivity））
### 第一步：xml中引入布局
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/rl_text_link_container">
</RelativeLayout>
```
### 第二步：代码引入示例
```
public class TextLinkOTTActivity extends AppCompatActivity implements FoxTextLinkHolder.LoadInfoAdListener {

    private RelativeLayout rlContainer;

    private FoxTextLinkHolder foxTextLinkHolder;

    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_link_ott);
        rlContainer = findViewById(R.id.rl_text_link_container);

        slotId = getIntent().getIntExtra("slotId", 0);
        initView();
    }

    private void initView(){
        // 创建文字链holder
        foxTextLinkHolder = FoxNativeAdHelper.getFoxTextLinkHolder();
        foxTextLinkHolder.loadInfoAd(this, slotId, TAConfig.USER_ID, this);
    }

    @Override
    protected void onDestroy() {
        // 销毁holder
        if (foxTextLinkHolder != null) {
            foxTextLinkHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "error = " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void infoAdSuccess(FoxTextLintAd foxTextLintAd) {
        if (foxTextLintAd == null || foxTextLintAd.getView() == null){
            return;
        }

        if (isFinishing()){
            return;
        }

        // 对holder里对文字属性设置
        foxTextLintAd.setDescSize(26);
        foxTextLintAd.setTitleSize(26);
        foxTextLintAd.setDescColor(R.color.black);
        foxTextLintAd.setTitleColor(R.color.black);
        foxTextLintAd.setDescColor(getResources().getColor(R.color.yellow));
        // 动态添加文字链holder到container
        rlContainer.removeAllViews();
        rlContainer.addView(foxTextLintAd.getView());

        foxTextLintAd.getView().requestFocus();
    }

    @Override
    public void onReceiveAd() {
        Toast.makeText(this, getString(R.string.toast_receive_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedToReceiveAd(int i, String s) {
        Toast.makeText(this, getString(R.string.toast_receive_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(this, getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCloseClick() {
        Toast.makeText(this, getString(R.string.toast_click_skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClick() {
        Toast.makeText(this, getString(R.string.toast_click), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdExposure() {
        Toast.makeText(this, getString(R.string.toast_exposure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdActivityClose(String s) {
        Toast.makeText(this, getString(R.string.toast_time_over), Toast.LENGTH_SHORT).show();
    }
}
```
### 7.10 悬浮升级
```
public class AnimationFloatingOTTActivity extends AppCompatActivity implements FoxFloatingWebHolder.FloatingWebAdLoadListener {

    private int slotId;

    private FoxFloatingWebHolder foxFloatingWebHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_floating_ott);

        slotId = getIntent().getIntExtra("slotId", 0);

        initView();
    }

    private void initView() {
        // 创建悬浮升级holder
        foxFloatingWebHolder = FoxNativeAdHelper.getFoxFloatingWebHolder();
        // holder绑定当前activity
        foxFloatingWebHolder.setFloatingHost(this);
        // 设置holder信息
        foxFloatingWebHolder.setConfigInfo(TAConfig.APP_KEY, TAConfig.APP_SECRET);

        // 设置holder属性
        String left = "50";
        String right = "100";
        String top = "100";
        String bottom = "50";

        // holder加载广告id
        foxFloatingWebHolder.loadFloatingWebAd(slotId, TAConfig.USER_ID, left, top, right, bottom, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsumed = false;
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (foxFloatingWebHolder != null) {
                // 获取holder是否允许返回键退出
                isConsumed = foxFloatingWebHolder.goBack();
            }
        }
        if (!isConsumed) {
            return super.onKeyDown(keyCode, event);
        }

        return isConsumed;
    }

    @Override
    protected void onDestroy() {
        // 销毁holder
        if (foxFloatingWebHolder != null){
            foxFloatingWebHolder.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onLoadSuccess() {
        Toast.makeText(this, getString(R.string.toast_load_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed(int i, String s) {
        Toast.makeText(this, getString(R.string.toast_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdMessage(String s) {
        Toast.makeText(this, "ad msg = " + s, Toast.LENGTH_SHORT).show();
    }
}
```
