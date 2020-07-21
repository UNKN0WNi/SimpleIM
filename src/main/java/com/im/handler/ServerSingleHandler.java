package com.im.handler;

import com.im.constant.CommandConst;
import com.im.util.GsonUtil;
import com.im.util.UserRegister;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class ServerSingleHandler extends SimpleChannelInboundHandler<MyProtocol> {
    String userId;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol) throws Exception {
        MessageEntity messageEntity = GsonUtil.GsonToBean(new String(myProtocol.getContent(), "UTF-8"), MessageEntity.class);
        if(messageEntity.getCommand().equals(CommandConst.SAY_SINGLE)) {
            if (!UserRegister.contain(messageEntity.userId)) {
                UserRegister.set(messageEntity.userId, channelHandlerContext);
                userId = messageEntity.userId;
            }
            String entityString = GsonUtil.GsonString(new MessageEntity(UUID.randomUUID().toString(), "阿巴阿巴", CommandConst.SAY_SINGLE, messageEntity.userId,"msg"));
            UserRegister.get(messageEntity.userId).writeAndFlush(new MyProtocol(entityString.getBytes().length, entityString.getBytes()));
        }else {
            channelHandlerContext.fireChannelRead(messageEntity);
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        UserRegister.remove(userId);
        super.channelInactive(ctx);
    }
}
