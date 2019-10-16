package cn.zx.chat.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 聊天服务端
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */

@Component
@ServerEndpoint("/chatServer/{username}")
public class ChatServer {

    private String username;

    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<String, Session>();


    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.username = username;
        System.out.println(username+session.getId()+"建立连接");
        onlineSessions.put(username,session);
    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        Message message = JSON.parseObject(jsonStr, Message.class);
        String msg = message.getMsg();
        sendMessageToAll(username,msg);
    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println(username+session.getId()+"断开连接");
        onlineSessions.remove(username);

    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 公共方法：发送信息给所有人
     */
    private static void sendMessageToAll(String username,String msg) {
        onlineSessions.forEach((id, session) -> {
            try {
                if (!id.equals(username)){
                    session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private static void sendMessage(String toUsername,String msg) {
        try {
            onlineSessions.get(toUsername).getBasicRemote().sendText(msg);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
