package render;

import structures.MVertex;

public class Edge
{
	private float x;
	private float xStep;
	private int yStart;
	private int yEnd;
	private float texCoordX;
	private float texCoordXStep;
	private float texCoordY;
	private float texCoordYStep;
	private float oneOverZ;
	private float oneOverZStep;
	private float depth;
	private float depthStep;
	private float lightAmt;
	private float lightAmtStep;

	public float GetX() { return x; }
	public int GetYStart() { return yStart; }
	public int GetYEnd() { return yEnd; }
	public float GetTexCoordX() { return texCoordX; }
	public float GetTexCoordY() { return texCoordY; }
	public float GetOneOverZ() { return oneOverZ; }
	public float GetDepth() { return depth; }
	public float GetLightAmt() { return lightAmt; }

	public Edge(Interpolation interpolation, MVertex minYVert, MVertex maxYVert, int minYVertIndex)
	{
		yStart = (int)Math.ceil(minYVert.GetY());
		yEnd = (int)Math.ceil(maxYVert.GetY());

		float yDist = maxYVert.GetY() - minYVert.GetY();
		float xDist = maxYVert.GetX() - minYVert.GetX();

		float yPrestep = yStart - minYVert.GetY();
		xStep = (float)xDist/(float)yDist;
		x = minYVert.GetX() + yPrestep * xStep;
		float xPrestep = x - minYVert.GetX();

		texCoordX = interpolation.GetTexCoordX(minYVertIndex) +
			interpolation.GetTexCoordXXStep() * xPrestep +
			interpolation.GetTexCoordXYStep() * yPrestep;
		texCoordXStep = interpolation.GetTexCoordXYStep() + interpolation.GetTexCoordXXStep() * xStep;

		texCoordY = interpolation.GetTexCoordY(minYVertIndex) +
			interpolation.GetTexCoordYXStep() * xPrestep +
			interpolation.GetTexCoordYYStep() * yPrestep;
		texCoordYStep = interpolation.GetTexCoordYYStep() + interpolation.GetTexCoordYXStep() * xStep;

		oneOverZ = interpolation.GetOneOverZ(minYVertIndex) +
			interpolation.GetOneOverZXStep() * xPrestep +
			interpolation.GetOneOverZYStep() * yPrestep;
		oneOverZStep = interpolation.GetOneOverZYStep() + interpolation.GetOneOverZXStep() * xStep;

		depth = interpolation.GetDepth(minYVertIndex) +
			interpolation.GetDepthXStep() * xPrestep +
			interpolation.GetDepthYStep() * yPrestep;
		depthStep = interpolation.GetDepthYStep() + interpolation.GetDepthXStep() * xStep;

		lightAmt = interpolation.GetLightAmt(minYVertIndex) +
			interpolation.GetLightAmtXStep() * xPrestep +
			interpolation.GetLightAmtYStep() * yPrestep;
		lightAmtStep = interpolation.GetLightAmtYStep() + interpolation.GetLightAmtXStep() * xStep;
	}

	public void Step()
	{
		x += xStep;
		texCoordX += texCoordXStep;
		texCoordY += texCoordYStep;
		oneOverZ += oneOverZStep;
		depth += depthStep;
		lightAmt += lightAmtStep;
	}
}
