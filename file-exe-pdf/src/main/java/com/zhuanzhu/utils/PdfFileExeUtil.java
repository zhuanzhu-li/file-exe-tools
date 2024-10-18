package com.zhuanzhu.utils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Liwq
 */
public class PdfFileExeUtil {

    public static boolean sepPdf(String path, Integer preIdx, Integer endIdx){
        // path : pdf 文档路径；
        // preIdx : 选取文档起始页数；
        // endIdx : 选取文档终止页数；
        File file = new File(path);
        boolean result; // 拆分执行结果
        try (PDDocument document = Loader.loadPDF(file);
             PDDocument newDocument = new PDDocument()) {
            // 拆分后的pdf文档路径
            String targetPath = path.substring(0, path.lastIndexOf(".")) + "_Page" + preIdx + "-" + endIdx + ".pdf";
            for (int i = preIdx-1; i <= endIdx-1; i++) {
                newDocument.addPage(document.getPage(i));
            }
            newDocument.save(targetPath);
            result = true;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public static boolean saveToImage(String path, Integer preIdx, Integer endIdx){
        File file = new File(path);
        boolean result; // 拆分执行结果
        try (PDDocument document = Loader.loadPDF(file);
             ) {
            PDFRenderer renderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();
            for (int page = 1; page <= pageCount; page++) {
                // 渲染页面
                BufferedImage image = renderer.renderImageWithDPI(page - 1, 300, ImageType.RGB);

                // 构建PNG文件名
                String filename = file.getParent() + "/page_" + page + ".png";

                // 确保输出目录存在
                new File(file.getParent()).mkdirs();

                // 保存PNG文件
                ImageIO.write(image, "PNG", new File(filename));
            }
            result = true;
        } catch (Exception e) {
            result = false;
        }

        return result;

    }

    public static void main(String[] args) throws IOException {

//        sepPdf("D:\\desk\\SCAN_20241018_095300783.pdf",10,12);
        saveToImage("D:\\desk\\用户信息保密协议模板.pdf",1,3);
    }




}
