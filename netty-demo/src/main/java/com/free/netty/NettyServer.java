package com.free.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author wangqing
 */
public class NettyServer {

    private ServerBootstrap serverBootstrap;

    private EventLoopGroup eventLoopGroupSelector;

    private EventLoopGroup eventLoopGroupBoss;

    private int port;

    private ChannelWrapper channelWrapper;

    public NettyServer(NettyConfig nettyConfig) {

        this.port = nettyConfig.getPort();

        this.serverBootstrap = new ServerBootstrap();

        this.eventLoopGroupBoss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyBoss_%d", this.threadIndex.incrementAndGet()));
            }
        });

        this.eventLoopGroupSelector = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);
            private int threadTotal = 2;

            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyServerNIOSelector_%d_%d", threadTotal, this.threadIndex.incrementAndGet()));
            }
        });
    }

    public void start() {

        this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupSelector)
                .channel(NioServerSocketChannel.class)
                .handler(new ClientChannelInitializer())
                .localAddress(new InetSocketAddress(this.port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringEncoder())
                                .addLast(new FixedLengthFrameDecoder(3))
                                .addLast(new ServerHandler());
                    }
                });

        try {
            ChannelFuture channelFuture = this.serverBootstrap.bind("192.168.1.5", 8080).sync();
            this.channelWrapper = new ChannelWrapper(channelFuture);
        } catch (InterruptedException e) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e);
        }
    }

    public Channel getChannel() {
        if (channelWrapper.isOK()) {
            return channelWrapper.getChannel();
        }
        return null;
    }

}
