package test;

import com.ansj.vec.Word2VEC;
import com.ansj.vec.util.WordKmeans;

import java.io.IOException;

/**
 * 测试词向量聚类
 * Created by Guosongrong on 2016/8/11 0011.
 */
public class TestCluster {
    public static void main(String[] args) throws IOException {
        Word2VEC vec = new Word2VEC();
        vec.loadJavaModel("model/ningzetao");
        System.out.println("load model ok!");
        WordKmeans wordKmeans = new WordKmeans(vec.getWordMap(), 5, 50);
        WordKmeans.Classes[] explain = wordKmeans.explain();

        for (int i = 0; i < explain.length; i++) {
            System.out.println("--------" + i + "---------");
            System.out.println(explain[i].getTop(10));
        }
    }
}
