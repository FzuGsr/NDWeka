package nd.com.cn.gsr.tools;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.util.ArrayList;

/**
 * 工具类：
 * 读取文件转换为weka可以识别的instances
 *
 * Created by Guosongrong on 2016/7/29 0029.
 */
public class WekaTools {

    /**
     * 将Instances数据一行一行打印输出
     *
     * @param dataIns 要打印的实例 Instances
     */
    public static void showInstances(Instances dataIns) {
        for (int i = 0; i < dataIns.numInstances(); i++) {
            System.out.println("Row " + i + 1 + " : " + dataIns.instance(i));
        }
    }

    /**
     * 读取CSV文件，转换为Instances
     *
     * @param fileName     文件名
     * @param stringIndexs 字符型属性下标 从0开始计算
     * @param dateIndex    日期型属性下标 从0 开始计算
     * @return 返回数据集Instances
     */
    public Instances csvToIns(String fileName, int stringIndexs[], int dateIndex[]) {
        CSVLoader csvLoader = new CSVLoader();
        Instances dataIns = null;
        try {
            csvLoader.setFile(new File(fileName));
            for (int i = 0; i < stringIndexs.length; i++) {
                csvLoader.setStringAttributes(stringIndexs[i] + "");//设置字符属性
            }
            if (null != dateIndex) {
                for (int i = 0; i < dateIndex.length; i++) {
                    csvLoader.setDateAttributes(dateIndex[i] + "");//设置日期型属性
                }
            }
            dataIns = csvLoader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataIns;
    }


    /**
     * 只能够转换数值型属性
     * 读取csv文件，将数值型属性并转换为instances
     *
     * @param fileName
     * @return
     */
    public Instances csvNumToIns(String fileName) {
        BufferedReader br = null;
        Instances resultInstances = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            ArrayList<Attribute> attrs = new ArrayList<Attribute>();
            String attrName;
            attrName = br.readLine();//首行为标题行，作为属性名称
            String tokens[] = attrName.split(",");
            for (int i = 0; i < tokens.length; i++) {
                Attribute attr = new Attribute(tokens[i]);
                attrs.add(attr);//添加属性
            }
            resultInstances = new Instances("newdata", attrs, 0);//构建instances，初始容量为0
            //添加真实数据
            String valueString = null;
            while ((valueString = br.readLine()) != null) {
                tokens = valueString.split(",");
                double oneRowVector[] = new double[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    oneRowVector[i] = Double.parseDouble(tokens[i]);
                }
                Instance instance = new SparseInstance(1.0, oneRowVector);
                resultInstances.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultInstances;

    }




}
