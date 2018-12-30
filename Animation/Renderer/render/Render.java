package render;

import structures.Matrix4f;
import structures.MVertex;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Render extends Bitmap
{
	private float[] zBuffer;

	public Render(int width, int height)
	{
		super(width, height);
		zBuffer = new float[width * height];
	}

	public void ClearDepthBuffer()
	{
		for(int i = 0; i < zBuffer.length; i++)
		{
			zBuffer[i] = Float.MAX_VALUE;
		}
	}

	public void DrawTriangle(MVertex v1, MVertex v2, MVertex v3, Bitmap texture)
	{
		if(v1.IsInsideViewFrustum() && v2.IsInsideViewFrustum() && v3.IsInsideViewFrustum())
		{
			FillTriangle(v1, v2, v3, texture);
			return;
		}

		List<MVertex> vertices = new ArrayList<>();
		List<MVertex> auxillaryList = new ArrayList<>();
		
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);

		if(ClipPolygonAxis(vertices, auxillaryList, 0) &&
				ClipPolygonAxis(vertices, auxillaryList, 1) &&
				ClipPolygonAxis(vertices, auxillaryList, 2))
		{
			MVertex initialMVertex = vertices.get(0);

			for(int i = 1; i < vertices.size() - 1; i++)
			{
				FillTriangle(initialMVertex, vertices.get(i), vertices.get(i + 1), texture);
			}
		}
	}

	private boolean ClipPolygonAxis(List<MVertex> vertices, List<MVertex> auxillaryList,
									int componentIndex)
	{
		ClipPolygonComponent(vertices, componentIndex, 1.0f, auxillaryList);
		vertices.clear();

		if(auxillaryList.isEmpty())
		{
			return false;
		}

		ClipPolygonComponent(auxillaryList, componentIndex, -1.0f, vertices);
		auxillaryList.clear();

		return !vertices.isEmpty();
	}

	private void ClipPolygonComponent(List<MVertex> vertices, int componentIndex,
									  float componentFactor, List<MVertex> result)
	{
		MVertex previousMVertex = vertices.get(vertices.size() - 1);
		float previousComponent = previousMVertex.Get(componentIndex) * componentFactor;
		boolean previousInside = previousComponent <= previousMVertex.GetPosition().GetW();

		Iterator<MVertex> it = vertices.iterator();
		while(it.hasNext())
		{
			MVertex currentMVertex = it.next();
			float currentComponent = currentMVertex.Get(componentIndex) * componentFactor;
			boolean currentInside = currentComponent <= currentMVertex.GetPosition().GetW();

			if(currentInside ^ previousInside)
			{
				float lerpAmt = (previousMVertex.GetPosition().GetW() - previousComponent) /
					((previousMVertex.GetPosition().GetW() - previousComponent) -
					 (currentMVertex.GetPosition().GetW() - currentComponent));

				result.add(previousMVertex.Lerp(currentMVertex, lerpAmt));
			}

			if(currentInside)
			{
				result.add(currentMVertex);
			}

			previousMVertex = currentMVertex;
			previousComponent = currentComponent;
			previousInside = currentInside;
		}
	}

	private void FillTriangle(MVertex v1, MVertex v2, MVertex v3, Bitmap texture)
	{
		Matrix4f screenSpaceTransform = 
				new Matrix4f().InitScreenSpaceTransform(GetWidth()/2, GetHeight()/2);
		Matrix4f identity = new Matrix4f().InitIdentity();
		MVertex minYVert = v1.Transform(screenSpaceTransform, identity).PerspectiveDivide();
		MVertex midYVert = v2.Transform(screenSpaceTransform, identity).PerspectiveDivide();
		MVertex maxYVert = v3.Transform(screenSpaceTransform, identity).PerspectiveDivide();

		if(minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0)
		{
			return;
		}

		if(maxYVert.GetY() < midYVert.GetY())
		{
			MVertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}

		if(midYVert.GetY() < minYVert.GetY())
		{
			MVertex temp = midYVert;
			midYVert = minYVert;
			minYVert = temp;
		}

		if(maxYVert.GetY() < midYVert.GetY())
		{
			MVertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}

		ScanTriangle(minYVert, midYVert, maxYVert, 
				minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0,
				texture);
	}

	private void ScanTriangle(MVertex minYVert, MVertex midYVert,
							  MVertex maxYVert, boolean handedness, Bitmap texture)
	{
		Interpolation interpolation = new Interpolation(minYVert, midYVert, maxYVert);
		Edge topToBottom    = new Edge(interpolation, minYVert, maxYVert, 0);
		Edge topToMiddle    = new Edge(interpolation, minYVert, midYVert, 0);
		Edge middleToBottom = new Edge(interpolation, midYVert, maxYVert, 1);

		ScanEdges(interpolation, topToBottom, topToMiddle, handedness, texture);
		ScanEdges(interpolation, topToBottom, middleToBottom, handedness, texture);
	}

	private void ScanEdges(Interpolation interpolation, Edge a, Edge b, boolean handedness, Bitmap texture)
	{
		Edge left = a;
		Edge right = b;
		if(handedness)
		{
			Edge temp = left;
			left = right;
			right = temp;
		}

		int yStart = b.GetYStart();
		int yEnd   = b.GetYEnd();
		for(int j = yStart; j < yEnd; j++)
		{
			DrawScanLine(interpolation, left, right, j, texture);
			left.Step();
			right.Step();
		}
	}

	private void DrawScanLine(Interpolation interpolation, Edge left, Edge right, int j, Bitmap texture)
	{
		int xMin = (int)Math.ceil(left.GetX());
		int xMax = (int)Math.ceil(right.GetX());
		float xPrestep = xMin - left.GetX();

		float texCoordXXStep = interpolation.GetTexCoordXXStep();
		float texCoordYXStep = interpolation.GetTexCoordYXStep();
		float oneOverZXStep = interpolation.GetOneOverZXStep();
		float depthXStep = interpolation.GetDepthXStep();
		float lightAmtXStep = interpolation.GetLightAmtXStep();

		float texCoordX = left.GetTexCoordX() + texCoordXXStep * xPrestep;
		float texCoordY = left.GetTexCoordY() + texCoordYXStep * xPrestep;
		float oneOverZ = left.GetOneOverZ() + oneOverZXStep * xPrestep;
		float depth = left.GetDepth() + depthXStep * xPrestep;
		float lightAmt = left.GetLightAmt() + lightAmtXStep * xPrestep;

		for(int i = xMin; i < xMax; i++)
		{
			int index = i + j * GetWidth();
			if(depth < zBuffer[index])
			{
				zBuffer[index] = depth;
				float z = 1.0f/oneOverZ;
				int srcX = (int)((texCoordX * z) * (float)(texture.GetWidth() - 1) + 0.5f);
				int srcY = (int)((texCoordY * z) * (float)(texture.GetHeight() - 1) + 0.5f);

				CopyPixel(i, j, srcX, srcY, texture, lightAmt);
			}

			oneOverZ += oneOverZXStep;
			texCoordX += texCoordXXStep;
			texCoordY += texCoordYXStep;
			depth += depthXStep;
			lightAmt += lightAmtXStep;
		}
	}
}
