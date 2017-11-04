package com.mingli.unzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {

    public static void main(String[] args) {
        File[] fs = unZip(new File("D:/testFile.zip"),new File("D:/ttt"));
        for(File f:fs){
            System.out.println(f.getName());
        }
    }
    public static File[] unZip(File zipFile,File targetDir){
        InputStream is = null;
        try {
            is = new FileInputStream(zipFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return unZip(is,targetDir);
    }
    public static File[] unZip(InputStream is,File targetDir){
        long startTime = System.currentTimeMillis();

        ZipInputStream Zin = new ZipInputStream(is);// 输入源zip路径
        BufferedInputStream Bin = new BufferedInputStream(Zin);
//			File Parent = target; // 输出路径（文件夹目录）
        File Fout = null;
        ZipEntry entry;
        try {
            while ((entry = Zin.getNextEntry()) != null
                    && !entry.isDirectory()) {
                Fout = new File(targetDir, entry.getName());
                if (!Fout.exists()) {
                    (new File(Fout.getParent())).mkdirs();
                }
                FileOutputStream out = new FileOutputStream(Fout);
                BufferedOutputStream Bout = new BufferedOutputStream(out);
                int b;
                while ((b = Bin.read()) != -1) {
                    Bout.write(b);
                }
                Bout.close();
                out.close();
                System.out.println(Fout + "解压成功");
            }
            Bin.close();
            Zin.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
        return targetDir.listFiles();
    }
}