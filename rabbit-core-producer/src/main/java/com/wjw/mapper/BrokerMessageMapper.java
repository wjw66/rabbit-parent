package com.wjw.mapper;

import com.wjw.entity.BrokerMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/9 14:50
 **/
@Mapper
public interface BrokerMessageMapper {

    int deleteByPrimaryKey(String messageId);

    int insert(BrokerMessage record);

    int insertSelective(BrokerMessage record);

    BrokerMessage selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(BrokerMessage record);

    int updateByPrimaryKeyWithBLOBs(BrokerMessage record);

    int updateByPrimaryKey(BrokerMessage record);

    /**
     * 消息状态变更
     * @param brokerMessageId
     * @param brokerMessageStatus
     * @param updateTime
     */
    void changeBrokerMessageStatus(@Param("brokerMessageId") String brokerMessageId, @Param("brokerMessageStatus")String brokerMessageStatus, @Param("updateTime")Date updateTime);

    List<BrokerMessage> queryBrokerMessageStatus4Timeout(@Param("brokerMessageStatus")String brokerMessageStatus);

    /**
     * 查询broker消息状态
     * @param brokerMessageStatus
     * @return
     */
    List<BrokerMessage> queryBrokerMessageStatus(@Param("brokerMessageStatus")String brokerMessageStatus);

    int update4TryCount(@Param("brokerMessageId")String brokerMessageId, @Param("updateTime") Date updateTime);

}
