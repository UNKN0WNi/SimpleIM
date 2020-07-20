import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 维护用户注册关系
 *
 * @author zhangyaoliang
 * @since 2020-07-20 14:45
 */
public class UserRegister {
    private static Map<String, ChannelHandlerContext> relationshipMap = new HashMap();

    public static ChannelHandlerContext get(String userId) {
        return relationshipMap.get(userId);
    }

    public static void set(String userId, ChannelHandlerContext channelHandlerContext) {
        relationshipMap.put(userId, channelHandlerContext);
    }

    public static void remove(String userId) {
        relationshipMap.remove(userId);
    }

    public static boolean contain(String userId) {
        return relationshipMap.containsKey(userId);
    }
}
