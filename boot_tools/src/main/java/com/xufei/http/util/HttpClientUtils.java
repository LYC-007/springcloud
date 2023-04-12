package com.xufei.http.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * HttpClient工具类
 */
@Slf4j
public class HttpClientUtils {
    /**
     * 上传文件至服务器
     *
     * @param url
     * @param file
     */
    public static String httpPostFile(String url, MultipartFile file) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            String filename = file.getOriginalFilename();
            builder.addBinaryBody("file", file.getBytes(), ContentType.MULTIPART_FORM_DATA, filename);
            // 如果需要，传递额外参数
            // StringBody fileName = new StringBody("文件名称", ContentType.MULTIPART_FORM_DATA);
            // StringBody userName = new StringBody("用户名", ContentType.MULTIPART_FORM_DATA);
            // builder.addPart("fileName", fileName);
            // builder.addPart("userName", userName);

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("结果：" + result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败！");
        }
    }

    /**
     * GET---无参测试
     *
     * @date
     */

    public static String doGetTestOne(String url) {

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        // 响应模型 由客户端执行(发送)Get请求
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("响应内容长度为:" + responseEntity.getContentLength());
                log.info("响应内容为:" + EntityUtils.toString(responseEntity));
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;
    }

    /**
     * GET---有参测试 (方式一:手动在url后面加上参数)
     * <p>
     * 参数示例：
     * url:http://localhost:8080/list
     * StringBuilder params = new StringBuilder();
     * try {
     * // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
     * params.append("name=").append(URLEncoder.encode("&", "utf-8")).append("&").append("age=24");
     * } catch (UnsupportedEncodingException e1) {
     * e1.printStackTrace();
     * }
     *
     * @date
     */

    public static String doGetTestWayOne(String url, StringBuilder params) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url + "?" + params);
        // 响应模型
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("响应内容长度为:" + responseEntity.getContentLength());
                log.info("响应内容为:" + EntityUtils.toString(responseEntity));
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;
    }

    /**
     * GET---有参测试 (方式二:将参数放入键值对类中,再放入URI中,从而通过URI得到HttpGet实例)
     * 参数示例
     *
     * @param scheme http
     * @param host   127.0.0.1
     * @param port   8080
     * @param path   /doGetControllerTwo
     * @param params 将参数放入键值对类NameValuePair中,再放入集合中
     *               List params = new ArrayList<>();
     *               params.add(new BasicNameValuePair("name", "&"));
     *               params.add(new BasicNameValuePair("age", "18"));
     * @date
     */

    public static String doGetTestWayTwo(String scheme, String host, int port, String path, List params) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 参数
        URI uri = null;
        try {
            // 设置uri信息,并将参数集合放入uri;
            // 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
            uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path).setParameters(params).build();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        // 创建Get请求
        HttpGet httpGet = new HttpGet(uri);
        // 响应模型
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("响应内容长度为:" + responseEntity.getContentLength());
                log.info("响应内容为:" + EntityUtils.toString(responseEntity));
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;
    }

    /**
     * POST---无参测试
     *
     * @param url
     */

    public static String doPostTestOne(String url) {

        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        // 响应模型
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build(); CloseableHttpResponse response = httpClient.execute(httpPost)) {

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("响应内容长度为:" + responseEntity.getContentLength());
                log.info("响应内容为:" + EntityUtils.toString(responseEntity));
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;

    }

    /**
     * POST---有参测试(普通参数)
     *
     * @param url
     * @param params StringBuilder params = new StringBuilder();
     *               // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
     *               params.append("phone=").append(URLEncoder.encode("admin", "utf-8"));
     *               params.append("&");
     *               params.append("password=admin");
     */

    public static String doPostTestFour(String url, StringBuilder params) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url + "?" + params);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("响应内容长度为:" + responseEntity.getContentLength());
                log.info("响应内容为:" + EntityUtils.toString(responseEntity));
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;
    }

    /**
     * POST---有参测试(对象参数)
     *
     * @param url
     * @param jsonString //User user = new User();
     *                   //user.setName("潘晓婷");
     *                   //user.setAge(18);
     *                   //user.setGender("女");
     *                   //user.setMotto("姿势要优雅~");
     *                   // 我这里利用阿里的fastjson，将Object转换为json字符串;
     *                   // (需要导入com.alibaba.fastjson.JSON包)
     *                   //String jsonString = JSON.toJSONString(user);
     * @return
     */

    public static String doPostTestTwo(String url, String jsonString) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("响应内容长度为:" + responseEntity.getContentLength());
                log.info("响应内容为:" + EntityUtils.toString(responseEntity));
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;
    }

    /**
     * 发送文件
     * <p>
     * multipart/form-data传递文件(及相关信息)
     * <p>
     * 注:如果想要灵活方便的传输文件的话，
     * <p>
     * 除了引入org.apache.httpcomponents基本的httpclient依赖外
     * <p>
     * 再额外引入org.apache.httpcomponents的httpmime依赖。
     * <p>
     * 追注:即便不引入httpmime依赖，也是能传输文件的，不过功能不够强大。
     * MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
     * // 第一个文件
     * String filesKey = "files";
     * File file1 = new File("C:\\Users\\JustryDeng\\Desktop\\back.jpg");
     * multipartEntityBuilder.addBinaryBody(filesKey, file1);
     * // 第二个文件(多个文件的话，使用同一个key就行，后端用数组或集合进行接收即可)
     * File file2 = new File("C:\\Users\\JustryDeng\\Desktop\\头像.jpg");
     * // 防止服务端收到的文件名乱码。 我们这里可以先将文件名URLEncode，然后服务端拿到文件名时在URLDecode。就能避免乱码问题。
     * // 文件名其实是放在请求头的Content-Disposition里面进行传输的，如其值为form-data; name="files"; filename="头像.jpg"
     * multipartEntityBuilder.addBinaryBody(filesKey, file2, ContentType.DEFAULT_BINARY, URLEncoder.encode(file2.getName(), "utf-8"));
     * // 其它参数(注:自定义contentType，设置UTF-8是为了防止服务端拿到的参数出现乱码)
     * ContentType contentType = ContentType.create("text/plain", StandardCharsets.UTF_8);
     * multipartEntityBuilder.addTextBody("name", "邓沙利文", contentType);
     * multipartEntityBuilder.addTextBody("age", "25", contentType);
     */

    public static String sendFile(String url, MultipartEntityBuilder multipartEntityBuilder) {
        HttpPost httpPost = new HttpPost(url);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build(); CloseableHttpResponse response = httpClient.execute(httpPost);) {

            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPost.setEntity(httpEntity);

            HttpEntity responseEntity = response.getEntity();
            log.info("HTTPS响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("HTTPS响应内容长度为:" + responseEntity.getContentLength());
                // 主动设置编码，来防止响应乱码
                String responseStr = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                log.info("HTTPS响应内容为:" + responseStr);
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;
    }

    /**
     * 发送流
     *
     * @param url
     * @param is
     * @return
     */
    public static String sendStream(String url, InputStream is) {
        HttpPost httpPost = new HttpPost(url);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
            InputStreamEntity ise = new InputStreamEntity(is);
            httpPost.setEntity(ise);
            HttpEntity responseEntity = response.getEntity();
            log.info("HTTPS响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                log.info("HTTPS响应内容长度为:" + responseEntity.getContentLength());
                // 主动设置编码，来防止响应乱码
                String responseStr = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                log.info("HTTPS响应内容为:" + responseStr);
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException("请求异常，异常信息为：" + e.getMessage());
        }
        return null;
    }

}

