package com.gl365.wxpub.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountTransferUtils {
	
	private static final BigDecimal exchangeRate = new BigDecimal(100);
	
	private static final BigDecimal _0 = new BigDecimal(0);
	
	public static BigDecimal fen2yuan(BigDecimal fen){
		if(fen == null){
			return null;
		}
		if(_0.equals(fen)){
			return _0;
		}
		return fen.divide(exchangeRate).setScale(2);
	}
	
	public static BigDecimal yuan2fen(BigDecimal yuan){
		if(yuan == null){
			return null;
		}
		return new BigDecimal(yuan.multiply(exchangeRate).intValue());
	}
	
	/**
	 * 四舍五入保留2位小数
	 * @param arg
	 * @return BigDecimal
	 */
	public static BigDecimal formatComma2BigDecimal(BigDecimal arg) {  
        if (arg == null)  
            return new BigDecimal("0.00");  
        BigDecimal decimal = arg.setScale(2, RoundingMode.HALF_UP);  
        return decimal;  
  
    }
	
	public static BigDecimal add(BigDecimal arg, BigDecimal arg1){
		BigDecimal result = null;
		if(arg == null){
			arg = new BigDecimal("0.00");
		}
		if(arg1 == null){
			arg1 = new BigDecimal("0.00");
		}
		
		result = arg.add(arg1);
		return formatComma2BigDecimal(result);
	}
	
	/**
	 * 减法
	 * @param arg
	 * @param arg1
	 * @return arg - arg1
	 */
	public static BigDecimal subtract(BigDecimal arg, BigDecimal arg1){
		BigDecimal result = null;
		if(arg == null){
			arg = new BigDecimal("0.00");
		}
		if(arg1 == null){
			arg1 = new BigDecimal("0.00");
		}
		
		result = arg.subtract(arg1);
		return formatComma2BigDecimal(result);
	}
	
	/**
	 * 比较大小
	 * @param arg
	 * @param arg1
	 * @return -1:表示arg小于arg1</br>
	 *			0:表示arg等于arg1</br>
	 *			1:表示arg大于arg1
	 */
	public static int compareTo(BigDecimal arg,BigDecimal arg1){
		if(arg == null){
			arg = new BigDecimal("0");
		}
		if(arg1 == null){
			arg1 = new BigDecimal("0");
		}
		return arg.compareTo(arg1);
	}
}
