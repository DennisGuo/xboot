package cn.ghx.xboot.config;

import cn.ghx.xboot.common.Result;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"cn.ghx.xboot"})
public class ExceptionConfig {

    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<String> handle(IllegalArgumentException e){
        e.printStackTrace();
        return Result.fail(e.getMessage(),Result.CODE_VALID_ERROR);
    }
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<String> handle(Exception e){
        e.printStackTrace();
        return Result.fail(e.getMessage(),Result.CODE_FAIL);
    }
    @ResponseBody
    @ExceptionHandler(value = ClientAbortException.class)
    public void handle(ClientAbortException e){
        // 客户端端口异常，
    }

}
