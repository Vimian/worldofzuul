package worldofzuul;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;

public abstract class SpriteAnimation extends Sprite {


    private int animationCycleLengthMillis = 1000;

    HashMap<Object, Image[]> imageAnimations = new HashMap<>();
    Timeline timeline;

    public SpriteAnimation() {
    }

    public SpriteAnimation(Image defaultImage) {
        super(defaultImage);
    }

    public SpriteAnimation(Image[] images) {
        this(images[0]);
        this.imageAnimations.put(images, images);
    }

    public SpriteAnimation(List<Object> animationKeys, List<Image[]> imageAnimations) {
        this(imageAnimations.stream().findFirst().stream().findFirst().get());
        addAnimation(animationKeys, imageAnimations);
    }

    public void addAnimation(Object animationKey, Image[] animation) {
        imageAnimations.put(animationKey, animation);
    }

    public void addAnimation(List<Object> animationKeys, List<Image[]> imageAnimations) {
        for (int i = 0; i < animationKeys.size(); i++) {
            this.imageAnimations.put(animationKeys.get(i), imageAnimations.get(i));
        }
    }

    public void playAnimation() {
        if (getImageView() != null) {
            playAnimation(getImageView(), Animation.INDEFINITE);
        }
    }

    public void playAnimation(Object animationKey) {
        if (getImageView() != null) {
            playAnimation(getImageView(), Animation.INDEFINITE, animationKey);
        }
    }

    public void playAnimation(int cycles) {
        if (getImageView() != null) {
            if (imageAnimations.values().size() > 0) {
                playAnimation(getImageView(), cycles, imageAnimations.values().stream().findFirst());
            }
        }
    }

    public void playAnimation(int cycles, Object animationKey) {
        if (getImageView() != null) {
            if (imageAnimations.containsKey(animationKey)) {
                playAnimation(getImageView(), cycles, imageAnimations.get(animationKey));

            }
        }
    }

    public void playAnimation(ImageView view) {
        playAnimation(view, Animation.INDEFINITE);
    }

    public void playAnimation(ImageView view, Object animationKey) {
        playAnimation(view, Animation.INDEFINITE, animationKey);
    }

    public void playAnimation(ImageView view, int cycles) {
        if (imageAnimations.values().size() > 0) {
            playAnimation(view, cycles, imageAnimations.values().stream().findFirst());
        }
    }

    public void playAnimation(ImageView view, int cycles, Object animationKey) {
        if (imageAnimations.containsKey(animationKey)) {
            Image[] images = imageAnimations.get(animationKey);
            if(images != null && images.length > 0){
                playAnimation(view, cycles, images);
            }
        }
    }

    private void playAnimation(ImageView view, int cycles, Image[] images) {
        timeline = new Timeline(new KeyFrame(Duration.millis(animationCycleLengthMillis), ev -> {
            Transition animation = new Transition() {
                {
                    setCycleDuration(Duration.millis(animationCycleLengthMillis));
                }

                @Override
                protected void interpolate(double fraction) {
                    int index = (int) (fraction * (images.length - 1));
                    view.setImage(images[index]);
                }
            };
            animation.play();
        }));
        timeline.setCycleCount(cycles);
        timeline.play();
    }

    public void stopAnimation() {
        timeline.stop();
    }

    @JsonIgnore
    public int getAnimationCycleLengthMillis() {
        return animationCycleLengthMillis;
    }

    public void setAnimationCycleLengthMillis(int animationCycleLengthMillis) {
        this.animationCycleLengthMillis = animationCycleLengthMillis;
    }

    @Override
    public Image getImage() {
        if(super.getImage() != null){
            return super.getImage();
        } else if(imageAnimations.values().size() > 0){
            Image[] images = imageAnimations.values().stream().findFirst().get();
            if (images.length > 0) {
                setImage(images[0]);
                return super.getImage();
            }
        }
        return null;
    }

}
