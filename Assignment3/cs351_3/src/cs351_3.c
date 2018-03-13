/*
 ============================================================================
 Name        : cs351_3.c
 Author      : 
 Version     :
 Copyright   : 
 Description : Helpo World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>


void iterate(char *passthrough,int i,int max,
		void (*pl)(char *, int,  int)){
	if(i<max){
		pl(passthrough,i,max);
		iterate(passthrough,i+1,max,pl);
	}
}

void printline (char *passthrough,int i,int max){
	iterate(" ",i,max,*printf);
	printf(passthrough);
	iterate(" ",(max-i)<<1,(max<<1)-1,*printf);
	printf("%s\n",i?passthrough:"");
	if(i+1==max){
		iterate(passthrough,0,max<<1,*printf);
		printf(passthrough);
	}
}



int main(void) {
	iterate("*",0,5,printline);
	return EXIT_SUCCESS;
}


/*
 //this is what i wanted to do
 void iterate(char *passthrough,int i,int max,
		void (*pl)(char *, int,  int,  void *, void *),
		void (*lp)(char *, int,  int,  void *, void *)){
	if(i<max){
		pl(passthrough,i,max,pl,lp);
		lp(passthrough,i+1,max,pl,lp);
	}
}

void printline (char *passthrough,int i,int max,void (*pl)(char*,int,int,void*,void*),void (*lp)(char*,int,int,void*,void*)){
	lp(" ",i,max,*printf,lp);
	printf(passthrough);
	lp(" ",(max-i)<<1,(max-1)<<1,*printf,lp);
	printf(" %s\n",i?passthrough:"");
	if(i+1==max){
		lp(passthrough,0,max<<1,*printf,lp);
		printf(passthrough);
	}
}



int main(void) {
	iterate("*",0,8,printline,*iterate);
	return EXIT_SUCCESS;
}
 */
