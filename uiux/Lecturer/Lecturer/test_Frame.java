
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class test_Frame extends JFrame {    
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem saveAsItem = new JMenuItem("SaveAs");
    JMenuItem openItem = new JMenuItem("Open");
    
    stagePanel StagePanel;
	String savepathname; //사용자가 지정한 저장 경로

    public test_Frame(){
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        MyActionListener actionListener = new MyActionListener();
        
        
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
    	
        //stagePanel StagePanel = new stagePanel();
    	StagePanel = new stagePanel();
        commentPanel CommentPanel = new commentPanel();
        
        codePanel CodePanel = new codePanel();
        CommentPanel.setLayout(null);
        
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
                //newWindow.stageTitle.setText(stageTitle);
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
        
        //StagePanel.setBackground(Color.WHITE);
        CommentPanel.setBackground(Color.WHITE);
        CodePanel.setBackground(Color.WHITE);
        // 메뉴바 구현
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu run = new JMenu("Run");

        JMenuItem newItem = new JMenuItem("New");
        file.add(newItem);
        //file.add(new JMenuItem("New"));
        file.add(openItem);		   //file.add(new JMenuItem("Open));
        //file.addSeparator();//분리선 삽입        
        file.add(saveItem);        //file.add(new JMenuItem("Save"));
        file.add(saveAsItem);      //file.add(new JMenuItem("SaveAs"));
        
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
        setSize(1000,820);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        //actionListener 추가하기
        saveItem.addActionListener(actionListener);
        saveAsItem.addActionListener(actionListener);
        openItem.addActionListener(actionListener);
        
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
        
        newItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// 프로그램 새로 열기
        	}
        });

    }
    public static void copy(File sourceF, File targetF){
    	File[] target_file = sourceF.listFiles();
    	for (File file : target_file) {
    		File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
    		if(file.isDirectory()){
    			temp.mkdir();
    			copy(file, temp);
    		} else {
    			FileInputStream fis = null;
    			FileOutputStream fos = null;
    			try {
    				fis = new FileInputStream(file);
    				fos = new FileOutputStream(temp) ;
    				byte[] b = new byte[4096];
    				int cnt = 0;
    				while((cnt=fis.read(b)) != -1){
    					fos.write(b, 0, cnt);
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			} finally{
    				try {
    					fis.close();
    					fos.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}	
    			}
    		}
    	}	
    }
    class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(saveItem)) {
				if(savepathname == null) {
					savepathname = "C:\\savefile";
					File Folder = new File(savepathname);
					
					int addnum = 1;
					String t_savepathname = savepathname;
					
					while(Folder.exists()) {
						System.out.println("들어왔나요?");
						t_savepathname = savepathname+"_("+addnum+")";
						Folder = new File(t_savepathname);
						addnum++;
					}
					savepathname = t_savepathname;
					String savedatapath = savepathname+"\\data";
					//File dataFolder = new File(savedatapath);
					
					if(!Folder.exists()) {
						try {
							Folder.mkdir(); //폴더 생성
							//dataFolder.mkdir(); //data 폴더 생성
							
							System.out.println("폴더가 생성되었습니다.");
							System.out.println(savepathname);
						
						}catch(Exception ex) {
							ex.getStackTrace();
						}
					}else {
						System.out.println("이미 폴더가 생성되었습니다.");
						System.out.println(savepathname);					
					}
					filesave();
				}
				else {
					filesave();
				}
			}
			else if(e.getSource().equals(saveAsItem)) { // 버튼 입력 시 파일탐색창 열기		
				
		    	File savefile;
				//String savepathname; //사용자가 지정한 저장 경로
				String savedatapath; // + data 폴더 저장 경로
				
				StagePanel.getStageList();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File ("C:\\"));
				chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY);
						
				int re=chooser.showSaveDialog(null);
				
				if(re==JFileChooser.APPROVE_OPTION) {
					savefile = chooser.getSelectedFile();
					String foldername = JOptionPane.showInputDialog("파일명을 입력하세요.");
					savepathname = savefile.getAbsolutePath()+"\\"+foldername;
					savedatapath = savepathname+"\\data";
					File Folder = new File(savepathname);
					File dataFolder = new File(savedatapath);
					
					if(!Folder.exists() && foldername!=null) {
						try {
							Folder.mkdir(); //폴더 생성
							//dataFolder.mkdir(); //data 폴더 생성
							
							System.out.println("폴더가 생성되었습니다.");
							System.out.println(savepathname);
							
						}catch(Exception ex) {
							ex.getStackTrace();
						}
					}else {
						if(foldername==null) System.out.println("폴더 생성이 취소되었습니다.");
						else{
							System.out.println("이미 폴더가 생성되었습니다.");
							System.out.println(savepathname);
						}
					}					
				}else {
					JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.");
					return;
				}
				
				filesave();				
			}
			else if(e.getSource().equals(openItem)) {
				// 경로 지정해서 파일 열 수 있도록
		        JFileChooser jfc = new JFileChooser();
		        jfc.setCurrentDirectory(new File("/"));
		        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        
		        int returnVal = jfc.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭
		            savepathname = jfc.getSelectedFile().toString();
		            System.out.println(savepathname);
			}
		}
    }
    public void filesave() {
    	ArrayList<String> stagelist;// = new ArrayList<String>();
		stagelist = StagePanel.getStageList();
		System.out.println(stagelist.size());
		for(String str : stagelist) {
			System.out.println(str);
			int listindex = stagelist.indexOf(str);
			
			File com = new File(".");
        // 자바 파일 경로에 저장되어있는 코드 및 주석 복사
        // 파일 객체 생성
			String oriFilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //폴더 경로
			File oriFile = new File(oriFilePath);
			String copyFilePath = savepathname+"\\" + str + "_attachedfile";
			File copyFile = new File(copyFilePath);
			try {
				copyFile.mkdir(); //data 폴더 생성		
			}catch(Exception ex) {
				ex.getStackTrace();
			}
			
			copy(oriFile, copyFile);
			
			
			String ori_codeFilePath = com.getPath() +"\\data\\"+"code_"+str+".txt"; //폴더 경로
			String copy_codeFilePath = savepathname +"\\"+"code"+listindex+"_"+str+".txt";
			File ori_codeFile = new File(ori_codeFilePath);
			File copy_codeFile = new File(copy_codeFilePath);
			
			String ori_commentFilePath = com.getPath()+ "\\data\\"+"comment_" + str + ".txt"; //폴더 경로
			String copy_commentFilePath = savepathname+"\\"+"comment"+listindex+"_"+str+".txt";
			File ori_commentFile = new File(ori_commentFilePath);
			File copy_commentFile = new File(copy_commentFilePath);
			 try {
		            
		            FileInputStream code_fis = new FileInputStream(ori_codeFile); //읽을파일
		            FileOutputStream code_fos = new FileOutputStream(copy_codeFile); //복사할파일
		            
		            int fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = code_fis.read()) != -1) {
		                code_fos.write(fileByte);
		            }
		            //자원사용종료
		            code_fis.close();
		            code_fos.close();
		            
		            ///////////////////////////////////////////////////////
		            FileInputStream comment_fis = new FileInputStream(ori_commentFile); //읽을파일
		            FileOutputStream comment_fos = new FileOutputStream(copy_commentFile); //복사할파일
		            
		            fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = comment_fis.read()) != -1) {
		                comment_fos.write(fileByte);
		            }
		            //자원사용종료
		            comment_fis.close();
		            comment_fos.close();
		           
		     } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					            

		}			
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
}