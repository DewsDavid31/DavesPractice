import java.util.io.*;
import java.math.Random;



private class Scheduler{
}

private class LoadBalancer{
}

private class PrivateKey{
	PrivateKey(){
		this.randGenerator = new Random.Random();
	}
	public Random randGenerator;
	
	public Boolean isPrime(int candidate){
		for(int index = 2; index < candidate; index++){
			if(candidate % index == 0){
				return false;
			}
		}
		return true;
	}

	public int pickRandPrime(){
		
		do{
			int candidate = this.randGenerator.randInt();
		} while(!isPrime(candidate));
		return candidate;
	}

	public int rsa(string data){
		// pick two prime numbers p,q
		int p = pickRandPrime();
		int q = pickRandPrime();
		// compute n =p*q
		int n  = p * q;
		// find totient phi(n) = (p-1)(q-1)
		
                // choose a d that is prime in Z such that 1<e<phi(n), e is coprime to phi(n), gcd(e,phi(n)) = 1
		// and find e^n such that e*dmod(p-1)(q-1) = 1
		// decrypt D= c^d(modn), encrypt C = D^e(modn)
	}
	
}

private class PublicKey{
}

private class EncrypterDecrypter{
}

private class User{
}

public static void main(String [] args){
  System.out.println("not implemented yet, need research");
}
