package com.gl365.wxpub.util;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.exception.WxPubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL on 2017/10/16.
 */
public class RespUtil {

    private static final Logger logger = LoggerFactory.getLogger(RespUtil.class);

    public static  <T>T getData(ResultDto<T> result){
        if (result == null){
            logger.error("<======服务访问结果为："+result);
            throw new WxPubException(ResultCodeEnum.System.INVOKE_ERROR);
        }
        if(ResultCodeEnum.System.SUCCESS.getCode().equals(result.getResult())){
            T t = result.getData();
            if (t == null){
                logger.warn("<======服务访问结果为："+result);
//                throw new WxPubException(ResultCodeEnum.System.INVOKE_ERROR);
            }
            return t;
        }else{
            throw new WxPubException(result.getResult(),result.getDescription());
        }
    }

    private RespUtil(){}
}
