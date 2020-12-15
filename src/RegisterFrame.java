import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;

public class RegisterFrame extends JFrame{
	MessengerClient client;
	
	JPanel registerPanel;
	EtchedBorder eborder;
	
	JLabel idLabel;
	JLabel pswLabel;
	JLabel rePswLabel;
	JLabel nameLabel;
	JLabel lblNewLabel;
	JLabel emailLabel;
	JLabel birthLabel;
	JLabel addressMarkLabel;
	JLabel phoneLabel;
	JLabel questionLabel;
	JLabel answerLabel;
	
	JTextField idTxtField;	
	JTextField nameTxtField;
	JTextField nickNameTxtField;
	JTextField emailTxtField;
	JTextField addressTxtField;	
	JTextField phoneTxtField;
	JTextField answerTxtField;
	JPasswordField pswTxtField;
	JPasswordField rePswTxtField;
	
	JComboBox yearComboBox;
	JComboBox monthComboBox;
	JComboBox dayComboBox;
	JComboBox addressComboBox;
	JComboBox questionComboBox;
	
	JButton idCheckBtn;
	JButton registerBtn;
	
	/*	
	public static void main(String[] args) {
		new RegisterFrame();

	}
	*/

	
	public RegisterFrame()
	{
		setTitle("Register");
		setResizable(false);
		
		eborder=new EtchedBorder(EtchedBorder.RAISED);
		
		registerPanel = new JPanel();
		registerPanel.setBackground(UIManager.getColor("Panel.background"));
		registerPanel.setPreferredSize(new Dimension(500, 750));
		setContentPane(registerPanel);
		registerPanel.setLayout(null);
		
		JLabel registerLabel = new JLabel("R E G I S T E R");
		registerLabel.setBackground(Color.BLACK);
		registerLabel.setForeground(Color.BLACK);
		registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerLabel.setFont(new Font("Calibri", Font.BOLD, 35));
		registerLabel.setBounds(0, 20, 500, 60);
		registerLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), null));
		registerPanel.add(registerLabel);
		
		idLabel = new JLabel("ID : ");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		idLabel.setBounds(20, 90, 120, 35);
		registerPanel.add(idLabel);
		
		idTxtField=new JTextField();
		idTxtField.setColumns(20);
		idTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		idTxtField.setLocation(150, 90);
		idTxtField.setSize(200, 35);
		registerPanel.add(idTxtField);
		
		idCheckBtn=new JButton("check");
		idCheckBtn.setFont(new Font("Calibri", Font.PLAIN, 17));
		idCheckBtn.setSize(100, 30);
		idCheckBtn.setLocation(200, 130);
		idCheckBtn.addActionListener(new checkBtnHandler());
		registerPanel.add(idCheckBtn);
		
		pswLabel = new JLabel("PW : ");
		pswLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		pswLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pswLabel.setBounds(20, 175, 120, 35);
		registerPanel.add(pswLabel);
		
		pswTxtField=new JPasswordField();
		pswTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		pswTxtField.setSize(200, 35);
		pswTxtField.setLocation(150, 175);
		registerPanel.add(pswTxtField);
		
		rePswLabel = new JLabel("Confirm PW : ");
		rePswLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		rePswLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		rePswLabel.setBounds(20, 220, 120, 35);
		registerPanel.add(rePswLabel);
		
		rePswTxtField=new JPasswordField();
		rePswTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		rePswTxtField.setSize(200, 35);
		rePswTxtField.setLocation(150, 220);
		registerPanel.add(rePswTxtField);
		
		nameLabel = new JLabel("NAME : ");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		nameLabel.setBounds(20, 265, 120, 35);
		registerPanel.add(nameLabel);
		
		nameTxtField=new JTextField();
		nameTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		nameTxtField.setSize(200, 35);
		nameTxtField.setLocation(150, 265);
		registerPanel.add(nameTxtField);
		
		lblNewLabel = new JLabel("NICKNAME : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		lblNewLabel.setBounds(20, 310, 120, 35);
		registerPanel.add(lblNewLabel);
		
		nickNameTxtField=new JTextField();
		nickNameTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		nickNameTxtField.setSize(200, 35);
		nickNameTxtField.setLocation(150, 310);
		registerPanel.add(nickNameTxtField);
		
		birthLabel = new JLabel("BIRTHDAY : ");
		birthLabel.setSize(120, 35);
		birthLabel.setLocation(20, 355);
		birthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		birthLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		registerPanel.add(birthLabel);
		
		yearComboBox = new JComboBox();
		yearComboBox.setSize(80, 35);
		yearComboBox.setLocation(150, 355);
		yearComboBox.setBackground(Color.WHITE);
		yearComboBox.setModel(new DefaultComboBoxModel(new String[] {"year", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004"}));
		yearComboBox.setFont(new Font("Calibri", Font.PLAIN, 17));
		yearComboBox.addActionListener(new addressHandler());
		registerPanel.add(yearComboBox);
		
		monthComboBox = new JComboBox();
		monthComboBox.setSize(80, 35);
		monthComboBox.setLocation(250, 355);
		monthComboBox.setBackground(Color.WHITE);
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] {"month", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthComboBox.setFont(new Font("Calibri", Font.PLAIN, 17));
		monthComboBox.addActionListener(new addressHandler());
		registerPanel.add(monthComboBox);
		
		dayComboBox = new JComboBox();
		dayComboBox.setSize(80, 35);
		dayComboBox.setLocation(350, 355);
		dayComboBox.setBackground(Color.WHITE);
		dayComboBox.setModel(new DefaultComboBoxModel(new String[] {"day", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		dayComboBox.setFont(new Font("Calibri", Font.PLAIN, 17));
		dayComboBox.addActionListener(new addressHandler());
		registerPanel.add(dayComboBox);
		
		
		emailLabel = new JLabel("E_MAIL : ");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		emailLabel.setBounds(20, 400, 120, 35);
		registerPanel.add(emailLabel);
		
		emailTxtField=new JTextField();
		emailTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		emailTxtField.setSize(100, 35);
		emailTxtField.setLocation(150, 400);
		registerPanel.add(emailTxtField);
		
		addressMarkLabel = new JLabel("@");
		addressMarkLabel.setFont(new Font("Calibri", Font.BOLD, 17));
		addressMarkLabel.setBounds(260, 400, 20, 35);
		registerPanel.add(addressMarkLabel);
		
		addressTxtField=new JTextField();
		addressTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		addressTxtField.setSize(140, 35);
		addressTxtField.setLocation(290, 400);
		registerPanel.add(addressTxtField);
		
		addressComboBox=new JComboBox();
		addressComboBox.setBackground(Color.WHITE);
		addressComboBox.setModel(new DefaultComboBoxModel(new String[] {"INPUT DIRECTLY", "daum.net", "gachon.ac.kr", "gmail.com", "nate.com", "naver.com"}));
		addressComboBox.setFont(new Font("Calibri", Font.PLAIN, 17));
		addressComboBox.setSize(140, 35);
		addressComboBox.setLocation(290, 445);
		addressComboBox.addActionListener(new addressHandler());
		registerPanel.add(addressComboBox);
		
		phoneLabel = new JLabel("PHONE : ");
		phoneLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setBounds(20, 500, 120, 35);
		registerPanel.add(phoneLabel);
		
		phoneTxtField=new JTextField();
		phoneTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		phoneTxtField.setSize(200, 35);
		phoneTxtField.setLocation(150, 500);
		registerPanel.add(phoneTxtField);
		
		questionLabel = new JLabel("QUESTION : ");
		questionLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		questionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		questionLabel.setBounds(20, 545, 120, 35);
		registerPanel.add(questionLabel);
		
		questionComboBox=new JComboBox();
		questionComboBox.setBackground(Color.WHITE);
		questionComboBox.setModel(new DefaultComboBoxModel(new String[] {"QUESTION TO FIND P/W", "What is your favorite character?", "What is your no.1 treasure?", "What is your best friend's name?"}));
		questionComboBox.setFont(new Font("Calibri", Font.PLAIN, 17));
		questionComboBox.setSize(300, 35);
		questionComboBox.setLocation(150, 545);
		registerPanel.add(questionComboBox);
		
		answerLabel = new JLabel("ANSWER : ");
		answerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		answerLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		answerLabel.setBounds(20, 590, 120, 35);
		registerPanel.add(answerLabel);
		
		answerTxtField=new JTextField();
		answerTxtField.setFont(new Font("Calibri", Font.PLAIN, 17));
		answerTxtField.setSize(200, 35);
		answerTxtField.setLocation(150, 595);
		registerPanel.add(answerTxtField);
		
		registerBtn=new JButton("Register");
		registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
		registerBtn.setSize(130, 50);
		registerBtn.setLocation(185, 670);
		registerBtn.addActionListener(new registerBtnHandler());
		registerPanel.add(registerBtn);
		
		pack();
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			  {
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Do you want to return to the login page?", "REGISTER PAGE",
			        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION)
			    {
			      setVisible(false);
			      client.loginFrame.setVisible(true);
			    }
			    else
			    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			  }
		});
	}
	
	private class checkBtnHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String checkId=idTxtField.getText().trim();
			boolean isOK;
			
			if(checkId.length()<5||checkId.length()>15)
			{
				JOptionPane.showMessageDialog(null,"Please enter your ID in 5 to 15 characters.","ID check",JOptionPane.DEFAULT_OPTION);
			}
			
			else
			{
				client.outputStream.println("CHECK_ID");
				client.outputStream.println(checkId);
			}
		}
	}
	
	private class addressHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int index=addressComboBox.getSelectedIndex();
			String address=(String)addressComboBox.getSelectedItem();
			
			switch(index)
			{
			case -1:
			case 0:
				addressTxtField.setEditable(true);
				addressTxtField.setText("");
				break;
				
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				addressTxtField.setEditable(false);
				addressTxtField.setText(address);
				break;
			}
		}
	}
	
	private class registerBtnHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			String registerName=nameTxtField.getText().trim();
			String registerNickName=nickNameTxtField.getText().trim();
			String registerBirth=yearComboBox.getSelectedItem().toString()+monthComboBox.getSelectedItem().toString()+dayComboBox.getSelectedItem().toString();
			String registerEmail=emailTxtField.getText().trim();
			String registerAddress=addressTxtField.getText().trim();
			String registerPhone=phoneTxtField.getText().trim();
			int registerQuestion=questionComboBox.getSelectedIndex();
			String registerAnswer=answerTxtField.getText().trim();
			String today="hello~ I am "+registerName;
			
			if(idTxtField.isEditable()==true)
			{
				JOptionPane.showMessageDialog(null,"Check ID duplicates.","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			String psw="";
			char[] temp_psw=pswTxtField.getPassword();
			for(char cha : temp_psw)
			{
				Character.toString(cha);
				psw+=(psw.equals("")?""+cha+"" : ""+cha+"");
			}
			String rePsw="";
			char[] temp_rePsw=rePswTxtField.getPassword();
			for(char cha : temp_rePsw)
			{
				Character.toString(cha);
				rePsw+=(rePsw.equals("")?""+cha+"" : ""+cha+"");
			}
			if(!psw.equals(rePsw))
			{
				JOptionPane.showMessageDialog(null,"You entered the wrong password.","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			else if(psw.length()<5||psw.length()>15)
			{
				JOptionPane.showMessageDialog(null,"Please enter your password in 5 to 15 characters.","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			if(registerName.length()==0)
			{
				JOptionPane.showMessageDialog(null,"Type your name.","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			if(registerNickName.length()==0)
			{
				JOptionPane.showMessageDialog(null,"Type your nickname.","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			/*
			client.outputStream.println("CHECK_NICKNAME");
			client.outputStream.println(registerNickName);
			boolean isOK=client.inputStream.nextBoolean(); client.inputStream.nextLine();
			if(isOK==false)
			{
				
				JOptionPane.showMessageDialog(null,"You can't use nickname.\ntry again","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			*/
			
			if(registerBirth.length()!=8)
			{
				JOptionPane.showMessageDialog(null, "Select your birthday correctly","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			if(registerEmail.length()==0||registerAddress.length()==0)
			{
				JOptionPane.showMessageDialog(null,"Please enter a valid email.","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			if(registerPhone.length()==0)
			{
				JOptionPane.showMessageDialog(null,"Type your phone number.","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
			
			if(registerQuestion<=0||registerAnswer.length()==0)
			{
				JOptionPane.showMessageDialog(null,"Select you question or Type answer","ERROR",JOptionPane.DEFAULT_OPTION);
				return;
			}
				
			
			
			client.outputStream.println("REGISTER_TRY");
			client.outputStream.println(idTxtField.getText().trim());
			client.outputStream.println(psw.trim());
			client.outputStream.println(registerName);
			client.outputStream.println(registerNickName);
			client.outputStream.println(registerBirth);
			client.outputStream.println(registerEmail+"@"+registerAddress);
			client.outputStream.println(registerPhone);
			client.outputStream.println(registerQuestion);
			client.outputStream.println(registerAnswer);
			client.outputStream.println(today);
		}
		
	}
	
	//클라이언트와 회원가입 창을 연동시킴
	public void setMain(MessengerClient c) {
		client=c;		
	}
}