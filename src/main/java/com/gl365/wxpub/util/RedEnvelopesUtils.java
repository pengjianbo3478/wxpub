package com.gl365.wxpub.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gl365.wxpub.common.JsonUtils;

/**
 * 金额随机分配红包算法
 * @author dfs_508 2017年9月5日 下午4:39:05
 */
public class RedEnvelopesUtils {

	private static final Logger LOG = LoggerFactory.getLogger(RedEnvelopesUtils.class);
	/**
	 * 1.总金额不能超过0.01*100 单位是分 
	 * 2.每个红包都要有钱，最低不能低于1分，最大金额不能超过?*100
	 */
	private static int MINMONEY = 100;// 输入金额要大于(最小金额 *个数),否则会报错(产品说最低100元	20170904)
	private static int MAXMONEY = 0;
	
	/**
	 * 这里为了避免某一个红包占用大量资金，我们需要设定非最后一个红包的最大金额，我们把他设置为红包金额平均值的N倍；
	 */
	private static final double TIMES = 2.1;

	/**
	 * 拆分红包
	 * 
	 * @param money
	 *            ：红包总金额
	 * @param count
	 *            ：个数
	 * @return
	 * @throws Exception
	 */
	public static List<BigDecimal> splitRedPackets(BigDecimal totalMoney, int count) throws Exception {
		LOG.info("拆分红包  begin \n totalMoney={},count={}", totalMoney, count);
		Long beginTime = System.currentTimeMillis();
		
		BigDecimal init = new BigDecimal(count);
		if(totalMoney.compareTo(init) == -1){
			throw new Exception("最低金额不可少于"+JsonUtils.toJsonString(init));
		}
		
		// 元转换为 分 BigDecimal 转为 int
		int money = changeInt(totalMoney, count);

		// 不限制最大金额,设为最大值减一分
		MAXMONEY = money - MINMONEY;

		// 红包 合法性校验
		if (!isRight(money, count)) {
			return null;
		}

		// 红包列表
		List<BigDecimal> list = new ArrayList<BigDecimal>();

		// 每个红包最大的金额为平均金额的Times 倍
		int max = (int) (money * TIMES / count);

		max = max > MAXMONEY ? MAXMONEY : max;
		// 分配红包
		for (int i = 0; i < count; i++) {
			int one = randomRedPacket(money, MINMONEY, max, count - i);
			list.add(changeBigDecimal(one));
			money -= one;
		}
		Collections.sort(list);
		//只能有一个小数,其它都为整数
		List<BigDecimal> result = new ArrayList<BigDecimal>();
		BigDecimal otherMoney = BigDecimal.valueOf(0);
		for (int i = 0; i < list.size(); i++) {
			if (i != 1) {
				BigDecimal redEnvelopes = list.get(i).setScale(0, BigDecimal.ROUND_DOWN);
				otherMoney = otherMoney.add(redEnvelopes);
				result.add(redEnvelopes);
			}
		}
		BigDecimal value = totalMoney.subtract(otherMoney);
		result.add(value);
		
		//打乱顺序,用于分配给不同的人
		Collections.shuffle(result);
		Long endTime = System.currentTimeMillis();
		LOG.info("拆分红包  end \n rlt={},time={}ms", JsonUtils.toJsonString(list), (endTime - beginTime));
		return result;
	}

	/**
	 * 元转换为 分 BigDecimal 转为 int
	 * 
	 * @param count
	 * @param count2 
	 * @return
	 * @throws Exception
	 */
	private static int changeInt(BigDecimal count, int num) throws Exception {
		if(count == null || num == 0){
			throw new Exception("输入值不合法");
		}
		
		String totalMoney = JsonUtils.toJsonString(count);
		String returnValue = null;
		String[] str = totalMoney.split("\\.");
		if (str.length < 2) {
			returnValue = str[0] + "00";
		} else {
			if (str[1].length() == 1) {
				returnValue = str[0] + str[1] + "0";
			} else if (str[1].length() == 2) {
				returnValue = str[0] + str[1];
			} else {
				LOG.info("输入金额格式不正确{}", totalMoney);
				throw new Exception("输入金额格式不正确");
			}
		}
		Integer value = Integer.valueOf(returnValue);
		if(value < MINMONEY * num){
			throw new Exception("输入金额太少");
		}
		return Integer.valueOf(returnValue);
	}

	/**
	 * 红包分单位转为元 BigDecimal
	 * 
	 * @param count
	 * @return
	 */
	private static BigDecimal changeBigDecimal(int count) {
		String money = String.valueOf(count);
		String returnValue = null;
		if (money.length() == 1) {
			returnValue = "0.0" + money;
		} else if (money.length() == 2) {
			returnValue = "0." + money;
		} else {
			returnValue = money.substring(0, money.length() - 2) + "."
					+ money.substring(money.length() - 2, money.length());
		}
		if (StringUtils.isNotBlank(returnValue)) {
			return new BigDecimal(returnValue);
		} else {
			LOG.info("红包金额转换错误 param={}", returnValue);
			return new BigDecimal(0);
		}
	}

	/**
	 * 随机分配一个红包
	 * 
	 * @param money
	 * @param minS
	 *            :最小金额
	 * @param maxS
	 *            ：最大金额(每个红包的默认Times倍最大值)
	 * @param count
	 * @return
	 */
	private static int randomRedPacket(int money, int minS, int maxS, int count) {
		// 若是只有一个，直接返回红包
		if (count == 1) {
			return money;
		}
		// 若是最小金额红包 == 最大金额红包， 直接返回最小金额红包
		if (minS == maxS) {
			return minS;
		}
		// 校验 最大值 max 要是比money 金额高的话？ 去 money 金额
		int max = maxS > money ? money : maxS;
		// 随机一个红包 = 随机一个数* (金额-最小)+最小
		int one = ((int) Math.rint(Math.random() * (max - minS) + minS));
		// 剩下的金额
		int moneyOther = money - one;
		// 校验这种随机方案是否可行，不合法的话，就要重新分配方案
		if (isRight(moneyOther, count - 1)) {
			return one;
		} else {
			// 重新分配
			double avg = moneyOther / (count - 1);
			// 本次红包过大，导致下次的红包过小；如果红包过大，下次就随机一个小值到本次红包金额的一个红包
			if (avg < MINMONEY) {
				// 递归调用，修改红包最大金额
				return randomRedPacket(money, minS, one, count);

			} else if (avg > MAXMONEY) {
				// 递归调用，修改红包最小金额
				return randomRedPacket(money, one, maxS, count);
			}
		}
		return one;
	}

	/**
	 * 红包 合法性校验
	 * 
	 * @param money
	 * @param count
	 * @return
	 */
	private static boolean isRight(int money, int count) {
		double avg = money / count;
		// 小于最小金额
		if (avg < MINMONEY) {
			return false;
			// 大于最大金额
		} else if (avg > MAXMONEY) {
			return false;
		}
		return true;
	}
	
	/**
	 * 根据Id分配金额
	 * 
	 * @param money
	 *            ：红包总金额
	 * @param count
	 *            ：个数
	 * @param ids
	 *            ：人数ids
	 * @return
	 * @throws Exception
	 */
	public static Map<String, BigDecimal> distributionById(BigDecimal totalMoney, int count, List<String> ids) throws Exception {
		if(ids == null || count != ids.size() || count < 2){
			throw new Exception("人数不匹配");
		}
		List<BigDecimal> rltList = splitRedPackets(totalMoney, count);
		Map<String, BigDecimal> rlt = new HashMap<>();
		for (int i = 0; i < count; i++) {
			rlt.put(ids.get(i), rltList.get(i));
		}
		return rlt;
	}

	/*public static void main(String[] args) throws Exception {
		BigDecimal totalMoney = new BigDecimal("800.06");
		int count = 2;
		List<String> ids = new ArrayList<>();
		ids.add("0");
		ids.add("1");
		ids.add("2");
		ids.add("3");
		ids.add("4");
		ids.add("5");
		ids.add("6");
		ids.add("7");
		ids.add("8");
		ids.add("9");
		for (int i = 0; i < 200; i++) {
			Map<String, BigDecimal> rlt = distributionById(totalMoney, count, ids);
			System.err.println(JsonUtils.toJsonString(rlt));
		}
		
		// 单位是分 
		for (int i = 0; i < 2000; i++) {
			String value = "800.06";
			int count = 9;
			List<BigDecimal> ss = RedEnvelopesUtils.splitRedPackets(new BigDecimal(value), count);
			Collections.sort(ss);
			BigDecimal aa = BigDecimal.valueOf(0);
			for (BigDecimal integer : ss) {
				aa = aa.add(integer);
			}
			System.err.println(ss + "----->" + aa);
			if (!value.equals(JsonUtils.toJsonString(aa))) {
				System.err.println(aa);
				throw new Exception("错误sssssssssssss");
			}
		}
	}*/
}