package backend.v1.configuration.exceptionHandle;

import backend.v1.common.JsonResponse;
import backend.v1.tools.JsonResponseUtil;
import backend.v1.tools.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public Object handleCustomException(HttpServletRequest request, HttpServletResponse response,CustomException customException){
        log.error("",customException);
        if (Tools.isAjaxRequest(request)) {
            return new ResponseEntity<>(JsonResponse.error(customException.getMessage(),customException.getCode()), HttpStatus.INTERNAL_SERVER_ERROR);
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
            return new ResponseEntity<>(JsonResponse.error("身份验证异常",4001), HttpStatus.UNAUTHORIZED);
        }else {
            ModelAndView modelAndView = new ModelAndView("customError");
            modelAndView.addObject("message", authenticationException.getMessage());
            return modelAndView;
//            return ResponseEntity.status(HttpStatus.OK).body("customError");
        }
    }


    @ExceptionHandler(value = Exception.class)
    public Object otherHandelException(HttpServletRequest request, HttpServletResponse response,Exception exception){
        log.error("",exception);
        if (Tools.isAjaxRequest(request)) {
            return new ResponseEntity<>(JsonResponse.error(exception.getMessage(),5001), HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            ModelAndView modelAndView = new ModelAndView("somethingError");
            modelAndView.addObject("message", exception.getMessage());
            return modelAndView;
//            return ResponseEntity.status(HttpStatus.OK).body("somethingError");
        }
    }


}
