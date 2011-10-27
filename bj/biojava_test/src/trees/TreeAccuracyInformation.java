/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trees;

/**
 * Struktura do zwracania informacji o dokładności drzewa.
 * Wykorzytywana przez TreeBuilder.getTreeAccuracy() .
 * @author DanielWegner
 */
public class TreeAccuracyInformation 
{
    private double averageMatrixDistance;
    private double averageTreeDistance;
    private double averageErrorRate;

    public double getAverageErrorRate() {
        return averageErrorRate;
    }

    public void setAverageErrorRate(double averageErrorRate) {
        this.averageErrorRate = averageErrorRate;
    }

    public double getAverageMatrixDistance() {
        return averageMatrixDistance;
    }

    public void setAverageMatrixDistance(double averageMatrixDistance) {
        this.averageMatrixDistance = averageMatrixDistance;
    }

    public double getAverageTreeDistance() {
        return averageTreeDistance;
    }

    public void setAverageTreeDistance(double averageTreeDistance) {
        this.averageTreeDistance = averageTreeDistance;
    }
}
