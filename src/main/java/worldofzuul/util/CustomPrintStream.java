package worldofzuul.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.item.Item;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class CustomPrintStream extends PrintStream {
    private final ListProperty<String> printList = new SimpleListProperty<>(
            FXCollections.observableArrayList());

    public CustomPrintStream(PrintStream org) {
        super(org);
    }

    @Override
    public void println(String line) {
        printList.add(line);
        super.println(line);
    }


    public ObservableList<String> getPrintList() {
        return printList.get();
    }

    public ListProperty<String> printListProperty() {
        return printList;
    }

    public void setPrintList(ObservableList<String> printList) {
        this.printList.set(printList);
    }
}
