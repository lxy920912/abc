package org.check;

/**
 * Created by lxy_920912 on 21/07/2017.
 */
public class Algorithm {
    public  Gaussian getGaussain(double [] arr){
        double sum = 0;
        for(int i = 0;i < arr.length;i++){
            sum = sum + arr[i];
        }
        double average = sum / arr.length;
        double sigma = 0;
        for(int i = 0;i < arr.length;i++){
            sigma = sigma + Math.pow((arr[i]-average),2);
        }
        sigma = sigma / arr.length;
        sigma = Math.pow(sigma,0.5);
        Gaussian gaussian = new Gaussian(average,sigma);

        return gaussian;
    }
    public Skew getSkew(double[] x, double []y){

        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        int n = Math.min(x.length,y.length);

        for( int i = 0;n < i;i++){
            sumx = sumx + x[i];
            sumy = sumy + y[i];
            sumx2 = sumx2 + (x[i]*x[i]);
        }
        double xbar = sumx / n;
        double ybar = sumy / n;
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        double a = xybar / xxbar;
        double b= ybar - a * xbar;
        Skew skew = new Skew(a,b);
        return  skew;
    }
}
