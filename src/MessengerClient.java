import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MessengerClient {
	private String serverAddress;
	private int portNum;
	private Socket socket;
	
	UserInfo myInfo;
	
	Scanner inputStream;
	PrintWriter outputStream;
	
	LoginFrame loginFrame;//로그인 창
	RegisterFrame registerFrame;//회원가입 창
	ForgotFrame forgotFrame;//아이디,비번 찾기 창
	MainFrame mainFrame;//메인 창
	InfoFrame infoFrame;//정보 창
	Hashtable<String,ChatFrame>chatRoomList=new Hashtable<String,ChatFrame>();//대화방 창
	
	public MessengerClient(String serverAddress, int portNum)
	{
		this.serverAddress=serverAddress;
		this.portNum=portNum;
	}
	
	private void connectServer()
	{
		try
		{
			socket=new Socket(serverAddress,portNum);
			inputStream=new Scanner(socket.getInputStream());
			outputStream=new PrintWriter(socket.getOutputStream(),true);
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
			System.exit(0);
		}
	}
	
	//login page processing
	private void runLogin()
	{
		//open login frame
		loginFrame=new LoginFrame();
		loginFrame.setMain(this);
		loginFrame.setVisible(true);	
				
		registerFrame=new RegisterFrame();
		registerFrame.setMain(this);
				
		forgotFrame=new ForgotFrame();
		forgotFrame.setMain(this);
		
		//loop until login clear
		while(true)
		{
			String getMessage;
			
			//get protocol(message) from server
			getMessage=inputStream.nextLine();
			
			//get login result
			if(getMessage.equals("LOGIN_RESULT"))
			{
				String result=inputStream.nextLine();
				
				//login success
				if(result.equals("LOGIN_SUCCESS"))
				{
					JOptionPane.showMessageDialog(null, "SUCCESS","LOGIN RESULT",JOptionPane.DEFAULT_OPTION);
					
					//close frames
					registerFrame.dispose();
					forgotFrame.dispose();
					loginFrame.dispose();
					
					//end login method
					break;
				}
				
				//아이디 중복 접속 불가, 아이디 없음, 비밀번호 오류 나누기
				//duplicate login, not register ID, invalid password
				else if(result.equals("LOGIN_FAIL"))
					JOptionPane.showMessageDialog(null, "FAIL","LOGIN RESULT",JOptionPane.DEFAULT_OPTION);
			}
			
			//get checking ID result when register
			else if(getMessage.equals("REGISTER_ID_CHECK_RESULT"))
			{
				boolean isOK=inputStream.nextBoolean(); inputStream.nextLine();
				
				//useful ID
				if(isOK==true)
				{
					int result=JOptionPane.showConfirmDialog(null, "You can use this ID\nDo you want to use this ID?","ID check",JOptionPane.YES_NO_OPTION);
					
					if(result==JOptionPane.YES_OPTION)
					{
						registerFrame.idTxtField.setEditable(false);
						registerFrame.idCheckBtn.setEnabled(false);
					}
				}
					
				//ID that has already been registered
				else
				{
					JOptionPane.showMessageDialog(null,"You can't use this ID","ID check",JOptionPane.DEFAULT_OPTION);
					registerFrame.idTxtField.setText("");
				}
			}
			
			//get register result
			else if(getMessage.equals("REGISTER_RESULT"))
			{
				//success
				if(inputStream.nextLine().equals("REGISTER_SUCCESS"))
				{
					JOptionPane.showMessageDialog(null, "SUCCESS", "Register result", JOptionPane.DEFAULT_OPTION);
					
					//Empty all the input spaces in the frame
					registerFrame.idTxtField.setText("");
					registerFrame.idTxtField.setEditable(true);
					registerFrame.idCheckBtn.setEnabled(true);
					registerFrame.pswTxtField.setText("");
					registerFrame.rePswTxtField.setText("");
					registerFrame.nameTxtField.setText("");
					registerFrame.nickNameTxtField.setText("");
					registerFrame.yearComboBox.setSelectedIndex(0);
					registerFrame.monthComboBox.setSelectedIndex(0);
					registerFrame.dayComboBox.setSelectedIndex(0);
					registerFrame.emailTxtField.setText("");
					registerFrame.addressTxtField.setText("");
					registerFrame.phoneTxtField.setText("");
					registerFrame.answerTxtField.setText("");
					registerFrame.addressComboBox.setSelectedIndex(0);
					registerFrame.questionComboBox.setSelectedIndex(0);
					
					//close register frame & open login frame
					registerFrame.setVisible(false);
					loginFrame.setVisible(true);
				}
				
				//Failed to enter information into database
				else if(inputStream.nextLine().equals("REGISTER_FAIL"))
				{
					JOptionPane.showMessageDialog(null, "FAiL", "Register result", JOptionPane.DEFAULT_OPTION);
				}		
			}
			
			//get result for finding ID/password
			else if(getMessage.equals("FORGOT_ID_RESULT")||getMessage.equals("FORGOT_PSW_RESULT"))
				forgotFrame.lblResult.setText(inputStream.nextLine());
		}
	}

	//main page processing
	public void runMain()
	{	
		//user's information
		String id;
		String name;
		String nickname;
		String birth;
		String email;
		String phone;
		String feel;
		String friend;
		
		//get user information from the server
		if(inputStream.nextLine().equals("SEND_INFO"))
		{
			id=inputStream.nextLine();
			name=inputStream.nextLine();
			nickname=inputStream.nextLine();
			birth=inputStream.nextLine();
			email=inputStream.nextLine();
			phone=inputStream.nextLine();
			feel=inputStream.nextLine();
			
			//save received information to user object
			myInfo=new UserInfo(id,name,nickname,birth,email,phone,feel);	
		}
		
		//received user's friend list from server
		if(inputStream.nextLine().equals("SEND_FRIEND"))
		{
			while(true)
			{
				friend=inputStream.nextLine();
				
				if(friend.equals("SEND_FRIEND_END"))
					break;
				
				else
					myInfo.addFriend(friend);
			}
		}
		
		//open main frame
		mainFrame=new MainFrame(myInfo);
		mainFrame.setMain(this);
		mainFrame.insertFriend();  //divide the list of friends online and offline
		mainFrame.setVisible(true);
		
		//prepare information frame
		infoFrame=new InfoFrame();
		infoFrame.setMain(this);
		
		//
		while(true)
		{
			String getMessage;
			getMessage=inputStream.nextLine();
			
			if(getMessage.equals("JOIN_USER"))
			{
				mainFrame.gotoOnline(inputStream.nextLine());
			}
			
			else if(getMessage.equals("LEFT_USER"))
			{
				mainFrame.gotoOffline(inputStream.nextLine());
			}
			
			else if(getMessage.equals("SEARCH_INFORMATION_RESULT"))
			{
				String infoId=inputStream.nextLine();
				String infoName=inputStream.nextLine();
				String infoNickname=inputStream.nextLine();
				String infoBirth=inputStream.nextLine();
				String infoEmail=inputStream.nextLine();
				String infoPhone=inputStream.nextLine();
				String infoFeel=inputStream.nextLine();
				boolean isFriend=false;				
				for(String u:myInfo.getFriend())
				{
					if(infoId.equals(u))
					{
						isFriend=true;
						break;
					}
				}
				
				infoFrame.setFrame(infoId,infoName,infoNickname,infoBirth,infoEmail,infoPhone,infoFeel,isFriend);
				infoFrame.setLocationRelativeTo(mainFrame);
			}
			
			else if(getMessage.equals("SEARCH_USER_ID_RESULT"))
			{
				int index=0;
				ArrayList<String> userIds=new ArrayList<String>();
				String tempId;
				while(true)
				{
					tempId=inputStream.nextLine();
					if(tempId.equals("SEARCH_USER_ID_RESULT_END"))
						break;
					
					else
						userIds.add(tempId);
				}
				
				
				mainFrame.searchUserId(userIds);
			}
			
			else if(getMessage.equals("REQUEST_CHAT_ROOM_RESULT"))
			{
				String result=inputStream.nextLine();
				
				if(result.equals("REQUEST_CHAT_ROOM_CLEAR"))
				{
					String otherUser=inputStream.nextLine();
					infoFrame.setVisible(false);
					chatRoomList.put(myInfo.getId()+"-"+otherUser, new ChatFrame(otherUser,mainFrame.getX()-420,mainFrame.getY()));
				}
				
				else if(result.equals("USER_OFFLINE"))
				{
					JOptionPane.showMessageDialog(infoFrame,"The user is currently offline.","CHAT_REQUEST",JOptionPane.DEFAULT_OPTION);
				}
			}
			
			else if(getMessage.equals("REQUEST_CHAT_ROOM"))
			{
				String otherUser=inputStream.nextLine();
				int result=JOptionPane.showConfirmDialog(mainFrame,"You have been invited to chat room by "+otherUser+"\nWill you accept it?", "INVITE_CHAT", JOptionPane.YES_NO_OPTION);
				
				if(result==JOptionPane.YES_OPTION)
				{
					chatRoomList.put(myInfo.getId()+"-"+otherUser, new ChatFrame(otherUser,mainFrame.getX()-420,mainFrame.getY()));
					outputStream.println("REQUEST_CHAT_ACCEPT");
				}
				else
				{
					outputStream.println("REQUEST_CHAT_REFUSE");
				}
			}
			
		}
		
	}
	
	public void runChatRoom()
	{
		//ChatFrame chatRoom=new ChatFrame();
		//chatRoom.setMain(this);
		//chatRoom.setVisible(true);
	}	
	
	public static void main(String[] args) {
		Scanner fileInput=null;
		String inputServerAddress="";
		int inputPortNum=0;
		
		try
		{
			fileInput=new Scanner(new File("serverinfo.dat"));
			inputServerAddress=fileInput.nextLine();
			inputPortNum=fileInput.nextInt();
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("There is no file : serverinfo.dat");
			System.out.println("default IP : localhost & default portNum : 35859");
			inputServerAddress="14.47.236.108";
			inputPortNum=35859;
		}
		
		//client object / connect server
		MessengerClient client=new MessengerClient(inputServerAddress,inputPortNum);
		client.connectServer();
		
		client.runLogin();
		
		client.runMain();
	}

}