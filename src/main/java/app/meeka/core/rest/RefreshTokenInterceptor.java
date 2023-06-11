package app.meeka.core.rest;

import app.meeka.application.command.UserHolderCommand;
import app.meeka.core.context.UserHolder;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static app.meeka.core.cache.RedisConstants.LOGIN_USER_KEY;
import static app.meeka.core.cache.RedisConstants.LOGIN_USER_TTL;
import static cn.hutool.core.bean.BeanUtil.fillBeanWithMap;
import static java.util.concurrent.TimeUnit.MINUTES;

@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) return true;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(LOGIN_USER_KEY + token);
        if (userMap.isEmpty()) return true;
        UserHolderCommand userHolderCommand = fillBeanWithMap(userMap, new UserHolderCommand(), false);
        UserHolder.saveUser(userHolderCommand);
        // 刷新token有效期
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, MINUTES);
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserHolder.removeUser();
    }
}
