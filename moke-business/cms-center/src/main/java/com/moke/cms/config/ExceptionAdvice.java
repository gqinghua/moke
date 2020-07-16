package com.moke.cms.config;

import com.moke.common.exception.DefaultExceptionAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author mall
 * @date 2018/12/22
 */
@ControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {

}
