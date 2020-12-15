import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessengerServer {
	//현재 접속해 있는 유저들 모음
	//접속 끊었을 때 집합에 있는 아이디 삭제
	private static Hashtable<String,UserInfo>users=new Hashtable<>();
	private static Encode encode;

	public static class Handler implements Runnable{
		//TCP연결에 필요한 변수
		private Socket socket;
		private Scanner inputStream;
		private PrintWriter outputStream;
		
		private UserInfo myInfo=null;
		private String userId="";
		
		//데이터베이스 연결에 필요한 변수
		private Connection connectDB=null;
		private ResultSet resSet=null;
		
		private PreparedStatement selectInfoStmt=null;//유저의 정보를 찾기 위한 statement
		private String selectInfo="select name, nickname, birth, email, phone, feel from user_info where id=?";
		private PreparedStatement selectPswStmt=null;//유저가 로그인할 때 비밀번호가 맞는지 확인하는 statement
		private String selectPsw="select password from user_login where id=?";
		private PreparedStatement selectFindIdStmt=null;//유저가 아이디 찾기를 원할 때 쓸 statement
		private String selectFindId="select id from user_info where name=? and birth=? and phone=?";
		private PreparedStatement selectIdCheckStmt=null;//유저가 회원가입할 때 중복 아이디를 입력했는지를 확인할 때 쓸 statement
		private String selectIdCheck="select id from user_info where id=?";
		private PreparedStatement selectNicknameStmt=null;//유저가 회원가입할 때 중복 닉네임을 입력했는지를 확인할 때 쓸 statement
		private String selectNickName="select nickname from user_info where nickname=?";
		private PreparedStatement selectFindPswStmt=null;//유저가 패스워드 찾기를 원할 때 쓸 statement 
		private String selectFindPsw="select password from user_login where id=(select id from user_info where id=? and name=? and question=? and answer=?)";
		private PreparedStatement selectFriendStmt=null;//유저의 친구 찾기를 원할 때 쓸 statement
		private String selectFriend="select friend from user_friend where id=?";
		private PreparedStatement selectUserIdStmt=null;
		private String selectUserId="select id from user_info where id like ?";

		private PreparedStatement insertInfoStmt=null;   //insert user_info
		private String insertInfo="insert into user_info values(?,?,?,?,?,?,?,?,?)";
		private PreparedStatement insertLoginStmt=null;   //insert user_login	
		private String insertLogin="insert into user_login values(?,?)";
		private PreparedStatement insertFriendStmt=null;   //insert user_friend
		private String insertFriend="insert into user_friend values(?,?)";	
		
		private PreparedStatement updateStatusStmt=null;   //update user_info
		
		private String updateStatus="update user_info set name=?, feel=? where id=?";
		
		//constructor
		public Handler(Socket socket)
		{
			this.socket=socket;
			connectDB=sqlConnect();
			
			try
			{
				encode=new Encode();
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		
		//sql연결 메소드
		public static Connection sqlConnect()
		{
			// Connection 객체를 자동완성으로 import할 때는 com.mysql.connection이 아닌
	        // java 표준인 java.sql.Connection 클래스를 import해야 한다.
	        Connection conn = null;

	        try{
	            // 1. 드라이버 로딩
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // 2. 연결하기
	            // mysql은 "jdbc:mysql://DB의 IP/사용할db이름" 이다.
	            String DBName="messenger";
	            String url = "jdbc:mysql://localhost:3306/"+DBName+"?serverTimezone=UTC";	       

	            // @param  getConnection(url, userName, password);
	            //이후 GUI를 이용해서 직접 password를 입력하도록 설정 할 수 있도록
	            conn = DriverManager.getConnection(url, "root", "12345");
	            
	            return conn;

	        }
	        catch(ClassNotFoundException e){
	            System.out.println("드라이버 로딩 실패");
	            return null;
	        }
	        catch(SQLException e){
	            System.out.println("에러: " + e);
	            return null;
	        }
		}
		
		@Override
		public void run() {
			try
			{				
				//select, insert, update를 위한 prepareStatement 생성	
				selectInfoStmt=connectDB.prepareStatement(selectInfo);
				selectPswStmt=connectDB.prepareStatement(selectPsw);
				selectIdCheckStmt=connectDB.prepareStatement(selectIdCheck);
				selectNicknameStmt=connectDB.prepareStatement(selectNickName);
				selectFindIdStmt=connectDB.prepareStatement(selectFindId);
				selectFindPswStmt=connectDB.prepareStatement(selectFindPsw);
				selectFriendStmt=connectDB.prepareStatement(selectFriend);
				selectUserIdStmt=connectDB.prepareStatement(selectUserId);
				
				insertInfoStmt=connectDB.prepareStatement(insertInfo);
				insertLoginStmt=connectDB.prepareStatement(insertLogin);
				insertFriendStmt=connectDB.prepareStatement(insertFriend);
				
				updateStatusStmt=connectDB.prepareStatement(updateStatus);
			}
			catch(SQLException sqle)
			{
				System.out.println("데이터베이스 에러");
				System.out.println(sqle.getMessage());
			}
			
			
			//input, output 스트림 생성
			try
			{
				//IOException 발생 가능성 있음
				inputStream=new Scanner(socket.getInputStream());
				outputStream=new PrintWriter(socket.getOutputStream(),true);
				
				//비밀번호 암호와 객체 생성
				Encode encode=new Encode();
				
				//로그인 창 처리
				while(true)
				{
					String id = null;
					String psw=null;
					String encodePsw=null;
					String name=null;
					String nickname=null;
					String birth=null;
					String email=null;
					String phone=null;
					int question=0;
					String answer=null;
					String feel=null;
					
					//로그인, 회원가입 입력받는 정보
					String tryMessage;
					
					tryMessage=inputStream.nextLine();
					
					//로그인 시도 요청 받음 (LOGIN_TRY)
					if(tryMessage.equals("LOGIN_TRY"))
					{
						//클라이언트로부터 아이디, 비번 입력 받음
						id=inputStream.nextLine();
						psw=inputStream.nextLine();
						encodePsw=encode.encrypt(psw);						
						
						selectPswStmt.setString(1, id);
						resSet=selectPswStmt.executeQuery();						
						boolean isNext=resSet.next();
						
						//아이디와 비번이 같다면 로그인 성공후 로그인창 종료
						if(isNext&&encodePsw.equals(resSet.getString(1))&&!users.containsKey(id))
						{
							System.out.println(id+" join server");

							//로그인 성공 알림
							outputStream.println("LOGIN_RESULT");
							outputStream.println("LOGIN_SUCCESS");
							
							//로그인한 유저의 정보 qeury
							selectInfoStmt.setString(1, id);
							resSet=selectInfoStmt.executeQuery();
							
							resSet.next();
							name=resSet.getString(1);
							nickname=resSet.getString(2);
							birth=resSet.getString(3);
							email=resSet.getString(4);
							phone=resSet.getString(5);
							feel=resSet.getString(6);
							
							//유저 객체에 저장
							myInfo=new UserInfo(id,name,nickname,birth,email,phone,feel,outputStream);
							userId=id;
							
							//클라이언트에게 유저 정보 보냄
							outputStream.println("SEND_INFO");
							outputStream.println(id);
							outputStream.println(name);
							outputStream.println(nickname);
							outputStream.println(birth);
							outputStream.println(email);
							outputStream.println(phone);
							outputStream.println(feel);
							
							//유저 친구 검색
							selectFriendStmt.setString(1, id);
							resSet=selectFriendStmt.executeQuery();
							
							//클라이언트에게 친구들 목록을 보냄
							outputStream.println("SEND_FRIEND");
							while(resSet.next())
							{
								outputStream.println(resSet.getString(1));
							}
							outputStream.println("SEND_FRIEND_END");
							
							//로그인 while문 종료
							break;
						}
						else if(users.containsKey(id))
						{
							outputStream.println("LOGIN_RESULT");
							outputStream.println("LOGIN_FAIL");
						}
						
						//아이디에 대한 데이터베이스가 없다면 아이디 없음을 알림
						//비번이 다르다면 비번 틀렸음을 알림
						else
						{
							outputStream.println("LOGIN_RESULT");
							outputStream.println("LOGIN_FAIL");
						}
					}
					
					//회원가입 중 아이디 중복 확인
					else if(tryMessage.equals("CHECK_ID"))
					{
						String check=inputStream.nextLine();
						boolean isOK=true;  //check id duplication
						
						selectIdCheckStmt.setString(1,check);
						resSet=selectIdCheckStmt.executeQuery();
						
						if(resSet.next())
							isOK=false;
						
						outputStream.println(isOK);
					}
					
					//회원가입 중 닉네임 중복확인
					else if(tryMessage.equals("CHECK_NICKNAME"))
					{
						String check=inputStream.nextLine();
						boolean isOK=true;
						
						selectNicknameStmt.setString(1,check);
						resSet=selectNicknameStmt.executeQuery();
						
						if(resSet.next())
							isOK=false;
						
						outputStream.println(isOK);
					}
					
					//회원가입 요청 받음 (REGISTER_TRY)
					else if(tryMessage.equals("REGISTER_TRY"))
					{
						//아이디,비번,이름,별명,이메일,전화번호,비번질문,답 입력 받음
						id=inputStream.nextLine();
						psw=inputStream.nextLine();
						encodePsw=encode.encrypt(psw);
						name=inputStream.nextLine();
						nickname=inputStream.nextLine();
						birth=inputStream.nextLine();
						email=inputStream.nextLine();
						phone=inputStream.nextLine();
						question=inputStream.nextInt(); inputStream.nextLine();						
						answer=inputStream.nextLine();
						feel=inputStream.nextLine();						
						
						//아이디,이름,별명,이메일,전화번호,비번질문,답은 user_info에 저장하도록함
						insertInfoStmt.setString(1, id);
						insertInfoStmt.setString(2, name);
						insertInfoStmt.setString(3, nickname);
						insertInfoStmt.setString(4, birth);
						insertInfoStmt.setString(5, email);
						insertInfoStmt.setString(6, phone);
						insertInfoStmt.setInt(7, question);
						insertInfoStmt.setString(8, answer);
						insertInfoStmt.setString(9, feel);
						
						//아이디,비번은 user_login에 저장하도록함
						insertLoginStmt.setString(1, id);
						insertLoginStmt.setString(2, encodePsw);
						
						//저장
						int count1=insertInfoStmt.executeUpdate();
						int count2=insertLoginStmt.executeUpdate();
						
						outputStream.println("REGISTER_RESULT");
						if(count1==0||count2==0)
							outputStream.println("REGISTER_FAIL");
						else
							outputStream.println("REGISTER_SUCCESS");
					}
					
					//아이디 찾기 요청 받음 (FIND_ID_TRY)
					else if(tryMessage.equals("FIND_ID_TRY"))
					{
						//이름과 전화번호를 입력받음
						name=inputStream.nextLine();
						birth=inputStream.nextLine();
						phone=inputStream.nextLine();
						
						selectFindIdStmt.setString(1, name);
						selectFindIdStmt.setString(2, birth);
						selectFindIdStmt.setString(3, phone);
						
						resSet=selectFindIdStmt.executeQuery();
						
						outputStream.println("FORGOT_ID_RESULT");
						if(resSet.next())
							outputStream.println("YOUR ID IS "+resSet.getString(1));
						else
							outputStream.println("THERE IS NO INFORMATION");
					}
					
					//비번 찾기 요청 받음 (FIND_PSW_TRY)
					else if(tryMessage.equals("FIND_PSW_TRY"))
					{
						//아이디,비번질문,답을 입력받음
						id=inputStream.nextLine();
						name=inputStream.nextLine();
						question=inputStream.nextInt(); inputStream.nextLine();
						answer=inputStream.nextLine();
						boolean isCorrespond=true;
						
						selectFindPswStmt.setString(1, id);
						selectFindPswStmt.setString(2, name);
						selectFindPswStmt.setInt(3, question);
						selectFindPswStmt.setString(4, answer);
						resSet=selectFindPswStmt.executeQuery();
						
						outputStream.println("FORGOT_PSW_RESULT");
						if(resSet.next())
							outputStream.println("YOUR PASSWORD IS "+encode.decrypt(resSet.getString(1)));						
						else
							outputStream.println("THERE IS NO INFORMATION");
					}
				}
				
				//메인창 들어가기 전 친구의 온라인/오프라인 구분
				while(true)
				{
					String getMessage=inputStream.nextLine();
					//유저의 친구가 온라인인지 아닌지를 알고싶어할 때
					if(getMessage.equals("CHECK_FRIEND_ID"))
					{
						if(users.containsKey(inputStream.nextLine()))
							outputStream.println("ONLINE");
							
						else
							outputStream.println("OFFLINE");							
					}
					
					else if(getMessage.equals("CHECK_FRIEND_END"))
						break;
						
				}
				
				//온라인 유저들에게 새 유저가 들어왔음을 알림
				for(UserInfo i:users.values())
				{
					PrintWriter p=i.getOutputStream();
					p.println("JOIN_USER");
					p.println(userId);
				}
				
				//접속하는 유저의 id,정보를 table에 추가
				users.put(userId,myInfo);
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				//메인창 처리
				while(true)
				{
					String tryMessage;
					tryMessage=inputStream.nextLine();
					
					//클라이언트가 종료를 요청하면 TCP연결 해제
					if(tryMessage.equals("QUIT"))
					{
						return;
					}					
					
					//유저의 정보를 업데이트 하기를 원할 때
					else if(tryMessage.equals("UPDATE_STATUS"))
					{
						String newName=inputStream.nextLine();
						String newFeel=inputStream.nextLine();
						
						updateStatusStmt.setString(1, newName);
						updateStatusStmt.setString(2,newFeel);
						updateStatusStmt.setString(3, userId);
						
						int r=updateStatusStmt.executeUpdate();
						
						if(r==0)
							System.out.println("Error : update failed");
						else
							System.out.println("Success update");
					}
					
					else if(tryMessage.equals("SEARCH_USER_ID"))
					{
						String searchId=inputStream.nextLine();
						
						selectUserIdStmt.setString(1, "%"+searchId+"%");
						resSet=selectUserIdStmt.executeQuery();
						
						outputStream.println("SEARCH_USER_ID_RESULT");
						while(resSet.next())
						{
							outputStream.println(resSet.getString(1));
						}
						outputStream.println("SEARCH_USER_ID_RESULT_END");
					}
					
					else if(tryMessage.equals("SEARCH_INFORMATION"))
					{
						String searchId=inputStream.nextLine();
						//로그인한 유저의 정보 qeury
						selectInfoStmt.setString(1, searchId);
						resSet=selectInfoStmt.executeQuery();
						
						resSet.next();
						outputStream.println("SEARCH_INFORMATION_RESULT");
						outputStream.println(searchId);
						outputStream.println(resSet.getString(1));
						outputStream.println(resSet.getString(2));
						outputStream.println(resSet.getString(3));
						outputStream.println(resSet.getString(4));
						outputStream.println(resSet.getString(5));
						outputStream.println(resSet.getString(6));
					}
					
					else if(tryMessage.equals("REQUEST_CHAT_ROOM"))
					{
						String chatId=inputStream.nextLine();
						outputStream.println("REQUEST_CHAT_ROOM_RESULT");
						if(users.containsKey(chatId))
						{
							PrintWriter receiver=users.get(chatId).getOutputStream();
							outputStream.println("REQUEST_CHAT_ROOM_CLEAR");
							outputStream.println(chatId);
							
							receiver.println("REQUEST_CHAT_ROOM");
							receiver.println(userId);
						}
						else
							
							outputStream.println("USER_OFFLINE");

							
					}
				}
			}
			catch(SQLException sqle)
			{
				System.out.println("데이터베이스 에러");
				System.out.println(sqle.getMessage());
			}
			catch (UnsupportedEncodingException encodee)
			{
				System.out.println("암호화 에러");
				System.out.println(encodee.getMessage());
				encodee.printStackTrace();
			}
			catch (NoSuchAlgorithmException encodee)
			{
				System.out.println("암호화 에러");
				System.out.println(encodee.getMessage());
				encodee.printStackTrace();
			}
			catch (GeneralSecurityException encodee)
			{
				System.out.println("암호화 에러");
				System.out.println(encodee.getMessage());
				encodee.printStackTrace();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			finally 
			{
				/*데이터베이스 연결 해제, 서버 소켓 닫기*/
				
				if (users.containsKey(userId)) {
					System.out.println(userId + " left");
					users.remove(userId);
					
					//온라인 유저들에게 유저가 나갔음을 알림
					for(UserInfo i:users.values())
					{
						PrintWriter p=i.getOutputStream();
						p.println("LEFT_USER");
						p.println(userId);
					}
				}
				try
				{
					if(resSet!=null)
						resSet.close();
					
					//statement 닫기
					
					if(connectDB!=null&&!connectDB.isClosed())
						connectDB.close();
				}
				catch (SQLException sqle)
				{
					System.out.println("데이터베이스 연결 해제에 문제가 발생했습니다.");
					System.out.println(sqle.getMessage());
				}
				
				try
				{
					socket.close();
				}
				catch(IOException ioe)
				{
					System.out.println("서버를 닫는데 문제가 발생했습니다.");
					System.out.println(ioe.getMessage());
				}				
			}
		}
	}
	
	//프로그램 실행
	public static void main(String[] args) throws Exception{
		System.out.println("The server is running...");
		System.out.println("Server IP address : ");
		System.out.println("Server port number : 35859");
		
		ExecutorService pool=Executors.newFixedThreadPool(500);
		
		try(ServerSocket listener=new ServerSocket(35859))
		{
			while(true)
			{
				pool.execute(new Handler(listener.accept()));
			}
			
		}

	}

}