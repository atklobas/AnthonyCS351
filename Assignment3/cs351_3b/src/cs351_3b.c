/*
 ============================================================================
 Name        : cs351_3b.c
 Author      : 
 Version     :
 Copyright   : 
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

int main(void) {
	srand(time(NULL));
	int secret=rand()%100;
	printf("Guess the number:");
	int guess=-1;
	int tries=1;
	scanf("%d",&guess);

	while(guess!=secret){
		if(guess>secret){
			printf("Too high. Guess again. ");
		}else{
			printf("Too low. Guess again. ");
		}
		scanf("%d",&guess);
		tries++;
	}
	printf("You guessed correctly in %d tries!",tries);
	return EXIT_SUCCESS;
}
