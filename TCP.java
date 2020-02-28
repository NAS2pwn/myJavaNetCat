import java.net.Socket;
import java.lang.Thread;
import java.io.IOException;

public class TCP{

	protected Socket socket;
	protected ThreadStreamPipe writer;
	protected ThreadStreamPipe reader;
	protected static final String PORT_ERROR = "Le port %d est occupé ou inaccessible";
	protected static final int EXIT_ERROR=1;

	public void closeConnection(int exit_code) throws IOException{
		if(this.writer!=null)
			this.writer.interrupt();
		if(this.reader!=null)
			this.reader.interrupt();
		this.socket.close();
		System.exit(exit_code);
	}

	public void go() throws IOException, InterruptedException{
		this.writer.start();
		this.reader.start();
		this.reader.join();
		this.writer.interrupt();
		this.socket.close();
		System.exit(0);
	}
}