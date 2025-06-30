package io.github.blockneko11.grasslogin.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 玩家登录数据。
 */
@Data
@AllArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public final class PlayerAuthData {
    /**
     * 玩家名称。
     */
    private String name;

    /**
     * 玩家密码。
     */
    private String pwd;
}
