""" Daily problem: Given a 2d matrix of cahracters and a target word,
    Write a function that returns whether the word can be found in the matrix
    by going left-right or top-down
    
    ex: F A C I
        O B Q P
        A N O B
        M A S S
                should return True on both MASS and FOAM"""


def daily_problem(matrix, query):
    """ Daily problem: simple iteration swapping length/width for
        vertical/horizontal words"""
    for row in range(len(matrix)):
        string_horizontal = ""
        string_vertical = ""
        for col in range(len(matrix[0])):
            string_vertical += matrix[col][row]
            string_horizontal += matrix[row][col]
        if query in string_vertical or string_horizontal:
            return True
    return False

if __name__ == "__main__":
    print(daily_problem([['F','A','C','I'],['O','B','Q','P'],['A','N', 'O','B'],['M','A','S','S']], "MASS"))
    print(daily_problem([['F','A','C','I'],['O','B','Q','P'],['A','N', 'O','B'],['M','A','S','S']], "FOAM"))