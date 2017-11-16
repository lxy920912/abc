package org.check;

/**
 * Created by lxy_920912 on 21/07/2017.
 */
/**

 　　* 最小二乘法 线性回归 y = a*x + b

 　　*

 　　* b = sum( y ) / n - a * sum( x ) / n

 　　*

 　　* a = ( n * sum( xy ) - sum( x ) * sum( y ) ) / ( n * sum( x^2 ) - sum(x) ^ 2 )

 　　*

 　　*/
public class Skew {

    public  double a;
    public  double b;
    public double[][] dots;
    public Skew(double a,double b){
        this.a = a;
        this.b = b;
    }
    public void train(double x[], double y[]) {
        int num;
        num = x.length < y.length ? x.length : y.length;
        calCoefficientes(x,y,num);
    }
    public void calCoefficientes (double x[],double y[],int num){

        double xy = 0.0,xT = 0.0,yT = 0.0,xS = 0.0;

        for(int i=0;i < num;i++){
            x[i] = x[i] - x[0];
            y[i] = y[i] - y[0];
            xy += x[i]*y[i];
            xT += x[i];
            yT += y[i];
            xS += Math.pow(x[i], 2.0);
        }

        a = (num*xy - xT*yT)/(num * xS - Math.pow(xT, 2.0));
        b = yT/num - a*xT/num;
        dots = new double[num][2];
        for(int i = 0;i < num;i++){
            dots[i][0] = x[0];
            dots[i][1] = y[0];
        }
    }
}
