""" Daily problem: Given an array of time intervals(start,end) for classroom
    lectures(possibly overlapped) find the minimum number of rooms required.
    For example: given [(30, 75), (0,50), (60,150)] you should return 2"""


def daily_problem(time_tuples):
    """ Checks if range is not in 2d array of rooms, adds range when not colliding,
    if colliding adds another array and places the range inside like reservations.
    Returns the number of rooms."""
    rooms = []
    for times in time_tuples:
        new_time = list(range(times[0],times[1]))
        found = False
        for room in rooms:
            occupied = False
            for compare_time in new_time:
                if compare_time in room:
                    occupied = True
                if not occupied:
                    found = True
                    room.extend(new_time)
        if not found:
            rooms.append(new_time)
    return len(rooms)

if __name__ == "__main__":
    print(daily_problem([(30,75),(0,50),(60,150)]))