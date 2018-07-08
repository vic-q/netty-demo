package com.free.chatroom;

/**
 * @author wangqing
 */
public interface UserAction {

    /**
     * 创建房间
     * @param userId
     */
    void createRoom(int userId);

    /**
     * 加入房间
     * @param userId
     * @param roomId
     */
    void joinRoom(int userId, int roomId);

    /**
     * 退出房间
     * @param userId
     * @param roomId
     */
    void leaveRoom(int userId, int roomId);

    /**
     * 发送消息
     * @param userId
     * @param roomId
     * @param message
     */
    void sendMessage(int userId, int roomId, String message);

}
