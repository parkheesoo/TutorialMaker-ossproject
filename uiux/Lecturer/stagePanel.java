import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;


public class stagePanel extends JPanel {
    // stage list ����
    DefaultListModel model = new DefaultListModel();
    JList stageList = new JList(model);
    JScrollPane scrolled = new JScrollPane(stageList);

    JButton add_btn = new JButton("add");
    JButton delete_btn = new JButton("delete");
    
    
    stagePanel() {
        MyListener myListener = new MyListener(); // �̺�Ʈ ������
        setLayout(new BorderLayout());

        // stage list ���� ���
        JPanel listLabelPanel = new JPanel();
        listLabelPanel.setBackground(Color.LIGHT_GRAY);
        add(listLabelPanel, BorderLayout.NORTH);
        
        JLabel listLabel = new JLabel("Stage List");
        listLabel.setHorizontalAlignment(JLabel.CENTER);
        listLabelPanel.add(listLabel);
        
        // stage list ǥ��
        stageList.setBorder(new LineBorder(Color.lightGray));
        add(stageList, BorderLayout.CENTER);
        
        // buttons �г� ����
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
        public void actionPerformed(ActionEvent e) { // add ��ư�� ������ ��
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