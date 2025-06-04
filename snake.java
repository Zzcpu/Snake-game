import java.util.LinkedList;

public class snake {
    public LinkedList<int[]> snakeBody = new LinkedList<>();
    public String snakeHeadDirection;
    
    public snake() { //default constructor
        //initialize snake of size 4 and pointing upwards near the center of the screen, [8, 8] - [8, 11]
        snakeBody.add(new int[]{8, 8}); //  0
        snakeBody.add(new int[]{8, 9}); //  1
        snakeBody.add(new int[]{8, 10}); // 2
        snakeBody.add(new int[]{8, 11}); // 3
        snakeHeadDirection = "UP";
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

    public void setDirection(String dir) { // sets the direction of the snake's head
        snakeHeadDirection = dir;
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
}