#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// �� ��Ҹ� ��ȯ�ϴ� �Լ�
void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// ���� ����
void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n-1; i++) {
        for (int j = 0; j < n-i-1; j++) {
            if (arr[j] > arr[j+1]) {
                swap(&arr[j], &arr[j+1]);
            }
        }
    }
}

// ���� ����
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

// ���� ����
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
    
    // ���� ������ �ʱ�ȭ�� ���� �ð��� �õ�� ���
    srand(time(NULL));
    
    // ������ ����Ͽ� �迭 ��� �ʱ�ȭ
    for (int i = 0; i < 500; i++) {
        arr[i] = rand() % 1001;
    }
    
    // ���� �� �迭 ��� ���
    printf("���� ���� �迭 ���:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    // ���� ������ ����Ͽ� �迭 ����
    bubbleSort(arr, 500);
    printf("\n\n���� ���� ���� �迭 ���:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    // ���� ������ ����Ͽ� �迭 ����
    selectionSort(arr, 500);
    printf("\n\n���� ���� ���� �迭 ���:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    // ���� ������ ����Ͽ� �迭 ����
    insertionSort(arr, 500);
    printf("\n\n���� ���� ���� �迭 ���:\n");
    for (int i = 0; i < 500; i++) {
        printf("%d ", arr[i]);
    }
    
    return 0;
}