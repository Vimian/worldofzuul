package worldofzuul.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.PrintStream;

/**
 * The type Custom print stream.
 */
public class CustomPrintStream extends PrintStream {
    /**
     * The Print list.
     *
     * Contains list of string parameters to have been passed to {@link CustomPrintStream#println(String)}.
     *
     */
    private final ListProperty<String> printList = new SimpleListProperty<>(
            FXCollections.observableArrayList());

    /**
     * Instantiates a new Custom print stream.
     *
     * @param org the original {@link PrintStream}
     */
    public CustomPrintStream(PrintStream org) {
        super(org);
    }

    @Override
    public void println(String line) {
        printList.add(line);

        super.println(line);
    }


    /**
     * Gets print list.
     *
     * @return the print list
     */
    public ObservableList<String> getPrintList() {
        return printList.get();
    }

    /**
     * Sets print list.
     *
     * @param printList the print list
     */
    public void setPrintList(ObservableList<String> printList) {
        this.printList.set(printList);
    }

    /**
     * Print list property list property.
     *
     * @return the list property
     */
    public ListProperty<String> printListProperty() {
        return printList;
    }
}
