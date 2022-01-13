package D_Game_Engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Shader {
	
	private int ID;
	
	public Shader(String fileLocation) {
		
		
		
	}
	
	private String openShaderSourceFile(String fileLocation) throws FileNotFoundException {
		
		String src = "";
		
		File file = new File(fileLocation);
		
		try {
			
			Scanner fileReader = new Scanner(file);
			
			while(fileReader.hasNext()) {
				
				src += fileReader.nextLine();
				
			}
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
			throw new FileNotFoundException();
			
		}
		
		return src;
		
	}
	

}
