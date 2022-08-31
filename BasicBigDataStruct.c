import <stdio.h>
import <string.h>

struct DiskNode{
/*Heap only*/
}
struct MemNode{
/*Stack only*/
}
struct DiskBTree{
	DiskNode[] nodes;
	DiskNode root;	

}
struct MemBTree{
	MemNode[] nodes;
	MemNode root;	
}
struct LSMTree{
	MemBTree c0;
	DiskBTree c1;
}
