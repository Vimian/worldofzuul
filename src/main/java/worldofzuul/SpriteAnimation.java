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

import java.util.*;

/**
 * The type Sprite animation.
 *
 * Contains functionality for displaying a collection of images or animations.
 *
 */
public abstract class SpriteAnimation extends Sprite {
    /**
     * The Animation cycle length millis.
     *
     * The time at which an animation ought to take.
     *
     */
    private final IntegerProperty animationCycleLengthMillis = new SimpleIntegerProperty(1000);
    /**
     * The Image animations.
     *
     * List of image collections.
     *
     */
    private LinkedHashMap<String, Image[]> imageAnimations = new LinkedHashMap<>();
    /**
     * The Animation timeline.
     *
     * Timeline containing animation frames and managing the changing of images.
     *
     */
    private Timeline animationTimeline;
    /**
     * The Animation string keys.
     *
     * Used for JSON deserialization.
     * Describes the keys in order that ought to be mapped to {@link SpriteAnimation#imageAnimations}
     *
     */
    private LinkedList<String> animationStringKeys = new LinkedList<>();
    /**
     * The Animation string values.
     *
     * Used for JSON deserialization.
     * Describes the string paths of images that ought to be mapped to {@link SpriteAnimation#imageAnimations}
     *
     */
    private LinkedList<String> animationStringValues = new LinkedList<>();


    /**
     * Instantiates a new Sprite animation.
     */
    public SpriteAnimation() {
    }

    /**
     * Add animation.
     *
     * @param animationKeys   the animation keys
     * @param imageAnimations the image animations
     */
    public void addAnimation(List<Object> animationKeys, List<Image[]> imageAnimations) {
        for (int i = 0; i < animationKeys.size(); i++) {
            this.imageAnimations.put(getAnimationString(animationKeys.get(i)), imageAnimations.get(i));
        }
    }

    /**
     * Gets animation string.
     *
     * Converts object to string.
     *
     * @param obj the obj
     * @return the string
     */
    private String getAnimationString(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "default";
        }
    }

    /**
     * Play animation.
     *
     * Plays an animation indefinitely using the default {@link SpriteAnimation#getImageView()}.
     *
     * @param animationKey the animation key
     */
    public void playAnimation(Object animationKey) {
        if (getImageView() != null) {
            playAnimation(getImageView(), Animation.INDEFINITE, animationKey);
        }
    }

    /**
     * Play animation.
     *
     * Plays an animation for the amount of cycles given by {@param cycles} using the default {@link SpriteAnimation#getImageView()}.
     *
     * @param cycles       the cycles
     * @param animationKey the animation key
     */
    public void playAnimation(int cycles, Object animationKey) {
        if (getImageView() != null) {
            if (imageAnimations.containsKey(getAnimationString(animationKey))) {
                playAnimation(getImageView(), cycles, imageAnimations.get(getAnimationString(animationKey)));
            }
        }
    }

    /**
     * Play animation.
     *
     * Plays an animation for the amount of cycles given by {@param cycles} using the given {@param imageView}.
     * With the images that correspond to the {@param animationKey} turned into a string using {@link SpriteAnimation#getAnimationString(Object)} in {@link SpriteAnimation#imageAnimations}.
     *
     * @param view         the view
     * @param cycles       the cycles
     * @param animationKey the animation key
     */
    public void playAnimation(ImageView view, int cycles, Object animationKey) {
        if (imageAnimations.containsKey(getAnimationString(animationKey))) {
            Image[] images = imageAnimations.get(getAnimationString(animationKey));
            if (images != null && images.length > 0) {
                playAnimation(view, cycles, images);
            }
        }
    }

    /**
     * Play animation.
     *
     * Plays an animation of {@param images} cycling over {@link SpriteAnimation#getAnimationCycleLengthMillis()} milliseconds for the amount of cycles given by {@param cycles} using the given {@param imageView}.
     *
     * @param view   the view
     * @param cycles the cycles
     * @param images the images
     */
    private void playAnimation(ImageView view, int cycles, Image[] images) {
        if (images.length <= 0)
            return;

        setImage(Arrays.stream(images).findFirst().orElseThrow());
        stopAnimation();

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
        animationTimeline.setOnFinished(event -> display());

        animationTimeline.setCycleCount(cycles);
        animationTimeline.play();
    }

    /**
     * Stop animation.
     */
    public void stopAnimation() {
        if (animationTimeline != null) {
            animationTimeline.stop();
        }
    }


    /**
     * Gets animation cycle length millis.
     *
     * @return the animation cycle length millis
     */
    public int getAnimationCycleLengthMillis() {
        return animationCycleLengthMillis.get();
    }

    /**
     * Sets animation cycle length millis.
     *
     * @param animationCycleLengthMillis the animation cycle length millis
     */
    public void setAnimationCycleLengthMillis(int animationCycleLengthMillis) {
        this.animationCycleLengthMillis.set(animationCycleLengthMillis);
    }

    /**
     * Animation cycle length millis property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty animationCycleLengthMillisProperty() {
        return animationCycleLengthMillis;
    }

    /**
     * Is animation active boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean isAnimationActive() {
        return animationTimeline.getStatus().equals(Animation.Status.RUNNING);
    }

    /**
     * Gets animation timeline.
     *
     * @return the animation timeline
     */
    @JsonIgnore
    public Timeline getAnimationTimeline() {
        return animationTimeline;
    }

    /**
     * Sets default image.
     *
     * @param animationKey the animation key
     */
    public void setDefaultImage(Object animationKey) {
        if (imageAnimations.values().size() > 0 && imageAnimations.containsKey(getAnimationString(animationKey))) {
            Image[] images = imageAnimations.get(getAnimationString(animationKey));
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

    /**
     * Gets animation string keys.
     *
     * @return the animation string keys
     */
    public LinkedList<String> getAnimationStringKeys() {
        return animationStringKeys;
    }

    /**
     * Sets animation string keys.
     *
     * @param animationStringKeys the animation string keys
     */
    public void setAnimationStringKeys(LinkedList<String> animationStringKeys) {
        this.animationStringKeys = animationStringKeys;
    }

    /**
     * Gets animation string values.
     *
     * @return the animation string values
     */
    public LinkedList<String> getAnimationStringValues() {
        return animationStringValues;
    }

    /**
     * Sets animation string values.
     *
     * @param animationStringValues the animation string values
     */
    public void setAnimationStringValues(LinkedList<String> animationStringValues) {
        this.animationStringValues = animationStringValues;
    }

    /**
     * Configure images.
     *
     * @param images the images
     */
    public void configureImages(HashMap<String, Image> images) {
        for (String animationStringValue : animationStringValues) {
            if (images.containsKey(animationStringValue) && animationStringValues.indexOf(animationStringValue) < animationStringKeys.size()) {
                imageAnimations.put(getAnimationStringKeys().get(animationStringValues.indexOf(animationStringValue)), new Image[]{images.get(animationStringValue)});
            }

        }
    }

    /**
     * Gets image animations.
     *
     * @return the image animations
     */
    @JsonIgnore
    public LinkedHashMap<String, Image[]> getImageAnimations() {
        return imageAnimations;
    }

    /**
     * Sets image animations.
     *
     * @param animations the animations
     */
    @JsonIgnore
    public void setImageAnimations(LinkedHashMap<String, Image[]> animations) {
        this.imageAnimations = animations;
    }

}
