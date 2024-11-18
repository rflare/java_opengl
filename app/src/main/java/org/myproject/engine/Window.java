package org.myproject.engine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;




public class Window{

    private static Window instance = null;

    private long window;

    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;
    public static final String SCREEN_TITLE = "Hello Java + Gradle + LWJGL + JOML + Kotlin";

    public static Window Get(){
        if(instance == null)
            instance = new Window();
        return instance;
    }


    private Window(){

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
        glfwWindowHint(GLFW_DECORATED, GL_TRUE); 

		// Create the window
		window = glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_TITLE, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");


        int[] pWidth = new int[1]; // int*
        int[] pHeight = new int[1]; // int*

        // Get the window size passed to glfwCreateWindow
        glfwGetWindowSize(window, pWidth, pHeight);

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Center the window
        glfwSetWindowPos(
            window,
            (vidmode.width() - pWidth[0]) / 2,
            (vidmode.height() - pHeight[0]) / 2
        );

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
    }

    public void Terminate(){
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
    }

    public void Update(){

        glfwSwapBuffers(window); // swap the color buffers

		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
        glEnable(GL_DEPTH_TEST);  
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        
    }



    public Boolean ShouldClose(){
        return glfwWindowShouldClose(window);
    }

    public Boolean Input(int key, int response){
        return glfwGetKey(window, key) == response;
    }
}
