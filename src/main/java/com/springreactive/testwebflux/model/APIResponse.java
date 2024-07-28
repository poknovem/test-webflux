package com.springreactive.testwebflux.model;

import com.springreactive.testwebflux.constants.ApiCode;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class APIResponse<T> {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private String code;
    private String message;
    private Integer pageNo;
    private Integer totalPage;
    private Boolean last;
    private T result;

    public static <T> APIResponse<List<T>> success(Integer pageNo, Page<T> page) {
        return success(pageNo, page.getContent(), page.isLast(), page.getTotalPages());
    }

    public static <T> APIResponse<List<T>> success(Integer pageNo, List<T> result, Boolean isLast, Integer totalPage) {
        APIResponse<List<T>> response = new APIResponse<>();
        response.setCode(ApiCode.SUCCESS200);
        response.setMessage(SUCCESS);
        response.result = result;
        response.last = isLast;
        response.pageNo = pageNo == null ? 0 : pageNo;
        response.totalPage = totalPage;
        return response;
    }

    public static <T> APIResponse<T> success(T result) {
        return success(ApiCode.SUCCESS200, result);
    }

    public static <T> APIResponse<T> success(String code, T result) {
        return response(code, SUCCESS, result);
    }

    public static <T> APIResponse<T> response(String code, String message) {
        return response(code, message, null);
    }

    public static <T> APIResponse<T> response(String code, String message, T result) {
        APIResponse<T> response = new APIResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.result = result;
        return response;
    }

    public static <T> APIResponse<T> successBasicAuth() {
        return response(ApiCode.SUCCESS201, SUCCESS, null);
    }

    public static <T> APIResponse<T> fail(String userMessage) {
        APIResponse<T> response = new APIResponse<>();
        response.setCode(ApiCode.INTERNAL_ERROR);
        response.setMessage(userMessage == null || userMessage.isEmpty() ? FAIL : userMessage);
        return response;
    }

    public static <T> APIResponse<T> defaultz() {
        return success(ApiCode.SUCCESS200, null);
    }
}
