package com.xufei.string;

/**
 * String.format(...)方法的测试
 */
public class StringTest {
    public static void main(String[] args) {
        //微信开发平台授权baseUrl   %s相当于？表示占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&state=%s";
        //设置%s中的值
        String url = String.format(
                baseUrl,
                "ConstantWxUtils.WX_OPEN_APP_ID",
                "redirect_url",
                "xunqi"
        );
        System.out.println(url);

    }
}
