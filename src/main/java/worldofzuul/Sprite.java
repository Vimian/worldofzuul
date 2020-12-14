package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import worldofzuul.util.Vector;

/**
 * The type Sprite.
 *
 * Base sprite class containing the functionality of displaying one image.
 *
 */
public abstract class Sprite {
    /**
     * The Default image file.
     *
     * The string path that the sprite is by default associated with.
     *
     */
    private final StringProperty defaultImageFile = new SimpleStringProperty();
    /**
     * The Image.
     *
     * The image that the sprite ought to display.
     *
     */
    private Image image;
    /**
     * The Image view.
     *
     * The node that displays {@link Sprite#image}
     *
     */
    private ImageView imageView;


    /**
     * Instantiates a new Sprite.
     */
    public Sprite() {

    }

    /**
     * Gets image.
     *
     * @return the image
     */
    @JsonIgnore
    public Image getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param defaultImage the default image
     */
    public void setImage(Image defaultImage) {
        this.image = defaultImage;
    }

    /**
     * Gets image view.
     *
     * @return the image view
     */
    @JsonIgnore
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Sets image view.
     *
     * @param imageView the image view
     */
    public void setImageView(ImageView imageView) {
        if (image == null && imageView.getImage() != null) {

            image = imageView.getImage();
        }

        this.imageView = imageView;
    }

    /**
     * Display.
     *
     * Calls {@link Sprite#display(ImageView)} using {@link Sprite#getImageView()} if it is not null.
     *
     */
    public void display() {
        if (getImageView() != null) {
            display(getImageView());
        }
    }

    /**
     * Display.
     *
     * Sets the image of given the image view to be {@link Sprite#image}.
     *
     * @param imageView the image view
     */
    public void display(ImageView imageView) {
        if (getImage() != null) {
            imageView.setImage(getImage());
        }
    }


    /**
     * Gets default image file.
     *
     * @return the default image file
     */
    public String getDefaultImageFile() {
        return defaultImageFile.get();
    }

    /**
     * Sets default image file.
     *
     * @param defaultImageFile the default image file
     */
    public void setDefaultImageFile(String defaultImageFile) {
        this.defaultImageFile.set(defaultImageFile);
    }

    /**
     * Default image file property string property.
     *
     * @return the string property
     */
    public StringProperty defaultImageFileProperty() {
        return defaultImageFile;
    }

}
