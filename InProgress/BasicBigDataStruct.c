#include <stdio.h>

struct SSTable{
   int* segtable;
   int* tree;
   int* treedepth;
   int* currentSeg;
} sstable;


int* btreeify(int* arrayptr, int size){
  printf("From array: ");
  for(int printindex2 = 0; printindex2 < size; printindex2++){
    printf("%d, ", arrayptr[printindex2]);
  }
  printf("\n");
  int* tree = malloc((size * 2) *sizeof(int));
  for(int clearindex = 0; clearindex <= (size * 2); clearindex++){
    tree[clearindex] = 0;
  }
  tree[0] = arrayptr[0];
  int arrayIndex = 1;
  int treeindex = 0;
  int selection = 0;
  int finished = 0;
  while(arrayIndex < size && treeindex * 2 < size * 2){
    selection = arrayptr[arrayIndex];
    if(selection < tree[treeindex]){
      if(tree[2*treeindex + 1] != 0){
        finished++;
      }
      else{
         tree[2*treeindex + 1] = selection;
         arrayIndex++;
         selection = arrayptr[arrayIndex]; 
        }
    }
    if(selection >= tree[treeindex]){
      if(tree[2*treeindex + 2] != 0){
        finished++;
      }
      else{
         tree[2*treeindex + 2] = selection;
         arrayIndex++;
         selection = arrayptr[arrayIndex];
        }
    }
    if(finished == 2){
      finished = 0;
      treeindex++;
    }
  }
  printf("Created Tree:\n");
  for(int printindex = 0; printindex < 12; printindex++){
    printf("%d, ", tree[printindex]);
  }
  printf("\n");
  return tree;
}

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

void compaction(struct SSTable table){
  printf("\nCompacting sstable...");
  for(int index = 0; index < table.currentSeg[0]; index++){
     for(int index1 = index + 1; index1 < table.currentSeg[0]; index1++){
       if(table.segtable[index1] == table.segtable[index] && table.segtable[index] != 0){
         table.segtable[index] = 0;
       }
     }  
  }
  
  int* newptr = malloc(table.currentSeg[0] * sizeof(int));
  newptr = mergesort(table.segtable, table.currentSeg[0]);
  for(int swapindex = 0; swapindex < table.currentSeg[0]; swapindex++){
    table.segtable[swapindex] = newptr[swapindex];
  }
}

void showSSTable(struct SSTable table, int size){
  printf("\ntree:\n");
  for(int index1 = 0; index1 < table.treedepth[0]; index1++){
      printf("%d,", table.tree[index1]);
  }
  printf("\nsstable:\n");

  for(int index = 0; index < table.currentSeg[0]; index++){
    if(index % size == 0){
      printf("\nsegment %d: ", index / size);
    }
    printf("%d, ",table.segtable[index]);
  }
}

void flush(int* treein, int size, struct SSTable table){
  int* sorted = mergesort(treein, size);
  for(int arrayindex = 0; arrayindex < size; arrayindex++){
      if(sorted[arrayindex] != 0){
         table.segtable[arrayindex + table.currentSeg[0]] = sorted[arrayindex];
      }
    }
  printf("\n");
  for(int arrayindex2 = 0; arrayindex2 < size; arrayindex2++){
      printf("%d ,", table.segtable[arrayindex2]);
    }
  printf("\n");
  printf("Flushing b-tree into sstable...\n");
  table.currentSeg[0]+=size;
}

void addTree(int* array, int arraysize, struct SSTable table){
  int* tree = btreeify(array, arraysize);
  table.tree = &tree;
  table.treedepth[0] = arraysize * 2;
}

struct SSTable createLSM(int arraysize, int* array){
  int treesize = arraysize * 2;
  struct SSTable testTable;
  int* segs = malloc(arraysize * 200 * sizeof(int));
  testTable.segtable = segs;
  testTable.currentSeg = malloc(sizeof(int));
  testTable.currentSeg[0] = 1;
  testTable.treedepth = malloc(sizeof(int));
  testTable.treedepth[0] = 1;
  addTree(array, arraysize, testTable);
  int* ptr = malloc(arraysize * sizeof(int));
  for(int clear = 0; clear < treesize; clear++){
    testTable.segtable[clear] = 0;
  }
  return testTable;
}

int main(){
  int* array = malloc(6 * sizeof(int));
  struct SSTable lsm = createLSM(6, array);
  showSSTable(lsm, 12);
  compaction(lsm);
  showSSTable(lsm, 12);
}
