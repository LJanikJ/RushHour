package rushhour;

/**
* The game object.
*/
public class WindowUI {
    public WindowUI() {

    }

    public static void main(String[] args) {
        RushHourParser parser = new RushHourParser("./src/main/resources/RushHourEx2.json");
        RushHour game = new RushHour(parser);

        System.out.println(game.displayPath());
        //System.out.println(game.displayRoot());
    }
}
