package model;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public abstract class Bank {
	protected String bankName;
	protected String apiLink;
	protected Map<String, Cost> ccy;
	protected String baseCcy;
	protected Date updateTime;

	@Override
	public String toString() {
		return "Bank [bankName=" + bankName + ", apiLink=" + apiLink + ", currency="
				+ Arrays.toString(this.getCurrency()) + "]";
	}

	public String getBankName() {
		return bankName;
	}

	public String getApiLink() {
		return apiLink;
	}

	public String[] getCurrency() {
		String[] keys = ccy.keySet().toArray(new String[ccy.size() + 1]);
		keys[keys.length - 1] = baseCcy;
		return keys;
	}

	public Cost getCost(String key) {
		return ccy.get(key);
	}

	public abstract void update();

	public String getBaseCcy() {
		return baseCcy;
	}
	public String getUpdateTime() {
		return updateTime.toString()+'\n';
	}

}

class Cost {
	private double buy;
	private double sale;

	public Cost(double buy, double sale) {
		this.buy = buy;
		this.sale = sale;
	}

	public double getBuy() {
		return buy;
	}

	public double getSale() {
		return sale;
	}

	@Override
	public String toString() {
		return  buy + "/" + sale;
	}

}
