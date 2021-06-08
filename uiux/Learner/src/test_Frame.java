import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class test_Frame extends JFrame {    
	Call_compiler compiler = new Call_compiler();
	
	stagePanel StagePanel = new stagePanel();
    commentPanel CommentPanel = new commentPanel();
    codePanel CodePanel = new codePanel();

    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem saveasItem = new JMenuItem("SaveAs");
    
    JMenuItem runItem = new JMenuItem("run");
    JMenuItem compileItem = new JMenuItem("compile");
	
    public test_Frame(){
        Container c = getContentPane();
        c.setLayout(new BorderLayout(10, 10));
        
        // data�� �����ϱ� ���� ���� ����
        File recent = new File(".");
        String path = recent.getPath() + "\\data"; //���� ���
    	File Folder = new File(path);
    	
    	if (!Folder.exists()) { // �ش� ���丮�� ������� ���丮�� �����մϴ�.
    		try{
    		    Folder.mkdir(); //���� �����մϴ�.
    		    System.out.println("������ �����Ǿ����ϴ�.");
    	        } 
    	        catch(Exception e){
    		    e.getStackTrace();
    		}              
    	}
    	  
        // stage�� �ٲ� ������ �۵�
        StagePanel.stageList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) // ����Ʈ�� ���� ���� ��
            {
        		//CommentPanel.writeFile();
        		//CommentPanel.makeattachedfolder();
        		CodePanel.writeFile(CommentPanel.stageTitle.getText());
        		
        		String stageTitle = (String) StagePanel.stageList.getSelectedValue();
        		int stageIndex = StagePanel.stageList.getSelectedIndex();
        		
                CommentPanel.stageTitle.setText(stageTitle);
                CommentPanel.initContent();
                CommentPanel.readFile(stageIndex, stageTitle);
                CodePanel.readFile(stageTitle);
            }
        });
    
        
        setTitle("Tutorial Maker");
        add(StagePanel, BorderLayout.WEST);
        add(CommentPanel, BorderLayout.CENTER);
        add(CodePanel, BorderLayout.EAST);
        
        StagePanel.setBackground(Color.LIGHT_GRAY);
        // �޴��� ����
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu run = new JMenu("Run");
        
        MyActionListener actionListener = new MyActionListener();
        
        runItem.addActionListener(actionListener);
        compileItem.addActionListener(actionListener);
        openItem.addActionListener(actionListener);
        saveItem.addActionListener(actionListener);
        saveasItem.addActionListener(actionListener);

        file.add(openItem);
        //file.addSeparator();//�и��� ����
        file.add(saveItem);
        file.add(saveasItem);

        edit.add(new JMenuItem("Delete"));

        // run ��ư�� �ӽ÷� �з��߽��ϴ�.
        run.add(runItem); //new JMenuItem("run")
        run.add(compileItem); //new JMenuItem("compile")

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(run);
        setJMenuBar(menuBar);

        setLocationRelativeTo(null);
        setVisible(true);
        setSize(900,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    class MyActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		if(e.getSource().equals(openItem)) {
    			JFileChooser jfc = new JFileChooser();
    			jfc.setCurrentDirectory(new File("."));
    			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    			jfc.showOpenDialog(null);

    			File dir = jfc.getSelectedFile();
    			CommentPanel.setFile(dir);
        		StagePanel.initStage(dir);
    		}
    		else if(e.getSource().equals(saveItem)) {
    			// save Ŭ�� �� �߻�
    		}
    		else if(e.getSource().equals(saveasItem)) {
    			// save as Ŭ�� �� �߻�
    		}
    		else if(e.getSource().equals(compileItem)) {
    			CodePanel.makeCodeFile();
    			//���� ���
    			compiler.compile();
    		}
    		else if(e.getSource().equals(runItem)) {
    			compiler.run();
    		}
    	}
    }
}




// ������ �κ� - Mingw-w64�� �̿��߽��ϴ�.
// �ش� �κп����� ù��° �ڵ��� ��κκ��� �� ���� ��ο� ����Ŷ� ���� �����ּž� �� �� �����ϴ�.
class Call_compiler{
	
	File file = new File(".");
    String path = file.getPath();
	
    public void compile(){
        String s;

        try {
            Process oProcess = new ProcessBuilder("cmd", "/c", "cd", path).start();
            Process Process1 = new ProcessBuilder("cmd", "/c", "gcc", "-o", "test", "out.c").start();

            // �ܺ� ���α׷� ��� �б�
            BufferedReader stdOut1   = new BufferedReader(new InputStreamReader(Process1.getInputStream()));
            BufferedReader stdError1 = new BufferedReader(new InputStreamReader(Process1.getErrorStream()));

            // "ǥ�� ���"�� "ǥ�� ���� ���"�� ���
            compileWindow window = new compileWindow();
            while ((s =   stdOut1.readLine()) != null) System.out.println(s);
            while ((s = stdError1.readLine()) != null) window.setResultArea(s);

            // �ܺ� ���α׷� ��ȯ�� ��� (�� �κ��� �ʼ��� �ƴ�)
            System.out.println("Exit Code: " + Process1.exitValue());
            //System.exit(Process2.exitValue()); // �ܺ� ���α׷��� ��ȯ����, �� �ڹ� ���α׷� ��ü�� ��ȯ������ ���

        } catch (IOException e) { // ���� ó��
            System.err.println("����! �ܺ� ��� ���࿡ �����߽��ϴ�.\n" + e.getMessage());
            System.exit(-1);
        }
    }

    public void run(){
        String s;

        try {
            Process oProcess = new ProcessBuilder("cmd", "/c", "cd", path).start();
            Process Process2 = new ProcessBuilder("cmd", "/c", "test").start();

            // �ܺ� ���α׷� ��� �б�
            BufferedReader stdOut2   = new BufferedReader(new InputStreamReader(Process2.getInputStream()));
            BufferedReader stdError2 = new BufferedReader(new InputStreamReader(Process2.getErrorStream()));

            compileWindow window = new compileWindow();
            while ((s =   stdOut2.readLine()) != null) window.setResultArea(s);
            while ((s = stdError2.readLine()) != null) window.setResultArea(s);


            // �ܺ� ���α׷� ��ȯ�� ��� (�� �κ��� �ʼ��� �ƴ�)
            System.out.println("Exit Code: " + Process2.exitValue());
            //System.exit(Process2.exitValue()); // �ܺ� ���α׷��� ��ȯ����, �� �ڹ� ���α׷� ��ü�� ��ȯ������ ���

        } catch (IOException e) { // ���� ó��
            System.err.println("����! �ܺ� ��� ���࿡ �����߽��ϴ�.\n" + e.getMessage());
            System.exit(-1);
        }
    }

}