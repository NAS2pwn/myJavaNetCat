import java.lang.Thread;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class ThreadStreamPipe extends Thread {
	private static final int CR=13;
	private static final int LF=10;
	private DataInputStream input; //C'est mieux pour lire du raw bytes
	private DataOutputStream output;
	
	//Ca m'enerve je réussis po à rapatrier les null bytes genre :
	
	/*hexeditor /bin/ls
	00000000  7F 45 4C 46  02 01 01 00   00 00 00 00  00 00 00 00   .ELF............
	00000010  03 00 3E 00  01 00 00 00   50 58 00 00  00 00 00 00   ..>.....PX......
	00000020  40 00 00 00  00 00 00 00   A0 03 02 00  00 00 00 00   @...............

	hexeditor outPtnDePut
	00000000  7F 45 4C 46  02 01 01 03   3E 01 50 58  40 03 02 40                                        .ELF....>.PX@..@
	00000010  38 09 40 1C  1B 06 05 40   40 40 01 01  08 03 04 38                                        8.@....@@@.....8
	00000020  02 38 02 38  02 1C 1C 01   01 05 01 01  20 01 06 01                                        .8.8........ ...
	
	
	Trop bête, suffisait de bien écrire la condition :(
	
	Bon et pourquoi mtn
	
	/bin/ls
	ca marche chepo normal
	
	./outPtnDePut
	bash: ./outPtnDePut : impossible d'exécuter le fichier binaire : Erreur de format pour exec()
	
	Ah bah j'enlève la condition, faut pas confondre int et byte ptn >:( >:(
	
	Et
	Ca
	Marche
	
	Hehehe
	 */
	
	public ThreadStreamPipe(String name, InputStream input, OutputStream output){
		super(name);
		this.input=new DataInputStream(input);
		this.output=new DataOutputStream(output);
	}

	public void run(){
		try {
			boolean stop=false;
			byte byteRead;
			while(!stop){
				try{		
					byteRead=this.input.readByte();
					
					this.output.writeByte(byteRead);
					
					if(byteRead==CR||byteRead==LF)
						this.output.flush();
					
				} catch(EOFException e) {
					this.output.flush();
					stop=true;
				}
			}
		}
		catch(IOException e){
			//e.printStackTrace(System.err);
			System.err.println("Unexpected IO");
			System.exit(1);
		}
	}       
}