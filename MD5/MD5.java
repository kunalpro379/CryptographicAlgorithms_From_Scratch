import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MD5 {

    // Initial MD5 constants
    private static final int[] INIT_STATE = {
        0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476
    };

    // Constants for MD5 rounds
    private static final int[] SHIFTS = {
        7, 12, 17, 22, 5, 9, 14, 20, 4, 11, 16, 23, 6, 10, 15, 21
    };

    // MD5 logical functions
    private static int F(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private static int G(int x, int y, int z) {
        return (x & z) | (y & ~z);
    }

    private static int H(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private static int I(int x, int y, int z) {
        return y ^ (x | ~z);
    }

    // Rotate left operation
    private static int rotateLeft(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    // MD5 hash function
    public static String hash(String message) {
        // Initialize state
        int[] state = INIT_STATE.clone();

        // Pad the message
        byte[] paddedMessage = padMessage(message);

        // Process message in 512-bit blocks
        for (int i = 0; i < paddedMessage.length; i += 64) {
            processBlock(paddedMessage, i, state);
        }

        // Convert state to byte array
        byte[] hashBytes = new byte[16];
        ByteBuffer buffer = ByteBuffer.wrap(hashBytes).order(ByteOrder.LITTLE_ENDIAN);
        for (int s : state) {
            buffer.putInt(s);
        }

        // Convert to hexadecimal string
        StringBuilder hexHash = new StringBuilder();
        for (byte b : hashBytes) {
            hexHash.append(String.format("%02x", b));
        }
        return hexHash.toString();
    }

    // Pad the message to a multiple of 512 bits
    private static byte[] padMessage(String message) {
        byte[] bytes = message.getBytes();
        int bitLength = bytes.length * 8;
        int paddingLength = (448 - (bitLength + 1) % 512 + 512) % 512;
        int totalLength = bitLength + 1 + paddingLength + 64;

        ByteBuffer buffer = ByteBuffer.allocate(totalLength / 8)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put(bytes)
                .put((byte) 0x80); // Append 1 bit

        // Append padding zeros
        while (buffer.position() < (totalLength - 64) / 8) {
            buffer.put((byte) 0);
        }

        // Append original length in bits
        buffer.putLong(bitLength);
        return buffer.array();
    }

    // Process a 512-bit block
    private static void processBlock(byte[] block, int offset, int[] state) {
        int[] X = new int[16];
        ByteBuffer buffer = ByteBuffer.wrap(block, offset, 64).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < 16; i++) {
            X[i] = buffer.getInt();
        }

        int A = state[0], B = state[1], C = state[2], D = state[3];

        // Perform 64 rounds
        for (int i = 0; i < 64; i++) {
            int F, g;
            if (i < 16) {
                F = F(B, C, D);
                g = i;
            } else if (i < 32) {
                F = G(B, C, D);
                g = (5 * i + 1) % 16;
            } else if (i < 48) {
                F = H(B, C, D);
                g = (3 * i + 5) % 16;
            } else {
                F = I(B, C, D);
                g = (7 * i) % 16;
            }

            int temp = D;
            D = C;
            C = B;
            B = B + rotateLeft(A + F + X[g] + getConstant(i), SHIFTS[i % 16]);
            A = temp;
        }

        // Update state
        state[0] += A;
        state[1] += B;
        state[2] += C;
        state[3] += D;
    }

    // Get MD5 constant for round i
    private static int getConstant(int i) {
        return (int) (Math.abs(Math.sin(i + 1)) * 0x100000000L);
    }

    public static void main(String[] args) {
        String message = "Hello, MD5!";
        String hash = hash(message);
        System.out.println("Message: " + message);
        System.out.println("MD5 Hash: " + hash);
    }
}