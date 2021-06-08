import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LangFrame extends JFrame implements ActionListener{
    private JList<String> langList;
    private DefaultListModel<String> langModel;
    private String selectedLang;
    private Button launchButton;

    LangFrame(){
    	
        setPreferredSize(new Dimension(300, 300));
        setSize(300, 200);
        launchButton = new Button("Launch");
        launchButton.addActionListener(this);
        langModel = new DefaultListModel<String>();
        langModel.addElement("C");
        langModel.addElement("C++");
        langModel.addElement("Java");
        langModel.addElement("Python");
        langList = new JList(langModel);
        add(langList, BorderLayout.CENTER);
        add(launchButton, BorderLayout.SOUTH);
        langList.setPreferredSize(new Dimension(250, 250));
        langList.setSize(250, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(langList.getSelectedValue().contentEquals("C")){
            test_Frame CFrame = new test_Frame();
            dispose();
        }
    }
}
