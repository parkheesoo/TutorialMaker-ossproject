import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddStage extends JFrame {
    String stageTitle;
    JButton Ok_btn = new JButton("submit");
    JTextField stageText = new JTextField(15);

    public AddStage(){
        setTitle("Add Stage");
        setLayout(new GridLayout(2, 1));

        JPanel getStage = new JPanel();
        JPanel buttons = new JPanel();

        // getStage 패널 구현
        getStage.add(new JLabel("Stage title: "));
        getStage.add(stageText);
        add(getStage);

        // buttons 패널 구현
        JButton Cancle_btn = new JButton("cancle");
        Ok_btn.setBackground(Color.LIGHT_GRAY);
        Cancle_btn.setBackground(Color.LIGHT_GRAY);
        buttons.add(Ok_btn);
        buttons.add(Cancle_btn);
        add(buttons);

        Cancle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // cancle 버튼을 눌렀을 때

                dispose();
            }
        });

        setSize(300,120);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }
}