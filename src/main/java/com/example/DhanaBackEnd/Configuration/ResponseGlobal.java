package com.example.DhanaBackEnd.Configuration;

import com.example.DhanaBackEnd.DTO.StaffDto;
import com.example.DhanaBackEnd.Model.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseGlobal<T> {
    private String message;
    private boolean success;
    private T data;
    public static <T> ResponseGlobal<T> onSuccess(String message, T data){
        ResponseGlobal<T> response=new ResponseGlobal<>();
        response.setMessage(message);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
    public static <T> ResponseGlobal<T> onFailure(String message){
        ResponseGlobal<T> failure=new ResponseGlobal<>();
        failure.setMessage(message);
        failure.setSuccess(false);
        return failure;
    }
    public static <T> ResponseGlobal<T> onError(String message){
        ResponseGlobal<T> error=new ResponseGlobal<>();
        error.setMessage(message);
        error.setSuccess(false);
        return error;
    }

}


