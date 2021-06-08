package Lecturer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class codePanel extends JPanel {
    private JPanel codepanel = new JPanel(); //코드 부분 전체 panel
    private JPanel txtpanel = new JPanel(); //코드 입력 부분 :: 페이지 넘어갈 때 panel 교체
    private JPanel btnpanel = new JPanel(); //버튼 :: 코드 입력 부분의 panel 교체 시 분리될 수 있도록

    // 버튼 세팅 - 이미지 세팅
    private JButton Pre_btn = new JButton();
    private JButton Next_btn = new JButton();
    private ImageIcon Next_btn_img = new ImageIcon("image\\nextbutton.png");
    private ImageIcon Next_press_img = new ImageIcon("image\\nextbutton_press.png");

    // 코드 입력 부분 component
    private JTextArea textArea1 = new JTextArea(37, 35); //크기조정 필요
    private JScrollPane scroll = new JScrollPane(textArea1);

    // JTextArea 에서 행,열을 얻어서 보여주는 임시 라벨(주석 달 때 행 필요하면 사용)
    private JLabel status = new JLabel();


    // +추가하기+ 코드 입력 시 주석 또는 퀴즈를 달 수 있는 버튼 생성

    codePanel() {
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

        txtpanel.add(scroll);
        btnpanel.add(Next_btn);
        txtpanel.setBackground(Color.WHITE);
        btnpanel.setBackground(Color.WHITE);

        
        Next_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 입력코드에 대한 파일 생성 가능 ==> 학습자 부분에서 활용하기
                String code_str = textArea1.getText();
                String File_name = "out.txt"; //Change to desired extension(ex. ".c")
                try {
                    FileWriter writer = new FileWriter(File_name);
                    writer.write(code_str);
                    writer.close();
                } catch (IOException ex) {
                }

                // 조건 입력 창 띄우기
                test_newWindow newWindow = new test_newWindow();
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
    private void updateStatus(int linenumber, int columnnumber) {
        status.setText("Line: " + linenumber + " Column: " + columnnumber);
    }

    // 현재 code 내용을 text 파일로 쓰기
    public void writeFile(String stageTitle){
    	String code_str = textArea1.getText();
    	if (!stageTitle.equals("No stage")) { // stage가 존재할 때만 실행
        	String File_name = "data\\code_" + stageTitle + ".txt"; //Change to desired extension(ex. ".c")
        	try {
        		FileWriter writer = new FileWriter(File_name);
        		writer.write(code_str);
        		writer.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
    }
    
    // 선택된 text 파일을 code에 읽어오기
    public void readFile(String stageTitle){
    	File file = new File(".");
    	String path = file.getPath() + "\\data\\code_" + stageTitle + ".txt";
    	StringBuffer code_str = new StringBuffer("");
    	try {
            String s;
            File read = new File(path);
            BufferedReader bReader = null;
            bReader = new BufferedReader(new FileReader(read));
            
            // 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
            while((s = bReader.readLine()) != null) {
                code_str.append(s);
                code_str.append("\n");
            }
        } catch(IOException e) {}
    	
    	textArea1.setText(code_str.toString());
    }

    // 코드 패널에 있는 문장들 파일출력하는 메소드
    public void makeCodeFile(){
        String code_str = textArea1.getText();
        String File_name = "out.c"; //Change to desired extension(ex. ".c")
        try {
            FileWriter writer = new FileWriter(File_name);
            writer.write(code_str);
            writer.close();
        } catch (IOException ex) {}
    }
}