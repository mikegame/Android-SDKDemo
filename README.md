# 米壳Android SDK客户端说明文档
[![License MIT](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](https://raw.githubusercontent.com/mikegame/Android-SDKDemo/master/LICENSE)&nbsp;



演示项目
==============
查看并运行 `Android-SDKDemo/MKSDKDemo`


使用
==============



1. 将 libMKSDK-release.aar 添加(拖放)到你的工程目录下lib中。
<img src="https://github.com/mikegame/Android-SDKDemo/blob/master/Snapshots/Framework.png"><br/>
2. 在对应项目下找到build.gradle添加以下库。<br/>
   
```java
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:24.0.0'
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.loopj.android:android-async-http:1.4.9'
    compile 'com.android.support:appcompat-v7:24.0.0'
    compile(name: 'libMKSDK-release', ext:'aar')
}
```


3. 导入 `com.mk.sdk`。
```
import com.mk.sdk.MKSDK;
import com.mk.sdk.models.biz.output.MKOrder;
import com.mk.sdk.models.biz.output.MKRole;
import com.mk.sdk.models.biz.output.MKUser;
```

4. 在AndroidManifest.xml添加以下权限
```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_LOGS"/>
<uses-permission android:name="android.permission.GET_TASKS" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```

5. 初始化SDK。并更改对应的参数

#### 初始化SDK
 *  @param gameId    游戏编号
 *  @param subGameId 游戏子包
 *  @param apiKey 游戏密钥
 *  @param trackKey 热云广告KEY
 *  @param gameKey  热云运营KEY
 *  @param ryChannelID 渠道ID
```java
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
```

#### 登陆方法

```java
MKSDK.getInstance().mkLogin();
```

#### 登陆回调方法

```java
MKSDK.getInstance().xsLogin(new MKSDK.IMKSDKLoginCallback() {
    @Override
    public void loginSuccess(MKUser user) {
    String username = user.getUsername();
    String token = user.getToken();
    String userId = user.getUsername();
    String text = "userId = " + userId + ";username = " + username + ";token = " + token;
    Log.e("MKSDKDemo", "登陆成功" + text);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
    Date curDate =  new Date(System.currentTimeMillis());
    String timeStr = formatter.format(curDate);

    //上报角色信息
    MKRole role = new MKRole();
    role.setRoleId("9527");
    role.setRoleName("凯特琳");
    role.setServerId("server1");
    role.setServerName("紫陌红尘");
    role.setRoleLevel(1);
    role.setLoginTime(timeStr);
    MKSDK().mkSaveRole(role);
}
```

@Override
public void loginFail(String errorString) {
    Log.e("MKSDKDemo", "登陆失败" + errorString);
}
});

#### 支付方法

```java
MKOrder orderModel = new MKOrder();
orderModel.setProductId("productId1");
orderModel.setServerId("serverId1");
orderModel.setServerName("紫陌红尘");
orderModel.setTotalFee(1);
orderModel.setRoleId("9527");
orderModel.setRoleName("GG20思密达");
orderModel.setProductId("productId1");
orderModel.setProductName("拉克丝小姐姐");
orderModel.setProductDescription("真是一个深思熟虑的选择");
orderModel.setOrderId(getOrderStringByTime());
orderModel.setCustomInfo("自定义字段");
Log.e("MKSDKDemo","" + orderModel.toString());
MKSDK.getInstance().mkPay(orderModel);
```


#### 用户注销回调方法

```java
MKSDK.getInstance().setSdkLogoutCallback(new MKSDK.IMKSDKLogoutCallback() {
    @Override
    public void logout() {
        Log.e("MKSDKDemo", "注销成功");
    }
});
```





系统要求
==============
该项目最低支持 `minSdkVersion 16`。



许可证
==============
MKSDK 使用 MIT 许可证，详情见 LICENSE 文件。
