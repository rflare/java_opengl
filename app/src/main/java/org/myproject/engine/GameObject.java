package org.myproject.engine;

import org.joml.Matrix4f;

public class GameObject {
    public Model model = null;
    public Shader shader = new Shader(); 
  

    public void Draw(){


        if(model != null)
            model.Draw();

        shader.Use();
    }
}
