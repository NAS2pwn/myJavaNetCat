import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;

public class ClientTCP extends TCP{

	public ClientTCP(String host, int port){
		try{
			this.socket = new Socket(host, port);
			this.writer = new ThreadStreamPipe("clientWrite", System.in, this.socket.getOutputStream());
			this.reader = new ThreadStreamPipe("clientRead", this.socket.getInputStream(), System.out);
		}catch (IOException e){
			System.err.println(String.format(TCP.PORT_ERROR, port));
			System.exit(TCP.EXIT_ERROR);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}