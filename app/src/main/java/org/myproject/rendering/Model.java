package org.myproject.rendering;

import java.nio.IntBuffer;
import java.util.*;

import org.lwjgl.assimp.*;

import static org.lwjgl.assimp.Assimp.*;

public class Model {
    private ArrayList<Mesh> meshes = new ArrayList<Mesh>();

    public Model(String modelPath){
        AIScene scene = aiImportFile(modelPath, aiProcess_Triangulate);

        if(
            scene == null ||
            scene.mRootNode() == null
        ) 
            throw new IllegalStateException(aiGetErrorString());
    }

    private Mesh ProcessMesh(AIMesh aiMesh){

        int vertexCount = aiMesh.mNumVertices();
        
        List<Float> coords = new ArrayList<>(); 
        List<Float> colors = new ArrayList<>();
        List<Float> texCoords = new ArrayList<>();
        List<Float> normals = new ArrayList<>();

        List<Integer> indices = new ArrayList<>();


        
        ProcessVertices(aiMesh.mVertices(), coords, 3);
        ProcessColor(aiMesh.mColors(0), colors);
        ProcessVertices(aiMesh.mTextureCoords(0), texCoords, 2);
        ProcessVertices(aiMesh.mNormals(), normals, 3);

        ProcessIndices(aiMesh.mFaces())

        return new Mesh(coords, colors, texCoords, normals, indices);
    }

    private void ProcessVertices(AIVector3D.Buffer aibuffer, List<Float> vertices, int dimensions){
        while(aibuffer.remaining() > 0){
            AIVector3D aivertex = aibuffer.get();
            if(dimensions >= 1) vertices.add(aivertex.x()); 
            if(dimensions >= 2) vertices.add(aivertex.y()); 
            if(dimensions >= 3) vertices.add(aivertex.z()); 
        }
    }

    private void ProcessColor(AIColor4D.Buffer aibuffer, float[] vertices){
        int i = 0;
        if(aibuffer == null) return;
        while(aibuffer.remaining() > 0){
            AIColor4D aivertex = aibuffer.get();
            vertices[i] = aivertex.r(); 
            vertices[i + 1] = aivertex.g(); 
            vertices[i + 2] = aivertex.b();
            vertices[i + 3] = aivertex.a();

            i += 4;
        }
    }
    private void ProcessIndices(AIFace.Buffer facebuffer, List<Integer> indices, int count){
        for(int i = 0; i < count;){
            AIFace face = facebuffer.get();
            IntBuffer indiceBuffer = face.mIndices(); 

            while(indiceBuffer.remaining() > 0){
                int indice = indiceBuffer.get();
                indices[i] = indice;
                i++;
            }
        }
    }
    public void Draw(){
        for(int i = 0; i < meshes.size(); i++){
            meshes.get(i).Draw();
        }
    }
}
