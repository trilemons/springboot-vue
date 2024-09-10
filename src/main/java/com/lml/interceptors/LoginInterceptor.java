package com.lml.interceptors;

import com.lml.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        try {
            //如果能够成功解析令牌，则放行
            JwtUtil.parseToken(token);//返回值是<Map>claims
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
