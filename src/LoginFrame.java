import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame {
	MessengerClient client;
	
	private String id="";
	private	String psw="";

	JPanel loginPanel;
	JLabel loginLogo;
	JTextField idTextField;
	JPasswordField passwordField;
	JButton loginBtn;
	JLabel idLabel;
	JLabel pswLabel;
	JLabel registerLabel;
	JLabel forgotLabel;
	
	ImageIcon logoImage;

	public LoginFrame() {
		setTitle("LOGIN");
		setResizable(false);
		
		loginPanel = new JPanel();
		loginPanel.setPreferredSize(new Dimension(495,290));
		setContentPane(loginPanel);
		loginPanel.setLayout(null);
		
		logoImage=changeImageSize(new ImageIcon(LoginFrame.class.getResource("/image/logo.png")),475,90);
		loginLogo = new JLabel(logoImage);
		loginLogo.setBounds(10, 10, 475, 90);
		loginPanel.add(loginLogo);

		idLabel = new JLabel("ID");
		idLabel.setFont(new Font("Calibri", Font.BOLD, 30));
		idLabel.setVerticalAlignment(SwingConstants.CENTER);
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setBounds(10, 130, 70, 30);
		loginPanel.add(idLabel);

		pswLabel = new JLabel("P/W");
		pswLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		pswLabel.setVerticalAlignment(SwingConstants.CENTER);
		pswLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pswLabel.setBounds(10, 180, 70, 30);
		loginPanel.add(pswLabel);		

		idTextField = new JTextField();
		idTextField.setFont(new Font("Calibri", Font.PLAIN, 25));
		idTextField.setBounds(90, 130, 250, 30);
		idTextField.setColumns(10);
		idTextField.addActionListener(new LoginHandler());
		loginPanel.add(idTextField);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calibri", Font.PLAIN, 25));
		passwordField.setBounds(90, 180, 250, 30);
		passwordField.addActionListener(new LoginHandler());
		loginPanel.add(passwordField);
		
		loginBtn = new JButton("LOGIN");				
		loginBtn.setFont(new Font("Arial", Font.BOLD, 20));
		loginBtn.setBounds(376, 130, 105, 80);
		loginBtn.addActionListener(new LoginHandler());
		loginPanel.add(loginBtn);

		registerLabel = new JLabel("Register");
		registerLabel.addMouseListener(new LoginLabelHandler());
		registerLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		registerLabel.setBounds(420, 259, 61, 18);
		loginPanel.add(registerLabel);

		forgotLabel = new JLabel("Forgot ID/Password?");
		forgotLabel.addMouseListener(new LoginLabelHandler());
		forgotLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		forgotLabel.setBounds(15, 255, 160, 23);
		loginPanel.add(forgotLabel);

		pack();
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			  {
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Are you sure you want to exit the program?", "Exit Program Message Box",
			        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION)
			      System.exit(0);
			    else
			    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			  }
		});
	}
	
	private class LoginHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//입력한 아이디, 비번 추출
			id = idTextField.getText();
			psw="";
			
			char[] temp_psw=passwordField.getPassword();
			for(char cha : temp_psw)
			{
				Character.toString(cha);
				psw+=(psw.equals("")?""+cha+"" : ""+cha+"");
			}
			
			idTextField.setText("");
			passwordField.setText("");
			
			//아이디나 비번을 안쳤을 경우 에러 메세지
			if(id.length()==0||psw.length()==0)
			{
				JOptionPane.showMessageDialog(null, "Type ID or PW","LOGIN RESULT",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			//서버로부터 로그인 요청
			client.outputStream.println("LOGIN_TRY");
			client.outputStream.println(id);
			client.outputStream.println(psw);
		}
	}
	
	private class LoginLabelHandler implements MouseListener
	{
		
		public void mouseClicked(MouseEvent e) {
			JLabel jl=(JLabel) e.getSource();
			String str=jl.getText();
			
			if(jl==registerLabel)
			{
				client.registerFrame.setVisible(true);
				setVisible(false);
			}
			else if(jl==forgotLabel)
			{
				client.forgotFrame.setVisible(true);
				setVisible(false);
			}
		}

		public void mouseEntered(MouseEvent e) {
			JLabel jl=(JLabel) e.getSource();
			jl.setForeground(Color.BLUE);
		}

		public void mouseExited(MouseEvent e) {
			JLabel jl=(JLabel) e.getSource();
			jl.setForeground(Color.BLACK);
		}

		public void mousePressed(MouseEvent e) {
			JLabel jl=(JLabel) e.getSource();
			jl.setForeground(Color.MAGENTA);
		}

		public void mouseReleased(MouseEvent e) {
			JLabel jl=(JLabel) e.getSource();
			jl.setForeground(Color.BLACK);
		}
	}
	
	//change image size
	private ImageIcon changeImageSize(ImageIcon icon, int width, int height)
	{
		Image img=icon.getImage();
		Image resultImage=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			
		ImageIcon resultIcon=new ImageIcon();

		return new ImageIcon(resultImage);
	}
		
	public void setMain(MessengerClient c) {
		client=c;		
	}
}


