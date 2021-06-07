import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;


public class stagePanel extends JPanel {
    // stage list ����
    DefaultListModel model = new DefaultListModel();
    JList stageList = new JList(model);

    stagePanel() {
        setLayout(new BorderLayout());
        JScrollPane scrolled = new JScrollPane(stageList);
        
        // stage list ���� ���
        JLabel listLabel = new JLabel("Stage List");
        listLabel.setHorizontalAlignment(JLabel.CENTER);
        add(listLabel, BorderLayout.NORTH);
                       
        // stage list ǥ��
        stageList.setBorder(new LineBorder(Color.lightGray));
        stageList.setPreferredSize(new Dimension(100, 700));
        add(stageList, BorderLayout.CENTER);

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
				String stageName = files[i].substring(9, files[i].length()-4); // �� 9����, �� 4���� ����
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