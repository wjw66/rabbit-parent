package com.wjw.entity;

import com.wjw.api.Message;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * $BrokerMessage 消息记录表实体映射
 *
 * @author codespark
 */
@Data
public class BrokerMessage implements Serializable {

    private static final long serialVersionUID = 7447792462810110841L;

    private String messageId;

    private Message message;

    private Integer tryCount = 0;

    private String status;

    private Date nextRetry;

    private Date createTime;

    private Date updateTime;
}
