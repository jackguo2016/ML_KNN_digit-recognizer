package com.ZiyanGuo;
import org.jfree.ui.RefineryUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Main {
    //Testset 是小的用来预测的，Traincsv是对照样本
    static long startTime = System.nanoTime();
    private static int errorcount = 0;
    private static List<Data> list = new ArrayList<Data>();
    private static int[][] cm = new int[10][10];

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/com/ZiyanGuo/traincsv.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                int label = Integer.parseInt(line.substring(0, 1));
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                int[] data = new int[784];
                for (int i = 1; i < item.length; i++) {
                    data[i - 1] = Integer.parseInt(item[i]);
                }
                list.add(new Data(data, label));
            }
            //System.out.println("数据库的" + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        Main mm = new Main();
        double [] errorratearray = new double[20];
        int k = 1;
        for (;k<20;k++){
            errorcount = 0;
            errorratearray[k]=mm.knnFunction(k);
        }

        graph chart = new graph(
                "K values Vs Error rate on number 8 case" ,
                "K values Vs Error rate on number 8 case",errorratearray);
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Totoal time use: "+duration/1000000000+"S");
        print2darray(cm);

    }

    public Double knnFunction(int k) {
        List<Data> testlist = new ArrayList<Data>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/com/ZiyanGuo/testSet.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                int label = Integer.parseInt(line.substring(0, 1));
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                int[] data = new int[784];
                for (int i = 1; i < item.length; i++) {
                    data[i - 1] = Integer.parseInt(item[i]);
                }
                testlist.add(new Data(data, label));
            }
            System.out.println("test数据库的" + testlist.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Data testPoint : testlist) {
            int[] vector1 = testPoint.getPixels();
            List<Data> anslist = new ArrayList<Data>(); //算好距离的数据点就存进去
            for (Data dataPoint : list) {
                int[] vector2 = dataPoint.getPixels();
                double osd = sim_distance(vector1, vector2);
                dataPoint.setDistance(osd);
                anslist.add(dataPoint);
            }

            Collections.sort(anslist, Comparator.comparingDouble(Data::getDistance));//排序了list从小到大的距离顺序

            int ii = k;
            int gaseoutcome = 0;//猜测结果
            HashMap<Integer, Integer> Sites = new HashMap<Integer, Integer>();
            Sites.put(1, 0);
            Sites.put(2, 0);
            Sites.put(3, 0);
            Sites.put(4, 0);
            Sites.put(5, 0);
            Sites.put(6, 0);
            Sites.put(7, 0);
            Sites.put(8, 0);
            Sites.put(9, 0);
            Sites.put(0, 0);

            while(ii>0){
                int a = anslist.get(ii-1).getLabel();//最近的数字是几

                int setvalue = Sites.get(a)+1;
                Sites.put(a,setvalue);//把新的key，value加完之后放进去更新
                ii--;
            }
            gaseoutcome = getProcessCdByName(Sites);
            //System.out.println(gaseoutcome+"----"+testPoint.getLabel());
            cm[gaseoutcome][testPoint.getLabel()]+=1;
            if(gaseoutcome != testPoint.getLabel()){
                errorcount ++;
            }


        }
        System.out.println("=========================================================================");
        System.out.println("This round k is: "+ k);
        System.out.println("Number of testcase is: "+ testlist.size());
        System.out.println("Number of error is: "+ errorcount);
        String errorRate = String.format("%.2f", (float) errorcount / (float) (testlist.size()) * 100);
        System.out.println("this time the error rate is: "+ errorRate+"%");
        String errorRate2 = String.format("%.2f", 100 -(float) errorcount / (float) (testlist.size()) * 100);
        System.out.println("Accuracy is : "+ errorRate2 +"%");
        System.out.println("=========================================================================");


        return Double.parseDouble(errorRate);


    }

    public double sim_distance(int[] vector1, int[] vector2) { //欧式距离
        double distance = 0;

        if (vector1.length == vector2.length) {
            for (int i = 0; i < vector1.length; i++) {
                double temp = Math.pow((vector1[i] - vector2[i]), 2);
                distance += temp;
            }
            distance = Math.sqrt(distance);
        }
        return distance;
    }

    public static int getProcessCdByName(HashMap<Integer, Integer> processMap){//我找最大值对应的哪一个键
        int max=0;
        for (Integer in : processMap.values()) {
            System.err.println(in);
            max=Math.max(max, in);
        }

        int result = 0;
        Set<Map.Entry<Integer, Integer>> set = processMap.entrySet();
        for(Map.Entry<Integer, Integer> entry : set){
            if(entry.getValue()==max){
                result = entry.getKey();
                break;
            }
        }

        return result;
    }

    public static void print2darray(int[][] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; (array[i] != null && j < array[i].length); j++) {
                System.out.print(array[i][j] + " ");
            }

            System.out.println();
        }
    }

}
