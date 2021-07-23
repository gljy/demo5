package demo.data;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工商数据清洗
 *
 * @author guild
 */
public class BusinessDataClean {

    public static void main(String[] args) {
        String path = "F:\\BusinessData\\区市监局企业";
//        path = "F:\\BusinessData\\区市场局个体";
        List<File> dataFiles = loopDataFile(path);

        String descDataPath = "F:\\BusinessData\\工商注册登记.xlsx";
//        descDataPath = "F:\\BusinessData\\工商个体户.xlsx";
        ExcelWriter writer = ExcelUtil.getWriter(descDataPath);
        for (int i = 0; i < dataFiles.size(); i++) {
            File dataFile = dataFiles.get(i);

            BusinessData businessData = read(dataFile);
            if (i == 0) {
                writer.writeHeadRow(businessData.header);
            }
            writer.write(businessData.content);
        }
        writer.close();
    }

    /*static void validFileHeader(List<File> dataFiles) {
        String defaultHeader = null;
        for (File dataFile : dataFiles) {
            BusinessData businessData = read(dataFile);

            String header = ArrayUtil.join(businessData.header, ",");
            if (defaultHeader == null) {
                defaultHeader = header;
                continue;
            }

            if (!defaultHeader.equals(header)) {
                System.out.println(dataFile.getName());
                continue;
            }
        }
        System.out.println("Valid file header complete!");
    }*/

    static BusinessData read(File bookFile) {
        ExcelReader reader = ExcelUtil.getReader(bookFile);
        List<List<Object>> rows = reader.read();
        reader.close();

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("错误数据" + bookFile.getName());
        }

        BusinessData businessData = new BusinessData();
        businessData.header = rows.get(0);
        businessData.content = rows.stream().skip(1).collect(Collectors.toList());
        System.out.println(bookFile.getName() + "\tread!");
        return businessData;
    }

    /**
     * 遍历工商数据文件
     */
    static List<File> loopDataFile(String path) {
        return FileUtil.loopFiles(path, file -> {
            if (!file.isFile()) {
                return false;
            }

            String suffix = FileUtil.getSuffix(file);
            return StrUtil.equalsAny(suffix, "xls", "xlsx");
        });
    }

    static class BusinessData {

        List<Object> header;
        List<List<Object>> content;
    }
}
