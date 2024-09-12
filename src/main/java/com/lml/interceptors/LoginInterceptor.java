package com.lml.interceptors;

import com.lml.utils.JwtUtil;
import com.lml.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        try {
            //验证redis中是否有对应的令牌
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            if (ops.get(token)==null){
                throw new RuntimeException("redis缓存找不到对应令牌");
            }
            //如果能够成功解析令牌，则放行
            Map<String, Object> claims = JwtUtil.parseToken(token);//返回值是<Map>claims
            //将claims放入ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放ThreadLocal资源
        ThreadLocalUtil.remove();
    }
}
