""" daily problem: make a job scheduler that takes function f and runs it after n milliseconds"""
import time
import threading

class Scheduler:
    """ Class that handles job scheduling"""

    def encapsulated_function(self,f,n):
        """ places a sleep before running function"""
        time.sleep(n / 1000.0)
        f(n)

    def schedule(self,f,n):
        """ Function that creates threads"""
        job_thread = (threading.Thread(target=self.encapsulated_function, args=(f,n)))
        job_thread.start()

def test_func(n):
    """ our test job """
    print(f"I should print this after {n} millisecs")

if __name__ == "__main__":
    test_scheduler = Scheduler()
    test_scheduler.schedule(test_func, 1000)
    test_scheduler.schedule(test_func, 2000)
    test_scheduler.schedule(test_func, 1500)