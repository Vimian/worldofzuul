package worldofzuul.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vector {
    private static final String delimiter = ",";
    private final IntegerProperty x = new SimpleIntegerProperty(0);
    private final IntegerProperty y = new SimpleIntegerProperty(0);
    private final StringProperty vectorValue = new SimpleStringProperty();

    public Vector() {
        setVectorValue(this.toString());
    }

    public Vector(int x, int y) {
        this();
        setX(x);
        setY(y);
    }

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


    @JsonIgnore
    public final String getVectorValue() {
        return vectorValue.get();
    }

    public final void setVectorValue(String value) {
        vectorValue.set(value);
    }

    @JsonIgnore
    public StringProperty vectorValueProperty() {
        return vectorValue;
    }


    @Override
    public String toString() {
        return x.get() + delimiter + y.get();
    }


    public int getX() {
        return x.get();
    }

    public void setX(int x) {
        this.x.set(x);
        setVectorValue(this.toString());
    }

    @JsonIgnore
    public IntegerProperty xProperty() {
        return x;
    }

    public int getY() {
        return y.get();
    }

    public void setY(int y) {
        this.y.set(y);
        setVectorValue(this.toString());
    }

    @JsonIgnore
    public IntegerProperty yProperty() {
        return y;
    }
}
