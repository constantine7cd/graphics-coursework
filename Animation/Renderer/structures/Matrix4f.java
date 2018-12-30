package structures;

public class Matrix4f
{
	private float[][] mtx;

	public Matrix4f()
	{
		mtx = new float[4][4];
	}

	public Matrix4f InitIdentity()
	{
		mtx[0][0] = 1;	mtx[0][1] = 0;	mtx[0][2] = 0;	mtx[0][3] = 0;
		mtx[1][0] = 0;	mtx[1][1] = 1;	mtx[1][2] = 0;	mtx[1][3] = 0;
		mtx[2][0] = 0;	mtx[2][1] = 0;	mtx[2][2] = 1;	mtx[2][3] = 0;
		mtx[3][0] = 0;	mtx[3][1] = 0;	mtx[3][2] = 0;	mtx[3][3] = 1;

		return this;
	}

	public Matrix4f InitScreenSpaceTransform(float halfWidth, float halfHeight)
	{
		mtx[0][0] = halfWidth;	mtx[0][1] = 0;	mtx[0][2] = 0;	mtx[0][3] = halfWidth - 0.5f;
		mtx[1][0] = 0;	mtx[1][1] = -halfHeight;	mtx[1][2] = 0;	mtx[1][3] = halfHeight - 0.5f;
		mtx[2][0] = 0;	mtx[2][1] = 0;	mtx[2][2] = 1;	mtx[2][3] = 0;
		mtx[3][0] = 0;	mtx[3][1] = 0;	mtx[3][2] = 0;	mtx[3][3] = 1;

		return this;
	}

	public Matrix4f InitTranslation(float x, float y, float z)
	{
		mtx[0][0] = 1;	mtx[0][1] = 0;	mtx[0][2] = 0;	mtx[0][3] = x;
		mtx[1][0] = 0;	mtx[1][1] = 1;	mtx[1][2] = 0;	mtx[1][3] = y;
		mtx[2][0] = 0;	mtx[2][1] = 0;	mtx[2][2] = 1;	mtx[2][3] = z;
		mtx[3][0] = 0;	mtx[3][1] = 0;	mtx[3][2] = 0;	mtx[3][3] = 1;

		return this;
	}


	public Matrix4f InitScale(float x, float y, float z)
	{
		mtx[0][0] = x;	mtx[0][1] = 0;	mtx[0][2] = 0;	mtx[0][3] = 0;
		mtx[1][0] = 0;	mtx[1][1] = y;	mtx[1][2] = 0;	mtx[1][3] = 0;
		mtx[2][0] = 0;	mtx[2][1] = 0;	mtx[2][2] = z;	mtx[2][3] = 0;
		mtx[3][0] = 0;	mtx[3][1] = 0;	mtx[3][2] = 0;	mtx[3][3] = 1;

		return this;
	}

	public Matrix4f InitPerspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;

		mtx[0][0] = 1.0f / (tanHalfFOV * aspectRatio);	mtx[0][1] = 0;					mtx[0][2] = 0;	mtx[0][3] = 0;
		mtx[1][0] = 0;						mtx[1][1] = 1.0f / tanHalfFOV;	mtx[1][2] = 0;	mtx[1][3] = 0;
		mtx[2][0] = 0;						mtx[2][1] = 0;					mtx[2][2] = (-zNear -zFar)/zRange;	mtx[2][3] = 2 * zFar * zNear / zRange;
		mtx[3][0] = 0;						mtx[3][1] = 0;					mtx[3][2] = 1;	mtx[3][3] = 0;


		return this;
	}


	public Matrix4f InitRotation(Vector4f forward, Vector4f up)
	{
		Vector4f f = forward.Normalized();

		Vector4f r = up.Normalized();
		r = r.Cross(f);

		Vector4f u = f.Cross(r);

		return InitRotation(f, u, r);
	}

	public Matrix4f InitRotation(Vector4f forward, Vector4f up, Vector4f right)
	{
		Vector4f f = forward;
		Vector4f r = right;
		Vector4f u = up;

		mtx[0][0] = r.GetX();	mtx[0][1] = r.GetY();	mtx[0][2] = r.GetZ();	mtx[0][3] = 0;
		mtx[1][0] = u.GetX();	mtx[1][1] = u.GetY();	mtx[1][2] = u.GetZ();	mtx[1][3] = 0;
		mtx[2][0] = f.GetX();	mtx[2][1] = f.GetY();	mtx[2][2] = f.GetZ();	mtx[2][3] = 0;
		mtx[3][0] = 0;		mtx[3][1] = 0;		mtx[3][2] = 0;		mtx[3][3] = 1;

		return this;
	}

	public Vector4f Transform(Vector4f r)
	{
		return new Vector4f(mtx[0][0] * r.GetX() + mtx[0][1] * r.GetY() + mtx[0][2] * r.GetZ() + mtx[0][3] * r.GetW(),
		                    mtx[1][0] * r.GetX() + mtx[1][1] * r.GetY() + mtx[1][2] * r.GetZ() + mtx[1][3] * r.GetW(),
		                    mtx[2][0] * r.GetX() + mtx[2][1] * r.GetY() + mtx[2][2] * r.GetZ() + mtx[2][3] * r.GetW(),
							mtx[3][0] * r.GetX() + mtx[3][1] * r.GetY() + mtx[3][2] * r.GetZ() + mtx[3][3] * r.GetW());
	}

	public Matrix4f Mul(Matrix4f r)
	{
		Matrix4f res = new Matrix4f();

		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				res.Set(i, j, mtx[i][0] * r.Get(0, j) +
						mtx[i][1] * r.Get(1, j) +
						mtx[i][2] * r.Get(2, j) +
						mtx[i][3] * r.Get(3, j));
			}
		}

		return res;
	}


	public float Get(int x, int y)
	{
		return mtx[x][y];
	}

	public void Set(int x, int y, float value)
	{
		mtx[x][y] = value;
	}




}
