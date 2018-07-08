package com.free.chatroom;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author wangqing 
 */
public class Room {

    private String id;

    private String name;

    private List<User> users;

    private Channel channel;

    public static Room valueOf(String id, String name, Channel channel) {
        Room room = new Room();
        room.id = id;
        room.name = name;
        room.channel = channel;
        room.users = new ArrayList<>();
        return room;
    }


}
