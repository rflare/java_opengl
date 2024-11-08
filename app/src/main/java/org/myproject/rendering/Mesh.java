package org.myproject.rendering;


import static org.lwjgl.opengl.GL30.*;


public class Mesh{

    private static final int FLOAT_SIZE = 4;

    private int VAO;

    private int EBO;

    private int coordBO;
    private int colorBO;
    private int textureBO;
    private int normalBO;

    private int vertexCount;

    public Mesh(float[] coords, float[] colors, float[] texCoords, float[] normals, int[] indices){
        

        vertexCount = coords.length / 3;

        VAO = glGenVertexArrays();

        coordBO = glGenBuffers();
        colorBO = glGenBuffers();
        textureBO = glGenBuffers();
        textureBO = glGenBuffers();

        EBO = glGenBuffers();


		glBindVertexArray(VAO);
        
        glBindBuffer(GL_ARRAY_BUFFER, coordBO);
		glBufferData(GL_ARRAY_BUFFER, coords, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);


        glBindBuffer(GL_ARRAY_BUFFER, colorBO);
		glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, textureBO);
		glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, normalBO);
		glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
		glVertexAttribPointer(3, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(3);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        


		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
    }


    public void Draw(){
        glBindVertexArray(VAO);
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
