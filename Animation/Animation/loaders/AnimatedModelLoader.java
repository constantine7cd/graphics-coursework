package loaders;

import aniModel.AniModel;
import aniModel.Joint;
import colladaLoader.ColladaLoader;
import dataStructures.AnimatedModelData;
import dataStructures.JointData;
import dataStructures.MeshData;
import dataStructures.SkeletonData;
import settings.Settings;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import structures.MVertex;
import structures.Vector4f;
import utils.MFile;

import dataStructures.Vertex;

import entity.Mesh;
import render.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class AnimatedModelLoader {

	public static AniModel loadEntity(MFile modelFile, Bitmap texture)
    {
		AnimatedModelData entityData = ColladaLoader.loadColladaModel(modelFile, Settings.MAX_WEIGHTS);
		Mesh model = createMesh(entityData.getMeshData());
		SkeletonData skeletonData = entityData.getJointsData();
		Joint headJoint = createJoints(skeletonData.headJoint);

		return new AniModel(model, texture, headJoint, skeletonData.jointCount);
	}


	private static Joint createJoints(JointData data) {
		Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
		for (JointData child : data.children) {
			joint.addChild(createJoints(child));
		}
		return joint;
	}

    private  static  Mesh createMesh(MeshData data) {
        int ilen = data.getVertices().size();

        List<MVertex> m_vertices = new ArrayList<MVertex>();
        List<Integer> m_indices = new ArrayList<Integer>();


        for (int i = 0; i < ilen; ++i) {
            Vertex v = data.getVertices().get(i);

            float [] wgts = new float[3];
            int [] jids = new int[3];

            //Weights
            for (int j = 0; j < 3; ++j)
                wgts[j] = v.getWeightsData().weights.get(j);

            //Joint ids
            for (int j = 0; j < 3; ++j)
                jids[j] = v.getWeightsData().jointIds.get(j);

            Vector3f n = data.getNormals().get(v.getNormalIndex());

            //Normal
            Vector4f m_normal = new Vector4f(n.x, n.y, n.z, 0);

            Vector2f t = data.getTextures().get(v.getTextureIndex());

            //TexCoords
            Vector4f m_texCoords = new Vector4f(t.x, 1 - t.y, 0, 0);

            //Position
            Vector4f m_pos = new Vector4f(v.getPosition().x, v.getPosition().y, v.getPosition().z, 1);

            m_vertices.add(new MVertex(m_pos, m_texCoords, m_normal, jids, wgts));
        }
		m_indices = data.getIndices();


        return new Mesh(m_vertices, m_indices);
    }


}
