package com.xufei.forest.client;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Request;
import com.dtflys.forest.http.ForestResponse;
import org.springframework.stereotype.Component;

/**
 * HTTPS请求:为保证网络访问安全，现在大多数企业都会选择使用SSL验证来提高网站的安全性。
 * 以前用其他http框架处理https的时候，总觉得特别麻烦，尤其是双向证书。
 *
 * Forest对于这方面也想的很周到，底层完美封装了对https单双向证书的支持。也是只要通过简单的配置就能迅速完成。
 */
@Component
//在一个个方法上设置太麻烦，也可以在 @BaseRequest 注解中设置一整个接口类的SSL协议
//@BaseRequest(sslProtocol = "TLS")
public interface SSLClient {
    /**
     * 在某个请求接口上通过 sslProtocol 属性设置单向SSL协议
     * @return
     */
    @Get(
            url = "https://localhost:5555/hello/user",
            sslProtocol = "SSL"
    )
    ForestResponse<String> testTruestSSLGet();




    @Request(
            url = "${base}/pay",
            contentType = "application/json",
            type = "post",
            dataType = "json",
            keyStore = "pay-keystore",
            data = "${json($0)}"
    )//其中pay-keystore对应着application.yml里的ssl-key-stores
    ForestResponse<String> testTruestSSLPost();
}
