import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    public final static double BORDER_ENERGY = 1000;
    private Picture picture;
    private double[][] pixelsEnergy;
    private boolean isEnergyCounted = false;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.picture = new Picture(picture);
        pixelsEnergy = new double[this.picture.height()][this.picture.width()];
        calculatePixelsEnergy();
        isEnergyCounted = true;
    }

    private void calculatePixelsEnergy() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                pixelsEnergy[i][j] = energy(i, j);
            }
        }
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

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IllegalArgumentException();
        }

        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return BORDER_ENERGY;
        }

        if (isEnergyCounted) {
            return pixelsEnergy[x][y];
        }

        Color color = picture.get(x, y);
        double energy = 0.0;
        final int[] deltas = new int[]{ -1, 1 };
        for (int i: deltas) {
            for (int j: deltas) {
                if (energy(x + i, y + j) != BORDER_ENERGY) {
                    Color tempPixelColor = picture.get(x + i, y + j);
                    energy += Math.pow(color.getRed() - tempPixelColor.getRed(), 2) +
                            Math.pow(color.getGreen() - tempPixelColor.getGreen(), 2) +
                            Math.pow(color.getBlue() - tempPixelColor.getBlue(), 2);
                }
            }
        }

        return energy;
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