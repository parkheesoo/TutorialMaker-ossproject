import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class test_newWindow extends JFrame {
    private static final ActionListener ActionListener = null;
	private JPanel btnPanel = new JPanel();
    private JPanel txtPanel1 = new JPanel();
    private JPanel txtPanel2 = new JPanel();
   
    private JButton OK_btn = new JButton("채점하기");
    private JButton Cancle_btn = new JButton("코드보기");
    
    private JLabel out_label = new JLabel("output");
    private JLabel title = new JLabel("title");
    private JTextArea in_txt = new JTextArea(13, 45); //크기조정 필요

    
    private JTextArea out_txt = new JTextArea(1, 40); //크기조정 필요
    String pathth = " ";
    String stageT = " ";
    public void title_get(String title_co, String filepath){
        stageT = title_co;
        pathth = filepath;
        //title.setText(title_t);
    }
    
    public test_newWindow(){
    	
        setTitle("문제");
        setLayout(new BorderLayout());

        //txtPanel1.add(in_label, BorderLayout.NORTH);
        
        txtPanel1.add(title);
        txtPanel1.add(in_txt);
        in_txt.setEditable(false);
        in_txt.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(in_txt);  //스크롤판 추가
         //화면에 추가
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//수평 스크롤 안쓰게함.

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        txtPanel1.add(scrollPane);                             //꽉차


        txtPanel2.add(out_label);
        txtPanel2.add(out_txt);
        out_txt.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
        in_txt.setBorder(new LineBorder(Color.LIGHT_GRAY,5));
        btnPanel.add(Cancle_btn);
        btnPanel.add(OK_btn);
        txtPanel1.setPreferredSize(new Dimension(400, 280));
        getContentPane().add(txtPanel1, BorderLayout.NORTH);
        getContentPane().add(txtPanel2, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        
        OK_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            }
        });

        Cancle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lecturercode_view newlecturercodeView = new lecturercode_view();
            	newlecturercodeView.title_get(stageT, pathth);
            	newlecturercodeView.readFile(stageT);
            }
        });

        setSize(500,400);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }
    public void readFile(String stageTitle){
    	
    	String path = pathth + "\\Quiz_" + stageTitle + ".txt";
    	
    	StringBuffer comment_str = new StringBuffer("");
    	try {
            String s;
            File read = new File(path);
            FileReader reader = new FileReader(read);

            int cnt = 0;
            int ch = 0;
            // 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
            while((ch = reader.read()) != -1) {
            	if((char)ch == '/' && cnt == 0) {
            		title.setText("Title : " + comment_str.toString());
            		comment_str.setLength(0);
            		cnt++;
            	}
            	else if((char)ch == '/' && cnt == 1){
            		in_txt.setText(comment_str.toString());
            		comment_str.setLength(0);
            		cnt++;
            	}
            	else {
            		comment_str.append((char)ch);
            	} 
            	
            }
            reader.close();
        } catch(IOException e) {}
    	
    	out_txt.setText(comment_str.toString());
    }
  
    
}