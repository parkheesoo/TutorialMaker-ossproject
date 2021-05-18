import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class commentPanel extends JPanel {
    private JPanel title = new JPanel(); // ���� �Է��� ���� �г�
    private JPanel fileButton = new JPanel(); // ÷������ ��ư�� ���� �г�
    private JPanel content = new JPanel(); // ���� �Է��� ���� �г�
    private JPanel fileName = new JPanel(); // ÷���� ������ �����ֱ� ���� �г�

    JLabel stageTitle= new JLabel("No stage");
    TextArea contentText = new TextArea("Enter the content here", 25, 50);
    TextArea fileNames = new TextArea(5, 50); // ÷������ �ּҸ� ����ϴ� textArea

    // �̹��� ������ ���� ���� ��� ����
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

    public commentPanel() {
        MyActionListener actionListener = new MyActionListener();

        // title �г� ����
        title.add(new JLabel("Title: "));
        title.add(stageTitle);

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
        content.add(contentText);
        contentText.addFocusListener(new FocusListener() { // textArea Ŭ�� �� �ʱ� ������ �����
            public void focusLost(FocusEvent e) {}
            public void focusGained(FocusEvent e) {
            	if (contentText.getText().equals("Enter the content here")) {
            		contentText.setText("");
            	}
            }
        });

        // fileName �г� ����
        fileName.setLayout(new BorderLayout());
        fileName.add(new JLabel("Attached files"), BorderLayout.NORTH);
        fileNames.setEnabled(false);
        fileName.add(fileNames, BorderLayout.CENTER);

        add(title);
        add(fileButton);
        add(content);
        add(fileName);

        setSize(500,700);
        setVisible(true);
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
    
    // ���� content ������ text ���Ϸ� ����
    public void writeFile(){
    	String comment_str = contentText.getText();
    	if (!comment_str.equals("Enter the content here")) { // stage�� ������ ���� ����
        	String File_name = "data\\comment_" + stageTitle.getText() + ".txt"; //Change to desired extension(ex. ".c")
        	try {
        		FileWriter writer = new FileWriter(File_name);
        		writer.write(comment_str);
        		writer.close();
        	} catch (IOException ex) {}
        }
    }
    
    // ���õ� text ������ content�� �о����
    public void readFile(String stageTitle){
    	String path = file.getPath()+"\\data\\comment_" + stageTitle + ".txt";
    	StringBuffer comment_str = new StringBuffer("");
    	try {
            String s;
            File read = new File(path);
            BufferedReader bReader = null;
            bReader = new BufferedReader(new FileReader(read));
            
            // ���̻� �о���ϰ� ���� ������ �о���̰� �մϴ�.
            while((s = bReader.readLine()) != null) {
                comment_str.append(s);
                comment_str.append("\n");
            }
        } catch(IOException e) {}
    	
    	if (comment_str.toString().length() == 0)
    		contentText.setText("Enter the content here");
    	else
    		contentText.setText(comment_str.toString());
    }
}