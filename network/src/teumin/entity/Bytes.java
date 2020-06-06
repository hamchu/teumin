package teumin.entity;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Bytes implements Serializable {
    private byte[] bytes;

    public Bytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Bytes(Image image) {
        if (image == null) {
            bytes = null;

            return;
        }

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
            baos.flush();
            bytes = baos.toByteArray();
            baos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] getBytes() {
        return bytes;
    }

    public Image toImage() {
        if (bytes == null) {
            return null;
        }

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return SwingFXUtils.toFXImage(ImageIO.read(bais), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
