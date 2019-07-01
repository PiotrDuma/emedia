package emedia;


import java.math.BigInteger;
import java.util.Random;


public class RSA {

	private BigInteger p;
	private BigInteger q;
	private BigInteger N;
	private BigInteger phi;
	private BigInteger e;
	private BigInteger d;
	private int bitlength = 1024;
	private Random r;
	private int[] encrypted;
	private int[] decrypted;

	
	public RSA() { 
		// generating keys
		r = new Random();
//		p = BigInteger.valueOf(7907);//7907 //get const values for quick test. 
//		q = BigInteger.valueOf(10037);//10037
		p = new BigInteger(bitlength/2,10,r);
		q = new BigInteger(bitlength/2,10,r);
		N = p.multiply(q);
		
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = BigInteger.valueOf(3);
		while (devider(e, phi).compareTo(BigInteger.ONE) != 0) { // devider != 1
			e.add(BigInteger.valueOf(2));
		}
		d = e.modInverse(phi);
	}

	public RSA(BigInteger e, BigInteger d, BigInteger N) {
		this.e = e;
		this.d = d;
		this.N = N;
	}

	public void test() {
		int val = 10;
		System.out.println("val= " + val);
		int buf = encryptVal(val);
		System.out.println("encrypted: " + buf);
		int x = decryptVal(buf);
		System.out.println("decrypted " + x);
	}


	public void run(int[] dataSet) {
		System.out.println("String encrypt");

		encrypted = encrypt(dataSet);
		System.out.println("Decrypting");

		decrypted = decrypt(encrypted);
		System.out.println("Encrypting done");

//        if( equalsData(dataSet,decrypted)){
//        	System.out.println("Decrypting bytes equals data sample");
//        }
//        else {
//        	System.out.println("Decrypting bytes NOT equals data sample");
//        }
	}
	
	//test function checking equality between arrays
	private boolean equalsData(int[] x1, int[] x2) {
		boolean result;
		if(x1.length != x2.length) {
			result = false;
			System.out.println("WRONG LENGTH");
		}else {
			if (x1.equals(x2)) {
				result = true;
			}else {
				System.out.println("Not Equals");
				result = false;
			}
		}
		return result;
	}
	private BigInteger devider(BigInteger first, BigInteger second) {
		while (second.compareTo(BigInteger.ZERO) != 0) {
			BigInteger temp = second;
			second = first.mod(second);
			first = temp;
		}
		return first;
	}

	private int encryptVal(int val) {
		BigInteger power = BigInteger.valueOf(val);
		BigInteger result = power.modPow(e, N);
		return result.intValue();
	}

	// Encrypt message, public key
	private int[] encrypt(int[] message) {
		int[] temp = new int[message.length];
		
		for (int i = 0; i < message.length; i++) {
			temp[i] = encryptVal(message[i]);
		}
		return temp;
	}

	private int decryptVal(int val) {
		BigInteger power = BigInteger.valueOf(val);
		BigInteger result = power.modPow(d, N);
		return result.intValue();
	}

	// Decrypt message, private key
	private int[] decrypt(int[] message) {
		int[] temp = new int[message.length];
		
		for (int i = 0; i < message.length; i++) {
			temp[i] = decryptVal(message[i]);
		}
		return temp;
	}

	public int[] getEncrypted() {
		return this.encrypted;
	}

	public int[] getDecrypted() {
		return this.decrypted;
	}

}
