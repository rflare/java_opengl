package org.myproject;


import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.myproject.engine.*;

import static org.lwjgl.glfw.GLFW.*;

public class Script{

    GameObject backpack = new GameObject();

    public void Start(){
        backpack.shader.Compile("vertex.glsl", "fragment.glsl");
        Matrix4f modeltransform = new Matrix4f();
        modeltransform.translate(0f, 0f, -3f);

        Model model = new Model("Survival_BackPack_2/Survival_BackPack_2.fbx");
        backpack.model = model;


        backpack.shader.attributes.put("transform", modeltransform);


        Scene.Get().objects.add(backpack);



    }

    public void Update(){
        Window window = Window.Get();
        Camera camera = Camera.Get();
        Time time = Time.Get();

        if(window.Input(GLFW_KEY_RIGHT, GLFW_PRESS)) camera.yaw += 1 * time.deltaTime;
        if(window.Input(GLFW_KEY_LEFT, GLFW_PRESS)) camera.yaw -= 1 * time.deltaTime;
        if(window.Input(GLFW_KEY_UP, GLFW_PRESS)) camera.pitch += 1 * time.deltaTime;
        if(window.Input(GLFW_KEY_DOWN, GLFW_PRESS)) camera.pitch -= 1 * time.deltaTime;

        Vector3f deltaCameraPos = new Vector3f(0, 0, 0);

        Vector3f cameraRight = new Vector3f(camera.CameraDirection());
        cameraRight.cross(MathUtils.V_UP);

        if(window.Input(GLFW_KEY_W, GLFW_PRESS)) deltaCameraPos.add(camera.CameraDirection());
        if(window.Input(GLFW_KEY_S, GLFW_PRESS)) deltaCameraPos.sub(camera.CameraDirection());
        if(window.Input(GLFW_KEY_D, GLFW_PRESS)) deltaCameraPos.add(cameraRight);
        if(window.Input(GLFW_KEY_A, GLFW_PRESS)) deltaCameraPos.sub(cameraRight);

        deltaCameraPos.mul(time.deltaTime);

        camera.cameraPos.add(deltaCameraPos);

        backpack.shader.attributes.put("view", camera.View());
        backpack.shader.attributes.put("projection", camera.PerspectiveProjection());

    }
}
