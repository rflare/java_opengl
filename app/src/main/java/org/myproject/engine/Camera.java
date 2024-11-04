package org.myproject.engine;

import org.myproject.engine.*;

import static org.myproject.engine.WindowManager.*;
import static org.myproject.utils.MathUtils.*;


import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private static Camera instance = null;
    
    private float yaw = -0.5f * (float)Math.PI;
    private float pitch = 0f;


    private Vector3f cameraPos = new Vector3f(0f, 0f, 0f); 

    public float fov = 0.25f * (float)Math.PI;
    public float near = 0.1f;
    public float far = 100f;

    public float cameraMoveSpeed = 1f; 

    public float sensitivity = 1f;

    private Vector3f CameraTarget(){
        Vector3f retval = new Vector3f(cameraPos);
        retval.add(CameraDirection());
        return new Vector3f(retval);
    } 


    private Vector3f CameraDirection(){
        Vector3f retval = new Vector3f(
            (float) Math.cos(yaw) * (float) Math.cos(pitch),
            (float) Math.sin(pitch),
            -1 * (float) Math.sin(yaw) * (float) Math.cos(pitch)
        );

        retval.normalize();
        return new Vector3f(retval);

    }

    public static Camera Get(){
        if (instance == null)
            instance = new Camera();
        return instance;
    }

    private Camera(){}

    public Matrix4f PerspectiveProjection(){
        Matrix4f retval = new Matrix4f();
        retval.perspective(
            fov,
            SCREEN_WIDTH / SCREEN_HEIGHT,
            near,
            far
        );

        return new Matrix4f(retval);
    }

    public Matrix4f View(){
        Matrix4f view = new Matrix4f();
        view.lookAt(
            cameraPos,
            CameraTarget(),
            V_UP
        ); 

        return new Matrix4f(view);
    }



    public void Update(){

        float deltaYaw = 0f;
        float deltaPitch = 0f;

        WindowManager windowManager = WindowManager.Get();
        Time time = Time.Get();

        if(windowManager.Input(GLFW_KEY_UP, GLFW_PRESS)) deltaPitch += sensitivity * time.deltaTime;
        if(windowManager.Input(GLFW_KEY_DOWN, GLFW_PRESS)) deltaPitch -= sensitivity * time.deltaTime;
        if(windowManager.Input(GLFW_KEY_LEFT, GLFW_PRESS)) deltaYaw += sensitivity * time.deltaTime;
        if(windowManager.Input(GLFW_KEY_RIGHT, GLFW_PRESS)) deltaYaw -= sensitivity * time.deltaTime;
        

        yaw += deltaYaw;
        pitch += deltaPitch;
        pitch = (float) Math.max(Math.min(pitch, 0.5 * Math.PI), -0.5 * Math.PI);



        Vector3f cameraRight = new Vector3f(CameraDirection());
        cameraRight.cross(V_UP);
        cameraRight.normalize();

        Vector3f cameraFront = new Vector3f(V_UP);
        cameraFront.cross(cameraRight);
        cameraFront.normalize();

        Vector3f deltaCameraPos = new Vector3f(0f, 0f, 0f);

        if(windowManager.Input(GLFW_KEY_W, GLFW_PRESS)) deltaCameraPos.add(cameraFront);
        if(windowManager.Input(GLFW_KEY_S, GLFW_PRESS)) deltaCameraPos.sub(cameraFront);
        if(windowManager.Input(GLFW_KEY_D, GLFW_PRESS)) deltaCameraPos.add(cameraRight);
        if(windowManager.Input(GLFW_KEY_A, GLFW_PRESS)) deltaCameraPos.sub(cameraRight);

        if(deltaCameraPos.length() != 0) deltaCameraPos.normalize();
            deltaCameraPos.mul(cameraMoveSpeed * time.deltaTime);


        cameraPos.add(deltaCameraPos);
    }
}
