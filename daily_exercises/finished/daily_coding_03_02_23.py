""" Daily problem: given a matrix where True is a wall, and out of bondaries is also a wall,
    a tuple for a start point in y,x form, and and end point tuple, give the minimum distance
    from start to end and none if unable
    
    ([[False, False, False, False],[True, True, False, True],[False, False, False, False],[False, False, False, False]],
    (3,0),(0,0)) should return 7"""


def daily_problem(matrix, start, end):
    """ Took exhaustive brute force approach of recursively traversing all directions,
        cutting paths that are too long"""
    if matrix[end[0]][end[1]] or matrix[start[0]][start[1]]:
        return None
    return recursive_brute_force(matrix, start, end, 0)
    
def recursive_brute_force(matrix, start, end, curr_cost):
    """ Brute force recursive method taking all paths and incrementing cost, min it at the end"""
    width = len(matrix[0])
    length = len(matrix)
    longer = max(width, length)
    longest_valid_path = 2 * longer - 2 # found manually to be longest possible path on matrix
    x_out_of_bounds = start[1] < 0 or start[1] >= width
    y_out_of_bounds = start[0] < 0 or start[0] >= length
    if start[0] == end[0] and start[1] == end[1]:
        return curr_cost
    if y_out_of_bounds or x_out_of_bounds or curr_cost > longest_valid_path:
        return longest_valid_path + 1 # assumes longest valid path as infinity
    if matrix[start[0]][start[1]]: # if we are stuck in a wall, return infinity
        return longest_valid_path + 1
    else:
        left_paths = recursive_brute_force(matrix, (start[1] - 1, start[0]), end, curr_cost + 1)
        right_paths = recursive_brute_force(matrix, (start[1] + 1, start[0]), end, curr_cost + 1)
        down_paths = recursive_brute_force(matrix, (start[1], start[0] - 1), end, curr_cost + 1)
        up_paths = recursive_brute_force(matrix, (start[1], start[0] +  1), end, curr_cost + 1)
        return min(left_paths, right_paths, down_paths, up_paths) # find lowest of all brute-force paths

if __name__ == "__main__":
    print(daily_problem([[False, False, False, False],[True, True, False, True],[False, False, False, False],[False, False, False, False]], (3,0),(0,0)))