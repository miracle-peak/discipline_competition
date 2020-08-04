package com.gxuwz.subject.common.util;

//import io.swagger.annotations.ApiOperation;
import com.gxuwz.subject.common.constant.StatusCode;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 蔡奇峰
 * date: 2020/3/25 10:59
 * @Version V1.0
 */
@Setter
@Getter
public class R {

    private int code;
    private boolean success;
    private String message;
    private Map<String, Object> data = new HashMap<>();

//    @ApiOperation("操作成功")
    public static R ok(){
        R r = new R();
        r.setMessage("操作成功");
        r.setCode(StatusCode.SUCCESS);
        r.setSuccess(true);

        return r;
    }

//    @ApiOperation("操作失败")
    public static R error(){
        R r = new R();
        r.setMessage("操作失败");
        r.setCode(StatusCode.ERROR);
        r.setSuccess(false);

        return r;
    }



    // 链式编程
    public R success(Boolean success){
        this.setSuccess(success);

        return this;
    }

    public R message(String msg){
        this.setMessage(msg);

        return this;
    }

    public R code(int code){
        this.setCode(code);

        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);

        return this;
    }

    public R data(Map<String, Object> data){
        this.setData(data);

        return this;
    }

    public R() {
    }

    // 全局异常使用
    public R(int code, String msg){
        this.setCode(code);

        this.setMessage(msg);
    }


}
