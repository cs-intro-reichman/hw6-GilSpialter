import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		System.out.println("REGULAR PRINT:"); //--------------------------------------TEST---TEST----TEST----TEST-----------
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		System.out.println();
		System.out.println("HORIZONTAL PRINT:"); //------------------------------------TEST---TEST----TEST----TEST-------
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
		System.out.println();
		System.out.println("VERTICAL PRINT:"); //------------------------------------TEST---TEST----TEST----TEST-------------
		image = flippedVertically(tinypic);
		print(image);

//		System.out.println();
//		System.out.println("LUMINANCE:"); //-----------------------------------------TEST---TEST----TEST----TEST---------------
//		for (int i = 0; i < image.length; i++)
//			for (int j = 0; j < image [0].length; j++)
//				image[i][j] = luminance(tinypic[i][j]);
//		print(image);

//		System.out.println();
//		System.out.println("GRAYSCALE PRINT:"); //-----------------------------------TEST---TEST----TEST----TEST-----------------
//		image = grayScaled(tinypic);
//		print(image);

		System.out.println();
		System.out.println("SCALED PRINT:"); //----------------------------------------TEST---TEST----TEST----TEST------------
		image = scaled(tinypic, 2, 9);
		print(image);

		System.out.println();
		System.out.println("BLENDED PRINT:"); //----------------------------------------TEST---TEST----TEST----TEST------------
		Color c1 = new Color(100, 40, 100);
		Color c2 = new Color(200, 20, 40);
		print(blend(c1, c2, 0.25));

	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		int r, g, b;
		// Reads the RGB values from the file into the image array.
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				r = in.readInt();
				g = in.readInt();
				b = in.readInt();
				image[i][j] = new Color(r, g, b);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
		System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
      	System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for (int row = 0; row < image.length; row ++) {
			for (int  col = 0; col < image[0].length; col++) {
				print(image[row][col]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] flipped = new Color[ image.length ][ image[0].length ];
		for (int row = 0; row < image.length; row ++) {
			for (int  col = 0; col < image[0].length; col++) {
				flipped[row][col] = image[row][image[0].length - 1 - col]; 
			}
		}
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] flipped = new Color[ image.length ][ image[0].length ];

		for (int row = 0; row < image.length; row ++) {
			for (int  col = 0; col < image[0].length; col++) {
				flipped[row][col] = image[image.length - 1 - row][col]; 
			}
		}
		return flipped;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		double r = (double) 0.299 * pixel.getRed();
		double g = (double) 0.587 * pixel.getGreen();
		double b = (double) 0.114 * pixel.getBlue();
		int lum = (int) (r + g + b);
		Color c = new Color(lum, lum, lum);
		return c;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] gs = new Color[image.length][image[0].length];
		for (int i = 0; i < gs.length; i++) {
			for (int j = 0; j < gs[0].length; j++) {
				gs[i][j] = luminance(image[i][j]);
			}
		}
		return gs;
	}
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scaledPic = new Color[height][width];	
		int h0 = image.length;
		int w0 = image[0].length;
		double heightRatio = (double) h0 / height;
		double widthRatio = (double) w0 / width;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				scaledPic[i][j] = image[(int)(i * heightRatio)][(int)(j * widthRatio)];
			}
		}
		return scaledPic;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int r = (int) ((alpha * c1.getRed()) + ((1 - alpha) * c2.getRed()));
		int g = (int) ((alpha * c1.getGreen()) + (1 - alpha) * c2.getGreen());
		int b = (int) ((alpha * c1.getBlue()) + (1 - alpha) * c2.getBlue());
		Color c = new Color(r, g, b);
		return c;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int width = image1[0].length;
		int height = image1.length;
		
		if (alpha == 0) 	{ return image2; }	

		if ((image2.length != height) || (image2[0].length != width))	//if they are different sizes
			{ image2 = scaled(image2, width, height); }					//match image2 to image 1

		if (alpha == 1) 	{ return image1; }

		
		Color blended[][] = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int width = source[0].length;
		int height = source.length;
		target = scaled(target, width, height);
		double alpha;
		display(source);
		for (int i = 0; i <= n; i++) {
			alpha = (double) (n - i) / n;
			display(blend(source, target, alpha));
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}