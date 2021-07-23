package demo.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;

import java.io.File;
import java.util.List;

/**
 * 文件夹下文件大小统计
 *
 * @author guild
 */
public class FileSizeStatistics {

    public static void main(String[] args) {
        String pathname = "F:\\Java\\resources\\video";
        File[] folders = new File(pathname).listFiles();
        for (File folder : folders) {
            List<File> files = FileUtil.loopFiles(folder, file -> file.isFile());
            long length = files.stream().mapToLong(File::length).sum();
            System.out.println(folder.getName()
                    + "\t" + files.size()
                    + "\t" + formatFileSize(length));
        }
    }

    static String formatFileSize(long size) {
        String[] units = { "B", "KB", "MB", "GB" };
        for (int i = 0, len = units.length; i < len; i++) {
            double n = Math.pow(1024, len - i - 1);
            if (size >= n) {
                return NumberUtil.decimalFormat("#.00", size / n)
                        + units[len - i - 1];
            }
        }
        return "";
    }
}
