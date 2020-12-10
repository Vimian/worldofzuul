package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite {
    private Image image;
    private ImageView imageView;
    private final StringProperty defaultImageFile = new SimpleStringProperty();


    public Sprite() {

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
        return defaultImageFile.get();
    }

    public void setDefaultImageFile(String defaultImageFile) {
        this.defaultImageFile.set(defaultImageFile);
    }

    public StringProperty defaultImageFileProperty() {
        return defaultImageFile;
    }

}
