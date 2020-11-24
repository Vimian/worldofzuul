package worldofzuul.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vector {
    private static final String delimiter = ",";
    private int x = 0;
    private int y = 0;
    private StringProperty vectorValue = new SimpleStringProperty();

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
        if(s.contains(delimiter)){
            String[] vals = s.split(delimiter);

            if(vals.length == 2){
                setX(Math.tryParse(vals[0], 0));
                setY(Math.tryParse(vals[1], 0));
            }
        }

    }




    // Define a variable to store the property

    // Define a getter for the property's value
    public final String getVectorValue(){return vectorValue.get();}

    // Define a setter for the property's value
    public final void setVectorValue(String value){vectorValue.set(value);}

    // Define a getter for the property itself
    public StringProperty vectorValueProperty() {return vectorValue;}


    @Override
    public String toString() {
        return x + delimiter + y;
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        setVectorValue(this.toString());
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        setVectorValue(this.toString());
    }

//    @Override
//    public boolean equals(Object o) {
//        return true;
//    }

}
