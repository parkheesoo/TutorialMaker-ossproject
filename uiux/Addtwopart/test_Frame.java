import javax.swing.*;
import java.awt.*;

public class test_Frame extends JFrame {
    test_MainPanel mainPanel;
    public test_Frame(){
        mainPanel = new test_MainPanel();

        setTitle("Tutorial Maker");
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

/* ������ ���� �ӽ÷� �������� �����߽��ϴ�. */