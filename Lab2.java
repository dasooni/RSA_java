
package lab2;


import java.math.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;

public class Lab2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Variables to be used
        BigInteger p,q,n,e,d,rho,test,m,c,receivedMessage;
        BigInteger one = new BigInteger("1");
        
        //The bitlength of the primenumbers used as keys
        int bitLength = 512;
        
        //Generates a "random" number, and uses this to pick a random prime number for the first key p
        Random rnd1 = new Random();
        p = BigInteger.probablePrime(bitLength, rnd1);
        
        do {
            //Generates the second key q in the same way as p
            Random rnd2 = new Random();
            q = BigInteger.probablePrime(bitLength, rnd2);
        } while (q.equals(p)); //Re-randomizes q if it happens to be equal to p
        
        
        //Testcode to print out the two keys p and q
        /*String str = "With bitLength " + bitLength + ": " + 
                "\n" + " p = " + p + 
                "\n" + " q = " + q;
        System.out.println(str); */
        
        //Calculates n by multiplying q with p
        n = q.multiply(p);
        //System.out.println(" n = " + n);
        
        //Calculates rho
        rho = (p.subtract(one).multiply(q.subtract(one)));
        //System.out.println(" rho = " + rho);
        
        
        do {
            //Generates e similarly to how p and q are generated
            Random rnd3 = new Random();
            e = BigInteger.probablePrime(bitLength, rnd3);
        } while (!e.gcd(rho).equals(one)); //Re-randomizes e if gcd(e,rho) != 1
        //System.out.println(" e = " + e);
        
        //Calculates the value of d by utilizing the modInverse function
        d = e.modInverse(rho);
        //System.out.println(" d = " + d);
        
        //Testcode to make sure that d*e mod(rho) = 1
        /*test = d.multiply(e).mod(rho);
        System.out.println(" test = " + test);*/
        
        //Asks user to enter text to be encrypted
        System.out.print("Enter text to be encrypted: ");
        //Reads the user's input and converts it to BigInteger
        String inputText = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        m = new BigInteger(inputText.getBytes());
        //Prints the unencrypted value of the message
        System.out.println("Unencrypted value of message = " + m);
        
        //Calculates and prints the encrypted value of the message
        c = m.modPow(e, n); 
        System.out.println("Encrypted value of the message = " + c);
        
        //Decrypts the message and prints this as both BigInteger and String
        receivedMessage = c.modPow(d, n);
        System.out.println("Decrypted value of the message = " + receivedMessage);
        System.out.println("Decrypted text of the message = " + new String(receivedMessage.toByteArray()));
    }
    
}
