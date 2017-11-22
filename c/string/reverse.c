#include <stdio.h>
#include <string.h>

char* reverse_1(char* src)
{
	if(src==NULL)
		return NULL;

	char* p_s = src;
	char* p_l = src + strlen(src) - 1;
	char tmp = 0;

	while(p_s < p_l){
		tmp = *p_l;
		*p_l-- = *p_s;
		*p_s++ = tmp;
	}

	return src;
}

char* reverse_2(char* src)
{
	if(src==NULL)
		return NULL;

	char* r = src;
	char* p_s = src;
	char* p_l = src + strlen(src) - 1;

	while(p_s < p_l){
		*p_l = *p_l ^ *p_s;
		*p_s = *p_l ^ *p_s;
		*p_l = *p_l ^ *p_s;
		p_l--;
		p_s++;
	}

	return r;
}
int main(int argc, char* argv[])
{
	/* int i = 0; */
	/* for(i; i<argc; i++){ */
		/* printf("argv[%d] is %s\n", i, argv[i]); */
	/* } */

	char str[] = "abcde";
	printf("before reverse:%s\n", str);
	printf("after reverse_1:%s\n", reverse_1(str));
	printf("after reverse_2:%s\n", reverse_2(str));

	return 0;
}
