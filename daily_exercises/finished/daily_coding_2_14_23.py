""" Daily problem: : A unival tree (which stands for universal tree) is a tree where all nodes under it have the same value.
        Given a binary tree, count the number of unival subtrees for example: (0 1 ((1 1 1) 0)) has 5 """

class Tree:
    """ Tree defined by array binary implementation with first element being unused root, set that to 0, None is empty parts of tree"""
    def __init__(self, array_form, index):
        if index > len(array_form):
            self.root = None
        else:
            self.root = array_form[index]
        self.index = index
        self.array_form = array_form
    def left(self):
        temp = self.array_form
        if len(temp) <= 0:
            return None
        return Tree(temp[(self.index + 1) * 2:], self.index * 2)

    def right(self):
        temp = self.array_form
        if len(temp) <= 0:
            return None
        return Tree(temp[(self.index + 1) * 2 + 1:], self.index * 2 + 1)

    def get_left(self):
        if self.index * 2 < len(self.array_form):
            return self.array_form[self.index * 2]
        return None

    def get_right(self):
        if self.index * 2 + 1 < len(self.array_form):
            return self.array_form[self.index * 2 + 1]
        return None

    def parent(self):
        self.index =  round(self.index / 2)
        self.root = self.array_form[self.index]

def daily_problem(tree):
    """ Daily problem"""
    return traverse_universal(tree)
    

def traverse_universal(tree):
    """ Traverses and checks for lone-children, or same children, then adds 1 if the parent is the same for an outer tree"""
    if tree.root is None:
        return 0
    bonus = 0
    if tree.root == tree.get_left() or tree.root == tree.get_right():
        bonus = 1
    print(f"{tree.get_left()}. {tree.get_right()}")
    if tree.get_right() == tree.get_left() and tree.get_left() is not None or tree.get_left() is not tree.get_right():
            print("add 1")
            return  bonus + 1 + traverse_universal(tree.left()) + traverse_universal(tree.right())
    else:
            return bonus + traverse_universal(tree.left()) + traverse_universal(tree.right())


if __name__ == "__main__":
    test_tree = Tree([0,0,1,0,None,None,1,0,None,None,None,None,1,1,None,None],0)
    print(daily_problem(test_tree))
