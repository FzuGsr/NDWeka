package nd.com.cn.gsr.nlp;

import nd.com.cn.gsr.tools.FileTools;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.*;
import java.util.List;

/**
 * Created by Guosongrong on 2016/8/5 0005.
 */
public class WeiboProcessing {
    static String FileCODEING = "utf-8";

    public static void main(String[] args) {
        String fileStringPath = "E:\\dataset\\7-17-weibodata\\repos-id-cmnt-cn";
        String outPutFileStringPath = "E:\\dataset\\7-17-weibodata\\process\\repos-id-cmnt-cn-process";
        WeiboProcessing wbkw = new WeiboProcessing();
        long start = System.currentTimeMillis();
        wbkw.parseWeibo(fileStringPath, outPutFileStringPath);
        long end = System.currentTimeMillis();
        System.out.println("parseWeibo: used "+ (end - start) / 1000 +" s " + (end - start)+" ms");
    }

    /**
     * 对微博内容进行分词处理，并生成新的文件，保留微博文本ID
     * @param fileStringPath
     * @param outPutFileStringPath
     */
    public void parseWeibo(String fileStringPath, String outPutFileStringPath) {
        InputStreamReader in = null;
        BufferedReader br = null;
        FileOutputStream fop = null;
        int count = 0;
        try {
            in = new InputStreamReader(new FileInputStream(new File(fileStringPath)), FileCODEING);
            br = new BufferedReader(in);
            FileTools.delete(outPutFileStringPath);
            File outFile = new File(outPutFileStringPath);
            fop = new FileOutputStream(outFile, true);
            String temp = null;
            GetStopWordList getStopWordList = new GetStopWordList();
            List<String> stopWordList = getStopWordList.getStopWordList();
            while ((temp = br.readLine()) != null) {
                String tokens[] = temp.split("\t");
                String id = tokens[0];
                String content = tokens[1];
                if (count++ % 1000 == 0)
                    System.out.println("Process " + count + " ===================");
                List<Term> parse = ToAnalysis.parse(content).getTerms();
                StringBuffer sb = new StringBuffer();
                for (Term term : parse) {
                    String wordString = term.getName().trim();
                    if (stopWordList.contains(wordString))
                        ;
//                        System.out.print(" 停用词: " + wordString);
                    else {
                        sb.append(wordString);
                        sb.append(",");
                    }
                }
                if (sb.length() > 1)
                    sb.deleteCharAt(sb.length() - 1);
//                System.out.println("\n去掉停用词之后: " + sb.toString());
                fop.write((id + "," + sb.toString() + "\n").getBytes());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (in != null)
                    in.close();
                if (fop != null)
                    fop.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对文本进行关键词提取
     *  使用的是ansj自带的关键词提取，基于TFIDF
     *  如果文本太小，关键词个数可能小于给定的关键词个数
     * @param content    文本内容
     * @param numKeyWord 要提取的关键词个数
     * @return 返回关键词
     */
    public List<Keyword> getKeyWord(String content, int numKeyWord) {
        KeyWordComputer kwc = new KeyWordComputer(numKeyWord);
        return kwc.computeArticleTfidf(content);
    }
}
