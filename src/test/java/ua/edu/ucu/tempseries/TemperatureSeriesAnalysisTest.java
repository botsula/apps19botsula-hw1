package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {

    private double[] one = {1.0};
    private double[] arr = {3.0, -5.0, 1.0, 5.0};
    private double[] bigArr = {1.0, 6.0, -17.0, -273, 25.0, 14.0, -2.0};
    private double[] emp = {};
    private double expResult;
    private double actualResult;
    private TemperatureSeriesAnalysis common;
    private TemperatureSeriesAnalysis hard;
    private TemperatureSeriesAnalysis oneElement;


    @Before
    public void start() {
        common = new TemperatureSeriesAnalysis(Arrays.copyOf(arr, arr.length));
        hard = new TemperatureSeriesAnalysis(Arrays.copyOf(bigArr, bigArr.length));
        oneElement = new TemperatureSeriesAnalysis(Arrays.copyOf(one, 1));
    }

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testDeviationWithOneElementArray() {
        expResult = 0;
        actualResult = oneElement.deviation();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestDeviationEmpty() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(new double[]{});
        seriesAnalysis.deviation();
    }

    @Test
    public void testDeviation() {
        expResult = 97.8591240675355;
        actualResult = hard.deviation();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testMinWithOneElementArray() {
        expResult = 1.0;
        actualResult = oneElement.min();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinWithEmptyArray() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(new double[]{});
        seriesAnalysis.min();
    }

    @Test
    public void testMin() {
        expResult = -273.0;
        actualResult = hard.min();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testMaxWithOneElementArray() {
        expResult = 1.0;
        actualResult = oneElement.max();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testMax() {
        expResult = 25.0;
        actualResult = hard.max();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testFindTempClosestToZeroWithOneElementArray() {
        expResult = 1.0;
        actualResult = common.findTempClosestToZero();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToZeroWithEmptyArray() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(new double[]{});
        seriesAnalysis.findTempClosestToZero();
    }

    @Test
    public void testFindTempClosestToZero() {
        expResult = 1.0;
        actualResult = hard.findTempClosestToZero();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void TestFindTempClosestToValueTheSame() {
        expResult = 1.0;
        actualResult = hard.findTempClosestToValue(1.0);
        assertEquals(expResult, actualResult, 0.0001);
    }

    @Test
    public void TestFindTempClosestToValue() {
        expResult = 25.0;
        actualResult = hard.findTempClosestToValue(50.1);
        assertEquals(expResult, actualResult, 0.0001);
    }

    @Test
    public void TestFindTempsLess() {
        expResult = -17;
        actualResult = hard.findTempsLessThen(-16.0)[0];
        assertEquals(expResult, actualResult, 0.001);
    }

    @Test
    public void TestFindTempsGreater() {
        expResult = 3.0;
        actualResult = common.findTempsGreaterThen(1.0)[0];
        assertEquals(expResult, actualResult, 0.001);
  }

    @Test
    public void TestSummaryStatistics() {
//        TemperatureSeriesAnalysis my_ser = new TemperatureSeriesAnalysis();
        TempSummaryStatistics stat_sum = new TempSummaryStatistics(common.average(), common.deviation(), common.min(), common.max());
        assertArrayEquals(new double[]{1.0, 3.7416573867739413, -5.0, 5.0}, new double[]{stat_sum.getAvgTemp(), stat_sum.getDevTemp(), stat_sum.getMinTemp(), stat_sum.getMaxTemp()}, 0.0001);
    }

    @Test
    public void TestAddTemps() {
        expResult = 10.0;
        double[] temps = new double[] {0.4, 0.3, 0.8};
        actualResult = hard.addTemps(temps);
        assertEquals(expResult, actualResult, 0.0001);
    }

    @Test (expected = InputMismatchException.class)
    public void TestCheckTemp() {
        double[] temp = new double[]{-3.0, 4.0, -274.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temp);
        seriesAnalysis.checkTemp(temp);
    }
}
