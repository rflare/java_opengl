package org.myproject.engine;


import static org.lwjgl.opengl.GL30.*;


public class Mesh{

    private int VAO;

    private int EBO;

    private int coordBO;
    private int colorBO;
    private int textureBO;
    private int normalBO;

    private int vertexCount;

    public Mesh(float[] coords, float[] colors, float[] texCoords, float[] normals, int[] indices){
        

        vertexCount = indices.length;

        VAO = glGenVertexArrays();

        coordBO = glGenBuffers();
        colorBO = glGenBuffers();
        textureBO = glGenBuffers();
        normalBO = glGenBuffers();

        EBO = glGenBuffers();


		glBindVertexArray(VAO);
        
        glBindBuffer(GL_ARRAY_BUFFER, coordBO);
		glBufferData(GL_ARRAY_BUFFER, coords, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);


        glBindBuffer(GL_ARRAY_BUFFER, colorBO);
		glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, textureBO);
		glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, normalBO);
		glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
        glEnableVertexAttribArray(3);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, 0, 0);

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
