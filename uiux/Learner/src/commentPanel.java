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
    private Dimension size = new Dimension();//사이즈를 지정하기 위한 객체 생성
	
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
    	JTextArea text;
    	
    	try {
            String s;
            File read = new File(path);
            BufferedReader bReader = null;
            bReader = new BufferedReader(new FileReader(read));
            
            // 더이상 읽어들일게 없을 때까지 읽어들이게 합니다.
            while((s = bReader.readLine()) != null) {
            	
            	if (s.startsWith("[image]")) {
            		text = new JTextArea(comment_str.toString());
            		text.setColumns(30);
            		text.setLineWrap(true);
            		text.setWrapStyleWord(true);
            		//text.setRows(text.getLineCount());
            		content.add(text);
            		comment_str.setLength(0);
            		
            		ImageIcon img_icon = new ImageIcon(file.getPath() + "\\" + stageTitle + "_attachedfile\\" + s.substring(7));	
            		content.add(new JLabel(imageSetSize(img_icon, 365, 280)));
            	}
            	else {
            		comment_str.append(s);
                    comment_str.append("\n");
            	}
            }
        } catch(IOException e) {}
    	
    	if (comment_str.toString().length() != 0) {
    		text = new JTextArea(comment_str.toString());
    		text.setColumns(30);
    		text.setLineWrap(true);
    		text.setWrapStyleWord(true);
    		content.add(text);
    	}

    }
    
    public void initContent() {
    	content = new JPanel();
    	content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    	//content.setLayout(new GridLayout(4, 1));
    	//content.setPreferredSize(new Dimension(365, 300));
        scroll.setViewportView(content); //스크롤 팬 위에 패널을 올린다.
    	add(scroll, BorderLayout.CENTER);
    }
    
    ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
    
}