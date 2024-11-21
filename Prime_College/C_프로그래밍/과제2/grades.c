#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_STUDENTS 20   // 최대 학생 수
#define MAX_NAME_LEN 50   // 이름의 최대 길이

int main() {
    FILE *fp;
    char names[MAX_STUDENTS][MAX_NAME_LEN];
    int scores[MAX_STUDENTS];
    int count = 0;
    int i;
    double sum = 0.0;
    double average;
    int maxScore;
    char maxName[MAX_NAME_LEN];

    // 파일 열기
    fp = fopen("grades.txt", "r");
    if (fp == NULL) {
        printf("grades.txt 파일을 열 수 없습니다.\n");
        return 1;
    }

    // 데이터 읽기
    while (fscanf(fp, "%s %d", names[count], &scores[count]) != EOF && count < MAX_STUDENTS) {
        sum += scores[count];
        count++;
    }
    fclose(fp);

    // 평균 계산
    average = sum / count;

    // 최고 성적 초기화
    maxScore = scores[0];
    strcpy(maxName, names[0]);

    // 최고 성적 찾기
    for (i = 1; i < count; i++) {
        if (scores[i] > maxScore) {
            maxScore = scores[i];
            strcpy(maxName, names[i]);
        }
    }

    // 결과 출력
    printf("전체 학생의 평균 성적: %.2f\n", average);
    printf("평균 이상의 성적을 받은 학생:\n");
    for (i = 0; i < count; i++) {
        if (scores[i] >= average) {
            printf("%s: %d\n", names[i], scores[i]);
        }
    }
    printf("최고 성적 학생:\n");
    printf("%s: %d\n", maxName, maxScore);

    return 0;
}