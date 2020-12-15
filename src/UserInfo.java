import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class UserInfo {
	private String id;
	private String name;
	private String nickname;
	private String birth;
	private String email;
	private String phone;
	private String feel;
	
	private HashSet<String>friends;
	
	private PrintWriter userOutputStream;
	
	public UserInfo(String inputId, String inputName, String inputNickName, String inputBirth, String inputEmail, String inputPhone, String inputFeel, PrintWriter output)
	{
		id=inputId;
		name=inputName;
		nickname=inputNickName;
		birth=inputBirth;
		email=inputEmail;
		phone=inputPhone;
		feel=inputFeel;
		userOutputStream=output;
		
		friends=new HashSet<String>();
	}
	
	public UserInfo(String inputId, String inputName, String inputNickName, String inputBirth, String inputEmail, String inputPhone, String inputFeel)
	{
		id=inputId;
		name=inputName;
		nickname=inputNickName;
		birth=inputBirth;
		email=inputEmail;
		phone=inputPhone;
		feel=inputFeel;
		userOutputStream=null;
		
		friends=new HashSet<String>();
	}
	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public String getBirth()
	{
		return birth;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getFeel()
	{
		return feel;
	}
	
	public PrintWriter getOutputStream()
	{
		return userOutputStream;
	}
	
	public void addFriend(String newFriend)
	{
		friends.add(newFriend);
	}
	
	public HashSet<String> getFriend()
	{
		return friends;
	}
	
	public void setName(String newName)
	{
		name=newName;
	}
	
	public void setFeel(String newFeel)
	{
		feel=newFeel;
	}
}
