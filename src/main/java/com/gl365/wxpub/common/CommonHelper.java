package com.gl365.wxpub.common;

import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.exception.WxPubException;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;

/**
 * Created by DELL on 2017/10/13.
 * 业务工具类
 */

public class CommonHelper {
    private final static Logger logger = LoggerFactory.getLogger(CommonHelper.class);

    /**
     * 从Session获取UserId
     * @param
     * @return
     */
    public static String getUserIdByRequest(){
        HttpServletRequest request = getRequest();
        Object userId =  request.getSession().getAttribute(Constant.GL_USER_ID);
        if(userId == null || userId.toString().length() == 0 ) {
            logger.error("<======获取用户id为"+userId);
            throw  new WxPubException(ResultCodeEnum.System.ILLEGAL_REQUEST.getCode(),ResultCodeEnum.System.ILLEGAL_REQUEST.getMsg());
        }
        return  userId.toString();
    }

    public static HttpServletRequest getRequest(){
        RequestAttributes ra = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    public static Object getValueFromSession(String key){
        if (key == null) return null;
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session == null) return  null;
        return  session.getAttribute(key);
    }

    public static Object getValueFromCookie(String key){
        if (key == null) return null;
        HttpServletRequest request = getRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals(key)){
                return  cookie.getValue();
            }
        }
        return  null;
    }

    /**
     * 从session获取OpenId
     * @param
     * @return
     */
    public static String getOpenIdByRequest(){
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session != null) {
            Object openId = session.getAttribute(Constant.GL_WXPUB_OPENID);
            return openId == null ? null : (String) openId;
        }
        return null;
    }

    private CommonHelper(){

    }
}
