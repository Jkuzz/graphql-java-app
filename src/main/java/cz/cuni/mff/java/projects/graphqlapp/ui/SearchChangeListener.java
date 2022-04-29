package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public record SearchChangeListener(
        AreaPanel areaPanel) implements DocumentListener {

    private void update(DocumentEvent documentEvent) {
        Document doc = documentEvent.getDocument();
        String filter;
        try {
            filter = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
            filter = "";
        }
        areaPanel.setSearchFilter(filter);
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        update(documentEvent);
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        update(documentEvent);
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        update(documentEvent);
    }
}
