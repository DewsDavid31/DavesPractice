import java.util.io.*;

private class Scheduler{
}

private class LoadBalancer{
}

private class PrivateKey{
	public int rsa(string data){
		// pick two prime numbers p,q
		// compute n =p*q
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
