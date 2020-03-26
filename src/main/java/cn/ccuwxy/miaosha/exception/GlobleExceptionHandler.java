package cn.ccuwxy.miaosha.exception;

import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobleException) {
            GlobleException ex = (GlobleException) e;
            return Result.error(ex.getCm());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
//            System.out.println(111);
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        } else {
//            System.out.println(222);
            System.out.println(Arrays.toString(e.getStackTrace()));
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
