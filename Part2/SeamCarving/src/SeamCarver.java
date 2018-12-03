import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    public final static double BORDER_ENERGY = 1000;
    private final static double SCALE = 100;
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
        pixelsEnergy = new double[height][width];
        calculatePixelsEnergy();
    }

    private void transposePixelsEnergy() {
        double[][] transposed = new double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                transposed[i][j] = pixelsEnergy[j][i];
            }
        }

        pixelsEnergy = transposed;
    }

    private void switchHeightAndWidth() {
        int copy = height;
        height = width;
        width = copy;
    }

    private void calculatePixelsEnergy() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelsEnergy[i][j] = calculateEnergyForPixel(i, j);
            }
        }
    }

    private void recalculateEnergyAfterRemovalOfVerticalSeam(int[] seam) {
        for (int i = 0; i < seam.length; i++) {
            pixelsEnergy[i][seam[i]] = calculateEnergyForPixel(i, seam[i]);
        }
    }

    private void recalculateEnergyAfterRemovalOfHorizontalSeam(int[] seam) {
        for (int i = 0; i < seam.length; i++) {
            pixelsEnergy[seam[i]][i] = calculateEnergyForPixel(seam[i], i);
        }
    }

    // row col
    private boolean isBorderPixel(int x, int y) {
        return x == 0 || x == height - 1 || y == 0 || y == width - 1;
    }

    private int getColor(int color, int colorNumber) {
        switch (colorNumber) {
            case 0: return (color >> 16) & 0xFF;
            case 1: return (color >> 8) & 0xFF;
            case 2: return color & 0xFF;
            default: return 0;
        }
    }

    // row col
    private double calculateEnergyForPixel(int row, int col) {
        if (isBorderPixel(row, col)) {
            return BORDER_ENERGY;
        }

        int colorUp = picture.getRGB(col, row - 1);
        int colorDown = picture.getRGB(col, row + 1);

        double energy = 0.0;
        energy += Math.pow(getColor(colorUp, 0) - getColor(colorDown, 0), 2) +
                Math.pow(getColor(colorUp, 1) - getColor(colorDown, 1), 2) +
                Math.pow(getColor(colorUp, 2) - getColor(colorDown, 2), 2);

        int colorLeft = picture.getRGB(col - 1, row);
        int colorRight = picture.getRGB(col + 1, row);
        energy += Math.pow(getColor(colorLeft, 0) - getColor(colorRight, 0), 2) +
                Math.pow(getColor(colorLeft, 1) - getColor(colorRight, 1), 2) +
                Math.pow(getColor(colorLeft, 2) - getColor(colorRight, 2), 2);

        return Math.round(Math.sqrt(energy) * SCALE) / SCALE;
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
        return col < 0 || col > width - 1 || row < 0 || row > height - 1;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (isNonValidIndexes(x, y)) {
            throw new IllegalArgumentException();
        }

        return pixelsEnergy[y][x];
    }

    private boolean checkIfLineHasSameValues(int rowNumber) {
        double value = pixelsEnergy[rowNumber][0];

        for (double elem: pixelsEnergy[rowNumber]) {
            if (value != elem) {
                return false;
            }
        }

        return true;
    }

    // topological order algorithm
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        double[][] distTo = new double[height][width];
        int[][] from = new int[height][width];

        double min = Double.POSITIVE_INFINITY;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 1) {
                    distTo[i][j] = pixelsEnergy[i][j];
                    from[i][j] = j;
                }

                if (i == height - 2) {
                    distTo[i + 1][j] = distTo[i][j];
                    from[i + 1][j] = from[i][j];
                }

                // relax edges from current pixel
                for (int step = -1; step <= 1; step++) {
                    if (isNonValidIndexes(j + step, i)) {
                        continue;
                    }

                    int currAboveIndex = j + step;

                    double currValue = distTo[i][j] + pixelsEnergy[i + 1][currAboveIndex];
                    if (distTo[i + 1][currAboveIndex] == 0 || currValue < distTo[i + 1][currAboveIndex]) {
                        distTo[i + 1][currAboveIndex] = currValue;
                        from[i + 1][currAboveIndex] = j;
                    }
                }
            }
        }

        // find min value in last row
        int minIndex = 0;
        for (int i = 0; i < width; i++) {
            if (distTo[height - 1][i] < min) {
                min = distTo[height - 1][i];
                minIndex = i;
            }
        }

        // construct shortest path
        for (int i = height - 2; i >= 1; i--) {
            seam[i] = minIndex;
            minIndex = from[i][minIndex];
        }
        seam[0] = seam[1];
        seam[height - 1] = seam[height - 2];

        return seam;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] backupEnergy = pixelsEnergy;

        transposePixelsEnergy();

        // height <-> width
        switchHeightAndWidth();

        int[] seam = findVerticalSeam();

        pixelsEnergy = backupEnergy;

        // width <-> height
        switchHeightAndWidth();

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

        Picture newPicture = new Picture(this.picture.width(), this.picture.height() - 1);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (j < seam[i]) {
                    newPicture.setRGB(i, j, this.picture.getRGB(i, j));
                    continue;
                }
                int nextRightPixelColor = this.picture.getRGB(i, j + 1);
                newPicture.setRGB(i, j, nextRightPixelColor);
                pixelsEnergy[j][i] = pixelsEnergy[j + 1][i];
            }
        }

        height--;
        this.picture = new Picture(newPicture);
        recalculateEnergyAfterRemovalOfHorizontalSeam(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || height <= 1 || seam.length > height) {
            throw new IllegalArgumentException();
        }

        checkEveryPixelOfSeam(seam);

        Picture newPicture = new Picture(this.picture.width() - 1, this.picture.height());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                if (j < seam[i]) {
                    newPicture.setRGB(j, i, this.picture.getRGB(j, i));
                    continue;
                }
                int nextRightPixelColor = this.picture.getRGB(j + 1, i);
                newPicture.setRGB(j, i, nextRightPixelColor);
                pixelsEnergy[i][j] = pixelsEnergy[i][j + 1];
            }
        }
        width--;

        this.picture = new Picture(newPicture);
        recalculateEnergyAfterRemovalOfVerticalSeam(seam);
    }
}