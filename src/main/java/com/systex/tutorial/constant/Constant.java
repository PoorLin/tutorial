package com.systex.tutorial.constant;

public class Constant {
    /**
     *  通過審核
     */
    public final static Integer SUCCESS = 200;

    /**
     *  認證失敗
     */
    public final static Integer JWT_ERROR = 3000;

    /**
     *  登入輸入資料錯誤，像是沒輸入之類
     */
    public final static Integer LOGIN_DATA_ERROR = 3001;

    /**
     *  登入失敗
     *  帳號或密碼錯誤
     */
    public final static Integer LOGIN_ERROR = 3002;

  /**
   *  註冊失敗
   *  已經有此使用者
   */
  public final static Integer REGISTER_ERROR = 3004;

    /**
     *  註冊失敗
     *  確認密碼失敗
     */
    public final static Integer REGISTER_COMFIRMDATAERROR = 3005;

    /**
     *  用戶不存在
     */
    public final static Integer PROFILE_NOTEXIST = 3003;

    /**
     *  未預料到的錯誤
     */
    public static Integer UNKNOWN_ERROR = 9999;

    /**
     *  信箱的正則表達
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

}
