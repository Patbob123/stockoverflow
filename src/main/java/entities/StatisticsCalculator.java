package entities;

public class StatisticsCalculator {
public double mean (double [] returns){
    double sum = 0.0;
    for(int i = 0; i < returns.length; i++){
        sum += returns[i];
    }
    return sum / returns.length;
    }


}
