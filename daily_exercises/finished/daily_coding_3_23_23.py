""" Daily problem: We can determine how out of order
    an array is by counting the number of inversion
    trees it has. Two elements A[i], A[j] form an
    inversion tree if A[i] > A[j] but i < j. That is
    a smaller element appears before a larger element.
    
    Given an array, count the number of inversions it
    has. Do this faster than O(n^2) time.
    
    You may assume each element is unique.
    Example: [2,4,1,3,5] has 3 (2,1),(4,1),(4,3)
    [5,4,3,2,1] has 10
    """


def daily_problem(A):
    """ Takes running average of window, if exceeds the size of next item, adds
        difference from next item to inversions, which if a range should be the
        amount of inversions!
        Done in O(n), take that baby!"""
    inversions = 0
    total = 0
    for double_index in range(len(A) - 1):
        total += A[double_index] + A[double_index + 1]
        if total / (double_index + 2) > A[double_index + 1]:
            inversions += total / (double_index + 2) - A[double_index + 1]
    return round(inversions)
    
# STUCK, feel ill

if __name__ == "__main__":
    print(daily_problem([2,4,1,3,5]))
    print(daily_problem([5,4,3,2,1]))