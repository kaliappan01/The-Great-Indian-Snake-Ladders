package bootstrap;

import domain.Board;
import domain.Dice;
import domain.Player;
import domain.Tile;
import org.apache.log4j.*;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

public class Driver {

    public static org.slf4j.Logger logger = LoggerFactory.getLogger(Driver.class);
    public static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String objectFileName = "src/main/data/board.dat";
        applicationConfigurer(args);
        if (new File(objectFileName).exists() & permitPreviousGame()==1) {

            logger.info("Fetching previous game status");
            try {
                Board boardBuffer = deSerializeBoard();
                playGame(boardBuffer.getTiles(), boardBuffer.getPlayers());
            }
            catch(Exception e){
                e.printStackTrace();
            }
            } else {
            logger.info("New game started");
            newGame();
        }
        input.close();
    }

    public static int permitPreviousGame(){
        System.out.println("You have a previous Game paused.\nDo you want to continue it? (y=1/n=0)");
        return input.nextInt();
    }

    public static void serializeBoard(Tile[] board, ArrayList<Player> players) throws FileNotFoundException, IOException {
        String fileName = "src/main/data/board.dat";
        Board boardBuffer = new Board();
        boardBuffer.setTiles(board);
        boardBuffer.setPlayers(players);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
        objectOutputStream.writeObject(boardBuffer);
        objectOutputStream.close();
    }

    public static Board deSerializeBoard() throws IOException, ClassNotFoundException {
        String fileName = "src/main/data/board.dat";
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(fileName)));
        Board boardBuffer = (Board) objectInputStream.readObject();
        objectInputStream.close();
        return boardBuffer;
    }

    public static String configureLogging(String logLevel, String logFileName) {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();
        switch (logLevel) {

            case "DEBUG": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            }
            case "INFO": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            case "ERROR": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.ERROR_INT));
            }
            default: {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            break;
        }
        dailyRollingFileAppender.setFile(logFileName);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%-6d [%t] %-5p %c - %m%n"));
        dailyRollingFileAppender.activateOptions();
        Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }

    public static ApplicationConfig applicationConfigurer(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig(args);
        configureLogging(applicationConfig.isDebugLogEnabled(),
                applicationConfig.getLogFileName());
        logger.info("The Great Indian Snakes & Ladders ");
        return applicationConfig;
    }

    public static void playGame(Tile[] board, ArrayList<Player> players) throws IOException {
        int exit = 0;
        game:
        do {
            System.out.println("SNAKES & LADDERS \n 1.Roll dice \n 2.Pause Game \n 3.New Game  \n 4.Check Status \nEnter your choice : ");
            exit = makeChoice(players,board);
        } while (exit!=1);
    }

    public static int makeChoice(ArrayList<Player> playerList,Tile[] board){
        int choiceOfcurrentPlayer,pause = 0;
        for(int playerNo = 0;playerNo<playerList.size();playerNo++) {
            Player currentPlayer = playerList.get(playerNo);
            if (currentPlayer.getCompleted() == 0) {
                System.out.println(currentPlayer.getName());
                game:
                try {
                    choiceOfcurrentPlayer = input.nextInt();
                    switch (choiceOfcurrentPlayer) {
                        case 1:
                            currentPlayer = proceed(currentPlayer, board);
                            if (currentPlayer.getCompleted()==1){
                                break game;
                            }
                            break game;
                        case 2:
                            logger.info("Game paused by " + currentPlayer.getName());
                            System.out.println("You paused the game !!!!");
                            serializeBoard(board, playerList);
                            pause = 1;
                            break game;
                        case 3:
                            logger.info("Request for new Game from " + currentPlayer.getName());
                            System.out.println("Setting up New game ");
                            newGame();
                            break game;
                        case 4:
                            getGameStatus(playerList);
                            break game;

                    }
                } catch (Exception e) {
                    System.out.println("Invalid Choice");
                }
            }
            if (pause==1){
                break;
            }
        }
        return pause;
    }

    public static void getGameStatus(ArrayList<Player> playerList){
        Collections.sort(playerList);
        System.out.println("Name            Position");
        for(int iter = 0;iter<=playerList.size();iter++){
            System.out.println(playerList.get(iter).getName()+"         "+playerList.get(iter).getStepNo());
        }
    }

    public static Player proceed(Player playerName, Tile[] board) {
        Dice dice = new Dice();
        int move = dice.rollDice();
        logger.info(playerName.getName() + " got a " + move + " on dice");
        System.out.println("You got " + move);
        if ((playerName.getStepNo() + move) > 100) {
            return playerName;
        }
        Tile tile = board[(int) playerName.getStepNo() + move];
        if (tile.getSnake() != -1) {
            logger.error(playerName.getName() + " got a Snake bite");
            System.out.println("You got a Snake bite !!!!");
            System.out.println("You slided down from " + (playerName.getStepNo() + move));
            playerName.setStepNo(tile.getSnake());
        } else if (tile.getLadder() != -1) {
            logger.info(playerName.getName() + " found a ladder");
            System.out.println("You found a Ladder !!!!");
            System.out.println("You climbed up from " + (playerName.getStepNo() + move));
            playerName.setStepNo(tile.getLadder());
        } else {
            playerName.setStepNo(playerName.getStepNo() + move);
        }
        System.out.println("Your current position is : " + playerName.getStepNo());
        if (playerName.getStepNo() == 100) {
            playerName.setCompleted(1);
            System.out.println("You have reached the destination !!!! \n Congratulations ");
        }
        return playerName;
    }

    public static Tile[] initBoard(Tile[] board) {
        List<Integer> snakeHeads = Arrays.asList(100, 91, 85, 71, 66, 54, 48, 33, 22);
        List<Integer> snakeTails = Arrays.asList(1, 24, 64, 47, 51, 33, 23, 8, 5);
        List<Integer> ladderBottom = Arrays.asList(2, 11, 25, 34, 46, 53, 65, 70, 84);
        List<Integer> ladderTop = Arrays.asList(90, 84, 69, 50, 64, 73, 88, 92, 96);
        int idx;
        for (int iter = 0; iter < 100; iter++) {
            Tile tile = new Tile();
            tile.setStepNo(iter + 1);
            idx = snakeHeads.indexOf(iter + 1);
            if (idx >= 0) {
                tile.setSnake(snakeTails.get(idx));
            } else {
                tile.setSnake(-1);
            }
            idx = ladderBottom.indexOf(iter + 1);
            if (idx >= 0) {
                tile.setLadder(ladderTop.get(idx));
            } else {
                tile.setLadder(-1);
            }
            board[iter] = tile;
        }
        return board;
    }

    public static void newGame() throws IOException {
        Tile[] board = new Tile[100];
        board = initBoard(board);
        System.out.println("Enter no of players : ");
        try {
            int noOfPlayers = input.nextInt();
            input.nextLine();
            ArrayList<Player> playerList = new ArrayList<>();
            for (int iter = 0; iter < noOfPlayers; iter++) {
                System.out.println("Enter Player Name : ");
                Player playerObj = new Player();
                String name = input.nextLine();
                playerObj.setName(name);
                playerList.add(playerObj);

            }
            playGame(board, playerList);

        }
        catch(Exception e){
            printStackTrace(e);
        }
//        currentPlayer.setName(input.nextLine());
//        System.out.println("Enter Player Name : ");
//        Player playerB = new Player();
//        playerB.setName(input.nextLine());
    }

}
