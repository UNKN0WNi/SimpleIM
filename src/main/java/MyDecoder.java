import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author zhangyaoliang
 * @since 2020-06-02 11:19
 */
public class MyDecoder extends LengthFieldBasedFrameDecoder {


    public MyDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //在这里调用父类的方法,实现指得到想要的部分,我在这里全部都要,也可以只要body部分
        in = (ByteBuf) super.decode(ctx, in);

        if (in == null) {
            return null;
        }


        int header = in.readInt();
        if (header != MyProtocol.header) {
            throw new Exception("无法识别协议");
        }
        //读取length字段
        int length = in.readInt();

        if (in.readableBytes() != length) {
            throw new Exception("标记的长度不符合实际长度");
        }
        //读取body
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        return new MyProtocol(length, bytes);

    }
}
