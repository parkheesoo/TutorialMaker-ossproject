import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;

public class test_Frame extends JFrame {
    public test_Frame(){
    	Container c = getContentPane();
		c.setLayout(new BorderLayout());
    	
		stagePanel StagePanel = new stagePanel();
        codePanel CodePanel = new codePanel();
        commentPanel CommentPanel = new commentPanel();
        
        setTitle("Tutorial Maker");
        add(StagePanel, BorderLayout.WEST);
        add(CommentPanel, BorderLayout.CENTER);
        add(CodePanel, BorderLayout.EAST);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu run = new JMenu("Run");
        
        file.add(new JMenuItem("New"));
        file.add(new JMenuItem("Open"));
        //file.addSeparator();//분리선 삽입
        file.add(new JMenuItem("Save"));
        file.add(new JMenuItem("SaveAs"));
        
        edit.add(new JMenuItem("Delete"));
        
        run.add(new JMenuItem("run"));
        run.add(new JMenuItem("compile"));
        
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(run);
        setJMenuBar(menuBar);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(1000,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

/* 실행을 위해 임시로 프레임을 생성했습니다. */