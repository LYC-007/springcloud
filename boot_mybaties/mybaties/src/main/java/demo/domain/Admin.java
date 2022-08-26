package demo.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: XuFei
 * @Date: 2022/8/10 12:05
 */


@Data
@ToString
public class Admin {
    private int id;
    private String userName;
    private String password;
}
