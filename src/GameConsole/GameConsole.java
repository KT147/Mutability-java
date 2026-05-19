package GameConsole;

import java.util.Scanner;

public class GameConsole<T extends Game<? extends Player>> {

    private final T game;
    private static final Scanner scanner = new Scanner(System.in);

    public GameConsole(T game) {
        this.game = game;
    }

    public int addPlayer() {
        System.out.println("What is your name");
        String name = scanner.nextLine();
        System.out.println("Your name is " + name);

        //delegate to the games addPlayer method
        return game.addPlayer(name);
    }

    public void playGame(int playerIndex) {

        boolean done = false;
        while (!done) {
            var gameActions = game.getGameActions(playerIndex);
            System.out.println("Select from these actions");
            for (Character c : gameActions.keySet()) {
                String prompt = gameActions.get(c).prompt();
                System.out.println("Prompt " + prompt + " key " + c);
            }
            System.out.println("Enter next action");

            char nextMove = scanner.nextLine().toUpperCase().charAt(0);
            GameAction gameAction = gameActions.get(nextMove);

            if (gameAction != null) {
                System.out.println("------------------");
                done = game.executeGameAction(playerIndex, gameAction);
                if (!done) {
                    System.out.println("------------------");
                }
            }

        }
    }


}
