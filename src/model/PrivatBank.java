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
import static java.lang.Double.parseDouble;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class PrivatBank extends Bank {
	private final String ARH_DIR = "files/privatjson.txt";

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
			String fromStream = readFromUrl(apiLink);
			JSONParser p = new JSONParser();
			Object obj = JSONValue.parse(fromStream);
			JSONArray arr = (JSONArray) obj;
			FileWriter file = new FileWriter(ARH_DIR);
			file.write(arr.toJSONString());
			file.flush();
			file.close();
			Iterator<JSONObject> iter = arr.iterator();
			int i = arr.size() - 1;
			ccy = new HashMap<String, Cost>(i);
			while (i-- > 0 && iter.hasNext()) {
				JSONObject info = iter.next();
				ccy.put((String) info.get("ccy"),
						new Cost(parseDouble((String) info.get("buy")), parseDouble((String) info.get("sale"))));
			}
			updateTime = new Date();
		} catch (IOException e) {
			JSONParser p = new JSONParser();
			try {
				Object obj = JSONValue.parse(new FileReader(ARH_DIR));
				JSONArray arr = (JSONArray) obj;
				Iterator<JSONObject> iter = arr.iterator();
				int i = arr.size() - 1;
				ccy = new HashMap<String, Cost>(i);
				while (i-- > 0 && iter.hasNext()) {
					JSONObject info = iter.next();
					ccy.put((String) info.get("ccy"),
							new Cost(parseDouble((String) info.get("buy")), parseDouble((String) info.get("sale"))));
				}
				updateTime = new Date(new File(ARH_DIR).lastModified());
			} catch (FileNotFoundException e1) {
				// new File(ARH_DIR).createNewFile();
				e1.printStackTrace();
			}
		}

	}

	@Override
	public Cost[] getStat(String ex) throws MalformedURLException, IOException {
		String dates[] = sd.getYear();
		Cost ans[] = new Cost[dates.length];
		long a = System.currentTimeMillis();
		for (int i = 0; i < dates.length; i++) {
			String fromStream = readFromUrl(arApiLink + dates[i]);
			JSONParser p = new JSONParser();
			Object obj = JSONValue.parse(fromStream);
			JSONObject o = (JSONObject) obj;
			JSONArray arr = (JSONArray) o.get("exchangeRate");
			Iterator<JSONObject> iter = arr.iterator();
			while (iter.hasNext()) {
				JSONObject info = iter.next();
				if (ex.equals((String) info.get("currency"))) {
					try {
						ans[i] = new Cost((double) info.get("purchaseRate"), (double) info.get("saleRate"));
					} catch (NullPointerException npe) {
						continue;
					}
					System.err.printf("%d %s\n", i, ans[i]);
					break;
				}
			}

		}

		long b = System.currentTimeMillis();
		System.err.println(b - a);
		return ans;
	}

	private String readFromUrl(final String link) throws MalformedURLException, IOException {
		URLConnection con = new URL(link).openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String ln;
		while ((ln = br.readLine()) != null)
			sb.append(ln);
		br.close();
		return sb.toString();

	}

	/*public static void main(String[] args) throws MalformedURLException, IOException, ClassNotFoundException {
		PrivatBank pb = new PrivatBank();
		pb.getStat("EUR");
		// Cost arr[] = pb.readStat("statpb2014EUR.txt");
		// pb.writeArray(pb.getStat("CAD"),"statpb2014CAD.txt");
		// System.out.println(arr[11]);
	}*/

}
