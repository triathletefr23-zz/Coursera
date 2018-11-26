import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    public final static double BORDER_ENERGY = 1000;
    private Picture picture;
    private double[][] pixelsEnergy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.picture = new Picture(picture);
        pixelsEnergy = new double[this.picture.width()][this.picture.height()];
        calculatePixelsEnergy();
    }

    private void calculatePixelsEnergy() {
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                pixelsEnergy[i][j] = calculateEnergyForPixel(i, j);
            }
        }
    }

    private double calculateEnergyForPixel(int x, int y) {
        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1) {
            return BORDER_ENERGY;
        }

        double energy = 0.0;
        Color color = picture.get(x, y);
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int currX = x + i;
                int currY = y + j;

                if (isNonValidIndexes(currX, currY)) {
                    continue;
                }

                Color tempPixelColor = picture.get(currX, currY);
                energy += Math.pow(color.getRed() - tempPixelColor.getRed(), 2) +
                        Math.pow(color.getGreen() - tempPixelColor.getGreen(), 2) +
                        Math.pow(color.getBlue() - tempPixelColor.getBlue(), 2);
            }
        }

        return energy;
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    private boolean isNonValidIndexes(int col, int row) {
        return col < 0 || col > picture.width() - 1 || row < 0 || row > picture.height() - 1;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (isNonValidIndexes(x, y)) {
            throw new IllegalArgumentException();
        }

        return pixelsEnergy[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height()];

        double min = Double.POSITIVE_INFINITY;
        int pos = 0;
        for (int i = 0; i < width(); i++) {

        }


        for (int j = 1; j < height(); j++) {
            for (int i = 0; i < width(); i++) {

            }
        }

        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || width() <= 1 || seam.length > width()) {
            throw new IllegalArgumentException();
        }

        checkEveryPixelOfSeam(seam);
    }

    private void checkEveryPixelOfSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i+1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || height() <= 1 || seam.length > height()) {
            throw new IllegalArgumentException();
        }

        checkEveryPixelOfSeam(seam);
    }
}