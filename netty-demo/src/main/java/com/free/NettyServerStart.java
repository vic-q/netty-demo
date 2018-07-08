package com.free;

import com.free.netty.NettyConfig;
import com.free.netty.NettyServer;

/**
 * @author wangqing 
 */
public class NettyServerStart {

    public static void main(String[] args) {
        NettyConfig nettyConfig = new NettyConfig();
        nettyConfig.setPort(8080);
        NettyServer server = new NettyServer(nettyConfig);
        server.start();
    }

}
