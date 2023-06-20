package help;

import java.util.Scanner;

public class TournamentBracket {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int participants = scanner.nextInt();

        double[] powerData = new double[participants];
        for (int i = 0; i < participants; i++) {
            powerData[i] = scanner.nextDouble();
        }

        int numRounds = (int) (Math.log(participants) / Math.log(2));
        int[][] bracket = new int[numRounds + 1][participants];

        for (int i = 0; i < participants; i++) {
            bracket[0][i] = i;
        }

        for (int round = 1; round <= numRounds; round++) {
            for (int i = 0; i < participants / (int) Math.pow(2, round); i++) {
                int left = bracket[round - 1][2 * i];
                int right = bracket[round - 1][2 * i + 1];
                bracket[round][i] = (powerData[left] >= powerData[right]) ? left : right;
            }
        }

        for (int round = 0; round <= numRounds; round++) {
            for (int i = 0; i < participants / (int) Math.pow(2, round); i++) {
                if (bracket[round][i] != 0) {
                    System.out.print(bracket[round][i] + " ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}
