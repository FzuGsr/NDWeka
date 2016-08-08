package nd.com.cn.gsr;

import nd.com.cn.gsr.tools.WekaTools;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import java.io.FileReader;


/**
 * Created by Guosongrong on 2016/7/26 0026.
 */
public class NDCluster {
    public static void main(String args[]) throws Exception {
        WekaTools t = new WekaTools();
        int stringIndex[] = {1,2,3};
        Instances dataInstances = t.csvToIns("data/data1.csv", stringIndex,null);
        t.showInstances(dataInstances);
        System.out.println("=================");
        //删除字符型属性
        for (int i = 0; i < stringIndex.length; i++){
            dataInstances.deleteAttributeAt(stringIndex[i]-i);
        }
        t.showInstances(dataInstances);

        NDCluster nd = new NDCluster();

        nd.ndKmeans(dataInstances);
//        nd.run("E:\\dataset\\weka\\data\\cpu.arff");
    }

    /**
     * 运行简单k-means
     * @param dataIns 训练数据集
     */
    public void ndKmeans(Instances dataIns){
        SimpleKMeans sk = new SimpleKMeans();
        try {
            sk.setNumClusters(10);
            sk.buildClusterer(dataIns);
            System.out.println(sk.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void run(String fileName) throws Exception {
        FileReader frData = new FileReader(fileName);
        Instances dataInstances = new Instances(frData);
//        for (int i = 0; i < dataInstances.numInstances(); i++){
//            System.out.println(dataInstances.instance(i));
//        }

        SimpleKMeans skmeans = new SimpleKMeans();
        skmeans.setNumClusters(5);
        skmeans.buildClusterer(dataInstances);
//        System.out.println("canopyT1: " + skmeans.getCanopyT1() + " canopyT2: "+skmeans.getCanopyT2());


        System.out.println(skmeans.toString());
        EM em = new EM();
        em.setNumClusters(-1);
        em.setMaxIterations(50);
        em.buildClusterer(dataInstances);
        System.out.println("===============================");
        System.out.println(em.toString());

    }


}


