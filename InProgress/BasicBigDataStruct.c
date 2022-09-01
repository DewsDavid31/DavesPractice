#include <stdio.h>
int main(){
	printf("My pointers are rusty, so I need some work before merging trees");
	printf("unsorted array 1,5,3,2,5,76\n");
	int testArray[] = {1,5,3,2,5,76};
	for(int x = 0; x < sizeof(testArray)/sizeof(testArray[0]); x = x + 1){
		printf("%i,", testArray[x]);
	}
}



