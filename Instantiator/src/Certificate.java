import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import org.bouncycastle.mail.smime.handlers.pkcs7_mime;

public class Certificate implements Serializable {
	private short id;
	private String name;
	private byte[] publicKey;
	
	

	public Certificate( short identifier,String n, byte[] pKey) {
		name =n;
		publicKey = pKey;
		id = identifier;
	}

	public byte[] serialize() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(this);
		return out.toByteArray();
	}

	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	@Override
	public String toString() {
		return "Certificate [id=" + id + ", name=" + name + ", publicKey=" + Arrays.toString(publicKey) + "]";
	}
	
	

}
