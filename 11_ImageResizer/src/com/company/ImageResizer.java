package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable{

    private File[] files;
    private int newWidth;
    private int newHeight;
    private String srcFolder;
    private String dstFolder;
    private long start;

    public ImageResizer(File[] files, int newWidth, int newHeight, String dstFolder, long start) {
        this.files = files;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        System.out.println("Total No of Files:" + files.length);
        Image img = null;
        BufferedImage tempJPG = null;
        File newFileJPG = null;
        try{
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    System.out.println("File " + files[i].getName());
                    img = ImageIO.read(files[i]);
                    tempJPG = resizeImage(img, newWidth, newHeight);
                    newFileJPG = new File(dstFolder + "/" + files[i].getName()+"_New.jpg");
                    ImageIO.write(tempJPG, "jpg", newFileJPG);
                }
            }
            System.out.println("DONE");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Duration of process: " + (System.currentTimeMillis() - start) + " ms.");
    }

    public BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
//        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
}
