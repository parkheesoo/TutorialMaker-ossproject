import javax.swing.*;
import java.awt.*;

public class test_Frame extends JFrame {
    test_MainPanel mainPanel;
    public test_Frame(){
        mainPanel = new test_MainPanel();

        setTitle("Tutorial Maker");
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        setSize(600,450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

/* Frame에서 다음 페이지로 넘어갈 때마다 넘어갈 수 있는 Panel 생성 및 전환을 담당하면 될 것 같습니다.*/