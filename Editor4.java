import java.awt.Color;

// Morphing into a greay scale of itself

public class Editor4 {

	public static void main (String[] args) {
		String source = args[0];				
		int n = Integer.parseInt(args[1]);
		Color[][] sourceImage = Runigram.read(source);
		sourceImage = Runigram.scaled(sourceImage, 1000, 1000);
		Color[][] targetImage = Runigram.grayScaled(sourceImage);
		Runigram.setCanvas(sourceImage);
		Runigram.morph(sourceImage, targetImage, n);
	}
}