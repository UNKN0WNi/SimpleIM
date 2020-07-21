package com.im.handler;

import com.im.constant.CommandConst;
import com.im.util.GroupChatRegister;
import com.im.util.GsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 群聊操作处理handler
 *
 * @author zhangyaoliang
 * @since 2020-07-21 10:18
 */
public class ServerGroupHandler extends SimpleChannelInboundHandler<MessageEntity> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageEntity messageEntity) throws Exception {
        if(messageEntity.getCommand().equals(CommandConst.CREATE_GROUP)) {
            GroupChatRegister.set(messageEntity.targetId,channelHandlerContext);
            String entityString = GsonUtil.GsonString(new MessageEntity(UUID.randomUUID().toString(), "阿巴阿巴", CommandConst.SAY_SINGLE, messageEntity.userId,"群组群组"));
            GroupChatRegister.get(messageEntity.targetId).writeAndFlush(new MyProtocol(entityString.getBytes().length, entityString.getBytes()));
        }else {
            String entityString = GsonUtil.GsonString(new MessageEntity(UUID.randomUUID().toString(), "阿巴阿巴", CommandConst.SAY_SINGLE, messageEntity.userId,"群组群组"));
            GroupChatRegister.get(messageEntity.targetId).writeAndFlush(new MyProtocol(entityString.getBytes().length, entityString.getBytes()));
        }
    }


}
