import edu.princeton.cs.algs4.Picture;
import org.junit.Assert;
import org.junit.Test;

public class SeamCarverShould {
    private SeamCarver seamCarver;
    private Picture examplePicture;
    private final static double DELTA = 0.001;
    private final static String PATH_EXAMPLE = "data\\example.png";
    private final static String PATH_OCEAN = "data\\HJoceanSmall.png";

    public SeamCarverShould() {
        examplePicture = new Picture(PATH_OCEAN);
        seamCarver = new SeamCarver(examplePicture);
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
        Assert.assertTrue(seamCarver.energy(1, 1) > 0);
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
}
