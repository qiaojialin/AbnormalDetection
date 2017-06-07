package com.detection;

/**
 * Created by qiaojialin on 2017/6/1.
 */
import java.io.*;
import java.util.Random;

public class AddAbnormal {

    private static String src = "src/main/resources/sensor1.csv";
    private static String dest = "src/main/resources/sensor2.csv";

    public static void main(String args[]){
        addAbnormal(src);
    }

    private static void addAbnormal(String filePath){
        int i = 0;
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                Random random = new Random(10);
                while((lineTxt = bufferedReader.readLine()) != null) {
                    Double f = Double.parseDouble(lineTxt);
                    boolean addAnomal = random.nextInt(100) % 100 == 0;
                    double addvalue = random.nextDouble() + 0.2;

                    if(addAnomal) {
                        f += addvalue;
                        System.out.println(i + ": " + addvalue);
                        writeTxtFile(i+"", "src/main/resources/abnormalIndex");
                    }

                    writeTxtFile(f.toString(), dest);
                    i++;
                }
            read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
    } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

}

    private static void writeTxtFile(String content, String fileName)throws Exception{
        try{
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(content+"\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
