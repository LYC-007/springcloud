package com.plus01.netty.pojo;

import com.plus01.netty.client.ServerEndpointConfig;
import com.plus01.netty.matcher.AntPathMatcherWrapper;
import com.plus01.netty.matcher.DefaultPathMatcher;
import com.plus01.netty.matcher.WsPathMatcher;
import com.plus01.netty.resolver.MethodArgumentResolver;
import com.plus01.netty.resolver.PathVariableMapMethodArgumentResolver;
import com.plus01.netty.resolver.PathVariableMethodArgumentResolver;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.springframework.beans.TypeMismatchException;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 该类是 可以看着是一个调度服务器，，利用反射的方式来执行MyWebSocket定义的方法;
 */
public class PojoEndpointServer {//doBeforeHandshake

    /**
     *
     * Attribute就是一个属性对象，这个属性的名称为AttributeKey<T> key，而属性的值为T value。而AttributeKey也是Constant的一个扩展，因此也有一个ConstantPool来管理和创建
     * 当Netty底层初始化Channel的时候，就会将我们设置的Attribute给设置到Channel中
     * Channel类本身继承了AttributeMap类，而AttributeMap它持有多个Attribute，这些Attribute可以通过AttributeKey来访问的。所以，才可以通过channel.attr(key).set(value)的方式将属性设置到channel中了(即，这里的attr方法实际上是AttributeMap接口中的方法)
     *
     * AttributeKey、Attribute、AttributeMap间的关系：
     * AttributeMap相对于一个map，AttributeKey相当于map的key，Attribute是一个持有key(AttributeKey)和value的对象。
     *  因此在map中我们可以通过AttributeKey key获取Attribute，从而获取Attribute中的value(即,属性值)。
     *
     *
     * Channel上的AttributeMap就是大家共享的，每一个ChannelHandler都能获取到
     * AttributeMap:key是AttributeKey，value是Attribute，我们可以根据AttributeKey找到对应的Attribute，并且我们可以指定Attribute的类型T
     *
     * AttributeKey 可以想象成一个缓存 set ，存放了一组 key 的集合
     */

    //MyWebSocket
    private static final AttributeKey<Object> POJO_KEY = AttributeKey.valueOf("WEBSOCKET_IMPLEMENT");

    //Session
    public static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("WEBSOCKET_SESSION");

    private static final AttributeKey<String> PATH_KEY = AttributeKey.valueOf("WEBSOCKET_PATH");

    //new WebSocket("ws://localhost:8333/myWs?user=ok")
    public static final AttributeKey<Map<String, String>> URI_TEMPLATE = AttributeKey.valueOf("WEBSOCKET_URI_TEMPLATE");

    public static final AttributeKey<Map<String, List<String>>> REQUEST_PARAM = AttributeKey.valueOf("WEBSOCKET_REQUEST_PARAM");

    private final Map<String, PojoMethodMapping> pathMethodMappingMap = new HashMap<>();

    private final ServerEndpointConfig config;

    private Set<WsPathMatcher> pathMatchers = new HashSet<>();

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PojoEndpointServer.class);

    public PojoEndpointServer(PojoMethodMapping methodMapping, ServerEndpointConfig config, String path) {
        addPathPojoMethodMapping(path, methodMapping);
        this.config = config;
    }

    public boolean hasBeforeHandshake(Channel channel, String path) {
        PojoMethodMapping methodMapping = getPojoMethodMapping(path, channel);
        return methodMapping.getBeforeHandshake()!=null;
    }


    /**
     * 握手连接前
     * @param channel
     * @param req
     * @param path
     */
    public void doBeforeHandshake(Channel channel, FullHttpRequest req, String path) {
        PojoMethodMapping methodMapping = null;
        methodMapping = getPojoMethodMapping(path, channel);
        Object implement = null;
        try {
            implement = methodMapping.getEndpointInstance();
        } catch (Exception e) {
            logger.error(e);
            return;
        }
        /**
         Attribute就是一个属性对象，这个属性的名称为AttributeKey 的 key，而属性的值为T value。
         而AttributeKey也是Constant的一个扩展，因此也有一个ConstantPool来管理和创建 当Netty底层初始化Channel的时候，
         就会将我们设置的Attribute给设置到Channel中 Channel类本身继承了AttributeMap类，而AttributeMap它持有多个Attribute，这些Attribute可以通过AttributeKey来访问的。
         所以，才可以通过channel.attr(key).set(value)的方式将属性设置到channel中了(即，这里的attr方法实际上是AttributeMap接口中的方法)
         AttributeKey、Attribute、AttributeMap间的关系： AttributeMap相对于一个map，AttributeKey相当于map的key，Attribute是一个持有key(AttributeKey)和value的对象。
         因此在map中我们可以通过AttributeKey key获取Attribute，从而获取Attribute中的value(即,属性值)。
         Channel上的AttributeMap就是大家共享的，每一个ChannelHandler都能获取到 AttributeMap:key是AttributeKey，value是Attribute，我们可以根据AttributeKey找到对应的Attribute，并且我们可以指定Attribute的类型T AttributeKey 可以想象成一个缓存 set ，存放了一组 key 的集合
         */

        //channel.attr(POJO_KEY).set(implement);
        Attribute<Object> attr = channel.attr(POJO_KEY);
        attr.set(implement);
        Session session = new Session(channel);
        channel.attr(SESSION_KEY).set(session);
        Method beforeHandshake = methodMapping.getBeforeHandshake();
        if (beforeHandshake != null) {
            try {
                /**
                 *
                 * implement就是:MyWebSocket
                 *
                 */
                beforeHandshake.invoke(implement, methodMapping.getBeforeHandshakeArgs(channel, req));
            } catch (TypeMismatchException e) {
                throw e;
            } catch (Throwable t) {
                logger.error(t);
            }
        }
    }

    public void doOnOpen(Channel channel, FullHttpRequest req, String path) {
        PojoMethodMapping methodMapping = getPojoMethodMapping(path, channel);

        Object implement = channel.attr(POJO_KEY).get();
        if (implement==null){
            try {
                implement = methodMapping.getEndpointInstance();
                channel.attr(POJO_KEY).set(implement);
            } catch (Exception e) {
                logger.error(e);
                return;
            }
            Session session = new Session(channel);
            channel.attr(SESSION_KEY).set(session);
        }

        Method onOpenMethod = methodMapping.getOnOpen();
        if (onOpenMethod != null) {
            try {
                onOpenMethod.invoke(implement, methodMapping.getOnOpenArgs(channel, req));
            } catch (TypeMismatchException e) {
                throw e;
            } catch (Throwable t) {
                logger.error(t);
            }
        }
    }

    public void doOnClose(Channel channel) {
        Attribute<String> attrPath = channel.attr(PATH_KEY);
        PojoMethodMapping methodMapping = null;
        if (pathMethodMappingMap.size() == 1) {
            methodMapping = pathMethodMappingMap.values().iterator().next();
        } else {
            String path = attrPath.get();
            methodMapping = pathMethodMappingMap.get(path);
            if (methodMapping == null) {
                return;
            }
        }
        if (methodMapping.getOnClose() != null) {
            if (!channel.hasAttr(SESSION_KEY)) {
                return;
            }
            Object implement = channel.attr(POJO_KEY).get();
            try {
                methodMapping.getOnClose().invoke(implement,methodMapping.getOnCloseArgs(channel));
            } catch (Throwable t) {
                logger.error(t);
            }
        }
    }


    public void doOnError(Channel channel, Throwable throwable) {
        Attribute<String> attrPath = channel.attr(PATH_KEY);
        PojoMethodMapping methodMapping = null;
        if (pathMethodMappingMap.size() == 1) {
            methodMapping = pathMethodMappingMap.values().iterator().next();
        } else {
            String path = attrPath.get();
            methodMapping = pathMethodMappingMap.get(path);
        }
        if (methodMapping.getOnError() != null) {
            if (!channel.hasAttr(SESSION_KEY)) {
                return;
            }
            Object implement = channel.attr(POJO_KEY).get();
            try {
                Method method = methodMapping.getOnError();
                Object[] args = methodMapping.getOnErrorArgs(channel, throwable);
                method.invoke(implement, args);
            } catch (Throwable t) {
                logger.error(t);
            }
        }
    }

    public void doOnMessage(Channel channel, WebSocketFrame frame) {
        Attribute<String> attrPath = channel.attr(PATH_KEY);
        PojoMethodMapping methodMapping = null;
        if (pathMethodMappingMap.size() == 1) {
            methodMapping = pathMethodMappingMap.values().iterator().next();
        } else {
            String path = attrPath.get();
            methodMapping = pathMethodMappingMap.get(path);
        }
        if (methodMapping.getOnMessage() != null) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            Object implement = channel.attr(POJO_KEY).get();

            try {
                methodMapping.getOnMessage().invoke(implement, methodMapping.getOnMessageArgs(channel, textFrame));
            } catch (Throwable t) {
                logger.error(t);
            }
        }
    }

    public void doOnBinary(Channel channel, WebSocketFrame frame) {
        Attribute<String> attrPath = channel.attr(PATH_KEY);
        PojoMethodMapping methodMapping = null;
        if (pathMethodMappingMap.size() == 1) {
            methodMapping = pathMethodMappingMap.values().iterator().next();
        } else {
            String path = attrPath.get();
            methodMapping = pathMethodMappingMap.get(path);
        }
        if (methodMapping.getOnBinary() != null) {
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            Object implement = channel.attr(POJO_KEY).get();
            try {
                methodMapping.getOnBinary().invoke(implement, methodMapping.getOnBinaryArgs(channel, binaryWebSocketFrame));
            } catch (Throwable t) {
                logger.error(t);
            }
        }
    }

    public void doOnEvent(Channel channel, Object evt) {
        Attribute<String> attrPath = channel.attr(PATH_KEY);
        PojoMethodMapping methodMapping = null;
        if (pathMethodMappingMap.size() == 1) {
            methodMapping = pathMethodMappingMap.values().iterator().next();
        } else {
            String path = attrPath.get();
            methodMapping = pathMethodMappingMap.get(path);
        }
        if (methodMapping.getOnEvent() != null) {
            if (!channel.hasAttr(SESSION_KEY)) {
                return;
            }
            Object implement = channel.attr(POJO_KEY).get();
            try {
                methodMapping.getOnEvent().invoke(implement, methodMapping.getOnEventArgs(channel, evt));
            } catch (Throwable t) {
                logger.error(t);
            }
        }
    }

    public String getHost() {
        return config.getHost();
    }

    public int getPort() {
        return config.getPort();
    }

    public Set<WsPathMatcher> getPathMatcherSet() {
        return pathMatchers;
    }

    public void addPathPojoMethodMapping(String path, PojoMethodMapping pojoMethodMapping) {
        pathMethodMappingMap.put(path, pojoMethodMapping);
        for (MethodArgumentResolver onOpenArgResolver : pojoMethodMapping.getOnOpenArgResolvers()) {
            if (onOpenArgResolver instanceof PathVariableMethodArgumentResolver || onOpenArgResolver instanceof PathVariableMapMethodArgumentResolver) {
                pathMatchers.add(new AntPathMatcherWrapper(path));
                return;
            }
        }
        pathMatchers.add(new DefaultPathMatcher(path));
    }

    private PojoMethodMapping getPojoMethodMapping(String path, Channel channel) {
        PojoMethodMapping methodMapping;
        if (pathMethodMappingMap.size() == 1) {
            methodMapping = pathMethodMappingMap.values().iterator().next();
        } else {
            channel.attr(PATH_KEY).set(path);
            //Attribute<String> attrPath = channel.attr(PATH_KEY);
            //attrPath.set(path);
            methodMapping = pathMethodMappingMap.get(path);
            if (methodMapping == null) {
                throw new RuntimeException("path " + path + " is not in pathMethodMappingMap ");
            }
        }
        return methodMapping;
    }
}
