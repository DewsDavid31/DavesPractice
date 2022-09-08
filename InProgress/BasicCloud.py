import random as random

class bad_rsa_key:
  def gcd_rec(a,b):
    if a == 0:
      return b
    elif b == 0:
      return a
    else:
      return gcd_rec(b, a%b)
  #recursion fails here
  
  def find_d_e(x,n,p,q):
     # choose a d that is prime in Z 
     found = False
     while(not found):
       d = pick_rand_prime(x)
       e = pick_rand_prime(x)
       if gcd(e,x) == 1 and (e*d % (p-1)(q-1)) == 1:
         found = True
        # 1<e<phi(n)
        # e is coprime to phi(n), gcd(e,phi(n)) = 1
        # and find e^n such that e*dmod(p-1)(q-1) = 1
     return (d,e)
   
  def encrypt_char(self, datum, n):
     return ord(datum) ** self.e % n

  def decrypt_char(self, datum , n):
     return chr(datum ** self.d % n)
  def pick_rand_prime(cap):
    prime = False
    while(not prime):
      candidate = random.random(cap);
      prime = True
      for factors in range(2,candidate):
        if candidate % factors == 0:
          prime = False
    return candidate

  def linear_totient(p,q):
    # find totient phi(n) = (p-1)(q-1)
    n = p * q
    factor_1 = p - 1
    factor_2 = q - 1
    second_n = factor_1*factor_2
    new_slope = (second_n - n)
    return new_slope









  # decrypt D= c^d(modn), encrypt C = D^e(modn)
  def __init__(self,d,e):
      p = self.pick_rand_prime(1000)
      q = self.pick_rand_prime(1000)
      n  = p*q
      x = linear_totient(p,q)
      self.d,self.e = self.find_d_e(x,n,p,q)

    
