package backend.v1.configuration.exceptionHandle;

import backend.v1.common.AjaxResultJson;
import backend.v1.tools.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public Object handleCustomException(HttpServletRequest request,CustomException customException){
        log.error("",customException);
        if (Tools.isAjaxRequest(request)) {
            return new ResponseEntity<>(AjaxResultJson.error(customException.getMessage()), HttpStatus.OK);
        } else {
            // 返回错误页面
            ModelAndView modelAndView = new ModelAndView("customError");
            modelAndView.addObject("message", customException.getMessage());
            return modelAndView;
        }
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public Object handleShiroException(HttpServletRequest request,HttpServletResponse response,AuthenticationException authenticationException){
        log.error("",authenticationException);
        if (Tools.isAjaxRequest(request)) {
            return new ResponseEntity<>(AjaxResultJson.error("身份验证异常"), HttpStatus.OK);
        }else {
            ModelAndView modelAndView = new ModelAndView("customError");
            modelAndView.addObject("message", authenticationException.getMessage());
            return modelAndView;
//            return ResponseEntity.status(HttpStatus.OK).body("customError");
        }
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Object handleNotFound(HttpServletRequest request, NoHandlerFoundException noHandlerFoundException){
        log.error("",noHandlerFoundException);
        if (Tools.isAjaxRequest(request)) {
            return new ResponseEntity<>(AjaxResultJson.error("请求url不存在"), HttpStatus.OK);
        }else {
            ModelAndView modelAndView = new ModelAndView("customError");
            modelAndView.addObject("message", noHandlerFoundException.getMessage());
            return modelAndView;
//            return ResponseEntity.status(HttpStatus.OK).body("customError");
        }
    }

    @ExceptionHandler(value = {Exception.class})
    public Object otherHandelException(HttpServletRequest request,Exception exception){
        log.error("",exception);
        if (Tools.isAjaxRequest(request)) {
            return new ResponseEntity<>(AjaxResultJson.error(exception.getMessage()), HttpStatus.OK);
        }else {
            ModelAndView modelAndView = new ModelAndView("somethingError");
            modelAndView.addObject("message", exception.getMessage());
            return modelAndView;
//            return ResponseEntity.status(HttpStatus.OK).body("somethingError");
        }
    }


}
