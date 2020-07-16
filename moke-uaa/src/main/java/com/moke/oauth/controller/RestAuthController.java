package com.moke.oauth.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatOpenRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: sw
 * @ClassName RestAuthController
 * @description: 第三方登录 ,使用JustAuth 做的
 * @author: GUO
 * @create: 2020-06-02 21:58
 **/
@Api(tags = "OAuth2相关操作")
@Slf4j
@RestController
@RequestMapping("/oauth")
public class RestAuthController {

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.login(callback);
    }

    private AuthRequest getAuthRequest() {
        return new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId("Client ID")
                .clientSecret("Client Secret")
                .redirectUri("https://www.zhyd.me/oauth/callback/wechat")
                .build());
//        return new AuthWeChatRequest(AuthConfig.builder()
//                .clientId("Client ID")
//                .clientSecret("Client Secret")
//                .redirectUri("https://www.zhyd.me/oauth/callback/wechat")
//                .build());
    }
}
