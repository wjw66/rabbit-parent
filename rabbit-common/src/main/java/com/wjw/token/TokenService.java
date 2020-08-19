package com.wjw.token;

import com.wjw.exception.IdempotentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author : wjwjava01@163.com
 * @date : 21:32 2020/8/19
 * @description :
 */
@Service
public class TokenService {
    @Resource
    private RedisService redisService;

    /**
     * 创建token
     * @return
     */
    public String createToken() {
        String token = UUID.randomUUID().toString();
        boolean b = redisService.setExpire(token, token, 1000L);
        if (!b){
            return "出现未知错误！";
        }
        return token;
    }

    /**
     * 检查token合法性,检查通过删除token
     * @param request
     * @return
     * @throws IdempotentException
     */
    public boolean checkToken(HttpServletRequest request) throws IdempotentException {
        String token;
        token = request.getHeader("token");
        //前端token参数校验
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
            if (StringUtils.isBlank(token)) {
                throw new IdempotentException("token不存在");
            }
        }
        boolean exists = redisService.exists(token);
        //redis中不存在
        if (!exists) {
            throw new IdempotentException("请求重复");
        }
        boolean delete = redisService.delete(token);
        //redis删除失败
        if (!delete) {
            throw new IdempotentException("请求重复");
        }
        return true;
    }
}
