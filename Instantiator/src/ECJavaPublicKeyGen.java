import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ECJavaPublicKeyGen {
	public static void main(String[] args) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		
		KeyPair kp = ECJavaPublicKeyGen.generateECCKeyPair();
		ECJavaPublicKeyGen.printSecret((ECPrivateKey) kp.getPrivate());
		ECJavaPublicKeyGen.printSecret((ECPublicKey) kp.getPublic());
	}
	
	public static KeyPair generateECCKeyPair() throws NoSuchProviderException{
		try{
			ECGenParameterSpec ecParamSpec = new ECGenParameterSpec("prime192v1");
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDSA", "BC");
			kpg.initialize(ecParamSpec);
			return kpg.generateKeyPair();
		} catch(NoSuchAlgorithmException e){
			throw new IllegalStateException(e.getLocalizedMessage());
		} catch(InvalidAlgorithmParameterException e){
			throw new IllegalStateException(e.getLocalizedMessage());
		}
	}
	
	public static void printSecret(ECPrivateKey key){
		System.out.println("S: "+ new BigInteger(1, key.getD().toByteArray()).toString(16));
	}
	
	public static void printSecret(ECPublicKey key){
		System.out.println("W: "+ new BigInteger(1, key.getQ().getEncoded()).toString(16));
	}
}
