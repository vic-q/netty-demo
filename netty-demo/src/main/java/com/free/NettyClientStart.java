package com.free;

import com.free.netty.NettyClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author wangqing
 */
public class NettyClientStart {

    private static final String ADDRESS = "192.168.1.5:8080";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException, ExecutionException {
        NettyClient nettyClient = new NettyClient();
        nettyClient.invokeSync(ADDRESS, "hello netty");
    }

}
