import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    JMenuItem runItem = new JMenuItem("run");
    JMenuItem compileItem = new JMenuItem("compile");
	
    private String savepathname;
    private String openpath;
    
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
    			openpath = dir.getAbsolutePath();
    			CommentPanel.setFile(dir);
        		StagePanel.initStage(dir);
        		CodePanel.setFile(dir);
    		}
    		else if(e.getSource().equals(saveItem)) {
    			// save Ŭ�� �� �߻�
    			if(savepathname == null) {
					savepathname = "C:\\Learner_savefile";
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
					//String savedatapath = savepathname+"\\data";
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
    		else if(e.getSource().equals(saveasItem)) {
    			// save as Ŭ�� �� �߻�
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
					//savedatapath = savepathname+"\\data";
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
		    else if(e.getSource().equals(compileItem)) {
		       /* Call_compiler compiler = new Call_compiler();
                CodePanel.makeCodeFile();
                //���� ���
                compiler.compile();
		    	*/
		    }
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
    	ArrayList<String> stagelist;// = new ArrayList<String>();
		stagelist = StagePanel.getStageList();
		System.out.println(stagelist.size());
		for(String str : stagelist) {
			System.out.println(str);
			int listindex = stagelist.indexOf(str)+1;
			
			File com = new File(".");
			
			// ����� ���� ��ü�� �̹� �����ߴ� �� �ٽ� �����ϴ� �Ŷ� listindex num�� ���Ե� ���ϸ��̾����.
			String ori_codeFilePath = com.getPath() +"\\data\\code_"+str+".txt"; //���� ���
			String copy_codeFilePath = savepathname +"\\code"+listindex+"_"+str+".txt";
			File ori_codeFile = new File(ori_codeFilePath);
			File copy_codeFile = new File(copy_codeFilePath);
			
			String ori_commentFilePath = openpath+"\\comment"+listindex+"_" + str + ".txt"; //���� ���
			String copy_commentFilePath = savepathname+"\\comment"+listindex+"_"+str+".txt";
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
			 
        // �ڹ� ���� ��ο� ����Ǿ��ִ� �ڵ� �� �ּ� ����
        // ���� ��ü ����
			String oriFilePath = openpath +"\\"+ str + "_attachedfile"; //���� ���
			File oriFile = new File(oriFilePath);
			String copyFilePath = savepathname+"\\" + str + "_attachedfile";
			File copyFile = new File(copyFilePath);
			try {
				copyFile.mkdir(); //data ���� ����		
			}catch(Exception ex) {
				ex.getStackTrace();
			}
			
			copy(oriFile, copyFile);
			
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