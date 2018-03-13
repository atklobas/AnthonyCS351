#include<stdio.h>

void encrypt(char *str, int key){
	while(*str!=0){
		int caps=*str&0x20;
		*str=((*str-caps-'A'+key)%26)+'A'+caps;
		str++;
	}
}
void decrypt(char *str, int key){
	encrypt(str,26-(key%26));
}
int main(){
	char input[1000];
	int offset=0;

	printf("Enter a word:");
	scanf("%s",input);
	printf("Enter a number:");
	scanf("%d",&offset);
	encrypt(input,offset);
	printf("Encrypted text:%s\n",input);
	decrypt(input,offset);
	printf("Encrypted text:%s\n",input);
	return 0;
}
