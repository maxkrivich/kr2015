package model;

public class Model {
	private Bank[] banks = new Bank[] { new PrivatBank() }; // Banks array
	private int pt = 0; // what bank use now
	private final String PATTERN = "%.3f";

	public Bank getBank() {
		return banks[pt];
	}

	public void setPt(int pt) {
		this.pt = pt;
	}

	public String getLastUpdate() {
		return String.format("%s %s\n", getBank().updateTime, getBank().getBankName());
	}

	public double getCalculation(double cnts, String from, String to) {
		Cost c1 = banks[pt].getCost(from), c2 = banks[pt].getCost(to);
		if (banks[pt].getBaseCcy().equals(from) && !banks[pt].getBaseCcy().equals(to)) {
			return Math.floor((cnts / c2.getSale()) * 100) / 100;
		} else if (banks[pt].getBaseCcy().equals(from) && banks[pt].getBaseCcy().equals(to)) {
			return cnts;
		} else if (banks[pt].getBaseCcy().equals(to)) {
			return Math.floor((cnts * c1.getBuy()) * 100) / 100;
		} else if (!banks[pt].getBaseCcy().equals(from) && !banks[pt].getBaseCcy().equals(to)) {
			return Math.floor(((cnts * c1.getBuy()) / c2.getSale()) * 100) / 100;
		}
		return Double.NEGATIVE_INFINITY;
	}

	public String[] getBanksName() {
		String[] arr = new String[banks.length];
		for (int i = 0; i < arr.length; i++)
			arr[i] = banks[i].getBankName();
		return arr;
	}

	public String[] getCurrencyAtBank() {
		return getBank().getCurrency();
	}

	public String getCourses() {
		String s[] = getCurrencyAtBank();
		StringBuffer ans = new StringBuffer();
		ans.append(getLastUpdate());
		for (int i = 0; i < s.length - 1; i++)
			ans.append(s[i] + "  " + getBank().getCost(s[i]).toString() + '\n');
		return ans.toString();
	}

	// public static void main(String[] args) {
	// CurrencyModel cu = new CurrencyModel();
	// double s = cu.getCalculation(1, "USD", "RUR");
	// System.out.println(Arrays.toString(cu.getCurrencyAtBank()));
	// System.out.println(s);
	// }
}
