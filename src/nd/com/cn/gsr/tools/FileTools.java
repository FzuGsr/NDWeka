package nd.com.cn.gsr.tools;

import java.io.File;

/**
 * 文件工具类
 * 文件/文件目录删除，先判断文件是否存在，存在则删除
 *
 * Created by Guosongrong on 2016/8/6 0006.
 *
 */
public class FileTools {
    /**
     * 先判断文件是否存在，存在则删除文件，也可以删除文件目录
     *
     * @param filePath 文件/文件目录所在位置
     * @return 删除是否成功
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile()) {
                return deleteFile(filePath);
            } else {
                return deleteDictory(filePath);
            }
        } else {
            System.out.println("删除失败: " + filePath + "不存在");
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath 文件路径
     * @return 删除是否成功
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            if (file.delete()) {
                System.out.println("删除文件成功: " + filePath);
                return true;
            } else {
                System.out.println("删除文件失败: " + filePath);//因某些原因删除失败，例如文件被占用等。
                return false;
            }
        } else {
            System.out.println("删除文件失败: " + filePath);
            return false;
        }

    }

    /**
     * 删除文件目录
     *
     * @param dir 文件目录的路径
     * @return 删除是否成功
     */
    public static boolean deleteDictory(String dir) {
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.out.println("删除目录失败: " + dir + "不存在");
            return false;
        }
        boolean flag = true;
        File files[] = dirFile.listFiles();//先删除文件夹下的所有文件（包括目录），然后删除本身目录
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
            } else {
                //删除子目录
                flag = deleteDictory(files[i].getAbsolutePath());
            }
            if (!flag)
                break;//一旦某个文件删除失败，就结束，跳出循环
        }
        if (!flag) {
            System.out.println("删除目录失败");
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            System.out.println("删除目录" + dir + "失败！");
            return false;
        }

    }

    public static void main(String[] args) {
        String path = "E:\\tes";
        System.out.println("删除文件: " + FileTools.delete(path));
    }
}
