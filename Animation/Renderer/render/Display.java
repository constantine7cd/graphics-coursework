package render;
import java.nio.ByteBuffer;

import javafx.scene.image.PixelFormat;
import javafx.scene.canvas.Canvas;

public class Display
{
	private final Render bitmap;

	private Canvas canvas;

	private byte[] buffer;

	private int height;
	private int width;

	public Display(int width, int height)
	{
		bitmap = new Render(width, height);
		buffer = new byte[width * height * 3];

		this.width = width;
		this.height = height;

        bitmap.Clear((byte)0xFF);
        bitmap.ClearDepthBuffer();

	}

	public void update()
	{
		bitmap.CopyToByteArray(buffer);
		PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
		canvas.getGraphicsContext2D().getPixelWriter().setPixels(0, 0, width, height,
				pixelFormat, buffer,0, 3 * width);

	}

	public Render getBitmap()
    {
        return bitmap;
    }

    public Canvas getCanvas() { return canvas; }

    public void setCanvas(Canvas canvas) { this.canvas = canvas; }


}
