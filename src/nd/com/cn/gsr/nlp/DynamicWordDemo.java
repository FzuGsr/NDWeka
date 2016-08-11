package nd.com.cn.gsr.nlp;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.impl.NatureRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Guosongrong on 2016/8/5 0005.
 */
public class DynamicWordDemo {
    public static void main(String[] args) {
        // 增加新词,中间按照'\t'隔开
//        UserDefineLibrary.insertWord("ansj中文分词", "userDefine", 1000);
//        Result terms = ToAnalysis.parse("我觉得Ansj中文分词是一个不错的系统!我是王婆!");
//        System.out.println("增加新词例子:" + terms.toStringWithOutNature());
//        // 删除词语,只能删除.用户自定义的词典.
//        UserDefineLibrary.removeWord("ansj中文分词");
//        terms = ToAnalysis.parse("我觉得ansj中文分词是一个不错的系统!我是王婆!");
//        System.out.println("删除用户自定义词典例子:" + terms);
        /*分词*/
        Result parse = ToAnalysis.parse("\t薄案体现了中央反腐决心，坚决拥护党中央，打老虎不手软!");
        System.out.println(parse);
        /*用户自定义分词*/
        UserDefineLibrary.insertWord("陆金所", "userDefine", 1000);
        parse = NlpAnalysis.parse("陆金所，全称上海陆家嘴国际金融资产交易市场股份有限公司，隶属于中国平安集团，是中国最大的网络投融资平台之一，2011年9月在上海注册成立，注册资本金8.37亿元，总部设在上海陆家嘴。 陆金所是上海唯一一家通过国务院交易场所清理整顿的金融资产交易平台。");
        System.out.println(parse);

        /*关键词的提取*/
        KeyWordComputer kwc = new KeyWordComputer();
        String title = "维基解密否认斯诺登接受委内瑞拉庇护";
        String content = "有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科夫是误解了新闻内容。　　委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞拉的庇护，说会在适当时间公布有关决定。　　斯诺登相信目前还在莫斯科谢列梅捷沃机场，已滞留两个多星期。他早前向约20个国家提交庇护申请，委内瑞拉、尼加拉瓜和玻利维亚，先后表示答应，不过斯诺登还没作出决定。　　而另一场外交风波，玻利维亚总统莫拉莱斯的专机上星期被欧洲多国以怀疑斯诺登在机上为由拒绝过境事件，涉事国家之一的西班牙突然转口风，外长马加略]号表示愿意就任何误解致歉，但强调当时当局没有关闭领空或不许专机降落。";
        Collection<Keyword> result = kwc.computeArticleTfidf(title, content);
        System.out.println(result);

        /*对非ansj分词得到的结果及新的词语进行词性的标注*/
        String[] strs = {"对", "非", "ansj", "的", "分词", "结果", "进行", "词性", "标注"};
        List<String> lists = Arrays.asList(strs);
        List<Term> recognition = NatureRecognition.recognition(lists, 0);
        System.out.println(recognition);

//        FilterModifWord.modifResult(parse) ;
    }
}
