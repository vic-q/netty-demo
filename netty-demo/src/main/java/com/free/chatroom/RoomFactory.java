package com.free.chatroom;


import com.free.netty.NettyConfig;
import com.free.netty.NettyServer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wangqing
 */
public class RoomFactory {

    private static final ConcurrentMap<String, Room> ROOMS = new ConcurrentHashMap<>();

    public Room createRoom() {

        NettyConfig nettyConfig = new NettyConfig();
        nettyConfig.setPort(8080);

        NettyServer nettyServer = new NettyServer(nettyConfig);
        nettyServer.start();

        String id = getId();

        Room room = Room.valueOf(id, getName(), nettyServer.getChannel());
        ROOMS.put(id, room);
        return room;
    }


    private static String getId() {
        return UUID.randomUUID().toString();
    }

    private static String getName() {
        return "test room";
    }

}
