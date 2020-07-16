package com.moke.member.utils;


import com.moke.common.entity.ums.UmsMember;
import com.moke.member.vo.MemberDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Auther: shenzhuan
 * @Date: 2019/4/5 19:48
 * @Description:
 */
public class UserUtils {
    public static UmsMember getCurrentMember() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
            return memberDetails.getUmsMember();
        }catch (Exception e){
            return new UmsMember();
        }
    }
}
