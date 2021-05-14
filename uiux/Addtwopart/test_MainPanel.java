import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class test_MainPanel extends JPanel {
    private JPanel codepanel = new JPanel(); //�ڵ� �κ� ��ü panel
    private JPanel txtpanel = new JPanel(); //�ڵ� �Է� �κ� :: ������ �Ѿ �� panel ��ü
    private JPanel btnpanel = new JPanel(); //��ư :: �ڵ� �Է� �κ��� panel ��ü �� �и��� �� �ֵ���
    
    private JPanel title = new JPanel(); // ���� �Է��� ���� �г�
	private JPanel fileButton = new JPanel(); // ÷������ ��ư�� ���� �г�
    private JPanel content = new JPanel(); // ���� �Է��� ���� �г�
    private JPanel fileName = new JPanel(); // ÷���� ������ �����ֱ� ���� �г�
    
    // ��ư ���� - �̹��� ����
    private JButton Pre_btn = new JButton();
    private JButton Next_btn = new JButton();
    private ImageIcon Next_btn_img = new ImageIcon("image\\nextbutton.png");
    private ImageIcon Pre_btn_img = new ImageIcon("image\\prebutton.png");
    private ImageIcon Next_press_img = new ImageIcon("image\\nextbutton_press.png");
    private ImageIcon Pre_press_img = new ImageIcon("image\\prebutton_press.png");

    // �ڵ� �Է� �κ� component
    private JTextArea textArea1 = new JTextArea(20, 30); //ũ������ �ʿ�
    private JScrollPane scroll = new JScrollPane(textArea1);

    // JTextArea���� ��,�� �� �����ִ� �ӽ� ��(�ּ��� �� �� �ʿ��ϸ� ���)
    private JLabel status = new JLabel();

    TextArea fileNames = new TextArea(5, 50); // ÷������ �ּҸ� ����ϴ� textArea
    
    File file = new File(".");
	String path = file.getPath()+"\\image\\";
	
	// �̹��� ������ �ҷ��� �������� �����ϰ� �迭�� ����
    ImageIcon [] images = { new ImageIcon(path+"image.png"), new ImageIcon(path+"video.png"),
			new ImageIcon(path+"pdf.png"), new ImageIcon(path+"voice.png")};
    
    // ÷������ ������ ���� ��ư
    JButton image_btn = new JButton(images[0]);
    JButton video_btn = new JButton(images[1]);
    JButton pdf_btn = new JButton(images[2]);
    JButton voice_btn = new JButton(images[3]);
    
    // +�߰��ϱ�+ �ڵ� �Է½� �ּ� �Ǵ� ��� �� �� �ִ� ��ư ����
    public test_MainPanel() {
    	MyActionListener actionListener = new MyActionListener();
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

        //pre button setting
        Pre_btn.setIcon(Pre_btn_img);
        Pre_btn.setBorderPainted(false);
        Pre_btn.setContentAreaFilled(false);
        Pre_btn.setFocusPainted(false);
        Pre_btn.setPreferredSize(new Dimension(103, 46));
        Pre_btn.setPressedIcon(Pre_press_img);
        Pre_btn.setRolloverIcon(Pre_press_img);

        txtpanel.add(scroll);
        btnpanel.add(Pre_btn);
        btnpanel.add(Next_btn);
    	
    	// title �г� ����
    	JTextField titleText = new JTextField(25);
    	title.add(new JLabel("Title: "));
    	title.add(titleText);
    	
    	// button �г� ����    	
    	fileButton.add(image_btn);
    	fileButton.add(video_btn);
    	fileButton.add(pdf_btn);
    	fileButton.add(voice_btn);
    	
    	image_btn.addActionListener(actionListener);
    	video_btn.addActionListener(actionListener);
    	pdf_btn.addActionListener(actionListener);
    	voice_btn.addActionListener(actionListener);
    	
    	// content �г� ����
    	TextArea contentText = new TextArea("Enter the content here", 25, 60);
    	content.add(contentText);
    	contentText.addFocusListener(new FocusListener() { // textArea Ŭ�� �� �ʱ� ������ �����
    		public void focusLost(FocusEvent e) {}
    		public void focusGained(FocusEvent e) {
    			contentText.setText("");
    		}
    	});
    	
    	// fileName �г� ����
    	fileName.add(new JLabel("Attached files"));
    	fileNames.setEnabled(false);
    	fileName.add(fileNames);
    	
    	add(title);
    	add(fileButton);
    	add(content);
    	add(fileName);
        

        Pre_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ���� �ܰ� ǥ��
            }
        });
        Next_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test_newWindow newWindow = new test_newWindow(); // ���� �Է� â ����
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
    
    class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(image_btn)) { // ��ư �Է� �� ����Ž��â ����
				fileChooser chooser = new fileChooser(new String[]{"gif", "png", "jpg"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
			else if(e.getSource().equals(video_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"avi", "mp4"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
			else if(e.getSource().equals(pdf_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"pdf"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
			else if(e.getSource().equals(voice_btn)) {
				fileChooser chooser = new fileChooser(new String[]{"wav", "mp3"});
				String filePath = chooser.filePath;
				fileNames.setText(fileNames.getText() + filePath + "\n");
			}
		}
    }
    
    private void updateStatus(int linenumber, int columnnumber) {
        status.setText("Line: " + linenumber + " Column: " + columnnumber);
    }

}