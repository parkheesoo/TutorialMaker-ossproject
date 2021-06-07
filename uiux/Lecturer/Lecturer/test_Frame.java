
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
	String savepathname; //����ڰ� ������ ���� ���

    public test_Frame(){
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
        commentPanel CommentPanel = new commentPanel();
        
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
        // �޴��� ����
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu run = new JMenu("Run");

        JMenuItem newItem = new JMenuItem("New");
        file.add(newItem);
        //file.add(new JMenuItem("New"));
        file.add(openItem);		   //file.add(new JMenuItem("Open));
        //file.addSeparator();//�и��� ����        
        file.add(saveItem);        //file.add(new JMenuItem("Save"));
        file.add(saveAsItem);      //file.add(new JMenuItem("SaveAs"));
        
        edit.add(new JMenuItem("Delete"));

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
        
        newItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// ���α׷� ���� ����
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
					File dataFolder = new File(savedatapath);
					
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
    }
    public void filesave() {
    	ArrayList<String> stagelist;// = new ArrayList<String>();
		stagelist = StagePanel.getStageList();
		System.out.println(stagelist.size());
		for(String str : stagelist) {
			System.out.println(str);
			int listindex = stagelist.indexOf(str);
			
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
		            FileInputStream comment_fis = new FileInputStream(ori_commentFile); //��������
		            FileOutputStream comment_fos = new FileOutputStream(copy_commentFile); //����������
		            
		            fileByte = 0; 
		            // fis.read()�� -1 �̸� ������ �� ������
		            while((fileByte = comment_fis.read()) != -1) {
		                comment_fos.write(fileByte);
		            }
		            //�ڿ��������
		            comment_fis.close();
		            comment_fos.close();
		           
		     } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
}