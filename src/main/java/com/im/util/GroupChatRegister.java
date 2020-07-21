package com.im.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * 群聊注册
 *
 * @author zhangyaoliang
 * @since 2020-07-20 18:31
 */
public class GroupChatRegister {
    private static Map<String, ChannelGroup> relationshipMap = new HashMap();

    public static ChannelGroup get(String groupId) {
        return relationshipMap.get(groupId);
    }

    public static void set(String groupId, ChannelHandlerContext channelHandlerContext) {
        if (!relationshipMap.containsKey(groupId)) {
            relationshipMap.put(groupId, new DefaultChannelGroup(channelHandlerContext.executor()));
        }
        relationshipMap.get(groupId).add(channelHandlerContext.channel());
    }

    public static void add(String groupId, ChannelHandlerContext channelHandlerContext) {
        relationshipMap.get(groupId).add(channelHandlerContext.channel());
    }

    public static void remove(String groupId) {
        relationshipMap.remove(groupId);
    }

    public static boolean contain(String groupId) {
        return relationshipMap.containsKey(groupId);
    }
}
