package D_Game_Engine;
import static org.lwjgl.opengl.GL33.*;

public class VertexArray {
	
	private int ID;
	
	public VertexArray(VertexBuffer mesh, VertexBuffer texCoors) {
		
		ID = glGenVertexArrays();
				
		glBindVertexArray(ID);
		
		mesh.bind();
		
		//only works with floats !!
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0,2,GL_FLOAT,false,2*Float.BYTES,0);
		
		texCoors.bind();
		
		glVertexAttribPointer(1,2,GL_FLOAT,false,2*Float.BYTES,0);
		glEnableVertexAttribArray(1);
		
	}
	
	public void bind(){
		glBindVertexArray(ID);
	}

}
