package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

public class MainLCP {

	static List<WinkelData> winkelDataMap = new ArrayList<>();
	private static KeyStore keyStoreLCP;
	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		
		keyStoreLCP = KeyStore.getInstance("JKS");
		
		String fileName = "keyStoresAndCertificates/LCPKeyStore.jks";
		
		FileInputStream fis = new FileInputStream(fileName);
		
		keyStoreLCP.load(fis, "kiwikiwi".toCharArray());
		fis.close();
		winkelDataMap = makeWinkelData(keyStoreLCP);
		
		try {
			// create on port 1099
			Registry registry = LocateRegistry.createRegistry(3999);
			
			registry.rebind("dispatchermethodes", new LCPMethods());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("LCP is ready on port 3999");
		
	}
	private static List<WinkelData> makeWinkelData(KeyStore keyStoreLCP2) throws KeyStoreException, NoSuchAlgorithmException {
		
		List<WinkelData> wdMap = new ArrayList<>();

		Certificate cert = (Certificate) keyStoreLCP.getCertificate("DelhaizeKeyPair");
		WinkelData wd = new WinkelData(cert, "Delhaize");
		wdMap.add(wd);
		
		cert = (Certificate) keyStoreLCP.getCertificate("AlienwareKeyPair");
		wd = new WinkelData(cert, "AlienWare");
		wdMap.add( wd);
		
		cert = (Certificate) keyStoreLCP.getCertificate("RazorKeyPair");
		wd = new WinkelData(cert, "Razor");
		wdMap.add(wd);
		
		cert = (Certificate) keyStoreLCP.getCertificate("ColruytKeyPair");
		wd = new WinkelData(cert, "Colruyt");
		wdMap.add(wd);
		return wdMap;
	}
}
		

