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
    public test_Frame(){
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        
        // data를 저장하기 위한 폴더 생성
        File recent = new File(".");
        String path = recent.getPath() + "\\data"; //폴더 경로
    	File Folder = new File(path);
    	
    	if (!Folder.exists()) { // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
    		try{
    		    Folder.mkdir(); //폴더 생성합니다.
    		    System.out.println("폴더가 생성되었습니다.");
    	        } 
    	        catch(Exception e){
    		    e.getStackTrace();
    		}              
    	}
    	
        stagePanel StagePanel = new stagePanel();
        commentPanel CommentPanel = new commentPanel();
        codePanel CodePanel = new codePanel();
        
        // stage가 바뀔 때마다 작동
        StagePanel.stageList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) // 리스트를 선택 했을 때
            {
        		CommentPanel.writeFile();
        		CommentPanel.makeattachedfolder();
        		CodePanel.writeFile(CommentPanel.stageTitle.getText());
        		String stageTitle = (String) StagePanel.stageList.getSelectedValue();
                CommentPanel.stageTitle.setText(stageTitle);
                CommentPanel.readFile(stageTitle);
                CodePanel.readFile(stageTitle);
            }
        });
        
        StagePanel.delete_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		StagePanel.model.remove(StagePanel.stageList.getSelectedIndex());
        		CommentPanel.stageTitle.setText("No stage");
        	}
        });
        
        setTitle("Tutorial Maker");
        add(StagePanel, BorderLayout.WEST);
        add(CommentPanel, BorderLayout.CENTER);
        add(CodePanel, BorderLayout.EAST);
        
        StagePanel.setBackground(Color.LIGHT_GRAY);
        // 메뉴바 구현
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

        // run 버튼을 임시로 분류했습니다.
        JMenuItem runItem = new JMenuItem("run");
        JMenuItem compileItem = new JMenuItem("compile");
        run.add(runItem); //new JMenuItem("run")
        run.add(compileItem); //new JMenuItem("compile")

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(run);
        setJMenuBar(menuBar);

        setLocationRelativeTo(null);
        setVisible(true);
        setSize(900,720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Call_compiler compiler = new Call_compiler();
        compileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CodePanel.makeCodeFile();
                //파일 출력
                compiler.compile();
            }
        });
        runItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compiler.run();
            }
        });

    }
}


// 컴파일 부분 - Mingw-w64을 이용했습니다.
// 해당 부분에서의 첫번째 코드의 경로부분은 제 파일 경로에 맞춘거라 따로 맞춰주셔야 할 것 같습니다.
class Call_compiler{
	
	File file = new File(".");
    String path = file.getPath();
	
    public void compile(){
        String s;

        try {
            Process oProcess = new ProcessBuilder("cmd", "/c", "cd", path).start();
            Process Process1 = new ProcessBuilder("cmd", "/c", "gcc", "-o", "test", "out.c").start();

            // 외부 프로그램 출력 읽기
            BufferedReader stdOut1   = new BufferedReader(new InputStreamReader(Process1.getInputStream()));
            BufferedReader stdError1 = new BufferedReader(new InputStreamReader(Process1.getErrorStream()));

            // "표준 출력"과 "표준 에러 출력"을 출력
            compileWindow window = new compileWindow();
            while ((s =   stdOut1.readLine()) != null) System.out.println(s);
            while ((s = stdError1.readLine()) != null) window.setResultArea(s);

            // 외부 프로그램 반환값 출력 (이 부분은 필수가 아님)
            System.out.println("Exit Code: " + Process1.exitValue());
            //System.exit(Process2.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로 삼기

        } catch (IOException e) { // 에러 처리
            System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
            System.exit(-1);
        }
    }

    public void run(){
        String s;

        try {
            Process oProcess = new ProcessBuilder("cmd", "/c", "cd", path).start();
            Process Process2 = new ProcessBuilder("cmd", "/c", "test").start();

            // 외부 프로그램 출력 읽기
            BufferedReader stdOut2   = new BufferedReader(new InputStreamReader(Process2.getInputStream()));
            BufferedReader stdError2 = new BufferedReader(new InputStreamReader(Process2.getErrorStream()));

            compileWindow window = new compileWindow();
            while ((s =   stdOut2.readLine()) != null) window.setResultArea(s);
            while ((s = stdError2.readLine()) != null) window.setResultArea(s);


            // 외부 프로그램 반환값 출력 (이 부분은 필수가 아님)
            System.out.println("Exit Code: " + Process2.exitValue());
            //System.exit(Process2.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로 삼기

        } catch (IOException e) { // 에러 처리
            System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
            System.exit(-1);
        }
    }

}