import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JPanel implements GameConstants {

    private JFrame frame;
    private GameController gameController;

    public GameBoard(GameController controller,JFrame frame) {
        this.gameController = controller;
        this.frame = frame;

        setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);

        controller.setRepaintCallback(this::repaint);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode());
                gameController.changeDirection(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //For border
        g.setColor(Color.WHITE);
        g.drawRect(0, 30, ScreenWidth - 1, ScreenHeight - 31);

        // Draw score bar
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ScreenWidth, 30);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("SCORE: " + gameController.getScore().getCurrentScore(), 10, 20);
        g.drawString("HIGH SCORE: " + gameController.getScore().getHighScore(), ScreenWidth - 170, 20);

        gameController.draw(g);
    }
}