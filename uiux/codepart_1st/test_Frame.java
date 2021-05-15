import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;

public class test_Frame extends JFrame {
    public test_Frame(){
    	Container c = getContentPane();
		c.setLayout(new GridLayout(1, 2));
    	
        codePanel CodePanel = new codePanel();
        commentPanel CommentPanel = new commentPanel();

        setTitle("Tutorial Maker");
        add(CommentPanel);
        add(CodePanel);

        setVisible(true);
        setSize(1000,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

/* 실행을 위해 임시로 프레임을 생성했습니다. */