package controller;

import model.Model;

public class Controller {
    private Model model;
    public Controller(Model model){
        this.model = model;
        System.out.println("controller initilized");
    } 
 
    
    public void handleButtonClick(int row, int col){
        String result = model.addAttempt(row, col);
        System.out.println("Button at row " + row + " and column " + col+ result );

        if (model.checkGameOver()){
            System.out.println("Game Over");
        }
   
   
    }

}
    