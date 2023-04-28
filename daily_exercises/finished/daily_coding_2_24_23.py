""" Daily problem: given an array of integers and an int k
    return the array of integers that are the maximum in of each
    combination of k size.
    For example: [10,5,2,7,8,7] and k = 3 should return [10,7,8,8]"""


def daily_problem(int_array, k):
    """ Just iterating over a slice size of k,
    then taking the max of that slice, easy peasy"""
    new_list = []
    for compare_index in range(len(int_array) - k + 1):
        new_list.append(max(int_array[compare_index:compare_index + k]))
    return new_list

if __name__ == "__main__":
    print(daily_problem([10,5,2,7,8,7],3))