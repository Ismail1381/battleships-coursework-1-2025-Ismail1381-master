import cli.CLIBattleships;
import controller.Controller;
import java.util.Scanner;
import model.Model;
import view.GUIView;

public class Main {
    public static void main(String[] args) {
        System.out.println("battle ships started");
        System.out.println("For cli click 1 fro gui click 2 ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        

        //mvc


        //  cli -no contoller only model
      if(choice.equals("1")){   
        Model model = new Model();
        CLIBattleships cliView = new CLIBattleships(model);
        model.printBoard();
        cliView.startGames();
        // Gui using mvc
      } else if (choice.equals("2")) {
        Model model = new Model();
        Controller controller = new Controller (model);
        GUIView guiView = new GUIView(model, controller);
        model.printBoard();





      }  else {
        System.out.println("invalid pick 1 or 2");
      }
       scanner.close();
        
      } 

    }
