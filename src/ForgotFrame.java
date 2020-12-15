import java.awt.Button;
import java.awt.Choice;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ForgotFrame extends JFrame {
   private MessengerClient client;
   
   JPanel contentPane;
   Button buttonBack;   
    JTextField textFieldName;
    JTextField textFieldPH1;
    JTextField textFieldPH2;
    JTextField textFieldPH3;
    JTextField textFieldN;
    JTextField textFieldID;
    JTextField textField_6;
    JTextField textFieldAns;
    JPanel panelID;
    JLabel lblShowID;
    JLabel lblWName;
    JLabel lblSBD;
    JLabel lblWPH;
    JLabel lblPHd1;
    JLabel lblPHd2;
    JButton btnFinalCon;
    JPanel panelPW;
    JLabel lblShowPW;
    JLabel lblWName2;
    JLabel lblWID2;   
    JLabel lblSelQ;
    Choice choice_hint;   
    JLabel lblWAns;   
    JButton btnFinalCon2;
    JPanel panelSouth;
    JLabel lblresultShow;
    JLabel lblResult;
    JLabel lblYear;
    JLabel lblMonth;
    JLabel lblDay;
    JComboBox comboBoxY;
    JComboBox comboBoxM;
    JComboBox comboBoxD;
   
   /**
    * Create the frame.
    */
    public ForgotFrame() {      
       setTitle("Forgot ID/PW?");
       setResizable(false);
       
       contentPane = new JPanel();
       contentPane.setPreferredSize(new Dimension(550,510));
       contentPane.setBackground(Color.WHITE);
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       setContentPane(contentPane);
       contentPane.setLayout(null);
       
       JPanel panelNorth = new JPanel();
       panelNorth.setBackground(Color.WHITE);
       panelNorth.setBounds(0, 0, 534, 52);
       contentPane.add(panelNorth);
       panelNorth.setLayout(null);
       
       buttonBack = new Button("Back to login");
       buttonBack.setFont(new Font("Calibri", Font.BOLD, 17));
       buttonBack.setBounds(0, 0, 138, 28);
       buttonBack.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             client.forgotFrame.setVisible(false);
             client.loginFrame.setVisible(true);
             }
          });
       panelNorth.add(buttonBack);
       
       JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
       tabbedPane.setFont(new Font("Calibri", Font.PLAIN, 17));
       tabbedPane.setBounds(10, 62, 530, 356);
       contentPane.add(tabbedPane);  

         
       panelID = new JPanel();
       tabbedPane.addTab("ID", null, panelID, null);
       panelID.setLayout(null);  

       lblShowID = new JLabel("Did you forget your ID?");
       lblShowID.setForeground(Color.RED);
       lblShowID.setFont(new Font("Calibri", Font.BOLD, 17));
       lblShowID.setBounds(12, 10, 170, 30);
       panelID.add(lblShowID);
       
       lblWName = new JLabel("Write your name");
       lblWName.setFont(new Font("Calibri", Font.BOLD, 17));
       lblWName.setBounds(12, 70, 170, 30);
       panelID.add(lblWName);
       

       lblSBD = new JLabel("Select your birthday");
       lblSBD.setFont(new Font("Calibri", Font.BOLD, 17));
       lblSBD.setBounds(12, 131, 170, 30);
       panelID.add(lblSBD); 

       lblWPH = new JLabel("Write your phone number");
       lblWPH.setFont(new Font("Calibri", Font.BOLD, 17));
       lblWPH.setBounds(12, 190, 190, 30);
       panelID.add(lblWPH);
       
      
       textFieldName = new JTextField();
       textFieldName.setFont(new Font("Calibri", Font.PLAIN, 17));
       textFieldName.setBounds(210, 71, 285, 30);
       panelID.add(textFieldName);
       textFieldName.setColumns(10);
      
       textFieldPH1 = new JTextField();   
       textFieldPH1.setHorizontalAlignment(SwingConstants.CENTER);
       textFieldPH1.setFont(new Font("Calibri", Font.PLAIN, 17));
       textFieldPH1.setColumns(10);
       textFieldPH1.setBounds(210, 190, 79, 30);
       panelID.add(textFieldPH1);
      
       textFieldPH2 = new JTextField();
       textFieldPH2.setHorizontalAlignment(SwingConstants.CENTER);
       textFieldPH2.setFont(new Font("Calibri", Font.PLAIN, 17));
       textFieldPH2.setColumns(10);
       textFieldPH2.setBounds(313, 191, 79, 30);
       panelID.add(textFieldPH2);
      
       textFieldPH3 = new JTextField();
       textFieldPH3.setHorizontalAlignment(SwingConstants.CENTER);
       textFieldPH3.setFont(new Font("Calibri", Font.PLAIN, 17));
       textFieldPH3.setColumns(10);
       textFieldPH3.setBounds(416, 191, 79, 30);
       panelID.add(textFieldPH3);
       
       lblPHd1 = new JLabel("-");
       lblPHd1.setHorizontalAlignment(SwingConstants.CENTER);
       lblPHd1.setFont(new Font("Calibri", Font.BOLD, 20));
       lblPHd1.setBounds(296, 190, 10, 30);
       panelID.add(lblPHd1);

       lblPHd2 = new JLabel("-");
       lblPHd2.setHorizontalAlignment(SwingConstants.CENTER);
       lblPHd2.setFont(new Font("Calibri", Font.BOLD, 20));
       lblPHd2.setBounds(399, 190, 10, 30);
       panelID.add(lblPHd2);

       btnFinalCon = new JButton("Confirm");
       btnFinalCon.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             String name = textFieldName.getText();
             String birth = comboBoxY.getSelectedItem().toString() + comboBoxM.getSelectedItem().toString() + comboBoxD.getSelectedItem().toString();
             String phone = textFieldPH1.getText() + textFieldPH2.getText() + textFieldPH3.getText();
             
             textFieldName.setText("");
             comboBoxY.setSelectedIndex(0);
             comboBoxM.setSelectedIndex(0);
             comboBoxD.setSelectedIndex(0);
             textFieldPH1.setText("");
             textFieldPH2.setText("");
             textFieldPH3.setText("");
             
             client.outputStream.println("FIND_ID_TRY");
             client.outputStream.println(name);
             client.outputStream.println(birth);
             client.outputStream.println(phone);
          }
       });
       btnFinalCon.setFont(new Font("Calibri", Font.BOLD, 17));
       btnFinalCon.setBounds(361, 261, 134, 50);
       panelID.add(btnFinalCon);
       

       lblYear = new JLabel("YEAR");
       lblYear.setHorizontalAlignment(SwingConstants.CENTER);
       lblYear.setFont(new Font("Calibri", Font.BOLD, 17));
       lblYear.setBounds(210, 115, 79, 30);   
       panelID.add(lblYear);
       
       lblMonth = new JLabel("MONTH");
       lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
       lblMonth.setFont(new Font("Calibri", Font.BOLD, 17));
       lblMonth.setBounds(313, 115, 79, 30);
       panelID.add(lblMonth);
       
       lblDay = new JLabel("DAY");
       lblDay.setHorizontalAlignment(SwingConstants.CENTER);
       lblDay.setFont(new Font("Calibri", Font.BOLD, 17));
       lblDay.setBounds(416, 115, 79, 30);
       panelID.add(lblDay);
      
       comboBoxY = new JComboBox();
       comboBoxY.setModel(new DefaultComboBoxModel(new String[] {"1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004"}));
       comboBoxY.setBounds(210, 145, 79, 30);
       panelID.add(comboBoxY);
       
       comboBoxM = new JComboBox();
       comboBoxM.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
       comboBoxM.setBounds(313, 145, 79, 30);
       panelID.add(comboBoxM);
      
       comboBoxD = new JComboBox();
       comboBoxD.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
       comboBoxD.setBounds(416, 145, 79, 30);
       panelID.add(comboBoxD);
      

       //panel of PW
       panelPW = new JPanel();
       tabbedPane.addTab("P/W", null, panelPW, null);
       panelPW.setLayout(null);

       lblShowPW = new JLabel("Did you forget your PassWord?");
       lblShowPW.setForeground(Color.RED);
       lblShowPW.setFont(new Font("Calibri", Font.BOLD, 17));
       lblShowPW.setBounds(12, 10, 230, 30);
       panelPW.add(lblShowPW);
       
       lblWName2 = new JLabel("Write your name");
       lblWName2.setFont(new Font("Calibri", Font.BOLD, 17));
       lblWName2.setBounds(12, 70, 170, 30);
       panelPW.add(lblWName2);

       textFieldN = new JTextField();      
       textFieldN.setFont(new Font("Calibri", Font.PLAIN, 17));
       textFieldN.setColumns(10);
       textFieldN.setBounds(210, 71, 285, 30);
       panelPW.add(textFieldN);
      
       lblWID2 = new JLabel("Write your ID");
       lblWID2.setFont(new Font("Calibri", Font.BOLD, 17));
       lblWID2.setBounds(12, 110, 170, 30);
       panelPW.add(lblWID2);

       textFieldID = new JTextField();
       textFieldID.setFont(new Font("Calibri", Font.PLAIN, 17));
       textFieldID.setColumns(10);
       textFieldID.setBounds(210, 111, 285, 30);
       panelPW.add(textFieldID);
       
       lblSelQ = new JLabel("Select the question");
       lblSelQ.setFont(new Font("Calibri", Font.BOLD, 17));
       lblSelQ.setBounds(12, 152, 170, 27);
       panelPW.add(lblSelQ);
      
       choice_hint = new Choice();
       choice_hint.setFont(new Font("Calibri", Font.PLAIN, 17));
       choice_hint.add("What is your favorite character?");
       choice_hint.add("What is your no.1 treasure?");
       choice_hint.add("What is your best friend's name?");
       choice_hint.setBounds(210, 152, 285, 30);
       panelPW.add(choice_hint);
      
       lblWAns = new JLabel("Write the answer");
       lblWAns.setFont(new Font("Calibri", Font.BOLD, 17));
       lblWAns.setBounds(12, 192, 170, 30);
       panelPW.add(lblWAns);

       textFieldAns = new JTextField();
       textFieldAns.setFont(new Font("Calibri", Font.PLAIN, 17));
       textFieldAns.setColumns(10);
       textFieldAns.setBounds(210, 189, 285, 30);
       panelPW.add(textFieldAns);

       btnFinalCon2 = new JButton("Confirm");
       btnFinalCon2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             String name = textFieldN.getText();
             String id = textFieldID.getText();
             int question = choice_hint.getSelectedIndex()+1;
             String answer = textFieldAns.getText();


             textFieldN.setText("");
             textFieldID.setText("");
             textFieldAns.setText("");
             
             client.outputStream.println("FIND_PSW_TRY");
             client.outputStream.println(id);
             client.outputStream.println(name);
             client.outputStream.println(question);
             client.outputStream.println(answer);
          }
       });
       btnFinalCon2.setFont(new Font("Calibri", Font.BOLD, 17));
       btnFinalCon2.setBounds(361, 261, 134, 50);
       panelPW.add(btnFinalCon2);
      
      
       //panel of south
      
       panelSouth = new JPanel();
       panelSouth.setBackground(Color.WHITE);
       panelSouth.setBounds(0, 428, 550, 83);
       contentPane.add(panelSouth);
       panelSouth.setLayout(null);

       lblresultShow = new JLabel("RESULT : ");
       lblresultShow.setFont(new Font("Calibri", Font.BOLD, 25));
       lblresultShow.setBounds(10, 33, 160, 25);
       panelSouth.add(lblresultShow);

       lblResult = new JLabel("");
       lblResult.setBorder(new LineBorder(Color.black,2));
       lblResult.setHorizontalAlignment(SwingConstants.CENTER);
       lblResult.setBackground(Color.WHITE);
       lblResult.setFont(new Font("Calibri", Font.BOLD, 17));
       lblResult.setBounds(120, 10, 420, 63);
       panelSouth.add(lblResult);
       
       pack();
       setLocationRelativeTo(null);
       
       addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent e)
           {
             int confirmed = JOptionPane.showConfirmDialog(null, 
                 "Do you want to return to the login page?", "FORGOT ID/PW PAGE",
                 JOptionPane.YES_NO_OPTION);

             if (confirmed == JOptionPane.YES_OPTION)
             {
               setVisible(false);
             }
             else
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
           }
      });
    }
    
    public void setMain(MessengerClient c) {
      client=c;      
   }
}