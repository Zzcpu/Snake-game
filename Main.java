import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;

public class Main extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    //instance variables
    private int WIDTH;
    private int HEIGHT;
    private int tileSize;
    private snake s  = new snake();

    //Default Constructor
    public Main()
    {
        //initializing instance variables
        

        //dimensions of the gui
        WIDTH = 640;
        HEIGHT = 640;
        
        //the size of the tiles
        tileSize=40;
        
        //Setting up the GUI
        JFrame gui = new JFrame();//this makes the gui box
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//makes sure program can close
        gui.setTitle("Snake Game");//this is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(WIDTH+5,HEIGHT+30));//setting the size for the gui
        gui.setResizable(false);//makes it so the gui cant be resized
        gui.getContentPane().add(this);//Adding this class to the gui
        
        /*if after you finish everything you can declare your buttons or other things
        * at this spot. After gui.getContentPane().add(this) and BEFORE gui.pack();
        */
        
        gui.pack();//packs everything together
        gui.setLocationRelativeTo(null);//makes so the gui opens in the center of screen
        gui.setVisible(true);//makes the gui visible
        gui.addKeyListener(this);//stating that this object will listen to the keyboard
        gui.addMouseListener(this);//stating that this object will listen to the Mouse
        gui.addMouseMotionListener(this);//stating that this object will acknowledge when the Mouse moves

    }
    //This method will acknowledge user input
    public void keyPressed(KeyEvent e) 
    {
        //getting the key pressed
        int key = e.getKeyCode();
        if (s.getDirection() != "RIGHT" && (key == 37 || key == 65)) { // wasd or arrow keys
            s.setNextDirection("LEFT");
        }
        else if (s.getDirection() != "DOWN" && (key == 38 || key == 87)) {
            s.setNextDirection("UP");
        }
        else if (s.getDirection() != "LEFT" && (key == 39 || key == 68)) {
            s.setNextDirection("RIGHT");
        }
        else if (s.getDirection() != "UP" && (key == 40 || key == 83)) {
            s.setNextDirection("DOWN");
        }

        // System.out.println(key);//find the key int from pressed key
    }   
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {
        //Drawing a Blue Rectangle to be the background (temporary)
        g.setColor(Color.BLUE);
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        this.drawTiles(g);//calls method to draw the tiles for map of game
        this.drawSnake(g);//calls method to draw the snake
    }
    public void loop()
    {
        //place to put act method for moving snake
        s.updateDirection();
        if (!s.checkNextSelfCollision() && !checkWallCollision()) { // checks for collision before moving
            s.inch();
        }
        //Do not write below this
        repaint();
    }
    
    //These methods are required by the compiler.  
    //You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e) 
    {
    }
    public void keyReleased(KeyEvent e) 
    {
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
        s.add();
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mouseMoved(MouseEvent e)
    {
    }
    public void mouseDragged(MouseEvent e)
    {
    }
    public void start(final int ticks){
        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };	
        gameThread.start();
    }
    
    public void drawTiles(Graphics g){//method to draw tiles
        for(int r = 0; r<WIDTH/tileSize;r++){//total # of rows of tiles is number of tiles that fit the width of background
            for(int c = 0; c<HEIGHT/tileSize;c++){//total # of columns of tiles is number of tiles that fit the height of background
                if((r+c)%2==0){//condition to set alternating colors for each tile
                    g.setColor(Color.ORANGE);
                    g.fillRect(r*tileSize,c*tileSize,tileSize,tileSize);
                }
                else{
                    g.setColor(Color.GREEN);
                    g.fillRect(r*tileSize,c*tileSize,tileSize,tileSize);
                }
            }
        }
    }

    public void drawSnake(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < s.snakeBody.size(); i++) {
            int[] segment = s.getSegment(i);
            g.fillRect(segment[0] * tileSize, segment[1] * tileSize, tileSize, tileSize);
        }
    }

    public boolean checkWallCollision() {
        int gridWidth = WIDTH / tileSize;
        int gridHeight = HEIGHT / tileSize;
        int[] head = s.getNextSegment();
        //  || left wall   || right wall           || top wall    || bottom wall
        return head[0] < 0 || head[0] >= gridWidth || head[1] < 0 || head[1] >= gridHeight;
    }

    public static void main(String[] args)
    {
        Main g = new Main();
        g.start(6);
    }
}
