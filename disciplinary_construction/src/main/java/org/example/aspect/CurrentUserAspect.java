package org.example.aspect;

import org.example.annotation.CurrentUser;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当前用户信息参数解析器
 * 自动从JWT Token中解析用户信息并注入到方法参数中
 */
@Component
public class CurrentUserAspect implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(CurrentUserAspect.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        
        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        
        // 获取Authorization头
        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        
        log.debug("CurrentUserAspect处理请求: {}, Authorization头: {}", requestURI, authHeader != null ? "存在" : "不存在");
        
        // 如果注解标记为必需，但缺少Authorization头
        if (currentUserAnnotation.required() && (authHeader == null || !authHeader.startsWith("Bearer "))) {
            log.warn("缺少有效的Authorization头，请求路径: {}", requestURI);
            throw new RuntimeException("缺少有效的Authorization头");
        }
        
        // 如果没有Authorization头，返回null（用于非必需的情况）
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("没有Authorization头，返回null，请求路径: {}", requestURI);
            return null;
        }
        
        String token = authHeader.substring(7);
        
        // 验证Token
        if (!jwtUtil.validateToken(token)) {
            log.warn("Token无效或已过期，请求路径: {}", requestURI);
            if (currentUserAnnotation.required()) {
                throw new RuntimeException("Token无效或已过期");
            } else {
                return null;
            }
        }
        
        // 从Token中获取用户信息
        String userId = jwtUtil.getUserIdFromToken(token);
        String userType = jwtUtil.getUserTypeFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);
        
        // 创建用户信息Map
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("userType", userType);
        userInfo.put("username", username);
        
        log.debug("CurrentUserAspect成功解析用户信息，用户ID: {}, 用户类型: {}, 请求路径: {}", userId, userType, requestURI);
        
        return userInfo;
    }
} 