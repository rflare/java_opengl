package org.myproject.engine;



import org.joml.Matrix4f;
import org.joml.Vector3f;



public class Camera {

    private static Camera instance = null;
    

    public Vector3f cameraPos = new Vector3f(0f, 0f, 0f); 

    public float fov = 0.25f * (float)Math.PI;
    public float near = 0.1f;
    public float far = 100f;

    public float yaw = 0f;
    public float pitch = (float)Math.toRadians(-90);


    private Vector3f CameraTarget(){
        Vector3f retval = new Vector3f(cameraPos);
        retval.add(CameraDirection());
        return new Vector3f(retval);
    } 


    public Vector3f CameraDirection(){
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
            Window.SCREEN_WIDTH / Window.SCREEN_HEIGHT,
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
            MathUtils.V_UP 
        ); 

        return new Matrix4f(view);
    }



}
