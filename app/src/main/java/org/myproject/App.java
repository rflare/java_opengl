package org.myproject;

import org.myproject.engine.*;


import org.joml.*;




public class App {


	public static void main(String[] args){


        Window windowManager = Window.Get();
        Scene scene = Scene.Get();
        Time time = Time.Get();

        Script gameScript = new Script();
        gameScript.Start();



		while ( !windowManager.ShouldClose()) {
		    windowManager.Update();	
            gameScript.Update();
            scene.Update();
            time.Update();

            










			
		}
		
    
        windowManager.Terminate();  
	}
    
}

