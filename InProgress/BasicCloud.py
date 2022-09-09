import random as random
import math as math

class bad_rsa_key:
  
  def gcd(self,a,b):
    if b == 0:
      return a
    else:
      return self.gcd(b, a%b)
  
  def find_e(self,p,q):
     # choose a d that is prime in Z 
     found = False
     print("Calculating public key")
     while(not found):
       e = self.pick_rand_prime(1000)
       if self.gcd(e,(p-1)*(q-1)) == 1:
         found = True
     return e

  def find_d(self,p,q):
    print("Calculating private key")
    d = (self.e ** -1) % ((p-1)*(q-1)) 
    return d
    
  def pick_rand_prime(self, cap):
    prime = False
    while(not prime):
      candidate = random.randint(3,cap);
      prime = True
      for factors in range(2,candidate):
        if candidate % factors == 0:
          prime = False
    return candidate
    
  def __init__(self):
      p = self.pick_rand_prime(1000)
      q = self.pick_rand_prime(1000)
      self.e = self.find_e(p,q)
      self.d = self.find_d(p,q)
      self.p = p
      self.q = q

  def encrypt_char(self, datum):
    return chr(math.ceil((ord(datum) ** (self.e) % (self.p * self.q))))

  def decrypt_char(self, datum):
    return chr(math.ceil(ord(datum) ** ((self.d)) % (self.p * self.q)))
    
if __name__ == "__main__":
  test_key = bad_rsa_key()
  test_string = "Hello world!"
  print("Encrypting " + test_string + "...")
  output_str = ""
  for charac in test_string:
    print(charac + test_key.encrypt_char(charac))
    output_str += test_key.encrypt_char(charac) + " "
  print("Encyrpted badly: " + output_str)
  output_chars = output_str.split(' ')
  reverted_str= ""
  for chars in output_chars:
    if chars == '':
      continue
    reverted_str += str(test_key.decrypt_char(chars))
  print("Decrypted badly: " + reverted_str)
    
