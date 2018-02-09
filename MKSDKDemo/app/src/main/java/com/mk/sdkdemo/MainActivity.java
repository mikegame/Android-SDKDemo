package com.mk.mksdkdemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


import com.mk.sdk.MKSDK;
import com.mk.sdk.models.biz.output.MKOrder;
import com.mk.sdk.models.biz.output.MKRole;
import com.mk.sdk.models.biz.output.MKUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imbg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final Button initButton = (Button) findViewById(R.id.init);
        final Button loginButton = (Button) findViewById(R.id.login);
        final Button payButton = (Button) findViewById(R.id.pay);
        final Button centerButton = (Button) findViewById(R.id.center);
        final Button logoutButton = (Button) findViewById(R.id.logout);

        imbg = (ImageView) findViewById(R.id.imbg);

        /*SDK初始化*/
        init();

        /*注销回调*/
        MKSDK.getInstance().setSdkLogoutCallback(new MKSDK.IMKSDKLogoutCallback() {
            @Override
            public void logout() {
                Log.e("MKSDKDemo", "注销成功");
            }
        });

        /*登陆回调*/
        MKSDK.getInstance().setSdkLoginCallback(new MKSDK.IMKSDKLoginCallback() {
            @Override
            public void loginSuccess(MKUser user) {
                String username = user.getUsername();
                String accessToken = user.getAccessToken();
                String userId = user.getUsername();
                String text = "userId = " + userId + ";username = " + username + ";accessToken = " + accessToken;
                Log.e("MKSDKDemo", "登陆成功" + text);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String timeStr = formatter.format(curDate);

                MKRole role = new MKRole();
                role.setRoleId("9527");
                role.setRoleName("凯特琳");
                role.setServerId("server1");
                role.setServerName("紫陌红尘");
                role.setRoleLevel(1);
                role.setLoginTime(timeStr);
                MKSDK.getInstance().mkSaveRole(role);

            }

            @Override
            public void loginFail(String errorString) {
                Log.e("MKSDKDemo", "登陆失败" + errorString);
            }
        });

        initButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MKSDKDemo","initButton");
                init();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MKSDK.getInstance().mkLogin();
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MKOrder orderModel = new MKOrder();
                orderModel.setProductId("productId1");
                orderModel.setServerId("serverId1");
                orderModel.setServerName("紫陌红尘");
                orderModel.setTotalFee(200);
                orderModel.setRoleId("9527");
                orderModel.setRoleName("GG20思密达");
                orderModel.setProductId("productId1");
                orderModel.setProductName("拉克丝小姐姐");
                orderModel.setProductDescription("真是一个深思熟虑的选择");
                orderModel.setOrderId(getOrderStringByTime());
                orderModel.setCustomInfo("自定义字段");
                Log.e("MKSDKDemo","" + orderModel.toString());
                MKSDK.getInstance().mkPay(orderModel);
            }
        });
//
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MKSDK.getInstance().mkCenter();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MKSDK.getInstance().mkLogout();
            }
        });

    }

    public String getOrderStringByTime(){
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeNowString = dateFormat.format(nowDate) + getRandom();
        return timeNowString;
    }

    public String getRandom() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public void init(){
        MKSDK.getInstance().mkInit(MainActivity.this, 1, 1,
                "4f76c696869efaa7f84afe5a2d0de332", "0588d0cc6e180a5c1c34bd09526f2c03", "a3bbe541c303dd893a95759a625fda69", "unknown",
                new MKSDK.IMKSDKInitCallback() {
                    @Override
                    public void initSuccess() {
                        Log.e("MKSDKDemo", "初始化SDK成功");
                    }

                    @Override
                    public void initFail(String errorString) {
                        Log.e("MKSDKDemo", "初始化SDK失败-" + errorString);
                    }
                });

        /*注销回调*/
        MKSDK.getInstance().setSdkLogoutCallback(new MKSDK.IMKSDKLogoutCallback() {
            @Override
            public void logout() {
                Log.e("MKSDKDemo", "注销成功");
            }
        });
    }

    @Override
    protected void onResume() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            imbg.setImageResource(R.drawable.bgp);
        }
        else
        {
            imbg.setImageResource(R.drawable.bgl);
        }

        MKSDK.getInstance().getFloatView().showFloatView();
        super.onResume();
    }

    @Override
    protected void onStop() {
        MKSDK.getInstance().getFloatView().removeFloatView();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        MKSDK.getInstance().getFloatView().removeFloatView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        MKSDK.getInstance().mkExit(new MKSDK.IMKSDKExitCallback() {
            @Override
            public void exit(boolean isExist) {
                Log.e("MKSDKDemo","" + isExist);
            }
        });
    }
}
