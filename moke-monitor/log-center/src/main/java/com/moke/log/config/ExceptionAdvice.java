package com.moke.log.config;

import com.moke.common.exception.DefaultExceptionAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 统一异常处理
 *
 * @author mall
 * @date 2018/12/22
 */
@ControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {
}
