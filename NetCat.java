import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class NetCat{

	public static void main(String[] args) throws InterruptedException, IOException, ParseException{
		Options options = new Options();
		options.addOption("l", "listen", true, "Ecouter sur le port PORT");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		List<String> arguments=cmd.getArgList();

		TCP tcp=null;

		try{
			if(cmd.hasOption("l") && arguments.size()==0){
				tcp=new ServeurTCP(Integer.parseInt(cmd.getOptionValue("l")));
			}
			else if(arguments.size()==2){
				tcp=new ClientTCP(arguments.get(0), Integer.parseInt(arguments.get(1)));
			}
			else{
				System.err.println("Syntaxe incorrecte : USAGE : java NetCat [-l PORT] [HOST PORT]");
				System.exit(1);
			}
			
		}
		catch(NumberFormatException e){
			System.err.println("Port au mauvais format");
			System.exit(1);
		}

		tcp.go();
	}
}