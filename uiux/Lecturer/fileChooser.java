

import javax.swing.*;
import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class fileChooser extends JFrame {
    String filePath = "";

    public fileChooser(String[] extension){
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("/"));
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        for (int i = 0; i < extension.length; i++) {
            jfc.setFileFilter(new FileNameExtensionFilter(extension[i], extension[i])); // filter Ȯ���� �߰�
        }

        int returnVal = jfc.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) { // ���⸦ Ŭ��
            filePath = jfc.getSelectedFile().toString();
        }
    }
}