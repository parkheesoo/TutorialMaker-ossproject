import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;


public class stagePanel extends JPanel {
    // stage list 생성
    DefaultListModel model = new DefaultListModel();
    JList stageList = new JList(model);

    stagePanel() {
        setLayout(new BorderLayout());
        JScrollPane scrolled = new JScrollPane(stageList);
        
        // stage list 문구 출력
        JLabel listLabel = new JLabel("Stage List");
        listLabel.setHorizontalAlignment(JLabel.CENTER);
        add(listLabel, BorderLayout.NORTH);
                       
        // stage list 표시
        //stageList.setEnabled(false);
        stageList.setBorder(new LineBorder(Color.lightGray));
        stageList.setPreferredSize(new Dimension(100, 700));
        add(stageList, BorderLayout.CENTER);
        
        // skip button 구현
        //JPanel buttons = new JPanel();
        
        /*JButton back_btn = new JButton("back");
        back_btn.setBackground(Color.LIGHT_GRAY);
        buttons.add(back_btn);
        back_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int currentIndex = stageList.getSelectedIndex();
        		if (currentIndex > 0)
        			setFocus(stageList.getSelectedIndex()-1);
        	}
        });  

        JButton skip_btn = new JButton("skip");
        skip_btn.setBackground(Color.LIGHT_GRAY);
        buttons.add(skip_btn);
        skip_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setFocus(stageList.getSelectedIndex()+1);
        	}
        });     
        add(buttons, BorderLayout.SOUTH);*/

        setSize(100,700);
        setVisible(true);
    }
    
    public void setFocus(int index) {
    	stageList.setSelectedIndex(index);
    }
    
    public void initStage(File dir) {
    	String[] files = dir.list((f,name)->name.startsWith("comment"));
		
		if (files.length > 0) {    	
			model.removeAllElements();
			for (int i = 0; i < files.length; i++) {
				String stageName = files[i].substring(9, files[i].length()-4); // 앞 9글자, 뒤 4글자 제거
				model.addElement(stageName);
			}
			setFocus(0);
		}
    }
    public ArrayList<String> getStageList(){
    	
    	ArrayList<String> stagelist = new ArrayList<String>();
    	
    	int listSize = stageList.getModel().getSize();
    	for(int i=0; i<listSize; i++) {
    		Object item = stageList.getModel().getElementAt(i);
    		stagelist.add(item.toString());   		
    		System.out.println("item = "+item);
    	}
    	
    	return stagelist;
    }
}