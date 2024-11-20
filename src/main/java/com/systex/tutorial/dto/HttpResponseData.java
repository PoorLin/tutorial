package com.systex.tutorial.dto;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpResponseData<T> implements Serializable {
    /**
     * 回傳給前端的自定義狀態碼,與httpstatus不同的地方在於他是通過後端的審核
     * 並回傳自定義的數字，目前也是統一成功號碼為200，沒通過審核的號碼則各自定義
     * 例如：會員的審核開頭都會是1xx，這裏的數字會明確定義在ResponseCodeConstant
     */
    private Integer responseCode;

    /**
     * 回傳給前端的DTO物件，因為我要用這個物件當作每個請求的回傳值(DTO)
     * 所以在才設定為泛型
     */
    private T responseData;

    /**
     * 錯誤訊息
     */

    public String errorMsg;

    public HttpResponseData(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public HttpResponseData(Integer responseCode, T responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }
}
