import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int FRAME_WIDTH = 1300;
    static final int FRAME_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int UNITS = (FRAME_WIDTH*FRAME_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 175;
    final int x[] = new int[UNITS];
    final int y[] = new int[UNITS];
    int bodyBits = 6;
    int applesEaten;
    int AppleX;
    int AppleY;
    char direction = 'R'; 
    boolean running = false;
    Timer timer;
    Random random;  
    
    GamePanel() {

        random = new Random();
        this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame() {
        
        newApple();
            running = true;
            timer = new Timer(DELAY,this);
            timer.start();
    
    }    

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
            draw(g);
    }

    public void draw(Graphics g) {

        if(running) {
        
        /* Removes Grid Lines
        for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
       
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
        */
            
            g.setColor(Color.RED);
            g.fillOval(AppleX, AppleY, UNIT_SIZE, UNIT_SIZE);

        for(int i = 0; i<bodyBits;i++) {
            if(i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            else {
                g.setColor(new Color(45,180,0));
                g.setColor(new Color(random.nextInt(255), random.nextInt(255),random.nextInt(255)));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            
            }
        }

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("SCORE: "+ applesEaten, (FRAME_WIDTH - metrics.stringWidth("SCORE: "+ applesEaten))/2, g.getFont().getSize());

        }

        else {
            gameOver(g);

        }
    }


    public void newApple() {

        AppleX = random.nextInt((int)(FRAME_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        AppleY = random.nextInt((int)(FRAME_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public void move() {
        for(int i = bodyBits;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];

        }

        switch(direction) {
        
        case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;
        case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
        case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
        case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;

        }
    }

    public void checkApple() {

        if((x[0] == AppleX) && (y[0] == AppleY) ) {
            bodyBits++;
            applesEaten++;
            newApple();

        }
    }

    public void checkCollisions() {
    
    // This code checks if the head collides with the body
        for(int i = bodyBits; i>0; i--) {
            if((x[0] == x[i]&& (y[0] == y[i]))) {
                running = false;

            }
        }

    // This checks if the head touches the left border
        if(x[0] < 0) {
            running = false;
        }

    // This checks if the head touches the right border
        if(x[0] > FRAME_WIDTH) {
            running = false;
    }    

    // This checks if the head touches the top border
        if(y[0] < 0) {
            running = false;
    }

    // This checks if the head touches the bottom border
        if(y[0] > FRAME_HEIGHT) {
            running = false;
    }

        if(!running) {
            timer.stop();

        }        
    }

    public void gameOver(Graphics g) {

        //SCORE
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("SCORE: "+ applesEaten, (FRAME_WIDTH - metrics1.stringWidth("SCORE: "+ applesEaten))/2, g.getFont().getSize());

        // GameOver Text
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,75));

        // Lines the text in the middle of the Jframe
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("GAME OVER", (FRAME_WIDTH - metrics2.stringWidth("GAME OVER"))/2, FRAME_HEIGHT/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }    

        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
                
            case KeyEvent.VK_LEFT:
                if(direction != 'R') {
                    direction = 'L';
                    }
                    break;
            case KeyEvent.VK_RIGHT:
                if(direction != 'L') {
                    direction = 'R';
                    }
                    break;
            case KeyEvent.VK_UP:
                if(direction != 'D') {
                    direction = 'U';
                    }
                    break;
            case KeyEvent.VK_DOWN:
                if(direction != 'U') {
                    direction = 'D';
                    }
                    break;
                }        
            }
        }   
    }