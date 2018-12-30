package structures;

public class MVertex
{
	private Vector4f pos;
	private Vector4f texCoords;
	private Vector4f normal;
	private int[] jointIds;
	private float[] vertexWeights;

	public float GetX() { return pos.GetX(); }

	public float GetY() { return pos.GetY(); }

	public float GetZ() { return pos.GetZ(); }

	public float GetW() { return pos.GetW(); }

	public Vector4f GetPosition() { return pos; }
	public Vector4f GetTexCoords() { return texCoords; }
	public Vector4f GetNormal() { return normal; }
	public int[] GetJointIds() { return jointIds; }
	public float[] GetVertexWeights() { return vertexWeights; }

	public MVertex(Vector4f pos, Vector4f texCoords, Vector4f normal, int[] ids, float[] weights)
	{
		this.pos = pos;
		this.texCoords = texCoords;
		this.normal = normal;
		this.jointIds = ids;
		this.vertexWeights = weights;
	}

	public MVertex Transform(Matrix4f transform, Matrix4f normalTransform)
	{
		return new MVertex(transform.Transform(pos), texCoords,
				normalTransform.Transform(normal).Normalized(), jointIds, vertexWeights);
	}

	public MVertex PerspectiveDivide()
	{
		return new MVertex(new Vector4f(pos.GetX()/pos.GetW(), pos.GetY()/pos.GetW(),
						pos.GetZ()/pos.GetW(), pos.GetW()),	
				texCoords, normal, jointIds, vertexWeights);
	}

	public float TriangleAreaTimesTwo(MVertex b, MVertex c)
	{
		float x1 = b.GetX() - pos.GetX();
		float y1 = b.GetY() - pos.GetY();

		float x2 = c.GetX() - pos.GetX();
		float y2 = c.GetY() - pos.GetY();

		return (x1 * y2 - x2 * y1);
	}

	public MVertex Lerp(MVertex other, float lerpAmt)
	{
		return new MVertex(
				pos.Lerp(other.GetPosition(), lerpAmt),
				texCoords.Lerp(other.GetTexCoords(), lerpAmt),
				normal.Lerp(other.GetNormal(), lerpAmt),
                jointIds, vertexWeights
				);
	}

	public boolean IsInsideViewFrustum()
	{
		return 
			Math.abs(pos.GetX()) <= Math.abs(pos.GetW()) &&
			Math.abs(pos.GetY()) <= Math.abs(pos.GetW()) &&
			Math.abs(pos.GetZ()) <= Math.abs(pos.GetW());
	}

	public String toString()
	{
		return new String(this.pos.GetX() + " "
				+ this.pos.GetY() + " " + this.pos.GetZ() + " "
				+ this.pos.GetW());
	}

	public float Get(int index)
	{
		switch(index)
		{
			case 0:
				return pos.GetX();
			case 1:
				return pos.GetY();
			case 2:
				return pos.GetZ();
			case 3:
				return pos.GetW();
			default:
				throw new IndexOutOfBoundsException();
		}
	}
}
