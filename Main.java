/**
 * Snake Game
 * By: Anton and Zihang
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

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
    private food f = new food(); // by default generates one food item, you can add more if you want
    // also best not to use default food constructor since the play area size is hardcoded

    //Default Constructor
    public Main()
    {
        //initializing instance variables

        //dimensions of the gui
        WIDTH = 640;
        HEIGHT = 640;
        
        //the size of the tiles
        tileSize=40;

        f.clear();
        f.generateFood((WIDTH/tileSize), (HEIGHT/tileSize), s.getBody());
        f.generateFood((WIDTH/tileSize), (HEIGHT/tileSize), s.getBody());
        f.generateFood((WIDTH/tileSize), (HEIGHT/tileSize), s.getBody()); // generates 3 random food items

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
        if (s.getDirection() != "DOWN" && (key == 38 || key == 87)) {
            s.setNextDirection("UP");
        }
        if (s.getDirection() != "LEFT" && (key == 39 || key == 68)) {
            s.setNextDirection("RIGHT");
        }
        if (s.getDirection() != "UP" && (key == 40 || key == 83)) {
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
        this.drawFood(g);//calls method to draw the food
    }
    public void loop()
    {
        //place to put act method for moving snake
        s.updateDirection();
        if (!s.checkNextSelfCollision() && !checkWallCollision()) { // checks for collision before moving
            if (checkFoodCollision()) { // checks for food before moving
                int foodItem = f.isFood(s.getNextSegment()[0], s.getNextSegment()[1]); // gets the food that was eaten
                if (f.getFood(foodItem)[2] == 0) { // healthy food logic
                    s.add(1);
                    // // removes a random food item,  to clear away the poison food
                    // f.remove((int)(Math.random() * f.size()));
                    // f.generateFood((WIDTH/tileSize), (HEIGHT/tileSize), s.getBody());
                } else {
                    s.subtract(1);
                }
                f.remove(foodItem);
                LinkedList<int[]> exceptions = new LinkedList<>(s.getBody()); // Combine snake body and food positions as exceptions
                for (int i = 0; i < f.size(); i++) {
                    exceptions.add(f.getFood(i));
                }
                f.generateFood((WIDTH/tileSize), (HEIGHT/tileSize), exceptions);
            }
            if (s.getBody().size() == 0) { // if the snake eats too much poison food, it dies
                System.exit(0);
            }
            s.inch(); // moving should take place last
        } else {
            System.exit(0);
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
                    g.setColor(new Color(0x3ac267));
                    g.fillRect(r*tileSize,c*tileSize,tileSize,tileSize);
                }
                else{
                    g.setColor(new Color(0x37a65b));
                    g.fillRect(r*tileSize,c*tileSize,tileSize,tileSize);
                }
            }
        }
    }

    public void drawSnake(Graphics g) {
        g.setColor(new Color(0x005eff));
        for (int i = 0; i < s.snakeBody.size(); i++) {
            int[] segment = s.getSegment(i);
            g.fillRect(segment[0] * tileSize, segment[1] * tileSize, tileSize, tileSize);
        }
    }

    public void drawFood(Graphics g) {
        for (int i = 0; i < f.size(); i++) {
            int[] foodItem = f.getFood(i);
            if (f.getFood(i)[2] == 0) {
                g.setColor(new Color(0xbf263b));
            } else {
                g.setColor(new Color(0xbf253b)); // poison food color, almost identical to healthy food
            }
            g.fillRect(foodItem[0] * tileSize, foodItem[1] * tileSize, tileSize, tileSize);
        }
    }

    public boolean checkWallCollision() { // checks if the snake's head is colliding with the walls
        int gridWidth = WIDTH / tileSize;
        int gridHeight = HEIGHT / tileSize;
        int[] head = s.getNextSegment();
        //  || left wall   || right wall           || top wall    || bottom wall
        return head[0] < 0 || head[0] >= gridWidth || head[1] < 0 || head[1] >= gridHeight;
    }

    public boolean checkFoodCollision() { // checks if the snake's head is on any food tile
        for (int i = 0; i < f.size(); i++) {
            if (s.checkNextTileCollision(f.getFood(i))) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args)
    {
        Main g = new Main();
        g.start(6);
    }
}
