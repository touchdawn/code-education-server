
package com.itheima.interceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.controller.utils.ApiResult;
import com.itheima.util.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("开始鉴权");
        ObjectMapper objectMapper = new ObjectMapper();
        //Map map = new HashMap();
        //Result result = new Result();
        //String json = objectMapper.writeValueAsString(map);
        ApiResult apiResult = new ApiResult();
        try{
            JwtUtil.checkToken(token);
            System.out.println("鉴权成功");
            return true;
        } catch (io.jsonwebtoken.SignatureException | MalformedJwtException e) {
            e.printStackTrace();
            apiResult = ApiResult.F("",HttpStatus.UNAUTHORIZED.toString());
            System.out.println("token错误");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("token为空");
            apiResult = ApiResult.F("998","token为空");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("token其他错误");
            apiResult = ApiResult.F("990","token错误");
        }
        String jsonRes = objectMapper.writeValueAsString(apiResult);
        response.getWriter().println(jsonRes);
        return false;
        ///**
        // * {
        // *     'msg': 'xxxx'
        // *     'state': 'fail'
        // *     'code': 200
        // * }
        // */
        //response.getWriter().println(json);
        //
        //System.out.println(65536);

        //    return HandlerInterceptor.super.preHandle(request, response, handler);
        //    return false;


        //if (checkTokenResult){
        //    return HandlerInterceptor.super.preHandle(request, response, handler);
        //
        //}else {
        //    System.out.println("鉴权失败");
        //    return false;
        //}
    }


    //public ApiResult custom (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //    String token = request.getHeader("token");
    //    System.out.println("开始鉴权");
    //    boolean checkTokenResult = JwtUtil.checkToken(token);
    //    ObjectMapper objectMapper = new ObjectMapper();
    //    Map map = new HashMap();
    //    String json = objectMapper.writeValueAsString(map);
    //    System.out.println("112344445556767");
    //    System.out.println(json);
    //
    //    ///**
    //    // * {
    //    // *     'msg': 'xxxx'
    //    // *     'state': 'fail'
    //    // *     'code': 200
    //    // * }
    //    // */
    //    //response.setContentType("application/json;UTF-8");
    //    //response.getWriter().println(json);
    //    //
    //    //System.out.println(65536);
    //    if (checkTokenResult){
    //        return ApiResult.T();
    //
    //    }else {
    //        System.out.println("鉴权失败");
    //        return ApiResult.F("11111","eeeeeee");
    //    }
    //}
}
