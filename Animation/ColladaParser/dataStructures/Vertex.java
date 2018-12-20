package dataStructures;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class Vertex {
	
	private static final int NO_INDEX = -1;
	
	private Vector3f position;
	private int textureIndex = NO_INDEX;
	private int normalIndex = NO_INDEX;
	private int index;
	private float length;
	
	
	private VertexSkinData weightsData;
	
	public Vertex(int index,Vector3f position, VertexSkinData weightsData){
		this.index = index;
		this.weightsData = weightsData;
		this.position = position;
		this.length = position.length();
	}
	
	public VertexSkinData getWeightsData(){
		return weightsData;
	}
	

	public float getLength(){
		return length;
	}
	
	public boolean isSet(){
		return textureIndex!=NO_INDEX && normalIndex!=NO_INDEX;
	}

	
	public void setTextureIndex(int textureIndex){
		this.textureIndex = textureIndex;
	}
	
	public void setNormalIndex(int normalIndex){
		this.normalIndex = normalIndex;
	}

	public Vector3f getPosition() {
		return position;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public int getNormalIndex() {
		return normalIndex;
	}

}
