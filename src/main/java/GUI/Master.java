package GUI;

import javax.swing.JFrame;
import java.awt.*;

public class Master  extends JFrame{

    public Master(){
        initGUI();
    }
    public void initGUI(){
        add(new Board());
        setResizable(false);
        setTitle("Test GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    public static void main(String[] args){
        EventQueue.invokeLater(() ->{
            JFrame masterCall = new Master();
            masterCall.setVisible(true);
        });
    }

}
