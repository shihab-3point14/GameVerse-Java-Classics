import javax.swing.*;
import java.awt.*;

public class SnakeMenu extends JPanel{
    private JFrame fm;
    public SnakeMenu(JFrame frame) {
        this.fm=frame;
        setLayout(new GridLayout(5,1,10,10));
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));

        JButton newGamebtn=new JButton("New Game");
        JButton resumebtn=new JButton("Resume");
        JButton scoresbtn=new JButton("High Score");
        JButton returnMenubtn=new JButton("Return to Main Menu");
        JButton exitbtn=new JButton("Exit");

        //Action Listener
        newGamebtn.addActionListener(e ->startNewGame());
        resumebtn.addActionListener(e -> resumeGame());
        scoresbtn.addActionListener(e -> showScore());
        returnMenubtn.addActionListener(e -> returnMenu());
        exitbtn.addActionListener(e -> System.exit(0));

        //Add Buttons
        add(newGamebtn);
        add(resumebtn);
        add(scoresbtn);
        add(returnMenubtn);
        add(exitbtn);

    }

    private void startNewGame() {
        GameBoard board=new GameBoard(new GameController(fm), fm);
        switchPanel(board);
    }
    private void resumeGame() {
        JOptionPane.showMessageDialog(fm,"Not Found!");
    }
    private void showScore() {
        JOptionPane.showMessageDialog(fm,"High Scores: ");
    }

    private void returnMenu() {
        SnakeMenu menu=new SnakeMenu(fm);
        switchPanel(menu);
    }
    private void switchPanel(JPanel nextpanel){
        fm.setContentPane(nextpanel);
        fm.revalidate();
        fm.pack();
        fm.repaint();
        nextpanel.requestFocusInWindow();

    }

}
