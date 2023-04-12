package com.room2.netty.matcher;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.QueryStringDecoder;
/**
 * Ant匹配模式是当下（2022-03-18）最火的对URL进行匹配的模式。
 * 很多框架都采用Ant对URL进行匹配，如Spring框架中的org.springframework.util.AntPathMatcher。
 */

public interface WsPathMatcher {

    String getPattern();

    boolean matchAndExtract(QueryStringDecoder decoder, Channel channel);
}
