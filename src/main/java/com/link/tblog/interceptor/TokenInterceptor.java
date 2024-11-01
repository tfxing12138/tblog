package com.link.tblog.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.link.tblog.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    // 在请求处理之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
 
        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        log.info("Request URI: {}", request.getRequestURI());

        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        if (token != null){
            boolean result= TokenUtils.verify(token);
            if (result){
                log.info("通过拦截器{}",token);
                return true;
            }
        }
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json=new JSONObject();
            json.put("msg","token verify fail");
            json.put("code","500");
            response.getWriter().append(json.toString());
                log.error("认证失败，未通过拦截器");
        } catch (Exception e) {
            return false;
        }
        /**
         * 还可以在此处检验用户存不存在等操作
         */
        return false;
    }

    // 在请求处理之后执行 是在控制器方法执行后、视图渲染之前调用的，如果处理过程中发生异常，它可能不会执行。
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.info("postHandle");
    }

    // 在请求完全处理后执行 是在整个请求结束后执行的，不管请求是否成功都会调用，主要用于清理资源、记录日志等操作。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        log.info("afterCompletion");
    }
}