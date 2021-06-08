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
    private JPanel codepanel = new JPanel(); //�ڵ� �κ� ��ü panel
    private JPanel txtpanel = new JPanel(); //�ڵ� �Է� �κ� :: ������ �Ѿ �� panel ��ü
    private JPanel btnpanel = new JPanel(); //��ư :: �ڵ� �Է� �κ��� panel ��ü �� �и��� �� �ֵ���

    // ��ư ���� - �̹��� ����
    private JButton Pre_btn = new JButton();
    private JButton Next_btn = new JButton();
    private ImageIcon Next_btn_img = new ImageIcon("image\\nextbutton.png");
    private ImageIcon Next_press_img = new ImageIcon("image\\nextbutton_press.png");

    // �ڵ� �Է� �κ� component
    private JTextArea textArea1 = new JTextArea(37, 35); //ũ������ �ʿ�
    private JScrollPane scroll = new JScrollPane(textArea1);

    // JTextArea ���� ��,���� �� �����ִ� �ӽ� ��(�ּ� �� �� �� �ʿ��ϸ� ���)
    private JLabel status = new JLabel();


    // +�߰��ϱ�+ �ڵ� �Է� �� �ּ� �Ǵ� ��� �� �� �ִ� ��ư ����

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
                // �Է��ڵ忡 ���� ���� ���� ���� ==> �н��� �κп��� Ȱ���ϱ�
                String code_str = textArea1.getText();
                String File_name = "out.txt"; //Change to desired extension(ex. ".c")
                try {
                    FileWriter writer = new FileWriter(File_name);
                    writer.write(code_str);
                    writer.close();
                } catch (IOException ex) {
                }

                // ���� �Է� â ����
                test_newWindow newWindow = new test_newWindow();
            }
        });
        // JTextArea�� ��� �� ǥ�� (�ӽ�)
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

    // ���� code ������ text ���Ϸ� ����
    public void writeFile(String stageTitle){
    	String code_str = textArea1.getText();
    	if (!stageTitle.equals("No stage")) { // stage�� ������ ���� ����
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
    
    // ���õ� text ������ code�� �о����
    public void readFile(String stageTitle){
    	File file = new File(".");
    	String path = file.getPath() + "\\data\\code_" + stageTitle + ".txt";
    	StringBuffer code_str = new StringBuffer("");
    	try {
            String s;
            File read = new File(path);
            BufferedReader bReader = null;
            bReader = new BufferedReader(new FileReader(read));
            
            // ���̻� �о���ϰ� ���� ������ �о���̰� �մϴ�.
            while((s = bReader.readLine()) != null) {
                code_str.append(s);
                code_str.append("\n");
            }
        } catch(IOException e) {}
    	
    	textArea1.setText(code_str.toString());
    }

    // �ڵ� �гο� �ִ� ����� ��������ϴ� �޼ҵ�
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