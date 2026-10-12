package com.example.DhanaBackEnd.Configuration;

import com.example.DhanaBackEnd.DTO.StaffDto;
import com.example.DhanaBackEnd.Model.Staff;
import lombok.Data;

@Data
public class ResponseGlobal<T> {
    private String Message;
    private boolean Success;
    private T data;
    public static <T> ResponseGlobal<T> onSuccess(String Message, T data) {
            ResponseGlobal<T> response=new ResponseGlobal<>();
            response.setMessage(Message);
            response.setSuccess(true);
            response.setData(data);
            return response;

    }
     public static <T> ResponseGlobal<T> onFailure(String Message) {
        ResponseGlobal<T> failure=new ResponseGlobal<>();
        failure.setMessage(Message);
        failure.setSuccess(false);
        return failure;

    }
    public static <T> ResponseGlobal<T> onError(String Message) {
        ResponseGlobal<T> error=new ResponseGlobal<>();
        error.setMessage(Message);
        error.setSuccess(false);
        return error;

    }

}
