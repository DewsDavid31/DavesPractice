""" Daily problem: Suppose an arithmatic expression
    is given as a binary tree with each leaf as an int
    and each middle branch as an operation of: /*-+
    Given the root, write a function to evaluate it.
    example:
        *
      +    +
    3  2  4  5
    evaluates to 45"""

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


def daily_problem(root):
    """ Daily problem"""
    traverser = Traverser()
    return traverser.evaluate(root)

class Traverser:
    """ Class to hold recursive variables well"""
    def __init__(self):
        self.array_form = []
    
    def evaluate(self, root):
        """ poorly made evaluation using traversal, splitting
            tree in half and evaluating each half first left to right."""
        operands = ['/','*','+','-']
        pre_root = 0
        post_root = 0
        result = 0
        curr_operand = '+'
        self.traverse(root)
        for char in self.array_form[1:round(len(self.array_form) / 2)]:
            if char not in operands:
                if curr_operand == '*':
                    pre_root *= char
                if curr_operand == '-':
                    pre_root -= char
                if curr_operand == '/':
                    pre_root /= char
                if curr_operand == '+':
                    pre_root += char
            elif char == '*':
                curr_operand = '*'
            elif char == '/':
                curr_operand = '/'
            elif char == '+':
                curr_operand = '+'
            else:
                curr_operand = '-'
        curr_operand = '+'
        for char in self.array_form[round(len(self.array_form) / 2):]:
            if char not in operands:
                if curr_operand == '*':
                    post_root *= char
                if curr_operand == '-':
                    post_root -= char
                if curr_operand == '/':
                    post_root /= char
                if curr_operand == '+':
                    post_root += char
            elif char == '*':
                curr_operand = '*'
            elif char == '/':
                curr_operand = '/'
            elif char == '+':
                curr_operand = '+'
            else:
                curr_operand = '-'
        root_val = self.array_form[0]
        if root_val == '*':
            result = pre_root * post_root
        if root_val == '-':
            result = pre_root - post_root
        if root_val == '/':
            result = pre_root / post_root
        if root_val == '+':
            result = pre_root + post_root
        return result

    def traverse(self, root):
        """ simple helper method to convert binary tree into array"""
        self.array_form.append(root.val)
        if root.lchild is not None:
            self.traverse(root.lchild)
        if root.rchild is not None:
            self.traverse(root.rchild)


if __name__ == "__main__":
    test_tree = MyNode('*', None, None)
    test_tree.lchild = MyNode('+',MyNode(3,None, None), MyNode(2,None, None))
    test_tree.rchild = MyNode('+',MyNode(4, None, None), MyNode(5, None, None))
    print(daily_problem(test_tree))