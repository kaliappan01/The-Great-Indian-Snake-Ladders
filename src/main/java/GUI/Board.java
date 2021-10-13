package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TimerTask;

public class Board extends JPanel {

        private final int B_WIDTH = 350;
        private final int B_HEIGHT = 350;
        private final int INITIAL_X = -40;
        private final int INITIAL_Y = -40;
        private final int INITIAL_DELAY = 100;
//        private final int PERIOD_INTERVAL = 25;

        private Image star;
        private Timer timer;
        private int x, y;

        public Board() {

            initBoard();
        }

        private void loadImage() {

            ImageIcon ii = new ImageIcon("src/main/resources/green.jpeg");
            star = ii.getImage();
        }

        private void initBoard() {

            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

            loadImage();

            x = 0;
            y = 100;

//            timer = new Timer();
//            timer.scheduleAtFixedRate(new ScheduleTask(),
//                    INITIAL_DELAY);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            drawStar(g);
        }

        private void drawStar(Graphics g) {

            g.drawImage(star, x, y, this);
            Toolkit.getDefaultToolkit().sync();
        }

        private class ScheduleTask extends TimerTask {

            @Override
            public void run() {

                x += 1;
                y += 1;

                if (y > B_HEIGHT) {
                    y = INITIAL_Y;
                    x = INITIAL_X;
                }

                repaint();
            }
        }
    }

//
//    private int[] playerPositions;
//    private static BufferedImage boardImg;
////    private static byte[] boardBytes;
//
////    static {
////        try {
////            boardBytes = Files.readAllBytes(Paths.get(boardPath));
////            BufferedImage board = ImageIO.read(new ByteArrayInputStream(boardBytes));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//
//    public Board(BufferedImage boardImg){
//        this.boardImg = boardImg;
//    }
////    public static void displayBoard() throws IOException {
////        ImageIcon boardIcon  = new ImageIcon(boardPath);
////        JFrame jframe = new JFrame("The Great Indian Snakes & Ladders ");
////        JLabel jlabel = new JLabel(boardIcon);
////
////    }
//
//
//    public void paint(Graphics g){
//        Graphics2D g2D = (Graphics2D) g;
//        g2D.drawImage(boardImg,0,0,null);
//        g2D.finalize();
//    }

