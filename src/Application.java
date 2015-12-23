import java.awt.EventQueue;

import controller.Controller;
import model.Model;
import view.View;

public class Application {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.json.simple.parser.JSONParser");
		Class.forName("org.json.simple.JSONArray");
		Class.forName("org.json.simple.JSONObject");
		Class.forName("org.json.simple.JSONValue");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model m = new Model();
					View v = new View(m);
					Controller c = new Controller(v, m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
