package Lecturer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class test_newWindow extends JFrame {
    private JPanel btnPanel = new JPanel();
    private JPanel txtPanel1 = new JPanel();
    private JPanel txtPanel2 = new JPanel();

    private JButton OK_btn = new JButton("submit");
    private JButton Cancle_btn = new JButton("cancle");
    private JLabel in_label = new JLabel("input");
    private JLabel out_label = new JLabel("output");
    private JTextField in_txt = new JTextField(10);
    private JTextField out_txt = new JTextField(15);

    public test_newWindow(String stageTitle){
        setTitle("다음 단계 조건 설정");
        setLayout(new BorderLayout());

        txtPanel1.add(in_label);
        txtPanel1.add(in_txt);
        txtPanel2.add(out_label);
        txtPanel2.add(out_txt);

        btnPanel.add(Cancle_btn);
        btnPanel.add(OK_btn);

        getContentPane().add(txtPanel1, BorderLayout.NORTH);
        getContentPane().add(txtPanel2, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        OK_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		makeQuizFile(stageTitle);
        	}
        });
        
        Cancle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(300,150);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }
    
    public void makeQuizFile(String stageTitle) {
    	String input_str = in_txt.getText();
    	String output_str = out_txt.getText();
    	if (!stageTitle.equals("No stage")) { // stage가 존재할 때만 실행
        	String i_File_name = "data\\quiz_input_" + stageTitle + ".txt"; //Change to desired extension(ex. ".c")
        	String o_File_name = "data\\quiz_output_" + stageTitle + ".txt";
        	try {
        		FileWriter i_writer = new FileWriter(i_File_name);
        		i_writer.write(input_str);
        		i_writer.close();
        		
        		FileWriter o_writer = new FileWriter(o_File_name);
        		o_writer.write(output_str);
        		o_writer.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}        	
        }
    }
}