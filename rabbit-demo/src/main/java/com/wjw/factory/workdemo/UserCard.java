package com.wjw.factory.workdemo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/8/27 9:48
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserCard extends User{
    private String idCard;
}
