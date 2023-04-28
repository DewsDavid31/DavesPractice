""" Daily problem: There is an N x M matrix of zeroes. Given N and M,
    Write a function to counter the number of ways starting from the
    top left corner you can arrive at the bottom right corner with
    only moving right and down.
    Ex: with 2x2  you should return 2
        with 5x5 you should return 70"""


def daily_problem(n, m):
    """ Daily problem"""
    matrix = create_matrix(n,m)
    traverse(0,0,matrix)
    return matrix[-1][-1]



def traverse(start_x,start_y,matrix):
    """ Brute force traversal right and down
        incrementing each matrix element visited"""
    if start_x >= len(matrix) or start_y >= len(matrix):
        return
    matrix[start_x][start_y] += 1
    if start_y < len(matrix[0]):
        traverse(start_x,start_y + 1, matrix)
    if start_x < len(matrix):
        traverse(start_x + 1,start_y, matrix)

def create_matrix(n,m):
    """ Uses list comprehension to make a matrix nxm
        of zeroes"""
    matrix = [[] for x in range(n)]
    for row in matrix:
        for _ in range(m):
            row.append(0)
    return matrix

if __name__ == "__main__":
    print(daily_problem(2,2))
    print(daily_problem(5,5))