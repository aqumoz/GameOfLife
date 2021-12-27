//import java.io.BufferedReader;
//import com.sun.istack.internal.NotNull;

import java.io.Console;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//import greenfoot.MouseInfo;
//public class gfmi extends greenfoot.MouseInfo{}

//Greenfoot.isKeyDown("right");

class Board {
    private int width;
    private int height;
    private char[][] boardOld;
    private char[][] board;

    /**
     * Fills the board with chars.
     * @param c The char to fill with.
     */
    public void fill(char[][] arr, char c){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                arr[i][j] = c;
            }
        }
    }

    /**
     * Prints the board to stdout
     */
    public void print(){

        for(int i = 0; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                char c = this.board[j][i];
                if(c == 'O')
                    System.out.print("\033[31;1mO");
                else if(c == 'o')
                    System.out.print("\033[34;1mO");
                else if(c == 'X')
                    System.out.print("\033[32;1mX");
                else if(c == 'x')
                    System.out.print("\033[34;1mX");
            }
            System.out.print("\033[30;1m\n");
        }
    }

    /**
     * Calculates the action for a 3x3 area, and spawns or kills a cell
     * based on the following rules:
     * - Each cell with one or no neighbors dies, as if by solitude.
     * - Each cell with four or more neighbors dies, as if by overpopulation.
     * - Each cell with two or three neighbors survives.
     * - Each cell with three neighbors becomes populated.
     * @Return {boolean} true if new life is created otherwise false.
     */
    private boolean calculateCellArea(int x, int y){
        int count = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!(i == 1 && j == 1) && this.boardOld[i+x][j+y] == 'X')
                    count++;
            }
        }
        if(2 > count || count > 3) {
            this.board[x + 1][y + 1] = 'O';
            return false;
        }
        else if (count == 2) {
            this.board[x + 1][y + 1] = this.boardOld[x + 1][y + 1];
            return this.board[x + 1][y + 1] == 'X';
        }
        else{
            this.board[x + 1][y + 1] = 'X';
            return true;
        }
    }

    public static char[][] cloneCharArray(char[][] source){
        char[][] res = new char[source.length][source[0].length];
        for(int i = 0; i < source.length; i++){
            for(int j = 0; j < source[0].length; j++){
                res[i][j] = source[i][j];
            }
        }
        return res;
    }

    /**
     * Calculates the next board array
     * @return {boolean} true if not all life is extinct.
     */
    public boolean next(){
        //this.boardOld = this.board.clone();
        this.boardOld = Board.cloneCharArray(this.board);
        this.fill(this.board, 'O');

        for(int x = 0; x < this.width - 3; x++){
            for(int y = 0; y < this.height - 3; y++){
                calculateCellArea(x, y);
            }
        }

        //Check for extinction;
        int count = 0;
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                if(this.board[i][j] == 'X')
                    count++;
            }
        }

        //return count > 0;
        return true;
    }

    //Only for testing
    public void set(int x, int y, char c){
        this.boardOld[x][y] = c;
        this.board[x][y] = c;
    }

    public void enterSetupMode() throws Exception {
        int x = this.width / 2;
        int y = height / 2;
        int old_x = x;
        int old_y = y;

        this.set(x, y, 'x');
        char[] instructions = new char[0];
        boolean quit = false;
        do{
            for(int i = 0; i < instructions.length && !quit; i++){
                switch (instructions[i]){
                    case 'w':
                        y = Math.max(y - 1, 0);
                        break;
                    case 'a':
                        x = Math.max(x - 1, 0);
                        break;
                    case 's':
                        y = Math.min(y + 1, this.height-1);
                        break;
                    case 'd':
                        x = Math.min(x + 1, this.width-1);
                        break;
                    case 'q':
                        quit = true;
                        break;
                    case 'e':
                        if(board[x][y] == 'X')
                            set(x, y, 'O');
                        else if(board[x][y] == 'x')
                            set(x, y, 'o');
                        else if(board[x][y] == 'O')
                            set(x, y, 'X');
                        else if(board[x][y] == 'o')
                            set(x, y, 'x');
                        else
                            throw new Exception("Encountered an unknown character in board");
                        break;
                    default:
                        break;
                }
            }

            //Set old field to Red and Green colors, to indicate that it is no longer the selected field
            if(board[old_x][old_y] == 'X' || board[old_x][old_y] == 'x')
                set(old_x, old_y, 'X');
            else if(board[old_x][old_y] == 'O' || board[old_x][old_y] == 'o')
                set(old_x, old_y, 'O');
            else
                throw new Exception("Encountered an unknown character in board");

            // Set the new field to Blue, to indicate that this is the new selected field
            if(board[x][y] == 'X' || board[x][y] == 'x')
                set(x, y, 'x');
            else if(board[x][y] == 'O' || board[x][y] == 'o')
                set(x, y, 'o');
            else
                throw new Exception("Encountered an unknown character in board");

            old_x = x;
            old_y = y;
            if(!quit) {
                this.print();
                instructions = ConsoleUtils.getInstructions("Type q to quit setup mode. w,a,s or d to move or e to toggle between 'X' and 'O' (finish with enter): ");
            }
        } while(!quit);
    }

    /**
     * Creates a new Game of Life board
     * @param width width of board
     * @param height height of board
     * @param fillChar the char the board will be filled with initially
     */
    public Board(int width, int height, char fillChar){
        this.width = width;
        this.height = height;
        this.boardOld = new char[width][height];
        this.board = new char[width][height];
        this.fill(this.boardOld, fillChar);
        this.fill(this.board, fillChar);
    }

}

class ConsoleUtils{

    static java.io.Console waitForConsole(){
        Console console = System.console();
        try {
            while(console == null) {
                Thread.sleep(1000);
                console = System.console();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return console;
    }

    public static boolean askYesNo(String question) {
        System.out.print(question + " (Y/n): ");
        String answer = null;
        //Console console = waitForConsole();

        //set true if using MS Terminal and false if using IntelliJ Terminal
        if(false) {
            answer = System.console().readLine();
        }
        else {
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine().toLowerCase(Locale.ROOT);
        }

        System.out.print('\n');
//        System.out.println("answer -> |" + answer + "|");
        if(answer.equals("y") || answer.equals("yes") || answer.equals(""))
            return true;
        else if (answer.equals("n") || answer.equals("no"))
            return false;
        else{
            System.out.println("Please answer either \"yes\" or \"no\"");
            try {
                System.in.skip(System.in.available());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return askYesNo(question);
        }
    }

    public static char[] getInstructions(String s) {
        System.out.println(s);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        return input.toCharArray();
    }
}

public class GameOfLife {

    private static Board board;

    public static void main(String[] args){
        System.out.println("Start");
        int width = 80;
        int height = 40;
        if(args.length == 2){
           width = Integer.parseInt(args[0]);
           height = Integer.parseInt(args[1]);
        }
        board = new Board(width, height, 'O');

        try {
            board.enterSetupMode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int itteration = 0;
        boolean continueToExtinction = ConsoleUtils.askYesNo("\033[31;1mAutomatically continue untill extinction?\033[0m");
        do{
            System.out.println("itteration: " + itteration);
            board.print();
            if(continueToExtinction){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!board.next())
                break;
            itteration++;
        } while(continueToExtinction || ConsoleUtils.askYesNo("\033[31;1mcontinue?\033[0m"));

        System.out.println("End");
    }
}
