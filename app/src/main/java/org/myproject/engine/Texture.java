package org.myproject.engine;


import org.lwjgl.stb.*;


import static org.lwjgl.opengl.GL30.*;

import java.nio.*;


public class Texture{
    private int texture;  
    

    public Texture(String texturePath){

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        int[] width = new int[1];
        int[] height = new int[1];
        int[] nrchannels = new int[1];

        ByteBuffer textureData = STBImage.stbi_load(FileUtils.Path(texturePath), width, height, nrchannels, 0);

        texture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texture);

        if(textureData.hasRemaining())
        {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width[0], height[0], 0, GL_RGB, GL_UNSIGNED_BYTE, textureData);
            glGenerateMipmap(GL_TEXTURE_2D);
        }
        else{
            System.out.println("Failed to load texture");
        }

        STBImage.stbi_image_free(textureData);

    }


    public void Use(){
        glActiveTexture(GL_TEXTURE0 + this.hashCode());
        glBindTexture(GL_TEXTURE_2D, texture);
    }
}
