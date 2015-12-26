package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class PrivatBank extends Bank {
	private final String ARH_DIR = "src\\model\\privatjson.txt";

	public PrivatBank() {
		bankName = "PrivatBank";
		apiLink = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=5";
		arApiLink = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";
		baseCcy = "UAH";
		statStatus = true;
		update();
	}

	@Override
	public void update() {
		try {
			URLConnection con = new URL(apiLink).openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			int c;
			while ((c = br.read()) != -1)
				sb.append((char) c);
			JSONParser p = new JSONParser();
			Object obj = JSONValue.parse(sb.toString());
			JSONArray arr = (JSONArray) obj;
			FileWriter file = new FileWriter(ARH_DIR);
			file.write(arr.toJSONString());
			file.flush();
			file.close();

			Iterator iter = arr.iterator();
			int i = arr.size() - 1;
			ccy = new HashMap<String, Cost>(i);
			while (i-- > 0 && iter.hasNext()) {
				JSONObject info = (JSONObject) iter.next();
				ccy.put((String) info.get("ccy"), new Cost(Double.parseDouble((String) info.get("buy")),
						Double.parseDouble((String) info.get("sale"))));
			}
			updateTime = new Date();
		} catch (IOException e) {
			JSONParser p = new JSONParser();
			try {
				Object obj = JSONValue.parse(new FileReader(ARH_DIR));
				JSONArray arr = (JSONArray) obj;
				Iterator iter = arr.iterator();
				int i = arr.size() - 1;
				ccy = new HashMap<String, Cost>(i);
				while (i-- > 0 && iter.hasNext()) {
					JSONObject info = (JSONObject) iter.next();
					ccy.put((String) info.get("ccy"), new Cost(Double.parseDouble((String) info.get("buy")),
							Double.parseDouble((String) info.get("sale"))));
				}
				updateTime = new Date(new File("src\\model\\privatjson.txt").lastModified());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			// e.printStackTrace();
		}

	}

	@Override
	public Cost[] getStat(String ex) throws MalformedURLException, IOException {
		String dates[] = sd.getYear();
		Cost ans[] = new Cost[dates.length];
		long a = System.currentTimeMillis();
		for (int i = 0; i < dates.length; i++) {
			URLConnection con = new URL(arApiLink + dates[i]).openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			int c;
			while ((c = br.read()) != -1)
				sb.append((char) c);
			JSONParser p = new JSONParser();
			Object obj = JSONValue.parse(sb.toString());
			JSONObject o = (JSONObject) obj;
			JSONArray arr = (JSONArray) o.get("exchangeRate");
			Iterator iter = arr.iterator();
			while (iter.hasNext()) {
				JSONObject info = (JSONObject) iter.next();
				if (ex.equals((String) info.get("currency"))) {
					try {
						ans[i] = new Cost((double) info.get("purchaseRate"), (double) info.get("saleRate"));
					} catch (NullPointerException npe) {
						continue;
					}
					System.out.printf("%d %s\n", i, ans[i]);
					break;
				}
			}

		}

		long b = System.currentTimeMillis();
		System.out.println(b - a);
		return ans;
	}

//	public static void main(String[] args) throws MalformedURLException, IOException, ClassNotFoundException {
//		PrivatBank pb = new PrivatBank();
//		//Cost arr[] = pb.readStat("statpb2014EUR.txt");
//		pb.writeArray(pb.getStat("CAD"),"statpb2014CAD.txt");
//		//System.out.println(arr[11]);
//	}

}
