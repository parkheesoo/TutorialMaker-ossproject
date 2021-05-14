import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class test_MainPanel extends JPanel {
    private JPanel codepanel = new JPanel(); //코드 부분 전체 panel
    private JPanel txtpanel = new JPanel(); //코드 입력 부분 :: 페이지 넘어갈 때 panel 교체
    private JPanel btnpanel = new JPanel(); //버튼 :: 코드 입력 부분의 panel 교체 시 분리될 수 있도록
    
    private JPanel title = new JPanel(); // 제목 입력을 위한 패널
	private JPanel fileButton = new JPanel(); // 첨부파일 버튼을 위한 패널
    private JPanel content = new JPanel(); // 내용 입력을 위한 패널
    private JPanel fileName = new JPanel(); // 첨부한 파일을 보여주기 위한 패널
    
    // 버튼 세팅 - 이미지 세팅
    private JButton Pre_btn = new JButton();
    private JButton Next_btn = new JButton();
    private ImageIcon Next_btn_img = new ImageIcon("image\\nextbutton.png");
    private ImageIcon Pre_btn_img = new ImageIcon("image\\prebutton.png");
    private ImageIcon Next_press_img = new ImageIcon("image\\nextbutton_press.png");
    private ImageIcon Pre_press_img = new ImageIcon("image\\prebutton_press.png");

    // 코드 입력 부분 component
    private JTextArea textArea1 = new JTextArea(20, 30); //크기조정 필요
    private JScrollPane scroll = new JScrollPane(textArea1);

    // JTextArea에서 행,열 얻어서 보여주는 임시 라벨(주석달 때 행 필요하면 사용)
    private JLabel status = new JLabel();

    TextArea fileNames = new TextArea(5, 50); // 첨부파일 주소를 출력하는 textArea
    
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
    
    // +추가하기+ 코드 입력시 주석 또는 퀴즈를 달 수 있는 버튼 생성
    public test_MainPanel() {
    	MyActionListener actionListener = new MyActionListener();
        add(codepanel);
        codepanel.setLayout(new BorderLayout());
        codepanel.add(txtpanel, BorderLayout.CENTER);
        codepanel.add(btnpanel, BorderLayout.SOUTH);
        codepanel.add(status, BorderLayout.NORTH);

        //next button setting
        Next_btn.setIcon(Next_btn_img);
        Next_btn.setBorderPainted(false);
        Next_btn.setContentAreaFilled(false);
        Next_btn.setFocusPainted(false);
        Next_btn.setPreferredSize(new Dimension(103, 46));
        Next_btn.setPressedIcon(Next_press_img);
        Next_btn.setRolloverIcon(Next_press_img);

        //pre button setting
        Pre_btn.setIcon(Pre_btn_img);
        Pre_btn.setBorderPainted(false);
        Pre_btn.setContentAreaFilled(false);
        Pre_btn.setFocusPainted(false);
        Pre_btn.setPreferredSize(new Dimension(103, 46));
        Pre_btn.setPressedIcon(Pre_press_img);
        Pre_btn.setRolloverIcon(Pre_press_img);

        txtpanel.add(scroll);
        btnpanel.add(Pre_btn);
        btnpanel.add(Next_btn);
    	
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
    	fileName.add(new JLabel("Attached files"));
    	fileNames.setEnabled(false);
    	fileName.add(fileNames);
    	
    	add(title);
    	add(fileButton);
    	add(content);
    	add(fileName);
        

        Pre_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이전 단계 표시
            }
        });
        Next_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test_newWindow newWindow = new test_newWindow(); // 조건 입력 창 띄우기
            }
        });
        // JTextArea의 행과 열 표시 (임시)
        textArea1.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                JTextArea editArea = (JTextArea) e.getSource();

                int linenum = 1;
                int columnnum = 1;

                try {
                    int caretpos = editArea.getCaretPosition();
                    linenum = editArea.getLineOfOffset(caretpos);
                    columnnum = caretpos - editArea.getLineStartOffset(linenum);
                    linenum += 1;
                } catch (Exception ex) {
                }

                updateStatus(linenum, columnnum);
            }
        });
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
    
    private void updateStatus(int linenumber, int columnnumber) {
        status.setText("Line: " + linenumber + " Column: " + columnnumber);
    }

}