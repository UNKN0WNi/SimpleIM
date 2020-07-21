package com.im.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangyaoliang
 * @since 2020-06-02 11:07
 */
@Getter
@Setter
@AllArgsConstructor
public class MyProtocol {
    /**
     * 消息头标志
     */
    public static int header = 0x76;

    /**
     * 消息长度
     */
    private int contentLength;

    /**
     * 消息内容
     */
    private byte[] content;
}
