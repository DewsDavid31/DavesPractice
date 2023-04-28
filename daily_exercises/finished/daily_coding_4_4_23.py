""" Daily problem: Given an undirected graph
    represented as an adjacency matrix and an integer k,
    write a function to determine whether each vertex in the graph
    be colored such that no two adjacent vertices share the same color
    with at most k colors."""



#STUCK, messed up an flattened matrix

def daily_problem(adj_matrix, k):
    """ Daily problem, brute force approach of all combinations iterated and verified."""
    colors = [-1 for _ in adj_matrix[0]]
    carry = 0
    next_color = 0
    while not valid(colors, adj_matrix):
        if colors == [k-1,k-1,k-1]:
            return False
        if next_color >= len(colors):
            next_color = 0
        colors[next_color] += 1  + carry
        if colors[next_color] >= k:
            carry += 1
            colors[next_color] %= k
            next_color += 1
        else:
            next_color = 0
            carry = 0
    return True

    
def valid(color_list, adj_matrix):
    if -1 in color_list:
        return False
    for node_index in range(len(color_list)):
        for neighbor_index in adj_matrix[node_index]:
            if adj_matrix[neighbor_index][node_index] and color_list[neighbor_index] == color_list[node_index]:
                return False
    return True

if __name__ == "__main__":
    test_matrix = [[False, True, True],[True,False,True],[True,True,False]]
    print(daily_problem(test_matrix,2))
    print(daily_problem(test_matrix,9))
