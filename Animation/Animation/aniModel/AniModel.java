package aniModel;

import render.Bitmap;
import entity.Mesh;
import org.lwjgl.util.vector.Matrix4f;

import animation.Animation;
import animation.Animator;

public class AniModel {

	private final Mesh model;
	private final Bitmap texture;

	private final Joint rootJoint;
	private final int jointCount;

	private final Animator animator;

	public AniModel(Mesh model, Bitmap texture, Joint rootJoint, int jointCount)
    {
		this.model = model;
		this.texture = texture;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
		rootJoint.calcInverseBindTransform(new Matrix4f());
	}

	public Animator getAnimator() { return animator; }

	public Mesh getModel()
    {
		return model;
	}

	public Bitmap getTexture() {
		return texture;
	}

	public Joint getRootJoint() {
		return rootJoint;
	}

	public void doAnimation(Animation animation) {
		animator.doAnimation(animation);
	}

	public void update() {
		animator.update();
	}

	public Matrix4f[] getJointTransforms() {
		Matrix4f[] jointMatrices = new Matrix4f[jointCount];
		addJointsToArray(rootJoint, jointMatrices);
		return jointMatrices;
	}

	private void addJointsToArray(Joint headJoint, Matrix4f[] jointMatrices) {
		jointMatrices[headJoint.index] = headJoint.getAnimatedTransform();


		for (Joint childJoint : headJoint.children) {
			addJointsToArray(childJoint, jointMatrices);
		}
	}

}

