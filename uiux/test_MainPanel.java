import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test_MainPanel extends JPanel {
    private JPanel codepanel = new JPanel();
    private JPanel txtpanel = new JPanel();
    private JPanel btnpanel = new JPanel();

    //private JList list1 = new JList();
    private JButton Pre_btn = new JButton("이전 단계");
    private JButton Next_btn = new JButton("다음 단계");

    private JTextArea textArea1 = new JTextArea(20, 30); //크기조정 필요
    private JScrollPane scroll = new JScrollPane(textArea1);

    test_MainPanel(){
        add(codepanel);
        codepanel.setLayout(new BorderLayout());
        codepanel.add(txtpanel, BorderLayout.CENTER);
        codepanel.add(btnpanel, BorderLayout.SOUTH);

        //add(txtpanel, BorderLayout.EAST);
        txtpanel.add(scroll);
        btnpanel.add(Pre_btn);
        btnpanel.add(Next_btn);

        Pre_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이전 단계 표시
            }
        });
        Next_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test_newWindow newWindow = new test_newWindow();
            }
        });

    }
}
