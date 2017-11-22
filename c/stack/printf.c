#include <stdio.h>

int main(void)
{
	int val[] = {6, 7, 8, 9, 10};
	int *ptr = val;
	*(ptr++) += 123;

	printf("%d, %d\n", *ptr, *(++ptr));

	return 0;
}
