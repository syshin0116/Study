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
        { // ����Ʈ�� �ϳ��� ��常 ����
            printf("%d->", p->data);
        }
        else
        { // ����Ʈ�� �� �� �̻��� ���� ����
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
        printf("�޸� �Ҵ� ����\n");
        exit(1);
    }

    new_node->data = data;
    new_node->link = NULL;
    return (new_node);
}

// ���� �Լ�
void front_insert(ListNode **pp, int data)
{
}

// ���� �Լ�
void rear_insert(ListNode **pp, int data)
{
}

// ���� �Լ�
ListNode *front_remove(ListNode **pp)
{
}

// ���� �Լ�
void concatenate(ListNode **p1, ListNode **p2)
{
}

int main()
{   
    printf("����\n");

    ListNode *head1 = NULL; // ��� ������
    ListNode *head2 = NULL; // ��� ������
    ListNode *new_node = NULL;
    ListNode *removed_node = NULL;
    int data;
    FILE *fp1 = NULL;
    FILE *fp2 = NULL;

    // ����: ���� list1.txt�� �б� ���ؼ� �����ϰ�, ���� ���� ������ �߻��ϸ�, "list1.txt ���� ���� ����"��� �޽��� ����ϰ� ���α׷� ����
    fp1 = fopen("./list1.txt", "r");
    char str[MAX];
    
    while (feof(fp1) == 0){
        
        fgets(data, MAX, fp1);
        if (ferror(fp1)){
            printf("list1.txt ���� ���� ����\n");
            exit(1);
        }
        printf("%s", data);
        fclose(fp1);
    }
    

    // ����: ���� list2.txt�� �б� ���ؼ� �����ϰ�, ���� ���� ������ �߻��ϸ�, "list2.txt ���� ���� ����"��� �޽��� ����ϰ� ���α׷� ����
    fp2 = fopen("./list2.txt", "r");
    char str2[MAX];
    
    while (feof(fp2) == 0){
        
        fgets(str2, MAX, fp2);
        if (ferror(fp2)){
            printf("list2.txt ���� ���� ����\n");
            exit(1);
        }
        printf("%s", str2);
        fclose(fp2);
    }
    printf("1. front_insert �Լ� ����: \n");
    while (fscanf(fp1, "%d", &data) != EOF)
    {
        front_insert(&head1, data);
    }
    display(head1);
    printf("\n");

    printf("2. rear_insert �Լ� ����: \n");
    while (fscanf(fp2, "%d", &data) != EOF)
    {
        rear_insert(&head2, data);
    }
    display(head2);
    printf("\n");

    printf("3. front_remove �Լ� ����:  \n");
    removed_node = front_remove(&head1);
    printf("removed node data in head1 list: %d \n", removed_node->data);
    free(removed_node);
    display(head1);

    removed_node = front_remove(&head2);
    printf("removed node data in head2 list: %d \n", removed_node->data);
    free(removed_node);
    display(head2);
    printf("\n");

    printf("4. concatenate �Լ� ����: \n");
    concatenate(&head1, &head2);
    display(head1);
    printf("\n");

    fclose(fp1);
    fclose(fp2);

    return 0;
}