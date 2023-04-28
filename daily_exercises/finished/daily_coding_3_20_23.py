""" Daily problem: Given an unorganized list of flights taken
    by someone, each as origin, destination pairs with a starting point.
    Compute the persons itenerary. If no such itenerary exists, return null.
    If there are multiple possible iteneraries, return the lexically smaller one.
    
    For example:
    given [(SFO,HKO),(YYZ,SFO),(YUL,YYZ),(HKO,ORD)] and YUL you should give
    [YUL,YYZ,SFO,HKO,ORD]
    given [(SFO,COM),(COM,YYZ)] and COM you should return null"""


def daily_problem(flight_list, starting_point):
    """ Daily problem: sorting list on each iteration for least lexigraphical
        path, then appending matching starting points until done. If any are
        left over, it returns null. glacial O(n^3), space is O(n)"""
    itenerary = []
    done = False
    while not done:
        if len(flight_list) > 1:
            flight_list.sort(key=lambda a: a[1])
        done = True
        itenerary.append(starting_point)
        for flight_tuple in flight_list:
            if flight_tuple[0] == starting_point:
                done = False
                starting_point = flight_tuple[1]
                flight_list.remove(flight_tuple)
    if len(flight_list) > 0:
        return None
    else:
        return itenerary

if __name__ == "__main__":
    print(daily_problem([("SFO","HKO"),("YYZ","SFO"),("YUL","YYZ"),("HKO","ORD")],"YUL"))
    print(daily_problem([("SFO","COM"),("COM","YYZ")], "COM"))