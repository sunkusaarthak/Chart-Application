package week3;

public class EncryptDecryptBob {
	String encrypt(String str) {
		StringBuilder stringBuilder = new StringBuilder(str);
		for(int i=0;i<str.length();i++) {
			int val = str.charAt(i);
			
			stringBuilder.setCharAt(i, (char) (val-2));
		}
		str = stringBuilder.toString();
		return(str);
	}
	String decrypt(String str) {
		StringBuilder stringBuilder = new StringBuilder(str);
		for(int i=0;i<str.length();i++) {
			int val = str.charAt(i);
			stringBuilder.setCharAt(i, (char) (val+3));
		}
		str = stringBuilder.toString();
		return str;
	}
}
