#include <stdio.h>
#include <stdlib.h>

int count = 0;

struct student {
	char name[20];
	int  number;
	struct student *next;
};

struct student *create() {
	struct student *head = NULL;
	struct student *end, *new;

	end = new = (struct student *)malloc(sizeof(struct student));
	printf("please enter your name, then number\n");
	scanf("%s", new->name);
	scanf("%d", &new->number);

	while(0 != new->number) {
		count++;
		if(1 == count) {
			new->next = head;
			end = new;
			head = new;
		}
		else {
			new->next = NULL;
			end->next = new;
			end = new;
		}
		new = (struct student *)malloc(sizeof(struct student));
		scanf("%s", new->name);
		scanf("%d", &new->number);
	}
	free(new);

	return head;
}

void print(struct student *head) {
	struct student *temp;
	int    index = 1;
	printf("the list has %d numbers\n", count);
	temp = head;
	while(NULL != temp) {
		printf("the NO%d member:\n", index);
		printf("the name is:%s\n", temp->name);
		printf("the number is:%d\n", temp->number);

		temp = temp->next;
		index++;
	}
}

int main()
{
	struct student *head;
	head = create();
	print(head);

	return 0;
}
