package org.myproject.rendering;

import org.myproject.utils.*;

import org.lwjgl.*;
import org.joml.*;

import static org.lwjgl.opengl.GL30.*;

import java.nio.*;


public class Shader{
    private int ID;

    public Shader(String vertexPath, String fragmentPath)
    {
        String vertexCode = FileUtils.ReadFileToString(vertexPath);
        String fragmentCode = FileUtils.ReadFileToString(fragmentPath);
       
        int vertexShader = glCreateShader(GL_VERTEX_SHADER); 
        glShaderSource(vertexShader, vertexCode);
        glCompileShader(vertexShader);
        String infoLog = glGetShaderInfoLog(vertexShader);
        System.out.println(infoLog);
       
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
	    glShaderSource(fragmentShader, fragmentCode);
		glCompileShader(fragmentShader);
		infoLog = glGetShaderInfoLog(fragmentShader);
		System.out.println(infoLog);
        

        ID = glCreateProgram();
		glAttachShader(ID, vertexShader);
		glAttachShader(ID, fragmentShader);
		glLinkProgram(ID);
		infoLog = glGetProgramInfoLog(ID);
		System.out.println(infoLog);
		
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
    }
    
    public void Use(){
        glUseProgram(ID);
    }

    public void setMat4(String name, Matrix4f value) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        value.get(buf);
        glUniformMatrix4fv(
            glGetUniformLocation(ID, name),
            false,
            buf
        );
    } 
}
