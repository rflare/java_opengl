package org.myproject.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time{

    private static Time instance = null;

    public float deltaTime; 
    private float lastFrame;

    private Time(){
        deltaTime = 0;
        lastFrame = 0;
    }

    public static Time Get(){
        if (instance == null)
            instance = new Time();
        return instance;
    }

    public void Update(){
        float currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;
    }
}
