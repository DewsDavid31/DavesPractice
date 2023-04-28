""" Daily problem: A sorted array was rotated an unknown number of times
    given such an array, find the index of an element in faster than O(n) time.
    If the element in the array is not found, return null
     
    ex: [13,18,25,2,8,10] finding 8 returns 4 """


def daily_problem(rotated, element):
    """ Daily problem, rotates back to sorted list and
        applies a binary search for:
        O(n/2) + O(ln) which is less than O(n)"""
    rotations = 0
    while rotated[0] > rotated[-1]:
        rotated = rotate(rotated)
        rotations += 1
    print(rotated)
    return binary_search(rotated, element) + rotations

def rotate(rotated):
    temp = []
    temp.append(rotated.pop())
    temp.extend(rotated)
    return temp

def binary_search(area, element):
    if len(area) <= 0:
        return -1
    midpoint = round(len(area)/ 2)
    if area[midpoint] == element:
        return midpoint
    if midpoint > element:
        return binary_search(area[midpoint:], element)
    else:
        return binary_search(area[:midpoint], element)

if __name__ == "__main__":
    print(daily_problem([13,18,25,2,8,10],8))