package app.meeka.application.result;

import java.util.List;

public record Result(
        Boolean success,
        String errorMsg,
        Object data,
        Long total
) {

    public static Result Success(){
        return new Result(true, null, null, null);
    }
    public static Result Success(Object data){
        return new Result(true, null, data, null);
    }
    public static Result Success(List<?> data, Long total){
        return new Result(true, null, data, total);
    }
    public static Result defeat(String errorMsg){
        return new Result(false, errorMsg, null, null);
    }
}
