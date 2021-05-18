import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;


public class stagePanel extends JPanel {
    // stage list ����
    DefaultListModel model = new DefaultListModel();
    JList stageList = new JList(model);
    JScrollPane scrolled = new JScrollPane(stageList);

    //JLabel recentStage= new JLabel("No stage");

    JButton add_btn = new JButton("add");
    JButton delete_btn = new JButton("delete");

    stagePanel() {
        MyListener myListener = new MyListener(); // �̺�Ʈ ������
        setLayout(new BorderLayout());

        // stage list ���� ���
        JLabel listLabel = new JLabel("Stage List");
        listLabel.setHorizontalAlignment(JLabel.CENTER);
        add(listLabel, BorderLayout.NORTH);

        // stage list ǥ��
        add(stageList, BorderLayout.CENTER);

        // buttons �г� ����
        JPanel buttons = new JPanel();
        buttons.add(add_btn);
        buttons.add(delete_btn);
        add(buttons, BorderLayout.SOUTH);

        add_btn.addActionListener(myListener);
        delete_btn.addActionListener(myListener);

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
}