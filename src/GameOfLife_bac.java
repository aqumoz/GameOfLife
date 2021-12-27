////import java.io.BufferedReader;
//
//import java.io.Console;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;
//
////import greenfoot.MouseInfo;
////public class gfmi extends greenfoot.MouseInfo{}
//
////Greenfoot.isKeyDown("right");
//
//class Board {
//    private byte width;
//    private byte height;
//    private char[][] boardOld;
//    private char[][] board;
//
//    /**
//     * Fills the board with chars.
//     * @param c The char to fill with.
//     */
//    public void fill(char[][] arr, char c){
//        for(byte i = 0; i < this.width; i++){
//            for(byte j = 0; j < this.height; j++){
//                arr[i][j] = c;
//            }
//        }
//    }
//
//    /**
//     * Prints the board to stdout
//     */
//    public void print(){
////        System.out.print("\nOld Board:\n");
////
////        for(byte i = 0; i < this.height; i++){
////            for(byte j = 0; j < this.width; j++){
////                char c = this.boardOld[j][i];
////                if(c == '0')
////                    System.out.print("\033[31;1m0");
////                else if(c == 'X')
////                    System.out.print("\033[32;1mX");
////            }
////            System.out.print('\n');
////        }
////
////        System.out.print("\nBoard:\n");
//
//        for(byte i = 0; i < this.height; i++){
//            for(byte j = 0; j < this.width; j++){
//                char c = this.board[j][i];
//                if(c == '0')
//                    System.out.print("\033[31;1m0");
//                else if(c == 'X')
//                    System.out.print("\033[32;1mX");
//            }
//            System.out.print('\n');
//        }
//    }
//
//    /**
//     * Calculates the action for a 3x3 area, and spawns or kills a cell
//     * based on the following rules:
//     * - Each cell with one or no neighbors dies, as if by solitude.
//     * - Each cell with four or more neighbors dies, as if by overpopulation.
//     * - Each cell with two or three neighbors survives.
//     * - Each cell with three neighbors becomes populated.
//     * @Return {boolean} true if new life is created otherwise false.
//     */
//    private boolean calculateCellArea(byte x, byte y){
//        byte count = 0;
//        for(byte i = 0; i < 3; i++){
//            for(byte j = 0; j < 3; j++){
//                if(!(i == 1 && j == 1) && this.boardOld[i+x][j+y] == 'X')
//                    count++;
//            }
//        }
//        if(2 > count || count > 3) {
//            this.board[x + 1][y + 1] = '0';
//            return false;
//        }
//        else if (count == 2) {
//            this.board[x + 1][y + 1] = this.boardOld[x + 1][y + 1];
//            return this.board[x + 1][y + 1] == 'X';
//        }
//        else{
//            this.board[x + 1][y + 1] = 'X';
//            return true;
//        }
//    }
//
//    public static char[][] cloneCharArray(char[][] source){
//        char[][] res = new char[source.length][source[0].length];
//        for(byte i = 0; i < source.length; i++){
//            for(byte j = 0; j < source[0].length; j++){
//                res[i][j] = source[i][j];
//            }
//        }
//        return res;
//    }
//
//    /**
//     * Calculates the next board array
//     * @return {boolean} true if not all life is extinct.
//     */
//    public boolean next(){
//        //this.boardOld = this.board.clone();
//        this.boardOld = Board.cloneCharArray(this.board);
//        this.fill(this.board, '0');
//
//        for(byte x = 0; x < this.width - 3; x++){
//            for(byte y = 0; y < this.height - 3; y++){
//                calculateCellArea(x, y);
//            }
//        }
//
//        //Check for extinction;
//        int count = 0;
//        for(byte i = 0; i < this.width; i++){
//            for(byte j = 0; j < this.height; j++){
//                if(this.board[i][j] == 'X')
//                    count++;
//            }
//        }
//
//        if(true /*count > 0*/)
//            return true;
//        else
//            return false;
//    }
//
//    //Only for testing
//    public void set(byte x, byte y, char c){
//        this.boardOld[x][y] = c;
//        this.board[x][y] = c;
//    }
//
//    /**
//     * Creates a new Game of Life board
//     * @param width width of board
//     * @param height height of board
//     * @param fillChar the char the board will be filled with initially
//     */
//    public Board(byte width, byte height, char fillChar){
//        this.width = width;
//        this.height = height;
//        this.boardOld = new char[width][height];
//        this.board = new char[width][height];
//        this.fill(this.boardOld, fillChar);
//        this.fill(this.board, fillChar);
//    }
//
//}
//
//class ConsoleUtils{
//
//    static Console waitForConsole(){
//        Console console = System.console();
//        try {
//            while(console == null) {
//                Thread.sleep(1000);
//                console = System.console();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return console;
//    }
//
//    public static boolean askYesNo(String question) {
//        System.out.print(question + " (Y/n): ");
//        String answer = null;
//        Console console = waitForConsole();
//
//        //set true if using MS Terminal and false if using IntelliJ Terminal
//        if(true) {
//            answer = System.console().readLine();
//        }
//        else {
////            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
////            try {
////                answer = obj.readLine();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//            byte[] buffer = new byte[4];
//            try {
//                int length = System.in.read(buffer, 0, 4);
//                answer = new String(Arrays.copyOfRange(buffer, 0, length-1)).toLowerCase(Locale.forLanguageTag("da-DK"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //
//        System.out.print('\n');
////        System.out.println("answer -> |" + answer + "|");
//        if(answer.equals("y") || answer.equals("yes") || answer.equals("\n"))
//            return true;
//        else if (answer.equals("n") || answer.equals("no"))
//            return false;
//        else{
//            System.out.println("Please answer either \"yes\" or \"no\"");
//            try {
//                System.in.skip(System.in.available());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return askYesNo(question);
//        }
//    }
//}
//
//public class GameOfLife {
//
//    private static Board board;
//
//    public static void main(String[] args){
//        System.out.println("Start");
//        byte width = 80;
//        byte height = 40;
//        if(args.length == 2){
//           width = Byte.parseByte(args[0]);
//           height = Byte.parseByte(args[1]);
//        }
//        board = new Board(width, height, '0');
//
//        // setup
//
//        board.set((byte)4, (byte)5, 'X');
//        board.set((byte)5, (byte)5, 'X');
//        board.set((byte)4, (byte)6, 'X');
//
//        board.set((byte)8, (byte)5, 'X');
//        board.set((byte)9, (byte)5, 'X');
//        board.set((byte)10, (byte)5, 'X');
//        board.set((byte)9, (byte)5, 'X');
//        board.set((byte)8, (byte)6, 'X');
//        board.set((byte)8, (byte)7, 'X');
//
//        int itteration = 0;
//        boolean continueToExtinction = ConsoleUtils.askYesNo("\033[31;1mAutomatically continue untill extinction?\033[0m");
//        do{
//            System.out.println("itteration: " + itteration);
//            board.print();
//            if(continueToExtinction){
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(!board.next())
//                break;
//            itteration++;
//        } while(continueToExtinction || ConsoleUtils.askYesNo("\033[31;1mcontinue?\033[0m"));
//
//        System.out.println("End");
//    }
//}
