package com.pro.financial.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
public class QRCodeUtil {
    public static void getQRCode(  String filePath,
                                   String fileName, int width, int height, String format,
                                   String content) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
        Path path = FileSystems.getDefault().getPath( filePath,
                fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
    }
    public static ByteArrayOutputStream getQRCodeStream(int width, int height, String format,
                                                        String content) throws WriterException, IOException {
        ByteArrayOutputStream os= new ByteArrayOutputStream();
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, format, os);
        return os;
    }
    public static String getCode128Base64Stream(   int width, int height, String format,
                                                   String content) throws WriterException, IOException {
        ByteArrayOutputStream os= new ByteArrayOutputStream();
//		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.CODE_128, width, height);
        MatrixToImageWriter.writeToStream(bitMatrix, format, os);
        String imageCodeBase64 = Base64.encodeBase64String(os.toByteArray());
        return imageCodeBase64;
    }
    public static void main(String[] strs){
        System.out.println(new java.util.Date());
        for(int i = 0;i<1; i ++){
            Thread t = new Thread(new Runnable(){

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String filePath = "/Users/apple/Documents/upload/qrcode/";
                    int width = 800;
                    int height = 800;
                    String format = "png";
                    String tname = Thread.currentThread().getName();

                    for(int j = 0;j<1;j++){
                        String filename =tname+"_"+j+"."+format;
                        String content ="http://www.medcircle.cn/medapp/reginfo?uid=#259046#&regid=#418#&regcode=#259046#&name=#张晓磊#&org=#北京协和医院#";
                        //String content ="http://www.medcircle.cn/web/user?medcircle_cid=2";
//				String content = "content for therad "+tname+"_"+j;
                        try {
                            QRCodeUtil.getQRCode(filePath,filename,width,height,format,content);
                        } catch (WriterException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

            },"thread"+i);
            t.start();
        }
        System.out.println(new java.util.Date());
    }
}
