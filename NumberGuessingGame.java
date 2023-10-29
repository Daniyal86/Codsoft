import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 5;
        int totalScore = 0;
        
        System.out.println("Welcome to the Number Guessing Game!");

        System.out.print("How many rounds do you want to play? ");
        int rounds = scanner.nextInt();

        for (int round = 1; round <= rounds; round++) {
            int numberToGuess = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            boolean hasGuessedCorrectly = false;
            
            System.out.println("\nRound " + round);
            
            while (attempts < maxAttempts) {
                System.out.print("Guess the number between " + minRange + " and " + maxRange + ": ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == numberToGuess) {
                    System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    totalScore += attempts;
                    hasGuessedCorrectly = true;
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!hasGuessedCorrectly) {
                System.out.println("Sorry, you've used all " + maxAttempts + " attempts. The number was " + numberToGuess + ".");
            }
        }

        System.out.println("Game over! Your total score is " + totalScore + " attempts.");
    }
}
