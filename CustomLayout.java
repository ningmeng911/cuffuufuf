package com.auto.tone;

// Android 基础包
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.Log;

// 第三方库
import com.google.android.material.slider.Slider;
import de.robv.android.xposed.XposedBridge;

// Java 标准库
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Field; // 添加这一行
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

// 项目内部类
import com.auto.tone.CircleView;
import com.auto.tone.DebugView;
import com.auto.tone.Sample;

public class UIView extends FrameLayout {

    private Map<Integer, SparseArray<Parcelable>> pageStates = new HashMap<>();
    private int currentPage = 0;
    private Map<Integer, SparseArray<Parcelable>> scriptPageStates = new HashMap<>();
    private int currentScriptPage = 0;
    private static final int
        ID_PAGING1_ROOT = View.generateViewId(),
        ID_PAGING2_ROOT = View.generateViewId(),
        ID_PAGING3_ROOT = View.generateViewId(),
        ID_PAGING4_ROOT = View.generateViewId(),
        ID_CHECKBOX1_MODE = View.generateViewId(),
        ID_CHECKBOX2_MODE = View.generateViewId(),
        ID_CHECKBOX3_MODE = View.generateViewId(),
        ID_CHECKBOX4_MODE = View.generateViewId(),
        ID_CHECKBOX5_MODE = View.generateViewId(),
        ID_CHECKBOX6_MODE = View.generateViewId(),
        ID_CHECKBOX7_MODE = View.generateViewId(),
        ID_CHECKBOX8_MODE = View.generateViewId(),
        ID_CHECKBOX9_MODE = View.generateViewId(),
        ID_CHECKBOX10_MODE = View.generateViewId(),
        ID_CHECKBOX11_MODE = View.generateViewId(),
        ID_CHECKBOX12_MODE = View.generateViewId(),
        ID_CHECKBOX13_MODE = View.generateViewId(),
        ID_CHECKBOX14_MODE = View.generateViewId(),
        ID_CHECKBOX15_MODE = View.generateViewId(),
        ID_CHECKBOX16_MODE = View.generateViewId(),
        ID_CHECKBOX17_MODE = View.generateViewId(),
        ID_CHECKBOX18_MODE = View.generateViewId(),
        ID_CHECKBOX19_MODE = View.generateViewId(),
        ID_CHECKBOX20_MODE = View.generateViewId(),
        ID_CHECKBOX21_MODE = View.generateViewId(),
        ID_CHECKBOX22_MODE = View.generateViewId();
    
    private static FrameLayout rootView;
    private static int 触摸id = 8, 点击id = 9;
    private DrawView drawView;  
    public static DebugView debugview;
    public static SeekBarView seekBarView;
    private static Memory memory;
    private static NewCircle newCircle;
    public static float joystickUp, joystickDown, joystickAngle;
    public static Float initialAngle = null;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private boolean isRunning = false;
    private long Viewscale, Arrayaddress;
    private float 视图比例;
    private static Button 卡点, 同步, 同步三角, 锁球;
    
    public UIView(Context context, FrameLayout rootView) {
        super(context);
        init(context);
    
        debugview = new DebugView(context, rootView);
        debugview.updateButtonVisibility(false);
        memory = new Memory();
        newCircle = new NewCircle(context, rootView);
        NewCircle newCircle = new NewCircle(context, rootView);
        
        this.drawView = new DrawView(context);
        // 设置 DrawView 的布局参数
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        this.drawView.setLayoutParams(params);
        rootView.addView(this.drawView);
        drawView.setZ(10);
        
        Integer readValue = 读取(context, "RankId.txt", Integer.class);
        if (readValue != null) {
            // 如果文件中有记录，就更新到指定排名ID变量
            specifiedRankId = readValue;
        } else {
            // 文件不存在或内容不对，使用默认值
            specifiedRankId = 10; // 或其他
        }
            Integer MyRankId1 = 读取(context, "MyRankId.txt", Integer.class);
        if (MyRankId1 != null) {
            // 如果文件中有记录，就更新到指定排名ID变量
            myRankId = MyRankId1;
        } else {
            // 文件不存在或内容不对，使用默认值
            myRankId = 10; // 或其他
        }
        
       绘制玩家线段(context);
    }
    
    private void init(final Context context) {     
        Sample sample = new Sample();
        
        // 获取屏幕尺寸，并计算窗口宽高（宽62%，高76%）
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int viewWidth = dpToPx(524, context);
        int viewHeight = dpToPx(278, context);

        // 设置窗口背景为白色，并设置窗口四个角圆角（例如4dp）
        GradientDrawable windowBackground = new GradientDrawable();
        windowBackground.setColors(new int[]{Color.parseColor("#fff2f9d4"), Color.parseColor("#fff2f9d4")});
        windowBackground.setCornerRadius(dpToPx(4, context));
        setBackground(windowBackground);

        // 设置整个窗口的布局参数并居中显示
        FrameLayout.LayoutParams windowParams = new FrameLayout.LayoutParams(viewWidth, viewHeight);
        windowParams.gravity = Gravity.CENTER;
        setLayoutParams(windowParams);

        // 创建主容器（垂直LinearLayout）
        LinearLayout mainContainer = new LinearLayout(context);
        mainContainer.setOrientation(LinearLayout.VERTICAL);
        LayoutParams mainParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mainContainer.setLayoutParams(mainParams);
        mainContainer.setPadding(0, 0, 0, dpToPx(5, context));
        addView(mainContainer);

        // 1. 标题栏：水平布局，宽度填满窗口，高度35dp，背景色蓝色
        LinearLayout titleBar = new LinearLayout(context);
        titleBar.setOrientation(LinearLayout.HORIZONTAL);
        titleBar.setGravity(Gravity.CENTER_VERTICAL);
        int titleBarHeight = dpToPx(35, context);
        LinearLayout.LayoutParams titleBarParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, titleBarHeight);
        titleBar.setLayoutParams(titleBarParams);
        
        // 创建 15° 斜向渐变背景（左下→右上）
        GradientDrawable gradientDrawable = new GradientDrawable(
        GradientDrawable.Orientation.BL_TR,  // 15° 渐变，左下到右上
        new int[]{Color.parseColor("#FFFAFFE1"), Color.parseColor("#FFFAFFE1")}); // 浅粉→淡紫
        float radius = dpToPx(4, context); // 将4dp转换为像素
        gradientDrawable.setCornerRadii(new float[]{
            radius, radius,   // 左上角：x 和 y 半径
            radius, radius,   // 右上角：x 和 y 半径
            0f, 0f,           // 右下角：x 和 y 半径
            0f, 0f            // 左下角：x 和 y 半径
        });
        titleBar.setBackground(gradientDrawable);
        
        // 标题文本
        TextView titleText = new TextView(context);
        titleText.setText("蓝莓 - 游戏设置");
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleText.setTextColor(Color.parseColor("#FF80956C"));  // 设置白色文字
        LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        titleTextParams.setMargins(dpToPx(5, context), 0, 0, 0);
        titleText.setLayoutParams(titleTextParams);
        titleBar.addView(titleText);
        
        // 关闭按钮（文本样式，显示“X”）
        final TextView closeText = new TextView(context);
        closeText.setText("X");
        closeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        closeText.setTextColor(Color.parseColor("#FF80956C")); // 天鹅绒红关闭按钮
        closeText.setPadding(dpToPx(10, context), dpToPx(5, context), dpToPx(10, context), dpToPx(5, context));
        closeText.setClickable(true);
        closeText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Hook.globalUIView.setVisibility(View.GONE);
            }
        });
        titleBar.addView(closeText);
        mainContainer.addView(titleBar);

        // 2. 内容区域：水平布局，包括左侧导航和右侧分页区域
        LinearLayout contentArea = new LinearLayout(context);
        contentArea.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        contentArea.setLayoutParams(contentParams);
        mainContainer.addView(contentArea);

        // 左侧导航容器
        FrameLayout navWrapper = new FrameLayout(context);
        LinearLayout.LayoutParams navWrapperParams = new LinearLayout.LayoutParams(dpToPx(90, context), LayoutParams.MATCH_PARENT);
        navWrapperParams.setMargins(dpToPx(3, context), dpToPx(3, context), 0, dpToPx(3, context));
        navWrapper.setLayoutParams(navWrapperParams);
        GradientDrawable navBackground = new GradientDrawable();
        navBackground.setColor(Color.parseColor("#00000000")); // 半透淡灰色
        navBackground.setCornerRadius(dpToPx(8, context));
        navWrapper.setBackground(navBackground);

        // 导航栏
        ScrollView navScrollView = new ScrollView(context);
        FrameLayout.LayoutParams navScrollParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        navScrollView.setLayoutParams(navScrollParams);
        LinearLayout navLayout = new LinearLayout(context);
        navLayout.setOrientation(LinearLayout.VERTICAL);
        navLayout.setLayoutParams(new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        navScrollView.addView(navLayout);
        navScrollView.setVerticalScrollBarEnabled(false);
        navScrollView.setHorizontalScrollBarEnabled(false);
        navWrapper.addView(navScrollView);
        contentArea.addView(navWrapper);

        // 导航按钮
        final String[] navItems = {"基础", "脚本", "排名", "关于" };
        final TextView[] navButtons = new TextView[navItems.length];
        final int selectedColor = Color.parseColor("#80FFFFFF");
        final int defaultColor = Color.TRANSPARENT;

        // 分割线
        View divider = new View(context);
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(dpToPx(2, context), LayoutParams.MATCH_PARENT);
        dividerParams.setMargins(dpToPx(2, context), dpToPx(2, context), dpToPx(2, context), dpToPx(2, context));
        divider.setLayoutParams(dividerParams);
        divider.setBackgroundColor(Color.parseColor("#FF80956C")); // 丁香紫分割线
        contentArea.addView(divider);

        // 右侧分页区域
        final FrameLayout paginationArea = new FrameLayout(context);
        LinearLayout.LayoutParams paginationParams = new LinearLayout.LayoutParams(dpToPx(0, context), LayoutParams.MATCH_PARENT, 1);
        paginationArea.setLayoutParams(paginationParams);
        contentArea.addView(paginationArea);

        // 分页容器
        final FrameLayout pageWrapper = new FrameLayout(context);
        FrameLayout.LayoutParams pageWrapperParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        pageWrapper.setLayoutParams(pageWrapperParams);
        paginationArea.addView(pageWrapper);

        // 默认显示基础分页
        View Paging1 = createPaging1Layout(context);
        pageWrapper.addView(Paging1);

        // 导航按钮点击事件
        for (int i = 0; i < navItems.length; i++) {
            final int index = i;
            TextView navItem = new TextView(context);
            navItem.setText(navItems[i]);
            navItem.setId(1000 + i);
            navItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            navItem.setGravity(Gravity.CENTER);
            navItem.setTextColor(Color.parseColor("#FF80956C")); // 正文文字改为紫藤灰
            navItem.setPadding(dpToPx(10, context), dpToPx(10, context), dpToPx(10, context), dpToPx(10, context));
            LinearLayout.LayoutParams navItemParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            navItemParams.setMargins(0, dpToPx(5, context), 0, dpToPx(5, context));
            navItem.setLayoutParams(navItemParams);
            navItem.setBackground(createNavButtonBackground(context, defaultColor));
            navItem.setClickable(true);
            navItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // —— 1. 保存当前页状态 ——
                    if (pageWrapper.getChildCount() > 0) {
                        View oldPage = pageWrapper.getChildAt(0);
                        SparseArray<Parcelable> state = new SparseArray<>();
                        oldPage.saveHierarchyState(state);
                        pageStates.put(currentPage, state);
                    }
    
                    // —— 2. 更新导航按钮背景 ——
                    for (TextView btn : navButtons) {
                        btn.setBackground(createNavButtonBackground(context, defaultColor));
                    }
                    navItem.setBackground(createNavButtonBackground(context, selectedColor));
    
                    // —— 3. 移除旧页面并添加新页面 ——
                    pageWrapper.removeAllViews();
                    View newPage;
                    switch (index) {
                        case 0:
                            newPage = createPaging1Layout(context);
                            break;
                        case 1:
                            newPage = createPaging2Layout(context);
                            break;
                        case 2:
                            newPage = createPaging3Layout(context);
                            break;
                        case 3:
                            newPage = createPaging5Layout(context);
                            break;
                       /* case 4:
                            newPage = createPaging5Layout(context);
                            break;*/
                        default:
                            newPage = new View(context);
                            break;
                    }
                    pageWrapper.addView(newPage);
    
                    // —— 4. 恢复新页面状态 ——
                    SparseArray<Parcelable> saved = pageStates.get(index);
                    if (saved != null) {
                        newPage.restoreHierarchyState(saved);
                    }
    
                    // —— 5. 更新当前页索引 ——
                    currentPage = index;
                }
            });
            navLayout.addView(navItem);
            navButtons[i] = navItem;
        }
        if (navButtons.length > 0) {
            navButtons[0].setBackground(createNavButtonBackground(context, selectedColor));
        }
    }

    // =============== 基础分页 ===============
    private View createPaging1Layout(Context context) {
        // 外层滚动容器，支持内容过多时上下滚动
        ScrollView scrollView = new ScrollView(context);
        scrollView.setId(ID_PAGING1_ROOT);
        FrameLayout.LayoutParams scrollParams = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollParams);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        // 子布局：垂直LinearLayout
        LinearLayout childLayout = new LinearLayout(context);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        childParams.setMargins(0, 0, dpToPx(3, context), dpToPx(3, context));
        childLayout.setLayoutParams(childParams);
        int innerPadding = dpToPx(2, context);
        childLayout.setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
        scrollView.addView(childLayout);

        // 第一个嵌套子布局
        LinearLayout NestedLayout1 = new LinearLayout(context);
        NestedLayout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout1Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout1.setLayoutParams(nestedLayout1Params);
        NestedLayout1.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout1.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout1.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        TextView OptionText1 = new TextView(context);
        OptionText1.setText("过检方式:");
        OptionText1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText1.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        NestedLayout1.addView(OptionText1);

        createCheckBox(context, "占位", ID_CHECKBOX1_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (((CheckBox) v).isChecked()) {
                   // 吐球.setVisibility(View.VISIBLE);
                    同步.setVisibility(View.VISIBLE);
                } else {
                   // 吐球.setVisibility(View.GONE);
                    同步.setVisibility(View.GONE);
                }
            }
        }, NestedLayout1, 32, 0, 0, 0);

        createCheckBox(context, "内存过检", ID_CHECKBOX2_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (((CheckBox) v).isChecked()) {
                    new Thread(() -> {
                        try {
                            
                            memory.setPackageName("com.ztgame.bob");
            
                            long baseAddr = memory.getModuleAddress("libNetHTProtect.so");
                            
                            memory.setValue("1", baseAddr + 0x364010, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x364014, memory.TYPE_DWORD);            
                            memory.setValue("1", baseAddr + 0x364018, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x36401C, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x364020, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x364024, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x364028, memory.TYPE_DWORD);            
                            memory.setValue("1", baseAddr + 0x36402C, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x364030, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x364034, memory.TYPE_DWORD);
                            memory.setValue("1", baseAddr + 0x36400C, memory.TYPE_DWORD);
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                
                }
            }
        }, NestedLayout1, 5, 0, 0, 0);
        
        childLayout.addView(NestedLayout1);

        // 第二个嵌套子布局
        LinearLayout NestedLayout2 = new LinearLayout(context);
        NestedLayout2.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout2Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout2.setLayoutParams(nestedLayout2Params);
        NestedLayout2.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout2.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout2.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        TextView OptionText2 = new TextView(context);
        OptionText2.setText("键位设置:");
        OptionText2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText2.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        NestedLayout2.addView(OptionText2);

        createCheckBox(context, "调试键位(坐标)", ID_CHECKBOX3_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (((CheckBox) v).isChecked()) {
                    debugview.updateButtonVisibility(true);
                    NewCircle.setDebugMode(true);
                    Hook.globalSeekBarView.setDebugMode(true);
                } else {
                    debugview.updateButtonVisibility(false);
                    NewCircle.setDebugMode(false);
                    Hook.globalSeekBarView.setDebugMode(false);
                }
            }
        }, NestedLayout2, 32, 0, 0, 0);

        createCheckBox(context, "视野状态(显隐)", ID_CHECKBOX4_MODE, 
            Sample.视野_状态,
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox)v;
                    if (cb.isChecked()) {
                        Hook.globalSeekBarView.setVisibility(View.VISIBLE);
                    } else {
                        Hook.globalSeekBarView.setVisibility(View.GONE);
                    }
                    Sample.视野_状态 = cb.isChecked();
                    Sample.saveConfig(context);
                }
            }, 
            NestedLayout2, 5, 0, 0, 0);

        childLayout.addView(NestedLayout2);
        
        // 第三个嵌套子布局
        LinearLayout NestedLayout3 = new LinearLayout(context);
        NestedLayout3.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout3Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout3.setLayoutParams(nestedLayout3Params);
        NestedLayout3.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout3.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout3.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        TextView OptionText3 = new TextView(context);
        OptionText3.setText("数据设置:");
        OptionText3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText3.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        NestedLayout3.addView(OptionText3);

        createCheckBox(context, "初始绘制(技战室)", ID_CHECKBOX5_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (((CheckBox) v).isChecked()) {
                
                new Thread(() -> {
                        try {
                            
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
                                        Arrayaddress = address;
                                        break;
                                    
                                }
                            }
                            
                            memory.clearResult();
                            memory.setRange(memory.RANGE_ANONYMOUS);
                            memory.RangeMemorySearch("14.5", memory.TYPE_FLOAT);
                            memory.MemoryOffset("800", memory.TYPE_FLOAT, -104);
                    
                            if (memory.getResultCount() > 0) {
                                long[] resultAddresses = memory.getResult(1);
                                Viewscale = resultAddresses[0];
                            }
                            memory.clearResult();
                    
                            // 延迟 0.05 秒后执行 获取玩家结构体
                            mainHandler.postDelayed(() -> {
                                获取玩家结构体(context, "玩家坐标数据.txt");
                            }, 50);
                    
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();

                } else {
                
                }
            }
        }, NestedLayout3, 32, 0, 0, 0);
        
        createCheckBox(context, "初始内存(巨行星)", ID_CHECKBOX6_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                
                    IncomingAddress("com.ztgame.bob", mainHandler, Incomingone, Incomingtwo, Incomingthree, Incomingfour, Incomingfive, Incomingsix);
                } else {
                    
                }
            }
        }, NestedLayout3, 5, 0, 0, 0);

        
        childLayout.addView(NestedLayout3);
        
        LinearLayout NestedLayout4 = new LinearLayout(context);
        NestedLayout4.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout4Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout4.setLayoutParams(nestedLayout4Params);
        NestedLayout4.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout4.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout4.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        
        View divider = new View(context);
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dpToPx(1, context));
        dividerParams.setMargins(0, dpToPx(16, context), 0, dpToPx(16, context));
        divider.setLayoutParams(dividerParams);
        divider.setBackgroundColor(Color.parseColor("#FF80956C"));  // 使用和标题栏相同的粉色
        childLayout.addView(divider);
        
        TextView OptionText4 = new TextView(context);
        OptionText4.setText("视野范围:");
        OptionText4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText4.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        LinearLayout.LayoutParams optionText4Params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        optionText4Params.setMargins(0, 0, dpToPx(30, context), 0);
        OptionText4.setLayoutParams(optionText4Params);
        NestedLayout4.addView(OptionText4);
        
        addDoubleSeekBarWithText(context, NestedLayout4, 0.5, 0.5, 5, value -> {
        
            if (Incomingone[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String sy = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(sy, Incomingone[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        childLayout.addView(NestedLayout4);
        
        
        
        LinearLayout NestedLayout5 = new LinearLayout(context);
        NestedLayout5.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout5Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout5.setLayoutParams(nestedLayout5Params);
        NestedLayout5.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout5.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout5.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        TextView OptionText5 = new TextView(context);
        OptionText5.setText("合球动画:");
        OptionText5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText5.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        LinearLayout.LayoutParams optionText5Params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        optionText5Params.setMargins(0, 0, dpToPx(30, context), 0);
        OptionText5.setLayoutParams(optionText5Params);
        NestedLayout5.addView(OptionText5);
        
        addDoubleSeekBarWithText(context, NestedLayout5, 0.588, 0, 0.588, value -> {
        
            if (Incomingtwo[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String dh = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(dh, Incomingtwo[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
        
        childLayout.addView(NestedLayout5);
        
        LinearLayout NestedLayout6 = new LinearLayout(context);
        NestedLayout6.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout6Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout6.setLayoutParams(nestedLayout6Params);
        NestedLayout6.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout6.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout6.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        TextView OptionText6 = new TextView(context);
        OptionText6.setText("吐球连点:");
        OptionText6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText6.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        LinearLayout.LayoutParams optionText6Params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        optionText6Params.setMargins(0, 0, dpToPx(30, context), 0);
        OptionText6.setLayoutParams(optionText6Params);
        NestedLayout6.addView(OptionText6);
        
        addDoubleSeekBarWithText(context, NestedLayout6, 0.05, -1, 0.05, value -> {
        
            if (Incomingfour[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String ld = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(ld, Incomingfour[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
        
        childLayout.addView(NestedLayout6);
        
        LinearLayout NestedLayout7 = new LinearLayout(context);
        NestedLayout7.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout7Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout7.setLayoutParams(nestedLayout7Params);
        NestedLayout7.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout7.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout7.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        TextView OptionText7 = new TextView(context);
        OptionText7.setText("全局速度:");
        OptionText7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText7.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        LinearLayout.LayoutParams optionText7Params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        optionText6Params.setMargins(0, 0, dpToPx(30, context), 0);
        OptionText7.setLayoutParams(optionText6Params);
        NestedLayout7.addView(OptionText7);
        
        addDoubleSeekBarWithText(context, NestedLayout7, 0.14, 0.14, 1.4, value -> {
            String qj = String.valueOf(value);
            new Thread(() -> {
                try {
                    memory.setPackageName("com.ztgame.bob");
                    long moduleAddress = memory.getModuleAddress("libunity.so");
                    long targetAddress = moduleAddress + 0x1524F4; 
                    memory.setValue(qj, targetAddress, memory.TYPE_FLOAT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
        
        childLayout.addView(NestedLayout7);
        
        LinearLayout NestedLayout8 = new LinearLayout(context);
        NestedLayout8.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nestedLayout8Params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        NestedLayout8.setLayoutParams(nestedLayout8Params);
        NestedLayout8.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        NestedLayout8.setBackgroundColor(Color.parseColor("#00000000"));
        NestedLayout8.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        TextView OptionText8 = new TextView(context);
        OptionText8.setText("名字大小:");
        OptionText8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        OptionText8.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        LinearLayout.LayoutParams optionText8Params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        optionText8Params.setMargins(0, 0, dpToPx(30, context), 0);
        OptionText8.setLayoutParams(optionText8Params);
        NestedLayout8.addView(OptionText8);
        
        addDoubleSeekBarWithText(context, NestedLayout8, 0.01, 0.001, 1, value -> {
        
            if (Incomingsix[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String mz = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(mz, Incomingsix[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
        
        childLayout.addView(NestedLayout8);
        
        return scrollView;
    }

    // =============== 脚本分页 ===============
    private View createPaging2Layout(Context context) {
    // 外层容器：垂直LinearLayout
    LinearLayout container = new LinearLayout(context);
    container.setOrientation(LinearLayout.VERTICAL);
    container.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    // 1. 顶部横向导航栏（保留原有结构）
    HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
    LinearLayout.LayoutParams horizontalScrollViewParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dpToPx(42, context));
    horizontalScrollViewParams.setMargins(dpToPx(3, context), 0, dpToPx(3, context), 0);
    horizontalScrollView.setLayoutParams(horizontalScrollViewParams);
    horizontalScrollView.setVerticalScrollBarEnabled(false);
    horizontalScrollView.setHorizontalScrollBarEnabled(false);

    final LinearLayout horizontalNavLayout = new LinearLayout(context);
    horizontalNavLayout.setOrientation(LinearLayout.HORIZONTAL);
    horizontalNavLayout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    horizontalNavLayout.setGravity(Gravity.CENTER_HORIZONTAL);
    horizontalNavLayout.setPadding(dpToPx(5, context), 0, dpToPx(5, context), 0);

    // 2. 下方动态内容容器（关键修改点）
    final ScrollView verticalScrollView = new ScrollView(context);
    final LinearLayout verticalContentLayout = new LinearLayout(context);
    verticalContentLayout.setId(ID_PAGING2_ROOT);
    verticalContentLayout.setOrientation(LinearLayout.VERTICAL);
    verticalContentLayout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    verticalScrollView.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, 0, 1));
    verticalScrollView.addView(verticalContentLayout);
    verticalScrollView.setVerticalScrollBarEnabled(false);
    verticalScrollView.setHorizontalScrollBarEnabled(false);

    // 3. 导航按钮初始化（添加动态切换逻辑）
    final String[] scriptNavItems = {"连点", "三角", "侧合", "后仰", "蛇手", "旋转", "绘制", "卡点", "同步", "占位"};
    for (int i = 0; i < scriptNavItems.length; i++) {
        final int index = i;
        TextView navItem = new TextView(context);
        navItem.setText(scriptNavItems[i]);
        navItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        navItem.setPadding(dpToPx(10, context), dpToPx(10, context), dpToPx(10, context), dpToPx(10, context));
        navItem.setGravity(Gravity.CENTER_VERTICAL);
        navItem.setBackground(createNavButtonBackground(context, Color.parseColor("#fff2f9d4")));
        navItem.setTextColor(Color.parseColor("#FF80956C"));

        LinearLayout.LayoutParams navItemParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, dpToPx(40, context));
        navItemParams.setMargins(dpToPx(2, context), 0, dpToPx(2, context), 0);
        navItem.setLayoutParams(navItemParams);

        navItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // —— 1. 保存当前脚本页状态 ——
                if (verticalContentLayout.getChildCount() > 0) {
                    SparseArray<Parcelable> state = new SparseArray<>();
                    verticalContentLayout.saveHierarchyState(state);
                    scriptPageStates.put(currentScriptPage, state);
                }

                // —— 2. 重置所有按钮背景 & 高亮当前按钮 ——
                for (int j = 0; j < horizontalNavLayout.getChildCount(); j++) {
                    TextView btn = (TextView) horizontalNavLayout.getChildAt(j);
                    btn.setBackground(createNavButtonBackground(context, Color.parseColor("#fff2f9d4")));
                }
                navItem.setBackground(createNavButtonBackground(context, Color.parseColor("#ff75ab6d")));

                // —— 3. 移除旧内容并添加新内容 ——
                verticalContentLayout.removeAllViews();
                switch (index) {
                    case 0:
                        verticalContentLayout.addView(AutoClick0Layout(context));
                        verticalContentLayout.addView(AutoClick1Layout(context));
                        verticalContentLayout.addView(AutoClick2Layout(context));
                        verticalContentLayout.addView(AutoClick3Layout(context));
                        break;
                    case 1:
                        verticalContentLayout.addView(Macro0Layout(context));
                        break;
                    case 2:
                        verticalContentLayout.addView(Macro1Layout(context));
                        break;
                    case 3:
                        verticalContentLayout.addView(Macro2Layout(context));
                        break;
                    case 4:
                        verticalContentLayout.addView(Macro3Layout(context));
                        break;
                    case 5:
                        verticalContentLayout.addView(Macro4Layout(context));
                        break;
                    case 6:
                        verticalContentLayout.addView(DrawLayout(context));
                        break;
                    case 7:
                        verticalContentLayout.addView(AutoCardLayout(context));
                        break;
                    case 8:
                        verticalContentLayout.addView(Sync0Layout(context));
                        verticalContentLayout.addView(Sync1Layout(context));
                        break;
                    case 9:
                        break;
                }

                // —— 4. 恢复新子页面的状态 ——
                SparseArray<Parcelable> saved = scriptPageStates.get(index);
                if (saved != null) {
                    verticalContentLayout.restoreHierarchyState(saved);
                }

                // —— 5. 更新当前脚本页索引 ——
                currentScriptPage = index;
            }
        });
        // 默认选中第一个分页
        if (index == 0) {
            navItem.setBackground(createNavButtonBackground(context, Color.parseColor("#ff75ab6d")));
            verticalContentLayout.addView(AutoClick0Layout(context));
            verticalContentLayout.addView(AutoClick1Layout(context));
            verticalContentLayout.addView(AutoClick2Layout(context));
            verticalContentLayout.addView(AutoClick3Layout(context));
        }

        horizontalNavLayout.addView(navItem);
    }

    horizontalScrollView.addView(horizontalNavLayout);
    container.addView(horizontalScrollView);
    container.addView(verticalScrollView);
    return container;
}

private View AutoClick0Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));

    // 从 Sample 类中读取数值
    Sample sample = new Sample();
    
    layout.addView(createTextRow(context, "吐球宏（长按）：持续点击吐球键"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX7_MODE, 
        Sample.吐球_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.吐球.setVisibility(View.VISIBLE);
                } else {
                    CircleView.吐球.setVisibility(View.GONE);
                }
                Sample.吐球_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.吐球_半径, false, value -> { 
        Sample.吐球_半径 = value; 
        newCircle.updateButtonRadius(CircleView.吐球, Sample.吐球_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 位置浮动 :", Sample.吐球_位置浮动, false, value -> { 
        Sample.吐球_位置浮动 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 点击次数 :", Sample.吐球_点击次数, false, value -> { 
        Sample.吐球_点击次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 每轮延时 :", Sample.吐球_每轮延时, false, value -> { 
        Sample.吐球_每轮延时 = value; 
        Sample.saveConfig(context);
    }));

    return layout;
}

private View AutoClick1Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "分身宏（长按）：持续点击分身键"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX8_MODE, 
        Sample.分身_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.分身.setVisibility(View.VISIBLE);
                } else {
                    CircleView.分身.setVisibility(View.GONE);
                }
                Sample.分身_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.分身_半径, false, value -> { 
        Sample.分身_半径 = value; 
        newCircle.updateButtonRadius(CircleView.分身, Sample.分身_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 间隔浮动 :", Sample.分身_间隔浮动, false, value -> { 
        Sample.分身_间隔浮动 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 位置浮动 :", Sample.分身_位置浮动, false, value -> { 
        Sample.分身_位置浮动 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 点击间隔 :", Sample.分身_点击间隔, false, value -> { 
        Sample.分身_点击间隔 = value; 
        Sample.saveConfig(context);
    }));

    return layout;
}

private View AutoClick2Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "四分宏（杠杠）：单击4分，长按16分"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX9_MODE, 
        Sample.四分_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.四分.setVisibility(View.VISIBLE);
                } else {
                    CircleView.四分.setVisibility(View.GONE);
                }
                Sample.四分_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.四分_半径, false, value -> { 
        Sample.四分_半径 = value; 
        newCircle.updateButtonRadius(CircleView.四分, Sample.四分_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 4分, 点击间隔 :", Sample.四分_杠杆_点击间隔, false, value -> { 
        Sample.四分_杠杆_点击间隔 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 16分, 等待操作 :", Sample.四分_杠杆_十六分_等待操作, false, value -> { 
        Sample.四分_杠杆_十六分_等待操作 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-b 16分, 点击间隔 :", Sample.四分_杠杆_十六分_点击间隔, false, value -> { 
        Sample.四分_杠杆_十六分_点击间隔 = value; 
        Sample.saveConfig(context);
    }));
    
    return layout;
}

private View AutoClick3Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "冲球宏（单击）：持续点击分身键"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX10_MODE, 
        Sample.冲球_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.冲球.setVisibility(View.VISIBLE);
                } else {
                    CircleView.冲球.setVisibility(View.GONE);
                }
                Sample.冲球_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.冲球_半径, false, value -> { 
        Sample.冲球_半径 = value; 
        newCircle.updateButtonRadius(CircleView.冲球, Sample.冲球_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 点击次数 :", Sample.冲球_点击次数, false, value -> { 
        Sample.冲球_点击次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 点击间隔 :", Sample.冲球_点击间隔, false, value -> { 
        Sample.冲球_点击间隔 = value; 
        Sample.saveConfig(context);
    }));

    return layout;
}

private View Macro0Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "三角宏（单击）：按照预设模拟触摸"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX11_MODE, 
        Sample.三角_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.三角.setVisibility(View.VISIBLE);
                } else {
                    CircleView.三角.setVisibility(View.GONE);
                }
                Sample.三角_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.三角_半径, false, value -> { 
        Sample.三角_半径 = value; 
        newCircle.updateButtonRadius(CircleView.三角, Sample.三角_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 展开角度 :", Sample.三角_展开角度, true, value -> { 
        Sample.三角_展开角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-b 滑动距离 :", Sample.三角_滑动距离, false, value -> { 
        Sample.三角_滑动距离 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-c 滑动时长 :", Sample.三角_滑动时长, false, value -> { 
        Sample.三角_滑动时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-d 等待时长 :", Sample.三角_等待时长, false, value -> { 
        Sample.三角_等待时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 合球角度 :", Sample.三角_合球角度, true, value -> { 
        Sample.三角_合球角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-b 滑动距离 :", Sample.三角_滑动距离2, false, value -> { 
        Sample.三角_滑动距离2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-c 滑动时长 :", Sample.三角_滑动时长2, false, value -> { 
        Sample.三角_滑动时长2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 分身次数 :", Sample.三角_分身次数, false, value -> { 
        Sample.三角_分身次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-b 分身间隔 :", Sample.三角_分身间隔, false, value -> { 
        Sample.三角_分身间隔 = value; 
        Sample.saveConfig(context);
    }));

    return layout;
}

private View Macro1Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "侧合宏（拖拽）：按照预设模拟触摸"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX12_MODE, 
        Sample.侧合_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.侧合.setVisibility(View.VISIBLE);
                } else {
                    CircleView.侧合.setVisibility(View.GONE);
                }
                Sample.侧合_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.侧合_半径, false, value -> { 
        Sample.侧合_半径 = value; 
        newCircle.updateButtonRadius(CircleView.侧合, Sample.侧合_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 滑动角度 :", Sample.侧合_滑动角度, true, value -> { 
        Sample.侧合_滑动角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-b 滑动距离 :", Sample.侧合_滑动距离, false, value -> { 
        Sample.侧合_滑动距离 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-c 滑动时长 :", Sample.侧合_滑动时长, false, value -> { 
        Sample.侧合_滑动时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-d 等待时长 :", Sample.侧合_等待时长, false, value -> { 
        Sample.侧合_等待时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 合球角度 :", Sample.侧合_合球角度, true, value -> { 
        Sample.侧合_合球角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-b 滑动距离 :", Sample.侧合_滑动距离2, false, value -> { 
        Sample.侧合_滑动距离2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-c 滑动时长 :", Sample.侧合_滑动时长2, false, value -> { 
        Sample.侧合_滑动时长2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 分身次数 :", Sample.侧合_分身次数, false, value -> { 
        Sample.侧合_分身次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-b 分身间隔 :", Sample.侧合_分身间隔, false, value -> { 
        Sample.侧合_分身间隔 = value; 
        Sample.saveConfig(context);
    }));
    
    return layout;
}

private View Macro2Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "后仰宏（拖拽）：按照预设模拟触摸"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX13_MODE, 
        Sample.后仰_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.后仰.setVisibility(View.VISIBLE);
                } else {
                    CircleView.后仰.setVisibility(View.GONE);
                }
                Sample.后仰_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.后仰_半径, false, value -> { 
        Sample.后仰_半径 = value; 
        newCircle.updateButtonRadius(CircleView.后仰, Sample.后仰_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 滑动角度 :", Sample.后仰_滑动角度, true, value -> { 
        Sample.后仰_滑动角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-b 滑动距离 :", Sample.后仰_滑动距离, false, value -> { 
        Sample.后仰_滑动距离 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-c 滑动时长 :", Sample.后仰_滑动时长, false, value -> { 
        Sample.后仰_滑动时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-d 等待时长 :", Sample.后仰_等待时长, false, value -> { 
        Sample.后仰_等待时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 滑动角度 :", Sample.后仰_滑动角度2, true, value -> { 
        Sample.后仰_滑动角度2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-b 滑动距离 :", Sample.后仰_滑动距离2, false, value -> { 
        Sample.后仰_滑动距离2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "E-a 合球角度 :", Sample.后仰_合球角度, true, value -> { 
        Sample.后仰_合球角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "E-b 滑动距离 :", Sample.后仰_滑动距离3, false, value -> { 
        Sample.后仰_滑动距离3 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "E-c 滑动时长 :", Sample.后仰_滑动时长2, false, value -> { 
        Sample.后仰_滑动时长2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "F-a 分身次数 :", Sample.后仰_分身次数, false, value -> { 
        Sample.后仰_分身次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "F-b 分身间隔 :", Sample.后仰_分身间隔, false, value -> { 
        Sample.后仰_分身间隔 = value; 
        Sample.saveConfig(context);
    }));
    
    return layout;
}

private View Macro3Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "蛇手宏（拖拽）：按照预设模拟触摸"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX14_MODE, 
        Sample.蛇手_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.蛇手.setVisibility(View.VISIBLE);
                } else {
                    CircleView.蛇手.setVisibility(View.GONE);
                }
                Sample.蛇手_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.蛇手_半径, false, value -> { 
        Sample.蛇手_半径 = value; 
        newCircle.updateButtonRadius(CircleView.蛇手, Sample.蛇手_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 滑动角度 :", Sample.蛇手_滑动角度, true, value -> { 
        Sample.蛇手_滑动角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-b 滑动距离 :", Sample.蛇手_滑动距离, false, value -> { 
        Sample.蛇手_滑动距离 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-c 滑动时长 :", Sample.蛇手_滑动时长, false, value -> { 
        Sample.蛇手_滑动时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-d 等待时长 :", Sample.蛇手_等待时长, false, value -> { 
        Sample.蛇手_等待时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 滑动角度 :", Sample.蛇手_滑动角度2, true, value -> { 
        Sample.蛇手_滑动角度2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-b 滑动距离 :", Sample.蛇手_滑动距离2, false, value -> { 
        Sample.蛇手_滑动距离2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-c 滑动时长 :", Sample.蛇手_滑动时长2, false, value -> { 
        Sample.蛇手_滑动时长2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 合球角度 :", Sample.蛇手_合球角度, true, value -> { 
        Sample.蛇手_合球角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-b 滑动距离 :", Sample.蛇手_滑动距离3, false, value -> { 
        Sample.蛇手_滑动距离3 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-c 滑动时长 :", Sample.蛇手_滑动时长3, false, value -> { 
        Sample.蛇手_滑动时长3 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "E-a 分身次数 :", Sample.蛇手_分身次数, false, value -> { 
        Sample.蛇手_分身次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "E-b 分身间隔 :", Sample.蛇手_分身间隔, false, value -> { 
        Sample.蛇手_分身间隔 = value; 
        Sample.saveConfig(context);
    }));
    
    return layout;
}

private View Macro4Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));
    
    layout.addView(createTextRow(context, "旋转宏（拖拽）：按照预设模拟触摸"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX15_MODE, 
        Sample.旋转_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    CircleView.旋转.setVisibility(View.VISIBLE);
                } else {
                    CircleView.旋转.setVisibility(View.GONE);
                }
                Sample.旋转_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.旋转_半径, false, value -> { 
        Sample.旋转_半径 = value; 
        newCircle.updateButtonRadius(CircleView.旋转, Sample.旋转_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 滑动角度 :", Sample.旋转_滑动角度, true, value -> { 
        Sample.旋转_滑动角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-b 滑动距离 :", Sample.旋转_滑动距离, false, value -> { 
        Sample.旋转_滑动距离 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-c 滑动时长 :", Sample.旋转_滑动时长, false, value -> { 
        Sample.旋转_滑动时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-d 等待时长 :", Sample.旋转_等待时长, false, value -> { 
        Sample.旋转_等待时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 滑动角度 :", Sample.旋转_滑动角度2, true, value -> { 
        Sample.旋转_滑动角度2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-b 滑动距离 :", Sample.旋转_滑动距离2, false, value -> { 
        Sample.旋转_滑动距离2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-c 滑动时长 :", Sample.旋转_滑动时长2, false, value -> { 
        Sample.旋转_滑动时长2 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 合球角度 :", Sample.旋转_合球角度, true, value -> { 
        Sample.旋转_合球角度 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-b 滑动距离 :", Sample.旋转_滑动距离3, false, value -> { 
        Sample.旋转_滑动距离3 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-c 滑动时长 :", Sample.旋转_滑动时长3, false, value -> { 
        Sample.旋转_滑动时长3 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "E-a 分身次数 :", Sample.旋转_分身次数, false, value -> { 
        Sample.旋转_分身次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "E-b 分身间隔 :", Sample.旋转_分身间隔, false, value -> { 
        Sample.旋转_分身间隔 = value; 
        Sample.saveConfig(context);
    }));
    
    return layout;
}

private View DrawLayout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));

    // 从 Sample 类中读取数值
    Sample sample = new Sample();
    
    layout.addView(createTextRow(context, "绘制（复选）：绘制球体相关信息"));
    
    LinearLayout horizontalLayout = new LinearLayout(context);
    horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
    horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    horizontalLayout.setPadding(0, dpToPx(4, context), 0, dpToPx(4, context));
    layout.addView(horizontalLayout);
    
    createCheckBox(context, "天线", ID_CHECKBOX16_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (((CheckBox) v).isChecked()) {
                
                    画天线 = true;
                } else {
                
                    画天线 = false;
                    drawView.clearAllShapes();
                }
            }
        }, horizontalLayout, 0, 0, 0, 0);
    createCheckBox(context, "边框", ID_CHECKBOX17_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (((CheckBox) v).isChecked()) {
                
                    画边框 = true;
                } else {
                
                    画边框 = false;
                    drawView.clearAllShapes();
                }
            }
        }, horizontalLayout, 0, 0, 0, 0);
    createCheckBox(context, "排名", ID_CHECKBOX18_MODE, false, new OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (((CheckBox) v).isChecked()) {
                    
                    画排名 = true;
                } else {
                    
                    画排名 = false;
                    drawView.clearAllShapes();
                }
            }
        }, horizontalLayout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 分辨率x :", Sample.绘图_分辨率x, false, value -> { 
        Sample.绘图_分辨率x = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "A-b 分辨率y :", Sample.绘图_分辨率y, false, value -> { 
        Sample.绘图_分辨率y = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createDoubleEditWithButtons(context, "B-a 绘图大小 :", Sample.绘图_大小, false, value -> { 
        Sample.绘图_大小 = value; 
        Sample.saveConfig(context);
    }));
    
    return layout;
}

private View AutoCardLayout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));

    // 从 Sample 类中读取数值
    Sample sample = new Sample();
    
    layout.addView(createTextRow(context, "卡点（单击）：检测目标体积触发分身"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX19_MODE, 
        Sample.卡点_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    卡点.setVisibility(View.VISIBLE);
                } else {
                    卡点.setVisibility(View.GONE);
                }
                Sample.卡点_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.卡点_半径, false, value -> { 
        Sample.卡点_半径 = value; 
        newCircle.updateButtonRadius(UIView.卡点, Sample.卡点_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 检测延迟 :", Sample.卡点_判断延时, false, value -> { 
        Sample.卡点_判断延时 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 分身延迟 :", Sample.卡点_触摸延迟, false, value -> { 
        Sample.卡点_触摸延迟 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 分身次数 :", Sample.卡点_分身次数, false, value -> { 
        Sample.卡点_分身次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-b 点击间隔 :", Sample.卡点_点击间隔, false, value -> { 
        Sample.卡点_点击间隔 = value; 
        Sample.saveConfig(context);
    }));
    return layout;
}

private View Sync0Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));

    // 从 Sample 类中读取数值
    Sample sample = new Sample();
    
    layout.addView(createTextRow(context, "同步（单击）：检测目标体积触发分身"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX20_MODE, 
        Sample.同步_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    同步.setVisibility(View.VISIBLE);
                } else {
                    同步.setVisibility(View.GONE);
                }
                Sample.同步_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.同步_半径, false, value -> { 
        Sample.同步_半径 = value; 
        newCircle.updateButtonRadius(UIView.同步, Sample.同步_半径);
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "B-a 滑动距离 :", Sample.同步_滑动距离, false, value -> { 
        Sample.同步_滑动距离 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "C-a 滑动时长 :", Sample.同步_滑动时长, false, value -> { 
        Sample.同步_滑动时长 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-a 分身次数 :", Sample.同步_分身次数, false, value -> { 
        Sample.同步_分身次数 = value; 
        Sample.saveConfig(context);
    }));
    layout.addView(createIntegerEditWithButtons(context, "D-b 点击间隔 :", Sample.同步_点击间隔, false, value -> { 
        Sample.同步_点击间隔 = value; 
        Sample.saveConfig(context);
    }));
    return layout;
}

private View Sync1Layout(Context context) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.setPadding(dpToPx(8, context), dpToPx(8, context), dpToPx(8, context), dpToPx(8, context));

    // 从 Sample 类中读取数值
    Sample sample = new Sample();
    
    layout.addView(createTextRow(context, "同步三角（单击）：检测目标体积触发分身"));
    
    createCheckBox(context, "按钮状态 - 显示/隐藏", ID_CHECKBOX20_MODE, 
        Sample.同步三角_状态,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                if (cb.isChecked()) {
                    同步三角.setVisibility(View.VISIBLE);
                } else {
                    同步三角.setVisibility(View.GONE);
                }
                Sample.同步三角_状态 = cb.isChecked();
                Sample.saveConfig(context);
            }
        }, 
        layout, 0, 0, 0, 0);
    
    // 添加位置保存/恢复逻辑
    layout.addView(createIntegerEditWithButtons(context, "A-a 按钮半径 :", Sample.同步三角_半径, false, value -> { 
        Sample.同步三角_半径 = value; 
        newCircle.updateButtonRadius(UIView.同步三角, Sample.同步三角_半径);
        Sample.saveConfig(context);
    }));
    return layout;
}

// 按钮拖拽事件处理（在按钮初始化时设置）
private void setupButtonDraggable(View button, String buttonName, Context context) {
    button.setOnTouchListener(new View.OnTouchListener() {
        private int initialX, initialY;
        private float initialTouchX, initialTouchY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialX = (int) v.getX();
                    initialY = (int) v.getY();
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    return true;
                    
                case MotionEvent.ACTION_MOVE:
                    int x = initialX + (int)(event.getRawX() - initialTouchX);
                    int y = initialY + (int)(event.getRawY() - initialTouchY);
                    v.setX(x);
                    v.setY(y);
                    return true;
                    
                case MotionEvent.ACTION_UP:
                    // 保存新位置
                    try {
                        Field xField = Sample.class.getDeclaredField(buttonName + "_坐标x");
                        Field yField = Sample.class.getDeclaredField(buttonName + "_坐标y");
                        
                        xField.setAccessible(true);
                        yField.setAccessible(true);
                        
                        xField.set(null, (int) v.getX());
                        yField.set(null, (int) v.getY());
                        
                        Sample.saveConfig(context);
                    } catch (Exception e) {
                        XposedBridge.log("[位置保存错误] " + e.getMessage());
                    }
                    return true;
            }
            return false;
        }
    });
}

    
    // =============== 排名分页 ===============
    private View createPaging3Layout(Context context) {
        // 整体滚动容器
        ScrollView scrollView = new ScrollView(context);
        scrollView.setId(ID_PAGING3_ROOT);
        FrameLayout.LayoutParams svParams = new FrameLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(svParams);
    
        // 内容容器
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        int padding = dpToPx(16, context);
        container.setPadding(padding, padding, padding, padding);
        scrollView.addView(container);
    
        // —— 输入玩家 ID —— //
      /*  final EditText inputId = new EditText(context);
        inputId.setHint("请输入玩家 ID");
        inputId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        inputId.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        inputId.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF80956C")));
        inputId.setTextColor(Color.parseColor("#FFFFFFFF"));
        container.addView(inputId);
    
        // —— 查询按钮 —— //
        Button btnQuery = new Button(context);
        btnQuery.setText("查询排名");
        btnQuery.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnQuery.setTextColor(Color.parseColor("#FFFFFFFF"));
        btnQuery.setAllCaps(false);
        btnQuery.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        
        // 1. 宽度撑满父布局
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dpToPx(44, context));
        btnParams.setMargins(0, dpToPx(12, context), 0, 0);
        btnQuery.setLayoutParams(btnParams);
        
        // 2. 圆角背景
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setCornerRadius(dpToPx(5, context));
        btnBg.setColor(Color.parseColor("#FF80956C"));
        btnQuery.setBackground(btnBg);
        container.addView(btnQuery);
    
        // —— 结果显示面板 —— //
        final TextView tvResult = new TextView(context);
        tvResult.setText("等待查询…");
        tvResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvResult.setLineSpacing(dpToPx(4, context), 1f);
        tvResult.setTextColor(Color.parseColor("#FF80956C"));
        tvResult.setGravity(Gravity.START);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#FFFFFF"));
        bg.setCornerRadius(dpToPx(4, context));
        tvResult.setBackground(bg);
        int rtPadding = dpToPx(12, context);
        tvResult.setPadding(rtPadding, rtPadding, rtPadding, rtPadding);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        tvParams.setMargins(0, dpToPx(12, context), 0, 0);
        container.addView(tvResult, tvParams);
    
        // —— 点击查询 —— //
        btnQuery.setOnClickListener(v -> {
            String playerId = inputId.getText().toString().trim();
            if (playerId.isEmpty()) {
                tvResult.setText("请输入有效的玩家 ID");
                return;
            }
            btnQuery.setEnabled(false);
            tvResult.setText("查询中…");
    
            new Thread(() -> {
                try {
                    // 网站地址与参数
                    String url = "https://rank.mysqil.top";
                    String params = "playerId=" + URLEncoder.encode(playerId, "UTF-8");
    
                    // 配置 HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
    
                    // 发送 POST 数据
                    OutputStream os = connection.getOutputStream();
                    os.write(params.getBytes());
                    os.flush();
                    os.close();
    
                    // 处理响应
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                        StringBuilder html = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            html.append(line);
                        }
                        reader.close();
    
                        // 解析 HTML，提取排名信息
                        String resultMessage = parseRankFromHtml(html.toString());
    
                        // 更新 UI
                        new Handler(Looper.getMainLooper()).post(() -> {
                            tvResult.setText(resultMessage);
                            btnQuery.setEnabled(true);
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            tvResult.setText("查询失败：服务器错误。");
                            btnQuery.setEnabled(true);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        tvResult.setText("查询失败：网络错误或解析错误。");
                        btnQuery.setEnabled(true);
                    });
                }
            }).start();
        });*/
        
      /*  View divider = new View(context);
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dpToPx(1, context));
        dividerParams.setMargins(0, dpToPx(16, context), 0, dpToPx(16, context));
        divider.setLayoutParams(dividerParams);
        divider.setBackgroundColor(Color.parseColor("#FF80956C"));  // 使用和标题栏相同的粉色
        container.addView(divider);*/
    
        // 设置自身ID输入框
        final EditText inputSelfId = new EditText(context);
        inputSelfId.setHint("请输入自身排名ID");
        inputSelfId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        inputSelfId.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        inputSelfId.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF80956C")));
        inputSelfId.setTextColor(Color.parseColor("#FFFFFFFF"));
        
        // 读取已保存的ID
        Integer savedId = 读取(context, "MyRankId.txt", Integer.class);
        if(savedId != null) inputSelfId.setText(String.valueOf(savedId));
        
        container.addView(inputSelfId);
    
        // 设置按钮
        Button btnSetId = new Button(context);
        btnSetId.setText("保存");
        btnSetId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnSetId.setTextColor(Color.parseColor("#FFFFFFFF"));
        btnSetId.setAllCaps(false);
        
        // 按钮样式
        LinearLayout.LayoutParams btnParamsSet = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dpToPx(44, context));
        btnParamsSet.setMargins(0, dpToPx(12, context), 0, 0);
        btnSetId.setLayoutParams(btnParamsSet);
        GradientDrawable btnBgSet = new GradientDrawable();
        btnBgSet.setCornerRadius(dpToPx(5, context));
        btnBgSet.setColor(Color.parseColor("#ff75ab6d"));//保存按键背景色
        btnSetId.setBackground(btnBgSet);
        container.addView(btnSetId);
    
        // 按钮点击事件
        btnSetId.setOnClickListener(v -> {
            String input = inputSelfId.getText().toString().trim();
            if(input.isEmpty()) {
                Toast.makeText(context, "ID不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            
            try {
                int newId = Integer.parseInt(input);
                
                // 保存到文件，并更新实例中的 myRankId
                new Thread(() -> {
                    保存(context, "MyRankId.txt", newId);
                    
                    // 更新当前实例的变量
                    myRankId = newId;
                    
                    // UI线程反馈
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(context, "ID设置成功", Toast.LENGTH_SHORT).show();
                        // 在ID更新后，通知重绘界面，更新显示
                        drawView.invalidate(); // 触发 onDraw() 重新绘制
                    });
                }).start();
            } catch (NumberFormatException e) {
                Toast.makeText(context, "必须输入数字ID", Toast.LENGTH_SHORT).show();
            }
        });
        
        // 新增：指定排名ID的分割线
        View divider2 = new View(context);
        LinearLayout.LayoutParams dividerParams2 = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dpToPx(1, context));
        dividerParams2.setMargins(0, dpToPx(16, context), 0, dpToPx(16, context));
        divider2.setLayoutParams(dividerParams2);
        divider2.setBackgroundColor(Color.parseColor("#FF80956C"));
        container.addView(divider2);
        
        // 新增：指定排名ID输入框
        final EditText inputSpecifiedId = new EditText(context);
        inputSpecifiedId.setHint("请输入指定排名ID");
        inputSpecifiedId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        inputSpecifiedId.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        inputSpecifiedId.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF80956C")));
        inputSpecifiedId.setTextColor(Color.parseColor("#FFFFFFFF"));
        
        // 读取已保存的指定ID
        Integer savedSpecifiedId = 读取(context, "RankId.txt", Integer.class);
        if(savedSpecifiedId != null) inputSpecifiedId.setText(String.valueOf(savedSpecifiedId));
        container.addView(inputSpecifiedId);
        
        // 新增：设置指定ID按钮
        Button btnSetSpecifiedId = new Button(context);
        btnSetSpecifiedId.setText("保存");
        btnSetSpecifiedId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnSetSpecifiedId.setTextColor(Color.parseColor("#FFFFFFFF"));
        btnSetSpecifiedId.setAllCaps(false);
        
        // 按钮样式（与自身ID按钮一致）
        LinearLayout.LayoutParams btnParamsSpecified = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, dpToPx(44, context));
        btnParamsSpecified.setMargins(0, dpToPx(12, context), 0, 0);
        btnSetSpecifiedId.setLayoutParams(btnParamsSpecified);
        GradientDrawable btnBgSpecified = new GradientDrawable();
        btnBgSpecified.setCornerRadius(dpToPx(5, context));
        btnBgSpecified.setColor(Color.parseColor("#ff75ab6d"));//保存按键背景色
        btnSetSpecifiedId.setBackground(btnBgSpecified);
        container.addView(btnSetSpecifiedId);
        
        // 按钮点击事件
        btnSetSpecifiedId.setOnClickListener(v -> {
            String input = inputSpecifiedId.getText().toString().trim();
            if(input.isEmpty()) {
                Toast.makeText(context, "ID不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            
            try {
                int newId = Integer.parseInt(input);
                
                new Thread(() -> {
                    保存(context, "RankId.txt", newId);
                    specifiedRankId = newId;
                    
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(context, "指定ID设置成功", Toast.LENGTH_SHORT).show();
                        drawView.invalidate();
                    });
                }).start();
            } catch (NumberFormatException e) {
                Toast.makeText(context, "必须输入数字ID", Toast.LENGTH_SHORT).show();
            }
        });
                
        return scrollView;
    }
    
    // =============== 美化分页 ===============
    private View createPaging4Layout(Context context) {
        // 外层容器：垂直LinearLayout
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    
        // 1. 顶部横向导航栏（保留原有结构）
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        LinearLayout.LayoutParams horizontalScrollViewParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dpToPx(42, context));
        horizontalScrollViewParams.setMargins(dpToPx(3, context), 0, dpToPx(3, context), 0);
        horizontalScrollView.setLayoutParams(horizontalScrollViewParams);
        horizontalScrollView.setVerticalScrollBarEnabled(false);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
    
        final LinearLayout horizontalNavLayout = new LinearLayout(context);
        horizontalNavLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalNavLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        horizontalNavLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        horizontalNavLayout.setPadding(dpToPx(5, context), 0, dpToPx(5, context), 0);
    
        // 2. 下方动态内容容器（关键修改点）
        final ScrollView verticalScrollView = new ScrollView(context);
        final LinearLayout verticalContentLayout = new LinearLayout(context);
        verticalContentLayout.setId(View.generateViewId());
        verticalContentLayout.setOrientation(LinearLayout.VERTICAL);
        verticalContentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        verticalScrollView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0, 1));
        verticalScrollView.addView(verticalContentLayout);
        verticalScrollView.setVerticalScrollBarEnabled(false);
        verticalScrollView.setHorizontalScrollBarEnabled(false);
    
        // 3. 导航按钮初始化（添加动态切换逻辑）
        final String[] scriptNavItems = {"箭头", "孢子/荷叶", "孢子/雪花", "孢子/爱心蛋", "孢子/蒲公英"};
        for (int i = 0; i < scriptNavItems.length; i++) {
            final int index = i;
            TextView navItem = new TextView(context);
            navItem.setText(scriptNavItems[i]);
            navItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            navItem.setPadding(dpToPx(10, context), dpToPx(10, context), dpToPx(10, context), dpToPx(10, context));
            navItem.setGravity(Gravity.CENTER_VERTICAL);
            navItem.setBackground(createNavButtonBackground(context, Color.parseColor("#80979ea6")));
            navItem.setTextColor(Color.parseColor("#FF80956C"));
    
            LinearLayout.LayoutParams navItemParams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, dpToPx(40, context));
            navItemParams.setMargins(dpToPx(2, context), 0, dpToPx(2, context), 0);
            navItem.setLayoutParams(navItemParams);
    
            navItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // —— 1. 保存当前脚本页状态 ——
                    if (verticalContentLayout.getChildCount() > 0) {
                        SparseArray<Parcelable> state = new SparseArray<>();
                        verticalContentLayout.saveHierarchyState(state);
                        scriptPageStates.put(currentScriptPage, state);
                    }
    
                    // —— 2. 重置所有按钮背景 & 高亮当前按钮 ——
                    for (int j = 0; j < horizontalNavLayout.getChildCount(); j++) {
                        TextView btn = (TextView) horizontalNavLayout.getChildAt(j);
                        btn.setBackground(createNavButtonBackground(context, Color.parseColor("#80979ea6")));
                    }
                    navItem.setBackground(createNavButtonBackground(context, Color.parseColor("#ff75ab6d")));
    
                    // —— 3. 移除旧内容并添加新内容 ——
                    verticalContentLayout.removeAllViews();
                    switch (index) {
                        case 0:
                            verticalContentLayout.addView(BeautifyLayout0(context));
                            break;
                        case 1:
                            verticalContentLayout.addView(BeautifyLayout1(context));
                            break;
                        case 2:
                            verticalContentLayout.addView(BeautifyLayout2(context));
                            break;
                        case 3:
                            verticalContentLayout.addView(BeautifyLayout3(context));
                            break;
                        case 4:
                            verticalContentLayout.addView(BeautifyLayout4(context));
                            break;
                    }
    
                    // —— 4. 恢复新子页面的状态 ——
                    SparseArray<Parcelable> saved = scriptPageStates.get(index);
                    if (saved != null) {
                        verticalContentLayout.restoreHierarchyState(saved);
                    }
    
                    // —— 5. 更新当前脚本页索引 ——
                    currentScriptPage = index;
                }
            });
            // 默认选中第一个分页
            if (index == 0) {
                navItem.setBackground(createNavButtonBackground(context, Color.parseColor("#ff75ab6d")));
                verticalContentLayout.addView(BeautifyLayout0(context));
            }
    
            horizontalNavLayout.addView(navItem);
        }
    
        horizontalScrollView.addView(horizontalNavLayout);
        container.addView(horizontalScrollView);
        container.addView(verticalScrollView);
        return container;
    }
    
    // 创建各选项的布局内容
    private View BeautifyLayout0(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        LinearLayout S_BeautifyNestedLayout = createBeautifyNestedLayout(context);
        createDownloadmd5CheckBox(context, "恢复原样 (箭头)", "", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/", "", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/ver.xml", "uipic.unity3d", "", S_BeautifyNestedLayout);
        layout.addView(S_BeautifyNestedLayout);
        
        LinearLayout S_BeautifyNestedLayout1 = createBeautifyNestedLayout(context);
        createDownloadmd5CheckBox(context, "箭头 => 地球", "http://wpan.cdndns.site/down/4adc10edb1535bf21a5a1b561a6b130c", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/", "uipic.unity3d_u_b52fd97e30b7e091940415f5fe3b5abf", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/ver.xml", "uipic.unity3d", "b52fd97e30b7e091940415f5fe3b5abf", S_BeautifyNestedLayout1);
        layout.addView(S_BeautifyNestedLayout1);
        
        return layout;
    }
    
    private View BeautifyLayout1(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        LinearLayout A_BeautifyNestedLayout = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "恢复原样 (荷叶)", "", "", "", A_BeautifyNestedLayout);
        layout.addView(A_BeautifyNestedLayout);
        
        LinearLayout A_BeautifyNestedLayout1 = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "荷叶 => 无", "http://wpan.cdndns.site/down/a0c11066385f98cc2b69c110577014df", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/alltextures/ballmaterial/", "bz_sf135.unity3d_u_4", A_BeautifyNestedLayout1);
        createDownloadCheckBox(context, "荷叶 => ", "", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/alltextures/ballmaterial/", "bz_sf135.unity3d_u_4", A_BeautifyNestedLayout1);
        layout.addView(A_BeautifyNestedLayout1);
        
        LinearLayout A_BeautifyNestedLayout2 = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "荷叶 => ", "http://wpan.cdndns.site/down/a0c11066385f98cc2b69c110577014df", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/alltextures/ballmaterial/", "bz_sf135.unity3d_u_4", A_BeautifyNestedLayout2);
        createDownloadCheckBox(context, "荷叶 => ", "", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/alltextures/ballmaterial/", "bz_sf135.unity3d_u_4", A_BeautifyNestedLayout2);
        layout.addView(A_BeautifyNestedLayout2);
        return layout;
    }
    
    private View BeautifyLayout2(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                
        LinearLayout B_BeautifyNestedLayout = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "恢复原样 (雪花)", "", "", "", B_BeautifyNestedLayout);
        layout.addView(B_BeautifyNestedLayout);
        
        LinearLayout B_BeautifyNestedLayout1 = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "雪花 => ", "", "", "", B_BeautifyNestedLayout1);
        layout.addView(B_BeautifyNestedLayout1);
        return layout;
    }
    
    private View BeautifyLayout3(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                
        LinearLayout C_BeautifyNestedLayout = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "恢复原样 (爱心蛋)", "", "", "", C_BeautifyNestedLayout);
        layout.addView(C_BeautifyNestedLayout);
        
        LinearLayout C_BeautifyNestedLayout1 = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "爱心蛋 => 爱恋阿芙娜", "http://wpan.cdndns.site/down/2d794b3314e4256f7a95d2981b535b16", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/alltextures/ballmaterial/", "bz_sf146.unity3d_u_4", C_BeautifyNestedLayout1);
        layout.addView(C_BeautifyNestedLayout1);
        return layout;
    }
    
    private View BeautifyLayout4(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
             
        LinearLayout D_BeautifyNestedLayout = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "恢复原样 (蒲公英)", "", "", "", D_BeautifyNestedLayout);
        layout.addView(D_BeautifyNestedLayout);
           
        LinearLayout D_BeautifyNestedLayout1 = createBeautifyNestedLayout(context);
        createDownloadCheckBox(context, "蒲公英 => ", "", "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/", "uipic.unity3d_u_b52fd97e30b7e091940415f5fe3b5abf", D_BeautifyNestedLayout1);
        layout.addView(D_BeautifyNestedLayout1);
        return layout;
    }
    
    private View createPaging5Layout(Context context) {
        // 外层滚动容器，支持内容过多时上下滚动
        ScrollView scrollView = new ScrollView(context);
        FrameLayout.LayoutParams scrollParams = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollParams);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        // 子布局：垂直LinearLayout
        LinearLayout childLayout = new LinearLayout(context);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        childParams.setMargins(0, 0, dpToPx(3, context), dpToPx(3, context));
        childLayout.setLayoutParams(childParams);
        int innerPadding = dpToPx(2, context);
        childLayout.setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
        scrollView.addView(childLayout);
        
        TextView Text1 = new TextView(context);
        Text1.setText("联系开发: BlueBerry");
        Text1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        Text1.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        
        // 创建边距参数并设置
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(dpToPx(5, context), dpToPx(5, context), 0, 0);
        Text1.setLayoutParams(params);
        
        childLayout.addView(Text1);

        Text1.setOnClickListener(v -> {
            
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://qm.qq.com/q/XgR1G9sBeq"));
            context.startActivity(intent);
        });
        
        TextView Text2 = new TextView(context);
        Text2.setText("加入我们");
        Text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        Text2.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        
        // 创建边距参数并设置
        ViewGroup.MarginLayoutParams params1 = new ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params1.setMargins(dpToPx(5, context), dpToPx(5, context), 0, 0);
        Text2.setLayoutParams(params);
        
        childLayout.addView(Text2);

        Text2.setOnClickListener(v -> {
            
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=2fjMgEW8SBYN_BTEv4ttPigitBEV9egO&authKey=P3fdwtJXYzVCc744exPsPIiyLQ8YKl%2FrANGJ0iDoL%2FNSOHM90G55M02IiZHOtDXo&noverify=0&group_code=1042385459"));
            context.startActivity(intent);
        });

        return scrollView;
    }
    
    // =============== 方法 ===============
    
    public LinearLayout createBeautifyNestedLayout(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dpToPx(42, context)
        );
        layout.setLayoutParams(params);
        layout.setPadding(dpToPx(3, context), 0, dpToPx(3, context), 0);
        layout.setBackgroundColor(Color.parseColor("#00000000"));
        layout.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        return layout;
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
    
    private boolean downloadAndSaveFile(Context context, String fileUrl, String saveDirectory, String newFileName) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(30000);
    
            // 响应码处理
            int responseCode;
            try {
                responseCode = connection.getResponseCode();
            } catch (IOException e) {
                showToast(context, "连接失败: " + e.getMessage(), Toast.LENGTH_SHORT);
                return false;
            }
    
            if (responseCode != HttpURLConnection.HTTP_OK) {
                showToast(context, "服务器错误: " + responseCode, Toast.LENGTH_SHORT);
                return false;
            }
    
            // 创建目录
            File dir = new File(saveDirectory);
            if (!dir.exists() && !dir.mkdirs()) {
                showToast(context, "无法创建存储目录", Toast.LENGTH_SHORT);
                return false;
            }
    
            // 文件操作
            File outputFile = new File(dir, newFileName);
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
    
            // 强制写入磁盘
            outputStream.flush();
    
            // 关闭流确保文件可读取
            outputStream.close();
            inputStream.close();
    
            // 非空校验
            long actualSize = outputFile.length();
            if (actualSize == 0) {
                outputFile.delete();
                showToast(context, "下载文件为空", Toast.LENGTH_SHORT);
                return false;
            }
    
            // 显示实际文件大小（仅最终大小）
            showToast(context, 
                "下载完成：" + formatFileSize(actualSize), 
                Toast.LENGTH_LONG
            );
            
            return true;
        } catch (IOException e) {
            showToast(context, "下载失败: " + e.getMessage(), Toast.LENGTH_LONG);
            return false;
        } finally {
            try {
                // 确保资源释放
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (connection != null) connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private String formatFileSize(long size) {
        if (size <= 0) return "未知大小";
        if (size >= 1024 * 1024) {
            return String.format(Locale.US, "%.1f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format(Locale.US, "%.1f KB", size / 1024.0);
        }
    }
    
    private CheckBox createDownloadCheckBox(
        Context context,
        String text,
        String url,
        String savePath,
        String rename,
        LinearLayout parent
    ) {
        return createCheckBox(context, text, View.NO_ID, false, v -> {
            if (((CheckBox) v).isChecked()) {
                new Thread(() -> {
                    boolean downloadSuccess = downloadAndSaveFile(context, url, savePath, rename);
                }).start();
            }
        }, parent, 5, 0, 0, 0);
    }
    
    private CheckBox createDownloadmd5CheckBox(
        Context context,
        String text,
        String url,
        String savePath,
        String rename,
        String fileToModify,
        String keyword,
        String newMd5,
        LinearLayout parent
    ) {
        return createCheckBox(context, text, View.NO_ID, false, v -> {
            if (((CheckBox) v).isChecked()) {
                new Thread(() -> {
                    boolean downloadSuccess = downloadAndSaveFile(context, url, savePath, rename);
                    if (downloadSuccess) {
                        // 下载成功后尝试替换md5
                        boolean modified = replaceMd5ByKeyword(fileToModify, keyword, newMd5, context);
                        if (!modified) {

                        }
                    } else {
                        
                    }
                }).start();
            }
        }, parent, 5, 0, 0, 0);
    }
    
    public static boolean replaceMd5ByKeyword(String filePath, String keyword, String newMd5, Context context) {
      try {
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        boolean replaced = false;
    
        for (int i = 0; i < lines.size(); i++) {
          String line = lines.get(i);
          if (line.contains(keyword)) {
            Pattern pattern = Pattern.compile("md5=\"([a-fA-F0-9]{32})\"");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
              String oldMd5 = matcher.group(1);
              String newLine = line.replace(oldMd5, newMd5);
              lines.set(i, newLine);
              replaced = true;
              break;  // 只替换第一个匹配项
            }
          }
        }
    
        if (replaced) {
          Files.write(Paths.get(filePath), lines, StandardCharsets.UTF_8);
          
        } else {
          
        }
    
        return replaced;
    
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    }
    
    // 创建文本行
    private View createTextRow(Context context, String label) {
        LinearLayout rowLayout = new LinearLayout(context);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(0, 0, 0, dpToPx(6, context)); // 外边距
        rowLayout.setLayoutParams(rowParams);
        rowLayout.setGravity(Gravity.CENTER_VERTICAL);
    
        // 左侧文本
        TextView tv = new TextView(context);
        tv.setText(label);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        rowLayout.addView(tv);
    
        return rowLayout;
    }
    
    private View createIntegerEditWithButtons(Context context, String label, int initialValue, boolean allowNegative, Consumer<Integer> onValueChanged) {
        // 创建水平LinearLayout，用于排列标签和输入框
        LinearLayout rowLayout = new LinearLayout(context);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(0, dpToPx(3, context), 0, dpToPx(3, context));
        rowLayout.setLayoutParams(rowParams);
        rowLayout.setGravity(Gravity.CENTER_VERTICAL);
        rowLayout.setBaselineAligned(true);
    
        // 设置标签文本
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                dpToPx(180, context), dpToPx(30, context));  // 设置宽高
        textParams.setMargins(0, 0, dpToPx(10, context), 0);  // 设置右边距
        tv.setLayoutParams(textParams);
        tv.setText(label);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        rowLayout.addView(tv);
    
        // 设置 EditText，用于显示和编辑数值
        EditText valueEditText = new EditText(context);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                dpToPx(60, context), dpToPx(34, context));
        editTextParams.setMargins(dpToPx(30, context), 0, 0, 0);  // 设置左边距
        valueEditText.setLayoutParams(editTextParams);
        valueEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        valueEditText.setText(String.valueOf(initialValue));  // 设置初始值
        valueEditText.setPadding(dpToPx(10, context), 0, 0, 0);
        valueEditText.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF80956C"))); // 霓虹粉光标色数值显示👿
        rowLayout.addView(valueEditText);
        
        // 创建减号按钮，减少数值
        Button minusButton = new Button(context);
        minusButton.setText("-");
        minusButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        minusButton.setGravity(Gravity.CENTER);
        minusButton.setBackground(createRoundButtonBackground(context)); // 设置圆角背景
        minusButton.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams minusButtonParams = new LinearLayout.LayoutParams(
                dpToPx(30, context), dpToPx(30, context));  // 设置固定宽高
        minusButtonParams.setMargins(dpToPx(10, context), 0, 0, 0);  // 设置左外边距
        minusButton.setLayoutParams(minusButtonParams);
        
        final Runnable decrementRunnable = new Runnable() {
            @Override
            public void run() {
                String value = valueEditText.getText().toString();
                int currentValue = value.isEmpty() ? 0 : Integer.parseInt(value);
                // 如果不允许负数，且当前值为0，则直接返回
                if (!allowNegative && currentValue <= 0) return;
                int newValue = currentValue - 1;
                valueEditText.setText(String.valueOf(newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
                handler.postDelayed(this, 120);  // 每120毫秒执行一次
            }
        };
        
        // 点击事件，减少数值
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueEditText.getText().toString();
                int currentValue = value.isEmpty() ? 0 : Integer.parseInt(value);
                // 如果不允许负数，且当前值为0，则直接返回
                if (!allowNegative && currentValue <= 0) return;
                int newValue = currentValue - 1;
                valueEditText.setText(String.valueOf(newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
            }
        });
        
        // 长按事件，持续减少数值
        minusButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handler.postDelayed(decrementRunnable, 200);  // 按下后200毫秒开始执行
                return true;
            }
        });
        
        // 手指移动出按钮范围时停止
        minusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 检查手指是否离开按钮范围
                        if (!v.getLocalVisibleRect(new Rect())) {
                            handler.removeCallbacks(decrementRunnable);  // 停止减少
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(decrementRunnable);  // 停止减少
                        break;
                }
                return false;
            }
        });
        rowLayout.addView(minusButton);
        
        // 创建加号按钮，增加数值
        Button plusButton = new Button(context);
        plusButton.setText("+");
        plusButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        plusButton.setGravity(Gravity.CENTER);
        plusButton.setBackground(createRoundButtonBackground(context)); // 设置圆角背景
        plusButton.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams plusButtonParams = new LinearLayout.LayoutParams(
                dpToPx(30, context), dpToPx(30, context));  // 设置固定宽高
        plusButtonParams.setMargins(dpToPx(10, context), 0, 0, 0);  // 设置左外边距
        plusButton.setLayoutParams(plusButtonParams);
        
        final Runnable incrementRunnable = new Runnable() {
            @Override
            public void run() {
                String value = valueEditText.getText().toString();
                int currentValue = value.isEmpty() ? 0 : Integer.parseInt(value);
                int newValue = currentValue + 1;
                valueEditText.setText(String.valueOf(newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
                handler.postDelayed(this, 120);  // 每120毫秒执行一次
            }
        };
        
        // 点击事件，增加数值
        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueEditText.getText().toString();
                int currentValue = value.isEmpty() ? 0 : Integer.parseInt(value);
                int newValue = currentValue + 1;
                valueEditText.setText(String.valueOf(newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
            }
        });
        
        // 长按事件，持续增加数值
        plusButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handler.postDelayed(incrementRunnable, 200);  // 按下后200毫秒开始执行
                return true;
            }
        });
        
        // 手指移动出按钮范围时停止
        plusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 检查手指是否离开按钮范围
                        if (!v.getLocalVisibleRect(new Rect())) {
                            handler.removeCallbacks(incrementRunnable);  // 停止增减
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(incrementRunnable);  // 停止增减
                        break;
                }
                return false;
            }
        });
        
        rowLayout.addView(plusButton);
    
        // 监听 EditText 文本变化并实时更新变量
        valueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
    
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
    
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    // 获取新的值并通过回调更新变量
                    int newValue = Integer.parseInt(editable.toString());
                    onValueChanged.accept(newValue);
                    Sample.saveConfig(context);
                } catch (NumberFormatException e) {
                    // 如果输入无效，回调为 0
                    onValueChanged.accept(0);
                }
            }
        });
    
        return rowLayout;
    }
    
    // 支持 Double 类型输入和加减按钮
    private View createDoubleEditWithButtons(Context context, String label, double initialValue, boolean allowNegative, Consumer<Double> onValueChanged) {
        // 创建水平LinearLayout，用于排列标签和输入框
        LinearLayout rowLayout = new LinearLayout(context);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(0, dpToPx(3, context), 0, dpToPx(3, context));
        rowLayout.setLayoutParams(rowParams);
        rowLayout.setGravity(Gravity.CENTER_VERTICAL);
        rowLayout.setBaselineAligned(true);
    
        // 设置标签文本
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                dpToPx(180, context), dpToPx(30, context));  // 设置宽高
        textParams.setMargins(0, 0, dpToPx(10, context), 0);  // 设置右边距
        tv.setLayoutParams(textParams);
        tv.setText(label);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(Color.parseColor("#FF80956C")); // 紫藤灰
        rowLayout.addView(tv);
    
        // 设置 EditText，用于显示和编辑数值
        EditText valueEditText = new EditText(context);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                dpToPx(60, context), dpToPx(34, context));
        editTextParams.setMargins(dpToPx(30, context), 0, 0, 0);  // 设置左边距
        valueEditText.setLayoutParams(editTextParams);
        valueEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        valueEditText.setText(String.format("%.2f", initialValue));
        valueEditText.setPadding(dpToPx(10, context), 0, 0, 0);
        valueEditText.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF80956C"))); // 霓虹粉光标色数值显示
        rowLayout.addView(valueEditText);
        
        // 创建减号按钮，减少数值
        Button minusButton = new Button(context);
        minusButton.setText("-");
        minusButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        minusButton.setGravity(Gravity.CENTER);
        minusButton.setBackground(createRoundButtonBackground(context)); // 设置圆角背景
        minusButton.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams minusButtonParams = new LinearLayout.LayoutParams(
                dpToPx(30, context), dpToPx(30, context));  // 设置固定宽高
        minusButtonParams.setMargins(dpToPx(10, context), 0, 0, 0);  // 设置左外边距
        minusButton.setLayoutParams(minusButtonParams);
        
        final Runnable decrementRunnable = new Runnable() {
            @Override
            public void run() {
                String value = valueEditText.getText().toString();
                double currentValue = value.isEmpty() ? 0 : Double.parseDouble(value);
                // 如果不允许负数，且当前值为0，则直接返回
                if (!allowNegative && currentValue <= 0) return;
                double newValue = currentValue - 0.01;
                valueEditText.setText(String.format("%.2f", newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
                handler.postDelayed(this, 120);  // 每120毫秒执行一次
            }
        };
        
        // 点击事件，减少数值
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueEditText.getText().toString();
                double currentValue = value.isEmpty() ? 0 : Double.parseDouble(value);
                // 如果不允许负数，且当前值为0，则直接返回
                if (!allowNegative && currentValue <= 0) return;
                double newValue = currentValue - 0.01;
                valueEditText.setText(String.format("%.2f", newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
            }
        });
        
        // 长按事件，持续减少数值
        minusButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handler.postDelayed(decrementRunnable, 200);  // 按下后200毫秒开始执行
                return true;
            }
        });
        
        // 手指移动出按钮范围时停止
        minusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 检查手指是否离开按钮范围
                        if (!v.getLocalVisibleRect(new Rect())) {
                            handler.removeCallbacks(decrementRunnable);  // 停止减少
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(decrementRunnable);  // 停止减少
                        break;
                }
                return false;
            }
        });
        rowLayout.addView(minusButton);
        
        // 创建加号按钮，增加数值
        Button plusButton = new Button(context);
        plusButton.setText("+");
        plusButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        plusButton.setGravity(Gravity.CENTER);
        plusButton.setBackground(createRoundButtonBackground(context)); // 设置圆角背景
        plusButton.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams plusButtonParams = new LinearLayout.LayoutParams(
                dpToPx(30, context), dpToPx(30, context));  // 设置固定宽高
        plusButtonParams.setMargins(dpToPx(10, context), 0, 0, 0);  // 设置左外边距
        plusButton.setLayoutParams(plusButtonParams);
        
        final Runnable incrementRunnable = new Runnable() {
            @Override
            public void run() {
                String value = valueEditText.getText().toString();
                double currentValue = value.isEmpty() ? 0 : Double.parseDouble(value);
                double newValue = currentValue + 0.01;
                valueEditText.setText(String.format("%.2f", newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
                handler.postDelayed(this, 120);  // 每120毫秒执行一次
            }
        };
        
        // 点击事件，增加数值
        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueEditText.getText().toString();
                double currentValue = value.isEmpty() ? 0 : Double.parseDouble(value);
                double newValue = currentValue + 0.01;
                valueEditText.setText(String.format("%.2f", newValue));
                // 通过回调更新数值
                onValueChanged.accept(newValue);
            }
        });
        
        // 长按事件，持续增加数值
        plusButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handler.postDelayed(incrementRunnable, 200);  // 按下后200毫秒开始执行
                return true;
            }
        });
        
        // 手指移动出按钮范围时停止
        plusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 检查手指是否离开按钮范围
                        if (!v.getLocalVisibleRect(new Rect())) {
                            handler.removeCallbacks(incrementRunnable);  // 停止增减
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(incrementRunnable);  // 停止增减
                        break;
                }
                return false;
            }
        });
        
        rowLayout.addView(plusButton);
    
        // 监听 EditText 文本变化并实时更新变量
        valueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
    
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
    
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    // 获取新的值并通过回调更新变量
                    double newValue = Double.parseDouble(editable.toString());
                    onValueChanged.accept(newValue);
                    Sample.saveConfig(context);
                } catch (NumberFormatException e) {
                    // 如果输入无效，回调为 0
                    onValueChanged.accept(0.0);
                }
            }
        });
    
        return rowLayout;
    }
    
    private void addDoubleSeekBarWithText(Context context, LinearLayout parent, double initialValue, double min, double max, DoubleValueChangeListener listener) {
        // 使用大范围的缩放因子，支持小数点后 6 位
        int precisionScale = 1_000_000; // 支持小数点后 6 位
    
        // 创建水平排列的 LinearLayout 作为容器，设置宽度为 WRAP_CONTENT 以便根据子控件自适应，然后通过 LayoutParams 将其居中
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dpToPx(350, context),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layout.setLayoutParams(layoutParams);
        layout.setPadding(8, 4, 8, 4);
    
        // 设置 SeekBar
        SeekBar seekBar = new SeekBar(context);
        LinearLayout.LayoutParams seekBarParams = new LinearLayout.LayoutParams(
                dpToPx(200, context), LayoutParams.WRAP_CONTENT);
        seekBarParams.setMargins(0, 20, dpToPx(20, context), 0); // 调整上下边距以居中
        seekBar.setLayoutParams(seekBarParams);
        seekBar.setMax((int) ((max - min) * precisionScale));  // 放大到 10^6 范围
        seekBar.setProgress((int) ((initialValue - min) * precisionScale)); // 初始化时放大
        seekBar.getThumb().setColorFilter(Color.parseColor("#FF80956C"), android.graphics.PorterDuff.Mode.SRC_IN);
        seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#c5979ea6"), android.graphics.PorterDuff.Mode.SRC_IN);
        layout.addView(seekBar);
    
        // 替换编辑框为文本显示：用 TextView 显示当前值
        TextView valueTextView = new TextView(context);
        valueTextView.setText(String.format("%.3f", initialValue));  // 显示 3 位小数
        valueTextView.setTextSize(17);
        valueTextView.setTextColor(Color.parseColor("#FF80956C"));
        // 如果需要居中，可设置文本重心
        // valueTextView.setGravity(Gravity.CENTER);
        
        // 创建布局参数，使用权重 1 占满剩余空间，高度为 WRAP_CONTENT
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.9f);
        // 设置右外边距为 20dp（注意 dpToPx 的参数顺序：dpToPx(20, context) ）
        tvParams.setMargins(0, 20, 0, 0);
        
        // 将 tvParams 应用于 valueTextView
        valueTextView.setLayoutParams(tvParams);
        layout.addView(valueTextView);
        
            // 联动逻辑：SeekBar -> TextView
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // 计算浮动值，进度值除以缩放因子
                    double value = min + (progress / (double) precisionScale);
                    // 更新文本显示
                    valueTextView.setText(String.format("%.3f", value));
                    listener.onValueChanged(value);  // 回调监听器
                }
        
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
        
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        
        parent.addView(layout);
    }
    
    // 封装复选框创建方法
    private CheckBox createCheckBox(Context context, String text, int id, boolean isChecked, View.OnClickListener onClickListener, LinearLayout parentLayout, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        // 创建复选框
        CheckBox checkBox = new CheckBox(context);
        checkBox.setId(id);
        checkBox.setText(text);
        checkBox.setChecked(isChecked);  // 设置复选框默认开启状态
        
        // 设置复选框的样式
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkBoxParams.width = dpToPx(200, context);  // 设置宽度
        checkBoxParams.height = dpToPx(35, context);  // 设置高度
    
        // 独立设置外边距
        checkBoxParams.setMargins(dpToPx(leftMargin, context), dpToPx(topMargin, context), dpToPx(rightMargin, context), dpToPx(bottomMargin, context)); // 设置外边距
        checkBox.setLayoutParams(checkBoxParams);
        
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FF80956C"))); // 设置选中色
        checkBox.setTextColor(Color.parseColor("#FF80956C")); // 设置文字颜色
    
        // 设置点击事件的回调
        checkBox.setOnClickListener(onClickListener);
    
        // 将复选框添加到父布局中
        if (parentLayout != null) {
            parentLayout.addView(checkBox);
        }
    
        return checkBox;
    }
    
    public interface DoubleValueChangeListener {
        void onValueChanged(double value);
    }

    // 创建带圆角的按钮背景
    private GradientDrawable createRoundButtonBackground(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#80979ea6")); 
        drawable.setCornerRadius(dpToPx(2, context));
        return drawable;
    }

    // 创建带圆角的导航按钮背景
    private GradientDrawable createNavButtonBackground(Context context, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(dpToPx(8, context));
        drawable.setColor(color);
        return drawable;
    }
    
    public static long[] Incomingone = new long[1], Incomingtwo = new long[1], Incomingthree = new long[1], Incomingfour = new long[1], Incomingfive = new long[1], Incomingsix = new long[1];
    
    private static void IncomingAddress(String packageName, Handler mainHandler, final long[] Incomingone, final long[] Incomingtwo, final long[] Incomingthree, final long[] Incomingfour, final long[] Incomingfive, final long[] Incomingsix) {

        new Thread(() -> {
            
                try {
                    // 搜索第一个地址
                    memory.clearResult();
                    memory.setPackageName(packageName);
                    memory.setRange(memory.RANGE_ANONYMOUS);
                    memory.RangeMemorySearch("20000", memory.TYPE_FLOAT);
                    memory.MemoryOffset("500", memory.TYPE_FLOAT, 12);
                    memory.MemoryOffset("500", memory.TYPE_FLOAT, 24);
                    int resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);
                        for (long addr : resultAddresses) {
                            Incomingone[0] = addr - 388;
                            
                            //XposedBridge.log("[Hook] Found Incomingone: " + Long.toHexString(Incomingone[0]));
                        }
                    } else {
                        //XposedBridge.log("[Hook] Not found Incomingone in this iteration");
                    }
    
                    // 搜索第二个地址
                    memory.clearResult();
                    memory.setRange(memory.RANGE_ANONYMOUS);
                    memory.RangeMemorySearch("9.27533467e-41", memory.TYPE_FLOAT);
                    memory.MemoryOffset("9.27337285e-41", memory.TYPE_FLOAT, - 312);
                    resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);
                        for (long addr : resultAddresses) {
                            Incomingtwo[0] = addr - 4; // 初始值 A F 0.58799999952~
                            
                            //XposedBridge.log("[Hook] Found Incomingtwo: " + Long.toHexString(Incomingtwo[0]));
                        }
                    } else {
                        //XposedBridge.log("[Hook] Not found Incomingtwo in this iteration");
                    }
    
                    // 搜索第三个地址
                    memory.clearResult();
                    memory.setRange(memory.RANGE_ANONYMOUS);
                    memory.RangeMemorySearch("0.00389999989", memory.TYPE_FLOAT);
                    memory.MemoryOffset("5.51029793e-40", memory.TYPE_FLOAT, 116);
                    resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);
                        for (long addr : resultAddresses) {
                            Incomingthree[0] = addr + 120; // 初始值 A F 0.01~
                            
                            //XposedBridge.log("[Hook] Found Incomingthree: " + Long.toHexString(Incomingthree[0]));
                        }
                    } else {
                        //XposedBridge.log("[Hook] Not found Incomingthree in this iteration");
                    }
    
                    // 搜索第四个地址
                    memory.clearResult();
                    memory.setRange(memory.RANGE_ANONYMOUS);
                    memory.RangeMemorySearch("2.75739104e-40", memory.TYPE_FLOAT);
                    memory.MemoryOffset("5.60519386e-45", memory.TYPE_FLOAT, -16);
                    resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);
                        for (long addr : resultAddresses) {
                            Incomingfour[0] = addr  - 4; // 初始值 0.05~
                            
                            //XposedBridge.log("[Hook] Found Incomingfour: " + Long.toHexString(Incomingfour[0]));
                        }
                    } else {
                        //XposedBridge.log("[Hook] Not found Incomingfour in this iteration");
                    }
                    
                    // 搜索第五个地址
                    memory.clearResult();
                    memory.setRange(memory.RANGE_ANONYMOUS);
                    memory.RangeMemorySearch("14.5", memory.TYPE_FLOAT);
                    memory.MemoryOffset("0.79999995232", memory.TYPE_FLOAT, -40);
                    resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);
                        for (long addr : resultAddresses) {
                            Incomingfive[0] = addr - 104;
                            
                            //XposedBridge.log("[Hook] Found Incomingfive: " + Long.toHexString(Incomingfour[0]));
                        }
                    } else {
                        //XposedBridge.log("[Hook] Not found Incomingfive in this iteration");
                    }
    
                    // 搜索第六个地址
                    memory.clearResult();
                    memory.setRange(memory.RANGE_ANONYMOUS);
                    memory.RangeMemorySearch("0.01499999966", memory.TYPE_FLOAT);
                    memory.MemoryOffset("0.01166000031", memory.TYPE_FLOAT, -72);
                    resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);
                        for (long addr : resultAddresses) {
                            Incomingsix[0] = addr + 40;
                            
                            //XposedBridge.log("[Hook] Found Incomingsix: " + Long.toHexString(Incomingsix[0]));
                        }
                    } else {
                        //XposedBridge.log("[Hook] Not found Incomingsix in this iteration");
                    }
                    
                    JoystickAngleCalculation();
                    
                } catch (Exception e) {
                    //XposedBridge.log("[Hook] Exception in IncomingAddress: " + e.getMessage());
                    e.printStackTrace();
                }
            
        }).start();
    }
    
    public static void JoystickAngleCalculation() {
        Handler mainHandler;
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper != null) {
            mainHandler = new Handler(mainLooper);
        } else {
            mainHandler = new Handler(Looper.myLooper());
        }
    
        new Thread(() -> {
            try {
                //XposedBridge.log("开始进行内存搜索...");
                memory.clearResult();
                memory.setPackageName("com.ztgame.bob");
                memory.setRange(memory.RANGE_ANONYMOUS);
                memory.RangeMemorySearch("20000", memory.TYPE_FLOAT);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 12);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 24);
    
                long lastToastTime = 0;
    
                while (true) {
                    int resultCount = memory.getResultCount();
                    if (resultCount > 0) {
                        long[] resultAddresses = memory.getResult(resultCount);
    
                        for (long addr : resultAddresses) {
                            //XposedBridge.log("搜索到的地址: " + Long.toHexString(addr));
    
                            joystickUp = memory.readFloat(addr - 0x570);
                            joystickDown = memory.readFloat(addr - 0x56C);
    
                            joystickAngle = (float) Math.toDegrees(Math.atan2(joystickDown, joystickUp));
    
                            if (joystickAngle < 0) {
                                joystickAngle += 360;
                            }
    
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - lastToastTime >= 1000) {
                                lastToastTime = currentTime;
                                mainHandler.post(() -> {
                                    // 可在此处更新 UI 或其他主线程操作
                                    joystickAngle = joystickAngle;
                                });
                            }
                        }
                    } else {
                        //XposedBridge.log("二次进行内存搜索...");
                        memory.clearResult();
                        memory.setPackageName("com.ztgame.bob");
                        memory.setRange(memory.RANGE_ANONYMOUS);
                        memory.RangeMemorySearch("20000", memory.TYPE_FLOAT);
                        memory.MemoryOffset("500", memory.TYPE_FLOAT, 12);
                        memory.MemoryOffset("500", memory.TYPE_FLOAT, 24);
                    }
    
                    Thread.sleep(5);
                    //XposedBridge.log("循环结束，当前结果计数: " + resultCount + ", joystickAngle: " + joystickAngle);
                }
            } catch (InterruptedException e) {
                //XposedBridge.log("线程中断异常: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    
    private static class PlayerEntity {
        public long address;       // 内存地址(识别球体)
        public long uniqueId;      // 全局唯一编号
        public float x;            // 世界坐标 X
        public float y;            // 世界坐标 Y
        public float radius;       // 球体半径
        public float volume;       // 球体体积
        public int subIndex;       // 在同 rankId 分组内的排序(0=>最大球)
        public int rankId;         // rankId(组ID)

        public PlayerEntity(long address, long uniqueId, float x, float y, float radius, int rankId) {
            this.address = address;
            this.uniqueId = uniqueId;
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.rankId = rankId;
            this.volume = (float) ((4 / 3.0) * Math.PI * Math.pow(radius, 3));
        }
    }

    // rankId => List<PlayerEntity>
    private Map<Integer, List<PlayerEntity>> rankToPlayers = new HashMap<>();
    // 扁平映射: uniqueId => PlayerEntity
    private Map<Long, PlayerEntity> allPlayers = new HashMap<>();
    // address => uniqueId
    private Map<Long, Long> addressToUniqueIdMap = new HashMap<>();
    private static long globalUniqueCounter = 1;

    // =================== 2) 全局控制变量 ===================

    private boolean 卡点检测 = false;
    private int 卡点选中等级 = -1;
    private int 卡点选中索引 = -1;
    private float 卡点初始目标体积 = -1;
    private boolean 卡点选中可见状态 = false;

    private boolean 同步检测 = false;
    private int 同步选中索引 = -1;
    private float 同步初始目标体积 = -1;
    private boolean 同步允许重新获取 = true;
    private boolean 同步选中可见状态 = false;
    
    private boolean 锁球检测 = false;
    private int 锁球选中索引 = -1;
    private boolean 锁球允许重新获取 = true;
    private boolean 锁球选中可见状态 = false;
    
    private boolean 同步三角检测 = false;
    private int 同步三角选中索引 = -1;
    private float 同步三角初始目标体积 = -1;
    private boolean 同步三角允许重新获取 = true;
    private boolean 同步三角选中可见状态 = false;

    // 在分裂前后记录的角度
    private float 角度1 = 0f;  // 旧球相对于我方最大球的角度
    private float 角度2 = 0f;  // 新球相对于我方最大球的角度

    // 指定队友rankId，我方rankId
    private int specifiedRankId = 10;
    private int myRankId = 99;  

    // 视图/绘制控制
    private boolean showCardButton = false;
    private boolean showRankButton = false;
    private boolean 画排名 = false;
    private boolean 画边框 = false;
    private boolean 画天线 = false;

    // UI 按钮移动
    private boolean isMoved = false;
    private final float MOVE_THRESHOLD = 100f;
    private float downX, downY;

    private int 刷新延时 = 8;  


    /************************************************
     * 5) 获取玩家结构体 => rankToPlayers + allPlayers
     ************************************************/
    private void 获取玩家结构体(Context context, String fileName) {
        new Thread(() -> {
            try {
                int bits = 64;
                long 数组地址 = Arrayaddress; // 你自己的偏移
                Handler handler = new Handler(Looper.getMainLooper());

                Runnable updateRunnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<Integer, List<PlayerEntity>> 临时map = new HashMap<>();

                            float 视图比例1 = memory.readFloat(Viewscale - 0x38);
                            handler.post(() -> 视图比例 = 视图比例1);

                            // 先确定有效玩家数量
                            int 实际玩家数量 = 0;
                            for (int i = 0; i < 256; i++) {
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
                            for (int i = 0, 当前玩家计数 = 0; 当前玩家计数 < 实际玩家数量 && i < 256; i++) {
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

                                PlayerEntity entity = new PlayerEntity(
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
                            for (Map.Entry<Integer, List<PlayerEntity>> e : 临时map.entrySet()) {
                                List<PlayerEntity> group = e.getValue();
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

    // 扁平化
    private void updateAllPlayers(Map<Integer, List<PlayerEntity>> map) {
        allPlayers.clear();
        for (Map.Entry<Integer, List<PlayerEntity>> entry : map.entrySet()) {
            List<PlayerEntity> list = entry.getValue();
            if (list == null) continue;
            for (PlayerEntity p : list) {
                allPlayers.put(p.uniqueId, p);
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

                    float startX = Sample.绘图_分辨率x / 2f;
                    float startY = Sample.绘图_分辨率y / 2f;
                    float offsetX = memory.readFloat(Viewscale - 100);
                    float offsetY = 200 - memory.readFloat(Viewscale - 96);

                    // 遍历各组
                    for (Map.Entry<Integer, List<PlayerEntity>> entry : rankToPlayers.entrySet()) {
                        int rankId = entry.getKey();
                        List<PlayerEntity> groupList = entry.getValue();
                        if (groupList == null || groupList.isEmpty()) continue;

                        for (PlayerEntity entity : groupList) {
                            float screenXa = (float) ((entity.x - offsetX)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例))
                                    + startX;
                            float screenYa = (float) ((entity.y - offsetY)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例))
                                    + startY;
                            float drawRadius = (float) (entity.radius
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)
                                    );
                            if (!卡点选中可见状态) {
                                // 普通绘制
                                if (画天线) {
                                    drawView.addLine(
                                            startX, startY,
                                            screenXa, screenYa,
                                            Color.RED, 3f
                                    );
                                刷新延时=8;
                                }
                                if (画边框) {
                                    if (卡点检测
                                            && rankId == 卡点选中等级
                                            && entity.subIndex == 卡点选中索引) {
                                        drawView.addCircle(screenXa, screenYa, drawRadius, Color.YELLOW, 6f);
                                    刷新延时=1;
                                    } else {
                                        drawView.addCircle(screenXa, screenYa, drawRadius, Color.RED, 3f);
                                    刷新延时=8;
                                    }
                                }
                            } else {
                                // 如果处于“选中持续可见”状态
                                if (rankId == 卡点选中等级 && entity.subIndex == 卡点选中索引) {
                                    drawView.addCircle(screenXa, screenYa, drawRadius, Color.YELLOW, 6f);
                                刷新延时=1;
                                }
                            }

                            // 卡点判断
                            if (卡点检测 && rankId == 卡点选中等级 && entity.subIndex == 卡点选中索引) {
                                float currentVolume = entity.volume;
                                if (卡点初始目标体积 < 0) {
                                    刷新延时=1;
                                    卡点初始目标体积 = currentVolume;
                                } else if (currentVolume < 卡点初始目标体积 * 0.5f) {
                                    Hook.TouchEventHandler.按下(debugview.分x, debugview.分y, 点击id);
                                    Hook.TouchEventHandler.抬起(debugview.分x, debugview.分y, 点击id);
                                    触发一分为二功能(context);
                                    刷新延时=8;
                                    停止检测并恢复状态();
                                }
                            }

                            // 同步检测 => 分裂判断
                            if (同步检测 && rankId == specifiedRankId) {
                                if (同步选中索引 < 0 || 同步选中索引 >= groupList.size()) {
                                    if (同步允许重新获取) {
                                        reAcquireSyncBall(context);
                                    } else {
                                        停止检测并恢复状态();
                                    }
                                } else {
                                    PlayerEntity syncTarget = groupList.get(同步选中索引);
                                    if (syncTarget.rankId != specifiedRankId) {
                                        if (同步允许重新获取) {
                                            reAcquireSyncBall(context);
                                        } else {
                                            停止检测并恢复状态();
                                        }
                                    } else {
                                        float currentVolume = syncTarget.volume;
                                        if (同步初始目标体积 < 0) {
                                            同步初始目标体积 = currentVolume;
                                        } else if (currentVolume < 同步初始目标体积 * 0.5f) {
                                            触发同步一分为二功能(context);
                                            停止检测并恢复状态();
                                        }
                                    }
                                }
                            }
                            if (锁球检测 && rankId == specifiedRankId) {
                                // --- 修改1：移除动态重新获取，强制目标有效性 ---
                                if (锁球选中索引 < 0 || 锁球选中索引 >= groupList.size()) {
                                    if (groupList.isEmpty()) {
                                        停止检测并恢复状态();
                                        return;
                                    }
                                    锁球选中索引 = 0; // 重置为第一个目标
                                    XposedBridge.log("同步目标索引越界，已重置为0");
                                }
                            
                                PlayerEntity syncTarget = groupList.get(锁球选中索引);
                                if (syncTarget == null) {
                                    XposedBridge.log("同步目标为空，停止检测");
                                    停止检测并恢复状态();
                                    return;
                                }
                            
                                // --- 修改2：删除 rankId 校验和体积变化检测 ---
                                // 直接触发同步操作
                                触发锁球模拟触摸(context);
                                停止检测并恢复状态();
                            }
                            
                            else if (同步三角检测 && rankId == specifiedRankId) {
                                if (同步三角选中索引 < 0 || 同步三角选中索引 >= groupList.size()) {
                                    if (同步三角允许重新获取) {
                                        reAcquireSyncBall(context);
                                    } else {
                                        停止检测并恢复状态();
                                    }
                                } else {
                                    PlayerEntity syncTarget = groupList.get(同步三角选中索引);
                                    float currentVolume = syncTarget.volume;
                                    if (同步三角初始目标体积 < 0) {
                                        同步三角初始目标体积 = currentVolume;
                                    } else if (currentVolume < 同步三角初始目标体积 * 0.5f) {
                                        触发同步三角模拟触摸(context);
                                        停止检测并恢复状态();
                                    }
                                }
                            }
                        }

                        // 画排名(只画该组 subIndex=0)
                        if (画排名 && !groupList.isEmpty()) {
                            PlayerEntity maxBall = groupList.get(0);
                            float screenXa = (float) ((maxBall.x - offsetX)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例))
                                    + startX;
                            float screenYa = (float) ((maxBall.y - offsetY)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例))
                                    + startY;
                            float drawRadius = (float) (maxBall.radius
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)
                                    );
                            float textPositionY = screenYa - (drawRadius / 3f);

                            drawView.addTextWithFormatting(
                                maxBall.rankId,
                                screenXa,
                                textPositionY,
                                Color.WHITE,
                                2f * 23.25f * (float) Sample.绘图_大小 
                                    * (float) (23.25f * Sample.绘图_大小 / 视图比例),  // 添加 (float) 强制转换
                                2f,
                                10
                            );
                        }
                    }

                    // 若同步开启，绘制选中的球(蓝色圆)
                    if (同步选中可见状态 && 同步检测 && rankToPlayers.containsKey(specifiedRankId)) {
                        List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null
                                && 同步选中索引 >= 0
                                && 同步选中索引 < syncGroup.size()) {
                            PlayerEntity lockBall = syncGroup.get(同步选中索引);
                            float screenXa = (float) ((lockBall.x - offsetX)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)) + startX;
                            float screenYa = (float) ((lockBall.y - offsetY)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)) + startY;
                            float drawRadius = (float) (lockBall.radius
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)
                                    );
                            drawView.addCircle(screenXa, screenYa, drawRadius, Color.BLUE, 6f);
                        }
                    }
                    
                    if (同步三角选中可见状态 && 同步三角检测 && rankToPlayers.containsKey(specifiedRankId)) {
                        List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null
                                && 同步三角选中索引 >= 0
                                && 同步三角选中索引 < syncGroup.size()) {
                            PlayerEntity lockBall = syncGroup.get(同步三角选中索引);
                            float screenXa = (float) ((lockBall.x - offsetX)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)) + startX;
                            float screenYa = (float) ((lockBall.y - offsetY)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)) + startY;
                            float drawRadius = (float) (lockBall.radius
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)
                                    );
                            drawView.addCircle(screenXa, screenYa, drawRadius, Color.GREEN, 6f);
                        }
                    }
                    
                    if (锁球选中可见状态 && 锁球检测 && rankToPlayers.containsKey(specifiedRankId)) {
                        List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null
                                && 锁球选中索引 >= 0
                                && 锁球选中索引 < syncGroup.size()) {
                            PlayerEntity lockBall = syncGroup.get(锁球选中索引);
                            float screenXa = (float) ((lockBall.x - offsetX)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)) + startX;
                            float screenYa = (float) ((lockBall.y - offsetY)
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)) + startY;
                            float drawRadius = (float) (lockBall.radius
                                    * 23.25f * Sample.绘图_大小
                                    * (23.25f * Sample.绘图_大小 / 视图比例)
                                    );
                            drawView.addCircle(screenXa, screenYa, drawRadius, Color.RED, 6f);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this, 刷新延时);
            }
        };
        handler.post(drawRunnable);
        
            UIView.卡点 = newCircle.createCircleButton(
                "卡点",
                dpToPx(Sample.卡点_半径, context),
                Color.parseColor("#80FFDDE1"),
                Sample.卡点_坐标x,
                Sample.卡点_坐标y,
                new NewCircle.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        if (NewCircle.debugMode) return;
                        downX = 卡点.getX();
                        downY = 卡点.getY();
                    }
                    
                    @Override
                    public void onButtonMove(int x, int y) {
                        if (NewCircle.debugMode) return;
                        float dx = x - downX;
                        float dy = y - downY;
                        float distance = (float) Math.sqrt(dx * dx + dy * dy);
                        
                        卡点.setX(x);
                        卡点.setY(y);
                    }
    
                    @Override
                    public void onButtonUp() {
                        if (NewCircle.debugMode) return;
                        
                        卡点检测 = !卡点检测;
                       
                        if (卡点检测) {
                            卡点选中等级 = -1;
                            卡点选中索引 = -1;
                            卡点初始目标体积 = -1;

                            float centerX = Sample.绘图_分辨率x / 2f;
                            float centerY = Sample.绘图_分辨率y / 2f;
                            float offsetX = memory.readFloat(Viewscale - 100);
                            float offsetY = 200 - memory.readFloat(Viewscale - 96);

                            outerLoop:
                            for (Map.Entry<Integer, List<PlayerEntity>> entry : rankToPlayers.entrySet()) {
                                int rankId = entry.getKey();
                                List<PlayerEntity> groupList = entry.getValue();
                                if (groupList == null) continue;

                                for (PlayerEntity entity : groupList) {
                                    float drawX = (float) ((entity.x - offsetX)
                                            * 23.25f * Sample.绘图_大小
                                            * (23.25f * Sample.绘图_大小 / 视图比例))
                                            + centerX;
                                    float drawY = (float) ((entity.y - offsetY)
                                            * 23.25f * Sample.绘图_大小
                                            * (23.25f * Sample.绘图_大小 / 视图比例))
                                            + centerY;
                                    float drawRadius = (float) (entity.radius 
                                            * 23.25f * Sample.绘图_大小
                                            * (23.25f * Sample.绘图_大小 / 视图比例)
                                            );
                                    float dist = (float) Math.sqrt(
                                            Math.pow(downX - drawX, 2) 
                                            + Math.pow(downY - drawY, 2)
                                    );
                                    if (dist <= drawRadius) {
                                        卡点选中等级 = rankId;
                                        卡点选中索引 = entity.subIndex;
                                        卡点选中可见状态 = true;
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
                }
            );
        卡点.setZ(2f);
        卡点.setVisibility(Sample.卡点_状态 ? View.VISIBLE : View.GONE);
        
        UIView.同步 = newCircle.createCircleButton(
                "同步",
                dpToPx(Sample.同步_半径, context),
                Color.parseColor("#80FFDDE1"),
                Sample.同步_坐标x,
                Sample.同步_坐标y,
                new NewCircle.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        if (NewCircle.debugMode) return;
                        downX = 同步.getX();
                        downY = 同步.getY();
                    }
                    
                    @Override
                    public void onButtonMove(int x, int y) {
                        if (NewCircle.debugMode) return;
                        float dx = x - downX;
                        float dy = y - downY;
                        float distance = (float) Math.sqrt(dx * dx + dy * dy);
                        
                        同步.setX(x);
                        同步.setY(y);
                    }
    
                    @Override
                    public void onButtonUp() {
                        if (NewCircle.debugMode) return;
                        
                        if (!同步检测) {
                            同步检测 = true;
                            同步选中可见状态 = true;
                            同步选中索引 = -1;
                            同步初始目标体积 = -1;

                            // 选中目标组 => subIndex=0 (该组最大球)
                            if (rankToPlayers.containsKey(specifiedRankId)) {
                                List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                                if (syncGroup != null && !syncGroup.isEmpty()) {
                                    同步选中索引 = 0;
                                } else {
                                    同步检测 = false;
                                    同步选中可见状态 = false;
                                }
                            } else {
                                同步检测 = false;
                                同步选中可见状态 = false;
                            }
                        } else {
                            停止检测并恢复状态();
                        }
                    }
                }
            );
        同步.setZ(2f);
        同步.setVisibility(Sample.同步_状态 ? View.VISIBLE : View.GONE);
    
        UIView.同步三角 = newCircle.createCircleButton(
                "同步三角",
                dpToPx(Sample.同步三角_半径, context),
                Color.parseColor("#80FFDDE1"),
                Sample.同步三角_坐标x,
                Sample.同步三角_坐标y,
                new NewCircle.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        if (NewCircle.debugMode) return;
                        downX = 同步三角.getX();
                        downY = 同步三角.getY();
                    }
                    
                    @Override
                    public void onButtonMove(int x, int y) {
                        if (NewCircle.debugMode) return;
                        float dx = x - downX;
                        float dy = y - downY;
                        float distance = (float) Math.sqrt(dx * dx + dy * dy);
                        
                        同步三角.setX(x);
                        同步三角.setY(y);
                    }
    
                    @Override
                    public void onButtonUp() {
                        if (NewCircle.debugMode) return;
                        
                        if (!同步三角检测) {
                            同步三角检测 = true;
                            同步三角选中可见状态 = true;
                            同步三角选中索引 = -1;
                            同步三角初始目标体积 = -1;

                            // 选中目标组 => subIndex=0 (该组最大球)
                            if (rankToPlayers.containsKey(specifiedRankId)) {
                                List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                                if (syncGroup != null && !syncGroup.isEmpty()) {
                                    同步三角选中索引 = 0;
                                }
                            }
                        } else {
                            停止检测并恢复状态();
                        }
                    }
                }
            );
        同步三角.setZ(2f);
        同步三角.setVisibility(Sample.同步三角_状态 ? View.VISIBLE : View.GONE);
        
        UIView.锁球 = newCircle.createCircleButton(
                "锁球",
                dpToPx(Sample.锁球_半径, context),
                Color.parseColor("#80FFDDE1"),
                Sample.锁球_坐标x,
                Sample.锁球_坐标y,
                new NewCircle.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        if (NewCircle.debugMode) return;
                        downX = 锁球.getX();
                        downY = 锁球.getY();
                    }
                    
                    @Override
                    public void onButtonMove(int x, int y) {
                        if (NewCircle.debugMode) return;
                        float dx = x - downX;
                        float dy = y - downY;
                        float distance = (float) Math.sqrt(dx * dx + dy * dy);
                        
                        锁球.setX(x);
                        锁球.setY(y);
                    }
    
                    @Override
                    public void onButtonUp() {
                        if (NewCircle.debugMode) return;
                        
                        if (!锁球检测) {
                            锁球检测 = true;
                            锁球选中可见状态 = true;
                            锁球选中索引 = -1;

                            // 选中目标组 => subIndex=0 (该组最大球)
                            if (rankToPlayers.containsKey(specifiedRankId)) {
                                List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                                if (syncGroup != null && !syncGroup.isEmpty()) {
                                    锁球选中索引 = 0;
                                }
                            }
                        } else {
                            停止检测并恢复状态();
                        }
                    }
                }
            );
        锁球.setZ(2f);
        锁球.setVisibility(Sample.锁球_状态 ? View.VISIBLE : View.GONE);
    }

    /************************************************
     * 7) 停止检测并恢复状态
     ************************************************/
    private void 停止检测并恢复状态() {
        卡点检测 = false;
        卡点选中可见状态 = false;
        卡点选中等级 = -1;
        卡点选中索引 = -1;
        卡点初始目标体积 = -1;

        同步检测 = false;
        同步选中可见状态 = false;
        同步选中索引 = -1;
        同步初始目标体积 = -1;
        
        同步三角检测 = false;
        同步三角选中可见状态 = false;
        同步三角选中索引 = -1;
        同步三角初始目标体积 = -1;
        
        锁球检测 = false;
        锁球选中可见状态 = false;
        锁球选中索引 = -1;
        
        刷新延时=8;

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
                // Thread.sleep(Sample.卡点_触摸延迟);

                // 角度滑动
                Hook.TouchEventHandler.角度滑动(
                        debugview.摇x,
                        debugview.摇y,
                        initialAngle,
                        0,
                        200,
                        30,
                        触摸id, true
                );
                // 分裂次数
                for (int i = 0; i < Sample.卡点_分身次数; i++) {
                    Hook.TouchEventHandler.按下(
                            debugview.分x,
                            debugview.分y,
                            点击id
                    );
                    Hook.TouchEventHandler.抬起(
                            debugview.分x,
                            debugview.分y,
                            点击id
                    );
                    Thread.sleep(Sample.卡点_点击间隔);
                }
               
                卡点选中可见状态 = false;
               //XposedBridge.log("球体分裂: 卡点球体已一分为二！");
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
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
    // 同步逻辑
    private void 触发同步一分为二功能(Context context) {
        if (isRunning) return;
        isRunning = true;
    
        new Thread(() -> {
            try {
                List<PlayerEntity> friendGroup = rankToPlayers.get(specifiedRankId);
                if (friendGroup == null || friendGroup.isEmpty()) {
                    XposedBridge.log("同步失败：指定Rank组为空");
                    return;
                }
    
                PlayerEntity myLargestBall = getMyLargestBall();
                if (myLargestBall == null) {
                    XposedBridge.log("同步失败：我方最大球不存在");
                    return;
                }
    
                // ========== 路径一：轮询等待结构体增长 ==========
                int originalSize = friendGroup.size();
                long startTime = System.currentTimeMillis();
                boolean found = false;
    
                while (System.currentTimeMillis() - startTime < 100) {
                    List<PlayerEntity> updated = rankToPlayers.get(specifiedRankId);
                    if (updated != null && updated.size() > originalSize) {
                        friendGroup = updated;
                        found = true;
                        break;
                    }
                    try { Thread.sleep(5); } catch (InterruptedException ignored) {}
                }
    
                // ========== 路径二：fallback 强制等待 + 重取 ==========
                if (!found) {
                    XposedBridge.log("轮询未发现新球，执行 fallback");
                    try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                    friendGroup = rankToPlayers.get(specifiedRankId); // 尝试再取一次
                }
    
                // 找到分裂后距离我方最大球最远的新球（避免 sqrt）
                PlayerEntity farthestBall = null;
                float maxDistSq = -1f;
    
                for (PlayerEntity p : friendGroup) {
                    float dx = p.x - myLargestBall.x;
                    float dy = p.y - myLargestBall.y;
                    float distSq = dx * dx + dy * dy;
                    if (distSq > maxDistSq) {
                        maxDistSq = distSq;
                        farthestBall = p;
                    }
                }
    
                if (farthestBall == null) {
                    XposedBridge.log("同步失败：找不到最远球体");
                    return;
                }
    
                float dx = farthestBall.x - myLargestBall.x;
                float dy = farthestBall.y - myLargestBall.y;
                float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
                if (angle < 0) angle += 360;
    
                initialAngle = 360 - angle;
    
                Hook.TouchEventHandler.角度滑动(
                        debugview.摇x,
                        debugview.摇y,
                        initialAngle,
                        0,
                        Sample.同步_滑动距离,
                        Sample.同步_滑动时长,
                        触摸id,
                        true
                );
    
                for (int i = 0; i < Sample.同步_分身次数; i++) {
                    Hook.TouchEventHandler.按下(debugview.分x, debugview.分y, 点击id);
                    Hook.TouchEventHandler.抬起(debugview.分x, debugview.分y, 点击id);
                    Thread.sleep(Sample.同步_点击间隔);
                }
    
                同步选中可见状态 = false;
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);
                XposedBridge.log("同步分裂成功，最远角度=" + angle);
    
            } catch (Exception e) {
                XposedBridge.log("同步一分为二异常");
            } finally {
                isRunning = false;
            }
        }).start();
    }
    
    private void 触发同步三角模拟触摸(Context context) {
        if (isRunning) return;
        isRunning = true;
    
        new Thread(() -> {
            try {
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }
                            
                Hook.TouchEventHandler.角度滑动(
                            debugview.摇x,
                            debugview.摇y,
                            Sample.三角_展开角度,
                            initialAngle,
                            Sample.三角_滑动距离,
                            Sample.三角_滑动时长,
                            触摸id,
                            true
                        );
                        Thread.sleep(Sample.三角_等待时长);

                        Hook.TouchEventHandler.按下(
                            debugview.分x,
                            debugview.分y,
                            点击id
                        );
                        Hook.TouchEventHandler.抬起(
                            debugview.分x,
                            debugview.分y,
                            点击id
                        );
                        Thread.sleep(Sample.三角_等待时长);

                        Hook.TouchEventHandler.角度滑动(
                            debugview.摇x,
                            debugview.摇y,
                            360 - Sample.三角_展开角度,
                            initialAngle,
                            Sample.三角_滑动距离,
                            Sample.三角_滑动时长,
                            触摸id,
                            true
                        );
                        Thread.sleep(Sample.三角_等待时长);

                        Hook.TouchEventHandler.按下(
                            debugview.分x,
                            debugview.分y,
                            点击id
                        );
                        Hook.TouchEventHandler.抬起(
                            debugview.分x,
                            debugview.分y,
                            点击id
                        );
                        Thread.sleep(Sample.三角_等待时长);

                        Hook.TouchEventHandler.角度滑动(
                            debugview.摇x,
                            debugview.摇y,
                            Sample.三角_合球角度,
                            initialAngle,
                            Sample.三角_滑动距离2,
                            Sample.三角_滑动时长2,
                            触摸id,
                            true
                        );

                        for (int i = 0; i < Sample.三角_分身次数; i++) {
                            Hook.TouchEventHandler.按下(
                                debugview.分x,
                                debugview.分y,
                                点击id
                            );
                            Hook.TouchEventHandler.抬起(
                                debugview.分x,
                                debugview.分y,
                                点击id
                            );
                            Thread.sleep(Sample.三角_分身间隔);
                        }
    
                
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);
            } catch (Exception e) {
                XposedBridge.log("同步一分为二异常");
            } finally {
                isRunning = false;
            }
        }).start();
    }
    
    private void 触发锁球模拟触摸(Context context) {
        if (isRunning) return;
        isRunning = true;
    
        new Thread(() -> {
            try {
                // 获取目标分组
                List<PlayerEntity> friendGroup = rankToPlayers.get(specifiedRankId);
                if (friendGroup == null || friendGroup.isEmpty()) {
                    XposedBridge.log("强制同步失败：Rank组无有效目标");
                    return;
                }
    
                PlayerEntity myLargestBall = getMyLargestBall();
                if (myLargestBall == null) {
                    XposedBridge.log("强制同步失败：本方最大球体为空");
                    return;
                }
    
                // 轮询一次后 fallback 机制
                if (friendGroup.isEmpty()) {
                    XposedBridge.log("轮询未发现新球，执行 fallback");
                    try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                    friendGroup = rankToPlayers.get(specifiedRankId);
                    if (friendGroup == null || friendGroup.isEmpty()) return;
                }
    
                // 寻找距离我方最大球体最远的目标
                PlayerEntity farthestBall = null;
                float maxDistSq = -1f;
    
                for (PlayerEntity p : friendGroup) {
                    float dx = p.x - myLargestBall.x;
                    float dy = p.y - myLargestBall.y;
                    float distSq = dx * dx + dy * dy;
                    if (distSq > maxDistSq) {
                        maxDistSq = distSq;
                        farthestBall = p;
                    }
                }
    
                if (farthestBall == null) {
                    XposedBridge.log("强制同步失败：未找到最远目标球");
                    return;
                }
    
                // 计算角度
                float dx = farthestBall.x - myLargestBall.x;
                float dy = farthestBall.y - myLargestBall.y;
                float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
                if (angle < 0) angle += 360;
                initialAngle = 360 - angle;
    
                // 模拟滑动
                Hook.TouchEventHandler.角度滑动(
                    debugview.摇x,
                    debugview.摇y,
                    initialAngle,
                    0,
                    100,
                    Sample.同步_滑动时长,
                    触摸id,
                    true
                );
    
                // 分身点击
                for (int i = 0; i < Sample.同步_分身次数; i++) {
                    Hook.TouchEventHandler.按下(debugview.吐x, debugview.吐y, 点击id);
                    Hook.TouchEventHandler.抬起(debugview.吐x, debugview.吐y, 点击id);
                    Thread.sleep(Sample.同步_点击间隔);
                }
    
            } catch (Exception e) {
                XposedBridge.log("强制同步异常：" + e.getMessage());
            } finally {
                isRunning = false;
            }
        }).start();
    }

    /************************************************
     * 10) 重新获取最大球(若有需要)
     ************************************************/
    private void reAcquireSyncBall(Context context) {
        if (!同步检测) return;
        if (rankToPlayers.containsKey(specifiedRankId)) {
            List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
            if (syncGroup != null && !syncGroup.isEmpty()) {
                同步选中索引 = 0;
                同步初始目标体积 = -1;
            } else {
                停止检测并恢复状态();
            }
        } else {
            停止检测并恢复状态();
        }
    }

    /************************************************
     * 11) 获取我方最大球(示例)
     ************************************************/
    private PlayerEntity getMyLargestBall() {
        if (!rankToPlayers.containsKey(myRankId)) {
            return null;
        }
        List<PlayerEntity> myGroup = rankToPlayers.get(myRankId);
        if (myGroup == null || myGroup.isEmpty()) {
            return null;
        }
        return myGroup.get(0);
    }
    
    public static <T> T 读取(Context context, String fileName, Class<T> type) {
        File directory = context.getExternalFilesDir(null); // 外置存储私有目录
        if (directory == null) {
            //Log.e(TAG, "外置存储不可用");
            return null;
        }

        File file = new File(directory, fileName); // 找到目标文件
        if (!file.exists()) {
            //Log.e(TAG, "文件不存在: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.readLine(); // 假设文件只存储单行内容
            return convertContent(content, type);
        } catch (IOException e) {
            //Log.e(TAG, "文件读取失败: " + e.getMessage(), e);
        }
        return null;
    }
    
    private <T> void 保存(Context context, String fileName, T data) {
        File file = new File(context.getFilesDir(), fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static <T> T convertContent(String content, Class<T> type) {
        if (type == Integer.class) {
            return type.cast(Integer.parseInt(content));
        } else if (type == Float.class) {
            return type.cast(Float.parseFloat(content));
        } else if (type == Boolean.class) {
            return type.cast(Boolean.parseBoolean(content));
        } else {
            return type.cast(content); // 默认返回字符串
        }
    }
    
    private void showToast(Context context, String message, int duration) {
        handler.post(() -> Toast.makeText(context, message, duration).show());
    }

    private static int dpToPx(int dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * metrics.density);
    }

}
