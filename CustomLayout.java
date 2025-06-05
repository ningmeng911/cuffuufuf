package com.script.tone;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.provider.SimPhonebookContract;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.view.Choreographer;
import android.view.WindowManager;
import androidx.core.util.Supplier;
import java.util.function.Consumer;
import java.util.Map;
import java.util.HashMap;
import android.graphics.Paint;
import android.view.ViewTreeObserver;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import android.graphics.PorterDuff;
import android.widget.RelativeLayout;
import com.script.tone.parameters;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import android.util.DisplayMetrics;
import java.math.BigDecimal;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;


public class CustomLayout extends LinearLayout {

    private LinearLayout verifyLayout; // 验证界面布局
    private EditText cardPasswordInput; // 卡密输入框
    private Button verifyButton; // 验证按钮
    private Button confirmButton;
    private LinearLayout announcementLayout; // 验证界面布局
    private LinearLayout updateLayout; // 验证界面布局
    private TextView updateStatusTextView;
    private LinearLayout topLayout; // 验证界面布局
    private Button 购卡按钮; // 购卡按钮 
    static Float dragAngle; // 用于存储首次获取的角度
    private static boolean 按钮移动 = false; // 控制是否允许移动
    private final Handler handler = new Handler(Looper.getMainLooper());
    
    private FrameLayout contentFrame;
    private TextView gameSettings, skinSettings, operationSettings, beautifySettings, rankingSettings, advancedSettings;
    private parameters parameters; // 假设您有一个参数类用于传递参数
    private SparseArray<LinearLayout> pageLayouts = new SparseArray<>(); // 用于存储页面布局
    private static FrameLayout rootView;
    private static RoundButtonCreator buttonCreator;
    private static Button 吐球, 三角, 蛇手, 侧合, 后仰, 旋转, 四分, 同步, 分身, 卡点, 冲球, 内存卡点, 旋转卡点, 蛇手卡点, 追踪三角, 同步三角, 追踪大炮, 同步大炮, 同步蛇手;
    private static Runnable 吐球任务;
    private static int savedInitialX = -1; // 按钮初始X位置，默认-1表示未初始化
    private static int savedInitialY = -1; // 按钮初始Y位置，默认-1表示未初始化
    private static parameters cs;
    private static Memory memory;
    private static SimpleButtonController simpleButtonController;
    public static float joystickUp,joystickDown,joystickAngle;
    private Float initialAngle = null; // 用于存储首次获取的角度  
    private boolean isPressing = false; // 按钮是否被按下的标志
    private Thread pressThread; // 按下的线程
    private boolean isRunning = false;
    private long a1;
    private int fps;
    private long a2;
    private long a3;
    private long a4;
    private long a5;
    private long Incomingfive;
    private int previousValue = 0;
    private float 视图比例;
    private long aa;
    private boolean w1=true;
    private int 吐球id=6;
    private int 触摸id=7;
    private int 点击id=8;
    private DrawView drawView;//绘图类
    private long 数组;
    private long 数组地址 = 0; // 将数组地址变为类成员变量
    private List<String> 玩家信息列表 = new ArrayList<>();//玩家信息
    private Handler mainHandler = new Handler(Looper.getMainLooper()); // 主线程 Handler
    
    private float moveX, moveY, downX, downY;
    private int signX, signTop;
    
    public static long[] Incomingone = new long[1];
    
    // private final CopyOnWriteArrayList<String> 玩家信息列表 = new CopyOnWriteArrayList<>();

    public CustomLayout(Context context, parameters parameters, FrameLayout rootView) {
        super(context);
        this.parameters = parameters;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(dpToPx(371), dpToPx(257)));
        parameters = new parameters();
        buttonCreator = new RoundButtonCreator(context, rootView);
        memory = new Memory();
        simpleButtonController = new SimpleButtonController(context, rootView);
        simpleButtonController.updateButtonVisibility(false);
                
        // 在 CustomLayout 构造函数中初始化 drawView
    this.drawView = new DrawView(context);

        // 设置 DrawView 的布局参数
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
    this.drawView.setLayoutParams(params);

// 添加 DrawView 到根视图c
    rootView.addView(this.drawView);
    drawView.setZ(10);
    
    
//    
//    new Thread(() -> {
//    try {
//        Thread.sleep(00); 
//
//        while (true) {
//            try {
//                // 设置包名
//                memory.setPackageName("com.ztgame.bob");
//
//                // 获取模块地址
//                long moduleAddress = memory.getModuleAddress("libunity.so");
//
//                // 计算目标地址
//                long wq1 = moduleAddress + 0x747A98;
//
//                // 设置内存值
//                memory.setValue("16384", wq1, memory.TYPE_DWORD);
//
//                Thread.sleep(1);
//            } catch (Exception e) {
//                e.printStackTrace(); // 打印异常，防止线程崩溃
//            }
//        }
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//}).start();



  //  monitorRootViewDrawFrequency(rootView);
// 初始化文件写入工具类
    PeriodicFileWriter fileWriter = new PeriodicFileWriter();

        // new Handler(Looper.getMainLooper()).postDelayed(() -> {
        //    try {
        //        // 设置文件路径（外置私有存储目录下的文件）
        //        String filePath =
        // "/storage/emulated/0/Android/data/com.ztgame.bob/files/unsafeconfig.unity3d_u_ea8c45dfb1d9954bdf55704e9aa0e339";
        //        fileWriter.initialize(context, filePath);
        //
        //        // 启动每隔 10 秒写入一次的任务
        //        fileWriter.startWriting(10_00); // 每 10 秒写入一次
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        // }, 10_000); // 延迟 10 秒
        //

        // 停止任务（例如在 Activity 的 onDestroy 中）
        // fileWriter.stopWriting();

        // 设置整体背景色和圆角为紫色主题
        GradientDrawable background = new GradientDrawable();
background.setColor(Color.parseColor("#99ffb9ee"));  // 80 = 半透明（~50%透明度）
background.setCornerRadius(dpToPx(16));
setBackground(background);

Integer readValue = FileUtil.读取(context, "RankId.txt", Integer.class);
if (readValue != null) {
    specifiedRankId = readValue;
} else {
    specifiedRankId = 10; // 或其他
}
Integer MyRankId1 = FileUtil.读取(context, "MyRankId.txt", Integer.class);
if (MyRankId1 != null) {
    myRankId = MyRankId1;
} else {
    myRankId = 10; // 或其他
}


        
       initializeUpdateUI(context);
       绘制玩家线段(context);
        
        //初始化验证界面
        //initializeVerificationUI(context);
      // initializeMainContent(context); // 初始化主内容
    }

    private void initializeUpdateUI(Context context) {
    // 创建更新界面布局
    updateLayout = new LinearLayout(context);
    updateLayout.setOrientation(VERTICAL);
    updateLayout.setGravity(Gravity.CENTER);
    LayoutParams layoutParams = new LayoutParams(dpToPx(350), dpToPx(200));
    updateLayout.setLayoutParams(layoutParams);

    // 设置更新界面背景
    GradientDrawable background = new GradientDrawable();
    background.setColor(Color.parseColor("#00ffb9ee")); // 半透明深紫色背景
    background.setCornerRadius(dpToPx(16));
    updateLayout.setBackground(background);

    // 创建状态文本视图
    updateStatusTextView = new TextView(context);
    LayoutParams textParams = new LayoutParams(dpToPx(300), LayoutParams.WRAP_CONTENT);
    textParams.setMargins(0, dpToPx(20), 0, dpToPx(10));
    updateStatusTextView.setLayoutParams(textParams);
    updateStatusTextView.setText("正在检查更新...");
    updateStatusTextView.setTextColor(Color.parseColor("#ff000000")); // 设置为金黄色字体
    updateStatusTextView.setTextSize(16);
    updateStatusTextView.setGravity(Gravity.CENTER);

    // 创建确认按钮
    confirmButton = new Button(context);
    LayoutParams buttonParams = new LayoutParams(dpToPx(280), dpToPx(50));
    buttonParams.setMargins(0, dpToPx(10), 0, 0);
    confirmButton.setLayoutParams(buttonParams);
    confirmButton.setText("朕知道了");
    confirmButton.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#80a88bff"));
        setCornerRadius(dpToPx(10));
    }});
    confirmButton.setTextColor(Color.WHITE);
    confirmButton.setTextSize(16);
    confirmButton.setVisibility(View.GONE); // 初始隐藏

    // 设置确认按钮点击事件
    confirmButton.setOnClickListener(v -> {
    // 如果版本过期，可根据需求处理，例如退出程序
    removeView(updateLayout);

    // 使用系统浏览器打开网页
    String url = "https://ninglemon.flowus.cn";  // 替换为你要打开的网页
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

    // 直接使用传入的 context，而不是重新声明
    context.startActivity(browserIntent);
});




    // 将状态文本和按钮添加到更新界面
    updateLayout.addView(updateStatusTextView);
    updateLayout.addView(confirmButton);

    // 将更新界面添加到根布局
    addView(updateLayout);

    // 检查更新
    wy.更新(context, new wy.VersionCallback() {
        @Override
        public void onVersionReceived(String version) {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (version.equals(wy.当前版本号)) {
                    // 版本一致，移除更新布局并延时初始化新的布局
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        removeView(updateLayout);
                        initializeAnnouncementUI(context);
                    }, 500); // 延时 2 秒
                } else {
                    // 版本不一致，显示版本已过期
                    updateStatusTextView.setText("版本已过期，请更新到最新版本！");
                    updateStatusTextView.setTextColor(Color.RED); // 将字体颜色改为红色
                    confirmButton.setVisibility(View.VISIBLE); // 显示确认按钮
                }
            });
        }

        @Override
        public void onError(Exception e) {
            new Handler(Looper.getMainLooper()).post(() -> {
                // 更新检测失败，显示错误信息
                updateStatusTextView.setText("更新检测失败: " + e.getMessage());
                updateStatusTextView.setTextColor(Color.RED); // 将字体颜色改为红色
                confirmButton.setVisibility(View.VISIBLE); // 显示确认按钮
            });
        }
    });
}

    // 初始化验证界面
    private void initializeVerificationUI(Context context) {
    verifyLayout = new LinearLayout(context);
    verifyLayout.setOrientation(VERTICAL);
    verifyLayout.setGravity(Gravity.CENTER);
    LayoutParams verifyLayoutParams = new LayoutParams(dpToPx(350), dpToPx(300));
    verifyLayout.setLayoutParams(verifyLayoutParams);

    // 设置验证界面背景
   // 设置验证界面背景
GradientDrawable verifyBackground = new GradientDrawable();
verifyBackground.setColor(Color.parseColor("#00ffb9ee")); // 半透明深紫色背景
verifyBackground.setCornerRadius(dpToPx(16));
verifyLayout.setBackground(verifyBackground);

TextView yzText = new TextView(context);
yzText.setId(View.generateViewId());
yzText.setText("蓝莓 验证");
yzText.setTextSize(18);
yzText.setGravity(Gravity.CENTER);

// 创建水平渐变效果
ValueAnimator gradientAnimator = ValueAnimator.ofFloat(0f, 1f);
gradientAnimator.setDuration(3000); // 3秒完成一轮
gradientAnimator.setRepeatCount(ValueAnimator.INFINITE); // 无限循环
gradientAnimator.setInterpolator(new LinearInterpolator()); // 线性插值

gradientAnimator.addUpdateListener(animator -> {
    float fraction = animator.getAnimatedFraction();
    
    // 创建水平渐变颜色
    int[] gradientColors = {
        Color.HSVToColor(new float[]{(fraction * 360f) % 360, 1f, 1f}), // 动态变化的主色
        Color.HSVToColor(new float[]{(fraction * 360f + 120f) % 360, 1f, 1f}), // 主色+120度的补色
        Color.HSVToColor(new float[]{(fraction * 360f + 240f) % 360, 1f, 1f})  // 主色+240度的补色
    };
    
    // 创建渐变drawable
    GradientDrawable textGradient = new GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        gradientColors
    );
    textGradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    textGradient.setBounds(0, 0, yzText.getWidth(), yzText.getHeight());
    
    // 创建着色器
    TextPaint paint = yzText.getPaint();
    Shader textShader = new LinearGradient(
        0, 0, yzText.getWidth(), 0,
        gradientColors, null, Shader.TileMode.CLAMP
    );
    paint.setShader(textShader);
    
    yzText.invalidate(); // 重绘TextView
});

// 添加全局布局监听器，确保在视图布局完成后获取正确的宽度
yzText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
        yzText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        gradientAnimator.start();
    }
});
        //渐变颜色文本结尾
        // 设置布局参数
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(5), 0, 0);  // 设置上间距为 15dp
        yzText.setLayoutParams(layoutParams);

    // 创建卡密输入框
    cardPasswordInput = new EditText(context);
    LayoutParams cardParams = new LayoutParams(dpToPx(280), dpToPx(50));
    cardParams.setMargins(0, dpToPx(20), 0, dpToPx(10));
    cardPasswordInput.setLayoutParams(cardParams);
    cardPasswordInput.setHint("请输入卡密");
    cardPasswordInput.setTextColor(Color.WHITE);
    cardPasswordInput.setHintTextColor(Color.parseColor("#80000000"));
    cardPasswordInput.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#80000000"));
        setCornerRadius(dpToPx(10));
    }});
    cardPasswordInput.setGravity(Gravity.CENTER);

    // 自动读取私有目录中的卡密并填充到输入框
    String savedCardPassword = FileUtil.读取私有目录(context, "km"); // 私有文件读取方法
    if (savedCardPassword != null && !savedCardPassword.isEmpty()) {
        cardPasswordInput.setText(savedCardPassword);
        // 自动尝试登录
        attemptLogin(savedCardPassword, context);
    }

    // 创建验证按钮
    verifyButton = new Button(context);
    LayoutParams buttonParams = new LayoutParams(dpToPx(280), dpToPx(50));
    buttonParams.setMargins(0, dpToPx(10), 0, 0);
    verifyButton.setLayoutParams(buttonParams);
    verifyButton.setText("登录");
    verifyButton.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#80a88bff"));
        setCornerRadius(dpToPx(10));
    }});
    
        购卡按钮 = new Button(context);
    LayoutParams buttonParams1 = new LayoutParams(dpToPx(280), dpToPx(50));
    buttonParams1.setMargins(0, dpToPx(10), 0, 0);
    购卡按钮.setLayoutParams(buttonParams1);
    购卡按钮.setText("店铺购卡");
    购卡按钮.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#80a88bff"));
        setCornerRadius(dpToPx(10));
    }});
    购卡按钮.setTextColor(Color.WHITE);
    购卡按钮.setTextSize(16);

    // 设置验证按钮的点击事件
    购卡按钮.setOnClickListener(v -> {
        跳转网页(context, "https://shop.xiaoman.top//links/1BA6C92E");
        
    });
        
    
    
    verifyButton.setTextColor(Color.WHITE);
    verifyButton.setTextSize(16);

    // 设置验证按钮的点击事件
    verifyButton.setOnClickListener(v -> {
        String cardPassword = cardPasswordInput.getText().toString();
        if (!cardPassword.isEmpty()) {
            attemptLogin(cardPassword, context);
        } else {
            Toast.makeText(context, "请输入卡密！", Toast.LENGTH_LONG).show();
        }
    });

    // 将输入框和按钮添加到验证界面
    verifyLayout.addView(yzText);
    verifyLayout.addView(cardPasswordInput);
    verifyLayout.addView(verifyButton);
    verifyLayout.addView(购卡按钮);

    // 将验证界面添加到根布局
    addView(verifyLayout);
}

    /**
     * 尝试使用卡密进行登录
     *
     * @param cardPassword 卡密
     * @param context 上下文
     */
    private void attemptLogin(String cardPassword, Context context) {
    wy.登录(cardPassword, context, new wy.LoginCallback() {
        @Override
        public void onLoginSuccess(String message) {
            // 登录成功
            FileUtil.读取私有目录(context, "km"); // 保存卡密到私有目录
            removeView(verifyLayout);
            initializeMainContent(context); // 初始化主内容
            RemoteVariables.initialize(cardPassword, context); // 初始化远程变量
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            
        }

        @Override
        public void onLoginFailure(String errorMessage) {
            // 登录失败
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    });
}

    private void initializeAnnouncementUI(Context context) {
    // 创建公告界面布局
    LinearLayout announcementLayout = new LinearLayout(context);
    announcementLayout.setOrientation(VERTICAL);
    announcementLayout.setGravity(Gravity.CENTER);
    LayoutParams layoutParams = new LayoutParams(dpToPx(350), dpToPx(200));
    announcementLayout.setLayoutParams(layoutParams);

    // 设置公告界面背景
    GradientDrawable background = new GradientDrawable();
    background.setColor(Color.parseColor("#00ffb9ee")); // 半透明深紫色背景
    background.setCornerRadius(dpToPx(16));
    announcementLayout.setBackground(background);

    // 创建公告文本视图
    TextView announcementTextView = new TextView(context);
    LayoutParams textParams = new LayoutParams(dpToPx(300), LayoutParams.WRAP_CONTENT);
    textParams.setMargins(0, dpToPx(20), 0, dpToPx(10));
    
    announcementTextView.setLayoutParams(textParams);
    announcementTextView.setText("正在加载公告内容...");
    announcementTextView.setTextColor(Color.parseColor("#ff000000"));
    announcementTextView.setTextSize(16);
    announcementTextView.setGravity(Gravity.CENTER);

    // 创建确认按钮
    Button confirmButton = new Button(context);
    LayoutParams buttonParams = new LayoutParams(dpToPx(280), dpToPx(50));
    buttonParams.setMargins(0, dpToPx(10), 0, 0);
    confirmButton.setLayoutParams(buttonParams);
    confirmButton.setText("确认");
    confirmButton.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#80a88bff"));
        setCornerRadius(dpToPx(10));
    }});
    confirmButton.setTextColor(Color.WHITE);
    confirmButton.setTextSize(16);

    // 调用公告方法
wy.公告(context, new wy.Callback() {
    @Override
    public void onResult(String result) {
        // 在主线程更新 UI
        new Handler(Looper.getMainLooper()).post(() -> {
            announcementTextView.setText(result); // 显示公告内容
        });
    }

    @Override
    public void onError(Exception e) {
        // 在主线程更新 UI
        new Handler(Looper.getMainLooper()).post(() -> {
            announcementTextView.setText("公告加载失败: " + e.getMessage());
        });
    }
});

    // 设置确认按钮点击事件
    confirmButton.setOnClickListener(v -> {
        // 点击确认后，移除公告界面并执行下一步操作
        removeView(announcementLayout);
        initializeVerificationUI(context); // 进入验证界面
    });

    // 将公告文本和确认按钮添加到公告界面
    announcementLayout.addView(announcementTextView);
    announcementLayout.addView(confirmButton);

    // 将公告界面添加到根布局
    addView(announcementLayout);
}




    // 卡密验证逻辑
    private boolean validateCardPassword(String cardPassword) {
        // 这里可以添加具体的卡密验证逻辑
        return "123456".equals(cardPassword); // 示例验证卡密
    }

    // 创建主界面内容
    private void initializeMainContent(Context context) {
    // 标题栏
    LinearLayout topLayout = new LinearLayout(context);
    topLayout.setOrientation(LinearLayout.HORIZONTAL); // 设置水平布局
    topLayout.setLayoutParams(new LayoutParams(dpToPx(371), dpToPx(50)));
    topLayout.setBackgroundColor(Color.TRANSPARENT);

    // 创建 ImageView 并设置大小及左右边距
    ImageView imageView = new ImageView(context);
    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(dpToPx(30), dpToPx(30));
    imageParams.setMargins(dpToPx(30), dpToPx(12), 0, 0);
    imageView.setLayoutParams(imageParams);

    // 使用 Glide 加载网络图片
    Glide.with(context)
         .load("https://cccimg.com/view.php/efa93117190cc664bf758f50a93cb26a.gif")
         //.apply(RequestOptions.bitmapTransform(new CircleCrop())) // 可选：圆形裁剪
         .into(imageView);

    imageView.setOnClickListener(v -> {
        setVisibility(View.GONE); // 隐藏菜单布局
    });

    // 创建标题文本视图
    // 创建标题文本视图
// 1. 添加必要的import语句（必须放在文件顶部）


// ... 其他代码 ...

// 2. 创建TextView（你的原始代码）
TextView textView = new TextView(context);
LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
textParams.setMargins(dpToPx(30), dpToPx(15), 0, 0);    
textView.setLayoutParams(textParams);
textView.setText("Blueberry - Automatic Script");
textView.setTextSize(20);
textView.setGravity(Gravity.CENTER_VERTICAL);

// 3. 确保在视图布局完成后获取宽度（关键修复！）
textView.post(new Runnable() {
    @Override
    public void run() {
        // 定义颜色数组
        int[] colors = {
            Color.RED,        // 红
            Color.YELLOW,     // 黄
            Color.GREEN,     // 绿
            Color.CYAN,      // 青
            Color.BLUE,       // 蓝
            Color.MAGENTA    // 紫
        };

        // 创建渐变着色器（使用实际测量到的宽度）
        final LinearGradient gradient = new LinearGradient(
                0, 0, 
                textView.getWidth(), 0,  // 使用实际宽度
                colors,
                null,
                Shader.TileMode.MIRROR);

        // 初始设置
        textView.getPaint().setShader(gradient);
        
        // 创建动画
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private final Matrix matrix = new Matrix();
            
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                matrix.setTranslate(value * textView.getWidth() * 2, 0);
                gradient.setLocalMatrix(matrix);
                textView.invalidate();
            }
        });
        
        animator.start();
    }
});
    // 将 ImageView 和 TextView 添加到 topLayout
    topLayout.addView(imageView);
    topLayout.addView(textView);

    // 将 topLayout 添加到根布局
    addView(topLayout);

    // 主内容布局
    LinearLayout mainContentLayout = new LinearLayout(context);
    mainContentLayout.setLayoutParams(new LayoutParams(dpToPx(371), dpToPx(206)));
    mainContentLayout.setOrientation(HORIZONTAL);
    mainContentLayout.setBackgroundColor(Color.TRANSPARENT);

    // ------------------- 新增：左侧外层容器 -------------------
    LinearLayout leftContainer = new LinearLayout(context);
    // 设置左侧容器宽高和边距（与原 menuLayout 保持一致）
    LayoutParams containerLayoutParams = new LayoutParams(dpToPx(70), LayoutParams.MATCH_PARENT);
    containerLayoutParams.setMargins(dpToPx(16), dpToPx(8), 0, dpToPx(8));
    leftContainer.setLayoutParams(containerLayoutParams);
    leftContainer.setOrientation(VERTICAL);

    // ------------------- ScrollView -------------------
    ScrollView menuScrollView = new ScrollView(context);
    // ScrollView 的布局参数设置为充满 leftContainer
    LayoutParams scrollViewParams = new LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.MATCH_PARENT
    );
    menuScrollView.setLayoutParams(scrollViewParams);
    menuScrollView.setFillViewport(true); // 保证内容不足时填满容器

    // ------------------- 菜单布局（真正放菜单项） -------------------
    LinearLayout menuLayout = new LinearLayout(context);
    menuLayout.setOrientation(VERTICAL);
    // 这里使用 WRAP_CONTENT，高度根据内容自适应
    LayoutParams menuLayoutParams = new LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT
    );
    menuLayout.setLayoutParams(menuLayoutParams);

    // 创建菜单项
    gameSettings = createMenuTextView(context, "加载", 0);
    skinSettings = createMenuTextView(context, "脚本", 1);
    operationSettings = createMenuTextView(context, "参数", 2);
    beautifySettings = createMenuTextView(context, "美化", 3);
    rankingSettings = createMenuTextView(context, "调试", 4);
    advancedSettings = createMenuTextView(context, "关于", 5);

    menuLayout.addView(gameSettings);
    menuLayout.addView(skinSettings);
    menuLayout.addView(operationSettings);
    menuLayout.addView(beautifySettings);
    menuLayout.addView(rankingSettings);
    menuLayout.addView(advancedSettings);

    // 将菜单布局放进 ScrollView
    menuScrollView.addView(menuLayout);
    // 将 ScrollView 放进左侧容器
    leftContainer.addView(menuScrollView);
    // 将左侧容器加入主内容布局
    mainContentLayout.addView(leftContainer);

    // 右侧内容布局容器，用于加载不同的分页
    contentFrame = new FrameLayout(context);
    contentFrame.setLayoutParams(new LayoutParams(dpToPx(285), LayoutParams.MATCH_PARENT));
    contentFrame.setBackgroundColor(Color.TRANSPARENT);
    // 设置右侧内容布局容器的右侧内边距为15dp
    contentFrame.setPadding(0, dpToPx(10), 0, dpToPx(15));
    mainContentLayout.addView(contentFrame);

    // 将主内容布局添加到根布局
    addView(mainContentLayout);

    // 显示第一页内容
    showPage(0);

    // 启动更新检测线程
    startUpdateChecker(context, topLayout, mainContentLayout);
}


private void startUpdateChecker(Context context, View topLayout, View mainContentLayout) {
        new Thread(() -> {
                            while (true) {
                                try {
                                    // 每隔 60 秒检查一次版本更新
                                    Thread.sleep(6000);

                                    wy.更新(
                                            context,
                                            new wy.VersionCallback() {
                                                @Override
                                                public void onVersionReceived(String version) {
                                                    if (!version.equals(wy.当前版本号)) {
                                                        // 切换到主线程更新视图
                                                        new Handler(Looper.getMainLooper()).post(() -> {
                                                                            // 移除主内容布局并显示更新界面
                                                                            removeView(mainContentLayout);
                                                                            removeView(topLayout);
                                                                            Button[] floatingButtons = new Button [] {
                                                                                                吐球,
                                                                                                三角,
                                                                                                蛇手,
                                                                                                侧合,
                                                                                                后仰,
                                                                                                旋转,
                                                                                                四分,
                                                                                                分身,
                                                                                                卡点,
                                                                                                内存卡点,
                                                                                                同步,
                                                                                                旋转卡点,
                                                                                                蛇手卡点,
                                                                                                追踪三角,
                                                                                                同步三角,
                                                                                                追踪大炮,
                                                                                                同步大炮,
                                                                                                同步蛇手
                                                                            };

                                                                            // 使用循环隐藏所有按钮
                                                                            for (int i = 0;
                                                                                    i < floatingButtons.length;
                                                                                    i++) {
                                                                                if (floatingButtons[
                                                                                                i]
                                                                                        != null) {
                                                                                    floatingButtons[i].setVisibility(View.GONE); // 隐藏按钮
                                                                                }
                                                                            }

                                                                            initializeUpdateUI(context);
                                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    // 可以选择记录日志或显示错误信息，当前略过
                                                }
                                            });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    break; // 停止线程
                                }
                            }
                        })
                .start();
}


    private TextView createMenuTextView(Context context, String text, int pageIndex) {
        TextView textView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40));
        params.setMargins(0, dpToPx(6), 0, 0);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#ff000000"));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, dpToPx(6), 0, 0);
        textView.setClickable(true);
        textView.setBackgroundColor(Color.TRANSPARENT);

        textView.setOnClickListener(v -> showPage(pageIndex));

        return textView;
    }

    private void showPage(int pageIndex) {
        contentFrame.removeAllViews();

        LinearLayout pageLayout = pageLayouts.get(pageIndex);
        if (pageLayout == null) {
            if (pageIndex == 0) {
                pageLayout = createPage1Layout(getContext());
            } else if (pageIndex == 1) {
                pageLayout = createPage2Layout(getContext());
            } else if (pageIndex == 2) {
                pageLayout = createPage3Layout(getContext());
            } else if (pageIndex == 3) {
                pageLayout = createPage4Layout(getContext());
            } else if (pageIndex == 4) {
                pageLayout = createPage5Layout(getContext());
            } else if (pageIndex == 5) {
                pageLayout = createPage6Layout(getContext());
            }
            pageLayouts.put(pageIndex, pageLayout);
        }

        contentFrame.addView(pageLayout);
        updateMenuHighlight(pageIndex);
    }

    private LinearLayout createPage1Layout(Context context) {
        LinearLayout page1Layout = new LinearLayout(context);
        page1Layout.setOrientation(VERTICAL);

        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        LinearLayout switchContainer = new LinearLayout(context);
        switchContainer.setOrientation(VERTICAL);

        // 设置左右相同的边距
        LayoutParams switchContainerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        int margin = dpToPx(16); // 假设16dp的边距
        switchContainerParams.setMargins(margin, 0, margin, 0);
        switchContainer.setLayoutParams(switchContainerParams);
        
        
        
                                
        LinearLayout syLayout = new LinearLayout(context);
        syLayout.setOrientation(VERTICAL);
        syLayout.setVisibility(View.GONE); // 初始隐藏
        syLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
        addFloatSeekBarWithEditText(context, syLayout, "视野高度:", BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5), BigDecimal.valueOf(6), value -> {
        
            if (Incomingone[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String syValue = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(syValue, Incomingone[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        });

        long[] Incomingtwo = new long[1];  // 用来存储第二个地址
        
        // 创建折叠区域的视图，初始状态隐藏
        LinearLayout hqLayout = new LinearLayout(context);
        hqLayout.setOrientation(VERTICAL);
        hqLayout.setVisibility(View.GONE); // 初始隐藏
        hqLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
        addFloatSeekBarWithEditText(context, hqLayout, "动画速度:", BigDecimal.valueOf(0.588), BigDecimal.valueOf(0.000), BigDecimal.valueOf(1.588), value -> {
        
            if (Incomingtwo[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String hqValue = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(hqValue, Incomingtwo[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });        
        
        
        long[] Incomingthree = new long[1];  // 用来存储第一个地址
        
        LinearLayout bzLayout = new LinearLayout(context);
        bzLayout.setOrientation(VERTICAL);
        bzLayout.setVisibility(View.GONE); // 初始隐藏
        bzLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
        addFloatSeekBarWithEditText(context, bzLayout, "孢子大小:", BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.001), BigDecimal.valueOf(5.0), value -> {
        
            if (Incomingthree[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String bzValue = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(bzValue, Incomingthree[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
        
        
        long[] Incomingfour = new long[1];
        
        LinearLayout qjLayout = new LinearLayout(context);
        qjLayout.setOrientation(VERTICAL);
        qjLayout.setVisibility(View.GONE); // 初始隐藏
        qjLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
        addSeekBarWithEditText(context, qjLayout, "全局速度:", 1041313291, 1041313291, 1051313291, value -> {
                
                // 将浮动值转换为字符串并启动线程执行内存写入
                String qjValue = String.valueOf(value);
            
                memory.setPackageName("com.ztgame.bob");
                long moduleAddress = memory.getModuleAddress("libunity.so");
                long targetAddress = moduleAddress + 0x1524F4;
                memory.setValue(qjValue, targetAddress, memory.TYPE_DWORD);
                 
        });
        
        
        long[] Incomingsix = new long[1];  // 用来存储第一个地址
        
        LinearLayout mzLayout = new LinearLayout(context);
        mzLayout.setOrientation(VERTICAL);
        mzLayout.setVisibility(View.GONE); // 初始隐藏
        mzLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
        addFloatSeekBarWithEditText(context, mzLayout, "名字大小:", BigDecimal.valueOf(1.875), BigDecimal.valueOf(0.000), BigDecimal.valueOf(2.875), value -> {
        
            if (Incomingsix[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String mzValue = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(mzValue, Incomingsix[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

      
          addTextView(context, page1Layout, "开启加载内存后可正常使用脚本（初始化方向）", null);
        
        addTextView(context, page1Layout, "开启加载绘制后才可使用绘制卡点及绘制功能", null);
        
        // 添加8个开关按钮
        String[] switchNames = { "加载内存(实战吐球分身开启)", "加载绘制(训练营无分身开启)"/*,"环境异常(加载界面69%开启)"*/, "绘制边框", "绘制射线", "绘制排名"  
                                  /* "视野高度", "合球动画", "全局变速", "孢子大小", "名字大小", "吐球双连点", "解除帧率限制"*/};

        for (int i = 0; i < switchNames.length; i++) {
            final String switchName = switchNames[i];
            Switch switchButton = new Switch(context);
            switchButton.setText(switchName);
            switchButton.setTag(switchName); // 设置Tag
            switchButton.setTextColor(Color.parseColor("#ff000000"));
            applySwitchStyle(switchButton);

            // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        String name = (String) buttonView.getTag();
                        if (isChecked) {
                            // 开关被打开时的操作
                            switch (name) {
                                case "加载内存(实战吐球分身开启)":
                                    IncomingAddress("com.ztgame.bob", mainHandler, Incomingone, Incomingtwo, Incomingthree, Incomingfour, Incomingsix);
                                break;
                                case "加载绘制(训练营无分身开启)":
                                    new Thread(() -> {
                                    try {
                                        // 这里直接使用类的 mainHandler，不要重新声明
                                        memory.clearResult();
                                        memory.setPackageName("com.ztgame.bob");
                                        memory.setRange(memory.RANGE_ANONYMOUS);
                                        memory.RangeMemorySearch("2333", memory.TYPE_DWORD);
                                        memory.MemoryOffset("-1", memory.TYPE_DWORD, 12);
                                        memory.MemoryOffset("-1", memory.TYPE_DWORD, 36);
                            
                                        if (memory.getResultCount() > 0) {
                                            long[] resultAddresses = memory.getResult(memory.getResultCount());
                                            for (long address : resultAddresses) {
                                                float value = memory.readFloat(address + 96);
                                                if (value == 0.0f) {
                                                    a5 = address;
                                                    break;
                                                }
                                            }
                                        }
                            
                                        memory.clearResult();
                                        memory.setRange(memory.RANGE_ANONYMOUS);
                                        memory.RangeMemorySearch("14.5", memory.TYPE_FLOAT);
                                        memory.MemoryOffset("800", memory.TYPE_FLOAT, -104);
                            
                                        if (memory.getResultCount() > 0) {
                                            long[] resultAddresses = memory.getResult(1);
                                            a3 = resultAddresses[0];
                                        }
                                        memory.clearResult();
                            
                                        // 延迟 0.5 秒后执行 获取玩家结构体
                                        mainHandler.postDelayed(() -> {
                                            获取玩家结构体(context, "玩家坐标数据.txt");
                                        }, 500);
                                        
                                        } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    }).start();
                                    break;
                               /*case "环境异常(加载界面69%开启)":
                                    Detection("com.ztgame.bob");
                                    break;*/
                                case "绘制边框":
                                    enableDrawCircle = true;
                                    break;
                                case "绘制射线":
                                    enableDrawLine = true;
                                    break;
                                case "绘制排名":
                                    绘制排名 = true;
                                    break;                                                               
                                case "视野高度":
                                    syLayout.setVisibility(View.VISIBLE);
                                    break;
                                case "合球动画":
                                    hqLayout.setVisibility(View.VISIBLE);
                                    break;
                                case "全局变速":
                                    qjLayout.setVisibility(View.VISIBLE);
                                    break;
                                case "孢子大小":
                                    bzLayout.setVisibility(View.VISIBLE);
                                    break;
                                case "名字大小":
                                    mzLayout.setVisibility(View.VISIBLE);
                                    break;
                                case "吐球双连点":
                                    memory.setValue("-10", Incomingfour[0], memory.TYPE_FLOAT);
                                    break;
                                case "解除帧率限制":
                                    unlockFrameRate("com.ztgame.bob", mainHandler);
                                    break;
                            }
                            
                        } else {
                            // 开关被关闭时的操作
                            switch (name) {
                            case "加载内存(实战吐球分身开启)":
                                showToast(context, "没有此操作");
                                break;
                            case "加载绘制(训练营无分身开启)":
                                showToast(context, "没有此操作");
                                break;
                            /*case "环境异常(加载界面69%开启)":
                                showToast(context, "没有此操作");
                                break;*/
                            case "绘制边框":
                                enableDrawCircle = false;
                                break;
                            case "绘制射线":
                                enableDrawLine = false;
                                break;
                            case "绘制排名":
                                绘制排名 = false;
                                break;                                                        
                            case "视野高度":
                                syLayout.setVisibility(View.GONE);
                                break;
                            case "合球动画":
                                hqLayout.setVisibility(View.GONE);
                                break;
                            case "全局变速":
                                qjLayout.setVisibility(View.GONE);
                                break;
                            case "孢子大小":
                                bzLayout.setVisibility(View.GONE);
                                break;
                            case "名字大小":
                                mzLayout.setVisibility(View.GONE);
                                break;
                            case "吐球双连点":
                                memory.setValue("0.055", Incomingfour[0], memory.TYPE_FLOAT);
                                break;
                            case "解除帧率限制":
                                showToast(context, "没有此操作");                                    
                                break;
                            }
                            
                        }
                    });

            // 将Switch按钮添加到容器中
            switchContainer.addView(switchButton);

            // 在“双连点”开关下方添加折叠区域
            if ("视野高度".equals(switchName)) {
                switchContainer.addView(syLayout);
            }
            if ("合球动画".equals(switchName)) {
                switchContainer.addView(hqLayout);
            }
            if ("全局变速".equals(switchName)) {
                switchContainer.addView(qjLayout);
            }
            if ("孢子大小".equals(switchName)) {
                switchContainer.addView(bzLayout);
            }
            if ("名字大小".equals(switchName)) {
                switchContainer.addView(mzLayout);
            }
        }

        scrollView.addView(switchContainer);
        page1Layout.addView(scrollView);

        return page1Layout;
    }

    public int add(int a, int b) {
        return a + b; // 原始方法
    }
    
 /*  // 过环境检测
    private void Detection(String packageName) {
        new Thread(() -> {
            try {
                memory.setPackageName(packageName);

                long baseAddr = memory.getModuleAddress("libNetHTProtect.so");
                
                memory.setValue("10086", baseAddr + 0x2C9B94, memory.TYPE_DWORD);
                memory.setValue("10086", baseAddr + 0x2C9BD0, memory.TYPE_DWORD);            
                memory.setValue("10086", baseAddr + 0x2C9C08, memory.TYPE_DWORD);
                memory.setValue("10086", baseAddr + 0x2C9D94, memory.TYPE_DWORD);
                memory.setValue("10086", baseAddr + 0x2C9E80, memory.TYPE_DWORD);
                memory.setValue("10086", baseAddr + 0x2C9E98, memory.TYPE_DWORD);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }*/

    
    // 解除帧率限制操作
    private void unlockFrameRate(String packageName, Handler mainHandler) {
        new Thread(() -> {
            try {
                memory.setPackageName(packageName);
                long moduleAddress = memory.getModuleAddress("libunity.so");
                long targetAddress = moduleAddress + 0x17C05E8;
                memory.setValue("120", targetAddress, memory.TYPE_DWORD);

                //mainHandler.post(() -> showToast(null, "解除帧率限制 开启"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    // 内存初始化
    private void IncomingAddress(String packageName, Handler mainHandler, final long[] Incomingone, final long[] Incomingtwo, final long[] Incomingthree, final long[] Incomingfour, final long[] Incomingsix) {
    
    new Thread(() -> {
        try {
            
            memory.clearResult();
            memory.setPackageName(packageName);
            memory.setRange(memory.RANGE_ANONYMOUS);
            memory.RangeMemorySearch("20000", memory.TYPE_FLOAT);
            memory.MemoryOffset("500", memory.TYPE_FLOAT, 12);
            memory.MemoryOffset("500", memory.TYPE_FLOAT, 24);
            int resultCount = memory.getResultCount();
            if (resultCount > 0) {
                long[] resultAddresses = memory.getResult(resultCount);//视野

                for (long addr : resultAddresses) {
                    // 存储第一个地址
                    Incomingone[0] = addr - 388;
                
                }
            }
                  
            memory.clearResult();
            memory.setRange(memory.RANGE_ANONYMOUS);
            memory.RangeMemorySearch("1.84091382e-40", memory.TYPE_FLOAT);
            memory.MemoryOffset("9.27533467e-41", memory.TYPE_FLOAT, 308);
            
            resultCount = memory.getResultCount();
            if (resultCount > 0) {
                long[] resultAddresses = memory.getResult(resultCount);

                for (long addr : resultAddresses) {
                    // 存储第二个地址
                    Incomingtwo[0] = addr + 304;  // 初始值 A F 0.58799999952~
                    
                }
            }
            
            memory.clearResult();
            memory.setRange(memory.RANGE_ANONYMOUS);
            memory.RangeMemorySearch("0.00389999989", memory.TYPE_FLOAT);
            memory.MemoryOffset("5.51029793e-40", memory.TYPE_FLOAT, 116);
            
            resultCount = memory.getResultCount();
            if (resultCount > 0) {
                long[] resultAddresses = memory.getResult(resultCount);

                for (long addr : resultAddresses) {
                    // 存储第三个地址
                    Incomingthree[0] = addr + 120;  // 初始值 A F 0.01~
                    
                }
            }
            
            memory.clearResult();
            memory.setRange(memory.RANGE_ANONYMOUS);
            memory.RangeMemorySearch("2.75739104e-40", memory.TYPE_FLOAT);
            memory.MemoryOffset("5.60519386e-45", memory.TYPE_FLOAT, -16);
            memory.MemoryOffset("4.20389539e-45", memory.TYPE_FLOAT, -20);
            
            resultCount = memory.getResultCount();
            if (resultCount > 0) {
                long[] resultAddresses = memory.getResult(resultCount);

                for (long addr : resultAddresses) {
                    // 存储第四个地址
                    Incomingfour[0] = addr - 4;  // 可以根据需要调整偏移值(双联点)
                    
                }  
            }
            
            memory.clearResult(); // 清除搜索结果
            memory.setRange(memory.RANGE_ANONYMOUS);
            memory.RangeMemorySearch("1818845440", memory.TYPE_DWORD); 
            memory.MemoryOffset("1868767343", memory.TYPE_DWORD,8);
            
            if (memory.getResultCount() > 0) {
                long[] resultAddresses = memory.getResult(1);
                // 存储第五个地址
                Incomingfive = resultAddresses[0];
                
            }
            
            memory.clearResult();
            memory.setRange(memory.RANGE_ANONYMOUS);
            memory.RangeMemorySearch("0.01499999966", memory.TYPE_FLOAT);
            memory.MemoryOffset("0.01166000031", memory.TYPE_FLOAT, -72);
            
            resultCount = memory.getResultCount();
            if (resultCount > 0) {
                long[] resultAddresses = memory.getResult(resultCount);

                for (long addr : resultAddresses) {
                    // 存储第六个地址
                    Incomingsix[0] = addr + 40;  // 可以根据需要调整偏移值(名字大小)
                    
                }  
            }
            
            performMemorySearchAndCalculateAngle();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
}

/*
    // 获取内存地址并更新UI
    private void fetchMemoryAddressAndUpdateUI(String packageName, int range, String searchValue, Handler mainHandler, final long[] memoryAddressHolder) {
        new Thread(() -> {
            try {
                memory.clearResult();
                memory.setPackageName(packageName);
                memory.setRange(range);
                memory.RangeMemorySearch(searchValue, memory.TYPE_FLOAT);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 12);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 24);

                int resultCount = memory.getResultCount();
                if (resultCount > 0) {
                    long[] resultAddresses = memory.getResult(resultCount);

                    for (long addr : resultAddresses) {
                        memoryAddressHolder[0] = addr - 388;

                        mainHandler.post(() -> {
                            System.out.println("已获取到内存地址: " + memoryAddressHolder[0]);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
*/

  // =========================
// 全局变量与状态标记
// =========================
// =========================
// 全局变量 & 状态标识
// =========================

/************************************************
 * 1. 首先定义一个 PlayerEntity 结构体（替代原有的字符串存储方式）
 *    - 用于存储每个球体（对象）的关键信息
 ************************************************/
/****************************************************************************
 * 1. 定义 PlayerEntity 结构体
 *    - 不再使用全局编号，而是通过 rankId 分组。
 *    - 在同一个 rankId 组内，通过 subIndex 给它分组内的排名（0 表示该组最大半径）。
 ****************************************************************************/
/************************************************
 * 1) 定义 PlayerEntity 结构不变
 ************************************************/
/**************************************************************
 * 修复说明：
 * 1) 将所有的调试输出由 Android Log 改为 XposedBridge.log 方式；
 * 2) 同步按钮被点击后，先获取「指定排名ID」下最大球相对于「自身ID」下最大球的角度(角度1)；
 * 3) 触发分裂操作，等待分裂完成后，过滤掉原本分裂的那颗球(旧地址)，
 *    获取新的分裂球(新地址)中半径最大的那颗，并计算它相对于「自身ID」下最大球的角度(角度2)；
 * 4) 将角度1 和 角度2 存到全局变量，并用提示方式显示到主界面。
 **************************************************************/


/**************************************************************
 * 新增与调整的功能要点：
 * 1) 给每个球体分配一个唯一编号 (uniqueId)，以方便区分究竟是“旧球”还是“新球”。
 *   - 在扫描玩家结构体时，如果当前球体的内存地址尚未在全局映射中登记过，则分配一个自增的 uniqueId 并保存。
 *   - 将该 uniqueId 存入 PlayerEntity 中，以后在判断“旧地址/新地址”时，更可靠。
 *
 * 2) 在计算角度时，根据用户需求：
 *   - 如果“原本的是顺时针”就改为“逆时针”，反之亦然，这里采取最简单的方式：角度直接反转。
 *   - 反转方法之一： angle = 360 - angle (若想要直接转到对立角度)。
 *   - 示例：若原角度=78.9°，则反转后= 360-78.9=281.1°，即可视为“对侧”或“倒向”。
 *
 * 3) 当“指定排名球”分裂后，过滤旧球(通过 uniqueId 或 address 识别)，
 *   找到新分裂出来的球(同组中未出现过的 uniqueId 或 address)，并计算角度。
 *   若要对新角度也做同样的顺逆时针反转，可同理再处理。
 *
 * 下方示例代码在原基础上进行修改示意：
 **************************************************************/

/**************************************************************
 * 最终示例代码（示意）—— 使用 uniqueId 区分球体，
 * 并在分裂后自动吃掉队友旧球，再反转方向合给队友新球。
 *
 * 注意：
 * 1) 以下代码以“球球大作战”类似逻辑为参照，示例了整个流程：
 *    - 扫描内存并分配 uniqueId (用于识别球体)；
 *    - 在同步/分裂逻辑中，按下分身(不需手动移动)，让分裂球自动吃掉队友旧球；
 *    - 吃完后，再反转角度(或再次分裂、吐球等)把质量合给队友新球；
 * 2) 实际游戏逻辑可能更复杂，需根据你自己对游戏内存/地址/坐标的理解，
 *    以及分裂球“自动追踪队友旧球”的具体机制来做适配。
 * 3) 本示例仅供参考，部分函数(如 触发…功能)里包含线程延时、角度滑动等操作，
 *    需在实际环境中不断调试、调整参数。
 **************************************************************/


    // =========== 1) 数据结构 ===========

    /**************************************************************
 * 最终示例代码——在分裂前后分别计算旧球与新球相对于“我方最大球”的角度(角度1、角度2)，
 * 并同时保留原本的逻辑：用 uniqueId 区分球体，在分裂后自动吃掉队友旧球，再把质量合给队友新球。
 * 
 * 主要修改点：
 * 1) 在「同步」分裂前，先计算旧球相对于我方最大球的角度 => 角度1，并保存到全局变量。
 * 2) 在分裂完、吃掉旧球、重新获取队友组后，计算新球相对于我方最大球的角度 => 角度2，并保存到全局变量。
 * 3) 最后，将 angle1、dragAngle 一并通过提示或日志输出。
 **************************************************************/

    // =================== 1) 数据结构 ===================

    // 玩家球体信息
    private static class addCircle {
        public long address;       // 内存地址(识别球体)
        public long uniqueId;      // 全局唯一编号
        public float x;            // 世界坐标 X
        public float y;            // 世界坐标 Y
        public float radius;       // 球体半径
        public float volume;       // 球体体积
        public int subIndex;       // 在同 rankId 分组内的排序(0=>最大球)
        public int rankId;         // rankId(组ID)

        public addCircle(long address, long uniqueId, float x, float y, float radius, int rankId) {
            this.address = address;
            this.uniqueId = uniqueId;
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.rankId = rankId;
            this.volume = (float) ((4 / 3.0) * Math.PI * Math.pow(radius, 3));
        }
    }

    // rankId => List<addCircle>
    private Map<Integer, List<addCircle>> rankToPlayers = new HashMap<>();
    // 扁平映射: uniqueId => addCircle
    private Map<Long, addCircle> allPlayers = new HashMap<>();
    // address => uniqueId
    private Map<Long, Long> addressToUniqueIdMap = new HashMap<>();
    private static long globalUniqueCounter = 1;

    // =================== 2) 全局控制变量 ===================

    private boolean isDetecting = false;
    private int cardSelectedRank = -1;
    private int cardSelectedSubIndex = -1;
    private float 锁定球体初始体积 = -1;
    private boolean isSelectedCircleVisible = false;

    private boolean isDetecting2 = false;
    private int cardSelectedRank2 = -1;
    private int cardSelectedSubIndex2 = -1;
    private float 锁定旋转卡点初始体积 = -1;
    
    
        private boolean isDetecting3 = false;
    private int cardSelectedRank3 = -1;
    private int cardSelectedSubIndex3 = -1;
    private float 锁定蛇手卡点初始体积 = -1;
    
    private boolean isSyncDetecting = false;
    private boolean isSyncCircleVisible = false;
    private int syncSelectedSubIndex = -1;
    private float 锁定同步球体初始体积 = -1;
    private boolean allowReacquire = true;
     // 在全局变量区域新增
private boolean isSyncDetecting2 = false;
private int syncSelectedSubIndex2 = -1;
private float 锁定同步大炮球体初始体积 = -1;
      // 在全局变量区域新增
private boolean isSyncTriangleDetecting = false;
private int syncTriangleSelectedSubIndex = -1;
private float 锁定追踪三角球体初始体积 = -1;
    // 在全局变量区域新增222
private boolean isSyncTriangleDetecting2 = false;
private int syncTriangleSelectedSubIndex2 = -1;
private float 锁定同步三角球体初始体积 = -1;
    //同步蛇手
    private boolean isSyncSnakeDetecting = false;
private int syncSnakeSelectedSubIndex = -1;
private float lockSnakeInitialVolume = -1;
private List<addCircle> snakeGroup = new ArrayList<>();

    // 在分裂前后记录的角度
    private float 角度1 = 0f;  // 旧球相对于我方最大球的角度
    private float 角度2 = 0f;  // 新球相对于我方最大球的角度

    // 指定队友rankId，我方rankId
    private int specifiedRankId = 10;
    private int myRankId = 99;  

    // 视图/绘制控制
    private boolean showCardButton = false;
    private boolean showRankButton = false;
    private boolean 绘制排名 = false;
    private boolean enableDrawCircle = false;
    private boolean enableDrawLine = false;

    // UI 按钮移动
    
    private final float MOVE_THRESHOLD = 100f;
    

    private int 刷新延时 = 8;  


    /************************************************
     * 5) 获取玩家结构体 => rankToPlayers + allPlayers
     ************************************************/
 private void 获取玩家结构体(Context context, String fileName) {
        new Thread(() -> {
            try {
                int bits = 64;
                long 数组地址 = a5; // 你自己的偏移
                Handler handler = new Handler(Looper.getMainLooper());

                Runnable updateRunnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<Integer, List<addCircle>> 临时map = new HashMap<>();

                            float 视图比例1 = memory.readFloat(a3 - 0x38);
                            handler.post(() -> 视图比例 = 视图比例1);

                            // 先确定有效玩家数量
                            int 实际玩家数量 = 0;
                            for (int i = 0; i < 512; i++) {
                                long[] 玩家结构体指针 = memory.pointerJump(
                                        数组地址 + i * 0x18,
                                        new long[]{0x18, 0x0},
                                        bits
                                );
                                if (!checkPointerChain(玩家结构体指针)) {
                                    continue;
                                }
                                long playerBaseAddr = 玩家结构体指针[玩家结构体指针.length - 1];
                                if (memory.readDword(playerBaseAddr + 0x20) == 1) {
                                    实际玩家数量++;
                                }
                            }

                            // 遍历实际玩家数量
                            for (int i = 0, 当前玩家计数 = 0; 当前玩家计数 < 实际玩家数量 && i < 512; i++) {
                                long[] 玩家结构体指针 = memory.pointerJump(
                                        数组地址 + i * 0x18,
                                        new long[]{0x18, 0x0},
                                        bits
                                );
                                if (!checkPointerChain(玩家结构体指针)) {
                                    continue;
                                }
                                long playerBaseAddr = 玩家结构体指针[玩家结构体指针.length - 1];
                                if (memory.readDword(playerBaseAddr + 0x20) != 1) {
                                    continue;
                                }
                                当前玩家计数++;

                                // 取坐标等信息
                                long[] 坐标指针 = memory.pointerJump(
                                        playerBaseAddr,
                                        new long[]{0x18, 0x0},
                                        bits
                                );
                                if (!checkPointerChain(坐标指针)) {
                                    continue;
                                }
                                long 坐标地址 = 坐标指针[坐标指针.length - 1];

                                long[] 排名id指针 = memory.pointerJump(
                                        坐标地址,
                                        new long[]{0x30, 0x0},
                                        bits
                                );
                                if (!checkPointerChain(排名id指针)) {
                                    continue;
                                }
                                long 排名id地址 = 排名id指针[排名id指针.length - 1];

                                float 半径 = memory.readFloat(playerBaseAddr + 0x28);
                                int rankId = memory.readDword(排名id地址 + 0x10);
                                float x = memory.readFloat(坐标地址 + 0x80);
                                float y = 200 - memory.readFloat(坐标地址 + 0x84);

                                // 分配 uniqueId
                                long uniqueId;
                                if (addressToUniqueIdMap.containsKey(playerBaseAddr)) {
                                    uniqueId = addressToUniqueIdMap.get(playerBaseAddr);
                                } else {
                                    uniqueId = globalUniqueCounter++;
                                    addressToUniqueIdMap.put(playerBaseAddr, uniqueId);
                                }

                                addCircle entity = new addCircle(
                                        playerBaseAddr,
                                        uniqueId,
                                        x,
                                        y,
                                        半径,
                                        rankId
                                );
                                if (!临时map.containsKey(rankId)) {
                                    临时map.put(rankId, new ArrayList<>());
                                }
                                临时map.get(rankId).add(entity);
                            }

                            // 每组按半径降序 => 赋 subIndex
                            for (Map.Entry<Integer, List<addCircle>> e : 临时map.entrySet()) {
                                List<addCircle> group = e.getValue();
                                group.sort((a, b) -> Float.compare(b.radius, a.radius));
                                for (int j = 0; j < group.size(); j++) {
                                    group.get(j).subIndex = j;
                                }
                            }

                            // 替换到全局
                            rankToPlayers.clear();
                            rankToPlayers.putAll(临时map);

                            // 扁平化 => allPlayers
                            updateAllPlayers(rankToPlayers);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.postDelayed(this, 刷新延时);
                    }
                };
                handler.post(updateRunnable);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

// 读取玩家的详细信息
private void readPlayerDetails(long playerBaseAddr, Map<Integer, List<addCircle>> 临时map) {
    try {
        int bits = 64;
        // 读取坐标和排名ID
        long[] 坐标指针 = memory.pointerJump(playerBaseAddr, new long[]{0x18, 0x0}, bits);
        if (!checkPointerChain(坐标指针)) return;
        long 坐标地址 = 坐标指针[坐标指针.length - 1];

        long[] 排名id指针 = memory.pointerJump(坐标地址, new long[]{0x30, 0x0}, bits);
        if (!checkPointerChain(排名id指针)) return;
        long 排名id地址 = 排名id指针[排名id指针.length - 1];

        float 半径 = memory.readFloat(playerBaseAddr + 0x28);
        int rankId = memory.readDword(排名id地址 + 0x10);
        float x = memory.readFloat(坐标地址 + 0x80);
        float y = 200 - memory.readFloat(坐标地址 + 0x84);

        long uniqueId = globalUniqueCounter++;
        addressToUniqueIdMap.put(playerBaseAddr, uniqueId);

        addCircle entity = new addCircle(playerBaseAddr, uniqueId, x, y, 半径, rankId);
        // 按照 rankId 分组
        临时map.computeIfAbsent(rankId, k -> new CopyOnWriteArrayList<>()).add(entity);

    } catch (Exception e) {
        Log.e("p3", "Error in readPlayerDetails", e);
    }
}

private void updateAllPlayers(Map<Integer, List<addCircle>> map) {
    synchronized (allPlayers) {
        allPlayers.clear();
        for (List<addCircle> list : map.values()) {
            if (list != null) {
                for (addCircle p : list) {
                    allPlayers.put(p.uniqueId, p);
                }
            }
        }
    }
}

private boolean checkPointerChain(long[] pointerResult) {
    if (pointerResult == null) return false;
    for (long addr : pointerResult) {
        if (addr == 0) return false;
    }
    return true;
}


    /************************************************
     * 6) 绘制玩家线段 (示意)
     ************************************************/
     public void 绘制玩家线段(Context context) {
        if (specifiedRankId == -1) {
            specifiedRankId = 10;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable drawRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    drawView.clearAllShapes();
                    if (rankToPlayers == null || rankToPlayers.isEmpty()) {
                        handler.postDelayed(this, 8);
                        return;
                    }

                    float startX = parameters.分辨率x / 2f;
                    float startY = parameters.分辨率y / 2f;
                    float offsetX = memory.readFloat(a3 - 100);
                    float offsetY = 200 - memory.readFloat(a3 - 96);

                    // 遍历各组
                    for (Map.Entry<Integer, List<addCircle>> entry : rankToPlayers.entrySet()) {
                        int rankId = entry.getKey();
                        List<addCircle> groupList = entry.getValue();
                        if (groupList == null || groupList.isEmpty()) continue;

                        for (addCircle entity : groupList) {
                            float screenXa = ((entity.x - offsetX)
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例))
                                    + startX;
                            float screenYa = ((entity.y - offsetY)
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例))
                                    + startY;
                            float drawRadius = entity.radius
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例);

                            if (!isSelectedCircleVisible) {
                                // 普通绘制
                                if (enableDrawLine) {
                                    drawView.addLine(
                                            startX, startY,
                                            screenXa, screenYa,
                                            Color.parseColor("#FFFFFF"), 3f
                                    );
                                刷新延时=8;
                                }
                                if (enableDrawCircle) {
                                    if (isDetecting
                                            && rankId == cardSelectedRank
                                            && entity.subIndex == cardSelectedSubIndex) {
                                    drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#FFFFFF"), 6f);
                                    刷新延时=1;
                                    } else {
                                        drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#FFFFFF"), 3f);
                                    刷新延时=8;
                                    }
                                }
                            } else {
                                                 if (rankId == cardSelectedRank && entity.subIndex == cardSelectedSubIndex) {
                                    drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#FFFF33"), 6f);
                                刷新延时=1;
                                }
                            }

                            // 卡点判断
                            if (isDetecting && rankId == cardSelectedRank && entity.subIndex == cardSelectedSubIndex) {
                                float currentVolume = entity.volume;
                                if (锁定球体初始体积 < 0) {
                                刷新延时=1;
                                    锁定球体初始体积 = currentVolume;
                                } else if (currentVolume < 锁定球体初始体积 * 0.5f) {
                       
            HOOK.TouchEventHandler.按下(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
            HOOK.TouchEventHandler.抬起(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
                                    触发一分为二功能(context);
                                刷新延时=8;
                                    停止检测并恢复状态();
                                }
                        } else {
                                                 if (rankId == cardSelectedRank2 && entity.subIndex == cardSelectedSubIndex2) {
                                    drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#FFA07A"), 6f);
                                刷新延时=1;
                                }
                            }

                            // 旋转卡点判断
                            if (isDetecting2 && rankId == cardSelectedRank2 && entity.subIndex == cardSelectedSubIndex2) {
                                float currentVolume = entity.volume;
                                if (锁定旋转卡点初始体积 < 0) {
                                刷新延时=1;
                                    锁定旋转卡点初始体积 = currentVolume;
                                } else if (currentVolume < 锁定旋转卡点初始体积 * 0.5f) {
                       
            HOOK.TouchEventHandler.按下(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
            HOOK.TouchEventHandler.抬起(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
                                    执行旋转卡点(context);
                                刷新延时=8;
                                        
                                    停止旋转卡点检测();
                                }
                                    
                                    
                                    //蛇手卡点
                                   } else {
                                                 if (rankId == cardSelectedRank3 && entity.subIndex == cardSelectedSubIndex3) {
                                    drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#FF4500"), 6f);
                                刷新延时=1;
                                }
                            }

                            // 卡点判断
                            if (isDetecting3 && rankId == cardSelectedRank3 && entity.subIndex == cardSelectedSubIndex3) {
                                float currentVolume = entity.volume;
                                if (锁定蛇手卡点初始体积 < 0) {
                                刷新延时=1;
                                    锁定蛇手卡点初始体积 = currentVolume;
                                } else if (currentVolume < 锁定蛇手卡点初始体积 * 0.5f) {
                       
            HOOK.TouchEventHandler.按下(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
            HOOK.TouchEventHandler.抬起(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
                                    触发蛇手卡点功能(context);
                                刷新延时=8;
                                    停止蛇手卡点检测();
                                }
                                    }
                                                      // 同步检测 => 分裂判断
                            if (isSyncDetecting && rankId == specifiedRankId) {
                                if (syncSelectedSubIndex < 0 || syncSelectedSubIndex >= groupList.size()) {
                                    if (allowReacquire) {
                                        reAcquireSyncBall(context);
                                    } else {
                                        停止检测并恢复状态();
                                    }
                                } else {
                                    addCircle syncTarget = groupList.get(syncSelectedSubIndex);
                                    if (syncTarget.rankId != specifiedRankId) {
                                        if (allowReacquire) {
                                            reAcquireSyncBall(context);
                                        } else {
                                            停止检测并恢复状态();
                                        }
                                    } else {
                                        float currentVolume = syncTarget.volume;
                                        if (锁定同步球体初始体积 < 0) {
                                            锁定同步球体初始体积 = currentVolume;
                                        } else if (currentVolume < 锁定同步球体初始体积 * 0.5f) {
                                            触发同步一分为二功能(context);
                                            停止检测并恢复状态();
                                        }
                                    }
                                }
                            }
                         
                                                       // 同步检测 => 分裂判断
                         // 在绘制玩家线段方法中找到体积检测逻辑，修改为
   if (isSyncDetecting && rankId == specifiedRankId) {
    // 原有同步逻辑...
} 
                                // 新增同步蛇手检测
else if (isSyncSnakeDetecting && rankId == specifiedRankId) {
    if (syncSnakeSelectedSubIndex < 0 || syncSnakeSelectedSubIndex >= groupList.size()) {
        if (allowReacquire) {
            reAcquireSyncSnakeBall(context);
        } else {
            stopSyncSnakeDetecting();
        }
    } else {
        addCircle syncTarget = groupList.get(syncSnakeSelectedSubIndex);
        float currentVolume = syncTarget.volume;
        if (lockSnakeInitialVolume < 0) {
            lockSnakeInitialVolume = currentVolume;
        } else if (currentVolume < lockSnakeInitialVolume * 0.5f) {
            triggerSyncSnake(context); // 改为调用蛇功能
            stopSyncSnakeDetecting();
        }
    }
}
                                    // 新增同步大炮检测
else if (isSyncDetecting2 && rankId == specifiedRankId) {
    if (syncSelectedSubIndex2 < 0 || syncSelectedSubIndex2 >= groupList.size()) {
        if (allowReacquire) {
            reAcquireSyncBall2(context);
        } else {
            停止同步大炮检测();
        }
    } else {
        addCircle syncTarget = groupList.get(syncSelectedSubIndex2);
        float currentVolume = syncTarget.volume;
        if (锁定同步大炮球体初始体积 < 0) {
            锁定同步大炮球体初始体积 = currentVolume;
        } else if (currentVolume < 锁定同步大炮球体初始体积 * 0.5f) {
            触发同步大炮一分为二功能(context); // 改为调用蛇功能
            停止同步大炮检测();
        }
    }
}
// 新增追踪三角检测
else if (isSyncTriangleDetecting && rankId == specifiedRankId) {
    if (syncTriangleSelectedSubIndex < 0 || syncTriangleSelectedSubIndex >= groupList.size()) {
        if (allowReacquire) {
            reAcquireSyncTriangleBall(context);
        } else {
            停止追踪三角检测();
        }
    } else {
        addCircle syncTarget = groupList.get(syncTriangleSelectedSubIndex);
        float currentVolume = syncTarget.volume;
        if (锁定追踪三角球体初始体积 < 0) {
            锁定追踪三角球体初始体积 = currentVolume;
        } else if (currentVolume < 锁定追踪三角球体初始体积 * 0.5f) {
            触发追踪三角功能(context); // 改为调用三角功能
            停止追踪三角检测();
        }
    }
}
                                // 新增同步三角检测
else if (isSyncTriangleDetecting2 && rankId == specifiedRankId) {
    if (syncTriangleSelectedSubIndex2 < 0 || syncTriangleSelectedSubIndex2 >= groupList.size()) {
        if (allowReacquire) {
            reAcquireSyncTriangleBall2(context);
        } else {
            停止同步三角检测();
        }
    } else {
        addCircle syncTarget = groupList.get(syncTriangleSelectedSubIndex2);
        float currentVolume = syncTarget.volume;
        if (锁定同步三角球体初始体积 < 0) {
            锁定同步三角球体初始体积 = currentVolume;
        } else if (currentVolume < 锁定同步三角球体初始体积 * 0.5f) {
            触发同步三角功能(context); // 改为调用三角功能
            停止同步三角检测();
        }
    }
}
                            
                        }
            
                        // 绘制排名(只画该组 subIndex=0)
                        if (绘制排名 && !groupList.isEmpty()) {
    addCircle maxBall = groupList.get(0);
    float screenXa = ((maxBall.x - offsetX) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + parameters.排名x坐标;
    float screenYa = ((maxBall.y - offsetY) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + parameters.排名y坐标;
    float drawRadius = maxBall.radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例);
    
    // 计算文字显示位置：球体正上方的中间位置
    float textPositionY = screenYa - (drawRadius / 2f); // 在球体中心和边缘的中间位置

    // 根据球体半径调整文字大小
    float textSize = drawRadius * 0.5f; // 将文字大小与球体半径挂钩，例如设置为半径的 50%
    if (textSize < 10f) {
        textSize = 10f; // 设置最小文字大小，避免文字太小无法看清
     } else if (textSize > parameters.排名最大显示) {
        textSize = parameters.排名最大显示; // 设置最大文字大小，避免文字太大溢出
    }

        // 调整文字绘制的位置
    float textWidth = getTextWidth(maxBall.rankId, textSize); // 计算文字的宽度
    float textStartX = screenXa - (textWidth / 2f); // 将文字的起点设置为中心偏移半个宽度

    drawView.addTextWithFormatting(
            maxBall.rankId,
            textStartX, // 使用计算后的居中起点
            textPositionY, // 在球体正上方的中间位置
            Color.WHITE,
            textSize,
            5,
            1.5f
    );
}



                    }

                                      // 若同步开启，绘制选中的球(蓝色圆)
if (isSyncCircleVisible && rankToPlayers.containsKey(specifiedRankId)) {
    List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
    
    // 普通同步显示蓝色
    if (isSyncDetecting && syncSelectedSubIndex >= 0 && syncSelectedSubIndex < syncGroup.size()) {
        addCircle lockBall = syncGroup.get(syncSelectedSubIndex);
        float screenXa = ((lockBall.x - offsetX) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startX;
        float screenYa = ((lockBall.y - offsetY) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startY;
        float drawRadius = lockBall.radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例);
        drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#0033FF"), 6f);
    }
                             
    // 步显示蓝色
    if (isSyncDetecting2 && syncSelectedSubIndex2 >= 0 && syncSelectedSubIndex2 < syncGroup.size()) {
        addCircle lockBall = syncGroup.get(syncSelectedSubIndex2);
        float screenXa = ((lockBall.x - offsetX) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startX;
        float screenYa = ((lockBall.y - offsetY) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startY;
        float drawRadius = lockBall.radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例);
        drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#33CCFF"), 6f);
    }
    
    
    // 新增追踪三角显示绿色
    if (isSyncTriangleDetecting && syncTriangleSelectedSubIndex >= 0 && syncTriangleSelectedSubIndex < syncGroup.size()) {
        addCircle lockBall = syncGroup.get(syncTriangleSelectedSubIndex);
        float screenXa = ((lockBall.x - offsetX) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startX;
        float screenYa = ((lockBall.y - offsetY) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startY;
        float drawRadius = lockBall.radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例);
        drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#FF6699"), 6f); // 修改颜色为绿色
    
}
                                // 新增同步三角显示绿色
    if (isSyncTriangleDetecting2 && syncTriangleSelectedSubIndex2 >= 0 && syncTriangleSelectedSubIndex2 < syncGroup.size()) {
        addCircle lockBall = syncGroup.get(syncTriangleSelectedSubIndex2);
        float screenXa = ((lockBall.x - offsetX) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startX;
        float screenYa = ((lockBall.y - offsetY) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startY;
        float drawRadius = lockBall.radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例);
        drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#66FF99"), 6f); // 修改颜色为绿色
    
}
// 新增同步蛇手显示红色
if (isSyncSnakeDetecting && syncSnakeSelectedSubIndex >= 0 && syncSnakeSelectedSubIndex < syncGroup.size()) {
        addCircle lockBall = syncGroup.get(syncTriangleSelectedSubIndex);
    float screenXa = ((lockBall.x - offsetX) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startX;
        float screenYa = ((lockBall.y - offsetY) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startY;
        float drawRadius = lockBall.radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例);
    drawView.addCircle(screenXa, screenYa, drawRadius, Color.parseColor("#FF3300"), 6f); // 修改颜色为红色
}
              }                  
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this, 刷新延时);
            }
        };
        handler.post(drawRunnable);

if (卡点 == null) {
        卡点 = buttonCreator.createRoundButton(
                "绘制\n卡点",
                150,
                Color.parseColor("#50FFFF33"),
                100, 200,
                new RoundButtonCreator.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        downX = 卡点.getX();
                        downY = 卡点.getY();
                        
                        }

                    @Override
                    public void onButtonUp() {

                        isDetecting = !isDetecting;

                        if (isDetecting) {
                            cardSelectedRank = -1;
                            cardSelectedSubIndex = -1;
                            锁定球体初始体积 = -1;

                            float centerX = parameters.分辨率x / 2f;
                            float centerY = parameters.分辨率y / 2f;
                            float offsetX = memory.readFloat(a3 - 100);
                            float offsetY = 200 - memory.readFloat(a3 - 96);

                            outerLoop:
                            for (Map.Entry<Integer, List<addCircle>> entry : rankToPlayers.entrySet()) {
                                int rankId = entry.getKey();
                                List<addCircle> groupList = entry.getValue();
                                if (groupList == null) continue;

                                for (addCircle entity : groupList) {
                                    float drawX = ((entity.x - offsetX)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerX;
                                    float drawY = ((entity.y - offsetY)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerY;
                                    float drawRadius = entity.radius 
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例);

                                    float dist = (float)Math.sqrt(
                                            Math.pow(downX - drawX, 2) 
                                            + Math.pow(downY - drawY, 2)
                                    );
                                    if (dist <= drawRadius) {
                                        cardSelectedRank = rankId;
                                        cardSelectedSubIndex = entity.subIndex;
                                        isSelectedCircleVisible = true;
                                        刷新延时=1;
                                        break outerLoop;
                                    }
                                }
                            }
                        } else {
                            刷新延时=8;
                            停止检测并恢复状态();
                        }
                    }

                    @Override
                    public void onButtonMove(int x, int y) {
                        
                    }
                }
        );
        //卡点.setVisibility(View.GONE);
    }

            if (旋转卡点 == null) {
        旋转卡点 = buttonCreator.createRoundButton(
                "旋转\n卡点",
                150,
                Color.parseColor("#50FFA07A"),
                100, 200,
                new RoundButtonCreator.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        downX = 旋转卡点.getX();
                        downY = 旋转卡点.getY();
                        
                    }

                    @Override
                    public void onButtonUp() {
                        
                        isDetecting2 = !isDetecting2;
                        
                        if (isDetecting2) {
                            cardSelectedRank2 = -1;
                            cardSelectedSubIndex2 = -1;
                            锁定旋转卡点初始体积 = -1;

                            float centerX = parameters.分辨率x / 2f;
                            float centerY = parameters.分辨率y / 2f;
                            float offsetX = memory.readFloat(a3 - 100);
                            float offsetY = 200 - memory.readFloat(a3 - 96);

                            outerLoop:
                            for (Map.Entry<Integer, List<addCircle>> entry : rankToPlayers.entrySet()) {
                                int rankId = entry.getKey();
                                List<addCircle> groupList = entry.getValue();
                                if (groupList == null) continue;

                                for (addCircle entity : groupList) {
                                    float drawX = ((entity.x - offsetX)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerX;
                                    float drawY = ((entity.y - offsetY)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerY;
                                    float drawRadius = entity.radius 
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例);

                                    float dist = (float)Math.sqrt(
                                            Math.pow(downX - drawX, 2) 
                                            + Math.pow(downY - drawY, 2)
                                    );
                                    if (dist <= drawRadius) {
                                        cardSelectedRank2 = rankId;
                                        cardSelectedSubIndex2 = entity.subIndex;
                                        isSelectedCircleVisible = true;
                                        刷新延时=1;
                                        break outerLoop;
                                    }
                                }
                            }
                        } else {
                            刷新延时=8;
                            停止旋转卡点检测();
                        }
                    }

                    @Override
                    public void onButtonMove(int x, int y) {
                        
                    }
                }
        );
        //旋转卡点.setVisibility(View.GONE);
    }

                        if (蛇手卡点 == null) {
        蛇手卡点 = buttonCreator.createRoundButton(
                "蛇手\n卡点",
                150,
                Color.parseColor("#50FF4500"),
                100, 200,
                new RoundButtonCreator.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
        downX = 蛇手卡点.getX();
                            downY = 蛇手卡点.getY();
                    }

                    @Override
                    public void onButtonUp() {
                        
                        isDetecting3 = !isDetecting3;
                        

                        if (isDetecting3) {
                            cardSelectedRank3 = -1;
                            cardSelectedSubIndex3 = -1;
                            锁定蛇手卡点初始体积 = -1;

                            float centerX = parameters.分辨率x / 2f;
                            float centerY = parameters.分辨率y / 2f;
                            float offsetX = memory.readFloat(a3 - 100);
                            float offsetY = 200 - memory.readFloat(a3 - 96);

                            outerLoop:
                            for (Map.Entry<Integer, List<addCircle>> entry : rankToPlayers.entrySet()) {
                                int rankId = entry.getKey();
                                List<addCircle> groupList = entry.getValue();
                                if (groupList == null) continue;

                                for (addCircle entity : groupList) {
                                    float drawX = ((entity.x - offsetX)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerX;
                                    float drawY = ((entity.y - offsetY)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerY;
                                    float drawRadius = entity.radius 
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例);

                                    float dist = (float)Math.sqrt(
                                            Math.pow(downX - drawX, 2) 
                                            + Math.pow(downY - drawY, 2)
                                    );
                                    if (dist <= drawRadius) {
                                        cardSelectedRank3 = rankId;
                                        cardSelectedSubIndex3 = entity.subIndex;
                                        isSelectedCircleVisible = true;
                                        刷新延时=1;
                                        break outerLoop;
                                    }
                                }
                            }
                        } else {
                            刷新延时=8;
                            停止蛇手卡点检测();
                        }
                    }

                    @Override
                    public void onButtonMove(int x, int y) {
                        
                    }
                }
        );
        蛇手卡点.setVisibility(View.GONE);
    }

    // --- 同步按钮 ---
     if (追踪大炮 == null) {
        追踪大炮 = buttonCreator.createRoundButton(
                "追踪\n大炮",
                150,
                Color.parseColor("#500033FF"),
                100, 200,
                new RoundButtonCreator.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        downX = 追踪大炮.getX();
                            downY = 追踪大炮.getY();
                        
                    }

                    @Override
                    public void onButtonUp() {
                        
                        // 点击“同步”按钮后逻辑：
                        if (!isSyncDetecting) {
                            isSyncDetecting = true;
                            isSyncCircleVisible = true;
                            syncSelectedSubIndex = -1;
                            锁定同步球体初始体积 = -1;

                            // 选中目标组 => subIndex=0 (该组最大球)
                            if (rankToPlayers.containsKey(specifiedRankId)) {
                                List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
                                if (syncGroup != null && !syncGroup.isEmpty()) {
                                    syncSelectedSubIndex = 0;
                                } else {
                                    isSyncDetecting = false;
                                    isSyncCircleVisible = false;
                                }
                            } else {
                                isSyncDetecting = false;
                                isSyncCircleVisible = false;
                            }
                        } else {
                            停止检测并恢复状态();
                        }
                    }

                    @Override
                    public void onButtonMove(int x, int y) {
                        
                    }
                }
        );
        追踪大炮.setVisibility(View.GONE);
    }
    // --- 同步按钮 ---
 if (同步大炮 == null) {
    同步大炮 = buttonCreator.createRoundButton(
        "同步\n大炮",
        150,
        Color.parseColor("#5033CCFF"), // 颜色可以根据需求调整
        200,
        200,
        new RoundButtonCreator.OnButtonTouchListener() {
            @Override
            public void onButtonDown() {
      downX = 同步大炮.getX();
                            downY = 同步大炮.getY();
            }

            @Override
            public void onButtonUp() {
                
               if (!isSyncDetecting2) {
                    isSyncDetecting2 = true;
                    isSyncCircleVisible = true;
                    syncSelectedSubIndex2 = -1;
                    锁定同步大炮球体初始体积 = -1;
                    
                    if (rankToPlayers.containsKey(specifiedRankId)) {
                        List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null && !syncGroup.isEmpty()) {
                            syncSelectedSubIndex2 = 0;
                        }
                    }
                } else {
                    停止同步大炮检测();
                }
            }

            @Override
            public void onButtonMove(int x, int y) {
                
            }
        }
    );
    同步大炮.setVisibility(View.GONE);
}

if (同步蛇手 == null) {
    同步蛇手 = buttonCreator.createRoundButton(
        "同步\n蛇手",
        150,
        Color.parseColor("#50FF3300"), // 颜色可以根据需求调整
        200,
        200,
        new RoundButtonCreator.OnButtonTouchListener() {
            @Override
            public void onButtonDown() {
  downX = 同步蛇手.getX();
                            downY = 同步蛇手.getY();
            }

            @Override
            public void onButtonUp() {
                

               if (!isSyncSnakeDetecting) {
                    isSyncSnakeDetecting = true;
                    isSyncCircleVisible = true;
                    syncSnakeSelectedSubIndex = -1;
                    lockSnakeInitialVolume = -1;
                    
                    if (rankToPlayers.containsKey(specifiedRankId)) {
                        List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null && !syncGroup.isEmpty()) {
                            syncTriangleSelectedSubIndex = 0;
                        }
                    }
                } else {
                    stopSyncSnakeDetecting();
                }
            }

            @Override
            public void onButtonMove(int x, int y) {
                
            }
        }
    );
    同步蛇手.setVisibility(View.GONE);
}
/*
    if (黑屏卡点 == null) {
        // 创建按钮
        黑屏卡点 = buttonCreator.createRoundButton(
                "黑屏卡点",
                150,
              Color.parseColor("#33FF66"),
                200,
                200,
                new RoundButtonCreator.OnButtonTouchListener() {

                    private int offsetX, offsetY; // 记录手指按下时的偏移

                    @Override
                    public void onButtonDown() {
                        RoundButtonCreator.isMovable = true;

                        // 记录手指按下时与按钮的偏移量
                        offsetX = (int) (黑屏卡点.getX() - savedInitialX);
                        offsetY = (int) (黑屏卡点.getY() - savedInitialY);
                    
            }

                    @Override
                    public void onButtonUp() {
                        RoundButtonCreator.isMovable = false;
                        memory.setValue(String.valueOf(parameters.黑屏值), a3-104, memory.TYPE_FLOAT);
                    

                        // 在新线程中运行捕获画面
                        new Thread(() -> {
                            捕获画面.startColorDetection(Color.BLACK, parameters.黑屏频率, (int) 黑屏卡点.getX(), (int) 黑屏卡点.getY(), 10, () -> {
                                // 匹配颜色后执行的功能
                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 5);
                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 5);

                                // 回到主线程更新UI
                                黑屏卡点.post(() -> {
                                    memory.setValue("800", a3-104, memory.TYPE_FLOAT);
                                 });
                            });
                        }).start();
                    }

                    @Override
                    public void onButtonMove(int x, int y) {
                        // 更新按钮位置时，使用手指实际的位置减去偏移量
                        int newX = x ;
                        int newY = y ;

                        黑屏卡点.setX(newX);
                        黑屏卡点.setY(newY);
                    }
                });

        
    } else {
        黑屏卡点.setVisibility(View.VISIBLE);

    
            }
        

        
        
                        if (同步蛇手 == null) {
    同步蛇手 = buttonCreator.createRoundButton(
        "同步蛇手",
        150,
        Color.parseColor("#FF3300"),
        200,
        200,
        new RoundButtonCreator.OnButtonTouchListener() {
            @Override
            public void onButtonDown() {
  
            }
    @Override
            public void onButtonUp() {
               
                       if (!isSyncSnakeDetecting) {
                    isSyncSnakeDetecting = true;
                    isSyncCircleVisible = true;
                    syncSnakeSelectedSubIndex = -1;
                    lockSnakeInitialVolume = -1;
                    
                    if (rankToPlayers.containsKey(specifiedRankId)) {
                        List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null && !syncGroup.isEmpty()) {
                            syncTriangleSelectedSubIndex = 0;
              
                        }
                    }
                } else {
                    stopSyncSnakeDetecting();
                }
            }

            @Override
            public void onButtonMove(int x, int y) {
                
            }
        }
    );
    同步蛇手.setVisibility(View.GONE);
}
*/
if (追踪三角 == null) {
    追踪三角 = buttonCreator.createRoundButton(
        "追踪\n三角",
        150,
              Color.parseColor("#50FF6699"),
                200,
                200,
                new RoundButtonCreator.OnButtonTouchListener() {
            @Override
            public void onButtonDown() {
        downX = 追踪三角.getX();
                            downY = 追踪三角.getY();
            }

            @Override
            public void onButtonUp() {
                
                
                // 独立追踪三角检测逻辑
                if (!isSyncTriangleDetecting) {
                    isSyncTriangleDetecting = true;
                    isSyncCircleVisible = true;
                    syncTriangleSelectedSubIndex = -1;
                    锁定追踪三角球体初始体积 = -1;
                    
                    if (rankToPlayers.containsKey(specifiedRankId)) {
                        List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null && !syncGroup.isEmpty()) {
                            syncTriangleSelectedSubIndex = 0;
                        }
                    }
                } else {
                    停止追踪三角检测();
                }
            }

            @Override
            public void onButtonMove(int x, int y) {
                
            }
        }
    );
    追踪三角.setVisibility(View.GONE);
}
    if (同步三角 == null) {
    同步三角 = buttonCreator.createRoundButton(
        "同步\n三角",
        150,
              Color.parseColor("#5066FF99"),
                200,
                200,
                new RoundButtonCreator.OnButtonTouchListener() {
            @Override
            public void onButtonDown() {
         downX = 同步三角.getX();
                            downY = 同步三角.getY();
            }

            @Override
            public void onButtonUp() {
               
                
                // 独立追踪三角检测逻辑
                if (!isSyncTriangleDetecting2) {
                    isSyncTriangleDetecting2 = true;
                    isSyncCircleVisible = true;
                    syncTriangleSelectedSubIndex2 = -1;
                    锁定同步三角球体初始体积 = -1;
                    
                    if (rankToPlayers.containsKey(specifiedRankId)) {
                        List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null && !syncGroup.isEmpty()) {
                            syncTriangleSelectedSubIndex2 = 0;
                        }
                    }
                } else {
                    停止同步三角检测();
                }
            }

            @Override
            public void onButtonMove(int x, int y) {
                
            }
        }
    );
    同步三角.setVisibility(View.GONE);
}

    // 根据开关显示/隐藏按钮
    if (showCardButton) {
        卡点.setVisibility(View.VISIBLE);
    } else {
        卡点.setVisibility(View.GONE);
    }
            if (showCardButton) {
        旋转卡点.setVisibility(View.VISIBLE);
    } else {
        旋转卡点.setVisibility(View.GONE);
    }
             if (showCardButton) {
        蛇手卡点.setVisibility(View.VISIBLE);
    } else {
        蛇手卡点.setVisibility(View.GONE);
    }
    if (showRankButton) {
        追踪大炮.setVisibility(View.VISIBLE);
    } else {
        追踪大炮.setVisibility(View.GONE);
    }
       if (showRankButton) {
        追踪三角.setVisibility(View.VISIBLE);
    } else {
        追踪三角.setVisibility(View.GONE);
    }
            if (showRankButton) {
        同步三角.setVisibility(View.VISIBLE);
    } else {
        同步三角.setVisibility(View.GONE);
    }
             if (showRankButton) {
        同步蛇手.setVisibility(View.VISIBLE);
    } else {
        同步蛇手.setVisibility(View.GONE);
    }
          if (showRankButton) {
        同步大炮.setVisibility(View.VISIBLE);
    } else {
        同步大炮.setVisibility(View.GONE);
    }
    /*
   if (showCardButton) {
        黑屏卡点.setVisibility(View.VISIBLE);
    } else {
        黑屏卡点.setVisibility(View.GONE);
    }
    */
    }
private float getTextWidth(int rankId, float textSize) {
    Paint paint = new Paint();
    paint.setTextSize(textSize);
    return paint.measureText(String.valueOf(rankId));
        }
        

    /************************************************
     * 7) 停止检测并恢复状态
     ************************************************/
    private void 停止检测并恢复状态() {
        isDetecting = false;
        cardSelectedRank = -1;
        cardSelectedSubIndex = -1;
        锁定球体初始体积 = -1;
        isSelectedCircleVisible = false;

        isSyncDetecting = false;
        isSyncCircleVisible = false;
        syncSelectedSubIndex = -1;
        锁定同步球体初始体积 = -1;
        刷新延时=8;
   


        RoundButtonCreator.isMovable = false;
    }
        private void 停止旋转卡点检测() {
        isDetecting2 = false;
        cardSelectedRank2 = -1;
        cardSelectedSubIndex2 = -1;
        锁定旋转卡点初始体积 = -1;
        isSelectedCircleVisible = false;

 RoundButtonCreator.isMovable = false;
    }
             private void 停止蛇手卡点检测() {
        isDetecting3 = false;
        cardSelectedRank3 = -1;
        cardSelectedSubIndex3 = -1;
        锁定蛇手卡点初始体积 = -1;
        isSelectedCircleVisible = false;

 RoundButtonCreator.isMovable = false;
    }
       private void 停止追踪三角检测() {
    isSyncTriangleDetecting = false;
    isSyncCircleVisible = false;
    syncTriangleSelectedSubIndex = -1;
    锁定追踪三角球体初始体积 = -1;
    刷新延时 = 8;
}
          private void 停止同步三角检测() {
    isSyncTriangleDetecting2 = false;
    isSyncCircleVisible = false;
    syncTriangleSelectedSubIndex2 = -1;
    锁定同步三角球体初始体积 = -1;
    刷新延时 = 8;
        }
        private void 停止同步大炮检测() {
    isSyncDetecting2 = false;
    isSyncCircleVisible = false;
    syncSelectedSubIndex2 = -1;
    锁定同步大炮球体初始体积 = -1;
    刷新延时 = 8;
}
private void stopSyncSnakeDetecting() {
    isSyncSnakeDetecting = false;
    syncSnakeSelectedSubIndex = -1;
    lockSnakeInitialVolume = -1;
    snakeGroup.clear();
}
        private void reAcquireSyncSnakeBall(Context context) {
    if (!isSyncSnakeDetecting) return;
    if (rankToPlayers.containsKey(specifiedRankId)) {
        snakeGroup = rankToPlayers.get(specifiedRankId);
        if (snakeGroup != null && !snakeGroup.isEmpty()) {
            syncSnakeSelectedSubIndex = 0;
            lockSnakeInitialVolume = -1;
        } else {
            stopSyncSnakeDetecting();
        }
    } else {
        stopSyncSnakeDetecting();
    }
}
        private void reAcquireSyncBall(Context context) {
        if (!isSyncDetecting) return;
        if (rankToPlayers.containsKey(specifiedRankId)) {
            List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
            if (syncGroup != null && !syncGroup.isEmpty()) {
                syncSelectedSubIndex = 0;
                锁定同步球体初始体积 = -1;
            } else {
                停止检测并恢复状态();
            }
        } else {
            停止检测并恢复状态();
        }
    }
   private void reAcquireSyncBall2(Context context) {
        if (!isSyncDetecting2) return;
        if (rankToPlayers.containsKey(specifiedRankId)) {
            List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
            if (syncGroup != null && !syncGroup.isEmpty()) {
                syncSelectedSubIndex2 = 0;
                锁定同步大炮球体初始体积 = -1;
            } else {
                停止同步大炮检测();
            }
        } else {
            停止同步大炮检测();
        }
    }
private void reAcquireSyncTriangleBall2(Context context) {
    if (rankToPlayers.containsKey(specifiedRankId)) {
        List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
        if (syncGroup != null && !syncGroup.isEmpty()) {
            syncTriangleSelectedSubIndex2 = 0;
        }
    }

}
private void reAcquireSyncTriangleBall(Context context) {
    if (rankToPlayers.containsKey(specifiedRankId)) {
        List<addCircle> syncGroup = rankToPlayers.get(specifiedRankId);
        if (syncGroup != null && !syncGroup.isEmpty()) {
            syncTriangleSelectedSubIndex = 0;
        }
    }
}


    /************************************************
     * 8) 卡点分裂功能(原逻辑)
     ************************************************/
    private void 触发一分为二功能(Context context) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            try {
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }
        if (parameters.卡点分身次数 > 0) {
                Thread.sleep(parameters.卡点前摇);

                // 角度滑动
                HOOK.TouchEventHandler.角度滑动(
                        simpleButtonController.摇杆x,
                        simpleButtonController.摇杆y,
                        initialAngle,
                        parameters.卡点第二分角度, 
                        parameters.卡点滑动长度,               // 偏移长度=0 (直线滑动)
                    parameters.卡点滑动时长,             // 滑动时长
                    触摸id, w1
            );
                // 分裂次数
                for (int i = 0; i < parameters.卡点分身次数; i++) {
                    HOOK.TouchEventHandler.按下(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    HOOK.TouchEventHandler.抬起(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    Thread.sleep(parameters.卡点分身间隔);
                }
        }
                RoundButtonCreator.isMovable = false;
                isSelectedCircleVisible = false;
                XposedBridge.log("球体分裂: 卡点球体已一分为二！");
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
            }
        }).start();
    }
private void 执行旋转卡点(Context context) {
    if (isRunning) return;
    isRunning = true;

    new Thread(() -> {
        try {
            // 初始化参数
            int 当前角度 = parameters.旋转卡点初始角度;
            int 角度递减 = parameters.旋转卡点角度递减;
            int 滑动长度 = parameters.旋转卡点滑动长度;
            int 滑动时长 = parameters.旋转卡点滑动时长;
            int 分身次数 = parameters.旋转卡点分身次数;
            int 分身间隔 = parameters.旋转卡点分身间隔;
            int 前摇 = parameters.旋转卡点前摇;
            int 总旋转次数 = parameters.旋转卡点次数; // 总旋转次数
            int 已旋转次数 = 0; // 已旋转次数计数

            if (initialAngle == null) {
                initialAngle = joystickAngle;
            }

            // 前摇延时
            Thread.sleep(前摇);

            // 先执行分身操作
         

            // 开始旋转和分身
            while (已旋转次数 < 总旋转次数) { // 控制旋转次数
                // 计算新的角度
                当前角度 -= 角度递减;
                if (当前角度 < 0) {
                    当前角度 += 360;
                }

                // 执行滑动操作
                HOOK.TouchEventHandler.角度滑动(
                    simpleButtonController.摇杆x,
                    simpleButtonController.摇杆y,
                    initialAngle,
                    当前角度,
                    滑动长度,
                    滑动时长,
                    触摸id,
                    w1
                );
                Thread.sleep(分身间隔);

                // 点击分身
                HOOK.TouchEventHandler.按下(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
                );
                HOOK.TouchEventHandler.抬起(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
                );
                Thread.sleep(分身间隔);

                已旋转次数++; // 增加已旋转次数
            }

            // 旋转完成后，点击摇杆以实现静止
            // 这里假设摇杆的按下和抬起动作可以停止旋转
            HOOK.TouchEventHandler.按下(
                simpleButtonController.摇杆x,
                simpleButtonController.摇杆y,
                触摸id
            );
            Thread.sleep(8); // 短暂延时，确保摇杆被按下
            HOOK.TouchEventHandler.抬起(
                simpleButtonController.摇杆x,
                simpleButtonController.摇杆y,
                触摸id
            );

            // 最终复位
            new Handler(Looper.getMainLooper()).post(() -> {
                initialAngle = null;
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
        }
    }).start();
}
private void 触发蛇手卡点功能(Context context) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            try {
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }
                          
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手卡点滑动1角度, parameters.蛇手卡点滑动1长度, parameters.蛇手卡点滑动时长, 触摸id,w1);
                Thread.sleep(parameters.蛇手卡点分身延时);
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.蛇手卡点滑动延时); // 延时
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手卡点滑动2角度, parameters.蛇手卡点滑动2长度, parameters.蛇手卡点滑动时长, 触摸id,w1);
                // 分裂次数
                for (int i = 0; i < parameters.蛇手卡点分身次数; i++) {
                    HOOK.TouchEventHandler.按下(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    HOOK.TouchEventHandler.抬起(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    Thread.sleep(parameters.蛇手卡点分身间隔);
                }
                RoundButtonCreator.isMovable = false;
                isSelectedCircleVisible = false;
                XposedBridge.log("球体分裂: 卡点球体已一分为二！");
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
            }
        }).start();
    }
private void 触发同步一分为二功能(Context context) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            try {
                 // ============ 1) 获取指定rankId最大球 & 我方最大球 ============
            List<addCircle> friendGroup = rankToPlayers.get(specifiedRankId);
            if (friendGroup == null || friendGroup.isEmpty()) {
                XposedBridge.log("追踪大炮: 指定Rank组为空, 无法获取目标球");
                return;
            }

            // 获取我方最大球
            addCircle myLargestBall = getMyLargestBall(); // 确保该方法返回有效值
            if (myLargestBall == null) {
                XposedBridge.log("追踪大炮: 我方最大球不存在");
                return;
            }

            // 记录分裂前的最大球体
            addCircle targetMaxBeforeSplit = friendGroup.get(0);

            // ============ 2) 分裂后获取排名ID的新最大球体 ============
                        rankToPlayers.clear();
            获取玩家结构体(context, "xx");
            Thread.sleep(50);

            List<addCircle> updatedFriendGroup = rankToPlayers.get(specifiedRankId);
            if (updatedFriendGroup == null || updatedFriendGroup.isEmpty()) {
                XposedBridge.log("分裂后: 排名组为空");
                return;
            }

            // ============ 3) 找到与我方最大球距离最远的球体 ============
            addCircle farthestBall = null;
            float maxDistance = 0;

            for (addCircle entity : updatedFriendGroup) {
                float dx = entity.x - myLargestBall.x;
                float dy = entity.y - myLargestBall.y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance > maxDistance) {
                    maxDistance = distance;
                    farthestBall = entity;
                }
            }

            if (farthestBall == null) {
                XposedBridge.log("分裂后未找到与我方最大球距离最远的球体");
                return;
            }

            // ============ 4) 计算角度: 我方最大球与最远球体之间的角度 ============
            float dx = farthestBall.x - myLargestBall.x;
            float dy = farthestBall.y - myLargestBall.y;
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
            if (angle < 0) angle += 360f; // 保证角度是正值

            XposedBridge.log("【追踪大炮】最远球体 vs 我方最大球体 angle = " + angle + "°");

            
            initialAngle = 360f - angle;

                // 角度滑动
                HOOK.TouchEventHandler.角度滑动(
                        simpleButtonController.摇杆x,
                        simpleButtonController.摇杆y,
                        initialAngle,
                        0,
                         parameters.同步滑动长度,               // 偏移长度=0 (直线滑动)
                    parameters.同步滑动时长,             // 滑动时长
                    触摸id, w1
            );
                // 分裂次数
                for (int i = 0; i < parameters.同步分身次数; i++) {
                    HOOK.TouchEventHandler.按下(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    HOOK.TouchEventHandler.抬起(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    Thread.sleep(parameters.同步分身间隔);
                }
                RoundButtonCreator.isMovable = false;
                isSelectedCircleVisible = false;
                XposedBridge.log("球体分裂: 同步球体已一分为二！");
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
            }
        }).start();
    }
                private void 触发同步大炮一分为二功能(Context context) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            try {
            if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }
                Thread.sleep(parameters.同步前摇);
                // 分裂次数
                for (int i = 0; i < parameters.同步分身次数; i++) {
                    HOOK.TouchEventHandler.按下(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    HOOK.TouchEventHandler.抬起(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    Thread.sleep(parameters.同步分身间隔);
                }
                RoundButtonCreator.isMovable = false;
                isSelectedCircleVisible = false;
                XposedBridge.log("球体分裂: 同步球体已一分为二！");
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
            }
        }).start();
    }

           private void 触发追踪三角功能(Context context) {
    if (isRunning) return;
    isRunning = true;

    new Thread(() -> {
        try {
            // ============ 1) 获取指定rankId最大球 & 我方最大球 ============
            List<addCircle> friendGroup = rankToPlayers.get(specifiedRankId);
            if (friendGroup == null || friendGroup.isEmpty()) {
                XposedBridge.log("追踪三角: 指定Rank组为空, 无法获取目标球");
                return;
            }

            // 获取我方最大球
            addCircle myLargestBall = getMyLargestBall(); // 确保该方法返回有效值
            if (myLargestBall == null) {
                XposedBridge.log("追踪三角: 我方最大球不存在");
                return;
            }

            // 记录分裂前的最大球体
            addCircle targetMaxBeforeSplit = friendGroup.get(0);

            // ============ 2) 分裂后获取排名ID的新最大球体 ============
                        rankToPlayers.clear();
            获取玩家结构体(context, "xx");
            Thread.sleep(50);

            List<addCircle> updatedFriendGroup = rankToPlayers.get(specifiedRankId);
            if (updatedFriendGroup == null || updatedFriendGroup.isEmpty()) {
                XposedBridge.log("分裂后: 排名组为空");
                return;
            }

            // ============ 3) 找到与我方最大球距离最远的球体 ============
            addCircle farthestBall = null;
            float maxDistance = 0;

            for (addCircle entity : updatedFriendGroup) {
                float dx = entity.x - myLargestBall.x;
                float dy = entity.y - myLargestBall.y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance > maxDistance) {
                    maxDistance = distance;
                    farthestBall = entity;
                }
            }

            if (farthestBall == null) {
                XposedBridge.log("分裂后未找到与我方最大球距离最远的球体");
                return;
            }

            // ============ 4) 计算角度: 我方最大球与最远球体之间的角度 ============
            float dx = farthestBall.x - myLargestBall.x;
            float dy = farthestBall.y - myLargestBall.y;
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
            if (angle < 0) angle += 360f; // 保证角度是正值

            XposedBridge.log("【追踪三角】最远球体 vs 我方最大球体 angle = " + angle + "°");

            
            initialAngle = 360f - angle;
                  // 第一次滑动
            HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角1滑动角度, parameters.三角1滑动长度, parameters.三角1滑动时长, 触摸id,w1);
                Thread.sleep(parameters.三角1点击延时); 

                // 按下和抬起
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.三角2滑动延时); 

                // 第二次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角2滑动角度, parameters.三角2滑动长度, parameters.三角2滑动时长, 触摸id,w1);
                Thread.sleep(parameters.三角2点击延时);

                // 按下和抬起
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.三角3滑动延时);

                // 第三次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角3滑动角度, parameters.三角3滑动长度, parameters.三角3滑动时长, 触摸id,w1);
                    
                // 循环点击
                for (int i = 0; i < parameters.三角分身次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.三角分身延时);
            }

            
            

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
            new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);
        }
    }).start();
}
 
            
    /************************************************
     * 9) 「同步」分裂功能 
     *    - 分裂前后计算 角度1(旧球相对我方最大球), 角度2(新球相对我方最大球)
     ************************************************/
    /**************************************************************
 * 思路：
 * 1) 点击“同步”后：
 *    a) 首先获取「指定排名ID」下的最大球 与 我方“最大球”之间的角度 => angle1，
 *       然后执行角度滑动(angle1, 0)，再点击一次分身 => 我方球就向该方向分裂；
 *
 * 2) 分裂后，我方会产生“新分裂球”。现在再获取「指定排名ID」下最新的最大球，
 *    与“我方新分裂的球”之间的角度 => angle2，
 *    然后执行角度滑动(angle2, 0)，再连续点击多次分身(比如10次)，
 *    就会把我方球体朝此角度的方向分裂并“合”到目标。
 *
 * 3) 注意点：
 *    - 如何识别“我方新分裂球”？常见方式：比较分裂前后“地址/uniqueId”差异，
 *      取新的那颗(通常半径较小)即为“新分裂球”。
 *    - 如果游戏机制是“最大的那颗仍算自己本体，小的才是新分裂球”，
 *      也可以根据实际需求来确认由哪颗球去做“对准移动”。
 *    - 以下代码仅作示例；你需结合自己项目中对“内存解析”、“分裂后球体识别”等逻辑做适配。

 **************************************************************/

    /************************************************
     * 10) 重新获取最大球(若有需要)
     ************************************************/
 
    /************************************************
     * 11) 获取我方最大球(示例)
     ************************************************/
    private addCircle getMyLargestBall() {
        if (!rankToPlayers.containsKey(myRankId)) {
            return null;
        }
        List<addCircle> myGroup = rankToPlayers.get(myRankId);
        if (myGroup == null || myGroup.isEmpty()) {
            return null;
        }
        return myGroup.get(0);
    }



  private void 触发同步三角功能(Context context) {
    if (isRunning) return;
    isRunning = true;

    new Thread(() -> {
        try {
              if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }            // 第一次滑动
           HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角1滑动角度, parameters.三角1滑动长度, parameters.三角1滑动时长, 触摸id,w1);
                Thread.sleep(parameters.三角1点击延时); 

                // 按下和抬起
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.三角2滑动延时); 

                // 第二次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角2滑动角度, parameters.三角2滑动长度, parameters.三角2滑动时长, 触摸id,w1);
                Thread.sleep(parameters.三角2点击延时);

                // 按下和抬起
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.三角3滑动延时);

                // 第三次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角3滑动角度, parameters.三角3滑动长度, parameters.三角3滑动时长, 触摸id,w1);
                    
                // 循环点击
                for (int i = 0; i < parameters.三角分身次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.三角分身延时);
            }

            
            

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
            new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);
        }
    }).start();
}
private void triggerSyncSnake(Context context) {
    if (isRunning) return;
    isRunning = true;

    new Thread(() -> {
        try {
            if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }
// 第一次滑动
     // 执行蛇手操作
                             // 执行蛇手1操作
                           HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手1滑动1角度, parameters.蛇手1滑动1长度, parameters.蛇手1滑动1时长, 触摸id,w1);
                Thread.sleep(parameters.蛇手1分身延时1); 

                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.蛇手1滑动延时2);

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手1滑动2角度, parameters.蛇手1滑动2长度, parameters.蛇手1滑动2时长, 触摸id,w1);
                Thread.sleep(parameters.蛇手1分身延时2);
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.蛇手1滑动延时3); // 延时
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手1滑动3角度, parameters.蛇手1滑动3长度, parameters.蛇手1滑动3时长, 触摸id,w1);

                // 循环按下和抬起
                for (int i = 0; i < parameters.蛇手1分身点击次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.蛇手1分身点击延时);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
            new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);
        }
    }).start();
}



    

    // Page2 的布局创建方法
    private LinearLayout createPage2Layout(Context context) {
    LinearLayout page2Layout = new LinearLayout(context);
    page2Layout.setOrientation(VERTICAL);
    
    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    LinearLayout switchContainer = new LinearLayout(context);
    switchContainer.setOrientation(VERTICAL);

    // 设置左右相同的边距
    LayoutParams switchContainerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    int margin = dpToPx(16);
    switchContainerParams.setMargins(margin, 0, margin, 0);
    switchContainer.setLayoutParams(switchContainerParams);

    // 自定义开关名称数组
    String[] switchNames = {"滑块显隐", "调键(位置/颜色/大小)", "吐球", "分身", "冲球", "四分", "三角", "蛇手", "侧合", "后仰", "旋转",/*  "卡点(旋转)", "卡点(蛇手)", "追踪(大炮)",*/ "同步炮", /*"追踪(三角)", */"同步△", "同步蛇", "绘制➩卡点", "内存➩卡点"};

    // 动态创建开关并添加到容器中
    for (int i = 0; i < switchNames.length; i++) {
            final String switchName = switchNames[i];
            Switch switchButton = new Switch(context);
            switchButton.setText(switchName);
            switchButton.setTag(switchName); // 设置Tag
            switchButton.setTextColor(Color.parseColor("#ff000000"));
            applySwitchStyle(switchButton);

            // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        if (isChecked) {
                            
                            switch (switchName) {
                            
// 显示滑条（对应true）
case "滑块显隐":
    滑块.setSliderVisible(true);
    
    break;


                                case "调键(位置/颜色/大小)":
                                    滑块.setAdjustable(true);  // 让滑块可以移动
                                   RoundButtonCreator.setDebugMode(true);
                                    simpleButtonController.updateButtonVisibility(true);
                                    break;
                                    
                                case "吐球":
                                        if (吐球 == null) {
                                            吐球 = buttonCreator.createRoundButton(
                                                "吐球",
                                                dpToPx(52),
                                                0x50ff8bff,
                                                200,
                                                200,
                                                new RoundButtonCreator.OnButtonTouchListener() {
                                                    // 使用 AtomicBoolean 管理状态，无需额外锁
                                                    private final AtomicBoolean 按钮按下 = new AtomicBoolean(false);
                                                    // 定时调度器及任务句柄
                                                    private ScheduledExecutorService scheduler;
                                                    private ScheduledFuture<?> futureTask;
                                                    private ScheduledFuture<?> releaseTask;
                                    
                                                    @Override
                                                    public synchronized void onButtonDown() {
                                                        final long period = 1000L / parameters.吐球_每秒次数;
                                                        final long 按住时长 = period - parameters.吐球_按住时长;
                                                        
                                                        if (按钮按下.compareAndSet(false, true)) {
                                                            // 创建单线程调度器
                                                            scheduler = Executors.newSingleThreadScheduledExecutor();
                                                            futureTask = scheduler.scheduleAtFixedRate(() -> {
                                                                if (!按钮按下.get()) return;
                                                                try {
                                                                    // 执行按下操作
                                                                    HOOK.TouchEventHandler.按下(simpleButtonController.吐球x, simpleButtonController.吐球y, 吐球id);
                                                                } catch (Exception e) {
                                                                    // 异常处理，必要时记录日志
                                                                }
                                                                // 如果之前有延时任务正在等待，则取消它（防止重叠）
                                                                if (releaseTask != null && !releaseTask.isDone()) {
                                                                    releaseTask.cancel(true);
                                                                }
                                                                // 调度延时“抬起”任务
                                                                releaseTask = scheduler.schedule(() -> {
                                                                    try {
                                                                        HOOK.TouchEventHandler.抬起(simpleButtonController.吐球x, simpleButtonController.吐球y, 吐球id);
                                                                    } catch (Exception e) {
                                                                        // 异常处理
                                                                    }
                                                                }, 按住时长, TimeUnit.MILLISECONDS);
                                                            }, 0, period, TimeUnit.MILLISECONDS);
                                                        }
                                                    }
                                    
                                                    @Override
                                                    public synchronized void onButtonUp() {
                                                        if (按钮按下.compareAndSet(true, false)) {
                                                            // 取消周期性任务
                                                            if (futureTask != null) {
                                                                futureTask.cancel(true);
                                                                futureTask = null;
                                                            }
                                                            // 取消延时任务
                                                            if (releaseTask != null) {
                                                                releaseTask.cancel(true);
                                                                releaseTask = null;
                                                            }
                                                            // 关闭调度器
                                                            if (scheduler != null) {
                                                                scheduler.shutdownNow();
                                                                scheduler = null;
                                                            }
                                                            // 确保执行一次抬起操作
                                                            HOOK.TouchEventHandler.抬起(simpleButtonController.吐球x, simpleButtonController.吐球y, 吐球id);
                                                        }
                                                    }
                                    
                                                    @Override
                                                    public void onButtonMove(int x, int y) {
                                                        // 根据需要实现移动逻辑
                                                    }
                                                }
                                            );
                                        } else {
                                            吐球.setVisibility(View.VISIBLE);
                                        }
                                        break;
                                    
                                    case "分身":
                                    if (分身 == null) {
                                        分身 = buttonCreator.createRoundButton(
                                                        "分身",
                                                        dpToPx(52),
                                                        0x507A63FF,
                                                        400,
                                                        200,
                                                        new RoundButtonCreator.OnButtonTouchListener() {

                                                            boolean 按钮按下 = false; 
                                                            Thread 按钮线程 = null;
                                                                                                                                                               
                                                            @Override
                                                            public void onButtonDown() {
                                                                                                                            
                                                                int x = simpleButtonController.分身x;
                                                                int y = simpleButtonController.分身y;
                                                                long 抬起延时 = 0;
                                                                long 一组延时 = 0;
                                                                long 按下延时 = (1000 / parameters.分身_每秒次数) - (抬起延时 + 一组延时);
                                                                if (按钮按下 || 按钮线程 != null) return;
                                    
                                                                按钮按下 = true;
                                                                按钮线程 = new Thread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        try {
                                                                            for (int i = 0; 按钮按下; i++) {
                                                                                if (Thread.interrupted()) {
                                                                                    break; 
                                                                                }
                                    
                                                                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x,simpleButtonController.分身y, 点击id);
                                                                                Thread.sleep(按下延时); 
                                    
                                                                                if (Thread.interrupted()) {
                                                                                    break; 
                                                                                }
                                                                                                    
                                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x,simpleButtonController.分身y, 点击id);
                                                                                Thread.sleep(抬起延时); 
                                    
                                                                                if (Thread.interrupted()) {
                                                                                    break;
                                                                                }
                                    
                                                                                Thread.sleep(一组延时);
                                                                            }
                                                                        } catch (InterruptedException e) {
                                                                            
                                                                            e.printStackTrace();
                                                                        } finally {
                                                                            按钮线程 = null; 
                                                                        }
                                                                    }
                                                                });
                                                                按钮线程.start(); 
                                                            }
                                    
                                                            @Override
                                                            public void onButtonUp() {
                                                                
                                                                int x = simpleButtonController.分身x;
                                                                int y = simpleButtonController.分身y;
                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x,simpleButtonController.分身y, 点击id);
                                                                按钮按下 = false; 
                                                                if (按钮线程 != null) {
                                                                    按钮线程.interrupt();
                                                                }
                                                            }
                                                            
                                                            @Override
                                                            public void onButtonMove(int x, int y) {
                                                                if (按钮移动) {
                                                                    
                                                                }
                                                            }
                                                            
                                                        });
                                                        
                                    } else {
                                        分身.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                    
                                case "冲球":
                                    if (冲球 == null) {
                                        冲球 = buttonCreator.createRoundButton(
                                                        "冲球",
                                                        dpToPx(52),
                                                        0x5000ffff,
                                                        600,
                                                        200,
                                                        new RoundButtonCreator.OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                            
                                                                Thread 按钮线程 = new Thread(() -> {
                                                                    try {
                                                                        for (int i = 0;
                                                                            i < parameters.冲球_分身次数;
                                                                            i++) {
                                                                            int x = simpleButtonController.分身x;
                                                                            int y = simpleButtonController.分身y;
                                                                            // 触发按下动作
                                                                            HOOK.TouchEventHandler.按下(x, y, 点击id);
                                                                            Thread.sleep(parameters.冲球_分身间隔);
                                                                            // 触发抬起动作
                                                                            HOOK.TouchEventHandler.抬起(x, y, 点击id);
                                                                            Thread.sleep(parameters.冲球_分身间隔);
                                                                            }
                                                                            } catch (InterruptedException e) {
                                                                                        e.printStackTrace();
                                                                                    } finally {
                                                                                        // 确保抬起动作在所有情况下都能触发
                                                                                        HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身x, 点击id);
                                                                                    }
                                                                                });

                                                                按钮线程.start();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                                
                                                            }
                                                            
                                                            @Override
                                                            public void onButtonMove(int x, int y) {
                                                                if (按钮移动) {
                                                                    
                                                                }
                                                            }
                                                            
                                                        });

                                    } else {
                                        冲球.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                    
                                case "四分":
                                    if (四分 == null) {
                                        四分 = buttonCreator.createRoundButton(
                                                        "四分",
                                                        dpToPx(52),
                                                        0x50a874ff,
                                                        800,
                                                        200,
                                                       new RoundButtonCreator.OnButtonTouchListener() {

                                                            boolean 按钮按下 = false;
                                                            Thread 长按线程 = null;
                                
                                                            @Override
                                                            public void onButtonDown() {
                                                            
                                                                按钮按下 = true;
                                
                                                                开始4分();
                                
                                                                长按线程 = new Thread(() -> {
                                                                    try {
                                                                        Thread.sleep(parameters.四分_等待延时);
                                                                        if (按钮按下) {
                                                                            开始16分();
                                                                        }
                                                                    } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                });
                                                                长按线程.start();
                                                            }
                                
                                                            @Override
                                                            public void onButtonUp() {
                                                            
                                                                按钮按下 = false;
                                
                                                                if (长按线程 != null) {
                                                                    长按线程.interrupt();
                                                                    try {
                                                                        长按线程.join();
                                                                    } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    长按线程 = null;
                                                                }
                                                            }
                                
                                                            private void 开始4分() {
                                                                new Thread(() -> {
                                                                    try {
                                                                        for (int i = 0; i < 2; i++) {
                                                                            int x = simpleButtonController.分身x;
                                                                            int y = simpleButtonController.分身y;
                                
                                                                            HOOK.TouchEventHandler.按下(x, y, 点击id);
                                                                            HOOK.TouchEventHandler.抬起(x, y, 点击id);
                                                                            Thread.sleep(parameters.四分_分身延时);
                                                                        }
                                                                    } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                    } finally {
                                                                        HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 5);
                                                                    }
                                                                }).start();
                                                            }
                                
                                                            private void 开始16分() {
                                                                new Thread(() -> {
                                                                    try {
                                                                        while (按钮按下) {
                                                                            int x = simpleButtonController.分身x;
                                                                            int y = simpleButtonController.分身y;
                                
                                                                            HOOK.TouchEventHandler.按下(x, y, 点击id);
                                                                            HOOK.TouchEventHandler.抬起(x, y, 点击id);
                                                                            Thread.sleep(parameters.四分_分身间隔);
                                                                        }
                                                                    } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                    } finally {
                                                                        HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 5);
                                                                    }
                                                                }).start();
                                                            }
                                                       
                                                       @Override
                                                       public void onButtonMove(int x, int y) {
                                                            if (按钮移动) {
                                                                    
                                                            }
                                                       }
                                                       
                                                   });
                                
                                        } else {
                                        
                                        四分.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                    
                                case "三角":
                                    if (三角 == null) {
                                        三角 = buttonCreator.createRoundButton(
                                            "三角",
                                            dpToPx(52),
                                            0x5000ff00,
                                            1000,
                                            200,
                                            new RoundButtonCreator.OnButtonTouchListener() {
                                                @Override
                                                public void onButtonDown() {
                                                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 三角.getLayoutParams();
                                                    signX = layoutParams.leftMargin;
                                                    signTop = layoutParams.topMargin;
                                                }
                                
                                                @Override
                                                public void onButtonMove(int x, int y) {
                                                    moveX = x;
                                                    moveY = y;
                                                }
                                
                                                @Override
                                                public void onButtonUp() {
                                                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 三角.getLayoutParams();
                                                    layoutParams.leftMargin = signX;
                                                    layoutParams.topMargin = signTop;
                                                    三角.setLayoutParams(layoutParams);
                                                    三角.requestLayout();
                                
                                                    // 延后执行角度计算和触发操作
                                                    三角.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            boolean dragLeft = (moveX < signX);
                                                            int angle1, angle2;
                                                            if (dragLeft) {
                                                                // 向左滑动：angle1取 parameters.蛇手_1_滑动角度（负值），angle2取 parameters.蛇手_5_滑动角度（正值）
                                                                angle1 = Math.abs((int) parameters.三角_1_滑动角度);
                                                                angle2 = -Math.abs((int) parameters.三角_1_滑动角度);
                                                            } else {
                                                                // 向右滑动：angle1取 parameters.蛇手_1_滑动角度（正值），angle2取 parameters.蛇手_5_滑动角度（负值）
                                                                angle1 = -Math.abs((int) parameters.三角_1_滑动角度);
                                                                angle2 = Math.abs((int) parameters.三角_1_滑动角度);
                                                            }
                                                            int triangleMergeAngle = (int) parameters.三角_5_合球角度;
                                                            if (dragLeft) {
                                                                triangleMergeAngle = -triangleMergeAngle;
                                                            }
                                                            //XposedBridge.log("拖拽方向: " + (dragLeft ? "向左" : "向右"));
                                                            //XposedBridge.log("当前使用角度: angle1 = " + angle1 + ", angle2 = " + angle2 + ", triangleMergeAngle = " + triangleMergeAngle);
                                
                                                            try {
                                                                if (initialAngle == null) {
                                                                    initialAngle = joystickAngle;
                                                                }
                                                                HOOK.TouchEventHandler.角度滑动(
                                                                    simpleButtonController.摇杆x,
                                                                    simpleButtonController.摇杆y,
                                                                    initialAngle,
                                                                    angle1,
                                                                    parameters.三角_2_滑动长度,
                                                                    parameters.三角_3_滑动时长,
                                                                    触摸id,
                                                                    w1
                                                                );
                                                                Thread.sleep(parameters.三角_4_滑动延时);
                                
                                                                HOOK.TouchEventHandler.按下(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                HOOK.TouchEventHandler.抬起(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                Thread.sleep(parameters.三角_4_滑动延时);
                                
                                                                HOOK.TouchEventHandler.角度滑动(
                                                                    simpleButtonController.摇杆x,
                                                                    simpleButtonController.摇杆y,
                                                                    initialAngle,
                                                                    angle2,
                                                                    parameters.三角_2_滑动长度,
                                                                    parameters.三角_3_滑动时长,
                                                                    触摸id,
                                                                    w1
                                                                );
                                                                Thread.sleep(parameters.三角_4_滑动延时);
                                
                                                                HOOK.TouchEventHandler.按下(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                HOOK.TouchEventHandler.抬起(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                Thread.sleep(parameters.三角_4_滑动延时);
                                
                                                                HOOK.TouchEventHandler.角度滑动(
                                                                    simpleButtonController.摇杆x,
                                                                    simpleButtonController.摇杆y,
                                                                    initialAngle,
                                                                    triangleMergeAngle,
                                                                    parameters.三角_6_滑动长度,
                                                                    parameters.三角_7_滑动时长,
                                                                    触摸id,
                                                                    w1
                                                                );
                                
                                                                for (int i = 0; i < parameters.三角_8_分身次数; i++) {
                                                                    HOOK.TouchEventHandler.按下(
                                                                        simpleButtonController.分身x,
                                                                        simpleButtonController.分身y,
                                                                        点击id
                                                                    );
                                                                    HOOK.TouchEventHandler.抬起(
                                                                        simpleButtonController.分身x,
                                                                        simpleButtonController.分身y,
                                                                        点击id
                                                                    );
                                                                    Thread.sleep(parameters.三角_9_点击延时);
                                                                }
                                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        initialAngle = null;
                                                                    }
                                                                });
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        );
                                    } else {
                                        三角.setVisibility(View.VISIBLE);
                                    }
                                    break;
        
                                case "蛇手":
                                    if (蛇手 == null) {
                                        蛇手 = buttonCreator.createRoundButton(
                                            "蛇手",
                                            dpToPx(52),
                                            0x50ffa817,
                                            200,
                                            400,
                                            new RoundButtonCreator.OnButtonTouchListener() {
                                                @Override
                                                public void onButtonDown() {
                                                    if (isRunning) {
                                                        return;  // 如果当前操作正在执行，则不做任何事情
                                                    }
                                                    isRunning = true;
                                                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 蛇手.getLayoutParams();
                                                    signX = layoutParams.leftMargin;
                                                    signTop = layoutParams.topMargin;
                                                }
                                
                                                @Override
                                                public void onButtonMove(int x, int y) {
                                                    moveX = x;
                                                    moveY = y;
                                                }
                                
                                                @Override
                                                public void onButtonUp() {
                                                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 蛇手.getLayoutParams();
                                                    layoutParams.leftMargin = signX;
                                                    layoutParams.topMargin = signTop;
                                                    蛇手.setLayoutParams(layoutParams);
                                                    蛇手.requestLayout();
                                                    蛇手.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            boolean dragLeft = (moveX < signX);
                                                            int angle1, angle2;
                                                            if (dragLeft) {
                                                                // 向左滑动：angle1取 parameters.蛇手_1_滑动角度（负值），angle2取 parameters.蛇手_5_滑动角度（正值）
                                                                angle1 = -Math.abs((int) parameters.蛇手_1_滑动角度);
                                                                angle2 = Math.abs((int) parameters.蛇手_5_滑动角度);
                                                            } else {
                                                                // 向右滑动：angle1取 parameters.蛇手_5_滑动角度（正值），angle2取 parameters.蛇手_1_滑动角度（负值）
                                                                angle1 = Math.abs((int) parameters.蛇手_1_滑动角度);
                                                                angle2 = -Math.abs((int) parameters.蛇手_5_滑动角度);
                                                            }
                                                            int triangleMergeAngle = (int) parameters.蛇手_6_合球角度;
                                                            if (!dragLeft) {
                                                                triangleMergeAngle = -triangleMergeAngle;
                                                            }
                                                            
                                                            try {
                                                                if (initialAngle == null) {
                                                                    initialAngle = joystickAngle;
                                                                }
                                                                HOOK.TouchEventHandler.角度滑动(
                                                                    simpleButtonController.摇杆x,
                                                                    simpleButtonController.摇杆y,
                                                                    initialAngle,
                                                                    angle1,
                                                                    parameters.蛇手_2_滑动长度,
                                                                    parameters.蛇手_3_滑动时长,
                                                                    触摸id,
                                                                    w1
                                                                );
                                                                Thread.sleep(parameters.蛇手_4_滑动延时);
                                                                HOOK.TouchEventHandler.按下(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                HOOK.TouchEventHandler.抬起(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                Thread.sleep(parameters.蛇手_4_滑动延时);
                                                                HOOK.TouchEventHandler.角度滑动(
                                                                    simpleButtonController.摇杆x,
                                                                    simpleButtonController.摇杆y,
                                                                    initialAngle,
                                                                    angle2,
                                                                    parameters.蛇手_2_滑动长度,
                                                                    parameters.蛇手_3_滑动时长,
                                                                    触摸id,
                                                                    w1
                                                                );
                                                                Thread.sleep(parameters.蛇手_4_滑动延时);
                                                                HOOK.TouchEventHandler.按下(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                HOOK.TouchEventHandler.抬起(
                                                                    simpleButtonController.分身x,
                                                                    simpleButtonController.分身y,
                                                                    点击id
                                                                );
                                                                Thread.sleep(parameters.蛇手_4_滑动延时);
                                                                HOOK.TouchEventHandler.角度滑动(
                                                                    simpleButtonController.摇杆x,
                                                                    simpleButtonController.摇杆y,
                                                                    initialAngle,
                                                                    triangleMergeAngle,
                                                                    parameters.蛇手_7_滑动长度,
                                                                    parameters.蛇手_8_滑动时长,
                                                                    触摸id,
                                                                    w1
                                                                );
                                                                for (int i = 0; i < parameters.蛇手_9_点击次数; i++) {
                                                                    HOOK.TouchEventHandler.按下(
                                                                        simpleButtonController.分身x,
                                                                        simpleButtonController.分身y,
                                                                        点击id
                                                                    );
                                                                    HOOK.TouchEventHandler.抬起(
                                                                        simpleButtonController.分身x,
                                                                        simpleButtonController.分身y,
                                                                        点击id
                                                                    );
                                                                    Thread.sleep(parameters.蛇手_10_点击延时);
                                                                }
                                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        initialAngle = null;
                                                                    }
                                                                });
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            } finally {
                                                                isRunning = false;
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                    } else {
                                        蛇手.setVisibility(View.VISIBLE);
                                    }
                                    break;

                                case "侧合":
                                    if (侧合 == null) {
                                        侧合 = buttonCreator.createRoundButton(
                                                        "侧合",
                                                        dpToPx(52),
                                                        0x50ff3417,
                                                        400,
                                                        400,
                                                        new RoundButtonCreator.OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                            
                                                            if (isRunning) {
                                                                return;  // 如果当前操作正在执行，则不做任何事情
                                                            }
                                                            
                                                            isRunning = true;
                                                            
                                                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 侧合.getLayoutParams();
                                                            signX = layoutParams.leftMargin;
                                                            signTop = layoutParams.topMargin;
                                                                                                                                                                                             
                                                            }
                                                                
                                                            @Override
                                                            public void onButtonMove(int x, int y) {
                                                                   
                                                                moveX = x;
                                                                moveY = y;
    
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                            
                                                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 侧合.getLayoutParams();
                                                            layoutParams.leftMargin = signX;
                                                            layoutParams.topMargin = signTop;
                                                            侧合.setLayoutParams(layoutParams);
                                                                
                                                            侧合.requestLayout();
                                                            
                                                            侧合.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                            
                                                            boolean dragLeft = (moveX < signX);
                                                            int angle1, angle2;
                                                            if (dragLeft) {
                                                                // 向左滑动：angle1取 parameters.蛇手_1_滑动角度（负值），angle2取 parameters.蛇手_5_滑动角度（正值）
                                                                angle1 = Math.abs((int) parameters.侧合_1_滑动角度);
                                                                angle2 = -Math.abs((int) parameters.侧合_5_滑动角度);
                                                            } else {
                                                                // 向右滑动：angle1取 parameters.蛇手_5_滑动角度（正值），angle2取 parameters.蛇手_1_滑动角度（负值）
                                                                angle1 = -Math.abs((int) parameters.侧合_1_滑动角度);
                                                                angle2 = Math.abs((int) parameters.侧合_5_滑动角度);
                                                            }
                                                            /*
                                                            int triangleMergeAngle = (int) parameters.蛇手_6_合球角度;
                                                            if (!dragLeft) {
                                                                triangleMergeAngle = -triangleMergeAngle;
                                                            }
                                                            */
                                                            try {
                                                                if (initialAngle == null) {
                                                                    initialAngle = joystickAngle;
                                                                }
                                                                
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, angle1, parameters.侧合_2_滑动长度, parameters.侧合_3_滑动时长, 触摸id,w1);
                                                                Thread.sleep(parameters.侧合_4_滑动延时); // 延时
                                                                for(int i = 0; i < 2; ++i) {
                                                                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                	HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                    Thread.sleep(parameters.侧合_4_滑动延时); // 延时
                                                                }
                                                                Thread.sleep(parameters.侧合_4_滑动延时); // 延时
                                                
                                                                // 第二次滑动
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, angle2, parameters.侧合_6_滑动长度, parameters.侧合_3_滑动时长, 触摸id,w1);
                                                
                                                                // 循环按下和抬起，带有延时
                                                                for (int i = 0; i < parameters.侧合_7_分身次数; i++) {
                                                                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                    Thread.sleep(parameters.侧合_8_分身间隔); // 延时
                                                                }
                                                                    
                                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        initialAngle = null;
                                                                    }
                                                                });
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                } finally {
                                                                isRunning = false;
                                                                }
                                                            }
                                                        });
                                                     }
                                                 });

                                    } else {
                                        侧合.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                
                                case "后仰":
                                    if (后仰 == null) {
                                        后仰 = buttonCreator.createRoundButton(
                                                        "后仰",
                                                        dpToPx(52),
                                                        0x50dcdc00,
                                                        600,
                                                        400,
                                                        new RoundButtonCreator.OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                            
                                                            if (isRunning) {
                                                                return;  // 如果当前操作正在执行，则不做任何事情
                                                            }
                                                            
                                                            isRunning = true;
                                                            
                                                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 后仰.getLayoutParams();
                                                            signX = layoutParams.leftMargin;
                                                            signTop = layoutParams.topMargin;
                                                                                                                                                                                             
                                                            }
                                                                
                                                            @Override
                                                            public void onButtonMove(int x, int y) {
                                                                   
                                                                moveX = x;
                                                                moveY = y;
    
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                            
                                                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 后仰.getLayoutParams();
                                                            layoutParams.leftMargin = signX;
                                                            layoutParams.topMargin = signTop;
                                                            后仰.setLayoutParams(layoutParams);
                                                                
                                                            后仰.requestLayout();
                                                            
                                                            后仰.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                            
                                                            boolean dragLeft = (moveX < signX);
                                                            int angle1, angle2;
                                                            if (dragLeft) {
                                                                // 向左滑动：angle1取 parameters.蛇手_1_滑动角度（负值），angle2取 parameters.蛇手_5_滑动角度（正值）
                                                                angle1 = Math.abs((int) parameters.后仰_1_滑动角度);
                                                                angle2 = Math.abs((int) parameters.后仰_5_滑动角度);
                                                            } else {
                                                                // 向右滑动：angle1取 parameters.蛇手_5_滑动角度（正值），angle2取 parameters.蛇手_1_滑动角度（负值）
                                                                angle1 = -Math.abs((int) parameters.后仰_1_滑动角度);
                                                                angle2 = -Math.abs((int) parameters.后仰_5_滑动角度);
                                                            }
                                                            
                                                            int triangleMergeAngle = (int) parameters.后仰_6_滑动角度;
                                                            if (!dragLeft) {
                                                                triangleMergeAngle = -triangleMergeAngle;
                                                            }
                                                           // XposedBridge.log("拖拽方向: " + (dragLeft ? "向左" : "向右"));
                                                           //XposedBridge.log("当前使用角度: angle1 = " + angle1 + ", angle2 = " + angle2 + ", triangleMergeAngle = " + triangleMergeAngle);
                                                            
                                                            try {
                                                                if (initialAngle == null) {
                                                                    initialAngle = joystickAngle;
                                                                }
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, angle1, parameters.后仰_2_滑动长度, parameters.后仰_3_滑动时长, 触摸id,w1);
                                                                Thread.sleep(parameters.后仰_4_滑动延时); // 延时
                                                                
                                                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                Thread.sleep(parameters.后仰_4_滑动延时); // 延时
                                                                
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, angle2, parameters.后仰_2_滑动长度, parameters.后仰_3_滑动时长, 触摸id,w1);
                                                                Thread.sleep(parameters.后仰_4_滑动延时); // 延时
                                                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                Thread.sleep(parameters.后仰_4_滑动延时); // 延时
                                                
                                                                // 第三次滑动
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, triangleMergeAngle, parameters.后仰_2_滑动长度, parameters.后仰_3_滑动时长, 触摸id,w1);
                                                
                                                                // 循环按下和抬起，带有延时
                                                                for (int i = 0; i < parameters.后仰_7_分身次数; i++) {
                                                                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                    Thread.sleep(parameters.后仰_8_分身间隔); // 延时
                                                                }
                    
                                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        initialAngle = null;
                                                                    }
                                                                });
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                } finally {
                                                                isRunning = false;
                                                                }
                                                            }
                                                        });
                                                     }
                                                 });

                                    } else {
                                        后仰.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                
                                case "旋转":
                                    if (旋转 == null) {
                                        旋转 = buttonCreator.createRoundButton(
                                                        "旋转",
                                                        dpToPx(52),
                                                        0x50ff8ba8,
                                                        800,
                                                        400,
                                                        new RoundButtonCreator.OnButtonTouchListener() {                                                            
                                                            @Override
                                                            public void onButtonDown() {
                                                            
                                                            if (isRunning) {
                                                                return;  // 如果当前操作正在执行，则不做任何事情
                                                            }
                                                            
                                                            isRunning = true;
                                                            
                                                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 旋转.getLayoutParams();
                                                            signX = layoutParams.leftMargin;
                                                            signTop = layoutParams.topMargin;
                                                                                                                                                                                             
                                                            }
                                                                
                                                            @Override
                                                            public void onButtonMove(int x, int y) {
                                                                   
                                                                moveX = x;
                                                                moveY = y;
    
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                            
                                                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) 旋转.getLayoutParams();
                                                            layoutParams.leftMargin = signX;
                                                            layoutParams.topMargin = signTop;
                                                            旋转.setLayoutParams(layoutParams);
                                                                
                                                            旋转.requestLayout();
                                                            
                                                            旋转.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                            
                                                            boolean dragLeft = (moveX < signX);
                                                            int angle1, angle2, angle3;
                                                            if (dragLeft) {
                                                                // 向左滑动：angle1取 parameters.蛇手_1_滑动角度（负值），angle2取 parameters.蛇手_5_滑动角度（正值）
                                                                angle1 = Math.abs((int) parameters.旋转_1_滑动角度);
                                                                angle2 = Math.abs((int) parameters.旋转_5_滑动角度);
                                                                angle3 = -Math.abs((int) parameters.旋转_8_滑动角度);
                                                            } else {
                                                                // 向右滑动：angle1取 parameters.蛇手_5_滑动角度（正值），angle2取 parameters.蛇手_1_滑动角度（负值）
                                                                angle1 = -Math.abs((int) parameters.旋转_1_滑动角度);
                                                                angle2 = -Math.abs((int) parameters.旋转_5_滑动角度);
                                                                angle3 = Math.abs((int) parameters.旋转_8_滑动角度);
                                                            }
                                                            
                                                            int triangleMergeAngle = (int) parameters.旋转_11_滑动角度;
                                                            if (!dragLeft) {
                                                                triangleMergeAngle = -triangleMergeAngle;
                                                            }
                                                            //XposedBridge.log("拖拽方向: " + (dragLeft ? "向左" : "向右"));
                                                            //XposedBridge.log("当前使用角度: angle1 = " + angle1 + ", angle2 = " + angle2 +", angle3 = " + angle3 + ", triangleMergeAngle = " + triangleMergeAngle);                                                            
                                                            try {
                                                                if (initialAngle == null) {
                                                                    initialAngle = joystickAngle;
                                                                }
                                                                
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, angle1, parameters.旋转_2_滑动长度, parameters.旋转_3_滑动时长, 触摸id,w1);
                                                                Thread.sleep(parameters.旋转_4_滑动延时); // 延时
                                                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                Thread.sleep(parameters.旋转_4_滑动延时); // 延时
                                                
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, angle2, parameters.旋转_6_滑动长度, parameters.旋转_7_滑动时长, 触摸id,w1);
                                                                Thread.sleep(parameters.旋转_4_滑动延时); // 延时
                                                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                Thread.sleep(parameters.旋转_4_滑动延时); // 延时
                                                
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, angle3, parameters.旋转_9_滑动长度, parameters.旋转_10_滑动时长, 触摸id,w1);
                                                                Thread.sleep(parameters.旋转_4_滑动延时); // 延时
                                                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                Thread.sleep(parameters.旋转_4_滑动延时); // 延时
                                                
                                                                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, triangleMergeAngle, parameters.旋转_12_滑动长度, parameters.旋转_13_滑动时长, 触摸id,w1);
                                                                Thread.sleep(parameters.旋转_4_滑动延时); // 延时
                                                                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                
                                                                Thread.sleep(parameters.旋转_15_分身间隔); // 延时
                                                
                                                                // 循环按下和抬起，带有延时
                                                                for (int i = 0; i < parameters.旋转_14_分身次数; i++) {
                                                                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                                                                    Thread.sleep(parameters.旋转_15_分身间隔); // 延时
                                                                }
                                                                
                                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        initialAngle = null;
                                                                    }
                                                                });
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                } finally {
                                                                isRunning = false;
                                                                }
                                                            }
                                                        });
                                                     }
                                                 });
                                    } else {
                                        旋转.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                    
                                case "绘制➩卡点":
                                    showCardButton = true;                          
                                    if (卡点 != null) {
                                        卡点.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                    
                                case "内存➩卡点":
                                    if (内存卡点 == null) {
                                        内存卡点 = buttonCreator.createRoundButton(
                                                        "内存\n卡点",
                                                        dpToPx(52),
                                                        0xff8be2ff,
                                                        1000,
                                                        400,
                                                        new RoundButtonCreator.OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                
                                                                new Thread(() -> {
                                                                    try {
                                                                        // 初始读取地址
                                                                        int initialValue = memory.readDword(Incomingfive + 1960);
                                                                        previousValue = initialValue;
                                                            
                                                                        while (true) {
                                                                        
                                                                            int currentValue = memory.readDword(Incomingfive + 1960);
                                                            
                                                                            // 判断值是否增加
                                                                            if (currentValue - previousValue > parameters.卡点_过滤分身) {
                                                            
                                                                             for (int i = 0; i < parameters.卡点_分身次数; i++) {
                                                                                    HOOK.TouchEventHandler.按下(
                                                                                        simpleButtonController.分身x,
                                                                                        simpleButtonController.分身y,
                                                                                        点击id
                                                                                    );
                                                                                    HOOK.TouchEventHandler.抬起(
                                                                                        simpleButtonController.分身x,
                                                                                        simpleButtonController.分身y,
                                                                                        点击id
                                                                                    );
                                                                                    Thread.sleep(parameters.卡点_分身间隔);
                                                                                    }
                                                                             break;
                                                                              
                                                                            }
                                                            
                                                                            // 更新之前的值
                                                                            previousValue = currentValue;
                                                            
                                                                            Thread.sleep(parameters.卡点_判断延时);
                                                                        }
                                                                    
                                                                    } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }).start();
                                                            
                                                            }
                                                            
                                                            @Override
                                                            public void onButtonUp() {
                                                                
                                                            }
                                                            
                                                            @Override
                                                            public void onButtonMove(int x, int y) {
                                                                 if (按钮移动) {
                                                                        
                                                                 }
                                                            }
                                                       
                                                        });

                                    } else {
                                        内存卡点.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                   /* 
                                case "卡点(黑屏)":
                                    showCardButton = true;                          
                                    if (黑屏卡点 != null) {
                                        黑屏卡点.setVisibility(View.VISIBLE);
                                    }
                                    break;
*/
                                case "卡点(旋转)":
                                    showCardButton = true;                          
                                    if (旋转卡点 != null) {
                                        旋转卡点.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                    
                                case "卡点(蛇手)":
                                    showCardButton = true;                          
                                    if (蛇手卡点 != null) {
                                        蛇手卡点.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                
                                case "追踪(大炮)":
                                    showCardButton = true;                          
                                    if (追踪大炮 != null) {
                                        追踪大炮.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case "同步炮":
                                    showCardButton = true;                          
                                    if (同步大炮 != null) {
                                        同步大炮.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                
                                case "追踪(三角)":
                                    showCardButton = true;                          
                                    if (追踪三角 != null) {
                                        追踪三角.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                    
                                case "同步△":
                                    showCardButton = true;                          
                                    if (同步三角 != null) {
                                        同步三角.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                    
                                case "同步蛇":
                                    showCardButton = true;                          
                                    if (同步蛇手 != null) {
                                        同步蛇手.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                                                
                                default:
                                    break;
                            }
                        } else {
                            
                            switch (switchName) {
                            
                            
                            // 隐藏滑条（对应false）
case "滑块显隐":
    滑块.setSliderVisible(false);
    
    break;
                            
                                case "调键(位置/颜色/大小)":
                                    滑块.setAdjustable(false);
                                    RoundButtonCreator.setDebugMode(false);
                                    simpleButtonController.updateButtonVisibility(false);
                                    break;
                                    
                                case "吐球":
                                    if (吐球 != null) {
                                        吐球.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "四分":
                                    if (四分 != null) {
                                        四分.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "分身":
                                    
                                    if (分身 != null) {
                                        分身.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "冲球":
                                    if (冲球 != null) {
                                        冲球.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "三角":
                                    if (三角 != null) {
                                        三角.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "蛇手":
                                    if (蛇手 != null) {
                                        蛇手.setVisibility(View.GONE);
                                    }
                                    break;

                                case "侧合":
                                    if (侧合 != null) {
                                        侧合.setVisibility(View.GONE);
                                    }
                                    break;
                                
                                case "后仰":
                                    if (后仰 != null) {
                                        后仰.setVisibility(View.GONE);
                                    }
                                    break;
                                
                                case "旋转":
                                    if (旋转 != null) {
                                        旋转.setVisibility(View.GONE);
                                    }
                                    break;
                                
                                case "绘制➩卡点":
                                    showCardButton = true;
                                    if (卡点 != null) {
                                        卡点.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "内存➩卡点":
                                    if (内存卡点 != null) {
                                        内存卡点.setVisibility(View.GONE);
                                    }
                                    break;
/*
                                case "卡点(黑屏)":
                                    showCardButton = true;                          
                                    if (黑屏卡点 != null) {
                                        黑屏卡点.setVisibility(View.GONE);
                                    }
                                    break;
*/
                                case "卡点(旋转)":
                                    showCardButton = true;                          
                                    if (旋转卡点 != null) {
                                        旋转卡点.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "卡点(蛇手)":
                                    showCardButton = true;                          
                                    if (蛇手卡点 != null) {
                                        蛇手卡点.setVisibility(View.GONE);
                                    }
                                    break;
                                
                                case "追踪(大炮)":
                                    showCardButton = true;                          
                                    if (追踪大炮 != null) {
                                        追踪大炮.setVisibility(View.GONE);
                                    }
                                    break;
                                case "同步炮":
                                    showCardButton = true;                          
                                    if (同步大炮 != null) {
                                        同步大炮.setVisibility(View.GONE);
                                    }
                                    break;
                                
                                case "追踪(三角)":
                                    showCardButton = true;                          
                                    if (追踪三角 != null) {
                                        追踪三角.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "同步△":
                                    showCardButton = true;                          
                                    if (同步三角 != null) {
                                        同步三角.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                case "同步蛇":
                                    showCardButton = true;                          
                                    if (同步蛇手 != null) {
                                        同步蛇手.setVisibility(View.GONE);
                                    }
                                    break;
                                    
                                default:
                                    break;
                            }
                        }
                    });

        switchContainer.addView(switchButton);
    }

    scrollView.addView(switchContainer);
    page2Layout.addView(scrollView);

    return page2Layout;
}



    private LinearLayout createPage3Layout(Context context) {
    // 分页3
    LinearLayout page3Layout = new LinearLayout(context);
    

    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
    ));
    LinearLayout.LayoutParams spacingParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(8)); // 8dp间隔

    GradientDrawable scrollBackground = new GradientDrawable();
    scrollBackground.setColor(Color.parseColor("#00ffb9ee"));
    scrollBackground.setCornerRadius(16); // 圆角背景
    scrollView.setBackground(scrollBackground);

    LinearLayout mainLayout = new LinearLayout(context);
    mainLayout.setOrientation(VERTICAL);
    mainLayout.setLayoutParams(new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    int sidePadding = dpToPx(16); // 设置左右边距
    mainLayout.setPadding(sidePadding, 0, sidePadding, 0);

    // 吐球分身设置
    mainLayout.addView(createCollapsibleSection(context, "连点参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
                    
    addSeekBarWithEditText(context, layout, "每秒吐球次数", parameters.吐球_每秒次数, 1, 50, value -> parameters.吐球_每秒次数 = value);
    addSeekBarWithEditText(context, layout, "吐球按住时间", parameters.吐球_按住时长, 1, 50, value -> parameters.吐球_按住时长 = value);
        
    addSeekBarWithEditText(context, layout, "每秒分身次数", parameters.分身_每秒次数, 1, 50, value -> parameters.分身_每秒次数 = value);
    
    addSeekBarWithEditText(context, layout, "冲球分身次数", parameters.冲球_分身次数, 1, 200, value -> parameters.冲球_分身次数 = value);
    addSeekBarWithEditText(context, layout, "冲球分身间隔", parameters.冲球_分身间隔, 1, 200, value -> parameters.冲球_分身间隔 = value);
    
    addSeekBarWithEditText(context, layout, "四分分身延迟", parameters.四分_分身延时, 1, 200, value -> parameters.四分_分身延时 = value);
    addSeekBarWithEditText(context, layout, "四分分身间隔", parameters.四分_分身间隔, 1, 200, value -> parameters.四分_分身间隔 = value);
    addSeekBarWithEditText(context, layout, "四分等待时间", parameters.四分_等待延时, 1, 600, value -> parameters.四分_等待延时 = value);
                    
    return layout;
}));

mainLayout.addView(new View(context), spacingParams);

// 绘图参数
mainLayout.addView(createCollapsibleSection(context, "绘图参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "分辨率x", parameters.分辨率x, 1, 3200, value -> parameters.分辨率x = value);
    addSeekBarWithEditText(context, layout, "分辨率y", parameters.分辨率y, 1, 1440, value -> parameters.分辨率y = value);
    addFloatSeekBarWithEditText(context, layout, "绘图大小", BigDecimal.valueOf(parameters.比例), BigDecimal.valueOf(0), BigDecimal.valueOf(2), value -> parameters.比例 = value);
    addSeekBarWithEditText(context, layout, "排名x坐标", parameters.排名x坐标, 0, 4000, value -> parameters.排名x坐标 = value);
    addSeekBarWithEditText(context, layout, "排名y坐标", parameters.排名y坐标, 0, 2500, value -> parameters.排名y坐标 = value);
    addFloatSeekBarWithEditText(context, layout, "排名最大显示", BigDecimal.valueOf(parameters.排名最大显示), BigDecimal.valueOf(0), BigDecimal.valueOf(2), value -> parameters.排名最大显示 = value);
    
    return layout;
}));

mainLayout.addView(new View(context), spacingParams);


mainLayout.addView(createCollapsibleSection(context, "卡点参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "分身前延迟", parameters.卡点_触摸延迟, 1, 200, value -> parameters.卡点_触摸延迟 = value);
    addSeekBarWithEditText(context, layout, "过滤分身数量", parameters.卡点_过滤分身, 1, 200, value -> parameters.卡点_过滤分身 = value);
    addSeekBarWithEditText(context, layout, "卡点判断间隔", parameters.卡点_判断延时, 1, 200, value -> parameters.卡点_判断延时 = value);
    addSeekBarWithEditText(context, layout, "点击分身次数", parameters.卡点_分身次数, 1, 200, value -> parameters.卡点_分身次数 = value);
    addSeekBarWithEditText(context, layout, "每次分身间隔", parameters.卡点_分身间隔, 1, 200, value -> parameters.卡点_分身间隔 = value);

    
    return layout;
}));

mainLayout.addView(new View(context), spacingParams);


// 三角参数
mainLayout.addView(createCollapsibleSection(context, "三角参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
    
   addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.三角_1_滑动角度, 0, 180, value -> parameters.三角_1_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.三角_2_滑动长度, 1, 600, value -> parameters.三角_2_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动时间", parameters.三角_3_滑动时长, 1, 200, value -> parameters.三角_3_滑动时长 = value);
    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.三角_4_滑动延时, 1, 200, value -> parameters.三角_4_滑动延时 = value);

    addSeekBarWithEditText(context, layout, "合球滑动方向", (int) parameters.三角_5_合球角度, 0, 180, value -> parameters.三角_5_合球角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.三角_6_滑动长度, 1, 600, value -> parameters.三角_6_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动时间", parameters.三角_7_滑动时长, 1, 200, value -> parameters.三角_7_滑动时长 = value);
        
    addSeekBarWithEditText(context, layout, "点击分身次数", parameters.三角_8_分身次数, 1, 200, value -> parameters.三角_8_分身次数 = value);
    addSeekBarWithEditText(context, layout, "点击分身延迟", parameters.三角_9_点击延时, 1, 200, value -> parameters.三角_9_点击延时 = value);


    return layout;
}));

mainLayout.addView(new View(context), spacingParams);

// 蛇手参数
mainLayout.addView(createCollapsibleSection(context, "蛇手参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.蛇手_1_滑动角度, 0, 180, value -> parameters.蛇手_1_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.蛇手_2_滑动长度, 1, 600, value -> parameters.蛇手_2_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动时间", parameters.蛇手_3_滑动时长, 1, 200, value -> parameters.蛇手_3_滑动时长 = value);
    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.蛇手_4_滑动延时, 1, 200, value -> parameters.蛇手_4_滑动延时 = value);  
              
    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.蛇手_5_滑动角度, 0, 180, value -> parameters.蛇手_5_滑动角度 = value);
        
    addSeekBarWithEditText(context, layout, "合球滑动方向", (int) parameters.蛇手_6_合球角度, 0, 180, value -> parameters.蛇手_6_合球角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.蛇手_7_滑动长度, 1, 600, value -> parameters.蛇手_7_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动时间", parameters.蛇手_8_滑动时长, 1, 200, value -> parameters.蛇手_8_滑动时长 = value);

    addSeekBarWithEditText(context, layout, "点击分身次数", parameters.蛇手_9_点击次数, 1, 200, value -> parameters.蛇手_9_点击次数 = value);
    addSeekBarWithEditText(context, layout, "点击分身延迟", parameters.蛇手_10_点击延时, 1, 200, value -> parameters.蛇手_10_点击延时 = value);

    return layout;
}));

        
    mainLayout.addView(new View(context), spacingParams);

    // 四分测合
    mainLayout.addView(createCollapsibleSection(context, "侧合参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
    
    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.侧合_1_滑动角度, 0, 180, value -> parameters.侧合_1_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.侧合_2_滑动长度, 1, 600, value -> parameters.侧合_2_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动时间", parameters.侧合_3_滑动时长, 1, 200, value -> parameters.侧合_3_滑动时长 = value);                               
    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.侧合_4_滑动延时, 1, 200, value -> parameters.侧合_4_滑动延时 = value);
        
    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.侧合_5_滑动角度, 0, 180, value -> parameters.侧合_5_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.侧合_6_滑动长度, 1, 600, value -> parameters.侧合_6_滑动长度 = value);
    
    addSeekBarWithEditText(context, layout, "点击分身次数", parameters.侧合_7_分身次数, 1, 200, value -> parameters.侧合_7_分身次数 = value);
    addSeekBarWithEditText(context, layout, "点击分身间隔", parameters.侧合_8_分身间隔, 1, 200, value -> parameters.侧合_8_分身间隔 = value);

    return layout;
}));

mainLayout.addView(new View(context), spacingParams);       


mainLayout.addView(createCollapsibleSection(context, "后仰参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
    
    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.后仰_1_滑动角度, 0, 180, value -> parameters.后仰_1_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.后仰_2_滑动长度, 1, 600, value -> parameters.后仰_2_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动时间", parameters.后仰_3_滑动时长, 1, 200, value -> parameters.后仰_3_滑动时长 = value);
    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.后仰_4_滑动延时, 1, 200, value -> parameters.后仰_4_滑动延时 = value);
    
    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.后仰_5_滑动角度, 0, 180, value -> parameters.后仰_5_滑动角度 = value);
    
    addSeekBarWithEditText(context, layout, "合球滑动方向", (int) parameters.后仰_6_滑动角度, 0, 180, value -> parameters.后仰_6_滑动角度 = value);
    
    addSeekBarWithEditText(context, layout, "点击分身次数", parameters.后仰_7_分身次数, 1, 200, value -> parameters.后仰_7_分身次数 = value);
    addSeekBarWithEditText(context, layout, "点击分身间隔", parameters.后仰_8_分身间隔, 1, 200, value -> parameters.后仰_8_分身间隔 = value);

    return layout;
}));
        
    mainLayout.addView(new View(context), spacingParams);

    mainLayout.addView(createCollapsibleSection(context, "旋转参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.旋转_1_滑动角度, -180, 180, value -> parameters.旋转_1_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.旋转_2_滑动长度, 1, 600, value -> parameters.旋转_2_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动时间", parameters.旋转_3_滑动时长, 1, 200, value -> parameters.旋转_3_滑动时长 = value);

    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.旋转_4_滑动延时, 1, 200, value -> parameters.旋转_4_滑动延时 = value);
    
    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.旋转_5_滑动角度, -180, 180, value -> parameters.旋转_5_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.旋转_6_滑动长度, 1, 600, value -> parameters.旋转_6_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.旋转_7_滑动时长, 1, 200, value -> parameters.旋转_7_滑动时长 = value);

    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.旋转_8_滑动角度, -180, 180, value -> parameters.旋转_8_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.旋转_9_滑动长度, 1, 600, value -> parameters.旋转_9_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.旋转_10_滑动时长, 1, 200, value -> parameters.旋转_10_滑动时长 = value);

    addSeekBarWithEditText(context, layout, "摇杆滑动方向", (int) parameters.旋转_11_滑动角度, -180, 180, value -> parameters.旋转_11_滑动角度 = value);
    addSeekBarWithEditText(context, layout, "摇杆滑动距离", parameters.旋转_12_滑动长度, 1, 600, value -> parameters.旋转_12_滑动长度 = value);
    addSeekBarWithEditText(context, layout, "滑动等待时间", parameters.旋转_13_滑动时长, 1, 200, value -> parameters.旋转_13_滑动时长 = value);

    addSeekBarWithEditText(context, layout, "点击分身次数", parameters.旋转_14_分身次数, 1, 200, value -> parameters.旋转_14_分身次数 = value);
    addSeekBarWithEditText(context, layout, "点击分身间隔", parameters.旋转_15_分身间隔, 1, 200, value -> parameters.旋转_15_分身间隔 = value);

    return layout;
}));

    mainLayout.addView(new View(context), spacingParams);
    
                                                      // 卡点同步设置
    mainLayout.addView(createCollapsibleSection(context, "同步炮参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);                  
  //  addSeekBarWithEditText(context, layout, "卡点过滤", parameters.卡点过滤, 0, 10, value -> parameters.卡点过滤 = value);
          addSeekBarWithEditText(context, layout, "同步分身前摇", parameters.同步前摇, 0, 1000, value -> parameters.同步前摇 = value);
   addSeekBarWithEditText(context, layout, "同步滑动长度", parameters.同步滑动长度, 0, 1000, value -> parameters.同步滑动长度 = value);
    addSeekBarWithEditText(context, layout, "同步滑动时长", parameters.同步滑动时长, 0, 1000, value -> parameters.同步滑动时长 = value);        
    addSeekBarWithEditText(context, layout, "同步分身次数", parameters.同步分身次数, 0, 100, value -> parameters.同步分身次数 = value);
    addSeekBarWithEditText(context, layout, "同步分身间隔", parameters.同步分身间隔, 0, 100, value -> parameters.同步分身间隔 = value);
    return layout;
}));

mainLayout.addView(new View(context), spacingParams);
   
                                               // 卡点旋转设置
    /*mainLayout.addView(createCollapsibleSection(context, "旋转卡点设置", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);                  
  //  addSeekBarWithEditText(context, layout, "卡点过滤", parameters.卡点过滤, 0, 10, value -> parameters.卡点过滤 = value);
                                                addSeekBarWithEditText(context, layout, "旋卡前摇", parameters.旋转卡点前摇, 0, 1000, value -> parameters.旋转卡点前摇 = value);
            addSeekBarWithEditText(context, layout, "旋卡第二分角度", parameters.旋转卡点初始角度, -180, 180, value -> parameters.旋转卡点初始角度 = value);
    addSeekBarWithEditText(context, layout, "旋卡角度递减", parameters.旋转卡点角度递减, 0, 80, value -> parameters.旋转卡点角度递减 = value);
                addSeekBarWithEditText(context, layout, "旋卡旋转次数", parameters.旋转卡点次数, 1, 100, value -> parameters.旋转卡点次数 = value); // 新增：旋转次数调节
                addSeekBarWithEditText(context, layout, "旋卡分身次数", parameters.旋转卡点分身次数, 0, 100, value -> parameters.旋转卡点分身次数 = value);
    addSeekBarWithEditText(context, layout, "旋卡分身间隔", parameters.旋转卡点分身间隔, 0, 500, value -> parameters.旋转卡点分身间隔 = value);
    addSeekBarWithEditText(context, layout, "旋卡滑动长度", parameters.旋转卡点滑动长度, 0, 1000, value -> parameters.旋转卡点滑动长度 = value);
    addSeekBarWithEditText(context, layout, "旋卡滑动时长", parameters.旋转卡点滑动时长, 0, 500, value -> parameters.旋转卡点滑动时长 = value);
    
    

    return layout;
}));
    
mainLayout.addView(new View(context), spacingParams);*/
                                            
        mainLayout.addView(createCollapsibleSection(context, "同步蛇手参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);                  
  //  addSeekBarWithEditText(context, layout, "卡点过滤", parameters.卡点过滤, 0, 10, value -> parameters.卡点过滤 = value);
          addSeekBarWithEditText(context, layout, "蛇手滑动1角度", (int) parameters.蛇手卡点滑动1角度, -180, 180, value -> parameters.蛇手卡点滑动1角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手滑动2角度", (int) parameters.蛇手卡点滑动2角度, -180, 180, value -> parameters.蛇手卡点滑动2角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手滑动1长度", parameters.蛇手卡点滑动1长度, 0, 1000, value -> parameters.蛇手卡点滑动1长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手滑动2长度", parameters.蛇手卡点滑动2长度, 0, 1000, value -> parameters.蛇手卡点滑动2长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手滑动时长", parameters.蛇手卡点滑动时长, 0, 500, value -> parameters.蛇手卡点滑动时长 = value);
    addSeekBarWithEditText(context, layout, "蛇手滑动延时", parameters.蛇手卡点滑动延时, 0, 500, value -> parameters.蛇手卡点滑动延时 = value);
    addSeekBarWithEditText(context, layout, "蛇手分身延时", parameters.蛇手卡点分身延时, 0, 500, value -> parameters.蛇手卡点分身延时 = value);                        
    addSeekBarWithEditText(context, layout, "蛇手分身次数", parameters.蛇手卡点分身次数, 0, 100, value -> parameters.蛇手卡点分身次数 = value);
    addSeekBarWithEditText(context, layout, "蛇手分身间隔", parameters.蛇手卡点分身间隔, 0, 500, value -> parameters.蛇手卡点分身间隔 = value);
    return layout;
}));
                            
  mainLayout.addView(new View(context), spacingParams);                          



mainLayout.addView(createCollapsibleSection(context, "同步三角参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "三角1滑动角度", (int) parameters.三角1滑动角度, -180, 180, value -> parameters.三角1滑动角度 = value);
    addSeekBarWithEditText(context, layout, "三角1滑动长度", parameters.三角1滑动长度, 0, 1000, value -> parameters.三角1滑动长度 = value);
    addSeekBarWithEditText(context, layout, "三角1滑动时长", parameters.三角1滑动时长, 0, 500, value -> parameters.三角1滑动时长 = value);
    addSeekBarWithEditText(context, layout, "三角1点击延时", parameters.三角1点击延时, 0, 500, value -> parameters.三角1点击延时 = value);
    addSeekBarWithEditText(context, layout, "三角2滑动延时", parameters.三角2滑动延时, 0, 500, value -> parameters.三角2滑动延时 = value);

    addSeekBarWithEditText(context, layout, "三角2滑动角度", (int) parameters.三角2滑动角度, -180, 180, value -> parameters.三角2滑动角度 = value);
    addSeekBarWithEditText(context, layout, "三角2滑动长度", parameters.三角2滑动长度, 0, 1000, value -> parameters.三角2滑动长度 = value);
    addSeekBarWithEditText(context, layout, "三角2滑动时长", parameters.三角2滑动时长, 0, 500, value -> parameters.三角2滑动时长 = value);
    addSeekBarWithEditText(context, layout, "三角2点击延时", parameters.三角2点击延时, 0, 500, value -> parameters.三角2点击延时 = value);
    addSeekBarWithEditText(context, layout, "三角3滑动延时", parameters.三角3滑动延时, 0, 500, value -> parameters.三角3滑动延时 = value);

    addSeekBarWithEditText(context, layout, "三角3滑动角度", (int) parameters.三角3滑动角度, -180, 180, value -> parameters.三角3滑动角度 = value);
    addSeekBarWithEditText(context, layout, "三角3滑动长度", parameters.三角3滑动长度, 0, 1000, value -> parameters.三角3滑动长度 = value);
    addSeekBarWithEditText(context, layout, "三角3滑动时长", parameters.三角3滑动时长, 0, 500, value -> parameters.三角3滑动时长 = value);

    addSeekBarWithEditText(context, layout, "三角分身次数", parameters.三角分身次数, 0, 100, value -> parameters.三角分身次数 = value);
    addSeekBarWithEditText(context, layout, "三角分身延时", parameters.三角分身延时, 0, 500, value -> parameters.三角分身延时 = value);

    return layout;
}));

mainLayout.addView(new View(context), spacingParams);


    
    scrollView.addView(mainLayout);
    page3Layout.addView(scrollView);

    return page3Layout;
}

private LinearLayout createPage4Layout(Context context) {
    // 分页4
    LinearLayout page4Layout = new LinearLayout(context);
    page4Layout.setOrientation(VERTICAL);

    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
    ));
    LinearLayout.LayoutParams spacingParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(8)); // 8dp间隔

    GradientDrawable scrollBackground = new GradientDrawable();
    scrollBackground.setColor(Color.parseColor("#00ffb9ee"));
    scrollBackground.setCornerRadius(16); // 圆角背景
    scrollView.setBackground(scrollBackground);

    LinearLayout mainLayout = new LinearLayout(context);
    mainLayout.setOrientation(VERTICAL);
    mainLayout.setLayoutParams(new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    int sidePadding = dpToPx(16); // 设置左右边距
    mainLayout.setPadding(sidePadding, 0, sidePadding, 0);
                        
       
    addTextView(context, page4Layout, "美化用插件悬浮里的这里仅作占位", null);                    
    addTextView(context, page4Layout, "任意皮肤美化定制+QQ3865328597", null);  
                                              
      mainLayout.addView(createCollapsibleSection(context, "刺球美化", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL); // 修复：添加LinearLayout导入
    
    String[] switchNames = {/*"刺球改透明", "刺球改白光无", "刺球改绿光无", "刺球改蓝光无", "刺球改泳圈","刺球改泡泡"*/};
    for (int i = 0; i < switchNames.length; i++) {
        final String switchName = switchNames[i];
        Switch switchButton = new Switch(context);
        switchButton.setText(switchName);
        switchButton.setTag(switchName); // 设置Tag
        switchButton.setTextColor(Color.parseColor("#B9A6FF"));
        applySwitchStyle(switchButton);

        // 开关状态监听
        switchButton.setOnCheckedChangeListener(
                (CompoundButton buttonView, boolean isChecked) -> {
                    if (isChecked) {
                        // 开关被打开时的操作
                        switch (switchName) {
                            case "刺球改透明":
                                setAssetsFile(context,"ci1","cici2.unity3d_u_4","/ui/activity22/");        
                                setAssetsFile(context,"ci2","ciqiu_dataosha.unity3d_u_4","/alltextures/ingameeffect/");
                                setAssetsFile(context,"ci3","ciqiu_yuzhou_5.unity3d_u_4","/alltextures/map3/");
                                break;
                            case "刺球改白光无":
                                setAssetsFile(context,"ci4","cici2.unity3d_u_4","/ui/activity22/");        
                                setAssetsFile(context,"ci5","ciqiu_dataosha.unity3d_u_4","/alltextures/ingameeffect/");
                                setAssetsFile(context,"ci6","ciqiu_yuzhou_5.unity3d_u_4","/alltextures/map3/");
                                break;
                            case "刺球改绿光无":
                                setAssetsFile(context,"ci7","cici2.unity3d_u_4","/ui/activity22/");        
                                setAssetsFile(context,"ci8","ciqiu_dataosha.unity3d_u_4","/alltextures/ingameeffect/");
                                setAssetsFile(context,"ci9","ciqiu_yuzhou_5.unity3d_u_4","/alltextures/map3/");
                                break;
                            case "刺球改蓝光无":
                                setAssetsFile(context,"ci10","cici2.unity3d_u_4","/ui/activity22/");        
                                setAssetsFile(context,"ci11","ciqiu_dataosha.unity3d_u_4","/alltextures/ingameeffect/");
                                setAssetsFile(context,"ci12","ciqiu_yuzhou_5.unity3d_u_4","/alltextures/map3/");
                                break;
                            case "刺球改泳圈":
                                setAssetsFile(context,"ci49","cici2.unity3d_u_4","/ui/activity22/");        
                                setAssetsFile(context,"ci50","ciqiu_dataosha.unity3d_u_4","/alltextures/ingameeffect/");
                                setAssetsFile(context,"ci51","ciqiu_yuzhou_5.unity3d_u_4","/alltextures/map3/");
                                break;
                            case "刺球改泡泡":
                                setAssetsFile(context,"ci52","cici2.unity3d_u_4","/ui/activity22/");        
                                setAssetsFile(context,"ci53","ciqiu_dataosha.unity3d_u_4","/alltextures/ingameeffect/");
                                setAssetsFile(context,"ci54","ciqiu_yuzhou_5.unity3d_u_4","/alltextures/map3/");
                                break;
                        }
                    }
                }
        );
        layout.addView(switchButton); // 将Switch按钮添加到LinearLayout中
    }
    return layout;
}));
                        
mainLayout.addView(new View(context), spacingParams);
                        
                        
   mainLayout.addView(createCollapsibleSection(context, "孢子美化", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL); // 修复：添加LinearLayout导入                     
                        
         
       String[] switchNames = {/*"爱心蛋改彩色泡泡","爱心蛋改璀璨之星","爱心蛋改王子", "爱心蛋改公主", "爱心蛋改嘉年华", "爱心蛋改塔坦杯","爱心蛋改NGF之翼","爱心蛋改快手logo","爱心蛋改抖音logo","爱心蛋改虎牙logo","辣妹子改骨头", "荷叶改金箍棒", "荷叶改泡泡", "荷叶改荣耀黑洞", "荷叶改微型黑洞", "荷叶改无","荷叶改泳圈"*/};
        for (int i = 0; i < switchNames.length; i++) {
        final String switchName = switchNames[i];
        Switch switchButton = new Switch(context);
        switchButton.setText(switchName);
        switchButton.setTag(switchName); // 设置Tag
        switchButton.setTextColor(Color.parseColor("#ff000000"));
        applySwitchStyle(switchButton);       
                                        
                
           // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        if (isChecked) {
                            // 开关被打开时的操作
                            switch (switchName) {
                            
                                case "爱心蛋改彩色泡泡":
                                setAssetsFile(context,"H15","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                    
                                case "爱心蛋改璀璨之星":
                                setAssetsFile(context,"H16","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                
                                case "爱心蛋改王子":
                                setAssetsFile(context,"H22","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                    
                                case "爱心蛋改公主":
                                setAssetsFile(context,"H23","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                
                                case "爱心蛋改嘉年华":
                                setAssetsFile(context,"H25","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                    
                                case "爱心蛋改塔坦杯":
                                setAssetsFile(context,"H27","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                case "爱心蛋改BGF之翼":
                                setAssetsFile(context,"BGFzy","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                case "爱心蛋改快手logo":
                                setAssetsFile(context,"kslogo","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                case "爱心蛋改抖音logo":
                                setAssetsFile(context,"dylogo","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                case "爱心蛋改虎牙logo":
                                setAssetsFile(context,"hylogo","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                case "辣妹子改骨头":
                                setAssetsFile(context,"yq.so","bz_sf135.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                case "荷叶改金箍棒":
                                setAssetsFile(context,"H2","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                    
                                case "荷叶改泡泡":
                                setAssetsFile(context,"H4","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                
                                case "荷叶改荣耀黑洞":
                                setAssetsFile(context,"H5","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                    
                                case "荷叶改微型黑洞":
                                setAssetsFile(context,"H6","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                
                                case "荷叶改无":
                                setAssetsFile(context,"H7","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break;
                                    
                                case "荷叶改泳圈":
                                setAssetsFile(context,"H9","bz_sf146.unity3d_u_4","/alltextures/ballmaterial/");
                                break; 
                                        
                                 
                           }
                    }
                }
        );
        layout.addView(switchButton); // 将Switch按钮添加到LinearLayout中
    }
    return layout;
}));
                        
mainLayout.addView(new View(context), spacingParams);   

mainLayout.addView(createCollapsibleSection(context, "关键词美化", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL); // 修复：添加LinearLayout导入                     
                        
         
       String[] switchNames = {/*"寂寞星球改阿芙娜", "寂寞星球改熊少爷", "寂寞星球改魔星战士", "寂寞星球改库拉拉", "cool西瓜美化无","橘子改黯灭", "橘子改水滴泡泡", "橘子改第五天灾", "橘子改彩云之南","橘子改鸭子舞菲露露","喳喳鸟改大眼怪","喳喳鸟改圣兽玉璧", "喳喳鸟改天空之力", "喳喳鸟改荣耀之光", "喳喳鸟改圣杯"*/};
        for (int i = 0; i < switchNames.length; i++) {
        final String switchName = switchNames[i];
        Switch switchButton = new Switch(context);
        switchButton.setText(switchName);
        switchButton.setTag(switchName); // 设置Tag
        switchButton.setTextColor(Color.parseColor("#ff000000"));
        applySwitchStyle(switchButton);    
        
           // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        if (isChecked) {
                            // 开关被打开时的操作
                            switch (switchName) {
                            
                                case "寂寞星球改阿芙娜":
                                setAssetsFile(context,"b1","pifu_017_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"b2","pifu_017_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"b3","pifu_017_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"c1","pifu_017i.unity3d_u_4","/ui/keyskin/");
                                break;
                                    
                                case "寂寞星球改熊少爷":
                                setAssetsFile(context,"b4","pifu_017_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"b5","pifu_017_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"b6","pifu_017_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"c2","pifu_017i.unity3d_u_4","/ui/keyskin/");
                                break;
                                
                                case "寂寞星球改魔星战士":
                                setAssetsFile(context,"d1","pifu_017_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"d2","pifu_017_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"d3","pifu_017_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"e1","pifu_017i.unity3d_u_4","/ui/keyskin/");
                                break;
                                    
                                case "寂寞星球改库拉拉":
                                setAssetsFile(context,"d4","pifu_017_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"d5","pifu_017_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"d6","pifu_017_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"e3","pifu_017i.unity3d_u_4","/ui/keyskin/");
                                break;
                                
                                case "cool西瓜美化无":
                                setAssetsFile(context,"w1.so","pifu_025_lv1.unity3d_u_4","/keyskin/pifu2/");        
                                setAssetsFile(context,"w2.so","pifu_025_lv2.unity3d_u_4","/keyskin/pifu2/");
                                setAssetsFile(context,"w3.so","pifu_025_lv3.unity3d_u_4","/keyskin/pifu2/");
                                setAssetsFile(context,"e3","pifu_025_lv1.unity3d_u_4","/ui/keyskin/");
                                break;
                                
                                case "橘子改黯灭":
                                setAssetsFile(context,"b10","pifu_031_lv1.unity3d_u_5","/keyskin/pifu3/");        
                                setAssetsFile(context,"b11","pifu_031_lv2.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"b12","pifu_031_lv3.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"c4","pifu_031i.unity3d_u_4","/ui/keyskin/");
                                break;
                                    
                                case "橘子改水滴泡泡":
                                setAssetsFile(context,"b16","pifu_031_lv1.unity3d_u_5","/keyskin/pifu3/");        
                                setAssetsFile(context,"b17","pifu_031_lv2.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"b18","pifu_031_lv3.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"c6","pifu_031i.unity3d_u_4","/ui/keyskin/");
                                break;
                                
                                case "橘子改第五天灾":
                                setAssetsFile(context,"b13","pifu_031_lv1.unity3d_u_5","/keyskin/pifu3/");        
                                setAssetsFile(context,"b14","pifu_031_lv2.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"b15","pifu_031_lv3.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"c5","pifu_031i.unity3d_u_4","/ui/keyskin/");
                                break;
                                    
                                case "橘子改彩云之南":
                                setAssetsFile(context,"b22","pifu_031_lv1.unity3d_u_5","/keyskin/pifu3/");        
                                setAssetsFile(context,"b23","pifu_031_lv2.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"b24","pifu_031_lv3.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"c8","pifu_031i.unity3d_u_4","/ui/keyskin/");
                                break;
                                
                                case "橘子改鸭子舞菲露露":
                                setAssetsFile(context,"fll1.so","pifu_031_lv1.unity3d_u_5","/keyskin/pifu3/");        
                                setAssetsFile(context,"fll2.so","pifu_031_lv2.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"fll3.so","pifu_031_lv3.unity3d_u_5","/keyskin/pifu3/");
                                setAssetsFile(context,"fll1.so","pifu_031i.unity3d_u_4","/ui/keyskin/");
                                break;
                                case "喳喳鸟改大眼怪":
                                setAssetsFile(context,"b28","pifu_018_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"b29","pifu_018_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"b30","pifu_018_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"c10","pifu_018i.unity3d_u_4","/ui/keyskin/");
                                break;
                                
                                case "喳喳鸟改圣兽玉璧":
                                setAssetsFile(context,"b31","pifu_018_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"b32","pifu_018_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"b33","pifu_018_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"c11","pifu_018i.unity3d_u_4","/ui/keyskin/");
                                break;
                                    
                                case "喳喳鸟改天空之力":
                                setAssetsFile(context,"b34","pifu_018_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"b35","pifu_018_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"b36","pifu_018_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"c12","pifu_018i.unity3d_u_4","/ui/keyskin/");
                                break;
                                
                                case "喳喳鸟改荣耀之光":
                                setAssetsFile(context,"b37","pifu_018_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"b38","pifu_018_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"b39","pifu_018_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"c13","pifu_018i.unity3d_u_4","/ui/keyskin/");
                                break;
                                    
                                case "喳喳鸟改圣杯":
                                setAssetsFile(context,"b40","pifu_018_lv1.unity3d_u_4","/keyskin/pifu1/");        
                                setAssetsFile(context,"b41","pifu_018_lv2.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"b42","pifu_018_lv3.unity3d_u_4","/keyskin/pifu1/");
                                setAssetsFile(context,"c14","pifu_018i.unity3d_u_4","/ui/keyskin/");
                                break;
                                        
                                 
                           }
                    }
                }
        );
        layout.addView(switchButton); // 将Switch按钮添加到LinearLayout中
    }
    return layout;
}));
                        
mainLayout.addView(new View(context), spacingParams); 

mainLayout.addView(createCollapsibleSection(context, "昵称美化", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL); // 修复：添加LinearLayout导入  
    
   String[] switchNames = {/*"足球美化路飞","足球美化柯南","猫猫美化猪猪侠","月亮美化蔡徐坤","月亮美化阿芙坤","月亮美化鬼畜球球"*/};
        for (int i = 0; i < switchNames.length; i++) {
        final String switchName = switchNames[i];
        Switch switchButton = new Switch(context);
        switchButton.setText(switchName);
        switchButton.setTag(switchName); // 设置Tag
        switchButton.setTextColor(Color.parseColor("#ff000000"));
        applySwitchStyle(switchButton);    
        
           // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        if (isChecked) {
                            // 开关被打开时的操作
                            switch (switchName) {
                            
                                case "足球美化路飞":
                                setAssetsFile(context,"lfeincheng","38.unity3d_u_4","/alltextures/iconmaterial/");        
                                setAssetsFile(context,"lfeincheng","38.unity3d_u_4","/alltextures/iconmaterial");
                                setAssetsFile(context,"lfeincheng","38.unity3d_u_4","/alltextures/iconmaterial");
                                break; 
                                
                                case "足球美化柯南":
                                setAssetsFile(context,"knanncheng2","38.unity3d_u_4","/alltextures/iconmaterial/");        
                                setAssetsFile(context,"knanncheng2","38.unity3d_u_4","/alltextures/iconmaterial");
                                setAssetsFile(context,"knanncheng2","38.unity3d_u_4","/alltextures/iconmaterial");
                                break; 
                                
                                case "猫猫美化猪猪侠":
                                setAssetsFile(context,"zzxiancheng","1.unity3d_u_4","/alltextures/iconmaterial/");        
                                setAssetsFile(context,"zzxiancheng","1.unity3d_u_4","/alltextures/iconmaterial");
                                setAssetsFile(context,"zzxiancheng","1.unity3d_u_4","/alltextures/iconmaterial");
                                break; 
                                
                                case "月亮改蔡徐坤":
                                setAssetsFile(context,"cxkunncheng","3.unity3d_u_4","/alltextures/iconmaterial/");        
                                setAssetsFile(context,"cxkunncheng","3.unity3d_u_4","/alltextures/iconmaterial");
                                setAssetsFile(context,"cxkunncheng","3.unity3d_u_4","/alltextures/iconmaterial");
                                break;
                                        
                                 
                           }
                    }
                }
        );
        layout.addView(switchButton); // 将Switch按钮添加到LinearLayout中
    }
    return layout;
}));
                        
mainLayout.addView(new View(context), spacingParams); 


mainLayout.addView(createCollapsibleSection(context, "音效包美化", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL); // 修复：添加LinearLayout导入  
    
   String[] switchNames = {/*"铃铛吞噬特效","足球美化柯南","猫猫美化猪猪侠","月亮美化蔡徐坤","月亮美化阿芙坤","月亮美化鬼畜球球"*/};
        for (int i = 0; i < switchNames.length; i++) {
        final String switchName = switchNames[i];
        Switch switchButton = new Switch(context);
        switchButton.setText(switchName);
        switchButton.setTag(switchName); // 设置Tag
        switchButton.setTextColor(Color.parseColor("#ff000000"));
        applySwitchStyle(switchButton);    
        
           // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        if (isChecked) {
                            // 开关被打开时的操作
                            switch (switchName) {
                            
                                case "铃铛吞噬音效":
                                setAssetsFile(context,"sound","levelsound_5.unity3d_u_4","/sound/uisound1/");
                                break;
                                
                                case "射击游戏改静谧空间":
                                setAssetsFile(context,"jmkj1","normal_18_divide.unity3d_u_4","/sound/sound56");
                        setAssetsFile(context,"jmkj2","normal_18_eat.unity3d_u_4","/sound/sound56");
                        setAssetsFile(context,"jmkj3","normal_18_feed_01.unity3d_u_4","/sound/sound56");
                        setAssetsFile(context,"jmkj4","normal_18_feed_02.unity3d_u_4","/sound/sound56");
                        setAssetsFile(context,"jmkj5","normal_18_feed_03.unity3d_u_4","/sound/sound56");
                                break;
                                        
                                 
                           }
                    }
                }
        );
        layout.addView(switchButton); // 将Switch按钮添加到LinearLayout中
    }
    return layout;
}));
                        
mainLayout.addView(new View(context), spacingParams); 



mainLayout.addView(createCollapsibleSection(context, "去皮/去除红蓝雾", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL); // 修复：添加LinearLayout导入  
    
   String[] switchNames = {/*"去除皮肤","去除红雾","去除蓝雾"*/};
        for (int i = 0; i < switchNames.length; i++) {
        final String switchName = switchNames[i];
        Switch switchButton = new Switch(context);
        switchButton.setText(switchName);
        switchButton.setTag(switchName); // 设置Tag
        switchButton.setTextColor(Color.parseColor("#ff000000"));
        applySwitchStyle(switchButton);    
        
           // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        if (isChecked) {
                            // 开关被打开时的操作
                            switch (switchName) {
                            
                            case "去除皮肤":
                                删除文件("/storage/emulated/0/Android/data/com.ztgame.bob/files/shopconfig.unity3d_u_734381d1e99080797bbfff029878b718");
                                删除文件("//storage/emulated/0/Android/data/com.excelliance.dualaid/gameplugins/com.ztgame.bob/files/shopconfig.unity3d_u_734381d1e99080797bbfff029878b718");
                                删除文件("/storage/emulated/0/Android/data/com.pubg.imobilei/gameplugins/com.ztgame.bob/files/shopconfig.unity3d_u_734381d1e99080797bbfff029878b718");
                            break;
                            
                            case "去除红雾":
                                setAssetsFile(context,"qchw","partnerdunew.unity3d_u_4","/alltextures/map3");        
                                setAssetsFile(context,"qchw","partnerdunew.unity3d_u_4","/alltextures/map3");
                                setAssetsFile(context,"qchw","partnerdunew.unity3d_u_4","/alltextures/map3");
                             break;
                             
                             case "去除蓝雾":
                                setAssetsFile(context,"qclw","caocongnew.unity3d_u_4","/alltextures/map1");        
                                setAssetsFile(context,"qclw","caocongnew.unity3d_u_4","/alltextures/map1");
                                setAssetsFile(context,"qclw","caocongnew.unity3d_u_4","/alltextures/map1");
                                break;
                                        
                                 
                           }
                    }
                }
        );
        layout.addView(switchButton); // 将Switch按钮添加到LinearLayout中
    }
    return layout;
}));
                        
mainLayout.addView(new View(context), spacingParams); 
                                



                                                                                                                                            
                                        

scrollView.addView(mainLayout);
page4Layout.addView(scrollView);

return page4Layout;

                        
   }                                 
                               
                    
   

               
            

 private LinearLayout createPage5Layout(Context context){
    // 创建 ScrollView 作为最外层布局
    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
    ));

    // 创建原有的 LinearLayout（垂直方向）
    LinearLayout page5Layout = new LinearLayout(context);
    page5Layout.setOrientation(LinearLayout.VERTICAL);
    int margin = dpToPx(16);
    LinearLayout.LayoutParams page5LayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );
    page5LayoutParams.setMargins(margin, margin, margin, margin);
    page5Layout.setLayoutParams(page5LayoutParams);

    // ------------------ 标题文字 ------------------
    TextView textView = new TextView(context);
    textView.setText("同步使用前此处按照要求填写排名");
    textView.setTextSize(16);
    textView.setTextColor(Color.parseColor("#ff000000")); // 紫色
    page5Layout.addView(textView);

    // ------------------ 横向容器: 左侧 EditText，右侧按钮 (指定排名ID) ------------------
    LinearLayout inputRowLayout1 = createInputRowLayout(
            context,
            "输入队友排名ID",
            "保存队友排名ID",
            () -> specifiedRankId,
            (newId) -> specifiedRankId = newId,
            "队友排名id.txt"
    );
    page5Layout.addView(inputRowLayout1);

    // ------------------ 横向容器: 左侧 EditText，右侧按钮 (自身ID) ------------------
    LinearLayout inputRowLayout2 = createInputRowLayout(
            context,
            "输入自身排名ID",
            "保存自身排名ID",
            () -> myRankId,
            (newId) -> myRankId = newId,
            "自身排名id.txt"
    );
    page5Layout.addView(inputRowLayout2);

    // ------------------ 查询排名功能 ------------------
    LinearLayout queryLayout = new LinearLayout(context);
    queryLayout.setOrientation(LinearLayout.VERTICAL);
    queryLayout.setPadding(0, dpToPx(8), 0, dpToPx(8));

    // 输入框
    /*EditText queryEditText = new EditText(context);
    queryEditText.setHint("请输入要查询排名的ID");
    queryEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
    queryEditText.setTextSize(16);
    queryEditText.setTextColor(Color.parseColor("#FFFFFF"));
    queryEditText.setHintTextColor(Color.parseColor("#FFFFFF"));
    queryEditText.setBackground(null);

    // 下划线
    View queryUnderlineView = new View(context);
    queryUnderlineView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(2)
    ));
    queryUnderlineView.setBackgroundColor(Color.parseColor("#FFFFFF"));
    queryEditText.setOnFocusChangeListener((v, hasFocus) -> {
        queryUnderlineView.setBackgroundColor(hasFocus ? Color.parseColor("#FFFFFF") : Color.parseColor("#FFFFFF"));
    });

    LinearLayout queryInputContainer = new LinearLayout(context);
    queryInputContainer.setOrientation(LinearLayout.VERTICAL);
    queryInputContainer.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    queryInputContainer.addView(queryEditText);
    queryInputContainer.addView(queryUnderlineView);

    queryLayout.addView(queryInputContainer);

    // 查询按钮
    Button queryButton = new Button(context);
    queryButton.setText("查询排名");
    queryButton.setTextSize(16);
    queryButton.setTextColor(Color.parseColor("#FFFFFF"));

    GradientDrawable buttonBg = new GradientDrawable();
    buttonBg.setCornerRadius(dpToPx(8));
    buttonBg.setColor(Color.parseColor("#85add8e6"));
    buttonBg.setStroke(dpToPx(1), Color.parseColor("#FFFFFF"));
    queryButton.setBackground(buttonBg);

    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );
    buttonParams.topMargin = dpToPx(8);
    queryButton.setLayoutParams(buttonParams);

    queryLayout.addView(queryButton);

    // 日志窗口
    TextView logTextView = new TextView(context);
    logTextView.setTextSize(14);
    logTextView.setTextColor(Color.parseColor("#000000"));
    logTextView.setBackgroundColor(Color.parseColor("#99ffb9ee"));
    logTextView.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
    logTextView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(150)
    ));
    logTextView.setText("此处下方为排名信息生成日志：\n");
    queryLayout.addView(logTextView);

    // 查询按钮点击事件
    queryButton.setOnClickListener(v -> {
    String inputId = queryEditText.getText().toString().trim();
    if (TextUtils.isEmpty(inputId)) {
        Toast.makeText(context, "蓝莓提示您:输入不能为空", Toast.LENGTH_SHORT).show();
        return;
    }

    logTextView.setText("蓝莓正在为您查询排名信息...\n");

    new Thread(() -> {
        try {
            //网站
            String url = "https://rank.mysqil.top";
            String params = "playerId=" + URLEncoder.encode(inputId, "UTF-8");

            // 配置 HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 发送 POST 数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();

            // 获取响应数据
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder htmlResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlResponse.append(line);
                }
                reader.close();

                // 从 HTML 中解析数据
                String resultMessage = parseRankFromHtml(htmlResponse.toString());

                // 更新日志窗口
                String finalMessage = resultMessage;
                new Handler(Looper.getMainLooper()).post(() -> logTextView.setText(finalMessage));
            } else {
                // 处理服务器错误
                new Handler(Looper.getMainLooper()).post(() -> logTextView.setText("查询失败：服务器错误。\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理网络或解析错误
            new Handler(Looper.getMainLooper()).post(() -> logTextView.setText("查询失败：网络错误或解析错误。\n"));
        }
    }).start();
});*/




    page5Layout.addView(queryLayout);

    // 将原有布局添加到 ScrollView 中
    scrollView.addView(page5Layout);

    // 外层布局，包含滚动布局
    LinearLayout finalLayout = new LinearLayout(context);
    finalLayout.setOrientation(LinearLayout.VERTICAL);
    finalLayout.addView(scrollView);

    return finalLayout; // 返回最终布局
}


// 可折叠区域的创建方法
private LinearLayout createCollapsibleSection(Context context, String title, CollapsibleContentProvider contentProvider) {
    LinearLayout sectionLayout = new LinearLayout(context);
    sectionLayout.setOrientation(VERTICAL);

    // 设置折叠标题的样式
    TextView titleView = new TextView(context);
    titleView.setText(title);
    titleView.setTextSize(12); // 使用相同的小字体大小
    titleView.setTextColor(Color.parseColor("#ff000000")); // 字体颜色
    titleView.setGravity(Gravity.CENTER_VERTICAL);
    titleView.setPadding(16, 16, 16, 16);

    // 设置圆角背景
    GradientDrawable background = new GradientDrawable();
    background.setColor(Color.parseColor("#99ffb9ee")); // 背景色契合主窗体
    background.setCornerRadius(16); // 圆角背景
    titleView.setBackground(background);

    // 创建内容容器并设置为折叠状态
    LinearLayout contentLayout = contentProvider.provideContent();
    contentLayout.setVisibility(GONE); // 初始折叠
    sectionLayout.addView(titleView);
    sectionLayout.addView(contentLayout);

    // 设置点击折叠/展开逻辑
    titleView.setOnClickListener(v -> {
        if (contentLayout.getVisibility() == VISIBLE) {
            contentLayout.setVisibility(GONE);
        } else {
            contentLayout.setVisibility(VISIBLE);
        }
    });

    return sectionLayout;
}

private LinearLayout createPage6Layout(Context context) {
    LinearLayout page6Layout = new LinearLayout(context);
    page6Layout.setOrientation(VERTICAL);
        
    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    LinearLayout switchContainer = new LinearLayout(context);
    switchContainer.setOrientation(VERTICAL);

    // 设置左右相同的边距
    LayoutParams switchContainerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    int margin = dpToPx(16);
    switchContainerParams.setMargins(margin, 0, margin, 0);
    switchContainer.setLayoutParams(switchContainerParams);

    addTextView(context, page6Layout, "作者: 柠檬", null);
    
    addTextView(context, page6Layout, "QQ频道{点击加入}", v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pd.qq.com/s/awflq5ewa?businessType=9"));
            context.startActivity(intent);
            Toast.makeText(context, "正在跳转浏览器...", Toast.LENGTH_SHORT).show();
        });
        
    addTextView(context, page6Layout, "反馈群聊: 422489131 {点击加入}", v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://qm.qq.com/q/8cwhOjSoZq"));
            context.startActivity(intent);
            Toast.makeText(context, "正在跳转浏览器...", Toast.LENGTH_SHORT).show();
        });
    
    addTextView(context, page6Layout, "Telegram: @柠檬 {点击加入}", v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/ningmengtec"));
            context.startActivity(intent);
            Toast.makeText(context, "正在跳转浏览器...", Toast.LENGTH_SHORT).show();
        });
    return page6Layout;
}

    private LinearLayout createInputRowLayout(
            Context context,
            String hintText,
            String buttonText,
            Supplier<Integer> currentValueGetter,
            Consumer<Integer> valueUpdater,
            String fileName) {
    LinearLayout inputRowLayout = new LinearLayout(context);
    inputRowLayout.setOrientation(LinearLayout.HORIZONTAL);
    inputRowLayout.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
    ));
    inputRowLayout.setPadding(0, dpToPx(8), 0, dpToPx(8));

    // ---- EditText ----
    EditText editText = new EditText(context);
    editText.setHint(hintText);
    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
    editText.setTextSize(16);
    editText.setTextColor(Color.parseColor("#ff000000"));       // 字体颜色
    editText.setHintTextColor(Color.parseColor("#50B9A6FF")); // Hint 半透明紫
    editText.setHighlightColor(Color.parseColor("#50B9A6FF")); // 选中文字高亮颜色

    // 去掉原生的下划线背景
    editText.setBackground(null);

    // 包裹 EditText 的容器，带底部横线
    LinearLayout editTextContainer = new LinearLayout(context);
    editTextContainer.setOrientation(LinearLayout.VERTICAL);
    editTextContainer.setLayoutParams(new LinearLayout.LayoutParams(
            0,
            LayoutParams.WRAP_CONTENT,
            1f
    ));

    editTextContainer.addView(editText);

    View underlineView = new View(context);
    underlineView.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            dpToPx(2)
    ));
    underlineView.setBackgroundColor(Color.parseColor("#50B9A6FF"));
    editTextContainer.addView(underlineView);

    editText.setOnFocusChangeListener((v, hasFocus) -> {
        if (hasFocus) {
            underlineView.setBackgroundColor(Color.parseColor("#ff000000"));
        } else {
            underlineView.setBackgroundColor(Color.parseColor("#50B9A6FF"));
        }
    });

    inputRowLayout.addView(editTextContainer);

    // 预先显示当前变量值
    editText.setText(String.valueOf(currentValueGetter.get()));

    // ---- 按钮 ----
    Button saveButton = new Button(context);
    saveButton.setText(buttonText);
    saveButton.setTextSize(16);
    saveButton.setTextColor(Color.parseColor("#ff000000"));

    GradientDrawable buttonBg = new GradientDrawable();
    buttonBg.setCornerRadius(dpToPx(8));
    buttonBg.setColor(Color.parseColor("#99ffb9ee"));
    buttonBg.setStroke(dpToPx(1), Color.parseColor("#ff000000"));
    saveButton.setBackground(buttonBg);

    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
    );
    buttonParams.leftMargin = dpToPx(16);
    saveButton.setLayoutParams(buttonParams);

    saveButton.setOnClickListener(v -> {
    String inputText = editText.getText().toString().trim(); // 获取用户输入
    if (TextUtils.isEmpty(inputText)) {
        Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
        return;
    }
    try {
        // 转换用户输入为数字
        int newId = Integer.parseInt(inputText);
        valueUpdater.accept(newId); // 更新内存中的值

        // 删除旧文件（如果存在）
        boolean 删除成功 = FileUtil.删除文件(context, fileName);
        if (删除成功) {
       // Toast.makeText(context, "旧文件删除成功", Toast.LENGTH_SHORT).show();
        } else {
          //  Toast.makeText(context, "旧文件不存在或删除失败，继续写入新文件", Toast.LENGTH_SHORT).show();
        }

        // 写入新文件到外置存储私有目录
        boolean 写入成功 = FileUtil.写入(context, fileName, newId, false, false);
        if (写入成功) {
            Toast.makeText(context, "保存成功，新的ID=" + newId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "保存失败，请检查存储状态", Toast.LENGTH_SHORT).show();
        }
    } catch (NumberFormatException e) {
        Toast.makeText(context, "输入格式错误，请输入数字", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
});



    inputRowLayout.addView(saveButton);

    return inputRowLayout;
}



    private void addSeekBarWithEditText(Context context, LinearLayout layout, String labelText, int initialValue, int min, int max, SeekBar.OnSeekBarChangeListener listener) {
        LinearLayout seekBarLayout = new LinearLayout(context);
        seekBarLayout.setOrientation(HORIZONTAL);
        seekBarLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40)));
        seekBarLayout.setPadding(dpToPx(4), dpToPx(6), dpToPx(4), dpToPx(6));

        TextView label = new TextView(context);
        label.setText(labelText);
        label.setTextColor(Color.WHITE);
        label.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        seekBarLayout.addView(label);

        SeekBar seekBar = new SeekBar(context);
        seekBar.setMax(max - min);
        seekBar.setProgress(initialValue - min);
        seekBar.setLayoutParams(new LayoutParams(dpToPx(160), LayoutParams.WRAP_CONTENT));
        seekBar.setOnSeekBarChangeListener(listener);
        seekBarLayout.addView(seekBar);

        EditText valueInput = new EditText(context);
        valueInput.setText(String.valueOf(initialValue));
        valueInput.setTextColor(Color.WHITE);
        valueInput.setBackgroundColor(Color.TRANSPARENT);
        valueInput.setLayoutParams(new LayoutParams(dpToPx(50), LayoutParams.WRAP_CONTENT));
        valueInput.setGravity(Gravity.CENTER);
        valueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    int value = Integer.parseInt(charSequence.toString());
                    seekBar.setProgress(value - min);
                } catch (NumberFormatException e) {}
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        seekBarLayout.addView(valueInput);
        layout.addView(seekBarLayout);
    }

    private LinearLayout createCollapsibleSection(Context context, String title, CollapsibleSectionContentProvider contentProvider) {
        LinearLayout sectionLayout = new LinearLayout(context);
        sectionLayout.setOrientation(VERTICAL);
        sectionLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        TextView sectionTitle = new TextView(context);
        sectionTitle.setText(title);
        sectionTitle.setTextColor(Color.WHITE);
        sectionTitle.setTextSize(16);
        sectionTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40)));
        sectionLayout.addView(sectionTitle);

        sectionLayout.addView(contentProvider.provideContent());
        return sectionLayout;
    }
    
    //整数
    private void addSeekBarWithEditText(Context context, LinearLayout parent, String label, int initialValue, int min, int max, ValueChangeListener listener) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    layout.setPadding(8, 4, 8, 4);

    TextView labelView = new TextView(context);
    labelView.setText(label);
    labelView.setTextSize(10); // 小字体
    labelView.setTextColor(Color.parseColor("#ff000000"));
    labelView.setGravity(Gravity.CENTER_VERTICAL);
    labelView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.2f));
    layout.addView(labelView);

    // 设置 SeekBar 居中
    SeekBar seekBar = new SeekBar(context);
    LinearLayout.LayoutParams seekBarParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
    seekBarParams.setMargins(0, 20, 0, 0); // 调整上下边距以居中
    seekBar.setLayoutParams(seekBarParams);
    seekBar.setMax(max - min);
    seekBar.setProgress(initialValue - min);
    seekBar.getThumb().setColorFilter(Color.parseColor("#99ffb9ee"), android.graphics.PorterDuff.Mode.SRC_IN);
    seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#A390E4"), android.graphics.PorterDuff.Mode.SRC_IN);
    layout.addView(seekBar);

    // 配置带圆角背景的输入框
    EditText valueEditText = new EditText(context);
    valueEditText.setText(String.valueOf(initialValue));
    valueEditText.setTextSize(10);
    valueEditText.setTextColor(Color.parseColor("#ff000000"));
    valueEditText.setGravity(Gravity.CENTER);
    
    // 设置输入框圆角背景
    GradientDrawable editTextBackground = new GradientDrawable();
    editTextBackground.setColor(Color.parseColor("#80e9e955")); // 背景颜色
    editTextBackground.setCornerRadius(16); // 圆角半径
    valueEditText.setBackground(editTextBackground);

    valueEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.8f));
    layout.addView(valueEditText);

    // 滑动条和输入框的联动逻辑
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int value = progress + min;
            valueEditText.setText(String.valueOf(value));
            valueEditText.setSelection(valueEditText.getText().length());  // 保持光标在末尾
            listener.onValueChanged(value);
            ConfigUtil.loadConfig(context);
            saveCurrentConfig(); // 保存当前配置
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    valueEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                int value = Integer.parseInt(s.toString());
                if (value >= min && value <= max) {
                    seekBar.setProgress(value - min);
                    listener.onValueChanged(value);
                    saveCurrentConfig(); // 保存当前配置
                }
            } catch (NumberFormatException ignored) {}
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            // 保持光标在末尾
            valueEditText.setSelection(valueEditText.getText().length());
        }
    });

    parent.addView(layout);
}

    
    
    //浮点
    private void addFloatSeekBarWithEditText(Context context, LinearLayout parent, String label, BigDecimal initialValue, BigDecimal min, BigDecimal max, FloatValueChangeListener listener) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    layout.setPadding(8, 4, 8, 4);

    TextView labelView = new TextView(context);
    labelView.setText(label);
    labelView.setTextSize(10);
    labelView.setTextColor(Color.parseColor("#ff000000"));
    labelView.setGravity(Gravity.CENTER_VERTICAL);
    labelView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.2f));
    layout.addView(labelView);

    SeekBar seekBar = new SeekBar(context);
    LinearLayout.LayoutParams seekBarParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
    seekBarParams.setMargins(0, 20, 0, 0);
    seekBar.setLayoutParams(seekBarParams);
    seekBar.setMax((int) (max.subtract(min).multiply(BigDecimal.valueOf(1000)).intValue()));
    seekBar.setProgress((int) (initialValue.subtract(min).multiply(BigDecimal.valueOf(1000)).intValue()));
    seekBar.getThumb().setColorFilter(Color.parseColor("#99ffb9ee"), android.graphics.PorterDuff.Mode.SRC_IN);
    seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#A390E4"), android.graphics.PorterDuff.Mode.SRC_IN);
    layout.addView(seekBar);

    EditText valueEditText = new EditText(context);
    valueEditText.setText(initialValue.toString());  // 直接使用初始值，不进行四舍五入
    valueEditText.setTextSize(10);
    valueEditText.setTextColor(Color.parseColor("#ff000000"));
    valueEditText.setGravity(Gravity.CENTER);

    GradientDrawable editTextBackground = new GradientDrawable();
    editTextBackground.setColor(Color.parseColor("#80e9e955"));
    editTextBackground.setCornerRadius(16);
    valueEditText.setBackground(editTextBackground);

    valueEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.8f));
    layout.addView(valueEditText);

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            BigDecimal value = min.add(BigDecimal.valueOf(progress).divide(BigDecimal.valueOf(1000)));
            valueEditText.setText(value.toString());  // 直接使用值，不进行四舍五入
            listener.onValueChanged(value.floatValue());
            ConfigUtil.loadConfig(context);
            saveCurrentConfig();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    valueEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                BigDecimal value = new BigDecimal(s.toString());
                if (value.compareTo(min) >= 0 && value.compareTo(max) <= 0) {
                    int progress = value.subtract(min).multiply(BigDecimal.valueOf(1000)).intValue();
                    seekBar.setProgress(progress);
                    listener.onValueChanged(value.floatValue());
                    saveCurrentConfig();
                }
            } catch (NumberFormatException ignored) {}
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            valueEditText.setSelection(valueEditText.getText().length()); // 光标置于文本末尾
        }
    });

    parent.addView(layout);
}
    public static void addDoubleSeekBarText(Context context, LinearLayout parent, String label, BigDecimal initialValue, BigDecimal min, BigDecimal max, Consumer<Double> listener) {
    // 使用大范围的缩放因子，支持小数点后 6 位（若需要更多位数，可相应调整）
    int precisionScale = 1_000_000;

    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    layout.setPadding(8, 4, 8, 4);

    // 标签TextView
    TextView labelView = new TextView(context);
    labelView.setText(label);
    labelView.setTextSize(18);
    labelView.setTextColor(Color.parseColor("#B9A6FF"));
    labelView.setGravity(Gravity.CENTER_VERTICAL);
    labelView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.2f));
    layout.addView(labelView);

    // 创建包含SeekBar和数值的RelativeLayout
    RelativeLayout seekContainer = new RelativeLayout(context);
    seekContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2.8f)); // 合并两个权重

    // 设置 SeekBar
    SeekBar seekBar = new SeekBar(context);
    RelativeLayout.LayoutParams seekBarParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );
    seekBar.setLayoutParams(seekBarParams);
    seekBar.setMax((int) (max.subtract(min).multiply(BigDecimal.valueOf(precisionScale)).doubleValue()));
    seekBar.setProgress((int) (initialValue.subtract(min).multiply(BigDecimal.valueOf(precisionScale)).doubleValue()));
    seekBar.getThumb().setColorFilter(Color.parseColor("#80000000"), android.graphics.PorterDuff.Mode.SRC_IN);
    // Changed to transparent pink (50% transparency - #80FFA2F3)
    seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#40FFA2F3"), android.graphics.PorterDuff.Mode.SRC_IN);
    seekContainer.addView(seekBar);

    // 数值显示 TextView（居中在SeekBar上）
    TextView valueTextView = new TextView(context);
    valueTextView.setText(String.format("%.3f", initialValue));
    valueTextView.setTextSize(18);
    valueTextView.setTextColor(Color.parseColor("#B9A6FF"));
    
    RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );
    textParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    valueTextView.setLayoutParams(textParams);
    seekContainer.addView(valueTextView);

    layout.addView(seekContainer);

    // 联动逻辑保持不变
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            double value = min.add(BigDecimal.valueOf(progress / (double) precisionScale)).doubleValue();
            valueTextView.setText(String.format("%.3f", value));
            listener.accept(value);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    parent.addView(layout);
}
//把这段滑条右侧的数值变化框与左侧滑条居中对齐，并且保持其他不变，特别是这个滑动条本身是可以拖动的，不要改完之后不能拖动
//把这段代码，让数值显示框与滑条“接”起来，并且衔接处是直角衔接且高度相同




    // 保存配置方法
    private void saveCurrentConfig() {
        ConfigUtil.saveConfigToTargetAppDir(parameters);
    }
    private void updateMenuHighlight(int pageIndex) {
        resetMenuBackground(gameSettings);
        resetMenuBackground(skinSettings);
        resetMenuBackground(operationSettings);
        resetMenuBackground(beautifySettings);
        resetMenuBackground(rankingSettings);
        resetMenuBackground(advancedSettings);

        if (pageIndex == 0) {
            setRoundedBackground(gameSettings, "#805dd6ff");
        } else if (pageIndex == 1) {
            setRoundedBackground(skinSettings, "#805dd6ff");
        } else if (pageIndex == 2) {
            setRoundedBackground(operationSettings, "#805dd6ff");
        } else if (pageIndex == 3) {
            setRoundedBackground(beautifySettings, "#805dd6ff");
        } else if (pageIndex == 4) {
            setRoundedBackground(rankingSettings, "#805dd6ff");
        } else if (pageIndex == 5) {
            setRoundedBackground(advancedSettings, "#805dd6ff");
        }
    }
    private void resetMenuBackground(TextView textView) {
        textView.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setRoundedBackground(TextView textView, String color) {
        GradientDrawable roundedBackground = new GradientDrawable();
        roundedBackground.setColor(Color.parseColor(color));
        roundedBackground.setCornerRadius(dpToPx(16));
        textView.setBackground(roundedBackground);
    }

    // 接口用于实时更新参数值
    interface ValueChangeListener {
        void onValueChanged(int value);
    }

    // 浮动值
    interface FloatValueChangeListener {
    void onValueChanged(float value); 
    }

    private void applySwitchStyle(Switch switchButton) {
        switchButton.getThumbDrawable().setColorFilter(Color.parseColor("#80ff7485"), PorterDuff.Mode.SRC_IN); // 紫色滑块
        switchButton.getTrackDrawable().setColorFilter(Color.parseColor("#A390E4"), PorterDuff.Mode.SRC_IN); // 浅紫色轨道
    }
    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    
    public static View createSpacer(CustomLayout layout, int dp高度) {
        // 使用 layout.getContext() 获取上下文创建 View
        View spacer = new View(layout.getContext());
        // 使用 CustomLayout 中的 dpToPx 方法进行转换
        int pxHeight = layout.dpToPx(dp高度);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                pxHeight
        );
        spacer.setLayoutParams(params);
        return spacer;
    }

    // 辅助方法：转换 dp 为 px
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
    // 接口定义
    public interface CollapsibleContentProvider {
    LinearLayout provideContent();
    }


    // 辅助接口：提供可折叠区域的内容
    interface CollapsibleSectionContentProvider {
        View provideContent();
        
    }
    
    private void addTextView(Context context, LinearLayout container, String textContent, View.OnClickListener clickListener) {
    TextView textView = new TextView(context);
    textView.setText(textContent);
    textView.setTextSize(12); // 设置字体大小
    textView.setTextColor(Color.parseColor("#ff000000")); // 设置文本颜色
    textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL); // 左对齐并垂直居中
    textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // 添加下划线

    // 设置左右16dp、上下8dp的间距
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );
    int horizontalMargin = dpToPx(16);
    int verticalMargin = dpToPx(8);
    params.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
    textView.setLayoutParams(params);

    // 如果传入的 clickListener 不为 null，则设置点击事件
    if (clickListener != null) {
        textView.setOnClickListener(clickListener);
    }

    container.addView(textView);
}

   
/*
   private void 黑屏值(Context context) {
    new Thread(() -> {
        try {
            // 定义文件名
            String fileName = "memory_value.txt";

            // 初始化文件路径，并确保文件存在，默认值为800
            FileUtil.写入(context, fileName, "800", false, false); // 确保文件存在且初始化为 "800"

            long q1 = a3 - 104;

            while (true) {
                // 每隔500ms检查一次文件内容
                String value = FileUtil.读取(context, fileName, String.class);

                // 如果文件有内容，进行写入操作
                if (value != null && !value.isEmpty()) {
                    // 写入内存
                    memory.setValue(value, q1, memory.TYPE_FLOAT);

                    // 清空文件内容，防止重复执行
                    FileUtil.写入(context, fileName, "", false, false);
                }

                // 每隔500ms再次检查文件
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}).start();
}
*/

    
    public static void performMemorySearchAndCalculateAngle() {
        Handler mainHandler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            try {
                // 执行搜索相关的内存操作
                memory.clearResult();
                memory.setPackageName("com.ztgame.bob");
                memory.setRange(memory.RANGE_ANONYMOUS);
                memory.RangeMemorySearch("20000", memory.TYPE_FLOAT);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 12);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 24);

                long lastToastTime = 0;

                // 进入循环进行内存搜索和角度计算
                while (true) {
                    int resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);

                        for (long addr : resultAddresses) {

                            joystickUp = memory.readFloat(addr - 0x570);
                            joystickDown = memory.readFloat(addr - 0x56C);

                            joystickAngle = (float) Math.toDegrees(Math.atan2(joystickDown, joystickUp));

                            if (joystickAngle < 0) {
                                joystickAngle += 360;
                            }

                            // 使用Handler将计算出来的角度值发送到主线程
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - lastToastTime >= 1000) {
                                lastToastTime = currentTime;

                                // 将计算出来的角度值发送到主线程，更新主线程的joystickAngle
                                mainHandler.post(() -> {
                                    // 更新主线程中的joystickAngle变量
                                    joystickAngle = joystickAngle; // 直接赋值给主线程中的变量
                                    // 你可以在此处更新UI，或者进行其他的主线程操作
                                });
                            }
                        }
                    }

                    Thread.sleep(5); // 避免过度占用CPU
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    

    
// 定义全局字符串变量，用于存储结果
private static String resultData;

// 方法：获取公告
public static void getAnnouncement(final Context context) {
    wy.公告(context, new wy.Callback() {
        @Override
        public void onResult(String result) {
            resultData = result; // 将公告内容存储到变量中
        }

        @Override
        public void onError(Exception e) {
            resultData = "公告获取失败: " + e.getMessage(); // 存储错误信息
        }
    });
}

  public static long[] 提取偏移链(String input) {
    if (input == null || input.isEmpty()) {
        return new long[0]; // 返回空数组
    }

    try {
        // 去掉字符串中的非数字和逗号部分
        input = input.replaceAll("[^0-9A-Fa-fx,]", "").trim();

        // 按逗号分割
        String[] hexValues = input.split(",");

        // 转换为 long 数组
        long[] offsets = new long[hexValues.length];
        for (int i = 0; i < hexValues.length; i++) {
            offsets[i] = Long.decode(hexValues[i].trim()); // 支持 0x 开头的十六进制转换
        }

        return offsets;
    } catch (Exception e) {
        Log.e("提取偏移链", "解析失败: " + e.getMessage());
        return new long[0]; // 如果解析失败返回空数组
    }
}
    public static long[] 提取字符(String input) {
    if (input == null || input.isEmpty()) {
        return new long[0]; // 返回空数组
    }

    try {
        // 去掉字符串中的非数字和逗号部分
        input = input.replaceAll("[^0-9A-Fa-fx,]", "").trim();

        // 按逗号分割
        String[] hexValues = input.split(",");

        // 转换为 long 数组
        long[] offsets = new long[hexValues.length];
        for (int i = 0; i < hexValues.length; i++) {
            offsets[i] = Long.decode(hexValues[i].trim()); // 支持 0x 开头的十六进制转换
        }

        return offsets;
    } catch (Exception e) {
        Log.e("提取偏移链", "解析失败: " + e.getMessage());
        return new long[0]; // 如果解析失败返回空数组
    }
}

        
        // 将 long[] 数组转换为字符串的方法
public static String 数组转字符串(long[] array) {
    if (array == null || array.length == 0) {
        return "空数组";
    }

    StringBuilder result = new StringBuilder();
    for (long value : array) {
        result.append(String.format("0x%X, ", value)); // 格式化为十六进制
    }

    // 去掉最后的逗号和空格
    return result.substring(0, result.length() - 2);
}

    public static int 提取变量(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("输入字符串不能为空");
        }

        // 匹配引号中的数字，例如 "96"
        String regex = "\"msg\":\"(\\d+)\"";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        // 查找匹配
        if (matcher.find()) {
            // 提取第一个捕获组中的数字并转换为 int 类型
            return Integer.parseInt(matcher.group(1));
        }

        // 如果没有匹配到数字，返回 -1 或其他默认值
        return -1;
    }
    
    
    
    
/// 定义一个全局变量用于显示刷新率和 FPS
TextView fpsTextView;
private Choreographer.FrameCallback frameCallback;
private int 捕获画面FPS = 0; // 捕获画面获取的刷新率

private void monitorRootViewDrawFrequency(View rootView) {
    // 初始化 TextView，用于显示 FPS 和刷新率
    fpsTextView = new TextView(rootView.getContext());
    fpsTextView.setTextColor(Color.RED);
    fpsTextView.setTextSize(16);
    fpsTextView.setBackgroundColor(Color.argb(128, 0, 0, 0)); // 半透明背景

    // 将 TextView 添加到根布局
    if (rootView instanceof ViewGroup) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        ((ViewGroup) rootView).addView(fpsTextView, params);
    }

    // 获取设备支持的屏幕刷新率
    final float refreshRate = ((WindowManager) rootView.getContext().getSystemService(Context.WINDOW_SERVICE))
            .getDefaultDisplay()
            .getRefreshRate(); // 真实的设备刷新率，例如 120Hz 或 60Hz

    // 使用 Choreographer 来统计 FPS
    frameCallback = new Choreographer.FrameCallback() {
        private long lastFrameTimeNanos = 0; // 上一次帧的时间
        private int frameCount = 0;         // 帧计数

        @Override
        public void doFrame(long frameTimeNanos) {
            if (lastFrameTimeNanos == 0) {
                lastFrameTimeNanos = frameTimeNanos;
            }

            frameCount++;

            // 计算时间间隔是否超过 1 秒
            if ((frameTimeNanos - lastFrameTimeNanos) >= 1_000_000_000L) { // 1 秒 = 10^9 纳秒
                int appFPS = frameCount; // 当前应用帧率
                frameCount = 0;          // 重置帧计数
                lastFrameTimeNanos = frameTimeNanos;

                // 更新 TextView 显示
                fpsTextView.setText(String.format(
                        "刷新率: %.1f Hz\nFPS: 当前应用刷新率 %d + 捕获画面刷新率 %d\n捕获画面实际刷新率: %d",
                        refreshRate, appFPS, 捕获画面FPS, 捕获画面FPS));
            }

            // 注册下一帧回调
            Choreographer.getInstance().postFrameCallback(this);
        }
    };

    // 启动帧回调
    Choreographer.getInstance().postFrameCallback(frameCallback);

    // 启动捕获画面 FPS 检测
    捕获画面.startFPSDetection(16, new 捕获画面.FPSCallback() {
        @Override
        public void onFPSDetected(int fps) {
            // 更新捕获画面的刷新率
            捕获画面FPS = fps;
        }
    });
}
            
            
            
            
// 全局变量
private boolean 允许运行 = false; // 控制线程是否可以正常运行

public void 绘制目标球体(int 目标排名ID) {
    new Thread(() -> {
        boolean 已检测到分裂 = false; // 用于标记是否已经检测到目标球体分裂
        while (允许运行) { // 线程运行受全局变量控制
            try {
                // 清除之前绘制的形状
                drawView.clearAllShapes();

                // 用于记录目标排名ID的最大体积球体
                int 目标序号 = -1;
                float 最大半径 = -1;
                int 相同排名ID数量 = 0; // 用于统计相同排名ID的球体数量

                // 遍历玩家信息列表
                for (int i = 0; i < 玩家信息列表.size(); i++) {
                    String 玩家信息 = 玩家信息列表.get(i);

                    try {
                        // 解析玩家信息中的编号、坐标和半径
                        String[] parts = 玩家信息.split(" ");
                        String 编号部分 = parts[0].replace(".", ""); // "1."
                        int 编号 = Integer.parseInt(编号部分);
                        float screenX = Float.parseFloat(parts[1].replace("X=", ""));
                        float screenY = Float.parseFloat(parts[2].replace("Y=", ""));
                        float radius = Float.parseFloat(parts[3].replace("半径=", ""));
                        int rankId = Integer.parseInt(parts[4].replace("排名ID=", ""));

                        // 判断是否为目标排名ID
                        if (rankId == 目标排名ID) {
                            相同排名ID数量++; // 统计目标排名ID的球体数量

                            // 判断是否为最大半径球体
                            if (radius > 最大半径) {
                                最大半径 = radius;
                                目标序号 = 编号;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); // 处理解析失败的异常
                    }
                }

                // 判断目标球体是否一分为二
                if (相同排名ID数量 >= 2 && !已检测到分裂) {
                    已检测到分裂 = true; // 标记已经检测到分裂


                    // 结束当前线程
                    break;
                }

                // 如果找到了目标球体，计算屏幕坐标并绘制
                if (目标序号 != -1) {
                    for (int i = 0; i < 玩家信息列表.size(); i++) {
                        String 玩家信息 = 玩家信息列表.get(i);

                        try {
                            // 解析玩家信息中的编号、坐标和半径
                            String[] parts = 玩家信息.split(" ");
                            String 编号部分 = parts[0].replace(".", ""); // "1."
                            int 编号 = Integer.parseInt(编号部分);
                            float screenX = Float.parseFloat(parts[1].replace("X=", ""));
                            float screenY = Float.parseFloat(parts[2].replace("Y=", ""));
                            float radius = Float.parseFloat(parts[3].replace("半径=", ""));

                            // 如果是目标序号，进行绘制
                            if (编号 == 目标序号) {
                                // 屏幕中心坐标与比例
                                float 屏幕中心相对地图的x = memory.readFloat(a3 - 100);
                                float 屏幕中心相对地图的y = 200 - memory.readFloat(a3 - 96);

                                float startX = parameters.分辨率x / 2;
                                float startY = parameters.分辨率y / 2;

                                // 计算屏幕坐标
                                float screenXa = ((screenX - 屏幕中心相对地图的x)
                                        * 23.25f * parameters.比例
                                        * (23.25f * parameters.比例 / 视图比例)) + startX;
                                float screenYa = ((screenY - 屏幕中心相对地图的y)
                                        * 23.25f * parameters.比例
                                        * (23.25f * parameters.比例 / 视图比例)) + startY;

                                // 绘制球体
                                drawView.addCircle(
                                        screenXa,
                                        screenYa,
                                        radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例),
                                        Color.YELLOW,
                                        6f
                                );
                                break; // 已找到并绘制，退出循环
                            }
                        } catch (Exception e) {
                            e.printStackTrace(); // 处理解析失败的异常
                        }
                    }
                }

                // 每10毫秒刷新一次
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
}
        
        
        public static boolean 跳转网页(Context context, String 网络链接) {
        Uri 链接=Uri.parse(网络链接);
        Intent 打开链接=new Intent(Intent.ACTION_VIEW, 链接);
        context.startActivity(打开链接);
        return true;
	}
	


    public static void setAssetsFile(Context context, String assetsFilePath, String newName, String appendedPath) {
    try {
        // 记录开始复制文件
        //XposedBridge.log("MyFileUtils: 开始复制文件，assets路径: " + assetsFilePath);

        // 打开 assets 中的文件
        InputStream inputStream = context.getAssets().open(assetsFilePath);
        if (inputStream == null) {
            //XposedBridge.log("MyFileUtils: assets文件为空，路径: " + assetsFilePath);
            throw new IOException("在 assets 中未找到文件: " + assetsFilePath);
        }
        //XposedBridge.log("MyFileUtils: 成功打开assets文件: " + assetsFilePath);

        // 获取外部存储的私有目录
        File baseDir = context.getExternalFilesDir(null);
        if (baseDir == null) {
            //XposedBridge.log("MyFileUtils: 获取外部存储私有目录失败");
            throw new IOException("无法获取外部存储私有目录");
        }
        //XposedBridge.log("MyFileUtils: 获取外部存储私有目录: " + baseDir.getAbsolutePath());

        // 拼接固定基础路径与追加的路径，形成最终的目标目录
        File targetDir = new File(baseDir, "vercache2022/android/common/data/" + appendedPath);
        //XposedBridge.log("MyFileUtils: 目标目录: " + targetDir.getAbsolutePath());

        // 检查目标目录是否存在，如不存在则创建
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (created) {
                //XposedBridge.log("MyFileUtils: 成功创建目标目录: " + targetDir.getAbsolutePath());
            } else {
                //XposedBridge.log("MyFileUtils: 目标目录创建失败或目录已存在: " + targetDir.getAbsolutePath());
            }
        }

        // 在目标目录下创建新文件
        File targetFile = new File(targetDir, newName);
        //XposedBridge.log("MyFileUtils: 目标文件路径: " + targetFile.getAbsolutePath());
        OutputStream outputStream = new FileOutputStream(targetFile);

        // 复制文件数据，记录每次读取的字节数
        byte[] buffer = new byte[1024];
        int length;
        int totalBytes = 0;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
            totalBytes += length;
            // 打印每次复制的累计字节数（日志较详细，如文件较大可选择注释该日志）
            //XposedBridge.log("MyFileUtils: 已复制 " + totalBytes + " 字节");
        }

        // 确保数据写入完成并关闭流
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        //XposedBridge.log("MyFileUtils: 文件复制完成，总共复制字节数: " + totalBytes);
    } catch (IOException e) {
        // 出现异常时记录错误日志
        //XposedBridge.log("MyFileUtils: 文件复制过程中出错: " + e.toString());
        //XposedBridge.log("MyFileUtils: 目标目录: " + targetDir.getAbsolutePath());
    e.printStackTrace();
    }
}



        
    public static boolean deleteFile(String filename) {
        return new File(filename).delete();
    }
        
        public static boolean 删除文件(String folder) {
        if (folder == null || folder.length() == 0 || folder.trim().length() == 0) {
            return true;
        }
        File file = new File(folder);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }
    
    private String parseRankFromHtml(String html) {
    try {
        // 根据返回的 HTML 提取相关字段
        String name = html.split("中文ID:")[1].split("</p>")[0].trim();
        String qqId = html.split("数字ID:")[1].split("</p>")[0].trim();
        String rank = html.split("全服排名:")[1].split("</p>")[0].trim();

        return "查询成功！\n" +
                "中文ID: " + name + "\n" +
                "数字ID: " + qqId + "\n" +
                "全服排名: " + rank;
    } catch (Exception e) {
        e.printStackTrace();
        return "查询失败：无法解析网页内容。\n";
    }
}
    
}
