import javax.swing.*;
import java.awt.*;

public class test_newWindow extends JFrame {
    public test_newWindow(){
        setTitle("다음 단계 조건 설정");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel btnPanel = new JPanel();
        JPanel txtPanel1 = new JPanel();
        JPanel txtPanel2 = new JPanel();

        //setContentPane(panel1);

        JButton OK_btn = new JButton("확인");
        JButton Cancle_btn = new JButton("취소");
        JLabel in_label = new JLabel("input");
        JLabel out_label = new JLabel("output");
        JTextField in_txt = new JTextField(10);
        JTextField out_txt = new JTextField(15);

        setLayout(new BorderLayout());

        txtPanel1.add(in_label);
        txtPanel1.add(in_txt);

        txtPanel2.add(out_label);
        txtPanel2.add(out_txt);

        btnPanel.add(Cancle_btn);
        btnPanel.add(OK_btn);

        add(txtPanel1, BorderLayout.NORTH);
        add(txtPanel2, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setSize(300,150);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }
}
