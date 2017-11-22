#include <stdio.h>
#include <string.h>

char *str_cpy(char *dst, const char *src)
{
	char *save = dst;
	while(*dst++ = *src++);

	return save;
}

int main(int argc, char* argv[])
{
	/* int i = 0; */
	/* for(i; i<argc; i++){ */
		/* printf("argv[%d] is %s\n", i, argv[i]); */
	/* } */

	char str_src[] = "abcde";
	char str_dst[10] = "";

	str_cpy(str_dst, str_src);
	printf("after string copy:%s\n", str_dst);

	return 0;
}
