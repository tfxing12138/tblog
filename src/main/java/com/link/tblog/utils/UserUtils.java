package com.link.tblog.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;

public class UserUtils {

    // 正则表达式：只允许字母、数字、下划线，且长度在4到20之间
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{4,20}$";

    // 正则表达式：至少包含一个大写字母、一个小写字母、一个数字，且长度在8到20之间
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$";

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static void validUserName(String username) {
        if (!username.matches(USERNAME_PATTERN)) {
            throw new RuntimeException("非法的用户名称，只允许字母、数字、下划线，且长度在4到20之间");
        }
    }

    public static void validPassWord(String password) {
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new RuntimeException("非法的用户密码，至少包含一个大写字母、一个小写字母、一个数字，且长度在8到20之间");
        }
    }

    public static void validEmail(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new RuntimeException("非法的邮箱");
        }
    }

    /**
     * 密码加盐加密
     * @param t
     * @param <T>
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static <T> void saltHandle(T t) throws Exception {
        Class<?> clazz = t.getClass();
        Field passWordField = clazz.getDeclaredField("passWord");
        passWordField.setAccessible(true);

        Object value = passWordField.get(t);
        if(null == value) {
            throw new RuntimeException("参数异常，请输入密码");
        }

        String salt = UuidUtils.generateId();

        String password = AESUtil.encrypt(salt, String.valueOf(value));
        passWordField.set(t,password);

        Field saltField = clazz.getDeclaredField("salt");
        saltField.setAccessible(true);
        saltField.set(t,salt);

    }
}
