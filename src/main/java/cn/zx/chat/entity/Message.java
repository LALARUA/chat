package cn.zx.chat.entity;

import com.alibaba.fastjson.JSON;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 13:13 2019/5/21 0021
 */
public class Message {

    public static final String ENTER = "ENTER";
    public static final String SPEAK = "SPEAK";
    public static final String QUIT = "QUIT";

    //消息类型
    private String type;

    //发送人
    private String username;

    //接受者
    private String toUsername;

    //发送消息
    private String msg;

    //发送时间
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public Message(String type, String username, String toUsername, String msg, String date) {
        this.type = type;
        this.username = username;
        this.toUsername = toUsername;
        this.msg = msg;
        this.date = date;

    }

}

