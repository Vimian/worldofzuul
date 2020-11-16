package sdu.student.editor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExitModel {

    private final StringProperty exitKey = new SimpleStringProperty();
    private final StringProperty exitValue = new SimpleStringProperty();


    public ExitModel(String exitKey, String exitValue) {
        setExitKey(exitKey);
        setExitValue(exitValue);
    }


    public String getExitKey() {
        return exitKey.get();
    }

    public void setExitKey(String exitKey) {
        this.exitKey.set(exitKey);
    }

    public StringProperty exitKeyProperty() {
        return exitKey;
    }

    public String getExitValue() {
        return exitValue.get();
    }

    public void setExitValue(String exitValue) {
        this.exitValue.set(exitValue);
    }

    public StringProperty exitValueProperty() {
        return exitValue;
    }
}
