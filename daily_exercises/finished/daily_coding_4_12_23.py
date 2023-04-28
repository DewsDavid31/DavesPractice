""" Daily problem: A knight's tour is a sequence pf
    moves by a knight on a chessboard such that all squares
    are visited once.
    
    Given N, write a function to return the number of knights
    tours on an N by N chessboard are possible."""


def daily_problem(N):
    """ Daily problem: paints each visited matrix space using integer increment
        with recursion, ignoring origin but adding each time, then finds minimum increment
        to be floor of how many moves could cover the board. Unsure if this is the solution"""
    board = [[] for _ in range(N)]
    for board_row in board:
        for _ in range(N):
            board_row.append(0)
    for row in range(len(board)):
        for col in range(len(board[0])):
            traverse(board, row, col, [])
    print(board)
    return min(min(board))
    
def traverse(matrix, start_x, start_y, previous):
    """ recursive brute force traversal, creating a knight
        at each end branch."""
    too_far =  start_x >= len(matrix) or start_y >= len(matrix)
    too_close = start_x < 0 or start_y < 0
    if too_far or too_close:
        return
    else:
        previous.append((start_x, start_y))
        matrix[start_x][start_y] += 1
        matrix[0][0] += 1
        if (start_x + 2, start_y + 1) not in previous:
            traverse(matrix, start_x + 2, start_y + 1, previous)
        if (start_x + 1 ,start_y + 2) not in previous:
            traverse(matrix, start_x + 1, start_y + 2, previous)
        if (start_x + 2, start_y + 1) not in previous:
            traverse(matrix, start_x - 2, start_y - 1, previous)
        if (start_x + 2, start_y - 1) not in previous:
            traverse(matrix, start_x + 2, start_y - 1, previous)
        if (start_x + 1, start_y - 2) not in previous:
            traverse(matrix, start_x + 1, start_y - 2, previous)
        if (start_x - 2, start_y + 1) not in previous:
            traverse(matrix, start_x - 2, start_y + 1, previous)
        if (start_x - 1, start_y + 2) not in previous:
            traverse(matrix, start_x - 1, start_y + 2, previous)
if __name__ == "__main__":
    print(daily_problem(8))