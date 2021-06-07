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

public class lecturercode_view extends JFrame {
    private static final ActionListener ActionListener = null;
	
    private JPanel txtPanel1 = new JPanel();
    
    private JTextArea in_txt = new JTextArea(33, 35); //ũ������ �ʿ�

    String pathth = " ";
    String stageT = " ";
    public void title_get(String title_co, String filepath){
        stageT = title_co;
        pathth = filepath;
        //title.setText(title_t);
    }
    
    public lecturercode_view(){
    	
        setTitle("code");
        setLayout(new BorderLayout());

        //txtPanel1.add(in_label, BorderLayout.NORTH);
        
        txtPanel1.add(in_txt);
        in_txt.setEditable(false);
        in_txt.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(in_txt);  //��ũ���� �߰�
         //ȭ�鿡 �߰�
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//���� ��ũ�� �Ⱦ�����.

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        txtPanel1.add(scrollPane);                             //����

        in_txt.setBorder(new LineBorder(Color.BLACK,1));
       
        txtPanel1.setPreferredSize(new Dimension(350, 600));
        getContentPane().add(txtPanel1, BorderLayout.NORTH);
       

        setSize(400,650);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }
    public void readFile(String stageTitle){
    	String path = pathth + "\\code_" + stageTitle + ".txt";
    	System.out.print(path);
    	StringBuffer comment_str = new StringBuffer("");
    	try {
            File read = new File(path);
            FileReader reader = new FileReader(read);

            int ch = 0;
            // ���̻� �о���ϰ� ���� ������ �о���̰� �մϴ�.
            while((ch = reader.read()) != -1) {
            	comment_str.append((char)ch);
            	
            }
            reader.close();
        } catch(IOException e) {}
    	
    	in_txt.setText(comment_str.toString());
    }
}