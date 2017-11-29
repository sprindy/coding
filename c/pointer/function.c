#include <stdio.h>

int *func1(int *p, int *q) {
	int tmp;
	tmp = *p;
	*p = *q;
	*q = tmp;

	return p;
}
int *func2(int *p, int *q) {

	*p = *p ^ *q;
	*q = *p ^ *q;
	*p = *p ^ *q;

	return p;
}

int *(*pfunc[2])(int *, int *);

int main(int argc, char *argv[])
{
	int a = 3;
	int b = 5;

	printf("a=%d, b=%d\n", a, b);

	pfunc[0] = func1;
	pfunc[1] = func2;

	pfunc[0](&a, &b);
	printf("a=%d, b=%d\n", a, b);

	pfunc[1](&a, &b);
	printf("a=%d, b=%d\n", a, b);

	return 0;
}
