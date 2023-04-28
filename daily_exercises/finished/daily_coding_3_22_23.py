""" Daily problem: Implement a stack that has the following:
    push(val)
    pop() popping topmost if empty, return null or throw error
    max() returns maximum value or throw an error or return null if empty"""



class Stack:
    """ Simple stack using array implementation"""
    def __init__(self):
        self.array_form = []

    def push(self, val):
        """ pushes value onto end of stack, no return"""
        self.array_form.append(val)

    def pop(self):
        """ removes last pushed item, unless empty, then None"""
        if len(self.array_form) <= 0:
            return None
        else:
            popped = self.array_form[-1]
            self.array_form = self.array_form[:-1] # shortening array
            return popped
        
    def max(self):
        """ pops off all values into array, checking for max
            thought this would be more fun than max() of array"""
        if len(self.array_form) <= 0:
            return None
        local_max = 0
        other_array = []
        # popping into another list
        for _ in range(len(self.array_form)):
            next_val = self.pop()
            if next_val > local_max: # finding maximum
                local_max = next_val
            other_array.append(next_val)
            # pushing back into stack
        for val_index in range(len(other_array) - 1, -1, -1):
            self.push(other_array[val_index])
        return local_max

def daily_problem():
    """ Daily problem: asserts on each feature, since it was easy"""
    test_stack = Stack()
    # pushing 1,2,3,4,12,5
    test_stack.push(1)
    test_stack.push(2)
    test_stack.push(3)
    test_stack.push(12)
    test_stack.push(5)
    # checking maximum is 12
    assert test_stack.max() == 12
    # checking array form is correct
    assert test_stack.array_form == [1,2,3,12,5]
    # checking pop works correctly
    assert test_stack.pop() == 5
    test_stack.pop()
    test_stack.pop()
    test_stack.pop()
    test_stack.pop()
    test_stack.pop()
    # checking for None on empty of max/pop
    assert test_stack.pop() is None
    assert test_stack.max() is None
    return "Stack passes all tests!!"

if __name__ == "__main__":
    print(daily_problem())