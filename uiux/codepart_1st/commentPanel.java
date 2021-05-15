package codepart_1st;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class commentPanel extends JPanel {
	private JPanel title = new JPanel(); // ���� �Է��� ���� �г�
	private JPanel fileButton = new JPanel(); // ÷������ ��ư�� ���� �г�
    private JPanel content = new JPanel(); // ���� �Է��� ���� �г�
    private JPanel fileName = new JPanel(); // ÷���� ������ �����ֱ� ���� �г�
    
    TextArea fileNames = new TextArea(5, 50); // ÷������ �ּҸ� ����ϴ� textArea
    
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
    
    public commentPanel() {
    	MyActionListener actionListener = new MyActionListener();
    	
    	// title �г� ����
    	JTextField titleText = new JTextField(25);
    	title.add(new JLabel("Title: "));
    	title.add(titleText);
    	
    	// button �г� ����    	
    	fileButton.add(image_btn);
    	fileButton.add(video_btn);
    	fileButton.add(pdf_btn);
    	fileButton.add(voice_btn);
    	
    	image_btn.addActionListener(actionListener);
    	video_btn.addActionListener(actionListener);
    	pdf_btn.addActionListener(actionListener);
    	voice_btn.addActionListener(actionListener);
    	
    	// content �г� ����
    	TextArea contentText = new TextArea("Enter the content here", 25, 60);
    	content.add(contentText);
    	contentText.addFocusListener(new FocusListener() { // textArea Ŭ�� �� �ʱ� ������ �����
    		public void focusLost(FocusEvent e) {}
    		public void focusGained(FocusEvent e) {
    			contentText.setText("");
    		}
    	});
    	
    	// fileName �г� ����
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
			if(e.getSource().equals(image_btn)) { // ��ư �Է� �� ����Ž��â ����
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