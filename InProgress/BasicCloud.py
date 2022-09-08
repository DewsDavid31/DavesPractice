import random as random

class bad_rsa_key:
  
  def gcd(self,a,b):
    if a == 0:
      return b
    elif b == 0:
      return a
    else:
      return self.gcd(b, a%b)
  #recursion fails here
  
  def find_d_e(self,x,n,p,q):
     # choose a d that is prime in Z 
     found = False
     while(not found):
       d = self.pick_rand_prime(x)
       e = self.pick_rand_prime(x)
       if self.gcd(e,x) == 1 and (e*d % ((p-1)*(q-1))) == 1:
         found = True
        # 1<e<phi(n)
        # e is coprime to phi(n), gcd(e,phi(n)) = 1
        # and find e^n such that e*dmod(p-1)(q-1) = 1
     return (d,e)
   
  def pick_rand_prime(self, cap):
    prime = False
    while(not prime):
      candidate = random.randint(3,cap);
      prime = True
      for factors in range(2,candidate):
        if candidate % factors == 0:
          prime = False
    return candidate

  def linear_totient(self,p,q):
    # find totient phi(n) = (p-1)(q-1)
    n = p * q
    factor_1 = p - 1
    factor_2 = q - 1
    second_n = factor_1*factor_2
    new_slope = abs(second_n - n)
    return new_slope

  # decrypt D= c^d(modn), encrypt C = D^e(modn)
  def __init__(self):
      p = self.pick_rand_prime(100)
      q = self.pick_rand_prime(100)
      n  = p*q
      x = self.linear_totient(p,q)
      self.d,self.e = self.find_d_e(x,n,p,q)

  def encrypt_char(self, datum, n):
    return ord(datum) ** self.e % n

  def decrypt_char(self, datum , n):
     return chr(datum ** self.d % n)
    
if __name__ == "__main__":
  test_key = bad_rsa_key()
  test_string = "Hello world!"
  print("Encrypting " + test_string + "...")
  output_str = ""
  for charac in test_string:
    output_str += str(test_key.encrypt_char(charac,10)) + " "
  print("result: " + output_str)
  print("descrypting " + output_str + "...")
  reverted = ""
  for integ in test_string:
    numeric = int(integ)
    reverted += str(test_key.decrypt_char(numeric,10))
  print("result: " + reverted)
    
  
    
