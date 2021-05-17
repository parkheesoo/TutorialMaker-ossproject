import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test_newWindow extends JFrame {
    private JPanel btnPanel = new JPanel();
    private JPanel txtPanel1 = new JPanel();
    private JPanel txtPanel2 = new JPanel();

    private JButton OK_btn = new JButton("확인");
    private JButton Cancle_btn = new JButton("취소");
    private JLabel in_label = new JLabel("input");
    private JLabel out_label = new JLabel("output");
    private JTextField in_txt = new JTextField(10);
    private JTextField out_txt = new JTextField(15);


    public test_newWindow(){
        setTitle("다음 단계 조건 설정");
        setLayout(new BorderLayout());

        txtPanel1.add(in_label);
        txtPanel1.add(in_txt);
        txtPanel2.add(out_label);
        txtPanel2.add(out_txt);

        btnPanel.add(Cancle_btn);
        btnPanel.add(OK_btn);

        getContentPane().add(txtPanel1, BorderLayout.NORTH);
        getContentPane().add(txtPanel2, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        Cancle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(300,150);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

    }
}