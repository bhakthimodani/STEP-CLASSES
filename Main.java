import java.util.*;

public class Main {
    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove))
            return "Draw";

        if ((playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
            (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
            (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper")))
            return "Player Wins";

        return "Computer Wins";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();

        String[] moves = {"Rock", "Paper", "Scissors"};
        String[] results = new String[5];
        String[] players = new String[5];
        String[] computers = new String[5];

        int wins = 0, losses = 0, draws = 0;

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter your move: ");
            players[i] = sc.nextLine();

            computers[i] = moves[r.nextInt(3)];
            results[i] = playRound(players[i], computers[i]);

            System.out.println(results[i]);

            if (results[i].equals("Player Wins")) wins++;
            else if (results[i].equals("Computer Wins")) losses++;
            else draws++;
        }

        System.out.println("\nRound\tPlayer\tComputer\tResult");

        for (int i = 0; i < 5; i++)
            System.out.println((i + 1) + "\t" + players[i] + "\t" +
                    computers[i] + "\t\t" + results[i]);

        double winPercentage = (wins / 5.0) * 100;

        System.out.println("\nWins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Draws: " + draws);
        System.out.println("Win % = " + winPercentage + "%");
    }
}