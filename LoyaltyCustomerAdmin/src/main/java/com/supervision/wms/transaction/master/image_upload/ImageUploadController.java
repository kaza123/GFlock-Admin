/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.master.image_upload;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kasun
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/master/image")
public class ImageUploadController {

    public static final String IMAGE_LOCATION = "./style-image";
    public static final String IMAGE_NAME_FILTER_TEMPLATE = "job-no-%s";
    public static final String IMAGE_NAME = "%s.jpg";

    @RequestMapping(value = "/upload-image/{style}", method = RequestMethod.POST)
    public void saveImage(@RequestParam("file") MultipartFile file, @PathVariable String style, HttpServletResponse response) {
        try {
            File uploadFile = new File(IMAGE_LOCATION, String.format(IMAGE_NAME, style));
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }

            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage outputImage = new BufferedImage(976, 1204, bufferedImage.getType());
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(bufferedImage, 0, 0, 976, 1204, null);
            g2d.dispose();
            ImageIO.write(outputImage, "JPG", uploadFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static BufferedImage makeTransparent(BufferedImage image, int x, int y) {
        ColorModel cm = image.getColorModel();
        if (!(cm instanceof IndexColorModel)) {
            return image; // sorry...
        }
        IndexColorModel icm = (IndexColorModel) cm;
        WritableRaster raster = image.getRaster();
        int pixel = raster.getSample(x, y, 0);
        int size = icm.getMapSize();
        byte[] reds = new byte[size];
        byte[] greens = new byte[size];
        byte[] blues = new byte[size];
        icm.getReds(reds);
        icm.getGreens(greens);
        icm.getBlues(blues);
        IndexColorModel icm2 = new IndexColorModel(8, size, reds, greens, blues, pixel);
        return new BufferedImage(icm2, raster, image.isAlphaPremultiplied(), null);
    }

    @RequestMapping(value = "/download-image/{fileName:.+}", method = RequestMethod.GET)
    public void downloadImage(@PathVariable String fileName, HttpServletResponse response) {
        try {
            InputStream inputStream = new FileInputStream(IMAGE_LOCATION + "/" + fileName);
            System.out.println("fileName : " + fileName);
            OutputStream outputStream = response.getOutputStream();

            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) > 0) {
                outputStream.write(bytes);
            }
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
