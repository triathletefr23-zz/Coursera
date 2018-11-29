import edu.princeton.cs.algs4.Picture;

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

    private double[][] transposePixelsEnergy() {
        double[][] transposed = new double[this.picture().height()][this.picture.width()];

        for (int i = 0; i < this.picture.height(); i++) {
            for (int j = 0; j < this.picture.width(); j++) {
                transposed[i][j] = pixelsEnergy[j][i];
            }
        }

        return transposed;
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
        int color = picture.getRGB(x, y);
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int currX = x + i;
                int currY = y + j;

                if (isNonValidIndexes(currX, currY)) {
                    continue;
                }

                int tempColor = picture.getRGB(currX, currY);
                int tempRed = (tempColor >> 16) & 0xFF;
                int tempGreen = (tempColor >> 8) & 0xFF;
                int tempBlue = tempColor & 0xFF;
                energy += Math.pow(red - tempRed, 2) +
                        Math.pow(green - tempGreen, 2) +
                        Math.pow(blue - tempBlue, 2);
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
        int[] seam = new int[picture.width()];

        double min = Double.POSITIVE_INFINITY;
        int pos = 0;

        for (int j = 1; j < picture.height() - 1; j++) {
            if (min > pixelsEnergy[1][j]) {
                min = pixelsEnergy[1][j];
                pos = j;
            }
        }

        seam[0] = pos;
        seam[1] = pos;

        for (int j = 2; j < picture.width(); j++) {
            int currMinPos = pos - 1;
            for (int i = 0; i <= 1; i++) {
                if (pixelsEnergy[j][pos + i] < pixelsEnergy[j][currMinPos]) {
                    currMinPos = pos + i;
                }
            }
            seam[j] = currMinPos;
            pos = currMinPos;
        }

        seam[picture.width() - 1] = pos;

        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[picture.height()];

        double min = Double.POSITIVE_INFINITY;
        int pos = 0;

        // we have to found the pixel with min energy in 2nd row
        for (int i = 1; i < picture.width() - 1; i++) {
            if (min > pixelsEnergy[i][1]) {
                min = pixelsEnergy[i][1];
                pos = i;
            }
        }
        // set the same value in the first row pixel's index with min energy as the second
        seam[0] = pos;
        seam[1] = pos;

        // find the min energy between the nearest pixels from the pos in the previous row
        for (int i = 2; i < picture.height() - 1; i++) {
            int currMinPos = pos - 1;
            for (int j = 0; j <= 1; j++) {
                if (pixelsEnergy[pos + j][i] < pixelsEnergy[currMinPos][i]) {
                    currMinPos = pos + j;
                }
            }
            seam[i] = currMinPos;
            pos = currMinPos;
        }

        // set the same index as the previous row pixel's index
        seam[picture.height() - 1] = pos;

        return seam;
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