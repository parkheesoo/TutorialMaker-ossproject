import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;


public class stagePanel extends JPanel {
    // stage list 생성
    DefaultListModel model = new DefaultListModel();
    JList stageList = new JList(model);
    JScrollPane scrolled = new JScrollPane(stageList);

    JButton add_btn = new JButton("add");
    JButton delete_btn = new JButton("delete");
    
    
    stagePanel() {
        MyListener myListener = new MyListener(); // 이벤트 리스너
        setLayout(new BorderLayout());

        // stage list 문구 출력
        JPanel listLabelPanel = new JPanel();
        listLabelPanel.setBackground(Color.LIGHT_GRAY);
        add(listLabelPanel, BorderLayout.NORTH);
        
        JLabel listLabel = new JLabel("Stage List");
        listLabel.setHorizontalAlignment(JLabel.CENTER);
        listLabelPanel.add(listLabel);
        
        // stage list 표시
        stageList.setBorder(new LineBorder(Color.lightGray));
        add(stageList, BorderLayout.CENTER);
        
        // buttons 패널 구현
        JPanel buttons = new JPanel();
        buttons.add(add_btn);
        buttons.add(delete_btn);
        add(buttons, BorderLayout.SOUTH);

        add_btn.addActionListener(myListener);
        delete_btn.addActionListener(myListener);
        add_btn.setBackground(Color.LIGHT_GRAY);
        delete_btn.setBackground(Color.LIGHT_GRAY);
        setSize(100,700);
        setVisible(true);
    }

    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { // add 버튼을 눌렀을 때
            if(e.getSource().equals(add_btn)) {
                AddStage addStage = new AddStage();
                addStage.Ok_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        model.addElement(addStage.stageText.getText());
                        addStage.dispose();
                    }
                });
            }
        }
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