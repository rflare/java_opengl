package org.myproject.engine;




import org.lwjgl.*;
import org.joml.*;

import static org.lwjgl.opengl.GL30.*;

import java.nio.*;
import java.util.*;


public class Shader{

    public HashMap<String, Object> attributes = new HashMap<String, Object>();

    private int ID;

    private Boolean ready = false;
    public Boolean isReady(){return ready;}



    public void Compile(String vertexPath, String fragmentPath)
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
        
        ready = true;
    }

    
    public void Use(){
        if(!ready)
            return;
        glUseProgram(ID);

        for(String i : attributes.keySet()){
            Object currentAttrib = attributes.get(i);
            String currentType = currentAttrib.getClass().getName();

            if(currentType == "Matrix4f")
                setMat4(i, (Matrix4f) currentAttrib);
        }

    }

    private void setMat4(String name, Matrix4f value) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        value.get(buf);
        glUniformMatrix4fv(
            glGetUniformLocation(ID, name),
            false,
            buf
        );
    } 

    private void setSampler(String name, int index) {
         
        glUniform1i(
            glGetUniformLocation(ID, name),
            index
        );
    }
}
