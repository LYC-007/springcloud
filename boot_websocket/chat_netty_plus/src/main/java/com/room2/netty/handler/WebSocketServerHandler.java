package com.room2.netty.handler;

import com.room2.netty.pojo.PojoEndpointServer;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.room2.service.MyWebSocket.channelGroup;
import static com.room2.service.MyWebSocket.userMap;


/**
 * SimpleChannelInboundHandler<WebSocketFrame>  使用该泛型可以获取到web客户端传过来的参数，根据消息传输的不同方式(有两种方式:BinaryWebSocketFrame和TextWebSocketFrame)来调用PojoEndpointServer中的不同方法等;
 * WebSocketFrame是一个抽象类，它的具体实现类有下面几种：
 * BinaryWebSocketFrame和TextWebSocketFrame很好理解，他们代表消息传输的两种方式。
 * CloseWebSocketFrame是代表关闭连接的frame。
 * ContinuationWebSocketFrame表示消息中多于一个frame的表示。
 * PingWebSocketFrame和PongWebSocketFrame是两个特殊的frame，他们主要用来做服务器和客户端的探测。
 * 这些frame都是跟Websocket的消息类型一一对应的，理解了websocket的消息类型，对应理解这些frame类还是很有帮助的。
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
private static final Logger logger=LoggerFactory.getLogger(WebSocketServerHandler.class);
    private final PojoEndpointServer pojoEndpointServer;

    public WebSocketServerHandler(PojoEndpointServer pojoEndpointServer) {
        this.pojoEndpointServer = pojoEndpointServer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handleWebSocketFrame(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        pojoEndpointServer.doOnError(ctx.channel(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与客户端断开连接，通道关闭！");
        //从channelGroup中移除
        channelGroup.remove(ctx.channel());
        pojoEndpointServer.doOnClose(ctx.channel());
    }



    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        pojoEndpointServer.doOnEvent(ctx.channel(), evt);
    }

    /**
     * 该方法通过前端传过来不同的消息类型,调用不同的方法
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        /**
         * BinaryWebSocketFrame和TextWebSocketFrame很好理解，他们代表消息传输的两种方式。
         * CloseWebSocketFrame是代表关闭连接的frame。
         * ContinuationWebSocketFrame表示消息中多于一个frame的表示。
         * PingWebSocketFrame和PongWebSocketFrame是两个特殊的frame，他们主要用来做服务器和客户端的探测。
         * 这些frame都是跟Websocket的消息类型一一对应的，理解了websocket的消息类型，对应理解这些frame类还是很有帮助的。
         */
        if (frame instanceof TextWebSocketFrame) {
            pojoEndpointServer.doOnMessage(ctx.channel(), frame);
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (frame instanceof CloseWebSocketFrame) {
            Channel channel = ctx.channel();
            ChannelId channelId = channel.id();
            //userMap.remove(userId);
            Optional.of(channelId).ifPresent(o -> channelGroup.remove(o));
            ctx.writeAndFlush(frame.retainedDuplicate()).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        if (frame instanceof BinaryWebSocketFrame) {
            pojoEndpointServer.doOnBinary(ctx.channel(), frame);
            return;
        }
        if (frame instanceof PongWebSocketFrame) {
            logger.info("程序结束的终点:{}", WebSocketServerHandler.class.getName());
        }
    }



}