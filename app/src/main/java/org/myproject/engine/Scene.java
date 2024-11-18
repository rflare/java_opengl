package org.myproject.engine;

import java.util.List;
import java.util.ArrayList;

public class Scene {

    private static Scene instance;
    private Scene(){}
    public static Scene Get(){
        if(instance == null)
            instance = new Scene();
        return instance;
    }


    public List<GameObject> objects = new ArrayList<GameObject>();

    public void Update(){
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).Draw();
        }
    }
}

