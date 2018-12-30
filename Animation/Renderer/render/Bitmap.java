package render;

import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bitmap
{
	private final int  _width;
	private final int  _height;

	private final byte _components[];

	public int GetWidth() { return _width; }
	public int GetHeight() { return _height; }

	public byte GetComponent(int index) { return _components[index]; }

	public Bitmap(int width, int height)
	{
		_width      = width;
		_height     = height;
		_components = new byte[width * height * 4];
	}

	public Bitmap(File fileName) throws IOException
	{
		int width = 0;
		int height = 0;
		byte[] components = null;

		BufferedImage image = ImageIO.read(fileName);

		width = image.getWidth();
		height = image.getHeight();

		int imgPixels[] = new int[width * height];
		image.getRGB(0, 0, width, height, imgPixels, 0, width);
		components = new byte[width * height * 4];

		for(int i = 0; i < width * height; i++)
		{
			int pixel = imgPixels[i];

			components[i * 4]     = (byte)((pixel >> 24) & 0xFF); // A
			components[i * 4 + 1] = (byte)((pixel      ) & 0xFF); // B
			components[i * 4 + 2] = (byte)((pixel >> 8 ) & 0xFF); // G
			components[i * 4 + 3] = (byte)((pixel >> 16) & 0xFF); // R
		}

		_width = width;
		_height = height;
		_components = components;
	}

	public void Clear(byte shade)
	{
		Arrays.fill(_components, shade);
	}


	public void CopyPixel(int destX, int destY, int srcX, int srcY, Bitmap src, float lightAmt)
	{
		int destIndex = (destX + destY * _width) * 4;
		int srcIndex = (srcX + srcY * src.GetWidth()) * 4;
		
		_components[destIndex    ] = (byte)((src.GetComponent(srcIndex) & 0xFF) * lightAmt);
		_components[destIndex + 1] = (byte)((src.GetComponent(srcIndex + 1) & 0xFF) * lightAmt);
		_components[destIndex + 2] = (byte)((src.GetComponent(srcIndex + 2) & 0xFF) * lightAmt);
		_components[destIndex + 3] = (byte)((src.GetComponent(srcIndex + 3) & 0xFF) * lightAmt);

	}

	public void CopyToByteArray(byte[] dest)
	{
		for(int i = 0; i < _width * _height; i++)
		{
			dest[i * 3 + 2] = _components[i * 4 + 1];
			dest[i * 3 + 1] = _components[i * 4 + 2];
			dest[i * 3    ] = _components[i * 4 + 3];
		}
	}
}
