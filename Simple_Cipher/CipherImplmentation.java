import java.util.Scanner; 
public class CipherImplmentation{
    private static final int ASCII_START=32;
    private static final int ASCII_END=126;
    private static  final int ASCII_RANGE=ASCII_END-ASCII_START+1;

public static String encryptAdditive(String text, int key){
    StringBuilder result=new StringBuilder();
    for(char ch: text.toCharArray()){
        if(ch>=ASCII_START&&ch<=ASCII_END){
             int shifted = ch - ASCII_START + key;
             shifted = ((shifted % ASCII_RANGE) + ASCII_RANGE) % ASCII_RANGE;
             char encryptedChar = (char)(ASCII_START + shifted);
             result.append(encryptedChar);            
        }else result.append(ch);
    }
    return result.toString();
}

public static String decryptAdditive(String text, int key){
    return encryptAdditive(text, -key);
}

public static void main(String[] args){
    Scanner scanner=new Scanner(System.in);
    System.out.println("Enter the text to encrypt: ");
    String text=scanner.nextLine();
    System.out.println("Enter the key: ");
    int key=scanner.nextInt();
    String encryptedText=encryptAdditive(text, key);
    System.out.println("Encrypted text: "+encryptedText);
    System.out.println("Decrypted text: "+decryptAdditive(encryptedText, key));
    scanner.close();
    
}
}