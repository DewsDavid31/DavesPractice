""" Daily problem: implement a URL shortener with the following methods:
    shorten(url) which shortens the url into a six character alphanumeric
    string, such as zLg6w1
    
    restore(url) which expands 6 digit alphanumeric into original url,
    if no such exists, return null"""
import random
import string

class UrlShortener:
    """ Class for url shortening with paired lists
        of ids and urls"""
    def __init__(self):
        self.urls = []
        self.shorts = []
        self.alphanum = list(string.ascii_lowercase +  string.ascii_uppercase +  string.digits)
    
    def generate(self):
        """ Creates a random 6 digit alphanumeric"""
        gen_string = ""
        for _ in range(6):
            gen_string += self.alphanum[random.randint(0,len(self.alphanum) - 1)]
        return gen_string
    
    def shorten(self,url):
        """ generates 6 digit alphanumeric id, mapped to
            original url using paired lists"""
        if url not in self.urls:
            self.urls.append(url)
            self.shorts.append(self.generate())
        return self.shorts[self.urls.index(url)]

    def restore(self,url):
        """ restores url from paired lists"""
        if url in self.shorts:
            return self.urls[self.shorts.index(url)]
        return None
    


def daily_problem():
    """ Daily problem: using assertions on all different
        situations: multiple shortens of the same, different shortens,
        restore"""
    test_shortener = UrlShortener()
    test = test_shortener.shorten("www.google.com")
    test2 = test_shortener.shorten("www.google.com")
    test_shortener.shorten("www.ebaumsworld.com")
    result = test_shortener.restore(test)
    assert test == test2
    assert result == "www.google.com"

if __name__ == "__main__":
    daily_problem()