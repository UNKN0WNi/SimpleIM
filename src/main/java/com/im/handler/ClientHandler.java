package com.im.handler;

import com.im.constant.CommandConst;
import com.im.util.GsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class ClientHandler extends SimpleChannelInboundHandler<MyProtocol> {


    static String msg="I am echo message";



    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String entityString = GsonUtil.GsonString(new MessageEntity(UUID.randomUUID().toString(), "阿巴", CommandConst.CREATE_GROUP,"group",msg));
        ctx.writeAndFlush(new MyProtocol(entityString.getBytes().length,entityString.getBytes()));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol) throws Exception {
        MessageEntity messageEntity = GsonUtil.GsonToBean(new String(myProtocol.getContent(), "UTF-8"), MessageEntity.class);
        System.out.println(messageEntity.getUserName()+" say: "+messageEntity.getMessage());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws InterruptedException {

        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
