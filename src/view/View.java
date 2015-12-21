package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import model.Model;

public class View extends JFrame {

	private JPanel contentPane;
	private JTextField sum;
	private JComboBox from;
	private JComboBox to;
	private JButton btn;
	private JButton course;
	private JTextArea log;
	private JList list;
	private JButton update;
	private boolean isOnline;

	public View(Model model) {
		super("CurrencyExchanger @maxkrivich");
		try {
			this.setIconImage(ImageIO.read(new File("ico.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblSum = new JLabel("<html><b>Sum:</b></html>");
		panel.add(lblSum);

		sum = new JTextField();
		panel.add(sum);
		sum.setColumns(10);

		from = new JComboBox(model.getCurrencyAtBank());
		panel.add(from);

		JLabel lblTo = new JLabel("<html><b>to</b></html>");
		panel.add(lblTo);

		to = new JComboBox(model.getCurrencyAtBank());
		panel.add(to);

		btn = new JButton("exchange");
		InputMap im = btn.getInputMap();
		im.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
		im.put(KeyStroke.getKeyStroke("released ENTER"), "released");
		panel.add(btn);
		panel.getRootPane().setDefaultButton(btn);

		log = new JTextArea();
		log.setBackground(SystemColor.menu);
		log.setEditable(false);
		log.setFont(new Font("Times New Roman", Font.BOLD, 11));
		JScrollPane scroll = new JScrollPane(log);
		contentPane.add(scroll, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		list = new JList(model.getBanksName());
		list.setBackground(SystemColor.menu);
		list.setSelectedIndex(0);
		list.setAlignmentX(Component.CENTER_ALIGNMENT);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scp = new JScrollPane(list);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.add(scp);

		update = new JButton("Update");
		update.setAlignmentX(Component.CENTER_ALIGNMENT);
		course = new JButton("Course");
		course.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(course);
		panel_1.add(update);
		contentPane.add(panel_1, BorderLayout.WEST);
		

		this.print("Last update: " + model.getLastUpdate());

		/*
		 * JPanel panel_2 = new JPanel(); contentPane.add(panel_2,
		 * BorderLayout.EAST);
		 */

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new FlowLayout());
		panel_3.add(new ClockPane());
		if (!(isOnline = netIsAvailable())) {
			JLabel off = new JLabel("<html><b><p>you offline</b></html>", JLabel.LEFT);
			off.setForeground(Color.RED);
			panel_3.add(off);
		}
		contentPane.add(panel_3, BorderLayout.SOUTH);

		setVisible(true);
		//setFocusable(true);

		setLocationRelativeTo(null);
	}

	public JList getList() {
		return list;
	}

	public double getSum() {
		String s = sum.getText().replaceAll(",", ".");
		if (!s.isEmpty() && s.matches("[+]?\\d*(\\.\\d+)?"))
			return Double.parseDouble(s);
		else {
			this.print("Wrong sum, please try again!\n");
			return Double.POSITIVE_INFINITY;
		}
	}

	public void print(String s) {
		log.append(s);
	}

	public String getFrom() {
		return from.getSelectedItem().toString();
	}

	public boolean getInetStatus() {
		return isOnline;
	}

	public String getTo() {
		return to.getSelectedItem().toString();
	}

	public void appendSolution(double sum1, double sum2, String from, String to) {
		log.append(String.format(Locale.US, "%.2f %s | %.2f %s\n", sum1, from, sum2, to));
	}

	public void addListenerForUpdate(ActionListener al) {
		this.update.addActionListener(al);
	}

	public void addListenerForExchange(ActionListener al) {
		this.btn.addActionListener(al);
	}

	public void addListenerCourse(ActionListener al) {
		this.course.addActionListener(al);
	}

	public void addListenerList(ListSelectionListener lsl) {
		this.list.addListSelectionListener(lsl);
	}

	public boolean netIsAvailable() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}

}

class ClockPane extends JPanel {

	private JLabel clock;

	public ClockPane() {
		setLayout(new BorderLayout());
		clock = new JLabel();
		clock.setHorizontalAlignment(JLabel.CENTER);
		clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 12));
		tickTock();
		add(clock);

		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tickTock();
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(0);
		timer.start();
	}

	public void tickTock() {
		clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
	}
}
