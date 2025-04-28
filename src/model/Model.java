package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import view.Observer;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

/** 
 * class
 * board is always 10x 10
 * each ship position ins in the bounds set out
 * ships do not over lap
 * valid ship lengths 5,4,3,2,2
 */


public class Model{
    
    public static final int STATE_Empty =0;
    public static final int STATE_SHIP = 1;
    public static final int STATE_HIT = 2;
    public static final int STATE_MISS = 3;



    private int [][] board;
    private List<Ship> ships;
    private List<String> attempts;
    private List<Observer> observers = new ArrayList<>();
    private static final int Board_Size = 10;
    private static final int[] Ship_Size = {5, 4, 3, 2, 2};

    public Model(){
        this.board = new int[10][10];
        this.ships = new ArrayList<Ship>();
        this.attempts = new ArrayList<String>();
        
        placeShipsRandom();

    }



    public void addObserver(Observer o){
        observers.add(o);
    }

    public void notifyObservers(){
        for (Observer o : observers){
            o.update();
        }
    }




/* placing ships randomly */

    private void placeShipsRandom(){
        Random rand = new Random();
        for(int size: Ship_Size){
            boolean placed = false;
            while(!placed){
                int x = rand.nextInt(Board_Size);
                int y = rand.nextInt(Board_Size);
                boolean horizontal = rand.nextBoolean();
                if(canPlaceShip(x, y, size, horizontal)){
                    placeShip(x, y, size, horizontal);
                    placed = true;

                }
        }
    
    }
}
/* checking i fthe ship can be placed at this location */
 private boolean canPlaceShip(int x, int y, int size, boolean horizontal){
    assert x >= 0 && x < Board_Size;
    assert y >= 0 && y < Board_Size;
    assert size > 1 && size <= Board_Size;

    if(horizontal){
        if (y+size > Board_Size) return false;
        for(int i = 0; i < size; i++){
            if(board[x][y+i] != STATE_Empty) return false;
        

        }

 }       else {
          if (x+size > Board_Size) return false;
          for(int i = 0; i < size; i++){
            if(board[x+i][y] != 0) return false;
 }
 }
         return true;

/*places ships size at location */

} private void placeShip (int x, int y, int size, boolean horizontal){
    List<String> position = new ArrayList<>();
    for (int i = 0; i < size; i++){
        if(horizontal){
            board[x][y+i]= 1;
            position.add(x + " " + (y+i));
    }  else {
        board[x+i][y] = 1;
        position.add((x+i) + " " + y);
    }
}
  ships.add(new Ship(size, position));
}
/*reading ships from text file */
 public void loadShipsFromFile(String filename){
    try(BufferedReader br = new BufferedReader(new FileReader(filename))){
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ;");
            int size = Integer.parseInt(parts[0]);
            List<String> positions = Arrays.asList(parts[1].split(";"));
            ships.add(new Ship(size, positions));
        }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());

    }
 }
/**add user attempts to hit ship, on conditation that codinates must be with bounds and atmmpted before
 * 
 */
 public String addAttempt(int x, int y){
    assert x >= 0 && x < Board_Size: "x out of bounds";
    assert y >= 0 && y < Board_Size: "y out of bounds";

  String coordinates = x +","+ y;
  if (attempts.contains(coordinates)){
    return "Already tried";
    }
    attempts.add(coordinates);
    String result;
    if (board[x][y] == 1){
        board[x][y] =2;

        for(Ship ship : ships){
            boolean sunk = true;
            for (String pos: ship.getPositions()){
                String[] parts = pos.split(" ");
                int sx = Integer.parseInt(parts[0]);
                int sy = Integer.parseInt(parts[1]);
                assert sx >= 0 && sx < Board_Size;
                assert sy >= 0 && sy < Board_Size;
                if (board[sx][sy] != STATE_HIT){
                    sunk = false;
                    break;
                }

            }
            if(sunk && ship.contains(x,y)){
                result = "sunk!";
                notifyObservers();
                return result;
            }
        }






        result = "Hit";
        }
        else{ board[x][y] =3;
            result = "miss";
    }

    notifyObservers();
    return result;
}
    
  
 /**return true if all hav sunk */
 public boolean checkGameOver(){
    for(Ship ship: ships){
        for (String pos : ship.getPositions()){
            String[] parts = pos.split(" ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            assert x >= 0 && x < Board_Size;
            assert y >= 0 && y < Board_Size;

            if (board[x][y] != 2 ){
                return false;
            }
        }
    }
    return true;
 }

 public void printBoard(){
    System.out.println("  A B C D E F G H I J");
    for (int i = 0; i < Board_Size; i++) {
        System.out.print((i + 1)+ "  ");

        for (int j = 0; j < Board_Size; j++) {
            if (board[i][j] ==  STATE_HIT) {
                System.out.print("H ");
                } else if (board[i][j] == STATE_MISS) {
                    System.out.print("M ");
                    } 
                     
                    else {
                        System.out.print("- ");
                    }


            
        }
            System.out.println();
 }

    
}

     public int getBoardSize(){
        return Board_Size;
     }

    public int [][] getBoard(){
        return board;
    }

    public List<String> getAttempts(){
        return attempts;
    }


}

