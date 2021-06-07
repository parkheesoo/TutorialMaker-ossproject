
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem deleteItem = new JMenuItem("Delete");

    stagePanel StagePanel;
    commentPanel CommentPanel;
	String savepathname; //사용자가 지정한 저장 경로

    public test_Frame(){
    	this.addWindowListener(new WinEvent());

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
        CommentPanel = new commentPanel();
        
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
                CodePanel.readFile(stageTitle);
                CodePanel.setStageTitle(stageTitle);
            }
        });
        
        StagePanel.delete_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//StagePanel.model.remove(StagePanel.stageList.getSelectedIndex());
        		int index = StagePanel.stageList.getSelectedIndex();
        		String stagename = StagePanel.model.get(index).toString();
        		StagePanel.model.remove(index);
        		CommentPanel.stageTitle.setText("No stage");
        		
    			File com = new File(".");
        		String attach_FilePath = com.getPath()  +"\\data\\"+ stagename + "_attachedfile"; //폴더 경로
				File attach_File = new File(attach_FilePath);
		        String code_path = com.getPath() + "\\data\\code_"+stagename+".txt"; //폴더 경로
		    	File code_txt = new File(code_path);
		        String comment_path = com.getPath() + "\\data\\comment_"+stagename+".txt"; //폴더 경로
		    	File comment_txt = new File(comment_path);
				System.out.println(stagename+"삭제");
        		
        		deleteFile delete_file = new deleteFile();
        		delete_file.delete(attach_File);
        		delete_file.delete(code_txt);
        		delete_file.delete(comment_txt);
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

        file.add(newItem);
        //file.add(new JMenuItem("New"));
        file.add(openItem);		   //file.add(new JMenuItem("Open));
        //file.addSeparator();//분리선 삽입        
        file.add(saveItem);        //file.add(new JMenuItem("Save"));
        file.add(saveAsItem);      //file.add(new JMenuItem("SaveAs"));
        
        
        edit.add(deleteItem);
        
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
        newItem.addActionListener(actionListener);
        deleteItem.addActionListener(actionListener);

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
    class WinEvent implements WindowListener{
		public void windowOpened(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {// 윈도우가 닫히려고 할 때
			// 자바 data 파일에 저장되어있는 임시 텍스트 파일 삭제 (불필요하면 삭제)
			ArrayList<String> stagelist;
			stagelist = StagePanel.getStageList();
			System.out.println(stagelist.size());
			
			File com = new File(".");
			for(String str : stagelist) {
				String attach_FilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //폴더 경로
				File attach_File = new File(attach_FilePath);
		        String code_path = com.getPath() + "\\data\\code_"+str+".txt"; //폴더 경로
		    	File code_txt = new File(code_path);
		        String comment_path = com.getPath() + "\\data\\comment_"+str+".txt"; //폴더 경로
		    	File comment_txt = new File(comment_path);
		        String quiz_path = com.getPath() + "\\data\\Quiz_"+str+".txt"; //폴더 경로
		    	File quiz_txt = new File(quiz_path);
				System.out.println(str+"삭제");
				
				//임시파일 삭제 - 굳이 삭제할 필요가 없다 느껴지시면 해당 코드 삭제하셔도 상관 없습니다!			
				deleteFile delete_file = new deleteFile();
				
				delete_file.delete(attach_File); //임시 첨부파일 삭제
				delete_file.delete(code_txt); //임시 코드파일 삭제
				delete_file.delete(comment_txt); //임시 주석파일 삭제
				delete_file.delete(quiz_txt);
			
			}			
		}
		public void windowClosed(WindowEvent e) { }
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
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
					ArrayList<String> stagelist;
					stagelist = StagePanel.getStageList();	
	        		deleteFile delete_file = new deleteFile();
	        		
	        		String[] filenames = showFilesInDIr(savepathname);
					for(int i=0; i<filenames.length; i++) {
						String delete_FilePath = savepathname  +"\\"+ filenames[i];
						File D_file = new File(delete_FilePath);
						System.out.println(filenames[i]+"삭제");						
						delete_file.delete(D_file);
					}					
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
					//File dataFolder = new File(savedatapath);
					
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
			else if(e.getSource().equals(newItem)) {
				int result = JOptionPane.showConfirmDialog(null, "작업 중인 파일을 저장하지 않고 새 파일을 엽니다.\n"
        				, "확인", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) {
        			// 예 버튼 눌렀을 때, 화면 내 모든 내용 삭제
        			ArrayList<String> stagelist;
        			stagelist = StagePanel.getStageList();
        			System.out.println(stagelist.size());
            		
        			//int index = StagePanel.stageList.getSelectedIndex();
            		//String stagename = StagePanel.model.get(index).toString();
            		StagePanel.model.removeAllElements();
            		CommentPanel.stageTitle.setText("No stage");
            		
        			File com = new File(".");
        			for(String str : stagelist) {
        				String attach_FilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //폴더 경로
        				File attach_File = new File(attach_FilePath);
        		        String code_path = com.getPath() + "\\data\\code_"+str+".txt"; //폴더 경로
        		    	File code_txt = new File(code_path);
        		        String comment_path = com.getPath() + "\\data\\comment_"+str+".txt"; //폴더 경로
        		    	File comment_txt = new File(comment_path);
        		        String quiz_path = com.getPath() + "\\data\\Quiz_"+str+".txt"; //폴더 경로
        		        File quiz_txt = new File(quiz_path);
        				System.out.println(str+"삭제");
        				
        				//임시파일 삭제 - 굳이 삭제할 필요가 없다 느껴지시면 해당 코드 삭제하셔도 상관 없습니다!			
        				deleteFile delete_file = new deleteFile();
        				
        				delete_file.delete(attach_File); //임시 첨부파일 삭제
        				delete_file.delete(code_txt); //임시 코드파일 삭제
        				delete_file.delete(comment_txt); //임시 주석파일 삭제     
        				delete_file.delete(quiz_txt); //임시 주석파일 삭제        			
        			}
        		}
			}
			else if(e.getSource().equals(deleteItem)) {
				int result = JOptionPane.showConfirmDialog(null, "작업 중인 파일을 삭제합니다.\n"
        				, "확인", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) {
        			// 예 버튼 눌렀을 때, 화면 내 모든 내용 삭제
        			ArrayList<String> stagelist;
        			stagelist = StagePanel.getStageList();
        			System.out.println(stagelist.size());
            		
        			//int index = StagePanel.stageList.getSelectedIndex();
            		//String stagename = StagePanel.model.get(index).toString();
            		StagePanel.model.removeAllElements();
            		CommentPanel.stageTitle.setText("No stage");
            		
            		String attach_FilePath;
            		String code_path;
            		String comment_path;
            		String quiz_path;
        			File com = new File(".");
        			for(String str : stagelist) {
        				attach_FilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //폴더 경로
        				File attach_File_T = new File(attach_FilePath);
        		        code_path = com.getPath() + "\\data\\code_"+str+".txt"; //폴더 경로
        		        File code_txt_T = new File(code_path);
        		        comment_path = com.getPath() + "\\data\\comment_"+str+".txt"; //폴더 경로
        		        File comment_txt_T = new File(comment_path);
        		        quiz_path = com.getPath() + "\\data\\Quiz_"+str+".txt"; //폴더 경로
        		        File quiz_txt_T = new File(quiz_path);
        				System.out.println(str+"임시파일 삭제");
        				
        				//임시파일 삭제 - 굳이 삭제할 필요가 없다 느껴지시면 해당 코드 삭제하셔도 상관 없습니다!			
        				deleteFile delete_file = new deleteFile();
        				
        				delete_file.delete(attach_File_T); //임시 첨부파일 삭제
        				delete_file.delete(code_txt_T); //임시 코드파일 삭제
        				delete_file.delete(comment_txt_T); //임시 주석파일 삭제
        				delete_file.delete(quiz_txt_T); //임시 퀴즈파일 삭제

    	        		String[] filenames = showFilesInDIr(savepathname);
    					for(int i=0; i<filenames.length; i++) {
    						String delete_FilePath = savepathname  +"\\"+ filenames[i];
    						File D_file = new File(delete_FilePath);
    						System.out.println(filenames[i]+"삭제");						
    						delete_file.delete(D_file);
    					}	
        			}
        		}
			}
    }
    public void filesave() {
    	ArrayList<String> stagelist;// = new ArrayList<String>();
		stagelist = StagePanel.getStageList();
		System.out.println(stagelist.size());
		for(String str : stagelist) {
			System.out.println(str);
			int listindex = stagelist.indexOf(str)+1;
			
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
			
			String ori_quizFilePath = com.getPath()+ "\\data\\"+"Quiz_" + str + ".txt"; //폴더 경로
			String copy_quizFilePath = savepathname+"\\"+"Quiz"+listindex+"_"+str+".txt";
			File ori_quizFile = new File(ori_quizFilePath);
			File copy_quizFile = new File(copy_quizFilePath);
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
		            fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		    		if( ori_commentFile.exists() ){ //파일존재여부확인 
			            FileInputStream comment_fis = new FileInputStream(ori_commentFile); //읽을파일
			            FileOutputStream comment_fos = new FileOutputStream(copy_commentFile); //복사할파일
			          
		    			while((fileByte = comment_fis.read()) != -1) {
		    				comment_fos.write(fileByte);
		    			}
		    			//자원사용종료
		    			comment_fis.close();
		    			comment_fos.close();
		    		}else{ 
		    			System.out.println("파일이 존재하지 않습니다."); 
		    		}
		            ///////////////////////////////////////////////////////

		            fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		    		if( ori_quizFile.exists() ){ //파일존재여부확인 
		    			System.out.println("파일 존재");
			            FileInputStream quiz_fis = new FileInputStream(ori_quizFile); //읽을파일
			            FileOutputStream quiz_fos = new FileOutputStream(copy_quizFile); //복사할파일
			           
		    			while((fileByte = quiz_fis.read()) != -1) {
		    				quiz_fos.write(fileByte);
		    			}
		    			//자원사용종료
		    			quiz_fis.close();
		    			quiz_fos.close();
		    		}else{ 
		    			System.out.println("파일이 존재하지 않습니다."); 
		    		}
		     } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}					            
		}			
	}
    public String[] showFilesInDIr(String dirPath) {
        File dir = new File(dirPath);
        String[] filenames = dir.list();
        for (int i = 0; i < filenames.length; i++) {
            System.out.println("file: " + filenames[i]);
        }
        
        return filenames;
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
