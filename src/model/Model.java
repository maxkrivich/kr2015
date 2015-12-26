package model;

import java.util.HashMap;
import java.util.Map;

import constants.My—onstants;

public class Model {
	// private Bank[] banks = new Bank[] { new PrivatBank() }; // Banks array
	private Map<String, Bank> banks = new HashMap<String, Bank>() {{
			put("NBU", new NBU());
			put("PrivatBank", new PrivatBank());
        }};
	private String bankUsed = getBanksName()[0]; // what bank use now
	private final String PATTERN = "%.3f";

	public Bank getBank() {
		return banks.get(bankUsed);
	}

	public void setBank(String name) {
		this.bankUsed = name;
	}

	public String getLastUpdate() {
		return String.format("%s %s\n", getBank().updateTime, getBank().getBankName());
	}

	public double getCalculation(double cnts, String from, String to) {
		Bank b = getBank();
		Cost c1 = b.getCost(from), c2 = b.getCost(to);
		if (b.getBaseCcy().equals(from) && !b.getBaseCcy().equals(to)) {
			return Math.floor((cnts / c2.getSale()) * 100) / 100;
		} else if (b.getBaseCcy().equals(from) && b.getBaseCcy().equals(to)) {
			return cnts;
		} else if (b.getBaseCcy().equals(to)) {
			return Math.floor((cnts * c1.getBuy()) * 100) / 100;
		} else if (!b.getBaseCcy().equals(from) && !b.getBaseCcy().equals(to)) {
			return Math.floor(((cnts * c1.getBuy()) / c2.getSale()) * 100) / 100;
		}
		return My—onstants.CALCULATION_ERR_MODEL;
	}

	public String[] getBanksName() {
		return banks.keySet().toArray(new String[0]);
	}

	public String[] getCurrencyAtBank() {
		return getBank().getCurrency();
	}

	public String getCourses() {
		String s[] = getCurrencyAtBank();
		StringBuffer ans = new StringBuffer();
		ans.append(getLastUpdate());
		for (int i = 0; i < s.length - 1; i++)
			ans.append(String.format("%s  %s\n", s[i], getBank().getCost(s[i]).toString()));
		return ans.toString();
	}

	public void updateInBank() {
		getBank().update();
	}

	/*
	 * public static void main(String[] args) { CurrencyModel cu = new
	 * CurrencyModel(); double s = cu.getCalculation(1, "USD", "RUR");
	 * System.out.println(Arrays.toString(cu.getCurrencyAtBank()));
	 * System.out.println(s); }
	 */
}
