package org.myproject.rendering;


import static org.lwjgl.opengl.GL30.*;


public class Mesh{

    private static final int POSITION_SIZE = 3;
    private static final int TEXCOORD_SIZE = 2;

    private static final int FLOAT_SIZE = 4;

    private int VAO;
    private int VBO;
    private int EBO;

    private int vertexCount;

    public Mesh(float[] vertices){
        
        int vertexSize = POSITION_SIZE + TEXCOORD_SIZE;
        int STRIDE = FLOAT_SIZE * vertexSize;

        vertexCount = vertices.length / vertexSize;

        VAO = glGenVertexArrays();
        VBO = glGenBuffers();


		glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        int offset = 0;
        
		glVertexAttribPointer(0, POSITION_SIZE, GL_FLOAT, false, STRIDE, offset);
        glEnableVertexAttribArray(0);
        offset += POSITION_SIZE * FLOAT_SIZE;

        glVertexAttribPointer(2, TEXCOORD_SIZE, GL_FLOAT, false, STRIDE, offset);
        glEnableVertexAttribArray(2); 

        


		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
    }

    public void Bind(){
        glBindVertexArray(VAO);
    }

    public void Unbind(){
        glBindVertexArray(0);
    }

    public void Draw(){
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }
}
