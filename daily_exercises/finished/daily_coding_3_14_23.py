""" Daily problem: Given an array of strictly the characters:
'R' 'G' and 'B'. Segregate the values of the array so that
all Rs come first, then Gs then Bs. You are only allowed swaps.

Do this in linear time and in-place.

For example: given [G,B,R,R,B,R,G] it should become [R,R,R,G,G,B,B]"""


def daily_problem(letter_array):
    """ Daily problem: unrolled sets of for loops that get the wanted locations for each item,
      then swaps each one after in own loops, making it hopefully O(6n) => O(n) """
    global next_swap
    next_swap = 0
    r_locations = []
    b_locations = []
    g_locations = []
    for char_index in range(len(letter_array)):
        if letter_array[char_index] == 'R':
            r_locations.append(char_index)
    for swap_location in r_locations:
        swap(letter_array,swap_location,next_swap)
        next_swap += 1
    for char_index in range(len(letter_array)):
            if letter_array[char_index] == 'G':
                g_locations.append(char_index)
    for swap_location in g_locations:
        swap(letter_array,swap_location,next_swap)
        next_swap += 1
    for char_index in range(len(letter_array)):
            if letter_array[char_index] == 'B':
                b_locations.append(char_index)
    for swap_location in g_locations:
        swap(letter_array,swap_location,next_swap)
        next_swap += 1
    return letter_array
  


def swap(array_a, index_a,index_b):
    print(index_a)
    temp = array_a[index_a]
    array_a[index_a] = array_a[index_b]
    array_a[index_b] = temp
    print(array_a)



if __name__ == "__main__":
    print(daily_problem(['G','B','R','R','B','R','G']))