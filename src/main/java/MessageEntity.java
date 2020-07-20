import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * IM消息实体类
 *
 * @author zhangyaoliang
 * @since 2020-07-20 14:21
 */
@Setter
@Getter
@AllArgsConstructor
public class MessageEntity {
    String userId;

    String userName;

    String message;

}
