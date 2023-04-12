package com.room2.service;

import com.room2.netty.annotation.*;
import com.room2.netty.pojo.Session;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;

@ServerEndpoint(path = "/myWs/{str}",host = "${ws.host}",port = "${ws.port}")
//@Component
public class MyWebSocket {

    /**
     *建立ws连接前的配置(可以为我们判断用户是否登录)
     *  WebSocket定义的格式为:new WebSocket("ws://localhost:8333/myWs/students?user=ok");
     *
     * ws = new WebSocket("ws://localhost:8333/myWs/students?user=ok");
     * @RequestParam(name = "user")   <-----------> ?user=ok
     * /myWs/{str}  <-------->  @PathVariable(name = "str") String arg
     *
     */
    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers, @RequestParam(name = "user") String req, @RequestParam MultiValueMap reqMap, @PathVariable(name = "str") String arg, @PathVariable Map pathMap){
        reqMap.forEach((key,value)->{
            System.out.println(key+"---------"+value);
        });
        System.out.println(arg+"========================");
        pathMap.forEach((key,value)->{
            System.out.println(key+"---------"+value);
        });

        //Raw use of parameterized class 'MultiValueMap'
        //采用stomp子协议
        session.setSubprotocols("stomp");
        //验证用户是否登录
        if (!"ok".equals(req)){
            System.out.println("用户登录失败!");
            session.close();
        }
    }

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String arg, @PathVariable Map pathMap){
        System.out.println("new connection");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("one connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("接收的消息为：" + message);
        session.sendText("Hello Netty!");
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }
}
