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
    private JPanel title = new JPanel(); // 제목 입력을 위한 패널
    private JPanel fileButton = new JPanel(); // 첨부파일 버튼을 위한 패널
    private JPanel content = new JPanel(); // 내용 입력을 위한 패널
    private JPanel fileName = new JPanel(); // 첨부한 파일을 보여주기 위한 패널
    private JPanel attachedPane = new JPanel();
    
    JLabel stageTitle= new JLabel("No stage");
    TextArea contentText = new TextArea("Enter the content here", 28, 55);
 
    // 이미지 파일을 위한 파일 경로 저장
    File file = new File(".");
    String path = file.getPath()+"\\image\\";

    // 이미지 파일을 불러와 아이콘을 생성하고 배열에 저장
    ImageIcon [] images = { new ImageIcon(path+"image.png"), new ImageIcon(path+"video.png"),
            new ImageIcon(path+"pdf.png"), new ImageIcon(path+"voice.png")};

    // 첨부파일 선택을 위한 버튼
    JButton image_btn = new JButton(images[0]);
    JButton video_btn = new JButton(images[1]);
    JButton pdf_btn = new JButton(images[2]);
    JButton voice_btn = new JButton(images[3]);
    
  //첨부된 파일을 보여주기 위한 컴포넌트
  	private DefaultListModel<String> fileNameListModel = new DefaultListModel<String>();
  	private JList fileNameList = new JList(fileNameListModel);
	
  	
    public commentPanel() {
        MyActionListener actionListener = new MyActionListener();

        // title 패널 구현
        title.add(new JLabel("Title: "));
        title.add(stageTitle);

        
        // button 패널 구현
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

        // content 패널 구현
        content.add(contentText);
        contentText.addFocusListener(new FocusListener() { // textArea 클릭 시 초기 문구가 사라짐
            public void focusLost(FocusEvent e) {}
            public void focusGained(FocusEvent e) {
            	if (contentText.getText().equals("Enter the content here")) {
            		contentText.setText("");
            	}
            }
        });

        // fileName 패널 구현
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
			if(e.getSource().equals(image_btn)) { // 버튼 입력 시 파일탐색창 열기
				fileChooser chooser = new fileChooser(new String[]{"gif", "png", "jpg"});
				String filePath = chooser.filePath;
				fileNameListModel.addElement(filePath);
				//원본 파일경로
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //복사될 파일경로
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //폴더 경로
		        
		        //파일객체생성
		        File oriFile = new File(oriFilePath);
		        //복사파일객체생성
		        File copyFile = new File(copyFilePath);
		        Image image = null;
		        try {
		        	File sourceimage = new File(oriFilePath);
		        	image = ImageIO.read(sourceimage);
		        
		        	
		        } catch (IOException eee) {
		        	eee.printStackTrace();
		        }
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //읽을파일
		            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
		            
		            int fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //자원사용종료
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
				//원본 파일경로
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //복사될 파일경로
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //폴더 경로
		        
		        //파일객체생성
		        File oriFile = new File(oriFilePath);
		        //복사파일객체생성
		        File copyFile = new File(copyFilePath);
		        
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //읽을파일
		            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
		            
		            int fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //자원사용종료
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
				//원본 파일경로
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //복사될 파일경로
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //폴더 경로
		        
		        //파일객체생성
		        File oriFile = new File(oriFilePath);
		        //복사파일객체생성
		        File copyFile = new File(copyFilePath);
		        
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //읽을파일
		            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
		            
		            int fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //자원사용종료
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
				//원본 파일경로
		        String oriFilePath = filePath;
		        File f = new File(filePath);
		        String p ="";
		        String filen = "";
		        
		        path = f.getParentFile().toString();
		        filen = f.getName();
		        //복사될 파일경로
		        File com = new File(".");
		        String copyFilePath = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile\\" + filen; //폴더 경로
		        
		        //파일객체생성
		        File oriFile = new File(oriFilePath);
		        //복사파일객체생성
		        File copyFile = new File(copyFilePath);
		        
		        try {
		            
		            FileInputStream fis = new FileInputStream(oriFile); //읽을파일
		            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
		            
		            int fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //자원사용종료
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
    // 현재 content 내용을 text 파일로 쓰기
    public void writeFile(){
    	String comment_str = contentText.getText();
    	if (!comment_str.equals("Enter the content here")) { // stage가 존재할 때만 실행
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
	    String pa = com.getPath() + "\\data\\" + stageTitle.getText() + "_attachedfile"; //폴더 경로
		File Fol = new File(pa);
		
		if(stageTitle.getText() != "No stage") {
			if (!Fol.exists()) { // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
				try{
				    Fol.mkdir(); //폴더 생성합니다.
			        } 
			        catch(Exception e){
				    e.getStackTrace();
				}              
			}
		}	
    }
    // 선택된 text 파일을 content에 읽어오기
    public void readFile(String stageTitle){
    	String path = file.getPath()+"\\data\\comment_" + stageTitle + ".txt";
    	StringBuffer comment_str = new StringBuffer("");
    	try {
            String s;
            File read = new File(path);
            BufferedReader bReader = null;
            bReader = new BufferedReader(new FileReader(read));
            
            // 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
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