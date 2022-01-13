package D_Game_Engine;
import static org.lwjgl.opengl.GL33.*;
public class VertexBuffer {
	
	private int ID;
	
	private float[] data;
	
	public VertexBuffer(float data[]){
		
		ID = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER,ID);
		
		this.data = data;
		
		// potential optimization with GL_STATIC_DRAW 
		
		glBufferData(GL_ARRAY_BUFFER,data,GL_STATIC_DRAW);
		
	}
	
	
	
	

}
