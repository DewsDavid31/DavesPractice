#include <stdio.h>

struct LSM{
   int* segtable;
   int* tree;
   int* treedepth;
   int* currentSeg;
   int* indextable;
   int* indextablesize;
} lsm;


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

void compaction(struct LSM table, int size){
  printf("\nCompacting sstable...");
  for(int index = 0; index < table.currentSeg[0]; index++){
     for(int index1 = index + 1; index1 < table.currentSeg[0]; index1++){
       if(table.segtable[index1] == table.segtable[index] && table.segtable[index] != 0){
         table.segtable[index] = 0;
       }
     }
  int* newptr = malloc(table.currentSeg[0] * sizeof(int));
  newptr = mergesort(table.segtable, table.currentSeg[0]);
  for(int swapindex = 0; swapindex < table.currentSeg[0]; swapindex++){
    table.segtable[swapindex] = newptr[swapindex];
  }
 }
  while(table.segtable[table.currentSeg[0]] == 0){  
     table.currentSeg[0]--;  
  }
  int current = table.currentSeg[0];
  if(current < size){
    table.currentSeg[0] = size + 1;
  }
  else{
    table.currentSeg[0] += (current % size) + 1;
  }
}

void showLSM(struct LSM table, int arraysize){
  int size = arraysize * 2;
  printf("\ntree: ");
  for(int index1 = 0; index1 < table.treedepth[0]; index1++){
      printf("%d,", table.tree[index1]);
  }
  printf("\n\nsstable:\n");

  for(int index = 0; index < table.currentSeg[0]; index++){
    if(index % size == 0){
      printf("\n\tsegment %d: ", index / size);
    }
    printf("%d, ",table.segtable[index]);
  }
  printf("\n\n");
}

void flush( int size, struct LSM table){
  int* unsorted = malloc(size * sizeof(int));
  for(int index = 0; index < size; index++){
    unsorted[index] = table.tree[index];
  }
  int* sorted = mergesort(unsorted, size);
  for(int arrayindex = 0; arrayindex < size; arrayindex++){
      if(sorted[arrayindex] != 0){
         table.segtable[arrayindex + table.currentSeg[0] - 1] = sorted[arrayindex];
      }
    }
  for(int clear = 0; clear < size; clear++ ){
    table.tree[clear] = 0;
  }
  printf("\nFlushing b-tree into sstable...\n");
  table.currentSeg[0]+=size;
  table.indextable[table.indextablesize[0]] = table.segtable[table.currentSeg[0]];
  table.indextablesize[0]++;
}

void addTree(int* array, int arraysize, struct LSM table){
  int* tree = btreeify(array, arraysize);
  for(int index = 0; index < arraysize * 2; index++){
    table.tree[index] = tree[index];
  }
  table.tree = tree;
  table.treedepth[0] = arraysize * 2;
}

struct LSM createLSM(int arraysize, int* array){
  int treesize = arraysize * 2;
  struct LSM testTable;
  int* segs = malloc(arraysize * 200 * sizeof(int));
  testTable.segtable = segs;
  testTable.currentSeg = malloc(sizeof(int));
  testTable.currentSeg[0] = 1;
  testTable.treedepth = malloc(sizeof(int));
  testTable.treedepth[0] = 0;
  testTable.tree = malloc(arraysize * 2 * sizeof(int));
  testTable.indextable = malloc(200 * sizeof(int));
  testTable.indextablesize = malloc(sizeof(int));
  testTable.indextablesize[0]=1;
  addTree(array, arraysize, testTable);
  return testTable;
}

int searchTree(int root, int key, struct LSM lsm){
  if(root >= lsm.treedepth[0]){
    return -1;
  }
  else if(key == lsm.tree[root]){
    return root;
  }
  else if(key > lsm.tree[root]){
    return searchTree((root * 2) + 2, key, lsm);
  }
  else{
    return searchTree((root * 2) + 1, key, lsm);
  }
}

int read(int key, struct LSM lsm, int arraysize){
  int result = -1;
  int treeresult = searchTree(0, key, lsm);
  if(treeresult != -1){
    printf("Found in memory tree!");
    return treeresult;
  }
  else{
    int start = 0;
    for(int segindex = 0; segindex < lsm.indextablesize[0]; segindex++){
      if(lsm.indextable[segindex] >= key){
        start = segindex * arraysize;
      }
    }
    for(int remainder = start; remainder < lsm.currentSeg[0]; remainder++){
      if(lsm.segtable[remainder] == key){
        printf("Found in storage segments!");
        return remainder;
      }
    }
    return result;
  }
}

void menu(int selection, struct LSM lsm, int arraysize){
  if(selection == 0){
    showLSM(lsm, arraysize);
    printf("LSM object demo:\n1. Write a tree of size %d to LSM\n2. Read a value from lsm\n3. Flush memory to disk\n4. compress/compact segments\n5. Quit\n", arraysize);
  int userselection = 0;
  printf("Make a selection: ");
  scanf("%d", &userselection);
  printf("\n");
  menu(userselection, lsm, arraysize);
  }
  else if(selection == 1){
    int* newarray = malloc(arraysize * sizeof(int));
    for(int inputindex = 0; inputindex < arraysize; inputindex++){
      printf("\nEnter a value: ");
      scanf("%d", &newarray[inputindex]);
    }
    addTree(newarray, arraysize, lsm);
    menu(0,lsm,arraysize);
  }
  else if(selection == 2){
    int key = 0;
    int source = 0;
    printf("\nEnter a value to find: ");
    scanf("%d", &key);
    int result = read(key, lsm, arraysize);
    if(result == -1){
      printf("\nNot found in LSM object!\n");
    }
    else{
      printf("\nFound in index: %d with value: %d\n", result, key);
    }
    menu(0,lsm,arraysize);
  }
  else if(selection == 3){
    flush(arraysize * 2, lsm);
    menu(0,lsm,arraysize);
  }
  else if(selection == 4){
    compaction(lsm, arraysize * 2);
    menu(0, lsm, arraysize);
  }
}

int main(){
  int arraysize = 6;
  int* array = malloc(arraysize * sizeof(int));
  struct LSM lsm = createLSM(arraysize, array);
  menu(0, lsm, arraysize);
}
