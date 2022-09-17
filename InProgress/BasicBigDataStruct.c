#include <stdio.h>

struct SSTable{
   int** segtable;
   int* segsizes;
   int *currentSeg;
} sstable;


int* btreeify(int* arrayptr, int size){
  printf("From array: ");
  for(int printindex2 = 0; printindex2 < size; printindex2++){
    printf("%d, ", arrayptr[printindex2]);
  }
  printf("\n");
  int* tree = malloc((size * 2) *sizeof(int));
  for(int clearindex = 0; clearindex < (size * 2); clearindex++){
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


void showSSTable(struct SSTable table){
  printf("sstable:\n");
  int current = table.currentSeg[0];
  for(int index = 0; index <= current; index++){
    printf("segment %d: ",index);
    int* nextSeg = table.segtable[index];
    for(int arrayindex = 0; arrayindex < table.segsizes[index]; arrayindex++){
      printf("%d ,", nextSeg[arrayindex]);
    }
    printf("\n");
  }
}

void flush(int* tree, int size, struct SSTable table){
  (*table.currentSeg)++;
  int nextseg = table.currentSeg[0];
  table.segsizes[nextseg] = size;
  int* segment = malloc(size * sizeof(int));
  for(int flushindex = 0; flushindex < size; flushindex++){
    if(tree[flushindex] != 0){
       segment[flushindex] = tree[flushindex];
       printf("appending %d ",tree[flushindex]);
    }  
  }
  printf("\n");
  int* sortedseg = mergesort(segment, size);
  table.segtable[nextseg] = sortedseg;
  printf("Flushing b-tree into sstable...\n");
  showSSTable(table);
}

void compaction(int* seg1, int* seg2, int size1, int size2,struct SSTable table){
  printf("Compacting two segments...of total size %d\n", size1 + size2);
  int* nextseg = malloc((size1 + size2) * sizeof(int));
  for(int index = 0; index < size1 + size2; index++){
    if(index < size1){
      nextseg[index] = seg1[index];
    }else{
      nextseg[index] = seg2[index - size1];
    }
  }
  flush(nextseg, (size1 + size2), table);
  showSSTable(table);
}

int main(){
  int arraysize = 6;
  int* ptr = malloc(arraysize * sizeof(int));
  ptr[0] = 1;
  ptr[1] = 5;
  ptr[2] = 4;
  ptr[3] = 2;
  ptr[4] = 5;
  ptr[5] = 76;
  int* ptr2 = malloc(arraysize * sizeof(int));
  ptr2[0] = 22;
  ptr2[1] = 3;
  ptr2[2] = 4;
  ptr2[3] = 4;
  ptr2[4] = 70;
  ptr2[5] = 1;
	int* treed = btreeify(ptr,arraysize);
  int* treed2 = btreeify(ptr2,arraysize);
  struct SSTable testTable;
  int* segs = malloc(arraysize * 200 * sizeof(int));
  int* segsizes = malloc(200 * sizeof(int));
  testTable.segtable = &segs;
  testTable.segsizes = segsizes;
  testTable.currentSeg = malloc(sizeof(int));
  testTable.currentSeg[0] = -1;
  flush(treed, arraysize * 2, testTable);
  flush(treed2,arraysize * 2, testTable);
  compaction(testTable.segtable[0],testTable.segtable[1],12, 12, testTable);
}
