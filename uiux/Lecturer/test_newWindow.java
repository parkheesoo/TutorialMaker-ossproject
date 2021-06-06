import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class test_newWindow extends JFrame {
    private static final ActionListener ActionListener = null;
	private JPanel btnPanel = new JPanel();
    private JPanel txtPanel1 = new JPanel();
    private JPanel txtPanel2 = new JPanel();
    
    private JButton OK_btn = new JButton("submit");
    private JButton Cancle_btn = new JButton("cancle");
    
    private JLabel out_label = new JLabel("output");
    private JLabel title = new JLabel("title");
    private JTextArea title_txt = new JTextArea(1,10);
    private JTextArea in_txt = new JTextArea(13, 45); //크기조정 필요
    private JTextArea out_txt = new JTextArea(1, 40); //크기조정 필요
    
    String stageT = " ";
    public void title_get(String title_co){
    	
        stageT = title_co;
    }
    
    public test_newWindow(){
    	
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
        btnPanel.add(Cancle_btn);
        btnPanel.add(OK_btn);
        txtPanel1.setPreferredSize(new Dimension(400, 280));
        getContentPane().add(txtPanel1, BorderLayout.NORTH);
        getContentPane().add(txtPanel2, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        
        OK_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String fileName = "data\\Quiz_" + stageT + ".txt"; ;
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
  
    
}