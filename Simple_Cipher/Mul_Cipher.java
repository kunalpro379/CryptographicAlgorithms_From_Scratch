import java.util.Scanner;

public class Mul_Cipher {

   private static final int ASCII_START=32;
    private static final int ASCII_END=126;
    private static  final int ASCII_RANGE=ASCII_END-ASCII_START+1;
    public static String EncryptMultiplicative(String text, int key){
        StringBuilder result=new StringBuilder();
        for(char ch:text.toCharArray()){
            if(ch>=ASCII_START&&ch<=ASCII_END){
                char encryptedChar=(char)((ch-ASCII_START)*key%ASCII_RANGE+ASCII_START);
                result.append(encryptedChar);
            }else result.append(ch);
        }return result.toString();
    }
    public static String DecryptedMultiplicative(String text,int key){
        int inverseKey=modInverse(key, ASCII_RANGE);
        if(inverseKey==-1) return "Invalid key";    
        return EncryptMultiplicative(text, inverseKey);
    }
    private static int modInverse(int key, int mod){
        for(int i=1;i<mod;i++){
            if((key*i)%mod==1) return i;

        }return -1;
    }
    public static void main(String[] args){

        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the text to encrypt: ");
        String plainTextMul = scanner.nextLine();
        System.out.println("Enter the key: ");
        int multiplicativeKey = scanner.nextInt();
        String multiplicativeEncrypted = EncryptMultiplicative(plainTextMul, multiplicativeKey);
        String multiplicativeDecrypted = DecryptedMultiplicative(multiplicativeEncrypted, multiplicativeKey);

        System.out.println("Encrypted text: "+multiplicativeEncrypted );
        System.out.println("Decrypted text: "+multiplicativeDecrypted);
        scanner.close();
        
    }
}
// Find the modular inverse of 3 mod 7
// We need to find an x such that:
// (3 × x) mod 7 = 1
// Checking manually:
// 3 × 1 mod 7 = 3
// 3 × 2 mod 7 = 6
// 3 × 3 mod 7 = 2
// 3 × 4 mod 7 = 5
// 3 × 5 mod 7 = 1 ✅
// So, the modular inverse of 3 mod 7 is 5.
