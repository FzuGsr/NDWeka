package nd.com.cn.gsr;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.util.ArrayList;

/**
 * �����ࣺ
 * ��ȡ�ļ�ת��Ϊweka����ʶ���instances
 * Created by Guosongrong on 2016/7/29 0029.
 */
public  class Tools {

    /***
     * ��Instances����һ��һ�д�ӡ���
     * @param dataIns Ҫ��ӡ��ʵ�� Instances
     */
    public static void showInstances(Instances dataIns){
        for (int i = 0; i < dataIns.numInstances(); i++){
            System.out.println("Row "+i+1+" : "+dataIns.instance(i));
        }
    }
    /**
     * ��ȡCSV�ļ���ת��ΪInstances
     *
     * @param fileName     �ļ���
     * @param stringIndexs �ַ��������±� ��0��ʼ����
     * @param dateIndex    �����������±� ��0 ��ʼ����
     * @return �������ݼ�Instances
     */
    public Instances csvToIns(String fileName, int stringIndexs[], int dateIndex[]) {
        CSVLoader csvLoader = new CSVLoader();
        Instances dataIns = null;
        try {
            csvLoader.setFile(new File(fileName));
            for (int i = 0; i < stringIndexs.length; i++) {
                csvLoader.setStringAttributes(stringIndexs[i] + "");//�����ַ�����
            }
            if (null != dateIndex) {
                for (int i = 0; i < dateIndex.length; i++) {
                    csvLoader.setDateAttributes(dateIndex[i] + "");//��������������
                }
            }
            dataIns = csvLoader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataIns;
    }


    /**
     * ֻ�ܹ�ת����ֵ������
     * ��ȡcsv�ļ�������ֵ�����Բ�ת��Ϊinstances
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
            attrName = br.readLine();//����Ϊ�����У���Ϊ��������
            String tokens[] = attrName.split(",");
            for (int i = 0; i < tokens.length; i++) {
                Attribute attr = new Attribute(tokens[i]);
                attrs.add(attr);//�������
            }
            resultInstances = new Instances("newdata", attrs, 0);//����instances����ʼ����Ϊ0
            //�����ʵ����
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
