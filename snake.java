import java.util.LinkedList;

public class snake {
    public LinkedList<int[]> snakeBody = new LinkedList<>();
    public String snakeHeadDirection;
    public String nextSnakeHeadDirection;
    
    public snake() { //default constructor
        //initialize snake of size 4 and pointing upwards near the center of the screen, [8, 8] - [8, 11]
        snakeBody.add(new int[]{8, 8}); //  0
        snakeBody.add(new int[]{8, 9}); //  1
        snakeBody.add(new int[]{8, 10}); // 2
        snakeBody.add(new int[]{8, 11}); // 3
        snakeHeadDirection = "UP";
        nextSnakeHeadDirection = snakeHeadDirection;
    }

    public snake(int x, int y, int length, String dir) { //parameterized constructor
        snakeBody.add(new int[]{x, y});
        if (dir ==  "UP") {
            for (int i = 0; i < length - 1; i++) {  snakeBody.add(new int[]{x, y + i});  }
        } else if (dir ==  "DOWN") {
            for (int i = 0; i < length - 1; i++) {  snakeBody.add(new int[]{x, y - i});  }
        } else if (dir ==  "LEFT") {
            for (int i = 0; i < length - 1; i++) {  snakeBody.add(new int[]{x + i, y});  }
        } else if (dir ==  "RIGHT") {
            for (int i = 0; i < length - 1; i++) {  snakeBody.add(new int[]{x - i, y});  }
        }
        snakeHeadDirection = dir;
    }

    public void add() {
        // adds a segment at the position of the tail (2 segments will share the same position)
        snakeBody.add(new int[]{snakeBody.getLast()[0], snakeBody.getLast()[1]});
    }

    public void subtract() {
        // subtracts a tail segment
        snakeBody.removeLast();
    }

    public int[] getSegment(int index) { // returns the value at the specified segment
        return snakeBody.get(index);
    }

    public int[] getNextSegment() { // returns the value at the next segment (the segment that will be the head after inching)
        int[] head = snakeBody.get(0);
        int[] nextSegment = new int[]{head[0], head[1]};
        
        if (snakeHeadDirection ==  "UP") {
            nextSegment[1] -= 1;
        } else if (snakeHeadDirection ==  "DOWN") {
            nextSegment[1] += 1;
        } else if (snakeHeadDirection ==  "LEFT") {
            nextSegment[0] -= 1;
        } else if (snakeHeadDirection ==  "RIGHT") {
            nextSegment[0] += 1;
        }
        
        return nextSegment;
    }

    public void setDirection(String dir) { // sets the direction of the snake's head
        snakeHeadDirection = dir;
    }

    public void setNextDirection(String dir) { // sets the next direction of the snake's head
        nextSnakeHeadDirection = dir;
    }

    public void updateDirection() {
        snakeHeadDirection = nextSnakeHeadDirection; // updates the direction of the snake's head to the next direction
    }

    public String getDirection() { // returns the direction of the snake's head
        return snakeHeadDirection;
    }

    public void inch() { // the snake inches
        for (int i = snakeBody.size() - 1; i > 0; i--) { // moves each segment to the position of the one in front of it, starting from the tail
            int[] prev = snakeBody.get(i - 1);
            snakeBody.set(i, new int[]{prev[0], prev[1]});
            //System.out.println("Seg" + i + ": [" + snakeBody.get(i)[0] + ", " + snakeBody.get(i)[1] + "]"); //print position of current segment
        }
        if (snakeHeadDirection ==  "UP") { // moves the head last, in the direction specified by snakeHeadDirection
            snakeBody.set(0, new int[]{snakeBody.get(0)[0], snakeBody.get(0)[1] -= 1});
        } else if (snakeHeadDirection ==  "DOWN") {
            snakeBody.set(0, new int[]{snakeBody.get(0)[0], snakeBody.get(0)[1] += 1});
        } else if (snakeHeadDirection ==  "LEFT") {
            snakeBody.set(0, new int[]{snakeBody.get(0)[0] -= 1, snakeBody.get(0)[1]});
        } else if (snakeHeadDirection ==  "RIGHT") {
            snakeBody.set(0, new int[]{snakeBody.get(0)[0] += 1, snakeBody.get(0)[1]});
        }
        // System.out.println(snakeHeadDirection);
        // System.out.println("Head: [" + snakeBody.get(0)[0] + ", " + snakeBody.get(0)[1] + "]");
        // System.out.println("==========================");
    }

    public boolean checkSelfCollision() { // checks if the snake has collided with itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeBody.get(0)[0] == snakeBody.get(i)[0] && snakeBody.get(0)[1] == snakeBody.get(i)[1]) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNextSelfCollision() { // checks if the snake will collide with itself in the next move
        int[] head = snakeBody.get(0);
        int[] futureHead = new int[]{head[0], head[1]};
        
        if (snakeHeadDirection ==  "UP") {
            futureHead[1] -= 1;
        } else if (snakeHeadDirection ==  "DOWN") {
            futureHead[1] += 1;
        } else if (snakeHeadDirection ==  "LEFT") {
            futureHead[0] -= 1;
        } else if (snakeHeadDirection ==  "RIGHT") {
            futureHead[0] += 1;
        }
        
        for (int i = 1; i < snakeBody.size(); i++) {
            if (futureHead[0] == snakeBody.get(i)[0] && futureHead[1] == snakeBody.get(i)[1]) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTileCollision(int[] tile) { // checks if the snake's head is on the specified tile
        return snakeBody.get(0)[0] == tile[0] && snakeBody.get(0)[1] == tile[1];
    }

    public boolean checkNextTileCollision(int[] tile) { // checks if the snake's head will be on the specified tile in the next move
        int[] head = snakeBody.get(0);
        int[] futureHead = new int[]{head[0], head[1]};
        
        if (snakeHeadDirection ==  "UP") {
            futureHead[1] -= 1;
        } else if (snakeHeadDirection ==  "DOWN") {
            futureHead[1] += 1;
        } else if (snakeHeadDirection ==  "LEFT") {
            futureHead[0] -= 1;
        } else if (snakeHeadDirection ==  "RIGHT") {
            futureHead[0] += 1;
        }
        
        return futureHead[0] == tile[0] && futureHead[1] == tile[1];
    }
}