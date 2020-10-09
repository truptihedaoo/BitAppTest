package com.example.bitapplication.javaexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import com.example.bitapplication.R;

import com.example.bitapplication.WheelView;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private JSONObject objest;
    private String selectedItem = "";
    private int selectedItemPosition;
    private boolean statusBarDark;
    private List currencyList;
    private List priceList;
    WheelView wheelView;
    public JSONObject jsonObject, jsonObject1;
    @NotNull
    private ArrayList arrayList;
    Call call;
    @NotNull
    private ArrayList cuisineslist;
    TextView tvTime;

 

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_main);

        int var16 = VERSION.SDK_INT;
        if (19 <= var16) {
            if (20 >= var16 && VERSION.SDK_INT >= 19) {
                this.setWindowFlag((Activity) this, true);
            }
        }

        Window window;
        View view;
        if (VERSION.SDK_INT >= 19) {
            window = getWindow();
            view = window.getDecorView();
            view.setSystemUiVisibility(1280);
        }

        if (VERSION.SDK_INT >= 21 && VERSION.SDK_INT >= 23) {
            if (this.statusBarDark) {
                window = this.getWindow();
                view = window.getDecorView();
                view.setSystemUiVisibility(1024);
            }

            window = this.getWindow();
            window.setStatusBarColor(0);
            this.setWindowFlag((Activity) this, false);
        }

        WindowManager var20 = this.getWindowManager();
        Display mDisplay = var20.getDefaultDisplay();
        Point mDisplaySize = new Point();
        mDisplay.getSize(mDisplaySize);
        int maxX = mDisplaySize.x;
        int maxY = mDisplaySize.y;
        RelativeLayout var21 = (RelativeLayout) findViewById(R.id.pickerviewbg);
        var21.setY((float) maxY);
        int alpha = 85;
        int alphaColor = ColorUtils.setAlphaComponent(-16777216, alpha);
        ValueAnimator colorAnimation = ValueAnimator.ofObject((TypeEvaluator) (new ArgbEvaluator()), new Object[]{0, alphaColor});
        colorAnimation.setDuration(500L);
        colorAnimation.addUpdateListener((AnimatorUpdateListener) (new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator animator) {
                LinearLayout window = (LinearLayout) findViewById(R.id.pickerviewtransbg);
                Intrinsics.checkExpressionValueIsNotNull(animator, "animator");
                Object obj = animator.getAnimatedValue();
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                } else {
                    window.setBackgroundColor((Integer) obj);
                }
            }
        }));
        colorAnimation.start();

        ((RelativeLayout) this.findViewById(R.id.pickerviewbg)).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        wheelView = (WheelView) findViewById(R.id.wheelview);
        wheelView.setSelectedItemColor(-16777216);
        wheelView.setUnselectedItemColor(-16777216);
        wheelView.setLinesColor(0);
        wheelView.setItemTextSize(25.0F);
        wheelView.setOffset(1);
        String defaultValue = "2016";
        wheelView.setSelection(0);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText("Price");
        apicall();
          }

    @RequiresApi(19)
    private final void setWindowFlag(Activity activity, boolean on) {
        Window win = activity.getWindow();
        Intrinsics.checkExpressionValueIsNotNull(win, "win");
        LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= 67108864;
        } else {
            winParams.flags &= ~67108864;
        }

        win.setAttributes(winParams);
    }

    public void onBackPressed() {
        int alpha = 85;
        int alphaColor = ColorUtils.setAlphaComponent(-16777216, alpha);
        ValueAnimator colorAnimation = ValueAnimator.ofObject((TypeEvaluator) (new ArgbEvaluator()), new Object[]{alphaColor, 0});
        Intrinsics.checkExpressionValueIsNotNull(colorAnimation, "colorAnimation");
        colorAnimation.setDuration(500L);
        colorAnimation.addUpdateListener((AnimatorUpdateListener) (new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator animator) {
                LinearLayout window = (LinearLayout) findViewById(R.id.pickerviewtransbg);
                Intrinsics.checkExpressionValueIsNotNull(animator, "animator");
                Object obj = animator.getAnimatedValue();
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                } else {
                    window.setBackgroundColor((Integer) obj);
                }
            }
        }));
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pickerviewbg);
        Intrinsics.checkExpressionValueIsNotNull(relativeLayout, "pickerviewbg");
        slideDown((View) relativeLayout);
        colorAnimation.addListener((AnimatorListener) (new AnimatorListenerAdapter() {
            public void onAnimationEnd(@NotNull Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }
        }));
        colorAnimation.start();
    }

    private final void slideUp(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", new float[]{0.0F});
        objectAnimator.setDuration(600L);
        objectAnimator.start();
    }

    private final void slideDown(View view) {
        WindowManager window = this.getWindowManager();
        Intrinsics.checkExpressionValueIsNotNull(window, "windowManager");
        Display mDisplay = window.getDefaultDisplay();
        Point mDisplaySize = new Point();
        mDisplay.getSize(mDisplaySize);
        int maxY = mDisplaySize.y;
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) maxY});
        Intrinsics.checkExpressionValueIsNotNull(animation, "animation");
        animation.setDuration(600L);
        animation.start();
        TextView textView = (TextView) this.findViewById(R.id.tvTime);
        textView.setText((CharSequence) this.selectedItem);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout relativeLayout = (RelativeLayout) this.findViewById(R.id.pickerviewbg);
        slideUp((View) relativeLayout);
        TextView window = (TextView) this.findViewById(R.id.tvTime);
        window.setText((CharSequence) this.selectedItem);
    }

    public final void apicall() {

        call = RetrofitClients.getInstance().getMyApi().getCash();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {

                    jsonObject = new JSONObject(String.valueOf(response.body()));


                    jsonObject1 = new JSONObject(jsonObject.getString("bpi"));
                    Log.d("jsonObject", String.valueOf(jsonObject1));

                    JSONObject jsonObject2 = new JSONObject(jsonObject1.getString("USD"));
                    JSONObject jsonObject3 = new JSONObject(jsonObject1.getString("GBP"));
                    JSONObject jsonObject4 = new JSONObject(jsonObject1.getString("EUR"));




                    currencyList.add(jsonObject2.getString("code"));
                    arrayList.add(jsonObject2.getString("rate"));
                    currencyList.add(jsonObject3.getString("code"));
                    arrayList.add(jsonObject3.getString("rate"));
                    currencyList.add(jsonObject4.getString("code"));
                    arrayList.add(jsonObject4.getString("rate"));


                    wheelView.setItemss(currencyList);
                    selectedItem = wheelView.getGetSelectedItem();

                    tvTime.setText(String.valueOf(arrayList.get(0)));
                    Log.d("listCuisines", arrayList.toString());
                    selectedItemPosition = wheelView.getGetSelectedIndex();
                    wheelView.setOnWheelViewListener((WheelView.OnWheelViewListener) (new WheelView.OnWheelViewListener() {
                        public void onSelected(int selectedIndex, @NotNull String item) {
                            selectedItem = item;
                            selectedItemPosition = selectedIndex;

                            tvTime.setText(String.valueOf(arrayList.get(selectedItemPosition-1)));
                        }
                    }));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ((LinearLayout) findViewById(R.id.pickerviewtransbg)).setOnClickListener((new OnClickListener() {
            public final void onClick(View it) {
                onBackPressed();
            }
        }));



    }

    public MainActivity() {
        List var3 = (List) (new ArrayList());
        this.currencyList = var3;
        this.arrayList = new ArrayList();
        this.cuisineslist = new ArrayList();
    }

}
