package view;

import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Model;

public class GUIView extends JFrame implements Observer {
  private final Model model;
  private final Controller controller;
  private final JButton[][] buttons;

  public GUIView(Model model, Controller controller) {
    super("battleships");
    this.model = model;
    this.controller = controller;
    model.addObserver(this);

    int size = model.getBoardSize();
    buttons = new JButton[size][size];

    initLayout(size);
    Buttons(size);
    

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void initLayout(int size) {
    getContentPane().setLayout(new GridLayout(size, size));
  }

  private void Buttons(int size) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            final int row = i;
            final int col =j;

            JButton btn = new JButton("");
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            buttons[i][j] = btn;
            btn.setPreferredSize(new Dimension(60,60));
            btn.addActionListener( e -> {
                String result = model.addAttempt(row, col);
                System.out.println("clicked" + row + "," + col + " = " + result);
                if("Hit".equalsIgnoreCase(result)|| "sunk!".equalsIgnoreCase(result)){
                    btn.setText("H");
                    btn.setBackground(Color.RED);

                    if ("Sunk!".equalsIgnoreCase(result)){
                        JOptionPane.showMessageDialog(this, "you sunk the ship");
                    }


                } else {
                    btn.setText("M");
                }
                btn.setEnabled(false);

                  if (model.checkGameOver()){
                    int attempts = model.getAttempts().size();
                    JOptionPane.showMessageDialog(this, "winner!!\nTotal attempts =" + attempts);
                  }

            });
            add(btn);
  }
}
  }

           

           
            
             
    
  

  
  @Override
  public void update() {
    int[][] board = model.getBoard();
    int size = model.getBoardSize();


    for (int i = 0; i < size; i++){
        for (int j = 0; j < size; j++){
            int state = board [i][j];
            JButton btn = buttons[i][j];

            switch (state){
                case Model.STATE_HIT:
                    btn.setText("H");
                    btn.setEnabled(false);
                    btn.setBackground(Color.RED);
                break;
                case Model.STATE_MISS:
                   btn.setText("M");
                   btn.setEnabled(false);
                   btn.setBackground(Color.GREEN);
                break;
                default:
                   btn.setText("");
                break;

                
            }

        }
    }
    revalidate();
    repaint();

    
  }
}
