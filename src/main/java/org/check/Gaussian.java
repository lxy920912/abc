package org.check;

import java.text.DecimalFormat;
import java.text.DecimalFormat;

/**
 * Created by lxy_920912 on 21/07/2017.
 */
class Gaussian {
    public double average;
    public double sigma;
    public double[][] dots;
    public Gaussian(double average,double sigma){
        DecimalFormat    df   = new DecimalFormat("######0.00");
        this.average = average;
        this.sigma =  Double.parseDouble(df.format(sigma));
        this.dots = getGaussianDots();
    }
    public double getGassuainValue(double value){
        double T = 1 / (Math.pow(sigma * 2 * Math.PI, 0.5));
        double k = -Math.pow((value - average), 2) / (2 * sigma);
        return T * Math.pow(Math.E, k);
    }
    public double[][] getGaussianDots(){
        DecimalFormat    df   = new DecimalFormat("######0.00");
        int k = 80;
        double[][] dots = new double[k][2];
        double dfAverage = Double.parseDouble(df.format(this.average));
        for(int i = -40,j = 0;j < k;i++,j++){
            dots[j][0] = this.average+i;
            dots[j][1] =  Double.parseDouble(df.format(getGassuainValue(dots[j][0])));
        }
        return dots;
    }
    public String getGuassianStr(){
        String str = "{";
        str = str+"\"average\":"+this.average+",\"sigma\":"+this.sigma+",\"dots\":[";
        for(int i = 0;i < this.dots.length-1;i++){
            str = str+"["+this.dots[i][0]+","+this.dots[i][1]+"],";
        }
        int i = this.dots.length-1;
        str = str+"["+this.dots[i][0]+","+this.dots[i][1]+"]";
        str = str +"]}";
        return str;
    }
}
