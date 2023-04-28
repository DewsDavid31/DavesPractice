""" Daily problem: You run an e-commerce website and want to record the last N
    order ids in a log. Implement a data structure to accomplish this, with the following API:
    record(order_id): adds the order_id to the log
    get_last(i): gets the ith last element from the log. i is guaranteed to be smaller or equal to N
    You should be as time and space efficient as possible.
"""
import random

class MyHashMap:
    """ Basic hashmap class with sequential indexing"""
    def __init__(self):
        self.seed = random.randint(1,9999)
        self.nodes = [None for x in range(self.seed)]
    
    def __my__hash__(self, key):
        """ fetches/adds element with O(1)"""
        return key % self.seed

    def __rehash__(self):
        """ Reindexes list if seed is too small, this is an O(n) operation that is rare"""
        self.seed *= 2
        new_nodes = [None for x in range(self.seed)]
        for item in self.nodes:
            new_nodes.insert(item, self.__hash__(item))
        self.nodes = new_nodes


    def record(self, order_id):
        """record(order_id): adds the order_id to the log
        O(1) worst case O(n) on rehash, space is size of list O(n)"""
        new_hash = self.__my__hash__(order_id)
        if self.nodes[order_id] is not None:
            self.__rehash__()
        self.nodes.insert(order_id, new_hash)

    def get_last(self, i):
        """ get_last(i): gets the ith last element from the log.
         i is guaranteed to be smaller or equal to N
        You should be as time and space efficient as possible.
        NOTE: you can't have both, space or time, that's how algs work!"""
        return self.nodes[i]



def daily_problem():
    """ Daily problem using hashmap"""
    my_map = MyHashMap()
    my_map.record(1)
    my_map.record(2)
    my_map.record(3)
    return(my_map.get_last(2))

if __name__ == "__main__":
    print(daily_problem())