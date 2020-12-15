import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;

public class ChatFrame extends JFrame {

   private String FriendName; //the opponent of the chat
   private String roomName;
   
   JPanel chatPanel;
   JPanel typePanel;
   JPanel chattingPanel;
   JPanel infoPanel;
   JPanel searchPanel;
   JLabel searchLabel;
   JLabel icon;
   JLabel friendName;
   JButton addFriendButton;
   JButton leaveChatButton;
   JButton typeButton;
   JButton searchButton;
   JTextArea messageArea;
   JTextArea chatArea;
   JScrollPane scrollPane;
   JScrollPane scrollPane_1;
   JTextField searchField;
   List resultList;
   
   public ChatFrame(String n, int x,int y) {
      setTitle("Chat Room");
      
      setLocation(x,y);
      setResizable(false);
      chatPanel = new JPanel();
      chatPanel.setPreferredSize(new Dimension(400,600));
      chatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(chatPanel);
      chatPanel.setLayout(null);
      
      
      //Panel of Chatting area
      
      chattingPanel = new JPanel();
      chattingPanel.setBackground(new Color(127, 255, 0));
      chattingPanel.setBounds(0, 80, 400, 420);
      chatPanel.add(chattingPanel);
      chattingPanel.setLayout(null);
      
      chatArea = new JTextArea();
      chatArea.setEditable(false);
      scrollPane_1 = new JScrollPane(chatArea);
      chatArea.setLineWrap(true);
      chatArea.setFont(new Font("Calibri", Font.PLAIN, 17));      
      scrollPane_1.setBounds(10, 10, 380, 400);
      
      chattingPanel.add(scrollPane_1);
      
      
      //Panel of information
      
      infoPanel = new JPanel();
      infoPanel.setBackground(new Color(173, 255, 47));
      infoPanel.setBounds(0, 0, 400, 80);
      chatPanel.add(infoPanel);
      infoPanel.setLayout(null);
      
      icon = new JLabel();
      icon.setIcon(changeImageSize((new ImageIcon(ChatFrame.class.getResource("/image/user_online.png"))),60,60));
      icon.setBounds(10, 10, 60, 60);
      infoPanel.add(icon);
      
      friendName = new JLabel(n);
      friendName.setFont(new Font("Calibri", Font.PLAIN, 20));
      friendName.setBounds(80, 10, 150, 30);
      infoPanel.add(friendName);
      
      addFriendButton = new JButton("Add New Friend");
      addFriendButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	chattingPanel.setVisible(false);
        	typePanel.setVisible(false);
            searchPanel.setVisible(true);
         }
      });
      addFriendButton.setFont(new Font("Calibri", Font.PLAIN, 16));
      addFriendButton.setBounds(80, 50, 150, 20);
      infoPanel.add(addFriendButton);
      
      leaveChatButton = new JButton("Leave The Chat");
      leaveChatButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             int confirmed = JOptionPane.showConfirmDialog(chatArea, 
                    "Are you sure you want to exit the chat?\n"
                    + "The message log will be deleted.", "Exit Chat Message Box",
                    JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION)
                  dispose();
                else
                   setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
         }
      });
      leaveChatButton.setFont(new Font("Calibri", Font.PLAIN, 17));
      leaveChatButton.setBounds(240, 50, 150, 20);
      infoPanel.add(leaveChatButton);
      
      
      //Panel to type the message      
      typePanel = new JPanel();
      typePanel.setBackground(new Color(255, 250, 250));
      typePanel.setBounds(0, 500, 400, 100);
      chatPanel.add(typePanel);
      typePanel.setLayout(null);
      
      messageArea = new JTextArea();
      scrollPane = new JScrollPane(messageArea);
      messageArea.setFont(new Font("Calibri", Font.PLAIN, 17));
      scrollPane.setBounds(10, 10, 280, 50);
      messageArea.setLineWrap(true);
      typePanel.add(scrollPane);
      
      typeButton = new JButton("Type");
      typeButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String message = messageArea.getText();
            chatArea.append(message+"\n");
            messageArea.setText(null);
         }
      });
      typeButton.setFont(new Font("Calibri", Font.PLAIN, 17));
      typeButton.setBounds(310, 10, 80, 80);
      typePanel.add(typeButton);
      
      
      //search for friend panel
      searchPanel = new JPanel();
      searchPanel.setBackground(new Color(127, 255, 0));
      searchPanel.setBounds(0, 80, 400, 520);
      chatPanel.add(searchPanel);
      searchPanel.setLayout(null);
      
      searchLabel = new JLabel("Search for friend");
      searchLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
      searchLabel.setBounds(10, 10, 145, 25);
      searchPanel.add(searchLabel);
      
      searchField = new JTextField();
      searchField.setFont(new Font("Calibri", Font.PLAIN, 17));
      searchField.setBounds(10, 40, 340, 30);
      searchPanel.add(searchField);
      searchField.setColumns(10);
      
      searchButton = new JButton("");
      searchButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 //search한다
 
         }
      });
      searchButton.setBackground(UIManager.getColor("Button.background"));
      searchButton.setIcon(changeImageSize(new ImageIcon(ChatFrame.class.getResource("/image/search.png")),30,30));
      searchButton.setBounds(360, 40, 30, 30);
      searchPanel.add(searchButton);
      
      resultList = new List();
      resultList.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
             if (e.getClickCount() == 2) { // if double clicked
                String name = resultList.getSelectedItem();
                resultList.removeAll();
                searchField.setText(null);
                System.out.println(name);
                searchPanel.setVisible(false);
                chattingPanel.setVisible(true);
                typePanel.setVisible(true);
             }
          }
       });
      resultList.setBounds(10, 80, 380, 430);
      searchPanel.add(resultList);
      searchPanel.setVisible(false);
      

     pack();
     setVisible(true);
   }
   
    //change image size
 	private ImageIcon changeImageSize(ImageIcon icon, int width, int height)
 	{
 		Image img=icon.getImage();
 		Image resultImage=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
 		
 		ImageIcon resultIcon=new ImageIcon();

 		return new ImageIcon(resultImage);
 	}

 	/*
 	public static void main(String[] args) {
 		new ChatFrame("hi",100,100);
 	}
 	*/
}