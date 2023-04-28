""" Daily problem: Implement an LRU cache.
    It should be able to be intitalized with a
    cache size of n and contain the following methods:
    
    set(key, value): sets key to value, if already at max of n,
     replaces least used
      
    get(key) returns value of key, if no such key exists, return null
    
    each operation should run in O(1) time"""


class LRUCache:
    """ LRUCache using dictionary map for O(k) access and insertion
        , uses a side-array with a top pointer to look at next eldest item
        in a loop for O(k) access and insertion by using a window instead of search"""
    def __init__(self, n):
        self.dict_form = {}
        self.size = n
        self.top = 0
        self.eldest = 0
        self.side_queue = []

    def set(self, key, value):
        """ pushes onto a stack, unless full then replaces bottom element(oldest)"""
        if self.top >= self.size:
            old_key = self.side_queue[self.eldest]
            self.dict_form[old_key] = None
            self.dict_form[key] = value
        else:
            self.side_queue.append(key)
            self.dict_form[key] = value
            self.top += 1

    def get(self, key):
        """ simple dictionary which is a map with O(1)"""
        query = self.dict_form[key]
        if self.side_queue[self.eldest] == key:
            self.eldest += 1
            self.eldest = self.eldest % self.size
        return query

def daily_problem(n):
    """ Daily problem, verifying functions using simple insertion of a range"""
    test_lru = LRUCache(n)
    test_lru.set(0,1)
    test_lru.set(1,2)
    test_lru.set(2,3)
    test_lru.set(3,4)
    print("should return 2,3,4 dropping 1")
    print(test_lru.get(0))
    print(test_lru.get(1))
    print(test_lru.get(2))
    print(test_lru.get(3))


if __name__ == "__main__":
    daily_problem(3)