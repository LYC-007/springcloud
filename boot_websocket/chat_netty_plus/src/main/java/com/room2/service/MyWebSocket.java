package com.room2.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.room2.model.OnlineMessage;
import com.room2.netty.annotation.*;
import com.room2.netty.pojo.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注意在使用Session的时候注意Session是被那个Channel初始化的:PojoEndpointServer类的doBeforeHandshake方法
 */
@ServerEndpoint(path = "/ws",host = "${netty.host}",port = "${netty.port}")
//@Component
public class MyWebSocket {
    /**
     * 存储已经登录用户的channel对象
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存储用户id和用户的channelId绑定
     */
    public static ConcurrentHashMap<String, ChannelId> userMap = new ConcurrentHashMap<>();
    /**
     *建立ws连接前的配置(可以为我们判断用户是否登录)
     *  WebSocket定义的格式为:new WebSocket("ws://localhost:8333/myWs/students?user=ok");
     *
     * ws = new WebSocket("ws://localhost:8333/myWs/students?user=ok");
     * @RequestParam(name = "user")   <-----------> ?user=ok
     * /myWs/{str}  <-------->  @PathVariable(name = "str") String arg
     */
    @BeforeHandshake
    public void handshake(Session session, @RequestParam(name = "userId") String req){
       if (Objects.nonNull(req)){
           userMap.put(req,session.id());
           LOGGER.info("MyWebSocket登录的用户id是：{}",req);
           LOGGER.info("MyWebSocket登录用户创建的ChannelId:{}",session.id());
       }else {
           System.out.println("用户登录失败！！！！");
           session.close();
       }
        session.setSubprotocols("stomp");//采用stomp子协议
    }
    private static final Logger LOGGER=LoggerFactory.getLogger(MyWebSocket.class);
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

        LOGGER.info("MyWebSocket发送的消息是:{}",message);
        OnlineMessage onlineMessage = JSON.parseObject(message, OnlineMessage.class);
        String channelId = onlineMessage.getAcceptId();//
        if (channelId != null) {
            Channel ct = channelGroup.find(userMap.get(channelId));//ConcurrentHashMap<String, ChannelId> userMap
            Session session1 = new Session(ct);
            if (ct != null) {
                session1.sendText(message);
                //或者使用上面的这句: ct.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(onlineMessage)));
            }
        }
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
