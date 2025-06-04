import java.awt.Graphics;
import java.awt.Color;

public class Food extends Circs{
    private boolean isSafe;//instance variable to tell if food is harmful 
    private String type;
    
    public Food(String s,boolean b,int xCoor, int yCoor, int d, Color c,int x1, int y1){
        super(xCoor,yCoor,d, c,x1, y1);
        type=s;
        isSafe=b;
    }
    
    public String getType(){
        return type;
    }
    public boolean isSafe(){
        return isSafe;
    }
    
    public int getRow(){//returns the nth row food is on starting from position 0
        return this.getY()/this.getDiam();
    }
    public int getCol(){//returns the nth column food is on starting from position 0
        return this.getX()/this.getDiam();
    }
}