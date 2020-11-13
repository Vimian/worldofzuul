package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite {
    private Image image;
    private ImageView imageView;

    public Sprite(){

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
        this.imageView = imageView;
    }

    public void displayStill(){
        if (getImageView() != null) {
            displayStill(getImageView());
        }
    }

    public void displayStill(ImageView imageView){
        if (getImage() != null) {
            imageView.setImage(getImage());
        }
    }

}
