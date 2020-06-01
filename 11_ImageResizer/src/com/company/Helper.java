package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Helper implements Runnable {

    //    private File[] files;
    private int maxSize;
    //    private int delaySec;
    private String dstFolder;
    private File file;
    private Image image;
    private int newWidth;
    private int newHeight;

    public Helper(String dstFolder, File file, int maxSize) {
        this.dstFolder = dstFolder;
        this.file = file;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
//        System.out.println("Total No of Files under resize: " + files.length);
        image = null;
        BufferedImage tempJPG = null;
        File newFileJPG = null;

        try {
//            TimeUnit.SECONDS.sleep(delaySec);
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isFile()) {
//                    System.out.println("File " + files[i].getName());
//                    img = ImageIO.read(files[i]);
//                    imageRatio = (double) img.getWidth(null)/(double) img.getHeight(null);
            setNewBounds(file, maxSize);
            tempJPG = resizeImage(image, newWidth, newHeight);
            newFileJPG = new File(dstFolder + "/" + file.getName() + "_New.jpg");
            ImageIO.write(tempJPG, "jpg", newFileJPG);
//                }
//            }
//            System.out.println("DONE");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        System.out.println("Duration of thread " + Thread.currentThread().getName() + ": " + (System.currentTimeMillis() - start) + " ms.");
    }

    public BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
//        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return
                bufferedImage;
    }

    public void setNewBounds(File file, int maxSize) {
        //Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        double imageRatio = (double) width / (double) height;
        if (width >= height) {
            newWidth = maxSize;
            newHeight = (int) (newWidth / imageRatio);
        } else {
            newHeight = maxSize;
            newWidth = (int) (newHeight * imageRatio);
        }
    }
}
