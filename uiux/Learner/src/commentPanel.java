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
    private JPanel title = new JPanel(); // 제목 입력을 위한 패널
    private JPanel content = new JPanel(); // 내용 출력을 위한 패널

    JLabel stageTitle= new JLabel("No stage");
    private JScrollPane scroll = new JScrollPane();
    private File file = new File("");
	
    public commentPanel() {
    	setLayout(new BorderLayout());

        // title 패널 구현
        title.add(new JLabel("Title: "));
        title.add(stageTitle);

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(title, BorderLayout.NORTH);
        initContent();  // content 패널 초기화

        setSize(400,700);
        setVisible(true);
    }

    
    public void setFile(File dir) {
    	file = dir;
    }
    
    // 선택된 text 파일을 content에 읽어오기
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
            
            // 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
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
    
    // content 패널 초기 설정
    public void initContent() {
    	content = new JPanel();
    	content.setPreferredSize(new Dimension(365, 0));
    	content.setBackground(Color.WHITE);
        scroll.setViewportView(content); //스크롤 팬 위에 패널을 올린다.
    	add(scroll, BorderLayout.CENTER);
    }
    
    // 입력받은 문자열로 jtextarea 생성
    public int addText(String string) {
    	JTextArea text = new JTextArea(string);
		text.setColumns(31);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		content.add(text);
		
		return (text.getLineCount() * 36) + 10;
    }
    
    // 이미지 크기 조정
    ImageIcon imageSetSize(ImageIcon icon, int i, int j) {
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
    
}