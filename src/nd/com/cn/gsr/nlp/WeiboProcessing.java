package nd.com.cn.gsr.nlp;

import nd.com.cn.gsr.tools.FileTools;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.*;
import java.util.*;

/**
 * Created by Guosongrong on 2016/8/5 0005.
 */
public class WeiboProcessing {
    static String FileCODEING = "utf-8";
    private static final Map<String, Double> POS_SCORE = new HashMap<String, Double>();
    static {
        POS_SCORE.put("null", 0.0);
        POS_SCORE.put("w", 0.0);
        POS_SCORE.put("en", 0.0);
        POS_SCORE.put("m", 0.0);
        POS_SCORE.put("num", 0.0);
        POS_SCORE.put("nr", 3.0);
        POS_SCORE.put("nrf", 3.0);
        POS_SCORE.put("nw", 3.0);
        POS_SCORE.put("nt", 3.0);
        POS_SCORE.put("l", 0.2);
        POS_SCORE.put("a", 0.2);
        POS_SCORE.put("nz", 3.0);
        POS_SCORE.put("v", 0.2);
        POS_SCORE.put("kw", 6.0); //关键词词性
    }

    public static void main(String[] args) {
        long start = 0l,end= 0l;
        WeiboProcessing wbkw = new WeiboProcessing();
        String fileStringPath = "E:\\dataset\\7-17-weibodata\\repos-id-cmnt-cn";
        String outPutFileStringPath = "E:\\dataset\\7-17-weibodata\\process\\nlp-repos-id-cmnt-cn-process";

        start = System.currentTimeMillis();
//        将微博内容进行分词
        wbkw.parseWeibo(fileStringPath, outPutFileStringPath);
        end = System.currentTimeMillis();
        System.out.println("parseWeibo: used " + (end - start) / 1000 + " s " + (end - start) + " ms");

//        wbkw.comIDF(outPutFileStringPath);
//        String wordsL[] = "太,危险".split(",");
//        wbkw.comTF(wordsL);

        start = System.currentTimeMillis();
//        计算微博文本的tf-idf
        wbkw.parseTFIDF(outPutFileStringPath, outPutFileStringPath + "-TFIDF");
        end = System.currentTimeMillis();
        System.out.println("comWeiboTFIDF: used " + (end - start) / 1000 + " s " + (end - start) + " ms");
    }

    /**
     * 对微博内容进行分词处理，并生成新的文件，保留微博文本ID
     *
     * @param inputFileStringPath
     * @param outPutFileStringPath
     */
    public void parseWeibo(String inputFileStringPath, String outPutFileStringPath) {
        InputStreamReader in = null;
        BufferedReader br = null;
        FileOutputStream fop = null;
        int count = 0;
        try {
            in = new InputStreamReader(new FileInputStream(new File(inputFileStringPath)), FileCODEING);
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
                if (++count % 1000 == 0)
                    System.out.println("Process " + count + " ===================");
//                List<Term> parse = ToAnalysis.parse(content).getTerms();
                List<Term> parse = NlpAnalysis.parse(content).getTerms();

                StringBuffer sb = new StringBuffer();
                for (Term term : parse) {
                    String pos = term.natrue().natureStr;
                    Double posScore = POS_SCORE.get(pos);
                    if(posScore != null && 0 == posScore)
                        continue;

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
     * 使用的是ansj自带的关键词提取，基于TFIDF
     * 如果文本太小，关键词个数可能小于给定的关键词个数
     *
     * @param content    文本内容
     * @param numKeyWord 要提取的关键词个数
     * @return 返回关键词
     */
    public List<Keyword> getKeyWord(String content, int numKeyWord) {
        KeyWordComputer kwc = new KeyWordComputer(numKeyWord);
        return kwc.computeArticleTfidf(content);
    }

    /**
     * 计算微博文本的TF-IDF
     *
     * @param inputFileStringPath
     * @param outPutFileStringPath
     */
    public void parseTFIDF(String inputFileStringPath, String outPutFileStringPath) {
        InputStreamReader in = null;
        BufferedReader br = null;
        FileOutputStream fop = null;
        int count = 0;
        try {
            in = new InputStreamReader(new FileInputStream(new File(inputFileStringPath)), FileCODEING);
            br = new BufferedReader(in);
            FileTools.delete(outPutFileStringPath);
            File outFile = new File(outPutFileStringPath);
            fop = new FileOutputStream(outFile, true);
            String line = null;

            Map<String, Double> idfMap = comIDF(inputFileStringPath);
            while ((line = br.readLine()) != null) {
                if (++count % 1000 == 0)
                    System.out.println("Process " + count + " ===================");
                String id = line.substring(0, line.indexOf(","));
                String content = line.substring(line.indexOf(",") + 1);
                if (content.equals("")){
                    fop.write((id + "," + "\n").getBytes());
                    continue;
                }
                String wordsL[] = content.split(",");
                Map<String, Double> wordTFMap = comTF(wordsL);

                StringBuffer sb = new StringBuffer();
                sb.append(id);
                sb.append(",");
                Iterator iter = wordTFMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry wordEntry = (Map.Entry) iter.next();
                    String word = (String) wordEntry.getKey();
                    Double tf = (Double) wordEntry.getValue();
                    Double idf = idfMap.get(word);
                    Double tfidf = tf * idf;
                    sb.append(word + ":" + tfidf);
                    sb.append(",");
                }
                if (sb.length() > 1)
                    sb.deleteCharAt(sb.length() - 1);
                String outLine = sb.toString();
//                System.out.println(outLine);
                fop.write((sb.toString()+"\n").getBytes());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
     * 计算文本的IDF
     *
     * @param inputFileStringPath
     * @return 词的逆文档频率
     */
    public Map<String, Double> comIDF(String inputFileStringPath) {
        InputStreamReader inputStream = null;
        BufferedReader br = null;
        Map<String, Double> idfMap = new HashMap<String, Double>();
        try {
            inputStream = new InputStreamReader(new FileInputStream(new File(inputFileStringPath)), FileCODEING);
            br = new BufferedReader(inputStream);
            String line = null;
            int numOfDoc = 0;
            while ((line = br.readLine()) != null) {
                numOfDoc++;
                String id = line.substring(0, line.indexOf(","));
                String content = line.substring(line.indexOf(",") + 1);
                if (content.equals(""))
                    continue;
                String wordsL[] = content.split(",");
                Set<String> distSet = new HashSet<String>();//用于存储不重复的word
                for (int i = 0; i < wordsL.length; i++) {
                    distSet.add(wordsL[i]);
                }
                String words[] = distSet.toArray(new String[distSet.size()]);//words里面不存在重复的词，因为算idf的时候只考虑在文章中出现的次数

                for (int i = 0; i < words.length; i++) {//遍历words里的词语
                    if (!idfMap.containsKey(words[i])) {
                        idfMap.put(words[i], 1.0);
                    } else {
                        Double last = idfMap.get(words[i]) + 1;
                        idfMap.put(words[i], last);
                    }
                }
            }
            Iterator iter = idfMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Double idf = Math.log(numOfDoc / ((Double) entry.getValue() + 1));
                idfMap.put((String) entry.getKey(), idf);//将计数换为IDF
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("所有词个数: " + idfMap.size());
        return idfMap;
    }

//    public String[] distinctWords(String words[]){
//        List<String> words =
//        for(int i = 0; i < words.length; i++){
//
//        }
//
//    }

    /**
     * 计算words里面单词的词频
     *
     * @param words
     * @return
     */
    public Map<String, Double> comTF(String words[]) {
        Map<String, Double> wordTF = new HashMap<String, Double>();
        for (int i = 0; i < words.length; i++) {
            if (!wordTF.containsKey(words[i])) {
                wordTF.put(words[i], 1.0);
            } else {
                Double last = wordTF.get(words[i]) + 1;
                wordTF.put(words[i], last);
            }
        }
        Iterator iter = wordTF.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Double tf = (Double) entry.getValue() / words.length;
            wordTF.put((String) entry.getKey(), tf);//将计数换为词频
        }
        return wordTF;
    }
}
