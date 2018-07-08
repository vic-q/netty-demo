package com.free.chatroom;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wangqing 
 */
public class UserFactory {

    private static final ConcurrentMap<Integer, User> USERS = new ConcurrentHashMap<>();

    public User getUser(int userId) {
        return USERS.get(userId);
    }

}
