#define _CRT_SECURE_NO_WARNINGS
#define MAX 100
#include <stdio.h>
#include <stdlib.h>

typedef struct ListNode
{
    int data;
    struct ListNode *link;
} ListNode;

void front_insert(ListNode **pp, int data);
void rear_insert(ListNode **pp, int data);
ListNode *front_remove(ListNode **pp);
void concatenate(ListNode **p1, ListNode **p2);

void display(ListNode *head)
{
    ListNode *p = head;

    if (p != NULL)
    {
        if (p->link == head)
        { // 리스트에 하나의 노드만 존재
            printf("%d->", p->data);
        }
        else
        { // 리스트에 두 개 이상의 노드들 존재
            p = p->link;
            printf("%d->", p->data);
            while (p != head)
            {
                p = p->link;
                printf("%d->", p->data);
            }
        }
    }
    printf("\n \n");
}

ListNode *create_node(int data)
{
    ListNode *new_node;

    new_node = (ListNode *)malloc(sizeof(ListNode));
    if (new_node == NULL)
    {
        printf("메모리 할당 에러\n");
        exit(1);
    }

    new_node->data = data;
    new_node->link = NULL;
    return (new_node);
}

// 구현 함수
void front_insert(ListNode **pp, int data)
{
}

// 구현 함수
void rear_insert(ListNode **pp, int data)
{
}

// 구현 함수
ListNode *front_remove(ListNode **pp)
{
}

// 구현 함수
void concatenate(ListNode **p1, ListNode **p2)
{
}

int main()
{   
    printf("실행\n");

    ListNode *head1 = NULL; // 헤드 포인터
    ListNode *head2 = NULL; // 헤드 포인터
    ListNode *new_node = NULL;
    ListNode *removed_node = NULL;
    int data;
    FILE *fp1 = NULL;
    FILE *fp2 = NULL;

    // 구현: 파일 list1.txt를 읽기 위해서 오픈하고, 파일 오픈 오류가 발생하면, "list1.txt 파일 오픈 오류"라는 메시지 출력하고 프로그램 종료
    fp1 = fopen("./list1.txt", "r");
    char str[MAX];
    
    while (feof(fp1) == 0){
        
        fgets(data, MAX, fp1);
        if (ferror(fp1)){
            printf("list1.txt 파일 오픈 오류\n");
            exit(1);
        }
        printf("%s", data);
        fclose(fp1);
    }
    

    // 구현: 파일 list2.txt를 읽기 위해서 오픈하고, 파일 오픈 오류가 발생하면, "list2.txt 파일 오픈 오류"라는 메시지 출력하고 프로그램 종료
    fp2 = fopen("./list2.txt", "r");
    char str2[MAX];
    
    while (feof(fp2) == 0){
        
        fgets(str2, MAX, fp2);
        if (ferror(fp2)){
            printf("list2.txt 파일 오픈 오류\n");
            exit(1);
        }
        printf("%s", str2);
        fclose(fp2);
    }
    printf("1. front_insert 함수 실행: \n");
    while (fscanf(fp1, "%d", &data) != EOF)
    {
        front_insert(&head1, data);
    }
    display(head1);
    printf("\n");

    printf("2. rear_insert 함수 실행: \n");
    while (fscanf(fp2, "%d", &data) != EOF)
    {
        rear_insert(&head2, data);
    }
    display(head2);
    printf("\n");

    printf("3. front_remove 함수 실행:  \n");
    removed_node = front_remove(&head1);
    printf("removed node data in head1 list: %d \n", removed_node->data);
    free(removed_node);
    display(head1);

    removed_node = front_remove(&head2);
    printf("removed node data in head2 list: %d \n", removed_node->data);
    free(removed_node);
    display(head2);
    printf("\n");

    printf("4. concatenate 함수 실행: \n");
    concatenate(&head1, &head2);
    display(head1);
    printf("\n");

    fclose(fp1);
    fclose(fp2);

    return 0;
}