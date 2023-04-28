""" Daily problem: given the root of a
    binary search tree, find the second largest node."""


class bin_tree:
    def __init__(self, array_form):
        self.array_form = array_form
        self.current = 0
        self.val = array_form[0]
    
    def right(self):
        if self.current >= len(self.array_form) - 1:
            return None
        self.current = 2 * self.current + 2
        self.val = self.array_form[self.current]
        return self
    
    def left(self):
        if self.current >= len(self.array_form) - 1:
            return None
        self.current = 2 * self.current + 1
        self.val = self.array_form[self.current]
        return self

    def get_right(self):
        if self.current * 2 + 2 > len(self.array_form):
            return None
        return self.array_form[self.current * 2 + 2]
    
    def get_left(self):
        if self.current * 2 + 1 > len(self.array_form):
            return None
        return self.array_form[self.current * 2 + 1]
    
def daily_problem(root_obj):
    """ Daily problem: assuming binary tree operates right this way,
    the rightmost childs sibling to the left should be second largest"""
    while root_obj.get_right() is not None:
        root_obj = root_obj.right()
    if root.get_left is None:
        return root.val
    root.left()
    return root.val

if __name__ == "__main__":
    root = bin_tree([12,1,13])
    print(daily_problem(root))