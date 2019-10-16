package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.Collections;

public class TemperatureSeriesAnalysis {

    double[] tempr;
    int len;

    public TemperatureSeriesAnalysis() {
        this.tempr = new double[10];
        this.len = 10;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.len = temperatureSeries.length;
        this.tempr = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
    }

    public double average() {
        if (len > 0) {
            double sum = 0;
            for (int i = 0; i < this.len; i++) sum += this.tempr[i];
            return sum / this.len;
        } else throw new IllegalArgumentException();
    }

    public double deviation() {
        if (len > 0) {
            double av = this.average();
            double sum = 0;
            for (int i = 0; i < this.len; i++) {
                double temp = Math.pow(this.tempr[i] - av, 2);
                sum += temp;
            }
            return sum / len;
        } else throw new IllegalArgumentException();
    }

    public double min() {
        if (len > 0) {
            double min;
            min = tempr[0];
            for (int i = 1; i < len; i++) {
                if (min > tempr[i]) min = tempr[i];
            }
            return min;
        } else throw new IllegalArgumentException();
    }

    public double max() {
        if (len > 0) {
            double max;
            max = tempr[0];
            for (int i = 1; i < len; i++) {
                if (max < tempr[i]) max = tempr[i];
            }
            return max;
        } else throw new IllegalArgumentException();
    }

    public double findTempClosestToZero() {
        if (len > 0) {
            return findTempClosestToValue(0);
        } else throw new IllegalArgumentException();
    }

    public double findTempClosestToValue(double tempValue) {
        if (len > 0) {
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
                } else if (dist == temp) closest = Math.abs(tempr[i]);
            }
            return closest;
        } else throw new IllegalArgumentException();
    }

    public double[] findTempsLessThen(double tempValue) {
        int counter = 0;
        if (len > 0) {
            double[] less = new double[100];
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
            if (counter > 0) return less;
        }
        return null;

    }

    public double[] findTempsGreaterThen(double tempValue) {
        int counter = 0;
        if (len > 0) {
            double[] great = new double[100];
            for (int i = 0; i < len; i++) {
                if (tempr[i] >= tempValue) {
                    if (counter >= great.length) {
                        double[] temp = Arrays.copyOf(great, great.length);
                        great = new double[counter * 2];
                        great = Arrays.copyOf(temp, temp.length);
                    }
                    great[counter] = tempr[i];
                    counter++;
                }
            }
            if (counter > 0) return great;
        }
        return null;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (len > 0) {
            TempSummaryStatistics fin = new TempSummaryStatistics(average(), deviation(), min(), max());
            return fin;
        } else throw new IllegalArgumentException();
    }

    public double addTemps(double... temps) {
        if (len > 0) {
            int counter = 0;
            double sum = 0;
            for (int i = 0; i < len; i++) {
                if (tempr[i] != Double.MIN_VALUE){
                    sum += tempr[i];
                    counter++;
                }
            }
            for (int j = 0; j < temps.length; j++) {
                if (counter >= len){
                    double[] temp = Arrays.copyOf(tempr, tempr.length);
                    int tmp_len = tempr.length;
                    tempr = new double[counter * 2];
                    len = tempr.length;
                    for (int k = 0; k < tmp_len; k++) {
                        tempr[k] = temp[k];
                    }
                }
                tempr[counter-1] = temps[j];
                sum += temps[j];
                counter++;
            }
            return sum;
        } else return 0;
    }

    public static void main(String[] args) {
        double[] arr = {1, 2, 3, 4};
        TemperatureSeriesAnalysis my_ser = new TemperatureSeriesAnalysis(arr);
        double[] temps = {0.4, 0.3, 0.8};
        System.out.println(my_ser.addTemps(temps));
    }

}

