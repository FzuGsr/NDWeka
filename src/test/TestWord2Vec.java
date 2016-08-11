package test;

import com.ansj.vec.Learn;
import com.ansj.vec.Word2VEC;
import com.ansj.vec.domain.WordEntry;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 测试word2vec
 * Created by Guosongrong on 2016/8/10 0010.
 */
public class TestWord2Vec {
    public static void main(String[] args) {
        TestWord2Vec twv = new TestWord2Vec();
        twv.run();


    }

    public void run() {
        File source = new File("data/ningzetaoparse.txt");

        //进行分词
        Learn learn = new Learn();
        try {

            FileOutputStream out = new FileOutputStream(new File("data/ningzetaoparse.txt"));
            String nzt = "在北京时间10日早晨结束的里约奥运会男子100米自由泳半决赛中，中国选手宁泽涛游出48秒37，排名小组第6，总排名第12，无缘决赛。中新网8月10日电在北京时间10日早晨结束的里约奥运会男子100米自由泳半决赛中，中国选手宁泽涛游出48秒37，排名小组第6，总排名第12，无缘决赛。编辑：付若愚关键词：宁泽涛;自由泳;男子;里约;半决赛说两句相关阅读宁泽涛：我已尽力了 能站在里约赛场非常荣幸北京时间8月10日，2016里约奥运会游泳男子100米自由泳半决赛，中国选手宁泽涛以48秒37的成绩位列小组第6，总成绩第12名无缘决赛。赛后接受采访之时，宁泽涛表示自己尽力了，发挥出正常水平原标题：宁泽涛没能晋级决赛 宁泽涛：站在奥运赛场 已非常开心 转！加油！里约奥运会男子100米自由泳半决赛中，中国选手宁泽涛没能晋级决赛。这只是宁泽涛第一次参加奥运会。宁泽涛表示一路走来，站在里约奥运赛场上，已非常开心。加油！转！[赞]";
            Writer outW = new OutputStreamWriter(out,"GBK");
            outW.write(NlpAnalysis.parse(nzt).toStringWithOutNature());
            outW.close();
            out.close();
            learn.learnFile(source);

            learn.saveModel(new File("model/ningzetao"));

            Word2VEC word2VEC = new Word2VEC();
            word2VEC.loadJavaModel("model/ningzetao");
            Set<WordEntry> result = word2VEC.distance("奥运");
            Iterator iter = result.iterator();
            while (iter.hasNext()) {
                WordEntry word = (WordEntry) iter.next();
                System.out.println(word.name + " " + word.score);
            }
//            System.out.println(word2VEC.distance("宁泽涛"));
//            System.out.println(word2VEC.analogy("奥运", "巴西", "宁泽涛"));
//
//            Set<WordEntry> result = word2VEC.analogy("男人", "国王", "女人");
//            Iterator iter = result.iterator();
//            while (iter.hasNext()) {
//                WordEntry word = (WordEntry) iter.next();
//                System.out.println(word.name + " " + word.score);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
