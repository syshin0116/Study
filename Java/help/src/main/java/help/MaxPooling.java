package help;

import java.util.Scanner;

public class MaxPooling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int M = scanner.nextInt();
        int N = scanner.nextInt();

        // 2차원 배열 입력
        int[][] matrix = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // 최대 풀링 결과 배열 생성
        int pooledM = M / 2;
        int pooledN = N / 2;
        int[][] pooledMatrix = new int[pooledM][pooledN];

        // 최대 풀링 수행
        for (int i = 0; i < pooledM; i++) {
            for (int j = 0; j < pooledN; j++) {
                int max = Integer.MIN_VALUE;
                for (int x = i * 2; x < i * 2 + 2; x++) {
                    for (int y = j * 2; y < j * 2 + 2; y++) {
                        if (matrix[x][y] > max) {
                            max = matrix[x][y];
                        }
                    }
                }
                pooledMatrix[i][j] = max;
            }
        }

        // 최대 풀링 결과 출력
        for (int i = 0; i < pooledM; i++) {
            for (int j = 0; j < pooledN; j++) {
                System.out.print(pooledMatrix[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
