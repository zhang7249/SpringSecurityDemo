package com.example.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * descripiton: 客户端逻辑处理
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 16:50
 * @modifier:
 * @since:
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s)
        throws Exception {

    }
}