package com.wjw.service;

import com.wjw.constant.BrokerMessageStatus;
import com.wjw.entity.BrokerMessage;
import com.wjw.mapper.BrokerMessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/9 16:15
 **/
@Service
public class MessageStoreServiceImpl {
    @Resource
    private BrokerMessageMapper brokerMessageMapper;

    public int insert(BrokerMessage brokerMessage) {
        return brokerMessageMapper.insert(brokerMessage);
    }

    public void success(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_OK.getCode(), new Date());
    }

    public void failure(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL.getCode(), new Date());
    }

}
