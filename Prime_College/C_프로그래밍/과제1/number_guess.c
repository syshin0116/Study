#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

int main() {
    srand((unsigned int)time(NULL));
    
    while (true) {
        int secretNumber = rand() % 100 + 1;
        int guess;
        int attempts = 0;
        char playAgain;
        
        printf("1부터 100 사이의 숫자를 맞춰보세요!\n");
        
        do {
            printf("추측 숫자를 입력하세요: ");
            scanf("%d", &guess);
            attempts++;
            
            if (guess > secretNumber) {
                printf("입력한 숫자가 너무 큽니다!\n");
            } else if (guess < secretNumber) {
                printf("입력한 숫자가 너무 작습니다!\n");
            } else {
                printf("축하합니다! %d번 만에 숫자 %d를 맞추셨습니다!\n", attempts, secretNumber);
            }
        } while (guess != secretNumber);
        
        printf("게임을 다시 하시겠습니까? (y/n): ");
        scanf(" %c", &playAgain);
        
        if (playAgain != 'y' && playAgain != 'Y') {
            break;
        }
    }
    
    return 0;
}