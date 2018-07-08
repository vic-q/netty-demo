package com.free.netty;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @author wangqing
 */
public class NettyClient {

    private final EventLoopGroup worker = new NioEventLoopGroup();

    private final Bootstrap bootstrap = new Bootstrap();

    private final ConcurrentMap<String, ChannelWrapper> channelTables = new ConcurrentHashMap();

    public NettyClient() {

        this.bootstrap.group(worker);
        this.bootstrap.channel(NioSocketChannel.class);
        this.bootstrap.handler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
            @Override
            public void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new StringEncoder())
                        .addLast(new StringDecoder())
                        .addLast(new ClientHandler());
            }
        });
    }

    public void invokeSync(String address, String message) throws InterruptedException, TimeoutException, ExecutionException {
        Channel channel = createAndGetChannel(address);
        channel.writeAndFlush(message).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("invoke success");
                } else {
                    System.out.println("invoke fail");
                }
            }
        });
    }

    private Channel createAndGetChannel(final String address) throws InterruptedException, TimeoutException, ExecutionException {
        ChannelWrapper cw = channelTables.get(address);
        if (cw != null && cw.isOK()) {
            return cw.getChannel();
        } else {
            ChannelFuture channelFuture = this.bootstrap.connect(string2SocketAddress(address));
            channelFuture.get(1000, TimeUnit.MILLISECONDS);
            this.channelTables.put(address, new ChannelWrapper(channelFuture));
            return channelFuture.channel();
        }

    }

    public static SocketAddress string2SocketAddress(final String address) {
        String[] s = address.split(":");
        InetSocketAddress isa = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
        return isa;
    }

}
