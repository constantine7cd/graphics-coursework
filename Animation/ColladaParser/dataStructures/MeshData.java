package dataStructures;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class MeshData {

	private static final int DIMENSIONS = 3;

	private List<Vertex> vertices;
	private List<Vector2f> textures;
	private List<Vector3f> normals;
	private List<Integer> indices;

	public MeshData(List<Vertex> vertices, List<Vector2f> textures, List<Vector3f> normals, List<Integer> indices)
	{
		this.vertices = vertices;
		this.textures = textures;
		this.normals = normals;
		this.indices = indices;
	}

	public List <Vertex> getVertices() {
	    return vertices;
    }

    public  List <Vector2f> getTextures() {
	    return textures;
    }

    public  List <Vector3f> getNormals() {
	    return normals;
    }

    public List<Integer> getIndices() {
	    return indices;
    }

}
