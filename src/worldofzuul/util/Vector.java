package worldofzuul.util;

public class Vector {
    private static final String delimiter = ",";
    public int x = 0;
    public int y = 0;

    public Vector() {

    }
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Vector(String s) {
        if(s.contains(delimiter)){
            var vals = s.split(delimiter);

            if(vals.length == 2){
                this.x = Math.tryParse(vals[0], 0);
                this.y = Math.tryParse(vals[1], 0);
            }
        }
    }
    @Override
    public String toString() {
        return x + delimiter + y;
    }
}
