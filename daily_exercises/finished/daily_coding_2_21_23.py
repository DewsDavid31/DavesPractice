""" Daily problem: Given a stream too large to store, pick a random element from the stream with uniform probability"""
import random

class Stream:
    def __init__(self, array_form):
        self.array_form = array_form

    def next(self):
        """ Stream access of next element"""
        if self.has_next():
            return self.array_form.pop()
        else:
            return None

    def has_next(self):
        """ Stream function, stating if next element exists"""
        return self.size != 0

    def size(self):
        """ Possibly cheating, but stream should know how large it is"""
        return len(self.array_form)

def daily_problem(stream):
    """ Daily problem: technically a solution to grab the nth item which is a random selection of the stream
        This is because the stream is unknown, we cannot know what the data is, so uniform is assumed, since selection
        is uniform distribution using random, selecting the nth item in the stream"""
    selection = random.randint(0,stream.size() - 1)
    for _ in range(selection):
        stream.next()
    return stream.next()

if __name__ == "__main__":
    print(daily_problem(Stream(list(range(0,9999)))))