import com.im.codec.MyDecoder;
import com.im.codec.MyEncoder;
import com.im.handler.IdleHandler;
import com.im.handler.ServerGroupHandler;
import com.im.handler.ServerSingleHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Echoes back any received data from a client.
 */
public final class IMServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //处理channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IdleHandler());
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            //消息解码
                            p.addLast(new MyEncoder());
                            p.addLast(new MyDecoder(1024, 4, 4, 0, 0));
                            //业务处理
                            p.addLast(new ServerSingleHandler());
                            p.addLast(new ServerGroupHandler());
                        }
                    });
            //绑定端口
            ChannelFuture f = b.bind(8090).sync();
            f.channel().closeFuture().sync();
        } finally {
            //处理完剩余请求再关闭
            workerGroup.shutdownGracefully();
        }
    }
}