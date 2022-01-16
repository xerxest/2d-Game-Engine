package D_Game_Engine;
import static org.lwjgl.opengl.GL33.*;
public class VertexBuffer {
	
	private int ID;
	
	public VertexBuffer(float data[]){
		
		ID = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER,ID);
		
		// potential optimization with GL_STATIC_DRAW 
		
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		
	}
	
	
	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER,ID);
	}
	
	
	

}
