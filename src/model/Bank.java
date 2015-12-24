package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public abstract class Bank {
	protected String bankName;
	protected String apiLink;
	protected String arApiLink;
	protected Map<String, Cost> ccy;
	protected String baseCcy;
	protected Date updateTime;
	protected ShortDate sd = new ShortDate();
	protected boolean statStatus;

	public boolean getStatStatus() {
		return statStatus;
	}

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
		return updateTime.toString() + '\n';
	}

	public abstract Cost[] getStat(String ex) throws MalformedURLException, IOException;

	public void writeArray(Cost[] arr, String dir) throws FileNotFoundException, IOException {
		if (statStatus) {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dir));
			for (int i = 0; i < arr.length; i++)
				out.writeObject(arr[i]);
			out.flush();
			out.close();
		}
	}

	public Cost[] readStat(String dir) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(dir));
		Cost arr[] = new Cost[365];
		for (int i = 0; i < arr.length; i++)
			arr[i] = (Cost) in.readObject();
		return arr;

	}
}
