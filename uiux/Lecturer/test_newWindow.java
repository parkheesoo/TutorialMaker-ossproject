import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class test_newWindow extends JFrame {
    private static final ActionListener ActionListener = null;
	private JPanel btnPanel = new JPanel();
    private JPanel txtPanel1 = new JPanel();
    private JPanel txtPanel2 = new JPanel();
    
    private JButton OK_btn = new JButton("submit");
    private JButton Cancle_btn = new JButton("cancle");
    //private JLabel in_label = new JLabel("Quiz");

    private JLabel out_label = new JLabel("output");
    private JLabel title = new JLabel("title");
    private JTextArea title_txt = new JTextArea(1,10);
    private JTextArea in_txt = new JTextArea(13, 45); //크기조정 필요
    private JTextArea out_txt = new JTextArea(1, 40); //크기조정 필요
    String stageT;
    public test_newWindow(){
    	stageT = "";
    	byte[] x = stageT.getBytes();
    	String data = new String(x);

    	System.out.println(data);
        setTitle(data + "문제 만들기");
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
                try{
                                 
                    // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
                    BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
                    // 파일안에 문자열 쓰기
                    fw.write("hi");
                    // 객체 닫기
                    fw.close();     
                     
                }catch(Exception el){
                    el.printStackTrace();
                }
                dispose();
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