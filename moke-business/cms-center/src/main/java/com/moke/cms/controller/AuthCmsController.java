package com.moke.cms.controller;


import com.moke.cms.service.ICmsSubjectCategoryService;
import com.moke.cms.service.ICmsSubjectCommentService;
import com.moke.cms.service.ICmsSubjectService;
import com.moke.cms.service.ICmsTopicService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther:
 * @Date: 2019/4/2 15:02
 * @Description:
 */
@RestController
@Api(tags = "AuthCmsController", description = "内容关系管理")
@RequestMapping("/auth")
public class AuthCmsController {



    @Resource
    private ICmsSubjectCategoryService subjectCategoryService;
    @Resource
    private ICmsSubjectService subjectService;
    @Resource
    private ICmsSubjectCommentService commentService;
    @Resource
    private ICmsTopicService topicService;

}
