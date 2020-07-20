import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class IMClientHandler extends SimpleChannelInboundHandler<MyProtocol> {


    static String msg="I am echo message";



    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String entityString = GsonUtil.GsonString(new MessageEntity(UUID.randomUUID().toString(), "阿巴", msg));
        ctx.writeAndFlush(new MyProtocol(entityString.getBytes().length,entityString.getBytes()));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol) throws Exception {
        System.out.println(myProtocol.getContent());
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
