import edu.princeton.cs.algs4.Picture;
import org.junit.Assert;
import org.junit.Test;

public class SeamCarverShould {
    private SeamCarver seamCarver;
    private Picture examplePicture;
    private final static double DELTA = 0.001;
    private final static String PATH_3x4 = "data\\3x4.png";
    private final static String PATH_6x5 = "data\\6x5.png";
    private final static String PATH_5x6 = "data\\5x6.png";
    private final static String PATH_EXAMPLE = "data\\example.png";
    private final static String PATH_OCEAN = "data\\HJoceanSmall.png";

    public SeamCarverShould() {
        examplePicture = new Picture(PATH_3x4);
        seamCarver = new SeamCarver(examplePicture);
    }

    private void init(String picture_path) {
        examplePicture = new Picture(picture_path);
        seamCarver = new SeamCarver(examplePicture);
    }

    private boolean checkIfArrayHasPercentageOfSameElements(int[] array, int[] seam) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != seam[i]) {
                count++;
            }
        }

        return count <= 2;
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfArgumentOfConstructorIsNull() {
        seamCarver = new SeamCarver(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIllegalCoordinateX() {
        seamCarver.energy(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIllegalCoordinatesY() {
        seamCarver.energy(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIllegalCoordinateXFromAnotherBound() {
        seamCarver.energy(examplePicture.width(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIllegalCoordinateYFromAnotherBound() {
        seamCarver.energy(1, examplePicture.height());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfHorizontalSeamToRemoveIsNull() {
        seamCarver.removeHorizontalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfVerticalSeamToRemoveIsNull() {
        seamCarver.removeVerticalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfLengthOfVerticalSeamIsMoreThanHeight() {
        var seam = new int[this.examplePicture.height() + 1];
        seamCarver.removeVerticalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfLengthOfHorizontalSeamIsMoreThanWidth() {
        var seam = new int[this.examplePicture.width() + 1];
        seamCarver.removeHorizontalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfThereArePixelsWithTheStepInTwoPixels() {
        var seam = new int[this.examplePicture.height()];
        seam[0] = 0;
        seam[1] = 1;
        seam[2] = 3;
        seamCarver.removeVerticalSeam(seam);
    }

    @Test
    public void ReturnAThousandIfPixelIsOnTheBorder() {
        Assert.assertEquals(SeamCarver.BORDER_ENERGY, seamCarver.energy(0, 1), DELTA);
        Assert.assertEquals(SeamCarver.BORDER_ENERGY, seamCarver.energy(1, 0), DELTA);
        Assert.assertEquals(SeamCarver.BORDER_ENERGY, seamCarver.energy(1, seamCarver.height() - 1), DELTA);
        Assert.assertEquals(SeamCarver.BORDER_ENERGY, seamCarver.energy(seamCarver.width() - 1, 1), DELTA);
    }

    @Test
    public void ReturnEnergyOfNormalPixel() {
        init(PATH_OCEAN);
        Assert.assertTrue(seamCarver.energy(1, 1) > 0);
    }

    @Test
    public void ReturnHeightOf3x4Picture() {
        init(PATH_3x4);
        Assert.assertEquals(4, seamCarver.height());
    }

    @Test
    public void ReturnWidthOf3x4Picture() {
        init(PATH_3x4);
        Assert.assertEquals(3, seamCarver.width());
    }

    @Test
    public void ReturnHeightOf5x6icture() {
        init(PATH_5x6);
        Assert.assertEquals(5, seamCarver.width());
    }

    @Test
    public void ReturnWidthOf5x6icture() {
        init(PATH_5x6);
        Assert.assertEquals(6, seamCarver.height());
    }


    @Test
    public void ReturnVerticalSeamFor6x5Picture() {
        init(PATH_6x5);
        var verticalSeam = seamCarver.findVerticalSeam();
        var expected = new int[] { 3, 4, 3, 2, 2 };
        Assert.assertTrue(checkIfArrayHasPercentageOfSameElements(verticalSeam, expected));
    }

    @Test
    public void ReturnHorizontalFor6x5Picture() {
        init(PATH_6x5);
        var horizontalSeam = seamCarver.findHorizontalSeam();
        var expected = new int[] { 2, 2, 1, 2, 1, 2 };
        Assert.assertTrue(checkIfArrayHasPercentageOfSameElements(horizontalSeam, expected));
    }

    @Test
    public void ReturnVerticalSeamWithHeightOfPicture() {
        var verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertEquals(seamCarver.height(), verticalSeam.length);
    }

    @Test
    public void ReturnVerticalSeamWithWidthOfPicture() {
        var horizontalSeam = seamCarver.findHorizontalSeam();
        Assert.assertEquals(seamCarver.width(), horizontalSeam.length);
    }

    @Test
    public void ReturnVerticalSeamFor3x4Picture() {
        init(PATH_3x4);
        var verticalSeam = seamCarver.findVerticalSeam();
        var expected = new int[] { 1, 1, 1, 1 };
        Assert.assertTrue(checkIfArrayHasPercentageOfSameElements(expected, verticalSeam));
    }

    @Test
    public void ReturnHorizontalSeamFor5x6Picture() {
        init(PATH_5x6);
        var horizontalSeam = seamCarver.findHorizontalSeam();
        var expected = new int[] { 2, 3, 2, 3, 2 };
        Assert.assertTrue(checkIfArrayHasPercentageOfSameElements(expected, horizontalSeam));
    }

    @Test
    public void CallRemoveVerticalSeam() {
        init(PATH_5x6);
        var verticalSeam = seamCarver.findVerticalSeam();
        seamCarver.removeVerticalSeam(verticalSeam);
    }

    @Test
    public void CallRemoveHorizontalSeam() {
        init(PATH_5x6);
        var horizontalSeam = seamCarver.findHorizontalSeam();
        seamCarver.removeHorizontalSeam(horizontalSeam);
    }
}
