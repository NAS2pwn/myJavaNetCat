import java.lang.Thread;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;

public class ThreadStreamPipe extends Thread {
	private static int CR=13;
	private static int LF=10;
	private InputStream input;
	private PrintStream printer;

	public ThreadStreamPipe(String name, InputStream input, OutputStream output){
		super(name);
		this.input=input;
		this.printer=new PrintStream(output, true);
	}

	public void run(){
		try{
			int byteRead;
			boolean stop=false;

			while(!stop){
				byteRead=this.input.read();

				if(byteRead>0)
					printer.write(byteRead);

				if(byteRead==CR||byteRead==LF||byteRead==-1)
					printer.flush();

				//System.err.println(byteRead);

				if(byteRead==-1)
					stop=true;
			}
			//System.out.println(this.getName());
		} catch(IOException e){
			System.err.println("Unexpected");
			//System.exit(1);
		}
	}       
}