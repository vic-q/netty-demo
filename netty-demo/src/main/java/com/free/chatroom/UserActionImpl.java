package com.free.chatroom;

/**
 * @author wangqing 
 */
public class UserActionImpl implements UserAction {

    private RoomFactory roomFactory = new RoomFactory();

    private UserFactory userFactory = new UserFactory();

    @Override
    public void createRoom(int userId) {
        Room room = roomFactory.createRoom();


    }

    @Override
    public void joinRoom(int userId, int roomId) {

    }

    @Override
    public void leaveRoom(int userId, int roomId) {

    }

    @Override
    public void sendMessage(int userId, int roomId, String message) {

    }
}
