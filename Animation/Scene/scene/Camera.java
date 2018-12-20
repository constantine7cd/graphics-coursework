package scene;

import structures.Matrix4f;
import structures.Quaternion;
import structures.Vector4f;
import transformations.Transform;


public class Camera
{
	private static final Vector4f Y_AXIS = new Vector4f(0,1,0);

	public Transform transform;
	public Matrix4f projection;

	private Transform GetTransform()
	{
		return transform;
	}

	public Camera(Matrix4f projection)
	{
		this.projection = projection;
		this.transform = new Transform();
	}

	public Matrix4f GetViewProjection()
	{
		Matrix4f cameraRotation = GetTransform().GetTransformedRot().Conjugate().ToRotationMatrix();
		Vector4f cameraPos = GetTransform().GetTransformedPos().Mul(-1);

		Matrix4f cameraTranslation = new Matrix4f().InitTranslation(cameraPos.GetX(), cameraPos.GetY(), cameraPos.GetZ());

		return projection.Mul(cameraRotation.Mul(cameraTranslation));
	}

	public void moveAhead(float delta)
	{
		Move(GetTransform().GetRot().GetForward(), delta);
	}

	public void moveBackward(float delta)
	{
		Move(GetTransform().GetRot().GetForward(), -delta);
	}

	public void moveLeft(float delta)
	{
		Move(GetTransform().GetRot().GetLeft(), delta);
	}

	public void moveRight(float delta)
	{
		Move(GetTransform().GetRot().GetRight(), delta);
	}


	public void rotateLeft(float delta)
	{
		Rotate(Y_AXIS, -delta);
	}

	public void rotateRight(float delta)
	{
		Rotate(Y_AXIS, delta);
	}

	public void rotateUp(float delta)
	{
		Rotate(GetTransform().GetRot().GetRight(), -delta);
	}

	public void rotateDown(float delta)
	{
		Rotate(GetTransform().GetRot().GetRight(), delta);
	}

	private void Move(Vector4f dir, float amt)
	{
		transform = GetTransform().SetPos(GetTransform().GetPos().Add(dir.Mul(amt)));
	}


	private void Rotate(Vector4f axis, float angle)
	{
		transform = GetTransform().Rotate(new Quaternion(axis, angle));
	}
}
