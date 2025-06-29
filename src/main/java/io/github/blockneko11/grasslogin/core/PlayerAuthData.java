package io.github.blockneko11.grasslogin.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public final class PlayerAuthData {
    private String name;
    private String pwd;
    private Date lastLogin;
}
