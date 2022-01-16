package D_Game_Engine;
import static org.lwjgl.opengl.GL33.*;

public class VertexArray {
	
	private int ID;
	
	public VertexArray(VertexBuffer vb) {
		
		ID = glGenVertexArrays();
				
		glBindVertexArray(ID);
		
		vb.bind();
		
		//only works with floats !!
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0,2,GL_FLOAT,false,2*4,0);
		
	}
	
	public void bind(){
		glBindVertexArray(ID);
	}

}
