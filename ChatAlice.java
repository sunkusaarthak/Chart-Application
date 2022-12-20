import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatAlice {
	public static void main(String[] args) {

		Thread clienThread = new Thread(() ->{
			Socket socket = null;
			DataInputStream dataInputStream = null;
			DataOutputStream dataOutputStream = null;
			EncryptDecryptAlice encryptDecrypt = new EncryptDecryptAlice();
			try {
				socket = new Socket("10.66.50.244",5000);
				System.out.println("Connected");
				dataInputStream = new DataInputStream(System.in);
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				
			} catch (UnknownHostException e) {
				System.out.println(e);
			}
			catch (IOException e) {
				System.out.println(e);
			}
			String lineString = "";
			while (!lineString.equals("over")) {
				try {
					lineString = dataInputStream.readLine();
					lineString = encryptDecrypt.encrypt(lineString);
					dataOutputStream.writeUTF(lineString);
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			try {
				dataInputStream.close();
				dataOutputStream.close();
				socket.close();
				System.out.println("Connection Closed");
			} catch (IOException e) {
				System.out.println(e);
			}
		}); 
		Thread serverThread = new Thread(()->{
			try {
				EncryptDecryptAlice encryptDecrypt = new EncryptDecryptAlice();
				Socket socket = null;
				try (ServerSocket serverSocket = new ServerSocket(9123)) {
					DataInputStream dataInputStream = null;
					System.out.println("Server Started");
					System.out.println("Waiting for the client....");
					socket = serverSocket.accept();
					System.out.println("Client Accepted");
					
					dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					
					String lineString= "";   
					
					while (!lineString.equals("over")) {
						try {
							lineString = dataInputStream.readUTF();
							lineString = encryptDecrypt.decrypt(lineString);
							System.out.println(lineString);
						} catch (IOException e) {
							System.out.println(e);
						}
					}
					socket.close();
					dataInputStream.close();
				}
				System.out.println("Connection Closed");
			} catch (IOException e) {
				System.out.println(e);
			}
		});
		serverThread.start();
		clienThread.start();
	}
}
