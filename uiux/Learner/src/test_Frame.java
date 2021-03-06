import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class test_Frame extends JFrame {    
	Call_compiler compiler = new Call_compiler();
	
	stagePanel StagePanel = new stagePanel();
    commentPanel CommentPanel = new commentPanel();
    codePanel CodePanel = new codePanel();

    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem saveasItem = new JMenuItem("SaveAs");
    
    JMenuItem deleteItem = new JMenuItem("Delete");
    
    JMenuItem runItem = new JMenuItem("run");
    JMenuItem compileItem = new JMenuItem("compile");
	
    private String savepathname;
    private String openpath;
    
    public test_Frame(){
    	this.addWindowListener(new WinEvent());
    	
    	Container c = getContentPane();
        c.setLayout(new BorderLayout(10, 10));
        
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
    	  
        // stage가 바뀔 때마다 작동
        StagePanel.stageList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) // 리스트를 선택 했을 때
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
                CodePanel.setStageTitle(stageTitle);
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
        
        MyActionListener actionListener = new MyActionListener();
        
        runItem.addActionListener(actionListener);
        compileItem.addActionListener(actionListener);
        openItem.addActionListener(actionListener);
        saveItem.addActionListener(actionListener);
        saveasItem.addActionListener(actionListener);
        deleteItem.addActionListener(actionListener);

        file.add(openItem);
        //file.addSeparator();//분리선 삽입
        file.add(saveItem);
        file.add(saveasItem);

        edit.add(deleteItem);

        // run 버튼을 임시로 분류했습니다.
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
    			openpath = dir.getAbsolutePath();
    			CommentPanel.setFile(dir);
        		StagePanel.initStage(dir);
        		CodePanel.setFile(dir);
        		
                String[] filenames = dir.list();
                for (int i = 0; i < filenames.length; i++) {
                	if(filenames[i].contains("_S.txt")) {
                		System.out.print("학습자 작성 코드// ");
                                		
                		File com = new File(".");
                    	String ori_LcodeFilePath = openpath +"\\"+filenames[i]; //폴더 경로
                    	
                    	String filename = filenames[i].replace("_S", "");
            			String copy_LcodeFilePath = com.getPath()+"\\data\\"+filename; //+filenames[i].replace("_S", "");
            			File ori_LcodeFile = new File(ori_LcodeFilePath);
            			File copy_LcodeFile = new File(copy_LcodeFilePath);           		
            			 try {
            		            
            		            FileInputStream Lcode_fis = new FileInputStream(ori_LcodeFile); //읽을파일
            		            FileOutputStream Lcode_fos = new FileOutputStream(copy_LcodeFile); //복사할파일
            		            
            		            int fileByte = 0; 
            		            // fis.read()가 -1 이면 파일을 다 읽은것
            		            while((fileByte = Lcode_fis.read()) != -1) {
            		                Lcode_fos.write(fileByte);
            		            }
            		            //자원사용종료
            		            Lcode_fis.close();
            		            Lcode_fos.close();

            		     } catch (IOException e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            		     }	
                	}
                    System.out.println("file: " + filenames[i]);
                }
                
        		CommentPanel.readFile(StagePanel.stageList.getSelectedIndex(), StagePanel.stageList.getSelectedValue().toString());
        		CodePanel.readFile(StagePanel.stageList.getSelectedValue().toString());
        	
    		}
    		else if(e.getSource().equals(saveItem)) {
    			// save 클릭 시 발생
    			if(savepathname == null) {
    				savepathname = openpath;
    				System.out.println("첫 번째 save: "+savepathname);
					filesave2();
				}
				else {    				
					System.out.println("save: "+savepathname);
					filesave2(); //학습자 작성 코드만 다시 저장
				}
    		}
    		else if(e.getSource().equals(saveasItem)) {
    			// save as 클릭 시 발생
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
					//savedatapath = savepathname+"\\data";
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
		        File dir = jfc.getSelectedFile();
    			openpath = dir.getAbsolutePath();
    			CommentPanel.setFile(dir);
		        if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭
		            savepathname = jfc.getSelectedFile().toString();
		            System.out.println(savepathname);
		        }
		        //StagePanel.initStage(dir);
        		//CodePanel.setFile(dir);
		    }
    		else if(e.getSource().equals(compileItem)) {
    			CodePanel.makeCodeFile();
    			//파일 출력
    			compiler.compile();
    		}
    		else if(e.getSource().equals(runItem)) {
    			compiler.run();
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
            		
            		String code_path;
        			File com = new File(".");
        			for(String str : stagelist) {
        		        code_path = com.getPath() + "\\data\\code_"+str+".txt"; //폴더 경로
        		        File code_txt_T = new File(code_path);
        				System.out.println(str+"임시파일 삭제");
        				
        				//임시파일 삭제 - 굳이 삭제할 필요가 없다 느껴지시면 해당 코드 삭제하셔도 상관 없습니다!			
        				deleteFile delete_file = new deleteFile();
        				
        				delete_file.delete(code_txt_T); //임시 코드파일 삭제

    	        		String[] filenames = showFilesInDIr(savepathname);
    					for(int i=0; i<filenames.length; i++) {
    						if(filenames[i].contains("_S.txt")) {
        						String delete_FilePath = savepathname  +"\\"+ filenames[i];
        						File D_file = new File(delete_FilePath);
        						System.out.println(filenames[i]+"삭제");						
        						delete_file.delete(D_file);
    						}
    					}	
        			}
        		}
    		}
    	}
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
		        String code_path = com.getPath() + "\\data\\code_"+str+".txt"; //폴더 경로
		    	File code_txt = new File(code_path);
				System.out.println(str+"삭제");
				
				//임시파일 삭제 - 굳이 삭제할 필요가 없다 느껴지시면 해당 코드 삭제하셔도 상관 없습니다!			
				deleteFile delete_file = new deleteFile();				
				delete_file.delete(code_txt); //임시 코드파일 삭제			
			}			
		}
		public void windowClosed(WindowEvent e) { }
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
    }
    private void copy(File sourceF, File targetF){
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
    public void filesave() {
    	//오픈 파일 복사
		String oriFilePath = openpath; //폴더 경로
		File oriFile = new File(oriFilePath);
		String copyFilePath = savepathname;
		File copyFile = new File(copyFilePath);

		copy(oriFile, copyFile);
		
		//작성 코드 저장
		ArrayList<String> stagelist;
		stagelist = StagePanel.getStageList();
		System.out.println(stagelist.size());
		
		for(String str : stagelist) {
			System.out.println(str);			
			File com = new File(".");
			
			// 저장된 파일 자체가 이미 저장했던 걸 다시 저장하는 거라서 listindex num가 포함된 파일명이어야함.
			String ori_codeFilePath = com.getPath() +"\\data\\code_"+str+".txt"; //폴더 경로
			String copy_codeFilePath = savepathname +"\\code_"+str+"_S.txt";
			File ori_codeFile = new File(ori_codeFilePath);
			File copy_codeFile = new File(copy_codeFilePath);

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

		     } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}	
		}
    }
    
    public void filesave2() {
    	System.out.println("학습자 작성 코드 저장하기");
    	
		//작성 코드 저장
		ArrayList<String> stagelist;
		stagelist = StagePanel.getStageList();
		System.out.println(stagelist.size());
		
		for(String str : stagelist) {
			System.out.println(str);			
			File com = new File(".");
			
			// 저장된 파일 자체가 이미 저장했던 걸 다시 저장하는 거라서 listindex num가 포함된 파일명이어야함.
			String ori_codeFilePath = com.getPath() +"\\data\\code_"+str+".txt"; //폴더 경로
			String copy_codeFilePath = savepathname +"\\code_"+str+"_S.txt";
			File ori_codeFile = new File(ori_codeFilePath);
			File copy_codeFile = new File(copy_codeFilePath);

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
    
    public void runforquiz(String tempresult) {
    	String s;

        try {
            Process oProcess = new ProcessBuilder("cmd", "/c", "cd", path).start();
            Process Process2 = new ProcessBuilder("cmd", "/c", "test").start();

            // 외부 프로그램 출력 읽기
            BufferedReader stdOut2   = new BufferedReader(new InputStreamReader(Process2.getInputStream()));
            BufferedReader stdError2 = new BufferedReader(new InputStreamReader(Process2.getErrorStream()));

            
            while ((s = stdOut2.readLine()) != null) {
            	if(s.equals(tempresult)) {
            		JOptionPane.showMessageDialog(null, "Right Answer.\n"
            				, "result", JOptionPane.INFORMATION_MESSAGE);
            		System.out.println(s + "정답");
            	}
            	else {
            		JOptionPane.showMessageDialog(null, "Wrong Answer.\n"
            				, "result", JOptionPane.INFORMATION_MESSAGE);
            		System.out.println(s + "오답");
            	}
            }
            while ((s = stdError2.readLine()) != null) {
            	System.out.println("오답");
            }


            // 외부 프로그램 반환값 출력 (이 부분은 필수가 아님)
            System.out.println("Exit Code: " + Process2.exitValue());
            //System.exit(Process2.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의 반환값으로 삼기

        } catch (IOException e) { // 에러 처리
            System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
            System.exit(-1);
        }
    }
}