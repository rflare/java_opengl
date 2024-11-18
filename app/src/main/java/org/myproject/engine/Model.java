package org.myproject.engine;

import java.nio.IntBuffer;
import java.util.*;

import org.joml.*;
import org.lwjgl.assimp.*;

import static org.lwjgl.assimp.Assimp.*;

public class Model {
    public List<Mesh> meshes = new ArrayList<Mesh>();
    public List<Matrix4f> transforms = new ArrayList<Matrix4f>();

    public Model(String modelPath){
        AIScene scene = aiImportFile(FileUtils.Path(modelPath),
            aiProcess_Triangulate | 
            aiProcess_PreTransformVertices);

        if(scene == null
        || scene.mRootNode() == null) 
            throw new IllegalStateException(aiGetErrorString());

        ProcessNode(scene.mRootNode(), scene);
    }

    private void ProcessNode(AINode node, AIScene scene){
        for(int i = 0; i < node.mNumMeshes(); i++){
            int meshIndex = node.mMeshes().get(i);
            AIMesh mesh = AIMesh.create(scene.mMeshes().get(meshIndex));
            meshes.add(ProcessMesh(mesh));
            transforms.add(MathUtils.AssimpToJoml(node.mTransformation()));
        }


        for(int i = 0; i < node.mNumChildren(); i++){
            ProcessNode(AINode.create(node.mChildren().get(i)), scene);
        }
    }

    private Mesh ProcessMesh(AIMesh aiMesh){

        List<Float> coords = new ArrayList<Float>(); 
        List<Float> colors = new ArrayList<Float>();
        List<Float> texCoords = new ArrayList<Float>();
        List<Float> normals = new ArrayList<Float>();

        List<Integer> indices = new ArrayList<Integer>();


    
        ProcessCoords(aiMesh, coords);
        ProcessColors(aiMesh, colors);
        ProcessTexcoords(aiMesh, texCoords);
        ProcessNormals(aiMesh, normals);
        

        ProcessIndices(aiMesh, indices);

        return new Mesh(
            ListUtils.ListToArrayFloat(coords),
            ListUtils.ListToArrayFloat(colors),
            ListUtils.ListToArrayFloat(texCoords),
            ListUtils.ListToArrayFloat(normals),
            ListUtils.ListToArrayInt(indices)
        );
    }

    private void ProcessCoords(AIMesh mesh, List<Float> coords){
        AIVector3D.Buffer buffer = mesh.mVertices();
        while(buffer.remaining() > 0){
            AIVector3D aivertex = buffer.get();
            coords.add(aivertex.x()); 
            coords.add(aivertex.y()); 
            coords.add(aivertex.z()); 
        }
    }

    private void ProcessColors(AIMesh mesh, List<Float> colors){
        AIColor4D.Buffer buffer = mesh.mColors(0);
        if(buffer == null)
            return;
        while(buffer.remaining() > 0){
            AIColor4D color = buffer.get();
            colors.add(color.r());
            colors.add(color.g());
            colors.add(color.b());
            colors.add(color.a());
        }
    }
    private void ProcessTexcoords(AIMesh mesh, List<Float> coords){
        AIVector3D.Buffer buffer = mesh.mTextureCoords(0);
        if(buffer == null)
            return;
        while(buffer.remaining() > 0){
            AIVector3D aivertex = buffer.get();
            coords.add(aivertex.x()); 
            coords.add(1 - aivertex.y()); 
        }
    }
    private void ProcessNormals(AIMesh mesh, List<Float> coords){
        AIVector3D.Buffer buffer = mesh.mNormals();
        while(buffer != null && buffer.remaining() > 0){
            AIVector3D aivertex = buffer.get();
            coords.add(aivertex.x()); 
            coords.add(aivertex.y()); 
            coords.add(aivertex.z()); 
        }
    }
    private void ProcessIndices(AIMesh mesh, List<Integer> indices){
        int numFaces = mesh.mNumFaces();
        AIFace.Buffer buffer = mesh.mFaces();
        for(int i = 0; i < numFaces; i++){

            AIFace face = buffer.get(i);
            IntBuffer indexbuffer = face.mIndices();
            if(indexbuffer == null) continue;
            while(indexbuffer.remaining() > 0){
                indices.add(indexbuffer.get());
            }
        }
    }
    public void Draw(){
        for(int i = 0; i < meshes.size(); i++){
            meshes.get(i).Draw();
        }
    }
}
