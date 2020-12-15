import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MainFrame extends JFrame{
	private MessengerClient client;
	private UserInfo myInfo;
	
	private Map<String, ImageIcon> imageList;
	
	JPanel mainPanel;	
	
	JPanel searchPanel;
	JButton addFriendBtn;
	JTextField searchTxtField;
	JButton searchFriendBtn;
	JPanel profilePanel;
	JPanel profileImagePanel;
	JLabel profileImageLabel;
	JPanel profileStatusPanel;
	JLabel profileNameLabel;
	JLabel profileTodayLabel;
	JButton profileChangeBtn;
	JPanel profileStatusChangePanel;
	JTextField profileChangeNameTxtField;
	JTextField profileChangeTodayTxtFiled;
	JButton profileChangeClearBtn;
	
	JPanel searchFriendPanel;
	DefaultMutableTreeNode searchFriendRoot;
	JTree searchFriendList;
	JScrollPane searchFriendListScroll;
	
	JPanel searchUserPanel;
	DefaultMutableTreeNode searchUserRoot;
	JTree searchUserList;
	JScrollPane searchUserListScroll;
	
	JPanel friendPanel;
	JScrollPane friendListScroll;
	JTree friendList;
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode onlineFriends;
	DefaultMutableTreeNode offlineFriends;
	boolean isNoOnlineFriend=true;
	boolean isNoOfflineFriend=true;
	JPopupMenu listPopup;
	
	JPanel openDataPanel;
	JLabel openDataLabel;
	
	ImageIcon myProfileImage;
	ImageIcon onlineImage;
	ImageIcon offlineImage;
	ImageIcon settingImage;
	ImageIcon confirmImage;
	ImageIcon openImage;
	ImageIcon closeImage;

	public MainFrame(UserInfo info) {
		myInfo=info;  //유저 정보 담은 개체
		
		setTitle("MAIN PAGE");
		setResizable(false);					
		
		//메인 패널
		mainPanel=new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(400, 645));
		setContentPane(mainPanel);
		
		/**********profile section**********/
		//프로필 패널
		profilePanel = new JPanel();
		profilePanel.setBackground(Color.LIGHT_GRAY);
		profilePanel.setBounds(0, 0, 400, 120);
		mainPanel.add(profilePanel);
		profilePanel.setLayout(null);
		
		//프로필 이미지
		myProfileImage=changeImageSize(new ImageIcon(MainFrame.class.getResource("/image/user_online.png")),100,100);
		//프로필 상태(변경 버튼)
		settingImage=changeImageSize(new ImageIcon(MainFrame.class.getResource("/image/update.png")),30,30);
		//프로필 상태 변경 완료 버튼
		confirmImage=changeImageSize(new ImageIcon(MainFrame.class.getResource("/image/confirm.png")),30,30);
		
		//프로필 이미지 패널
		profileImagePanel = new JPanel();
		profileImagePanel.setBackground(Color.WHITE);
		profileImagePanel.setBounds(10, 10, 100, 100);
		profilePanel.add(profileImagePanel);
		profileImagePanel.setLayout(null);
		profileImageLabel = new JLabel(myProfileImage);
		profileImageLabel.setBounds(0, 0, 100, 100);
		profileImageLabel.setBackground(Color.BLACK);
		profileImagePanel.add(profileImageLabel);
		
		//프로필 상태 패널
		profileStatusPanel = new JPanel();
		profileStatusPanel.setBackground(Color.WHITE);
		profileStatusPanel.setBounds(120, 10, 270, 100);
		profilePanel.add(profileStatusPanel);
		profileStatusPanel.setLayout(null);
		
		//프로필 상태(이름)
		profileNameLabel = new JLabel(" "+myInfo.getName());
		profileNameLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		profileNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		profileNameLabel.setBounds(5, 5, 180, 40);
		profileNameLabel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		profileStatusPanel.add(profileNameLabel);
		
		//프로필 상태(기분)
		profileTodayLabel = new JLabel(" "+myInfo.getFeel());
		profileTodayLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
		profileTodayLabel.setHorizontalAlignment(SwingConstants.LEFT);
		profileTodayLabel.setBounds(5, 55, 260, 40);
		profileTodayLabel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		profileStatusPanel.add(profileTodayLabel);
		profileChangeBtn = new JButton(settingImage);
		profileChangeBtn.setBounds(205, 5, 60, 40);
		profileChangeBtn.addActionListener(new changeBtnHandler());
		profileStatusPanel.add(profileChangeBtn);
		
		//프로필 상태 변경 패널
		profileStatusChangePanel = new JPanel();
		profileStatusChangePanel.setLayout(null);
		profileStatusChangePanel.setBackground(Color.DARK_GRAY);
		profileStatusChangePanel.setBounds(120, 10, 270, 100);
		profileStatusChangePanel.setVisible(false);
		profilePanel.add(profileStatusChangePanel);
		
		//프로필 상태 변경(이름)
		profileChangeNameTxtField = new JTextField();
		profileChangeNameTxtField.setFont(new Font("Calibri", Font.PLAIN, 20));
		profileChangeNameTxtField.setHorizontalAlignment(SwingConstants.LEFT);
		profileChangeNameTxtField.setBounds(5, 5, 180, 40);
		profileChangeNameTxtField.setColumns(15);
		profileChangeNameTxtField.setEditable(false);
		profileStatusChangePanel.add(profileChangeNameTxtField);
		
		//프로필 상태 변경(기분)
		profileChangeTodayTxtFiled = new JTextField();
		profileChangeTodayTxtFiled.setFont(new Font("Calibri", Font.PLAIN, 17));
		profileChangeTodayTxtFiled.setHorizontalAlignment(SwingConstants.LEFT);
		profileChangeTodayTxtFiled.setBounds(5, 55, 260, 40);
		profileChangeTodayTxtFiled.setColumns(15);
		profileChangeTodayTxtFiled.setEditable(false);
		profileStatusChangePanel.add(profileChangeTodayTxtFiled);
		profileChangeClearBtn = new JButton(confirmImage);
		profileChangeClearBtn.setBounds(205, 5, 60, 40);
		profileChangeClearBtn.addActionListener(new changeBtnHandler());
		profileStatusChangePanel.add(profileChangeClearBtn);
		
		/**********search&add friend section**********/
		//친구찾기 패널
		searchPanel = new JPanel();
		searchPanel.setBounds(0, 120, 400, 40);
		searchPanel.setLayout(null);
		mainPanel.add(searchPanel);		
		
		//친구 추가 버튼
		addFriendBtn = new JButton();
		addFriendBtn.setFont(new Font("Calibri", Font.PLAIN, 15));
		addFriendBtn.setText("Add");
		addFriendBtn.setSize(100, 30);
		addFriendBtn.setLocation(5, 5);
		addFriendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(searchFriendPanel.isVisible())
				{
					addFriendBtn.setText("Add");
					searchTxtField.setText("");
					searchFriendPanel.setVisible(false);
					searchUserPanel.setVisible(false);
					friendPanel.setVisible(true);
				}
				
				else if(searchUserPanel.isVisible())
				{
					addFriendBtn.setText("Add");
					searchTxtField.setText("");
					searchUserPanel.setVisible(false);
					searchFriendPanel.setVisible(false);					
					friendPanel.setVisible(true);
				}
				
				else if(friendPanel.isVisible())
				{
					addFriendBtn.setText("Back");
					searchTxtField.setText("");
					friendPanel.setVisible(false);
					searchUserPanel.setVisible(true);
					searchFriendPanel.setVisible(false);
				}
			}			
		});
		searchPanel.add(addFriendBtn);	
		
		//친구 찾기 입력 창
		searchTxtField = new JTextField();
		searchTxtField.setFont(new Font("Calibri", Font.PLAIN, 18));
		searchTxtField.setLocation(130, 5);
		searchTxtField.setSize(230, 30);
		searchPanel.add(searchTxtField);
		
		//친구 찾기 버튼
		searchFriendBtn = new JButton();
		searchFriendBtn.setSize(30, 30);
		searchFriendBtn.setLocation(365, 5);
		searchFriendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(friendPanel.isVisible())
				{
					String searchId=searchTxtField.getText();
					searchTxtField.setText("");
					addFriendBtn.setText("Back");
					
					friendPanel.setVisible(false);
					searchFriendPanel.setVisible(true);
					searchFriendId(searchId);
				}
				
				else if(searchFriendPanel.isVisible())
				{
					String searchId=searchTxtField.getText();
					searchTxtField.setText("");
					addFriendBtn.setText("Back");
					
					searchFriendId(searchId);
				}
				
				else if(searchUserPanel.isVisible())
				{
					String searchId=searchTxtField.getText();
					searchTxtField.setText("");
					
					client.outputStream.println("SEARCH_USER_ID");
					client.outputStream.println(searchId);
				}
			}
		});
		searchPanel.add(searchFriendBtn);	
		
		
		//유저 아이디 검색 결과
		searchUserPanel = new JPanel();
		searchUserPanel.setBounds(0, 160, 400, 420);
		searchUserPanel.setLayout(null);
		mainPanel.add(searchUserPanel);
		
		searchUserRoot=new DefaultMutableTreeNode("User Search Result");
		searchUserRoot.add(new DefaultMutableTreeNode("no friends"));
		searchUserList=new JTree(searchUserRoot);
		searchUserList.setRowHeight(40);
		searchUserList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(searchUserList.getSelectionCount()==0)
				{
					searchUserList.clearSelection();
					return;
				}
				
				if(e.getButton()==e.BUTTON3)
				{
					Rectangle rect = searchUserList.getPathBounds(searchUserList.getSelectionPath());
					Point p = new Point(rect.x + rect.width / 2, rect.y + rect.height);
					listPopup.show(e.getComponent(),p.x,p.y);
				}
				
				else
				{
					String id=searchUserList.getSelectionPath().getLastPathComponent().toString();
					if(searchUserList.getSelectionPath().getLastPathComponent()==searchUserRoot)
						return;
					if(id.equals("no friends"))
						return;
					
					if(e.getClickCount()==2)
					{
						client.outputStream.println("SEARCH_INFORMATION");
						client.outputStream.println(id);
					}	
				}							
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}			
		});
		searchUserListScroll=new JScrollPane(searchUserList);
		searchUserListScroll.setBounds(0, 0, 400, 420);
		searchUserPanel.add(searchUserListScroll);
		searchUserPanel.setVisible(false);
		
		//친구 아이디 검색 결과
		searchFriendPanel = new JPanel();
		searchFriendPanel.setBounds(0, 160, 400, 420);
		searchFriendPanel.setLayout(null);
		mainPanel.add(searchFriendPanel);
		
		searchFriendRoot=new DefaultMutableTreeNode("Friend Search Result");
		searchFriendList=new JTree(searchFriendRoot);
		searchFriendList.setRowHeight(40);
		searchFriendList.setRootVisible(false);	
		searchFriendList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(searchFriendList.getSelectionCount()==0)
				{
					searchFriendList.clearSelection();
					return;
				}
				
				if(e.getButton()==e.BUTTON3)
				{
					Rectangle rect = searchFriendList.getPathBounds(searchFriendList.getSelectionPath());
					Point p = new Point(rect.x + rect.width / 2, rect.y + rect.height);
					listPopup.show(e.getComponent(),p.x,p.y);
				}
				
				else
				{
					String id=searchFriendList.getSelectionPath().getLastPathComponent().toString();
					if(searchFriendList.getSelectionPath().getLastPathComponent()==searchFriendRoot)
						return;
					if(id.equals("no friends"))
						return;
					
					if(e.getClickCount()==2)
					{
						client.outputStream.println("SEARCH_INFORMATION");
						client.outputStream.println(id);
					}	
				}							
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}			
		});
		searchFriendListScroll=new JScrollPane(searchFriendList);
		searchFriendListScroll.setBounds(0, 0, 400, 420);
		searchFriendPanel.add(searchFriendListScroll);
		searchFriendPanel.setVisible(false);
		
		//친구 목록 패널
		friendPanel = new JPanel();
		friendPanel.setBackground(Color.GRAY);
		friendPanel.setBounds(0, 160, 400, 420);
		mainPanel.add(friendPanel);
		friendPanel.setLayout(null);

		//친구 리스트
		root=new DefaultMutableTreeNode("root");
		onlineFriends=new DefaultMutableTreeNode("ONLINE");
		offlineFriends=new DefaultMutableTreeNode("OFFLINE");
		root.add(onlineFriends);
		root.add(offlineFriends);	
		
		friendList=new JTree(root);
		friendList.setRowHeight(40);
		friendList.setRootVisible(false);	
		friendList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(friendList.getSelectionCount()==0)
				{
					friendList.clearSelection();
					return;
				}
					
				if(friendList.getSelectionPath().getLastPathComponent()==onlineFriends||friendList.getSelectionPath().getLastPathComponent()==offlineFriends)
				{
					friendList.clearSelection();
					return;
				}
				if(friendList.getSelectionPath().getLastPathComponent().toString().equals("no friends"))
				{
					friendList.clearSelection();
					return;
				}
				
				if(e.getButton()==e.BUTTON3)
				{
					Rectangle rect = friendList.getPathBounds(friendList.getSelectionPath());
					Point p = new Point(rect.x + rect.width / 2, rect.y + rect.height);
					listPopup.show(e.getComponent(),p.x,p.y);
				}
				
				else
				{
					String id=friendList.getSelectionPath().getLastPathComponent().toString();
					if(id.equals("no friends"))
						return;
					
					if(e.getClickCount()==2)
					{
						client.outputStream.println("SEARCH_INFORMATION");
						client.outputStream.println(id);
					}	
				}							
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}			
		});
		friendListScroll=new JScrollPane(friendList);
		friendListScroll.setBounds(0, 0, 400, 420);
		friendPanel.add(friendListScroll);
		
		//친구 리스트 이미지
		onlineImage=changeImageSize(new ImageIcon(MainFrame.class.getResource("/image/user_online.png")),40,40);
		offlineImage=changeImageSize(new ImageIcon(MainFrame.class.getResource("/image/user_offline.png")),40,40);
		openImage=changeImageSize(new ImageIcon(MainFrame.class.getResource("/image/nodeOpen.png")),20,20);
		closeImage=changeImageSize(new ImageIcon(MainFrame.class.getResource("/image/nodeClose.png")),20,20);
		
		//친구 리스트 이미지 설정
		DefaultTreeCellRenderer dt=new DefaultTreeCellRenderer();
		dt.setOpenIcon(openImage);
		dt.setClosedIcon(closeImage);
		dt.setLeafIcon(onlineImage);
		friendList.setCellRenderer(dt);
		searchFriendList.setCellRenderer(dt);
		searchUserList.setCellRenderer(dt);
		
		//친구리스트에서 우클릭시 뜨는 팝업
		listPopup = new JPopupMenu();
		listMenu();
		//listPopup.addPopupMenuListener(new popupHandler());
		//friendList.setComponentPopupMenu(listPopup);
		//friendPanel.add(listPopup);		
		

		/********공공데이터********/
		//공공데이터 패널
		openDataPanel = new JPanel();
		openDataPanel.setBackground(Color.YELLOW);
		openDataPanel.setBounds(0, 580, 400, 65);
		openDataPanel.setLayout(null);
		mainPanel.add(openDataPanel);
		
		//공공데이터 라벨
		openDataLabel = new JLabel("1000");
		openDataLabel.setBackground(Color.WHITE);
		openDataLabel.setFont(new Font("Arial", Font.BOLD, 25));
		openDataLabel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Today's confirmed number", TitledBorder.CENTER, TitledBorder.TOP, new Font("Calibri", Font.BOLD, 20), new Color(0, 0, 0)));
		openDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
		openDataLabel.setBounds(0, 0, 400, 65);
		openDataPanel.add(openDataLabel);

		//프레임의 크기를 컴포넌트에 맞추고 가운데 생성하도록 설정
		pack();
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//윈도우 창 닫기를 눌렀을 때 동작
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				int confirmed = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit the program?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);
				
				if (confirmed == JOptionPane.YES_OPTION)
				{
					client.outputStream.println("QUIT");
					System.exit(1);
					}
				else
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				}
		});
	}
	
	private class popupHandler implements PopupMenuListener
	{
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			if(friendList.getLastSelectedPathComponent()==onlineFriends||friendList.getLastSelectedPathComponent()==offlineFriends)
			{
				friendList.clearSelection();
				return;
			}
        		
        	
            Rectangle rect = friendList.getPathBounds(friendList.getSelectionPath());
            Arrays.stream(listPopup.getComponents()).forEach(c -> c.setEnabled(rect != null));
            if (rect == null)
                return;
            
            Point p = new Point(rect.x + rect.width / 2, rect.y + rect.height);
            SwingUtilities.convertPointToScreen(p, friendList);
            listPopup.setLocation(p.x, p.y);
		}
		
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			friendList.clearSelection();
			
		}

		public void popupMenuCanceled(PopupMenuEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	
	
	//상태(정보) 변경 버튼 눌렀을 때
	private class changeBtnHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==profileChangeBtn)
			{
				profileStatusPanel.setVisible(false);
				profileStatusChangePanel.setVisible(true);
				
				profileChangeNameTxtField.setText(profileNameLabel.getText());
				profileChangeTodayTxtFiled.setText(profileTodayLabel.getText());
				profileChangeNameTxtField.setEditable(true);
				profileChangeTodayTxtFiled.setEditable(true);
			}
			
			else if(e.getSource()==profileChangeClearBtn)
			{
				int result=JOptionPane.showConfirmDialog(null,"Do you want to update?","Confirm",JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION)
				{
					profileStatusChangePanel.setVisible(false);
					profileStatusPanel.setVisible(true);
					
					profileChangeNameTxtField.setEditable(false);
					profileChangeTodayTxtFiled.setEditable(false);
					
					//update name, today's feel
					if(profileChangeNameTxtField.getText().trim().length()>=15)
					{
						JOptionPane.showMessageDialog(null, "enter name with no more than 15 characters.");
						return;
					}
					if(profileChangeTodayTxtFiled.getText().trim().length()>=25)
					{
						JOptionPane.showMessageDialog(null, "enter Status message with no more than 25 characters.");
						return;
					}
						
					client.outputStream.println("UPDATE_STATUS");
					client.outputStream.println(profileChangeNameTxtField.getText().trim());
					client.outputStream.println(profileChangeTodayTxtFiled.getText().trim());
					
					myInfo.setName(profileChangeNameTxtField.getText().trim());
					myInfo.setFeel(profileChangeTodayTxtFiled.getText().trim());
					
					profileNameLabel.setText(" "+profileChangeNameTxtField.getText().trim());
					profileTodayLabel.setText(" "+profileChangeTodayTxtFiled.getText().trim());
					
					profileChangeNameTxtField.setText("");
					profileChangeTodayTxtFiled.setText("");
				}
				else
				{
					profileStatusChangePanel.setVisible(false);
					profileStatusPanel.setVisible(true);
					
					profileChangeNameTxtField.setEditable(false);
					profileChangeTodayTxtFiled.setEditable(false);
					
					profileChangeNameTxtField.setText("");
					profileChangeTodayTxtFiled.setText("");
				}
			}
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
	
	//우클릭 팝업 메뉴창
	private void listMenu()
	{
		JMenuItem item1 = new JMenuItem("Chat");
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=friendList.getSelectionPath().getLastPathComponent().toString();
				client.outputStream.println("REQUEST_CHAT_ROOM");
				client.outputStream.println(id);
				friendList.clearSelection();
			}
		});
		JMenuItem item2 = new JMenuItem("Information");
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=friendList.getSelectionPath().getLastPathComponent().toString();
				client.outputStream.println("SEARCH_INFORMATION");
				client.outputStream.println(id);
				friendList.clearSelection();
			}
		});
		JMenuItem item3 = new JMenuItem("Delete");
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFriend();
				friendList.clearSelection();
			}
		});
	    
	    listPopup.add(item1);
	    listPopup.addSeparator();
	    listPopup.add(item2);
	    listPopup.addSeparator();
	    listPopup.add(item3);
	}
	
	//친구 온라인인지 오프라인인지 확인후 나눔
	public void insertFriend()
	{		
		onlineFriends.add(new DefaultMutableTreeNode("no friends"));
		offlineFriends.add(new DefaultMutableTreeNode("no friends"));
	
		//등록된 친구수만큼 반복
		for(String f: myInfo.getFriend())
		{
			//친구 아이디가 온라인인지 아닌지를 확인
			client.outputStream.println("CHECK_FRIEND_ID");
			client.outputStream.println(f);
			
			//온라인이면 온라인 모델에 추가
			if(client.inputStream.nextLine().equals("ONLINE"))
			{
				if(isNoOnlineFriend==true)
				{
					onlineFriends.remove(0);
					isNoOnlineFriend=false;
				}
					
				onlineFriends.add(new DefaultMutableTreeNode(f));
			}
			
			//오프라인이라면 오프라인 모델에 추가
			else
			{
				if(isNoOfflineFriend==true)
				{
					offlineFriends.remove(0);
					isNoOfflineFriend=false;
				}
					
				offlineFriends.add(new DefaultMutableTreeNode(f));
			}
		}
		
		client.outputStream.println("CHECK_FRIEND_END");
		
		friendList.expandRow(1);
		friendList.expandRow(0);		
	}
	
	public void deleteFriend()
	{
		
	}
	
	public void gotoOnline(String f)
	{
		boolean isOnlineOpen=friendList.isExpanded(0);
		boolean isOfflineOpen=isOnlineOpen?friendList.isExpanded(onlineFriends.getChildCount()+1):friendList.isExpanded(1);
		
		DefaultTreeModel model=(DefaultTreeModel) friendList.getModel();
		TreeNode offlineNode=null;
		DefaultMutableTreeNode joinedUser=null;
		
		//0:온라인 1:오프라인
		offlineNode=root.getChildAt(1);
		
		for(int i=0;i<offlineFriends.getChildCount();i++)
		{

			joinedUser=(DefaultMutableTreeNode) offlineFriends.getChildAt(i);
			
			if(joinedUser==null)
				break;
			
			if(joinedUser.toString().equals(f))
			{
				if(onlineFriends.getChildAt(0).toString().equals("no friends"))
					onlineFriends.remove(0);
				
				onlineFriends.add(joinedUser);
				
				if(offlineFriends.getChildCount()==0)
					offlineFriends.add(new DefaultMutableTreeNode("no friends"));
				
				model.reload(root);
				
				if(isOfflineOpen)
					friendList.expandRow(1);
				
				if(isOnlineOpen)
					friendList.expandRow(0);
				
				break;
			}
		}
		
	}
	
	public void gotoOffline(String f)
	{
		boolean isOnlineOpen=friendList.isExpanded(0);
        boolean isOfflineOpen=isOnlineOpen?friendList.isExpanded(onlineFriends.getChildCount()+1):friendList.isExpanded(1);
        
        DefaultTreeModel model=(DefaultTreeModel) friendList.getModel();
        TreeNode onlineNode=null;
        DefaultMutableTreeNode leftUser=null;
        
        //child 0:온라인 1:오프라인
        onlineNode=root.getChildAt(1);
        for(int i=0;i<onlineFriends.getChildCount();i++)
        {
           leftUser=(DefaultMutableTreeNode) onlineFriends.getChildAt(i);
           
           if(leftUser==null)
              break;
           
           if(leftUser.toString().equals(f))
           {
              if(offlineFriends.getChildAt(0).toString().equals("no friends"))
                 offlineFriends.remove(0);
              
              offlineFriends.add(leftUser);
              
              if(onlineFriends.getChildCount()==0)
                 onlineFriends.add(new DefaultMutableTreeNode("no friends"));
              
              model.reload(root);
              if(isOfflineOpen)
                   friendList.expandRow(1);
              if(isOnlineOpen)
                 friendList.expandRow(0);

              break;
           }
        }
	}
	
	public void searchFriendId(String searchKey)
	{	
		DefaultTreeModel model=(DefaultTreeModel) searchFriendList.getModel();
		searchFriendRoot.removeAllChildren();
		
		for(int i=0;i<onlineFriends.getChildCount();i++)
		{
			String searchId=onlineFriends.getChildAt(i).toString();
			if(!searchId.equals("no friends")&&searchId.contains(searchKey))
				searchFriendRoot.add(new DefaultMutableTreeNode(searchId));
		}
		
		for(int i=0;i<offlineFriends.getChildCount();i++)
		{
			String searchId=offlineFriends.getChildAt(i).toString();
			if(!searchId.equals("no friends")&&searchId.contains(searchKey))
				searchFriendRoot.add(new DefaultMutableTreeNode(searchId));
		}
		
		if(searchFriendRoot.getChildCount()==0)
			searchFriendRoot.add(new DefaultMutableTreeNode("no friends"));
		
		model.reload(searchFriendRoot);
		searchFriendList.setRootVisible(true);
	}
	
	public void searchUserId(ArrayList<String> users)
	{
		DefaultTreeModel model=(DefaultTreeModel) searchUserList.getModel();
		searchUserRoot.removeAllChildren();
		
		if(users.isEmpty())
			searchUserRoot.add(new DefaultMutableTreeNode("no friends"));
		
		for(String id:users)
		{
			if(id.equals(myInfo.getId()))
				continue;
			
			searchUserRoot.add(new DefaultMutableTreeNode(id));
		}
		
		model.reload(searchUserRoot);
		searchUserList.setRootVisible(true);
	}
	public int requestChat(String id)
	{
		int confirmed=JOptionPane.showConfirmDialog(null,"You have been invited to chat room by "+id+"\nWill you accept it?", "INVITE_CHAT", JOptionPane.YES_NO_OPTION);
		
		return confirmed;
	}
	
	public void parse()
	{
		String serviceKey = "cokwJf%2BgicpbmpHxhr%2BICIzLWmnyEwglld%2BNLYZRzxPKfv3yQPtc6tYEUouXu%2BZGzUfQhZ5vMncRyvbCQxfA%2BA%3D%3D"; // 서비스키
	      
	      //오늘날짜를 받아옴
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        Calendar c1 = Calendar.getInstance();
	        String strToday = sdf.format(c1.getTime());
	      
	        StringBuilder urlBuilder2 = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
	        try {
	         urlBuilder2.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey);
	           urlBuilder2.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	           urlBuilder2.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	           urlBuilder2.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(strToday, "UTF-8")); /*검색할 생성일 범위의 시작*/
	           urlBuilder2.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(strToday, "UTF-8")); /*검색할 생성일 범위의 종료*/
	           URL url2 = new URL(urlBuilder2.toString());
	           URLConnection conn2 = url2.openConnection();
	           System.out.println(url2);
	         conn2.connect();
	         Document doc2 = null;
	         InputSource is2 = new InputSource(conn2.getInputStream());
	         doc2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is2);
	         Element root2 = doc2.getDocumentElement();
	         NodeList all2 = root2.getElementsByTagName("item");
	         for (int i = 0; i < all2.getLength(); i++) {   
	            Node item = all2.item(i);            
	            Node careCnt = item.getFirstChild().getNextSibling().getNextSibling().getNextSibling();
	            Node clearCnt = careCnt.getNextSibling();      
	            Node deathCnt = clearCnt.getNextSibling().getNextSibling();            
	            Node decideCnt = deathCnt.getNextSibling();

	         }
	      } catch (UnsupportedEncodingException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      } /*Service Key*/ catch (MalformedURLException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      } catch (IOException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      } catch (SAXException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      } catch (ParserConfigurationException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      }
	}
	
	//connect with client
	public void setMain(MessengerClient c) {
		client=c;	
	}
}