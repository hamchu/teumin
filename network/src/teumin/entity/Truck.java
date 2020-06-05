package teumin.entity;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Truck implements Serializable {
    private String name;
    private String introduction;
    private String explanation;
    private String category;
    private transient Image evidence;
    private transient Image icon;

    public Truck(String name, String introduction, String explanation, String category, Image evidence, Image icon) {
        this.name = name;
        this.introduction = introduction;
        this.explanation = explanation;
        this.category = category;
        this.evidence = evidence;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getCategory() {
        return category;
    }

    public Image getEvidence() {
        return evidence;
    }

    public Image getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEvidence(Image evidence) {
        this.evidence = evidence;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        String url = (String) s.readObject();
        evidence = SwingFXUtils.toFXImage(ImageIO.read(s), null);
        icon = SwingFXUtils.toFXImage(ImageIO.read(s), null);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(evidence, null), "png", s);
        ImageIO.write(SwingFXUtils.fromFXImage(icon, null), "png", s);
    }
}
