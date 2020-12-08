package worldofzuul;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class SpriteAnimation extends Sprite {


    private final IntegerProperty animationCycleLengthMillis = new SimpleIntegerProperty(1000);

    //Is Linked as order matters in getImage()
    private final LinkedHashMap<Object, Image[]> imageAnimations = new LinkedHashMap<>();
    private Timeline animationTimeline;
    private boolean animationActive;

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
        this(imageAnimations.stream().findFirst().stream().findFirst().orElseThrow());
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
            if (images != null && images.length > 0) {
                playAnimation(view, cycles, images);
            }
        }
    }

    private void playAnimation(ImageView view, int cycles, Image[] images) {
        if(images.length <= 0)
            return;

        setImage(Arrays.stream(images).findFirst().orElseThrow());
        stopAnimation();

        animationActive = true;
        animationTimeline = new Timeline(new KeyFrame(Duration.millis(getAnimationCycleLengthMillis()), ev -> {
            Transition animation = new Transition() {
                {
                    setCycleDuration(Duration.millis(getAnimationCycleLengthMillis()));
                }

                @Override
                protected void interpolate(double fraction) {
                    int index = (int) (fraction * (images.length - 1));
                    view.setImage(images[index]);
                }
            };
            animation.play();
        }));
        animationTimeline.setOnFinished(event -> {
            animationActive = false;
            display();
        });

        animationTimeline.setCycleCount(cycles);
        animationTimeline.play();
    }

    public void stopAnimation() {
        if (animationTimeline != null) {
            animationTimeline.stop();
        }
    }


    public int getAnimationCycleLengthMillis() {
        return animationCycleLengthMillis.get();
    }

    public void setAnimationCycleLengthMillis(int animationCycleLengthMillis) {
        this.animationCycleLengthMillis.set(animationCycleLengthMillis);
    }

    public IntegerProperty animationCycleLengthMillisProperty() {
        return animationCycleLengthMillis;
    }

    @JsonIgnore
    public boolean isAnimationActive() {
        return animationTimeline.getStatus().equals(Animation.Status.RUNNING);
    }

    @JsonIgnore
    public Timeline getAnimationTimeline() {
        return animationTimeline;
    }

    public void setDefaultImage(Object animationKey){
        if (imageAnimations.values().size() > 0 && imageAnimations.containsKey(animationKey)) {
            Image[] images = imageAnimations.get(animationKey);
            if (images.length > 0) {
                setImage(images[images.length - 1]);
                display();
            }
        }
    }

    @Override
    public Image getImage() {
        if (super.getImage() != null) {
            return super.getImage();
        } else if (imageAnimations.values().size() > 0) {
            Image[] images = imageAnimations.values().stream().findFirst().orElseThrow();
            if (images.length > 0) {
                setImage(images[0]);
                return super.getImage();
            }
        }
        return null;
    }

}
