#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// 두 요소를 교환하는 함수
void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// 버블 정렬
void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n-1; i++) {
        for (int j = 0; j < n-i-1; j++) {
            if (arr[j] > arr[j+1]) {
                swap(&arr[j], &arr[j+1]);
            }
        }
    }
}

// 선택 정렬
void selectionSort(int arr[], int n) {
    int minIndex;
    for (int i = 0; i < n-1; i++) {
        int tempIndex = i;
        for (int j = i+1; j < n; j++) {
            if (arr[j] < arr[tempIndex]) {
                tempIndex = j;
            }
        }
        swap(&arr[i], &arr[tempIndex]);
    }
}

// 삽입 정렬
void insertionSort(int arr[], int n) {
    int temp;
    int key;
    for (int i = 1; i < n; i++) {
        key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] > temp) {
            arr[j+1] = arr[j];
            j = j - 1;
        }
        arr[j+1] = temp;
    }
}

int main() {
    int arr[500];
    
    // 난수 생성기 초기화를 위해 시간을 시드로 사용
    srand(time(NULL));
    
    // 난수를 사용하여 배열 요소 초기화
    for (int i = 0; i < 500; i++) {
        arr[i] = rand() % 1001;
    }
    
    // 정렬 전 배열 요소 출력
    printf("정렬 이전 배열 요소:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    // 버블 정렬을 사용하여 배열 정렬
    bubbleSort(arr, 500);
    printf("\n\n버블 정렬 이후 배열 요소:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    // 선택 정렬을 사용하여 배열 정렬
    selectionSort(arr, 500);
    printf("\n\n선택 정렬 이후 배열 요소:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    // 삽입 정렬을 사용하여 배열 정렬
    insertionSort(arr, 500);
    printf("\n\n삽입 정렬 이후 배열 요소:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    return 0;
}