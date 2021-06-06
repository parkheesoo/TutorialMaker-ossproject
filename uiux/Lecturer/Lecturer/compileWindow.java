package Lecturer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class compileWindow extends JFrame {
    private JPanel txtPanel = new JPanel();
    private JTextArea resultArea = new JTextArea(15,30);
    private JScrollPane scroll = new JScrollPane(resultArea);

    public compileWindow(){
        setTitle("result");
        setLayout(new BorderLayout());

        txtPanel.add(scroll);
        getContentPane().add(txtPanel, BorderLayout.CENTER);

        setSize(500,300);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public void setResultArea(String result){
        resultArea.append(result+"\n");
    }
}