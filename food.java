import java.util.LinkedList;

public class food {
    public LinkedList<int[]> foodList = new LinkedList<>();

    public food() { //default constructor
        foodList.add(new int[]{(int)Math.random() * 16, (int)Math.random() * 16, 0}); //initialize one food item at a random position, in a 16x16 grid
        // third item in int[] dictates if the food is poison. 0 = healthy, 1 = poison
    }

    public food(LinkedList<int[]> list) { //parameterized constructor
        foodList = list;
    }

    public void add(int x, int y, int poison) {
        foodList.add(new int[]{x, y, poison});
    }

    public void remove(int index) {
        if (index >= 0 && index < foodList.size()) {
            foodList.remove(index);
        }
    }

    public void clear() {
        foodList.clear();
    }

    public int size() {
        return foodList.size();
    }

    public void generateFood(int width, int height, LinkedList<int[]> exceptions) { // generates a new food item at a random position within the specified width and height
        int poison;
        if (hasHealthyFood()) { // there must be at least one healthy food item
            if (Math.random() < 1.0 / 3.0) { // 1 in 3 chance of generating poison food
                poison = 1;
            } else {
                poison = 0;
            }
        } else {
            poison = 0;
        }
        int[] newFood = new int[]{(int)(Math.random() * width), (int)(Math.random() * height), poison};
        for (int i = 0; i < exceptions.size(); i++) { // checks the exception list
            if (newFood[0] == exceptions.get(i)[0] && newFood[1] == exceptions.get(i)[1]) { // if the food is in the exception list
                newFood = new int[]{(int)(Math.random() * width), (int)(Math.random() * height), poison}; // replaces the food with a new one
                i = -1; // and checks again
            }
        }
        foodList.add(newFood);

    }

    public boolean hasHealthyFood() { // returns true if there is at least one healthy food item
        for (int i = 0; i < foodList.size(); i++) {
            if (foodList.get(i)[2] == 0) { // checks if the food item is healthy (0)
                return true;
            }
        }
        return false;
    }

    public int[] getFood(int index) { // returns location of the food item at the specified index
        if (index >= 0 && index < foodList.size()) {
            return foodList.get(index);
        }
        return null;
    }

    public int isFood(int x, int y) { // returns which food item is at the specified position, or -1 if no food is there
        for (int i = 0; i < foodList.size(); i++) {
            int[] foodItem = foodList.get(i);
            if (foodItem[0] == x && foodItem[1] == y) {
                return i;
            }
        }
        return -1;
    }
}
