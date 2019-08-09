package com.larry.boot.fiveexample;

import com.larry.boot.fourexample.MyFourInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/7 10:23
 */
public class MyFiveServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketChannelnitializer());

            ChannelFuture future = bootstrap.bind(new InetSocketAddress(8899)).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
