package worldofzuul.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The type Vector.
 *
 */
public class Vector {
    /**
     * The constant delimiter.
     */
    private static final String delimiter = ",";
    /**
     * The X.
     */
    private final IntegerProperty x = new SimpleIntegerProperty(0);
    /**
     * The Y.
     */
    private final IntegerProperty y = new SimpleIntegerProperty(0);
    /**
     * The Vector value.
     *
     * The value of the vector in a string format.
     * This field is always updated whenever {@link Vector#x} or {@link Vector#y} is updated.
     *
     */
    private final StringProperty vectorValue = new SimpleStringProperty();

    /**
     * Instantiates a new Vector.
     */
    public Vector() {
        setVectorValue(this.toString());
    }

    /**
     * Instantiates a new Vector.
     *
     * @param x the x
     * @param y the y
     */
    public Vector(int x, int y) {
        this();
        setX(x);
        setY(y);
    }

    /**
     * Instantiates a new Vector.
     *
     * @param s the s
     */
    public Vector(String s) {
        this();
        if (s.contains(delimiter)) {
            String[] values = s.split(delimiter);

            if (values.length == 2) {
                setX(Math.tryParse(values[0], 0));
                setY(Math.tryParse(values[1], 0));
            }
        }

    }


    /**
     * Gets vector value.
     *
     * @return the vector value
     */
    @JsonIgnore
    public final String getVectorValue() {
        return vectorValue.get();
    }

    /**
     * Sets vector value.
     *
     * @param value the value
     */
    public final void setVectorValue(String value) {
        vectorValue.set(value);
    }

    /**
     * Vector value property string property.
     *
     * @return the string property
     */
    @JsonIgnore
    public StringProperty vectorValueProperty() {
        return vectorValue;
    }


    @Override
    public String toString() {
        return x.get() + delimiter + y.get();
    }


    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x.get();
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x.set(x);
        setVectorValue(this.toString());
    }

    /**
     * X property integer property.
     *
     * @return the integer property
     */
    @JsonIgnore
    public IntegerProperty xProperty() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y.get();
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y.set(y);
        setVectorValue(this.toString());
    }

    /**
     * Y property integer property.
     *
     * @return the integer property
     */
    @JsonIgnore
    public IntegerProperty yProperty() {
        return y;
    }
}
