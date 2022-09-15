#include <stdio.h>

int* splitr(int* arrayptr, int size){
  int splitsize1 = size/2;
  int splitsize2 = size - splitsize1;
  if(size == 1){
    return arrayptr;
  }
  else{
    
    int* resultptr2 = malloc((splitsize2)* sizeof(int));
    for(int splitindex = 0; splitindex < size; splitindex++){
        if(splitindex >= splitsize1){
          resultptr2[splitindex - splitsize1] = arrayptr[splitindex];
        }
    }
    return resultptr2;
  }
}

int* splitl(int* arrayptr, int size){
  int splitsize1 = size/2;
  int splitsize2 = size - splitsize1;
  if(size == 1){
    return arrayptr;
  }
  else{
    
    int* resultptr1 = malloc((splitsize1)* sizeof(int));
    for(int splitindex = 0; splitindex < size; splitindex++){
        if(splitindex < splitsize1){
          resultptr1[splitindex] = arrayptr[splitindex];
        }
    }
    return resultptr1;
  }
}

int* merge(int* arrayptr1,int *arrayptr2, int size1, int size2){
  int* resultptr = malloc((size1 + size2)* sizeof(int));
  int resultIndex = 0;
  int smallestLIndex = 0;
  int smallestRIndex = 0;
  while(1){
    int smallestL = 2147483647;
    int smallestR = 2147483647;
    for(int lindex = 0; lindex < size1; lindex++){
      if(arrayptr1[lindex] == 0){
        continue;
      }
      if(arrayptr1[lindex] < smallestL){
        smallestL = arrayptr1[lindex];
        smallestLIndex = lindex;
      }
    }
    for(int rindex = 0; rindex < size2; rindex++){
      if(arrayptr2[rindex] == 0){
        continue;
      }
      if(arrayptr2[rindex] < smallestR){
        smallestR = arrayptr2[rindex];
        smallestRIndex = rindex;
      }
    }
    if(smallestL == 2147483647 && smallestR == 2147483647){
      return resultptr;
    }
    if(smallestL < smallestR){
      resultptr[resultIndex] = smallestL;
      resultIndex++;
      arrayptr1[smallestLIndex] = 0;
    }
    if(smallestL >= smallestR){
      resultptr[resultIndex] = smallestR;
      resultIndex++;
      arrayptr2[smallestRIndex] = 0;
      }
    }
}

int* mergesort(int* arrayptr, int size){
  if(size == 1){
    return arrayptr;
  }
  else if(size == 2){
    if(arrayptr[0] < arrayptr[1]){
      int swap = arrayptr[0];
      arrayptr[0] = arrayptr[1];
      arrayptr[1] = swap;
      return arrayptr;
    }
    return arrayptr;
  }
  else {
    int remain = size - (size / 2);
    return merge(mergesort(splitl(arrayptr,size),size/2),mergesort(splitr(arrayptr,size),remain),size/2, remain);
    }
}

int main(){
  int* ptr = malloc(6* sizeof(int));
  ptr[0] = 1;
  ptr[1] = 5;
  ptr[2] = 4;
  ptr[3] = 2;
  ptr[4] = 5;
  ptr[5] = 76;
	int* sorted = mergesort(ptr,6);
  for(int printindex = 0; printindex < 6; printindex++){
    printf("%d, ", sorted[printindex]);
  }
}
