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
import java.util.ArrayList;

public class Main extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    //instance variables
    private int WIDTH;
    private int HEIGHT;
    private int tileSize;
    
    //add variables for snake aka user?
    
    //Default Constructor
    public Main()
    {
        //initializing instance variables
        
        //dimensions of the gui
        WIDTH = 600;
        HEIGHT = 600;
        
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
        System.out.println(key);//find the key int from pressed key
    }   
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {
        //Drawing a Blue Rectangle to be the background (temporary)
        g.setColor(Color.BLUE);
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        this.drawTiles(g);//calls method to draw the tiles for map of game
    }
    public void loop()
    {
        //place to put act method for moving snake
        
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
    public static void main(String[] args)
    {
        Main g = new Main();
        g.start(60);
    }
}
