package codepart_1st;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class commentPanel extends JPanel {
	private JPanel title = new JPanel(); // 제목 입력을 위한 패널
	private JPanel fileButton = new JPanel(); // 첨부파일 버튼을 위한 패널
    private JPanel content = new JPanel(); // 내용 입력을 위한 패널
    private JPanel fileName = new JPanel(); // 첨부한 파일을 보여주기 위한 패널
    
    TextArea fileNames = new TextArea(5, 50); // 첨부파일 주소를 출력하는 textArea
    
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
    
    public commentPanel() {
    	MyActionListener actionListener = new MyActionListener();
    	
    	// title 패널 구현
    	JTextField titleText = new JTextField(25);
    	title.add(new JLabel("Title: "));
    	title.add(titleText);
    	
    	// button 패널 구현    	
    	fileButton.add(image_btn);
    	fileButton.add(video_btn);
    	fileButton.add(pdf_btn);
    	fileButton.add(voice_btn);
    	
    	image_btn.addActionListener(actionListener);
    	video_btn.addActionListener(actionListener);
    	pdf_btn.addActionListener(actionListener);
    	voice_btn.addActionListener(actionListener);
    	
    	// content 패널 구현
    	TextArea contentText = new TextArea("Enter the content here", 25, 60);
    	content.add(contentText);
    	contentText.addFocusListener(new FocusListener() { // textArea 클릭 시 초기 문구가 사라짐
    		public void focusLost(FocusEvent e) {}
    		public void focusGained(FocusEvent e) {
    			contentText.setText("");
    		}
    	});
    	
    	// fileName 패널 구현
    	fileName.setLayout(new BorderLayout());
    	fileName.add(new JLabel("Attached files"), BorderLayout.NORTH);
    	fileNames.setEnabled(false);
    	fileName.add(fileNames, BorderLayout.CENTER);
    	
    	add(title);
    	add(fileButton);
    	add(content);
    	add(fileName);
    	
    	setSize(500,700);
		setVisible(true);
    }
    
    class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(image_btn)) { // 버튼 입력 시 파일탐색창 열기
				fileChooser chooser = new fileChooser(new String[]{"gif", "png", "jpg"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
			else if(e.getSource().equals(video_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"avi", "mp4"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
			else if(e.getSource().equals(pdf_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"pdf"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
			else if(e.getSource().equals(voice_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"wav", "mp3"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
		}
    }
}