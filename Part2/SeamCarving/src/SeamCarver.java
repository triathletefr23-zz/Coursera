import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    public final static double BORDER_ENERGY = 1000;
    private Picture picture;
    private double[][] pixelsEnergy;
    private int height;
    private int width;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        this.picture = new Picture(picture);
        height = this.picture.height();
        width = this.picture.width();
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

    private boolean isBorderPixel(int x, int y) {
        return x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1;
    }

    private int getColor(int color, int colorNumber) {
        switch (colorNumber) {
            case 0: return (color >> 16) & 0xFF;
            case 1: return (color >> 8) & 0xFF;
            case 2: return color & 0xFF;
            default: return 0;
        }
    }

    private double calculateEnergyForPixel(int x, int y) {
        if (isBorderPixel(x, y)) {
            return Math.pow(BORDER_ENERGY, 2);
//            return BORDER_ENERGY;
        }

        int colorUp = picture.getRGB(x - 1, y);
        int colorDown = picture.getRGB(x + 1, y);

        double energy = 0.0;
        energy += Math.pow(getColor(colorUp, 0) - getColor(colorDown, 0), 2) +
                Math.pow(getColor(colorUp, 1) - getColor(colorDown, 1), 2) +
                Math.pow(getColor(colorUp, 2) - getColor(colorDown, 2), 2);

        int colorLeft = picture.getRGB(x, y - 1);
        int colorRight = picture.getRGB(x, y + 1);
        energy += Math.pow(getColor(colorLeft, 0) - getColor(colorRight, 0), 2) +
                Math.pow(getColor(colorLeft, 1) - getColor(colorRight, 1), 2) +
                Math.pow(getColor(colorLeft, 2) - getColor(colorRight, 2), 2);

        return energy;
//        return Math.sqrt(energy);
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
        int[] seam = new int[width];

        double min = Double.POSITIVE_INFINITY;
        int pos = 0;

        for (int j = 1; j < height - 1; j++) {
            if (min > pixelsEnergy[1][j]) {
                min = pixelsEnergy[1][j];
                pos = j;
            }
        }

        seam[0] = getBorderPosition(height, pos);;
        seam[1] = pos;

        for (int j = 2; j < width - 1; j++) {
            int currMinPos = pos - 1;
            for (int i = 0; i <= 1; i++) {
                if (pixelsEnergy[j][pos + i] < pixelsEnergy[j][currMinPos]) {
                    currMinPos = pos + i;
                }
            }
            seam[j] = currMinPos;
            pos = currMinPos;
        }

        seam[width - 1] = getBorderPosition(height, pos);

        return seam;
    }

    // adjust the first and last border indexes to be near the middle index
    private int getBorderPosition(int length, int pos) {
        double mid = (double) length / 2;
        double diff = mid - pos;
        if (diff > 1) return pos + 1;
        if (diff <= -1) return pos - 1;
        return pos;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height];

        double min = Double.POSITIVE_INFINITY;
        int pos = 0;

        // we have to found the pixel with min energy in 2nd row
        for (int i = 1; i < width - 1; i++) {
            if (min > pixelsEnergy[i][1]) {
                min = pixelsEnergy[i][1];
                pos = i;
            }
        }
        // set the same value in the first row pixel's index with min energy as the second
        seam[0] = getBorderPosition(width, pos);
        seam[1] = pos;

        // find the min energy between the nearest pixels from the pos in the previous row
        for (int i = 2; i < height - 1; i++) {
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
        seam[height - 1] = getBorderPosition(width, pos);

        return seam;
    }

    private void checkEveryPixelOfSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i+1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || width <= 1 || seam.length > width) {
            throw new IllegalArgumentException();
        }

        checkEveryPixelOfSeam(seam);

        for (int i = 0; i < width; i++) {
            if (height - 1 - seam[i] >= 0)
                System.arraycopy(pixelsEnergy[i], seam[i] + 1, pixelsEnergy[i], seam[i], height - 1 - seam[i]);
        }

        height--;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || height <= 1 || seam.length > height) {
            throw new IllegalArgumentException();
        }

        checkEveryPixelOfSeam(seam);

        for (int i = 0; i < height; i++) {
            for (int j = seam[i]; j < width - 1; j++) {
                pixelsEnergy[j][i] = pixelsEnergy[j + 1][i];
            }
        }

        width--;
    }
}