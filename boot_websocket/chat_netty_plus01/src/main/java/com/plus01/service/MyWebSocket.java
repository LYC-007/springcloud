package com.plus01.service;


import com.alibaba.fastjson.JSON;
import com.plus01.model.OnlineMessage;
import com.plus01.netty.annotation.*;
import com.plus01.netty.pojo.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 注意在使用Session的时候注意Session是被那个Channel初始化的:PojoEndpointServer类的doBeforeHandshake方法
 */
@ServerEndpoint(path = "/ws", host = "${netty.host}", port = "${netty.port}")
//@Component
public class MyWebSocket {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> stringStringHashMap = new ConcurrentHashMap<>();
        stringStringHashMap.put("A0", "1002");
        stringStringHashMap.put("A1", "1002");
        stringStringHashMap.put("B0", "1002");
        stringStringHashMap.put("B1", "1002");
        stringStringHashMap.put("A2", "1002");
        stringStringHashMap.put("A3", "1002");


        stringStringHashMap.forEachKey(stringStringHashMap.size(), (key) -> {
            if (key.startsWith("A")) {
                copyOnWriteArraySet.add(key);
            }
        });
        copyOnWriteArraySet.forEach(System.out::println);


    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MyWebSocket.class);
    /**
     * 存储已经登录用户的channel对象
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 用于保存客服的 id
     */
    public static CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
    /**
     * 存储用户id和用户的channelId绑定
     */
    public static ConcurrentHashMap<String, ChannelId> userMap = new ConcurrentHashMap<>();

    /**
     * 建立ws连接前的配置(可以为我们判断用户是否登录)
     * WebSocket定义的格式为:new WebSocket("ws://localhost:8333/myWs/students?user=ok");
     * <p>
     * ws = new WebSocket("ws://localhost:8333/myWs/students?user=ok");
     *
     * @RequestParam(name = "user")   <-----------> ?user=ok
     * /myWs/{str}  <-------->  @PathVariable(name = "str") String arg
     */
    @BeforeHandshake
    public void handshake(Session session, @RequestParam(name = "userId") String req) {
        if (Objects.nonNull(req)) {
            userMap.put(req, session.id());
            userMap.forEachKey(userMap.size(), (key) -> {
                if (key.startsWith("customer")) {
                    copyOnWriteArraySet.add(key);
                    LOGGER.info("添加客服的名称：{}", key);
                }
            });

            LOGGER.info("MyWebSocket登录的用户id是：{}", req);
            LOGGER.info("MyWebSocket登录用户创建的ChannelId:{}", session.id());
        } else {
            System.out.println("用户登录失败！！！！");
            session.close();
        }
        session.setSubprotocols("stomp");//采用stomp子协议
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        LOGGER.info("MyWebSocket接受的消息是:{}", message);
        OnlineMessage onlineMessage = JSON.parseObject(message, OnlineMessage.class);
        String acceptId = onlineMessage.getAcceptId();
        String sendId = onlineMessage.getSendId();
        if (copyOnWriteArraySet.size() > 0) {
            if (sendId.startsWith("customer")) {//来自 客户 发给  客服
                if (Objects.isNull(acceptId)) {//如果客户前端没有指定客服
                    acceptId = copyOnWriteArraySet.stream().findAny().get();//随机给用户分发一个客服
                    Channel ct = channelGroup.find(userMap.get(acceptId));
                    if (ct != null) {
                        new Session(ct).sendText(message);
                    }
                } else {//来自  客服  发给  客户
                    Channel ct = channelGroup.find(userMap.get(acceptId));
                    if (ct != null) {
                        new Session(ct).sendText(message);
                        //或者使用上面的这句: ct.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(onlineMessage)));
                    }
                }
            }else {
                Channel ct = channelGroup.find(userMap.get(acceptId));
                if (ct != null) {
                    new Session(ct).sendText(message);
                }
            }
        } else {
            LOGGER.info("还没有客服人员上线:{}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            onlineMessage.setAcceptId(onlineMessage.getSendId());
            onlineMessage.setHeadImg("/images/head/1.jpg");
            onlineMessage.setMessage("还没有客服上线！！！！");
            session.sendText(JSON.toJSONString(onlineMessage));
        }
        LOGGER.info("MyWebSocket发送的消息是:{}", message);
    }
    /**
     * 用于指定连接成功后的回调函数
     */
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String arg, @PathVariable Map pathMap) {
        channelGroup.add(session.channel());
        System.out.println("new connection");
    }

    /**
     * 用于指定连接关闭后的回调函数。
     */
    @OnClose
    public void onClose(Session session, @RequestParam(name = "userId") String req) throws IOException {
        channelGroup.remove(session.channel());
        userMap.remove(req, session.id());
        if (req.startsWith("customer")) {
            copyOnWriteArraySet.remove(req);
        }
        System.out.println("one connection closed");
    }

    /**
     * 用于指定连接失败后的回调函数。
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
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
