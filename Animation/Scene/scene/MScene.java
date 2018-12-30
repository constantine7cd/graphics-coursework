package scene;

import org.lwjgl.util.vector.Vector3f;

public class MScene {

	private final Camera camera;
	private render.Display display;

	private Vector3f lightDirection = new Vector3f(0, -1, 0);

	public MScene(render.Display display, Camera cam) {
		this.camera = cam;
		this.display = display;
	}

	public Camera getCamera() {
		return camera;
	}
}
