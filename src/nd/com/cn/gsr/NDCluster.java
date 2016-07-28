package nd.com.cn.gsr;

import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

import java.io.FileReader;

/**
 * Created by Guosongrong on 2016/7/26 0026.
 */
public class NDCluster {
    public static void main(String args[]) throws Exception{
        NDCluster nd = new NDCluster();
        nd.run("E:\\dataset\\weka\\data\\cpu.arff");
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

    /**
     * 读取csv文件，并转换为instances
     * @param fileName
     * @return
     */
//    public Instances dataToInstance(String fileName){
//
//    }
}


