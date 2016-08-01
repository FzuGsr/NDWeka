package nd.com.cn.gsr;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

/**
 * Weka分类算法
 * Created by Guosongrong on 2016/7/29 0029.
 */
public class NDClassify {
    public static void main(String[] args) {
        File inputFile = new File("E:\\dataset\\weka\\data\\cpu.with.vendor.arff");
        ArffLoader arffLoader = new ArffLoader();

        try {
            arffLoader.setFile(inputFile);
            Instances dataInstances = arffLoader.getDataSet();
//            Tools.showInstances(dataInstances);

            NDClassify ndClassify = new NDClassify();
            ndClassify.ndClassify(dataInstances, dataInstances);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ndClassify(Instances trainInstances, Instances testInstances) {
        J48 classifier = new J48();
//        RandomForest classifier = new RandomForest();
        trainInstances.setClassIndex(0);

        testInstances.setClassIndex(0);

        try {
            classifier.buildClassifier(trainInstances);

            Evaluation evalution = new Evaluation(trainInstances);
            evalution.evaluateModel(classifier, testInstances);
//            System.out.println(evalution.errorRate());
//            //用十折交叉验证
//            evalution.crossValidateModel(classifier, trainInstances, 10, new Random(1));

            System.out.println(evalution.toSummaryString());//输出总结信息
            System.out.println(evalution.toClassDetailsString());//输出分类详细信息
            System.out.println(evalution.toMatrixString());//输出分类的混淆矩阵
            int rightCount = 0;
            for (int i = 0; i < testInstances.numInstances(); i++) {
                if (classifier.classifyInstance(testInstances.instance(i)) == testInstances.instance(i).classValue())
                    rightCount++;

            }
            System.out.println("precision: " + rightCount * 1.0 / testInstances.numInstances());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
