package entity;

import java.util.ArrayList;
import java.util.List;

import structures.MVertex;
import structures.Matrix4f;
import render.Bitmap;
import render.Render;
import structures.Vector4f;


public class Mesh
{
	private List<MVertex> vertices;
	private List<MVertex> fr_vertices;
	private List<Integer> indices;
	
	public Mesh(List <MVertex> vertices, List<Integer> indices)
	{
        this.vertices = vertices;

        int [] is = {0, 0, 0};
        float [] ws = {0, 0, 0};
        fr_vertices = new ArrayList<>();
        MVertex v = new MVertex(new Vector4f(0, 0, 0), new Vector4f(0, 0, 0),
                new Vector4f(0, 0, 0), is, ws);
        for (int i = 0; i < vertices.size(); ++i) {
            fr_vertices.add(v);
        }
        this.indices = indices;
	}

	public void Draw(Render context, Matrix4f viewProjection, Matrix4f transform, Bitmap texture)
	{
        Matrix4f mvp = viewProjection.Mul(transform);

		for(int i = 0; i < indices.size(); i += 3)
		{
			context.DrawTriangle(
					fr_vertices.get(indices.get(i)).Transform(mvp, transform),
                    fr_vertices.get(indices.get(i + 1)).Transform(mvp, transform),
                    fr_vertices.get(indices.get(i + 2)).Transform(mvp, transform),
					texture);
		}
	}

	public List<MVertex> getVertices()
    {
        return vertices;
    }

    public List<MVertex> getTVertices()
    {
        return fr_vertices;
    }

}
