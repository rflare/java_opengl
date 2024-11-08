package org.myproject;

import org.myproject.engine.*;
import org.myproject.rendering.*;



import org.joml.*;

import org.joml.Math;



public class App {


	public static void main(String[] args){


        WindowManager windowManager = WindowManager.Get();


		Shader myShader = new Shader("vertex.vert", "fragment.frag");



        Texture mytexture = new Texture("ground_tiles_21_baseColor_2k.png");
        
		
        Vector3f[] positions = new Vector3f[10];
        Vector3f[] rotations = new Vector3f[10];
        for(int i = 0; i < 10; i++) {
            rotations[i] = new Vector3f(
                (float) Math.random(),
                (float) Math.random(),
                (float) Math.random()
            ).normalize();
            positions[i] = new Vector3f(
                (float) Math.random() * 10 - 5f,
                (float) Math.random() * 3 - 1.5f,
                (float) Math.random() * 10 - 5f
            );
        }

        Camera mycamera = Camera.Get();
        mycamera.sensitivity = 4f;
        mycamera.cameraMoveSpeed = 3.5f;

        Time time = Time.Get();



		while ( !windowManager.ShouldClose()) {
		    windowManager.LoopBegin();	

            mycamera.Update();
            time.Update();
            

            mytexture.Use();
			myShader.Use();
            myShader.setMat4("projection", mycamera.PerspectiveProjection());
            myShader.setMat4("view", mycamera.View());


            for(int i = 0; i < 10; i++) {


                Matrix4f modelTransform = new Matrix4f();

                modelTransform.translate(positions[i]);
                modelTransform.rotate(
                    3.13f,
                    rotations[i]
                );
                myShader.setMat4("transform", modelTransform);



            }



			windowManager.LoopEnd();
			
		}
		
    
        windowManager.Terminate();  
	}
    
}

