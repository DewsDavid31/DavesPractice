""" Daily problem: Given a pre-order and in-order
    traversals of a binary tree, wright a function to
    reconstruct said tree.
    
    For example: [a,b,d,e,c,f,g], [d,b,e,a,f,c,g] should make the following tree:
    
                  a
        
            b           c
        d       e   f       g"""


class MyTree:
    """ Basic tree class with treeify, my function"""
    def __init__(self, root):
        self.root = root

    def print(self):
        """ print method, no return"""
        self.recursive_print(self.root,1)
    
    def recursive_print(self,curr_node, layer):
        """ recursive version, prints tree terribly sideways and flipped"""
        if curr_node is None:
            return ""
        else:
            for _ in range(layer):
                print("\t",end="")
            print(curr_node.val + "<")
            self.recursive_print(curr_node.left(), layer + 1)
            self.recursive_print(curr_node.right(), layer + 1) 


    def treeify(self, preorder, inorder):
        """ Terrible execution of building mini-trees on each side of root using in-order"""
        midpoint = round(len(inorder) / 2) - 1
        for left_side_index in range(midpoint):
            of_three = left_side_index % 3
            if of_three == 0:
                left_node = MyNode(inorder[left_side_index],None, None)
            if of_three == 1:
                new_root = MyNode(inorder[left_side_index], None, None)
            if of_three == 2:
                right_node = MyNode(inorder[left_side_index],None,None)
                new_root.lchild = left_node
                new_root.rchild = right_node
        true_root = MyNode(inorder[midpoint],new_root,None)
        for left_side_index in range(midpoint +1,len(inorder)):
            of_three = (left_side_index - (midpoint + 1)) % 3
            if of_three == 0:
                left_node = MyNode(inorder[left_side_index],None, None)
            if of_three == 1:
                new_root = MyNode(inorder[left_side_index], None, None)
            if of_three == 2:
                right_node = MyNode(inorder[left_side_index],None,None)
                new_root.lchild = left_node
                new_root.rchild = right_node
        true_root.rchild = new_root
        self.root = true_root
       
           
           

class MyNode:
    """ Basic node class I needed"""
    def __init__(self, val, lchild, rchild):
        self.val = val
        self.lchild = lchild
        self.rchild = rchild

    def left(self):
        """ getter for left node"""
        return self.lchild
    
    def right(self):
        """ getter for right node"""
        return self.rchild

def daily_problem(preorder, inorder):
    """ Daily problem, just basic calls, preorder is unused"""
    test_tree = MyTree(MyNode("a",None,None))
    test_tree.treeify(preorder, inorder)
    return test_tree.print()

if __name__ == "__main__":
    print(daily_problem(['a','b','d','e','c','f','g'], ['d','b','e','a','f','c','g']))
