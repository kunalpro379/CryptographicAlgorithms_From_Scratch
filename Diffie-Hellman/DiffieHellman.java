import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {

    // Prime number (p) and base (g) - these are public and agreed upon by both parties
    private static final BigInteger p = new BigInteger("23"); // Example prime number
    private static final BigInteger g = new BigInteger("5");  // Example base

    public static void main(String[] args) {
        // Alice and Bob generate their private keys
        BigInteger alicePrivateKey = generatePrivateKey();
        BigInteger bobPrivateKey = generatePrivateKey();

        // Alice and Bob compute their public keys
        BigInteger alicePublicKey = computePublicKey(alicePrivateKey);
        BigInteger bobPublicKey = computePublicKey(bobPrivateKey);

        // Alice and Bob exchange public keys and compute the shared secret
        BigInteger aliceSharedSecret = computeSharedSecret(alicePrivateKey, bobPublicKey);
        BigInteger bobSharedSecret = computeSharedSecret(bobPrivateKey, alicePublicKey);

        // Both shared secrets should be the same
        System.out.println("Alice's shared secret: " + aliceSharedSecret);
        System.out.println("Bob's shared secret: " + bobSharedSecret);
    }

    // Generate a private key (a random number less than p)
    private static BigInteger generatePrivateKey() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(p.bitLength() - 1, random);
    }

    // Compute the public key: g^privateKey mod p
    private static BigInteger computePublicKey(BigInteger privateKey) {
        return g.modPow(privateKey, p);
    }

    // Compute the shared secret: publicKey^privateKey mod p
    private static BigInteger computeSharedSecret(BigInteger privateKey, BigInteger publicKey) {
        return publicKey.modPow(privateKey, p);
    }
}