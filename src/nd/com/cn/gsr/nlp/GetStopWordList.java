package nd.com.cn.gsr.nlp;

/**
 * 获取停用词
 * 从文件中获取停用词，可供其他类进行调用
 * Created by Guosongrong on 2016/8/5 0005.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetStopWordList {

    static String STOP_FIle_PATH = "stopword.txt";
    static String STOP_CHINESE_FIle = "Chinese";
    static String STOP_ENGLISH_FIle = "English";
    // get stopword 1
    public Map<String, List> getStopWordListMap() throws Exception {

        Map<String, List> map = new HashMap<String, List>();

        String path_chinese = "chinese_stopword.txt";
        String path_english = "english_stopword.txt";

        GetStopWordList getStopWordList = new GetStopWordList();

        List<String> list_c = getStopWordList.readStopWord(path_chinese);
        List<String> list_e = getStopWordList.readStopWord(path_english);

        map.put(STOP_CHINESE_FIle, list_c);
        map.put(STOP_ENGLISH_FIle, list_e);

        return map;
    }

    // get stopword 2
    public Map<String, List> getStopWordList(String path_chinese,
                                             String path_english) throws Exception {

        Map<String, List> map = new HashMap<String, List>();

        GetStopWordList getStopWordList = new GetStopWordList();

        List<String> list_c = getStopWordList.readStopWord(path_chinese);
        List<String> list_e = getStopWordList.readStopWord(path_english);

        map.put(STOP_CHINESE_FIle, list_c);
        map.put(STOP_ENGLISH_FIle, list_e);

        return map;
    }

    /**
     * 获取停用词
     * @param path_stop 停用词所在的文件地址
     * @return 停用词List
     * @throws Exception
     */
    public List<String> getStopWordList(String path_stop)throws Exception{
        GetStopWordList getStopWordList = new GetStopWordList();
        return getStopWordList.readStopWord(path_stop);
    }

    /**
     * 获取停用词
     * @return 停用词List
     * @throws Exception
     */
    public List<String> getStopWordList()throws Exception{
        GetStopWordList getStopWordList = new GetStopWordList();
        return getStopWordList.readStopWord(STOP_FIle_PATH);
    }

    // read stopword from file
    public List<String> readStopWord(String path) throws Exception {

        List<String> list = new ArrayList<String>();

        File file = new File(path);
        InputStreamReader isr = new InputStreamReader(
                new FileInputStream(file), "utf-8");
        BufferedReader bf = new BufferedReader(isr);

        String stopword = null;
        while ((stopword = bf.readLine()) != null) {
            stopword = stopword.trim();
            list.add(stopword);
        }

        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) throws Exception {
        GetStopWordList getStopWordList = new GetStopWordList();
        Map<String, List> map = getStopWordList.getStopWordListMap();
        List<String> list = map.get(STOP_CHINESE_FIle);

        for (String str : list) {
            System.out.println(str);
        }
    }

}
