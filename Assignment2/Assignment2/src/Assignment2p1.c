#include <stdio.h>
#include <unistd.h>
#include <time.h>

struct Date{
int month;
int day;
int year;
};
struct Contact{
char firstname[16];//15+1 for nul
char lastname[16];
struct Date dob;
};

int getAge(struct Date* date,struct Date* today){
	return today->year-date->year-1+(today->month>date->month||(today->month==date->month&&today->day>=date->day));
}
void loadContacts(FILE *in, struct Contact* contacts, int *i){
    while(fscanf(in,"%s %s %d-%d-%d", &contacts[*i].firstname,&contacts[*i].lastname,&contacts[*i].dob.day,&contacts[*i].dob.month,&contacts[*i].dob.year)>1){
    (*i)++;
    }
}
struct Date getToday(){
	struct Date today;
	struct tm *now;
	time_t tnow;
	time(&tnow);
	now = localtime(&tnow);
	today.year=now->tm_year+1900;
	today.month=now->tm_mon+1;
	today.day=now->tm_mday;
	return today;

}
int main(int argc, char *argv[]){
	FILE *in;
	in=fopen("contacts.txt","r");
	struct Contact contacts[100];
	int count=0;
	loadContacts(in,contacts,&count);
	struct Date today=getToday();
	for(int i=0;i<count;i++){
		int age=getAge(&contacts[i].dob,&today);
		if(age>=18&&age<=25)
			printf("%.1s. %s, %d\n",contacts[i].firstname,contacts[i].lastname,age);
	}
	return 0;
}
