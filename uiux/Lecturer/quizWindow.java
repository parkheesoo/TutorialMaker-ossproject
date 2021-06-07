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
    
    private JButton OK_btn = new JButton("submit");
    private JButton Cancle_btn = new JButton("cancle");
    
    private JLabel out_label = new JLabel("output");
    private JLabel title = new JLabel("title");
    private JTextArea title_txt = new JTextArea(1,10);
    private JTextArea in_txt = new JTextArea(14, 45); //크기조정 필요
    private JTextArea out_txt = new JTextArea(1, 40); //크기조정 필요
    
    File file = new File(".\\data");
    String stageTitle;
    public void title_get(String title_co){
    	
        stageTitle = title_co;
    }
    
    public quizWindow(String stageTitle, File dir){
    	this.stageTitle = stageTitle;
    	file = dir;
    	
        setTitle("새 문제 만들기");
        setLayout(new BorderLayout());

        //txtPanel1.add(in_label, BorderLayout.NORTH);
        txtPanel1.add(title);
        txtPanel1.add(title_txt);
        txtPanel1.add(in_txt);
        
        txtPanel2.add(out_label);
        txtPanel2.add(out_txt);
        out_txt.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
        in_txt.setBorder(new LineBorder(Color.LIGHT_GRAY,5));
        in_txt.setLineWrap(true);
        in_txt.setWrapStyleWord(true);
        
        Cancle_btn.setBackground(Color.LIGHT_GRAY);
        btnPanel.add(Cancle_btn);
        OK_btn.setBackground(Color.LIGHT_GRAY);
        btnPanel.add(OK_btn);
        txtPanel1.setPreferredSize(new Dimension(400, 300));
        getContentPane().add(txtPanel1, BorderLayout.NORTH);
        getContentPane().add(txtPanel2, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        
        readFile(stageTitle);
        
        OK_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String fileName = file.getPath() + "\\Quiz_" + stageTitle + ".txt"; ;
                try {
                	BufferedWriter bos = new BufferedWriter(new FileWriter(fileName, true));
                	bos.write(title.getText() +"/");
                	bos.write(in_txt.getText()+"/");
                	bos.write(out_txt.getText()+"\r\n");
                	bos.close();
                	dispose();
                	
                }catch(Exception ex) {
                	
                }
            }
        });

        Cancle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(500,400);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }
    
    public void readFile(String stageTitle){
    	
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
            	if((char)ch == '/' && cnt == 0) {
            		title_txt.setText(comment_str.toString());
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