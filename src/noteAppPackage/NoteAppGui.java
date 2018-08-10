package noteAppPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JEditorPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Canvas;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class NoteAppGui {

	private JFrame frame;
	private JPanel panel;
	private JTextField textUsername;
	private JPasswordField textPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NoteAppGui window = new NoteAppGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NoteAppGui() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		
		// class object initialization
		RuntimeSession session = new RuntimeSession();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		panel = new JPanel();
		panel.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setForeground(UIManager.getColor("EditorPane.selectionBackground"));
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 255, 255));
		
		JTextPane messageDisplay = new JTextPane(); // list of user messages
		messageDisplay.setDropMode(DropMode.INSERT);
		messageDisplay.setEditable(false); 
		
		JTextPane messageInput = new JTextPane(); // stores a new message
		
		JLabel labelHello = new JLabel("");
		labelHello.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton buttonSendMessage = new JButton("Submit"); // submit message
		buttonSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = messageInput.getText();
				session.sendUserMessage(message);
				String messages = session.setuserMessagesAsString();
				messageDisplay.setText(messages);
			}
		});
		
		JButton buttonLogout = new JButton("Logout"); // active user logout
		buttonLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelHello.setText("");
				messageDisplay.setText("");
				messageInput.setText("");
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(messageDisplay, GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
						.addComponent(messageInput, GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(buttonLogout, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelHello, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
							.addComponent(buttonSendMessage, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(messageInput, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(buttonSendMessage, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(buttonLogout, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
								.addComponent(labelHello, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(messageDisplay, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)))
					.addGap(29))
		);
		
		textUsername = new JTextField(); // user input field
		textUsername.setColumns(10);
		
		textPassword = new JPasswordField(); // user password input
		
		JLabel lblNewLabel = new JLabel("Username");
		
		JLabel lblPassword = new JLabel("Password");
		
		JButton buttonLogin = new JButton("Login"); // login feature
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				messageDisplay.setText("");
				String username = textUsername.getText();
				String password = String.valueOf(textPassword.getPassword());
				labelHello.setText("Hello " + username);	
				session.loginUser(username, password);
				String messages = session.setuserMessagesAsString();
				messageDisplay.setText(messages);
			}
		});
		
		
		
		JLabel lblNewLabel_1 = new JLabel("JNote"); // logo
		lblNewLabel_1.setFont(new Font("Kokonor", Font.BOLD | Font.ITALIC, 31));
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(buttonLogin, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
							.addGap(21))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
								.addComponent(textPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
								.addComponent(textUsername, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
							.addGap(24))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(16)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(buttonLogin)
					.addContainerGap(232, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		// menu bar items
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar); 
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JButton menuDeleteButton = new JButton("Delete Messages");
		menuDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.deleteUserMessageData();
			}
		});
		mnFile.add(menuDeleteButton);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
	}
}
