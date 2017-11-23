#include <stdio.h>

union val{
	char c[4];
	int  i;
};

int main(int argc, char *argv[])
{
	union val data;
	data.c[0] = 0x01;
	data.c[1] = 0x02;
	data.c[2] = 0x03;
	data.c[3] = 0x04;

	printf("0x%x ", data.i);
	if((data.i && 0xff) == data.c[0])
		printf("little endian\n");
	else
		printf("big endian\n");
}
