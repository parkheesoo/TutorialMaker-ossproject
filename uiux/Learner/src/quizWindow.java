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

public class quizWindow extends JFrame {
    private static final ActionListener ActionListener = null;
	private JPanel btnPanel = new JPanel();
    private JPanel txtPanel1 = new JPanel();
    private JPanel txtPanel2 = new JPanel();
   
    private JButton OK_btn = new JButton("채점하기");
    
    private JLabel out_label = new JLabel("output");
    private JLabel title = new JLabel("title");
    private JTextArea in_txt = new JTextArea(14, 45); //크기조정 필요

    
    private JTextArea out_txt = new JTextArea(1, 40); //크기조정 필요
    String pathth = " ";
    String compileResult = " ";
    
    String stageTitle = " ";
    File file = new File(".\\data");
    
    public void title_get(String title_co, String filepath){
        stageTitle = title_co;
        pathth = filepath;
        //title.setText(title_t);
    }
    
    public quizWindow(String stageTitle, File dir){
    	this.stageTitle = stageTitle;
    	file = dir;
    	
        setTitle("문제");
        setLayout(new BorderLayout());

        //txtPanel1.add(in_label, BorderLayout.NORTH);
        
        txtPanel1.add(title);
        txtPanel1.add(in_txt);
        in_txt.setEditable(false);
        in_txt.setLineWrap(true);
        out_txt.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(in_txt);  //스크롤판 추가
         //화면에 추가
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//수평 스크롤 안쓰게함.

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        txtPanel1.add(scrollPane);                             //꽉차


        txtPanel2.add(out_label);
        txtPanel2.add(out_txt);
        out_txt.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
        in_txt.setBorder(new LineBorder(Color.LIGHT_GRAY,5));
        
        OK_btn.setBackground(Color.LIGHT_GRAY);
        btnPanel.add(OK_btn);
        txtPanel1.setPreferredSize(new Dimension(400, 300));
        getContentPane().add(txtPanel1, BorderLayout.NORTH);
        getContentPane().add(txtPanel2, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        
        OK_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	String path = file.getPath() + "\\Quiz_" + stageTitle + ".txt";
            	StringBuffer comment_str = new StringBuffer("");
            	try {
                    String s;
                    File read = new File(path);
                    FileReader reader = new FileReader(read);

                    int cnt = 0;
                    int ch = 0;
                    // 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
                    while((ch = reader.read()) != -1) {
                    	if((char)ch == '/' && cnt <= 1) {
                    		comment_str.setLength(0);
                    		cnt++;
                    	}
                    	else {
                    		comment_str.append((char)ch);
                    	} 
                    	
                    }
                    reader.close();
                } catch(IOException edd) {}

            	Call_compiler compile = new Call_compiler();
            	compile.runforquiz(comment_str.toString());
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