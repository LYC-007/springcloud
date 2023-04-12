package com.xufei.kaptcha.util;

import org.apache.commons.codec.binary.Base64;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Base64Utils {
    private static final Base64 BASE64 = new Base64();
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 编码
     */
    public static String encode(byte[] source) {
        if (source != null && source.length > 0) {
            byte[] bytes = BASE64.encode(source);
            return new String(bytes, DEFAULT_CHARSET);
        }
        return new String(source != null ? source : new byte[0], DEFAULT_CHARSET);
    }

    /**
     * 解码
     */
    public static byte[] decode(String source) {
        if (!isEmpty(source)) {
            return BASE64.decode(source.getBytes(DEFAULT_CHARSET));
        }
        return source.getBytes(DEFAULT_CHARSET);
    }

    /**
     * 非空判断
     */
    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /*
    字符串转图片
     */
    public static void generateImage() {
        try {
            // 解密
            byte[] b = Base64Utils.decode("/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAA8AKADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDtrW1ga1hZoIySikkoOeKsCztv+feL/vgU2z/484P+ua/yqyKiMY8q0IjGPKtCIWdr/wA+0P8A3wKeLK1/59of+/YqUU4U+WPYfLHsRCytP+fWH/v2KcLG0/59YP8Av2Kh1HVLLSbKS8vrhIYIxlmb+QHc+wqvoPiPTvEVkbrT5SyK21lYYZT7itVh5Om6qj7q0vbS/qFo3saIsLP/AJ9YP+/YpwsLP/n0g/79iq2raxZ6Hpsl/fOVt4yoYqMnkgdO/WrttcRXVvHPC4eKRQ6MO4IyDUex93n5dNr2/ruHLHsINPsv+fS3/wC/Y/wpw06y/wCfO3/79L/hSm6t0mWFpoxKwyELDcR9KHv7SG4it5LiNJpfuIzAFvoKPZ36Byx7Dhp1j/z52/8A36X/AApw02x/58rf/v0v+FTrzTxU8sewcsexXGmWH/Plbf8Afpf8KeNMsP8Anxtv+/S/4VYFLuA70csewcsexANL0/8A58bb/vyv+FPGlaf/AM+Fr/35X/CsF/H3h6PxGuhG+U3hbYSPuK/9wt03dsevHWuoUhhxWtTDSpW9pC19VdboEovYrjStO/58LX/vyv8AhThpOnf9A+1/78r/AIVaFPFZcsewcsexVGk6b/0D7T/vyv8AhVbU9L0+PSL10sbVXWByrCFQQdp5HFawqrq3/IFv/wDr3k/9BNKUY8r0FKMeV6HJWf8Ax5wf9c1/lVkVXs/+POD/AK5r/KrIpx+FDj8KHCqep3T21hO8X+tWNig98cVdFZWsxs1s230qkUeHLql14o1InWbuR7eEFxFnHtWl8N9YOneJJ7VWPkXKnjPGVyQfyzWHr9iltrjRRMUaZvmXsMmrGkeXoepw3FyD9nc7RNjlD7+1foGIrUK2ElTp/wDLyF4Qta1t7dG7/N20vocqTUrvp1N/xrq+uajetaNIFglcmG0UclF/ib611/gjxhd6lFHaXNgYkjhGyZPuNjjHsazn0mK/dNRtysrOmFlU5yvtVrw9oV1YatJLFOVtJQWkgI/j9R6V81Vx1Crhfq8qai4rR6rXrt5W37O/lsotSvc5zx1ol7d69/acN6zFmA29DEO2PauYtItRj8XWSPdvPcCRX8wuTxn1PsK9l1fR0mQ5I3kZA9a8j1bR9S0u9utQiuCGjbK7Tzt/z/KvRyjNqtRfVqsopcrjG66vRK6IqQS95H0CNZs7a1M13dRQoF3FpHC4A781yFt8ZNBm1w2TRTx2Zbal633SfUr1C+/5gduMur3T/GPhi1uL6V4pdPJafyxk4x82PQHAP4Vgt4isZ4BYy6NHHpB+WNlX94h/vbvX9fc9+fB5TTtOFWnKU07NJpcvpf4m1ql2+Q5VH0Z9DjW7CWUQwXkEsxj80IjhjsyBu47civEPEreL4PEE0A1Sdre7c7XichAPQjscUzwve6B4a1WKS3up725vGEAKjCxRsRkkDqeB+XQV6Tf6IZsy9q5+d5VW5qceaMlpzxt80nro/vHbnWp5Br2naXo+lW8duznUA4bzicFvU+w9K90+H3iU+IvDdvcyNmdB5U3++AMn8eD+NeA+IIJrnxVcwSK6ojiJSQcDjj8/61658LtOfStPaLJPmPvNd+bypvL6SrVOas/e+Ut16bafoTTvzuy0PVRTxTI/uipBXyhuOFVdW/5Al/8A9e0n/oJq2Kq6v/yBL/8A69pP/QTUy+Fky+FnJWf/AB5Qf9c1/lVkVXsv+PKD/rmv8qsiiPwoI/ChwqG7jDwsPapxSldwxVFHi/jTw491fC4gISUcHPQ1zU081lZS6dqsW+ORcxTLzgjpXuepaMl0CdvNcbq/hXz1MbxB0J6GvZwebypxhSrx5oRd10kuuj/TboZyp31RX+EzSPpVzFLkxedmPP0GcV6hFZIp3AVy3hXRv7PiSNIwiL0AFdui/LiuLH4lYrEzrpWUnexUI8sUjm/FFh9u04pFM1vcxnfDMvVG/qD3FeKajc+JSs9reQ7zkgybRyPY+lfQd/Z/aIyBXF6n4WknLYBrXB5g8MmnTjPquZXs/L/IUoc3U8g8MSTQa9Hb7TtmzHIhHBB9RXos/h3zbYxeQphK42AcYq5pXg8W+oJM0QLqeGI5FejWumx+QAyjpWma5n9erRrRjyuyv6rqKEOVWPMvCvhS30q/8+KFjKeA78lR6D0r0/7MWs8Ec4qeHTYo2yFFXvLGzGK8+tWqVp89WTk+71LSS0R4d480m/tnGo2JZjCcyw4yGX1I74/l9K7X4aa1Za9pPmwqIriEhJos52nsR7H+h9K2dZ0sy5ZBzVHwx4ftNJv5rq2s0hmn4kZMjd36dK6PrFGWF9jOHvraS7dU+/k91tsKz5ro7tRxUgqOP7oqUVxFDhVXV/8AkCX/AP17Sf8AoJq2Kq6v/wAgS/8A+vaT/wBBNTL4WTL4WclZf8eVv/1zX+VWRXMxa1cxRJGqREIoUZB7fjUn9v3X/POH/vk/41lGtGyM41Y2R0opwrmf+Ehu/wDnnB/3yf8AGl/4SK7/AOecH/fJ/wAar20R+2idPtBpj2qOeVFc5/wkl5/zyg/75P8AjS/8JLef88oP++T/AI0e2iHtonTRW6R/dGKsgVyP/CT3v/PK3/75b/Gl/wCEovf+eVv/AN8t/jR7aIe2idfjNIYVbqBXJf8ACVX3/PK3/wC+W/xpf+Ervv8Anlbf98t/jR7aIe2idYtrGDkKKsIoArjP+Etv/wDnjbf98t/jS/8ACX6h/wA8bb/vlv8A4qj20Q9tE7YU8Vw//CYah/zxtf8Avlv/AIql/wCEy1H/AJ42v/fLf/FUe2iHtonbNErjkUkdqiHIFcX/AMJnqP8Azxtf++G/+Kpf+E11L/nhaf8AfDf/ABVHtoh7aJ3qjAp4rgP+E21L/nhaf98N/wDFUv8AwnGp/wDPC0/74b/4qj20Q9tE9BFVdX/5Aeof9e0n/oJriv8AhOdT/wCeFp/3w3/xVR3PjPUbq1mt3htQkqMjFVbIBGOPmqZVo2YpVY2Z/9k=");
            // 处理数据
            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream("D:/"+"filename.jpg");
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 测试代码
     */
    public static void main(String[] args) {
        generateImage();
    }
}
