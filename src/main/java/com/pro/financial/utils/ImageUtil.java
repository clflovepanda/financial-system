package com.pro.financial.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static byte[] imageToBytes(BufferedImage buffImg, String png) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffImg, png, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static Image bytesToImage(byte[] bytes) {
        Image image = Toolkit.getDefaultToolkit().createImage(bytes);
        try {
            MediaTracker mt = new MediaTracker(new Label());
            mt.addImage(image, 0);
            mt.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return image;
    }
}
