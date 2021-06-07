import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class commentPanel extends JPanel {
    private JPanel title = new JPanel(); // ���� �Է��� ���� �г�
    private JPanel content = new JPanel(); // ���� ����� ���� �г�

    JLabel stageTitle= new JLabel("No stage");
    private JScrollPane scroll = new JScrollPane();
    private File file = new File("");
	
    public commentPanel() {
    	setLayout(new BorderLayout());

        // title �г� ����
        title.add(new JLabel("Title: "));
        title.add(stageTitle);

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(title, BorderLayout.NORTH);
        initContent();  // content �г� �ʱ�ȭ

        setSize(400,700);
        setVisible(true);
    }

    
    public void setFile(File dir) {
    	file = dir;
    }
    
    // ���õ� text ������ content�� �о����
    public void readFile(int stageIndex, String stageTitle){
    	String path = file.getPath() + "\\comment" + Integer.toString(stageIndex + 1)
    						+ "_" + stageTitle + ".txt";
    	StringBuffer comment_str = new StringBuffer("");
    	//JTextArea text;
    	int height = 0;
    	
    	try {
            String s;
            File read = new File(path);
            BufferedReader bReader = null;
            bReader = new BufferedReader(new FileReader(read));
            
            // ���̻� �о���ϰ� ���� ������ �о���̰� �մϴ�.
            while((s = bReader.readLine()) != null) {
            	
            	if (s.startsWith("[image]")) {
            		
            		height += addText(comment_str.toString().substring(0, comment_str.toString().length() - 1));
            		comment_str.setLength(0);
            		
            		File img_file = new File(file.getPath() + "\\" + stageTitle + "_attachedfile\\" + s.substring(7));
                    BufferedImage img = ImageIO.read(img_file);
            		
                    Image image = ImageIO.read(new File(file.getPath() + "\\" + stageTitle + "_attachedfile\\" + s.substring(7)));
            		int imageWidth = image.getWidth(null);
                    int imageHeight = image.getHeight(null);
                    int h = 350 * imageHeight / imageWidth;
                    
            		content.add(new JLabel(imageSetSize(new ImageIcon(img), 350, h)));
            		height += h + 10;
            	}
            	else {
            		comment_str.append(s);
                    comment_str.append("\n");
            	}
            }
            bReader.close();
        } catch(IOException e) {}
    	
    	if (comment_str.toString().length() != 0) {
    		height += addText(comment_str.toString().substring(0, comment_str.toString().length() - 1));
    	}
    	
    	content.setPreferredSize(new Dimension(365, height));
    }
    
    // content �г� �ʱ� ����
    public void initContent() {
    	content = new JPanel();
    	content.setPreferredSize(new Dimension(365, 0));
    	content.setBackground(Color.WHITE);
        scroll.setViewportView(content); //��ũ�� �� ���� �г��� �ø���.
    	add(scroll, BorderLayout.CENTER);
    }
    
    // �Է¹��� ���ڿ��� jtextarea ����
    public int addText(String string) {
    	JTextArea text = new JTextArea(string);
		text.setColumns(31);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		content.add(text);
		
		return (text.getLineCount() * 36) + 10;
    }
    
    // �̹��� ũ�� ����
    ImageIcon imageSetSize(ImageIcon icon, int i, int j) {
		Image ximg = icon.getImage();  //ImageIcon�� Image�� ��ȯ.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
    
}