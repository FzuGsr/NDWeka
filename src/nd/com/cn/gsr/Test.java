package nd.com.cn.gsr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Guosongrong on 2016/8/8 0008.
 */
public class Test {
    public static void main(String[] args) {
//        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("a");
//        list.add("b");
//        System.out.println("list " + list.size());
//        Set<String> set = new HashSet<String>();
//        set.add("a");
//        set.add("b");
//        set.add("a");
//        System.out.println("set " + set.size());
//        String []shuzu = set.toArray(new String[set.size()]);
//        System.out.println(shuzu[0]+" "+shuzu[1]);

        String line = "repos-cmnt-1000000260,字,福";
        String content = line.substring(line.indexOf(",") + 1);
        if(content.equals("")) System.out.println("空字符");
        String wordsL[]=content.split(",",-1);
        System.out.println(wordsL.length);
        Set<String> distSet = new HashSet<String>();//用于存储不重复的word
        for(int i = 0; i < wordsL.length; i++){
            distSet.add(wordsL[i]);
        }
        String words[] = distSet.toArray(new String[distSet.size()]);//words里面不存在重复的词
        System.out.println("length "+words.length);
        for(int i = 0; i < words.length; i++){
            System.out.print(words[i]+" ");
        }
    }
}
