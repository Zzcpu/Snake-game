import java.util.LinkedList;

public class food {
    public LinkedList<int[]> foodList = new LinkedList<>();

    public food() { //default constructor
        //initialize one food item at a random position, in a 16x16 grid
        foodList.add(new int[]{(int)Math.random() * 16, (int)Math.random() * 16});
    }

    public food(LinkedList<int[]> list) { //parameterized constructor
        foodList = list;
    }

    public void add(int x, int y) {
        foodList.add(new int[]{x, y});
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
        int[] newFood = new int[]{(int)(Math.random() * width), (int)(Math.random() * height)};
        for (int i = 0; i < exceptions.size(); i++) { // replaces the proposed food item if it is in the exceptions list
            if (newFood[0] == exceptions.get(i)[0] && newFood[1] == exceptions.get(i)[1]) {
                newFood = new int[]{(int)(Math.random() * width), (int)(Math.random() * height)};
                i = -1;
            }
        }
        foodList.add(newFood);

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
