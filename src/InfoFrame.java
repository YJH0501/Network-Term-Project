import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InfoFrame extends JFrame {
	MessengerClient client;
	private String infoId="";

   JPanel contentPane;
   JPanel namePanel;
   JPanel infoPanel;
   JLabel nameLabel;
   JLabel nicknameLabel;
   JLabel birthdayLabel;
   JLabel iconLabel;
   JLabel emailLabel;
   JLabel phonenumberLabel;
   JLabel feelLabel;
   JButton addButton;
   JButton chatButton;
   JLabel phoneNumber;
   JLabel birthday;
   JLabel email;
   JLabel feel;
   
   public InfoFrame() {
      setResizable(false);
      contentPane = new JPanel();
      contentPane.setPreferredSize(new Dimension(450,500));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      
      //Panel to show name, nickname
      namePanel = new JPanel();
      namePanel.setBackground(new Color(152, 251, 152));
      namePanel.setBounds(10, 10, 430, 130);
      contentPane.add(namePanel);
      namePanel.setLayout(null);
      
      nameLabel = new JLabel("Name");
      nameLabel.setOpaque(true);
      nameLabel.setBackground(Color.WHITE);
      nameLabel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Name", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
      nameLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
      nameLabel.setBounds(134, 10, 264, 50);
      namePanel.add(nameLabel);
      
      nicknameLabel = new JLabel("NickName");
      nicknameLabel.setOpaque(true);
      nicknameLabel.setBackground(Color.WHITE);
      nicknameLabel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Nick Name", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
      nicknameLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
      nicknameLabel.setBounds(134, 70, 264, 50);
      namePanel.add(nicknameLabel);
      
      iconLabel = new JLabel("");
      iconLabel.setIcon(new ImageIcon(InfoFrame.class.getResource("/image/user_online.png")));
      iconLabel.setBackground(Color.WHITE);
      iconLabel.setOpaque(true);
      iconLabel.setBorder(new TitledBorder(new LineBorder(Color.black,2)));
      iconLabel.setBounds(12, 10, 110, 110);
      namePanel.add(iconLabel);
      
      
      //Panel to show the info of friend
      
      infoPanel = new JPanel();
      infoPanel.setLayout(null);
      infoPanel.setBackground(new Color(152, 251, 152));
      infoPanel.setBounds(10, 150, 430, 340);
      contentPane.add(infoPanel);
      
      birthdayLabel = new JLabel("Birthday");
      birthdayLabel.setBorder(new LineBorder(Color.black,2));
      birthdayLabel.setHorizontalAlignment(SwingConstants.CENTER);
      birthdayLabel.setOpaque(true);
      birthdayLabel.setBackground(Color.YELLOW);
      birthdayLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
      birthdayLabel.setBounds(10, 30, 110, 30);
      infoPanel.add(birthdayLabel);
      
      emailLabel = new JLabel("Email");
      emailLabel.setBackground(Color.YELLOW);
      emailLabel.setOpaque(true);
      emailLabel.setBorder(new LineBorder(Color.black,2));
      emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
      emailLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
      emailLabel.setBounds(10, 90, 110, 30);
      infoPanel.add(emailLabel);
      
      phonenumberLabel = new JLabel("Phone");
      phonenumberLabel.setBackground(Color.YELLOW);
      phonenumberLabel.setOpaque(true);
      phonenumberLabel.setBorder(new LineBorder(Color.black,2));
      phonenumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
      phonenumberLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
      phonenumberLabel.setBounds(10, 150, 110, 30);
      infoPanel.add(phonenumberLabel);
      
      feelLabel = new JLabel("Feel");
      feelLabel.setBackground(Color.YELLOW);
      feelLabel.setOpaque(true);
      feelLabel.setBorder(new LineBorder(Color.black,2));
      feelLabel.setHorizontalAlignment(SwingConstants.CENTER);
      feelLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
      feelLabel.setBounds(10, 210, 110, 30);
      infoPanel.add(feelLabel);
      
      chatButton = new JButton("Chat");
      chatButton.setFont(new Font("Calibri", Font.PLAIN, 20));
      chatButton.setBounds(235, 280, 175, 40);
      chatButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			client.outputStream.println("REQUEST_CHAT_ROOM");
			client.outputStream.println(infoId);			
		}
      });
      infoPanel.add(chatButton);
      
      addButton = new JButton("Add Friend");
      addButton.setFont(new Font("Calibri", Font.PLAIN, 20));
      addButton.setBounds(20, 280, 175, 40);
      infoPanel.add(addButton);
      
      
      phoneNumber = new JLabel("");
      phoneNumber.setOpaque(true);
      phoneNumber.setBackground(Color.WHITE);
      phoneNumber.setBorder(new LineBorder(Color.black,2));
      phoneNumber.setFont(new Font("Calibri", Font.PLAIN, 20));
      phoneNumber.setBounds(150, 150, 270, 30);
      infoPanel.add(phoneNumber);
      
      birthday = new JLabel("");
      birthday.setOpaque(true);
      birthday.setBackground(Color.WHITE);
      birthday.setBorder(new LineBorder(Color.black,2));
      birthday.setFont(new Font("Calibri", Font.PLAIN, 20));
      birthday.setBounds(150, 30, 270, 30);
      infoPanel.add(birthday);
      
      email = new JLabel("");
      email.setOpaque(true);
      email.setBackground(Color.WHITE);
      email.setBorder(new LineBorder(Color.black,2));
      email.setFont(new Font("Calibri", Font.PLAIN, 20));
      email.setBounds(150, 90, 270, 30);
      infoPanel.add(email);
      
      feel = new JLabel("");
      feel.setOpaque(true);
      feel.setBackground(Color.WHITE);
      feel.setBorder(new LineBorder(Color.black,2));
      feel.setFont(new Font("Calibri", Font.PLAIN, 20));
      feel.setBounds(150, 210, 270, 30);
      infoPanel.add(feel);
      
      pack();
   }
   
    //change image size
  	private ImageIcon changeImageSize(ImageIcon icon, int width, int height)
 	{
 		Image img=icon.getImage();
 		Image resultImage=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
 		
 		ImageIcon resultIcon=new ImageIcon();

 		return new ImageIcon(resultImage);
 	}
   
   public void setFrame(String id, String name, String nickname, String b, String e, String p, String f,boolean isFriend) {
	      infoId=id;
	      
	      String foward = p.substring(0, 3);
	      String middle = p.substring(3, 7);
	      String last = p.substring(7);	      
	      String pn = foward + "-" + middle + "-" + last;
	      
	      nameLabel.setText(" "+name);
	      nicknameLabel.setText(" "+nickname);
	      birthday.setText("  "+b);
	      email.setText("  "+e);
	      phoneNumber.setText("  "+pn);
	      feel.setText("  "+f);
	      
	      addButton.setEnabled(!isFriend);
	      
	      setVisible(true);
   }
   
   //connect with client
   public void setMain(MessengerClient c) {
 		client=c;	
   }
}