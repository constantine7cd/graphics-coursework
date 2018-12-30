package structures;

public class Quaternion
{
	private float x;
	private float y;
	private float z;
	private float w;

	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		normalize();
	}

	public Quaternion(Vector4f axis, float angle)
	{
		float sinHalfAngle = (float)Math.sin(angle / 2);
		float cosHalfAngle = (float)Math.cos(angle / 2);

		this.x = axis.GetX() * sinHalfAngle;
		this.y = axis.GetY() * sinHalfAngle;
		this.z = axis.GetZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}


	public void normalize() {
		float mag = (float) Math.sqrt(w * w + x * x + y * y + z * z);
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
	}

	public org.lwjgl.util.vector.Matrix4f toRotationMatrix() {
		org.lwjgl.util.vector.Matrix4f matrix = new org.lwjgl.util.vector.Matrix4f();
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yz = y * z;
		final float yw = y * w;
		final float zw = z * w;
		final float xSquared = x * x;
		final float ySquared = y * y;
		final float zSquared = z * z;
		matrix.m00 = 1 - 2 * (ySquared + zSquared);
		matrix.m01 = 2 * (xy - zw);
		matrix.m02 = 2 * (xz + yw);
		matrix.m03 = 0;
		matrix.m10 = 2 * (xy + zw);
		matrix.m11 = 1 - 2 * (xSquared + zSquared);
		matrix.m12 = 2 * (yz - xw);
		matrix.m13 = 0;
		matrix.m20 = 2 * (xz - yw);
		matrix.m21 = 2 * (yz + xw);
		matrix.m22 = 1 - 2 * (xSquared + ySquared);
		matrix.m23 = 0;
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		matrix.m33 = 1;
		return matrix;
	}

	public static Quaternion fromMatrix(org.lwjgl.util.vector.Matrix4f matrix) {
		float w, x, y, z;
		float diagonal = matrix.m00 + matrix.m11 + matrix.m22;
		if (diagonal > 0) {
			float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);
			w = w4 / 4f;
			x = (matrix.m21 - matrix.m12) / w4;
			y = (matrix.m02 - matrix.m20) / w4;
			z = (matrix.m10 - matrix.m01) / w4;
		} else if ((matrix.m00 > matrix.m11) && (matrix.m00 > matrix.m22)) {
			float x4 = (float) (Math.sqrt(1f + matrix.m00 - matrix.m11 - matrix.m22) * 2f);
			w = (matrix.m21 - matrix.m12) / x4;
			x = x4 / 4f;
			y = (matrix.m01 + matrix.m10) / x4;
			z = (matrix.m02 + matrix.m20) / x4;
		} else if (matrix.m11 > matrix.m22) {
			float y4 = (float) (Math.sqrt(1f + matrix.m11 - matrix.m00 - matrix.m22) * 2f);
			w = (matrix.m02 - matrix.m20) / y4;
			x = (matrix.m01 + matrix.m10) / y4;
			y = y4 / 4f;
			z = (matrix.m12 + matrix.m21) / y4;
		} else {
			float z4 = (float) (Math.sqrt(1f + matrix.m22 - matrix.m00 - matrix.m11) * 2f);
			w = (matrix.m10 - matrix.m01) / z4;
			x = (matrix.m02 + matrix.m20) / z4;
			y = (matrix.m12 + matrix.m21) / z4;
			z = z4 / 4f;
		}
		return new Quaternion(x, y, z, w);
	}

	public static Quaternion interpolate(Quaternion a, Quaternion b, float blend) {
		Quaternion result = new Quaternion(0, 0, 0, 1);
		float dot = a.w * b.w + a.x * b.x + a.y * b.y + a.z * b.z;
		float blendI = 1f - blend;
		if (dot < 0) {
			result.w = blendI * a.w + blend * -b.w;
			result.x = blendI * a.x + blend * -b.x;
			result.y = blendI * a.y + blend * -b.y;
			result.z = blendI * a.z + blend * -b.z;
		} else {
			result.w = blendI * a.w + blend * b.w;
			result.x = blendI * a.x + blend * b.x;
			result.y = blendI * a.y + blend * b.y;
			result.z = blendI * a.z + blend * b.z;
		}
		result.normalize();

		return result;
	}
	
	

	public float Length()
	{
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public Quaternion Normalized()
	{
		float length = Length();
		
		return new Quaternion(x / length, y / length, z / length, w / length);
	}
	
	public Quaternion Conjugate()
	{
		return new Quaternion(-x, -y, -z, w);
	}

	public Quaternion Mul(float r)
	{
		return new Quaternion(x * r, y * r, z * r, w * r);
	}

	public Quaternion Mul(Quaternion r)
	{
		float w_ = w * r.GetW() - x * r.GetX() - y * r.GetY() - z * r.GetZ();
		float x_ = x * r.GetW() + w * r.GetX() + y * r.GetZ() - z * r.GetY();
		float y_ = y * r.GetW() + w * r.GetY() + z * r.GetX() - x * r.GetZ();
		float z_ = z * r.GetW() + w * r.GetZ() + x * r.GetY() - y * r.GetX();
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion Mul(Vector4f r)
	{
		float w_ = -x * r.GetX() - y * r.GetY() - z * r.GetZ();
		float x_ =  w * r.GetX() + y * r.GetZ() - z * r.GetY();
		float y_ =  w * r.GetY() + z * r.GetX() - x * r.GetZ();
		float z_ =  w * r.GetZ() + x * r.GetY() - y * r.GetX();
		
		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion Sub(Quaternion r)
	{
		return new Quaternion(x - r.GetX(), y - r.GetY(), z - r.GetZ(), w - r.GetW());
	}

	public Quaternion Add(Quaternion r)
	{
		return new Quaternion(x + r.GetX(), y + r.GetY(), z + r.GetZ(), w + r.GetW());
	}

	public Matrix4f ToRotationMatrix()
	{
		Vector4f forward =  new Vector4f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector4f up = new Vector4f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector4f right = new Vector4f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().InitRotation(forward, up, right);
	}

	public float Dot(Quaternion r)
	{
		return x * r.GetX() + y * r.GetY() + z * r.GetZ() + w * r.GetW();
	}


	
	public Quaternion(Matrix4f rot)
	{
		float trace = rot.Get(0, 0) + rot.Get(1, 1) + rot.Get(2, 2);

		if(trace > 0)
		{
			float s = 0.5f / (float)Math.sqrt(trace+ 1.0f);
			w = 0.25f / s;
			x = (rot.Get(1, 2) - rot.Get(2, 1)) * s;
			y = (rot.Get(2, 0) - rot.Get(0, 2)) * s;
			z = (rot.Get(0, 1) - rot.Get(1, 0)) * s;
		}
		else
		{
			if(rot.Get(0, 0) > rot.Get(1, 1) && rot.Get(0, 0) > rot.Get(2, 2))
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.Get(0, 0) - rot.Get(1, 1) - rot.Get(2, 2));
				w = (rot.Get(1, 2) - rot.Get(2, 1)) / s;
				x = 0.25f * s;
				y = (rot.Get(1, 0) + rot.Get(0, 1)) / s;
				z = (rot.Get(2, 0) + rot.Get(0, 2)) / s;
			}
			else if(rot.Get(1, 1) > rot.Get(2, 2))
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.Get(1, 1) - rot.Get(0, 0) - rot.Get(2, 2));
				w = (rot.Get(2, 0) - rot.Get(0, 2)) / s;
				x = (rot.Get(1, 0) + rot.Get(0, 1)) / s;
				y = 0.25f * s;
				z = (rot.Get(2, 1) + rot.Get(1, 2)) / s;
			}
			else
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.Get(2, 2) - rot.Get(0, 0) - rot.Get(1, 1));
				w = (rot.Get(0, 1) - rot.Get(1, 0) ) / s;
				x = (rot.Get(2, 0) + rot.Get(0, 2) ) / s;
				y = (rot.Get(1, 2) + rot.Get(2, 1) ) / s;
				z = 0.25f * s;
			}
		}

		float length = (float)Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}

	public Vector4f GetForward()
	{
		return new Vector4f(0,0,1,1).Rotate(this);
	}

	public Vector4f GetBack()
	{
		return new Vector4f(0,0,-1,1).Rotate(this);
	}

	public Vector4f GetUp()
	{
		return new Vector4f(0,1,0,1).Rotate(this);
	}

	public Vector4f GetDown()
	{
		return new Vector4f(0,-1,0,1).Rotate(this);
	}

	public Vector4f GetRight()
	{
		return new Vector4f(1,0,0,1).Rotate(this);
	}

	public Vector4f GetLeft()
	{
		return new Vector4f(-1,0,0,1).Rotate(this);
	}
	
	public float GetX()
	{
		return x;
	}

	public float GetY()
	{
		return y;
	}

	public float GetZ()
	{
		return z;
	}

	public float GetW()
	{
		return w;
	}

	public boolean equals(Quaternion r)
	{
		return x == r.GetX() && y == r.GetY() && z == r.GetZ() && w == r.GetW();
	}
}
