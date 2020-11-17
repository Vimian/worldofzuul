package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import worldofzuul.util.Drawing;

public abstract class Sprite {
    private final static int translationTime = 2000;
    private Image image;
    private ImageView imageView;
    private String defaultImageFile;


    public Sprite() {

    }

    public Sprite(Image defaultImage) {
        this.image = defaultImage;
    }

    @JsonIgnore
    public Image getImage() {
        return image;
    }

    public void setImage(Image defaultImage) {
        this.image = defaultImage;
    }

    @JsonIgnore
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        if (image == null && imageView.getImage() != null) {

            image = imageView.getImage();
        }

        this.imageView = imageView;
    }

    public void display() {
        if (getImageView() != null) {
            display(getImageView());
        }
    }

    public void display(ImageView imageView) {
        if (getImage() != null) {
            imageView.setImage(getImage());
        }
    }

    public String getDefaultImageFile() {
        return defaultImageFile;
    }

    public void setDefaultImageFile(String defaultImageFile) {
        this.defaultImageFile = defaultImageFile;
    }

    public TranslateTransition translate(double x, double y, double z) {
        return translate(x, y, z, translationTime);
    }

    public TranslateTransition translate(double x, double y, double z, int translationTime) {
        if (getImageView() != null) {
            return Drawing.translate(getImageView(), x, y, z, translationTime);
        }
        return null;
    }


}
