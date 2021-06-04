import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class commentPanel extends JPanel {
    private JPanel title = new JPanel(); // ���� �Է��� ���� �г�
    private JPanel fileButton = new JPanel(); // ÷������ ��ư�� ���� �г�
    private JPanel content = new JPanel(); // ���� �Է��� ���� �г�
    private JPanel fileName = new JPanel(); // ÷���� ������ �����ֱ� ���� �г�
    private JPanel attachedPane = new JPanel();
    
    JLabel stageTitle= new JLabel("No stage");
    TextArea contentText = new TextArea("Enter the content here", 28, 55);
 
    // �̹��� ������ ���� ���� ��� ����
    File file = new File(".");
    String path = file.getPath()+"\\image\\";

    // �̹��� ������ �ҷ��� �������� �����ϰ� �迭�� ����
    ImageIcon [] images = { new ImageIcon(path+"image.png"), new ImageIcon(path+"video.png"),
            new ImageIcon(path+"pdf.png"), new ImageIcon(path+"voice.png")};

    // ÷������ ������ ���� ��ư
    JButton image_btn = new JButton(images[0]);
    JButton video_btn = new JButton(images[1]);
    JButton pdf_btn = new JButton(images[2]);
    JButton voice_btn = new JButton(images[3]);
    
  //÷�ε� ������ �����ֱ� ���� ������Ʈ
  	private DefaultListModel<String> fileNameListModel = new DefaultListModel<String>();
  	private JList fileNameList = new JList(fileNameListModel);
	
  	
    public commentPanel() {
        MyActionListener actionListener = new MyActionListener();

        // title �г� ����
        title.add(new JLabel("Title: "));
        title.add(stageTitle);

        
        // button �г� ����
        fileButton.add(image_btn);
        fileButton.add(video_btn);
        fileButton.add(pdf_btn);
        fileButton.add(voice_btn);
        fileButton.setBackground(Color.WHITE);
   
        image_btn.setBackground(Color.LIGHT_GRAY);
        video_btn.setBackground(Color.LIGHT_GRAY);
        pdf_btn.setBackground(Color.LIGHT_GRAY);
        voice_btn.setBackground(Color.LIGHT_GRAY);
        
        image_btn.setPreferredSize(new Dimension(80, 50));
        video_btn.setPreferredSize(new Dimension(80, 50));
        pdf_btn.setPreferredSize(new Dimension(80, 50));
        voice_btn.setPreferredSize(new Dimension(80, 50));
        
        image_btn.addActionListener(actionListener);
        video_btn.addActionListener(actionListener);
        pdf_btn.addActionListener(actionListener);
        voice_btn.addActionListener(actionListener);

        // content �г� ����
        content.add(contentText);
        contentText.addFocusListener(new FocusListener() { // textArea Ŭ�� �� �ʱ� ������ �����
            public void focusLost(FocusEvent e) {}
            public void focusGained(FocusEvent e) {
            	if (contentText.getText().equals("Enter the content here")) {
            		contentText.setText("");
            	}
            }
        });

        // fileName �г� ����
        fileName.setLayout(new BorderLayout());
    	fileName.add(new JLabel("Attached files"), BorderLayout.NORTH);
    	fileName.setBorder(new LineBorder(Color.LIGHT_GRAY));
		fileNameList.setPreferredSize(new Dimension(400, 50));
		fileName.add(fileNameList, BorderLayout.CENTER);
		
		title.setBorder(new LineBorder(Color.LIGHT_GRAY));
		title.setPreferredSize(new Dimension(400, 30));
        
		title.setBounds(46, 10, 400, 30);
		fileButton.setBounds(15, 45, 465, 60);
		attachedPane.setBounds(95, 105, 300, 150);
		content.setBounds(5,260,470, 430);
		fileName.setBounds(5, 693, 467, 60);
        add(title);
        add(fileButton);
        add(attachedPane);
        add(content);
        add(fileName);
   
        setSize(500,1000);
        setVisible(true);
    }

    class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(image_btn)) { // ��ư �Է� �� ����Ž��â ����
				fileChooser chooser = new fileChooser(new String[]{"gif", "png", "jpg"});
				String filePath = chooser.filePath;
				fileNameListModel.addElement(filePath);
				//���� ���ϰ��
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //����� ���ϰ��
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //���� ���
		        
		        //���ϰ�ü����
		        File oriFile = new File(oriFilePath);
		        //�������ϰ�ü����
		        File copyFile = new File(copyFilePath);
		        Image image = null;
		        try {
		        	File sourceimage = new File(oriFilePath);
		        	image = ImageIO.read(sourceimage);
		        
		        	
		        } catch (IOException eee) {
		        	eee.printStackTrace();
		        }
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //��������
		            FileOutputStream fos = new FileOutputStream(copyFile); //����������
		            
		            int fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //�ڿ��������
		            fis.close();
		            fos.close();
		            
		        } catch (FileNotFoundException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        } catch (IOException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
		        Image changeImg = image.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
		        JLabel label = new JLabel(new ImageIcon(changeImg));
		        attachedPane.add(label);
			}
			else if(e.getSource().equals(video_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"avi", "mp4"});
				String filePath = chooser.filePath;
				fileNameListModel.addElement(filePath);
				//���� ���ϰ��
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //����� ���ϰ��
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //���� ���
		        
		        //���ϰ�ü����
		        File oriFile = new File(oriFilePath);
		        //�������ϰ�ü����
		        File copyFile = new File(copyFilePath);
		        
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //��������
		            FileOutputStream fos = new FileOutputStream(copyFile); //����������
		            
		            int fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //�ڿ��������
		            fis.close();
		            fos.close();
		            
		        } catch (FileNotFoundException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        } catch (IOException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
			}
			else if(e.getSource().equals(pdf_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"pdf"});
				String filePath = chooser.filePath;
				fileNameListModel.addElement(filePath);
				//���� ���ϰ��
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //����� ���ϰ��
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //���� ���
		        
		        //���ϰ�ü����
		        File oriFile = new File(oriFilePath);
		        //�������ϰ�ü����
		        File copyFile = new File(copyFilePath);
		        
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //��������
		            FileOutputStream fos = new FileOutputStream(copyFile); //����������
		            
		            int fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //�ڿ��������
		            fis.close();
		            fos.close();
		            
		        } catch (FileNotFoundException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        } catch (IOException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
			}
			else if(e.getSource().equals(voice_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"wav", "mp3"});
				String filePath = chooser.filePath;
				fileNameListModel.addElement(filePath);
				//���� ���ϰ��
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //����� ���ϰ��
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //���� ���
		        
		        //���ϰ�ü����
		        File oriFile = new File(oriFilePath);
		        //�������ϰ�ü����
		        File copyFile = new File(copyFilePath);
		        
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //��������
		            FileOutputStream fos = new FileOutputStream(copyFile); //����������
		            
		            int fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //�ڿ��������
		            fis.close();
		            fos.close();
		            
		        } catch (FileNotFoundException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        } catch (IOException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
			}
		}
    }
    // ���� content ������ text ���Ϸ� ����
    public void writeFile(){
    	String comment_str = contentText.getText();
    	if (!comment_str.equals("Enter the content here")) { // stage�� ������ ���� ����
        	String File_name = "data\\comment_" + stageTitle.getText() + ".txt"; //Change to desired extension(ex. ".c")
        	try {
        		FileWriter writer = new FileWriter(File_name);
        		writer.write(comment_str);
        		writer.close();
        	} catch (IOException ex) {}
        }
    }
    
    public void makeattachedfolder(){
	    File com = new File(".");
	    String pa = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile"; //���� ���
		File Fol = new File(pa);
		
		if(stageTitle.getText() != "No stage") {
			if (!Fol.exists()) { // �ش� ���丮�� ������� ���丮�� �����մϴ�.
				try{
				    Fol.mkdir(); //���� �����մϴ�.
			        } 
			        catch(Exception e){
				    e.getStackTrace();
				}              
			}
		}	
    }
    // ���õ� text ������ content�� �о����
    public void readFile(String stageTitle){
    	String path = file.getPath()+"\\data\\comment_" + stageTitle + ".txt";
    	StringBuffer comment_str = new StringBuffer("");
    	try {
            String s;
            File read = new File(path);
            BufferedReader bReader = null;
            bReader = new BufferedReader(new FileReader(read));
            
            // ���̻� �о���ϰ� ���� ������ �о���̰� �մϴ�.
            while((s = bReader.readLine()) != null) {
                comment_str.append(s);
                comment_str.append("\n");
            }
        } catch(IOException e) {}
    	
    	if (comment_str.toString().length() == 0)
    		contentText.setText("Enter the content here");
    	else
    		contentText.setText(comment_str.toString());
    }
    
}