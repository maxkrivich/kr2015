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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class NBU extends Bank {
	private final String ARH_DIR = "files/nbujson.txt";

	public NBU() {
		bankName = "NBU";
		apiLink = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=3";
		baseCcy = "UAH";
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
			// ccy.put("USD", new Cost(24.1, 25.1));
			// ccy.put("EUR", new Cost(26.3, 27.3));
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
				updateTime = new Date(new File(ARH_DIR).lastModified());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			//e.printStackTrace();
		}

	}

	@Override
	public Cost[] getStat(String ex) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

//	 public static void main(String[] args) {
//	 PrivatBank pb = new PrivatBank();
//	 //System.out.println(Arrays.toString(pb.getCurrency()));
//	 }

}
