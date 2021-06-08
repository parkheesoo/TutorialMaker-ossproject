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
    private JButton code_View = new JButton();
    private JButton Pre_btn = new JButton();
    private ImageIcon Code_btn_img = new ImageIcon("image\\codebutton.png");
    private ImageIcon Code_press_img = new ImageIcon("image\\codebutton_press.png");
    private ImageIcon Pre_btn_img = new ImageIcon("image\\prebutton.png");
    private ImageIcon Pre_press_img = new ImageIcon("image\\prebutton_press.png");
    // �ڵ� �Է� �κ� component
    private JTextArea textArea1 = new JTextArea(30, 30); //ũ������ �ʿ�
    private JScrollPane scroll = new JScrollPane(textArea1);

    // JTextArea ���� ��,���� �� �����ִ� �ӽ� ��(�ּ� �� �� �� �ʿ��ϸ� ���)
    private JLabel status = new JLabel();
    private File file = new File("");
    private File temp = new File(".");

    String stageTitle = "";
    //String temp = " ";

    // +�߰��ϱ�+ �ڵ� �Է� �� �ּ� �Ǵ� ��� �� �� �ִ� ��ư ����
    codePanel() {
        add(codepanel);
        Image pre_btn_img = Pre_btn_img.getImage();  //ImageIcon�� Image�� ��ȯ.
        Image pre_Btn_img = pre_btn_img.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon Pre_btn_img = new ImageIcon(pre_Btn_img); //Image�� ImageIcon ����
        Image pre_press_img = Pre_press_img.getImage();  //ImageIcon�� Image�� ��ȯ.
        Image Pre_Press_img = pre_press_img.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon Pre_press_img = new ImageIcon(Pre_Press_img); //Image�� ImageIcon ����
        
        Image code_btn_img = Code_btn_img.getImage();  //ImageIcon�� Image�� ��ȯ.
        Image code_Btn_img = code_btn_img.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon Code_btn_img = new ImageIcon(code_Btn_img); //Image�� ImageIcon ����
        Image code_press_img = Code_press_img.getImage();  //ImageIcon�� Image�� ��ȯ.
        Image code_Press_img = code_press_img.getScaledInstance(100, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon Code_press_img = new ImageIcon(code_Press_img); //Image�� ImageIcon ����
        codepanel.setLayout(new BorderLayout());
        codepanel.add(txtpanel, BorderLayout.CENTER);
        codepanel.add(btnpanel, BorderLayout.SOUTH);
        codepanel.add(status, BorderLayout.NORTH);

        //pre button setting
        Pre_btn.setIcon(Pre_btn_img);
        Pre_btn.setBorderPainted(false);
        Pre_btn.setContentAreaFilled(false);
        Pre_btn.setFocusPainted(false);
        
        Pre_btn.setPreferredSize(new Dimension(130, 50));
        Pre_btn.setPressedIcon(Pre_press_img);
        Pre_btn.setRolloverIcon(Pre_press_img);

       //pre button setting
        code_View.setIcon(Code_btn_img);
        code_View.setBorderPainted(false);
        code_View.setContentAreaFilled(false);
        code_View.setFocusPainted(false);
        
        code_View.setPreferredSize(new Dimension(130, 50));
        code_View.setPressedIcon(Code_press_img);
        code_View.setRolloverIcon(Code_press_img);
        
        txtpanel.add(scroll);
        btnpanel.add(Pre_btn);
        btnpanel.add(code_View);
        
        Pre_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ���� �ܰ� ǥ��
            	quizWindow newWindow = new quizWindow(stageTitle, file);
            	
            	newWindow.title_get(stageTitle, file.getPath());
            	newWindow.readFile(stageTitle);
            }
        });
        
        code_View.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ���� �ܰ� ǥ��
            	lecturercode_view lec = new lecturercode_view();
            	lec.title_get(stageTitle, file.getPath());
            	lec.readFile(stageTitle);
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
    private ImageIcon imageSetSize(ImageIcon pre_btn_img2, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	private void updateStatus(int linenumber, int columnnumber) {
        status.setText("Line: " + linenumber + " Column: " + columnnumber);
    }

	public void setFile(File dir) {
    	file = dir;
    }
	
	public void setStageTitle(String stageTitle) {
    	this.stageTitle = stageTitle;
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
    	//temp = stageTitle;
    	String path = "data\\code_" + stageTitle + ".txt";
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
            bReader.close();
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