package com.im.codec;

import com.im.handler.MyProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhangyaoliang
 * @since 2020-06-01 11:10
 */
public class MyEncoder extends MessageToByteEncoder<MyProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol, ByteBuf byteBuf)  {
        byteBuf.writeInt(MyProtocol.header);
        byteBuf.writeInt(myProtocol.getContentLength());
        byteBuf.writeBytes(myProtocol.getContent());
    }
}
