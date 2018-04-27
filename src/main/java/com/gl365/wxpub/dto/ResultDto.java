package com.gl365.wxpub.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.util.GsonUtils;

/**
 * 返回对象
 * @author dfs_519
 *2017年9月19日下午2:43:18
 * @param <T>
 */
public class ResultDto<T> implements Serializable {

	private static final long serialVersionUID = -8473119110299648593L;

	/**
	 * result : 响应码
	 */
	private String result;

	/**
	 * description ： 响应描述
	 */
	private String description;

	/**
	 * data : 结果数据
	 */
	private T data;

	public ResultDto() {

	}

	public ResultDto(T data) {
		this.data = data;
	}

	public ResultDto(String result, String msg, T data) {
		this.result = result;
		this.description = msg;
		this.data = data;
	}
	
	public ResultDto(ResultCodeEnum.System source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}
	
	public ResultDto(ResultCodeEnum.System source) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = null;
	}

	public ResultDto(ResultCodeEnum.Comment source) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = null;
	}
	
	public ResultDto(ResultCodeEnum.Payment source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}
	
	public ResultDto(ResultCodeEnum.Payment source) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = null;
	}
	
	public ResultDto(ResultCodeEnum.GroupPay source) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = null;
	}
	
	public ResultDto(ResultCodeEnum.Merchant source) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = null;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
	
	public static ResultDto<?> result(ResultCodeEnum.GroupPay source) {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultCodeEnum.Payment source) {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultCodeEnum.System source) {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultCodeEnum.System source,Object data) {
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultCodeEnum.Lottery source) {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultCodeEnum.Lottery source,Object data) {
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}
	
	public static ResultDto<?> result(ResultDto<?> source, Object data) {
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getResult());
		result.setDescription(source.getDescription());
		return result;
	}

	public static <T>ResultDto<T> success(T data){
		ResultDto<T> result = new ResultDto<>(data);
		result.setResult(ResultCodeEnum.System.SUCCESS.getCode());
		result.setDescription(ResultCodeEnum.System.SUCCESS.getMsg());
		return result;
	}
	
	public ResultDto(ResultCodeEnum.User source) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = null;
	}
	
	public static ResultDto<?> errorResult() {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
		result.setDescription(ResultCodeEnum.System.SYSTEM_ERROR.getMsg());
		return result;
	}
	
	public static ResultDto<?> wxPayRltConvert(String rltCode, String rltMsg){
		ResultDto<?> resp = null;
		if(StringUtils.isNotBlank(rltCode)){
			switch (rltCode) {
			// 打赏用户与被打赏用户一致
			case "000052":
				//打赏用户与被打赏用户一致
				resp = new ResultDto<>(ResultCodeEnum.Payment.CANNOT_REWARD_TO_SELF);
				break;
			case "002000":
				//该商家在给乐平台尚未开通微信支付
				resp = new ResultDto<>(ResultCodeEnum.Payment.MERCHANT_NOT_OPEN_WXPAY);
				break;
			case "008024":
				//账户乐豆余额不足
				resp = new ResultDto<>(ResultCodeEnum.Payment.INSUFFICIENT_BALANCE);
				break;
			case "A10027":
				//账户乐豆余额不足
				resp = new ResultDto<>(ResultCodeEnum.Payment.INSUFFICIENT_BALANCE);
				break;
			case "A10024":
				//打赏支付时员工账户不存在
				resp = new ResultDto<>(ResultCodeEnum.Payment.KEFU_ERROR);
				resp.setDescription(Constant.KEFU_MSG_1);
				break;
			case "A10023":
				//转出账户不存在
				resp = new ResultDto<>(ResultCodeEnum.Payment.KEFU_ERROR);
				resp.setDescription(Constant.KEFU_MSG_2);
				break;
			case "A10025":
				//转出账户已失效
				resp = new ResultDto<>(ResultCodeEnum.Payment.KEFU_ERROR);
				resp.setDescription(Constant.KEFU_MSG_3);
				break;
			case "A10026":
				//转入账户已失效
				resp = new ResultDto<>(ResultCodeEnum.Payment.KEFU_ERROR);
				resp.setDescription(Constant.KEFU_MSG_4);
				break;
			case "008001":
				//用户状态不正常
				resp = new ResultDto<>(ResultCodeEnum.Payment.KEFU_ERROR);
				resp.setDescription(Constant.KEFU_MSG_5);
				break;
			default:
				break;
			}
		}
		return resp;
	}
	
	public static ResultDto<?> drawLotteryRltConvert(ResultDto<?> rlt){
		if(null == rlt || StringUtils.isBlank(rlt.getResult())){
			return ResultDto.result(ResultCodeEnum.Lottery.DRAW_PRIZE_FAIL);
		}else{
			switch (rlt.getResult()) {
			case "100010":
				return ResultDto.result(ResultCodeEnum.Lottery.HAS_DRAW_PRIZE);
			case "100011":
				return ResultDto.result(ResultCodeEnum.Lottery.DRAW_PRIZE_FAIL);
			default:
				return ResultDto.result(ResultCodeEnum.Lottery.DRAW_PRIZE_FAIL);
			}
		}
		
	}

}
