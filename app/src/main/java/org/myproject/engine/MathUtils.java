package org.myproject.engine;

import org.joml.*;

import org.lwjgl.assimp.*;

public class MathUtils {
    public static final Vector3f V_UP = new Vector3f(0f, 1f, 0f);

    public static Matrix4f AssimpToJoml(AIMatrix4x4 aiMatrix) {
        Matrix4f jomlMatrix = new Matrix4f();
        
        jomlMatrix.m00(aiMatrix.a1()).m01(aiMatrix.a2()).m02(aiMatrix.a3()).m03(aiMatrix.a4());
        jomlMatrix.m10(aiMatrix.b1()).m11(aiMatrix.b2()).m12(aiMatrix.b3()).m13(aiMatrix.b4());
        jomlMatrix.m20(aiMatrix.c1()).m21(aiMatrix.c2()).m22(aiMatrix.c3()).m23(aiMatrix.c4());
        jomlMatrix.m30(aiMatrix.d1()).m31(aiMatrix.d2()).m32(aiMatrix.d3()).m33(aiMatrix.d4());

        return jomlMatrix;
    }
}
