package com.moke.oauth.service.impl;

import com.moke.common.entity.ums.UmsMember;
import com.moke.common.feign.MemberFeignClient;
import com.moke.common.feign.UserService;
import com.moke.common.model.LoginAppUser;
import com.moke.oauth.service.ZltUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements ZltUserDetailsService, SocialUserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private MemberFeignClient memberFeignClient;


    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginAppUser loginAppUser = userService.findByUsername(username);
        if (loginAppUser != null && loginAppUser.getUserId()!=null) {
            return loginAppUser;
        }
        throw new InternalAuthenticationServiceException("用户名或密码错误");
    }

    @Override
    public SocialUserDetails loadUserByUserId(String openId) {
        UmsMember loginAppUser = memberFeignClient.findByOpenId(openId);
        return loginAppUser;
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        LoginAppUser loginAppUser = userService.findByMobile(mobile);
        if (loginAppUser == null) {
            throw new InternalAuthenticationServiceException("手机号错误或没有注册");
        }
        return checkUser(loginAppUser);
    }

    private LoginAppUser checkUser(LoginAppUser loginAppUser) {
        if (loginAppUser != null && !loginAppUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
        return loginAppUser;
    }

    private UmsMember checkMember(UmsMember loginAppUser) {
        /*if (loginAppUser != null && loginAppUser.getStatus()==2) {
            throw new DisabledException("用户已作废");
        }*/
        return loginAppUser;
    }

    @Override
    public UserDetails loadMemberByUsername(String username) {
        UmsMember loginAppUser = memberFeignClient.findByUsername(username);
        if (loginAppUser == null) {
            throw new InternalAuthenticationServiceException("用户名或密码错误");
        }
        return checkMember(loginAppUser);
    }

    @Override
    public SocialUserDetails loadMemberByOpenId(String openId) {
        UmsMember loginAppUser = memberFeignClient.findByOpenId(openId);
        if (loginAppUser == null) {
            throw new InternalAuthenticationServiceException("用户名或密码错误");
        }
        return checkMember(loginAppUser);

    }

    @Override
    public UserDetails loadMemberByMobile(String mobile) {
        UmsMember loginAppUser = memberFeignClient.findByMobile(mobile);
        if (loginAppUser == null) {
            throw new InternalAuthenticationServiceException("手机号错误或没有注册");
        }
        return checkMember(loginAppUser);
    }
}
