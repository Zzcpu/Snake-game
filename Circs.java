import java.awt.Graphics;
import java.awt.Color;

public class Circs
{
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int diam;
    private Color col;
    //no default constructor b/c it will not be needed
    public Circs(int xCoor, int yCoor, int d, Color c)
    {
        x=xCoor;
        y=yCoor;
        diam=d;
        col=c;
        vx=1;
        vy=1; 
    }
    public Circs(int xCoor, int yCoor, int d, Color c,int x1, int y1)//extra parameters to change speed/direction?
    {
        x=xCoor;
        y=yCoor;
        diam=d;
        col=c;
        vx=x1;
        vy=y1; 
    }
    public String toString()
    {
        return "("+x+","+y+")"+"\n"+diam;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getvX(){
        return vx;
    }
    public int getvY(){
        return vy;
    }
    public int getDiam()
    {
        return diam;
    }
    private double distance(int x1, int y1, int x2, int y2)
    {
        double sol = Math.sqrt((Math.pow(x2-x1,2))+(Math.pow(y2-y1,2)));
        return sol;
    }
    public int getCenterX()
    {
        return x+(diam/2);
    }
    public int getCenterY()
    {
        return y+(diam/2);
    }
    public void setLocation(int xC,int yC){
        x=xC;
        y=yC;
    }
    
    public void act(){//method for circ movement
        //updating x and y
        x+=vx;
        y+=vy;
    }
    public void setXVel(int x){
        vx=x;
    }
    public void setYVel(int y){
        vy=y;
    }
    public void act(int w, int h)
    {
        //get the next x and y coordinates
        int nextX,nextY;
        nextX=x+vx;
        nextY=y+vy;
    
        //if-statements to handle the Bubble bouncing off of the 4 walls
        if(nextX+diam>w){
            vx*=-1;
        }
        if(nextY+diam>h){
            vy*=-1;
        }
        if(nextX<0){
            vx*=-1;
        }
        if(nextY<0){
            vy*=-1;
        }
        
        //updating x and y
        x+=vx;
        y+=vy;
    }
    public void shrink(int num){//shrinks the size of circs
        if(diam>num)
            diam--;
    }
    
    public void grow(){
        if(diam<200)
            diam++;
    }
    public void drawSelf(Graphics g)    
    {
        g.setColor(col);
        g.fillOval(x, y, diam, diam); 
    }
    public boolean hasCollided(Circs c){
        //Getting the center of this Circs and anotherCircs
        int aX = this.getCenterX();
        int aY = this.getCenterY();
        int bX = c.getCenterX();
        int bY = c.getCenterY();
                
        //getting the radius of this Circs and anotherCircs
        int aR = this.getDiam()/2;
        int bR = c.getDiam()/2;
        return this.distance(aX, aY, bX, bY)<=aR+bR;
    }
    public void handleCollision(Circs anotherCircs)//handles collision between two circs
    {
        //Getting the center of this Circs and anotherCircs
        int aX = this.getCenterX();
        int aY = this.getCenterY();
        int bX = anotherCircs.getCenterX();
        int bY = anotherCircs.getCenterY();
                
        //getting the radius of this Circs and anotherCircs
        int aR = this.getDiam()/2;
        int bR = anotherCircs.getDiam()/2;
        //checking if this Bubble Collided with anotherBubble
        if(this.distance(aX, aY, bX, bY)<=aR+bR){
             //calculating the velocities of this Bubble after colliding with anotherBubble
            vx = aX-bX;
            vy = aY-bY;
            
            //Slowing down the velocities.  otherwise they go crazy
            int maxSpeed = 1;
            
            if(vx<=-maxSpeed)
                vx = -maxSpeed;
            else if(vx>=maxSpeed)
                vx = maxSpeed;
            
            if(vy <= -maxSpeed)
                vy = -maxSpeed;
            else if(vy >= maxSpeed)
                vy = maxSpeed;

        }
   
    }
}
