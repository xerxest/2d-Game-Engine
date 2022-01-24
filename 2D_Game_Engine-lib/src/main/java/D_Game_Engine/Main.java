package D_Game_Engine;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

	// The window handle
	private long window;

	public void run() throws Exception {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	private void loop() throws Exception {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		

		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		
		Scene scene = new Scene();
		
		TestGameObj obj = new TestGameObj();
		
		scene.addGameObject(obj);
		
		// Testing Shader
		
		String vertexSrc = "#version 330 core\r\n"
				+ "layout (location = 0) in vec3 aPos; // the position variable has attribute position 0\r\n"
				+ "layout (location = 1) in vec2 atexCoors; \n"
				+ "  \r\n"
				+ "out vec4 vertexColor; // specify a color output to the fragment shader\r\n"
				+ "out vec2 texCoors;\n"
				+ "\r\n"
				+ "void main()\r\n"
				+ "{\r\n"
				+ "    gl_Position = vec4(aPos, 1.0); // see how we directly give a vec3 to vec4's constructor\r\n"
				+ "    vertexColor = vec4(0.5, 0.0, 0.0, 1.0); // set the output variable to a dark-red color\r\n"
				+ "		texCoors = atexCoors;\n"
				+ "}";
				
				
		String fragSrc = "#version 330 core\r\n"
				+ "out vec4 FragColor;\r\n"
				+ "  \r\n"
				+ "in vec4 vertexColor; // the input variable from the vertex shader (same name and same type)  \r\n"
				+ "in vec2 texCoors; \n"
				+ "uniform sampler2D myTexture; \n"
				+ "\r\n"
				+ "void main()\r\n"
				+ "{\r\n"
				+ "    FragColor =  texture(myTexture, texCoors);\n"
				+ "} ";
		
		float data[] = {
			-0.5f,-0.5f,
			 0.5f,-0.5f,
			 0.5f,0.5f,
			};
		
		float texCoords[] = {
			    0.0f, 0.0f,  // lower-left corner  
			    1.0f, 0.0f,  // lower-right corner
			    0.5f, 1.0f   // top-center corner
			};
		
		
		
		VertexBuffer vb = new VertexBuffer(data);
		
		VertexBuffer tc = new VertexBuffer(texCoords);
		
		VertexArray vao = new VertexArray(vb,tc);
		
		Texture tex = new Texture("test.png");
		
		vao.bind();
		
		tex.bind();
		
		Shader shader = new Shader(vertexSrc,fragSrc);
		
		shader.bind();
		
		while ( !glfwWindowShouldClose(window) ) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			vao.bind();
			glDrawArrays(GL_TRIANGLES,0,3);

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents(); 
		}
	}

	public static void main(String[] args) throws Exception {
		
		
		new Main().run();
	}

}