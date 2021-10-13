package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

public class TestGraphics {
    public static void main(String[] args) throws Exception{
//
//        try {
//            BufferedImage boardImg = ImageIO.read(new File("/home/kaliappan/Documents/work/hogwarts-engineering/KPsSnake_Ladders/src/main/resources/board.png"));
//            Component board = new Board(boardImg);
//            JFrame jf = new JFrame();
//            jf.getContentPane().add(board);
//            jf.setSize(600,600);
//            jf.setVisible(true);
//            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        }
//        catch(Exception e){
//            printStackTrace(e);
//        }
//        ImageIcon imageIcon = new ImageIcon("/home/kaliappan/Documents/work/hogwarts-engineering/KPsSnake_Ladders/src/main/resources/board.png");
//        JFrame jf2 = new JFrame("testing image icon");
//        JLabel jlabel = new JLabel(imageIcon);
//        jf2.add(jlabel);
//        Dimension imgDim =  jlabel.getSize();
//        JLabel player1 = new JLabel(new ImageIcon("/home/kaliappan/Documents/work/hogwarts-engineering/KPsSnake_Ladders/src/main/resources/green.jpeg"));
//        jf2.add(player1);
//        jf2.setSize(600,600);
//        jf2.setVisible(true);
//
//        jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        URL url = new URL("src/main/resources/board.png");
        BufferedImage im = ImageIO.read(new File("src/main/resources/board.png"));
//        URL url2 = new URL("src/main/resources/green.jpeg");
        BufferedImage im2 = ImageIO.read(new File("src/main/resources/red.jpeg"));
        Graphics2D g = im.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g.drawImage(im2,im.getHeight()-100,-im.getWidth()+100, null);
        g.dispose();

        JFrame JF = new JFrame("Test 2 ");
        JF.add(new JLabel(new ImageIcon(im)));
        JF.setSize(500,500);
        JF.setVisible(true);
    }

}
