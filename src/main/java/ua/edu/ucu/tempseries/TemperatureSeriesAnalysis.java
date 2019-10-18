package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private double[] tempr;
    private int len;
    private int absZERO = -273;
    private int LEN = 10;
    private int specLEN = 3;

    public TemperatureSeriesAnalysis() {

        this.tempr = new double[LEN];
        this.len = LEN;
    }

    public double[] getTempr() {

        return tempr;
    }

    public int getLen() {
        return len;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.len = temperatureSeries.length;
        this.tempr = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
    }

    public void checkTemp(double[] arr) {
        for (double n : arr) {
            if (n < absZERO) {
                throw new InputMismatchException();
            }
        }
    }

    public double average() {
        if (len > 0) {
            checkTemp(tempr);
            double sum = 0;
            for (int i = 0; i < this.len; i++) {
                sum += this.tempr[i];
            }
            return sum / this.len;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double deviation() {
        if (len > 0) {
            checkTemp(tempr);
            double av = this.average();
            double sum = 0;
            for (int i = 0; i < this.len; i++) {
                double temp = (this.tempr[i] - av) * (this.tempr[i] - av);
                sum += temp;
            }
            return Math.sqrt(sum / len);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double min() {
        if (len > 0) {
            checkTemp(tempr);
            double min;
            min = tempr[0];
            for (int i = 1; i < len; i++) {
                if (min > tempr[i]) {
                    min = tempr[i];
                }
            }
            return min;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double max() {
        if (len > 0) {
            checkTemp(tempr);
            double max;
            max = tempr[0];
            for (int i = 1; i < len; i++) {
                if (max < tempr[i]) {
                    max = tempr[i];
                }
            }
            return max;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double findTempClosestToZero() {
        if (len > 0) {
            checkTemp(tempr);
            return findTempClosestToValue(0);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double findTempClosestToValue(double tempValue) {
        if (len > 0) {
            checkTemp(tempr);
            double dist = Math.abs(tempValue + tempr[0]);
            double closest = tempr[0];
            for (int i = 0; i < len; i++) {
                if (tempr[i] == tempValue) {
                    return tempValue;
                }
                double temp = Math.abs(tempValue - tempr[i]);
                if (dist > temp) {
                    dist = temp;
                    closest = tempr[i];
                } else if (dist == temp) {
                    closest = Math.abs(tempr[i]);
                }
            }
            return closest;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double[] findTempsLessThen(double tempValue) {
        int counter = 0;
        if (len > 0) {
            checkTemp(tempr);
            double[] less = new double[specLEN];
            System.out.println(less.length);
            for (int i = 0; i < len; i++) {
                if (tempr[i] < tempValue) {
                    if (counter >= less.length) {
                        double[] temp = Arrays.copyOf(less, less.length);
                        less = new double[counter * 2];
                        less = Arrays.copyOf(temp, temp.length);
                    }
                    less[counter] = tempr[i];
                    counter++;
                }
            }
            if (counter > 0) {
                return less;
            }
        }
        return null;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int counter = 0;
        if (len > 0) {
            checkTemp(tempr);
            double[] great = new double[specLEN];
            for (int i = 0; i < len; i++) {
                if (tempr[i] > tempValue) {
                    if (counter >= great.length) {
                        double[] temp = Arrays.copyOf(great, great.length);
                        great = new double[counter * 2];
                        great = Arrays.copyOf(temp, temp.length);
                    }
                    great[counter] = tempr[i];
                    counter++;
                }
            }
            if (counter > 0) {
                return great;
            }
        }
        return null;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (len > 0) {
            checkTemp(tempr);
            TempSummaryStatistics fin = new TempSummaryStatistics(
                    average(), deviation(), min(), max()
            );
            return fin;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double addTemps(double... temps) {
        if (len > 0) {
            int counter = 0;
            for (int i = 0; i < len; i++) {
                if (tempr[i] != Double.MIN_VALUE) {
                    counter++;
                }
            }
            for (int j = 0; j < temps.length; j++) {
                if (counter >= len) {
                    double[] temp = Arrays.copyOf(tempr, tempr.length);
                    int tmpLen = tempr.length;
                    tempr = new double[counter * 2];
                    len = tempr.length;
                    for (int k = 0; k < tmpLen; k++) {
                        tempr[k] = temp[k];
                    }
                }
                tempr[counter] = temps[j];
                counter++;
            }
            return counter;
        } else {
            return 0;
        }
    }
}

