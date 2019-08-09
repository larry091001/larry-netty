package com.larry.boot.fiveexample;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/8/7 10:32
 * @version V1.0
 */
public class WebSocketChannelnitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        //以块的方式去写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        /**
         * ChannelHandler 对http message 进行aggregator(聚合)，
         * handler是请求或者是响应，而后面不在有httpclient,
         * 作用是进行把分段的聚合在一块，FullHttpRequest或FullHttpResponse
         */
        pipeline.addLast(new HttpObjectAggregator(8192));
        /**
         * WebSocketServerProtocolHandler 这个处理器帮完成所有一些繁重的工作
         * 使得run websocket server
         * 同时也控制frames的握手
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
