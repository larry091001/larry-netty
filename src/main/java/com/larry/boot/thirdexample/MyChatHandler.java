package com.larry.boot.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/6 16:15
 * @version V1.0
 */
public class MyChatHandler extends SimpleChannelInboundHandler<String> {
    //存放所有和服务端和客户端的channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //判断发信息是否为当前的channel对象
        channelGroup.forEach(ch ->{
            if (channel != ch){
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息：[" + msg + "]\n");
            }else{
                ch.writeAndFlush("[自己],发送的消息：[" + msg + "]\n");

            }
        });
    }

    /**
     * 建立了连接，
     * 表示客户端和服务端建立好连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //发送消息（广播）,将指定的消息写到这个channel group组的当中所有的channel
        channelGroup.writeAndFlush("[服务器]-" + channel.remoteAddress() + " 加入\n");
        channelGroup.add(channel);
    }

    /**
     * 连接断开：调用
     * 会自动的从channel group 组中断掉的移除
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //发送消息（广播）
        channelGroup.writeAndFlush("[服务器]-" + channel.remoteAddress() + " 离开\n");
        //移除 netty 会自动调用，无须手动移除
        //channelGroup.remove(channel);
        System.out.println("channel group 总数为：["+channelGroup.size()+"]");
    }

    /**
     * 活动状态：上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线\n");
    }

    /**
     * 不活动状态：下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线\n");
    }

    /**
     * 异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
