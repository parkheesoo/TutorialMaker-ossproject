
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
	String savepathname; //����ڰ� ������ ���� ���

    public test_Frame(){
    	this.addWindowListener(new WinEvent());

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        MyActionListener actionListener = new MyActionListener();
        
        
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
    	
        //stagePanel StagePanel = new stagePanel();
    	StagePanel = new stagePanel();
        CommentPanel = new commentPanel();
        
        codePanel CodePanel = new codePanel();
        CommentPanel.setLayout(null);
        
        // stage�� �ٲ� ������ �۵�
        StagePanel.stageList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) // ����Ʈ�� ���� ���� ��
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
        		String attach_FilePath = com.getPath()  +"\\data\\"+ stagename + "_attachedfile"; //���� ���
				File attach_File = new File(attach_FilePath);
		        String code_path = com.getPath() + "\\data\\code_"+stagename+".txt"; //���� ���
		    	File code_txt = new File(code_path);
		        String comment_path = com.getPath() + "\\data\\comment_"+stagename+".txt"; //���� ���
		    	File comment_txt = new File(comment_path);
				System.out.println(stagename+"����");
        		
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
        // �޴��� ����
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu run = new JMenu("Run");

        file.add(newItem);
        //file.add(new JMenuItem("New"));
        file.add(openItem);		   //file.add(new JMenuItem("Open));
        //file.addSeparator();//�и��� ����        
        file.add(saveItem);        //file.add(new JMenuItem("Save"));
        file.add(saveAsItem);      //file.add(new JMenuItem("SaveAs"));
        
        
        edit.add(deleteItem);
        
        // run ��ư�� �ӽ÷� �з��߽��ϴ�.
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

        
        //actionListener �߰��ϱ�
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
                //���� ���
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
		public void windowClosing(WindowEvent e) {// �����찡 �������� �� ��
			// �ڹ� data ���Ͽ� ����Ǿ��ִ� �ӽ� �ؽ�Ʈ ���� ���� (���ʿ��ϸ� ����)
			ArrayList<String> stagelist;
			stagelist = StagePanel.getStageList();
			System.out.println(stagelist.size());
			
			File com = new File(".");
			for(String str : stagelist) {
				String attach_FilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //���� ���
				File attach_File = new File(attach_FilePath);
		        String code_path = com.getPath() + "\\data\\code_"+str+".txt"; //���� ���
		    	File code_txt = new File(code_path);
		        String comment_path = com.getPath() + "\\data\\comment_"+str+".txt"; //���� ���
		    	File comment_txt = new File(comment_path);
		        String quiz_path = com.getPath() + "\\data\\Quiz_"+str+".txt"; //���� ���
		    	File quiz_txt = new File(quiz_path);
				System.out.println(str+"����");
				
				//�ӽ����� ���� - ���� ������ �ʿ䰡 ���� �������ø� �ش� �ڵ� �����ϼŵ� ��� �����ϴ�!			
				deleteFile delete_file = new deleteFile();
				
				delete_file.delete(attach_File); //�ӽ� ÷������ ����
				delete_file.delete(code_txt); //�ӽ� �ڵ����� ����
				delete_file.delete(comment_txt); //�ӽ� �ּ����� ����
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
						System.out.println("���Գ���?");
						t_savepathname = savepathname+"_("+addnum+")";
						Folder = new File(t_savepathname);
						addnum++;
					}
					savepathname = t_savepathname;
					String savedatapath = savepathname+"\\data";
					//File dataFolder = new File(savedatapath);
					
					if(!Folder.exists()) {
						try {
							Folder.mkdir(); //���� ����
							//dataFolder.mkdir(); //data ���� ����
							
							System.out.println("������ �����Ǿ����ϴ�.");
							System.out.println(savepathname);
						
						}catch(Exception ex) {
							ex.getStackTrace();
						}
					}else {
						System.out.println("�̹� ������ �����Ǿ����ϴ�.");
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
						System.out.println(filenames[i]+"����");						
						delete_file.delete(D_file);
					}					
					filesave();
				}
			}
			else if(e.getSource().equals(saveAsItem)) { // ��ư �Է� �� ����Ž��â ����		
				
		    	File savefile;
				//String savepathname; //����ڰ� ������ ���� ���
				String savedatapath; // + data ���� ���� ���
				
				StagePanel.getStageList();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File ("C:\\"));
				chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY);
						
				int re=chooser.showSaveDialog(null);
				
				if(re==JFileChooser.APPROVE_OPTION) {
					savefile = chooser.getSelectedFile();
					String foldername = JOptionPane.showInputDialog("���ϸ��� �Է��ϼ���.");
					savepathname = savefile.getAbsolutePath()+"\\"+foldername;
					savedatapath = savepathname+"\\data";
					File Folder = new File(savepathname);
					//File dataFolder = new File(savedatapath);
					
					if(!Folder.exists() && foldername!=null) {
						try {
							Folder.mkdir(); //���� ����
							//dataFolder.mkdir(); //data ���� ����
							
							System.out.println("������ �����Ǿ����ϴ�.");
							System.out.println(savepathname);
							
						}catch(Exception ex) {
							ex.getStackTrace();
						}
					}else {
						if(foldername==null) System.out.println("���� ������ ��ҵǾ����ϴ�.");
						else{
							System.out.println("�̹� ������ �����Ǿ����ϴ�.");
							System.out.println(savepathname);
						}
					}					
				}else {
					JOptionPane.showMessageDialog(null, "��θ� �������� �ʾҽ��ϴ�.");
					return;
				}
				
				filesave();				
			}
			else if(e.getSource().equals(openItem)) {
				// ��� �����ؼ� ���� �� �� �ֵ���
		        JFileChooser jfc = new JFileChooser();
		        jfc.setCurrentDirectory(new File("/"));
		        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        
		        int returnVal = jfc.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) { // ���⸦ Ŭ��
		            savepathname = jfc.getSelectedFile().toString();
		            System.out.println(savepathname);
		        }
			}
			else if(e.getSource().equals(newItem)) {
				int result = JOptionPane.showConfirmDialog(null, "�۾� ���� ������ �������� �ʰ� �� ������ ���ϴ�.\n"
        				, "Ȯ��", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) {
        			// �� ��ư ������ ��, ȭ�� �� ��� ���� ����
        			ArrayList<String> stagelist;
        			stagelist = StagePanel.getStageList();
        			System.out.println(stagelist.size());
            		
        			//int index = StagePanel.stageList.getSelectedIndex();
            		//String stagename = StagePanel.model.get(index).toString();
            		StagePanel.model.removeAllElements();
            		CommentPanel.stageTitle.setText("No stage");
            		
        			File com = new File(".");
        			for(String str : stagelist) {
        				String attach_FilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //���� ���
        				File attach_File = new File(attach_FilePath);
        		        String code_path = com.getPath() + "\\data\\code_"+str+".txt"; //���� ���
        		    	File code_txt = new File(code_path);
        		        String comment_path = com.getPath() + "\\data\\comment_"+str+".txt"; //���� ���
        		    	File comment_txt = new File(comment_path);
        		        String quiz_path = com.getPath() + "\\data\\Quiz_"+str+".txt"; //���� ���
        		        File quiz_txt = new File(quiz_path);
        				System.out.println(str+"����");
        				
        				//�ӽ����� ���� - ���� ������ �ʿ䰡 ���� �������ø� �ش� �ڵ� �����ϼŵ� ��� �����ϴ�!			
        				deleteFile delete_file = new deleteFile();
        				
        				delete_file.delete(attach_File); //�ӽ� ÷������ ����
        				delete_file.delete(code_txt); //�ӽ� �ڵ����� ����
        				delete_file.delete(comment_txt); //�ӽ� �ּ����� ����     
        				delete_file.delete(quiz_txt); //�ӽ� �ּ����� ����        			
        			}
        		}
			}
			else if(e.getSource().equals(deleteItem)) {
				int result = JOptionPane.showConfirmDialog(null, "�۾� ���� ������ �����մϴ�.\n"
        				, "Ȯ��", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) {
        			// �� ��ư ������ ��, ȭ�� �� ��� ���� ����
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
        				attach_FilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //���� ���
        				File attach_File_T = new File(attach_FilePath);
        		        code_path = com.getPath() + "\\data\\code_"+str+".txt"; //���� ���
        		        File code_txt_T = new File(code_path);
        		        comment_path = com.getPath() + "\\data\\comment_"+str+".txt"; //���� ���
        		        File comment_txt_T = new File(comment_path);
        		        quiz_path = com.getPath() + "\\data\\Quiz_"+str+".txt"; //���� ���
        		        File quiz_txt_T = new File(quiz_path);
        				System.out.println(str+"�ӽ����� ����");
        				
        				//�ӽ����� ���� - ���� ������ �ʿ䰡 ���� �������ø� �ش� �ڵ� �����ϼŵ� ��� �����ϴ�!			
        				deleteFile delete_file = new deleteFile();
        				
        				delete_file.delete(attach_File_T); //�ӽ� ÷������ ����
        				delete_file.delete(code_txt_T); //�ӽ� �ڵ����� ����
        				delete_file.delete(comment_txt_T); //�ӽ� �ּ����� ����
        				delete_file.delete(quiz_txt_T); //�ӽ� �������� ����

    	        		String[] filenames = showFilesInDIr(savepathname);
    					for(int i=0; i<filenames.length; i++) {
    						String delete_FilePath = savepathname  +"\\"+ filenames[i];
    						File D_file = new File(delete_FilePath);
    						System.out.println(filenames[i]+"����");						
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
        // �ڹ� ���� ��ο� ����Ǿ��ִ� �ڵ� �� �ּ� ����
        // ���� ��ü ����
			String oriFilePath = com.getPath()  +"\\data\\"+ str + "_attachedfile"; //���� ���
			File oriFile = new File(oriFilePath);
			String copyFilePath = savepathname+"\\" + str + "_attachedfile";
			File copyFile = new File(copyFilePath);
			try {
				copyFile.mkdir(); //data ���� ����		
			}catch(Exception ex) {
				ex.getStackTrace();
			}
			
			copy(oriFile, copyFile);
			
			
			String ori_codeFilePath = com.getPath() +"\\data\\"+"code_"+str+".txt"; //���� ���
			String copy_codeFilePath = savepathname +"\\"+"code"+listindex+"_"+str+".txt";
			File ori_codeFile = new File(ori_codeFilePath);
			File copy_codeFile = new File(copy_codeFilePath);
			
			String ori_commentFilePath = com.getPath()+ "\\data\\"+"comment_" + str + ".txt"; //���� ���
			String copy_commentFilePath = savepathname+"\\"+"comment"+listindex+"_"+str+".txt";
			File ori_commentFile = new File(ori_commentFilePath);
			File copy_commentFile = new File(copy_commentFilePath);
			
			String ori_quizFilePath = com.getPath()+ "\\data\\"+"Quiz_" + str + ".txt"; //���� ���
			String copy_quizFilePath = savepathname+"\\"+"Quiz"+listindex+"_"+str+".txt";
			File ori_quizFile = new File(ori_quizFilePath);
			File copy_quizFile = new File(copy_quizFilePath);
			 try {
		            
		            FileInputStream code_fis = new FileInputStream(ori_codeFile); //��������
		            FileOutputStream code_fos = new FileOutputStream(copy_codeFile); //����������
		            
		            int fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		            while((fileByte = code_fis.read()) != -1) {
		                code_fos.write(fileByte);
		            }
		            //�ڿ��������
		            code_fis.close();
		            code_fos.close();
		            
		            /////////////////////////////////////////////////////// 
		            fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		    		if( ori_commentFile.exists() ){ //�������翩��Ȯ�� 
			            FileInputStream comment_fis = new FileInputStream(ori_commentFile); //��������
			            FileOutputStream comment_fos = new FileOutputStream(copy_commentFile); //����������
			          
		    			while((fileByte = comment_fis.read()) != -1) {
		    				comment_fos.write(fileByte);
		    			}
		    			//�ڿ��������
		    			comment_fis.close();
		    			comment_fos.close();
		    		}else{ 
		    			System.out.println("������ �������� �ʽ��ϴ�."); 
		    		}
		            ///////////////////////////////////////////////////////

		            fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		    		if( ori_quizFile.exists() ){ //�������翩��Ȯ�� 
		    			System.out.println("���� ����");
			            FileInputStream quiz_fis = new FileInputStream(ori_quizFile); //��������
			            FileOutputStream quiz_fos = new FileOutputStream(copy_quizFile); //����������
			           
		    			while((fileByte = quiz_fis.read()) != -1) {
		    				quiz_fos.write(fileByte);
		    			}
		    			//�ڿ��������
		    			quiz_fis.close();
		    			quiz_fos.close();
		    		}else{ 
		    			System.out.println("������ �������� �ʽ��ϴ�."); 
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
}
