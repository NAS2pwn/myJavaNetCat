import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;

public class ServeurTCP extends TCP{

	public ServeurTCP(int port){
		try{
			ServerSocket serverSocket=new ServerSocket(port);
			this.socket=serverSocket.accept();
			this.writer = new ThreadStreamPipe("serverWrite", System.in, this.socket.getOutputStream());
			this.reader = new ThreadStreamPipe("serverRead", this.socket.getInputStream(), System.out);
		}catch (IOException e){
			System.err.println(String.format(TCP.PORT_ERROR, port));
			System.exit(TCP.EXIT_ERROR);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
