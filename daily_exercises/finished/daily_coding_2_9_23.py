import pytest
""" Given the root to a binary tree, implement serialize(root) which serializes the tree into a string and
    deserialize(s), whichh deserializes the string back into a tree
    given a node class with self.val, self.left, self.right the following should pass:
    node = Node('root', Node('left', Node('left.left')),Node('right'))
    assert deserialize(serialize(node)).left.left.val = 'left.left'"""

class Node():
    """ Provided node class given by problem"""
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

def serialize(node):
    """ Required interface"""
    return __recursive_serialize__(node, "")

def __recursive_serialize__(node, curr_string):
    if node is None:
        return curr_string + "None"
    return curr_string + node.val + "," + __recursive_serialize__(node.left, curr_string)+","+__recursive_serialize__(node.right, curr_string)
 
def deserialize(s):
    """ Required interface"""
    array_form = s.split(',')
    return Node(array_form[0],__recursive_deserialize__(array_form, 1),__recursive_deserialize__(array_form, 2))

def __recursive_deserialize__(tree_array, index):
    # FIXME: flip around to bottom-up
    node = Node(tree_array[index])
    left_oob = index * 2  - 1 > len(tree_array)
    right_oob = index * 2  > len(tree_array)
    if left_oob and right_oob:
        return node
    if left_oob:
        node.right = __recursive_deserialize__(tree_array,index * 2 - 1)
        return node
    # right_oob
    node.left = __recursive_deserialize__(tree_array, index * 2 )
    return node



def test_daily_problem():
    """ Daily problem"""
    node = Node('root', Node('left', Node('left.left')),Node('right'))
    assert deserialize(serialize(node)).left.left.val == 'left.left'
    
if __name__ == "__main__":
    test_daily_problem()
