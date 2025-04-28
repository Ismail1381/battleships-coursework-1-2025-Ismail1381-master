package cli;

import java.util.Scanner;
import model.Model;

public class CLIBattleships {
    private Model model;
    private Scanner scanner;



    public CLIBattleships(Model model){
        this.model = model;
        this.scanner = new Scanner(System.in);
        
    }

    public void startGames(){
        System.out.println("battleships game started!!");
        System.out.flush();
        boolean gameRunning = true;

        while(gameRunning){
            model.printBoard();
            System.out.print("Enter guess");
            String input = scanner.nextLine().toUpperCase().trim();



            if (input.matches("[A-J](10|[1-9])")){
                int x = Integer.parseInt(input.substring(1))- 1;
                int y = input.charAt(0)- 'A';
                String results = model.addAttempt(x, y);
                System.out.println(results);

                if (model.checkGameOver()){
                    System.out.println("game over, you sunk all ships");
                    System.out.println("Total attempts: " + model.getAttempts().size());
                    
                    gameRunning = false;
                }


            }
            else {
                System.out.println("Invalid input");
            }
        }
    }
   
    


   
}
