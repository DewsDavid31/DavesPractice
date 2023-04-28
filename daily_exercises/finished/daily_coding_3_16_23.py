""" Daily problem: You have an N x N board.
    Write a function that given N, 
    returns the number of possible arrangements
    of the board where N queens can be placed on
    the board without threatening eachother, i.e. no
    two queens share the same row, column or diagonal"""


def daily_problem(n):
    """ Daily problem: brute force approach of slicing 2d array
        and comparing before adding a piece with all possible starter pieces."""
    previous_boards = []
    board = []
    for start_row in range(n - 1):
        for start_col in range(n - 1):
            board = []
            for _ in range(n):
                temp_row = []
                for _ in range(n):
                    temp_row.append(False)
                board.append(temp_row)
            board[start_col][start_row] = True
            pieces = 1
            for col_index in range(0,len(board)):
                col = board[col_index]
                for row_index in range(0,len(board[0])):
                    row = find_row(board,row_index)
                    diagonal = find_diagonal(board, col_index,row_index)
                    if True in col or True in row or True in diagonal:
                        continue
                    else:
                        board[col_index][row_index] = True
                        pieces += 1
            if pieces == n:
                previous_boards.append(board)
    return len(previous_boards)


def find_row(board,row_index):
    rows = []
    row_increment = 0
    for col in board:
        row_increment = 0
        for row in col:
            if row_index == row_increment:
                rows.append(row)
            row_increment += 1
    return rows

def find_diagonal(board,col_index,row_index):
    diagonal = []
    for col_increment in range(len(board)):
        for row_increment in range(len(board[0])):
            if abs(col_increment - col_index) == abs(row_increment - row_index):
                diagonal.append(board[col_increment][row_increment])
    return diagonal            

if __name__ == "__main__":
    print(daily_problem(4))