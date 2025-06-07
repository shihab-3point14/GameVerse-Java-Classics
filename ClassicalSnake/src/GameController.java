import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class GameController implements GameConstants{

    private JFrame frame;

    private final int[] snakeX = new int[gameUnit];
    private final int[] snakeY = new int[gameUnit];

    private Runnable repaintCallback;

    static final int Delay = 80;
    private int bodyParts = 3;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;

    private Score score=new Score();


    public GameController(JFrame frame) {
        this.frame = frame;
        random = new Random();
        //initial position
        snakeX[0] = unit * 5;
        snakeY[0] = 30 +  unit * 5;//to remove from topbar
        startGame();
    }

    public void startGame(){
        score.reset();
        placeApple();
        running = true;
        timer = new Timer(Delay, e -> update());
        timer.start();

    }
    public void placeApple(){

        appleX = random.nextInt(ScreenWidth / unit) * unit;
        appleY = 30 + random.nextInt((ScreenHeight-30) / unit) * unit;//to remove from top bar
    }
    public void update(){
        if(!running) {return;}
        System.out.println("Snake Head: (" + snakeX[0] + ", " + snakeY[0] + ")");
        move();
        checkApple();
        checkCollision();

        if (repaintCallback != null) {
            repaintCallback.run();
        }

    }
    public void draw(Graphics g){
       if(running){
           //Draw apple
           g.setColor(Color.RED);
           g.fillOval(appleX, appleY, unit, unit);
           g.setColor(Color.BLACK);
           g.drawOval(appleX, appleY, unit, unit); // Apple border

           g.setColor(Color.RED);
           g.fillOval(appleX, appleY, unit, unit);

           //Draw Snake
           for (int i = 0; i < bodyParts; i++){
               if(i == 0){
                   g.setColor(Color.GREEN);
               }
               else {
                   g.setColor(new Color(0, 100, 0));
               }
               g.fillOval(snakeX[i], snakeY[i], unit, unit);
               g.setColor(Color.BLACK);
               g.drawOval(snakeX[i], snakeY[i], unit, unit);
           }
       }
       else gameOver(g);
    }
    public void setRepaintCallback(Runnable repaintCallback) {
        this.repaintCallback = repaintCallback;
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Game Over", (ScreenWidth - metrics.stringWidth("Game Over")) / 2, ScreenHeight / 2);

        // Delay UI changes slightly so the button doesn't flicker in paint
        SwingUtilities.invokeLater(() -> {
            JButton continueBtn = new JButton("Continue");
            continueBtn.setBounds((ScreenWidth - 120) / 2, (ScreenHeight / 2) + 40, 120, 30);

            // Add to layered pane for proper visibility
            JLayeredPane layeredPane = frame.getLayeredPane();
            layeredPane.add(continueBtn, JLayeredPane.POPUP_LAYER);
            continueBtn.setFocusable(false);

            continueBtn.addActionListener(e -> {
                layeredPane.remove(continueBtn);
                layeredPane.repaint();
                SnakeMenu menu = new SnakeMenu(frame);
                frame.setContentPane(menu);
                frame.pack();
                frame.revalidate();
                frame.repaint();
            });
        });

    }

    public void move(){
        //Move BOdy
        for (int i = bodyParts; i >0; i--){
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }
        //directions
        switch (direction){
            case 'U' : snakeY[0] -= unit; break;
            case 'D' : snakeY[0] += unit; break;
            case 'R' : snakeX[0] += unit; break;
            case 'L' : snakeX[0] -= unit; break;
        }

    }
    public void checkApple(){
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            bodyParts++;
            score.increment();
            placeApple();
        }
    }
    public void checkCollision(){
        // Check head body collsion
        for (int i = bodyParts; i > 0; i--) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                running = false;
                timer.stop();
            }
        }

        // Check wall collisions
        if (snakeX[0] < 0 || snakeX[0] >= ScreenWidth || snakeY[0] < 30 || snakeY[0] >= ScreenHeight) {
            running = false;
            timer.stop();
        }
    }
    public Score getScore() {
        return score;
    }
    public void changeDirection(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;

        }
        System.out.println("Direction: " + direction);
    }

}
