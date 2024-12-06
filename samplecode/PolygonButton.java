import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PolygonButton extends JButton implements ActionListener {
    private JPanel drawingPanel; // Panel for drawing
    private View view; // Reference to the view
    private MouseHandler mouseHandler; // Mouse handler for polygon drawing
    private PolygonCommand polygonCommand; // Command for managing the polygon
    private UndoManager undoManager; // UndoManager for tracking commands

    public PolygonButton(UndoManager undoManager, View view, JPanel panel) {
        super("Polygon");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = panel;
        addActionListener(this); // Listen for button clicks
        mouseHandler = new MouseHandler(); // Initialize the mouse handler
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // Change cursor to crosshair when the Polygon button is clicked
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler); // Attach the mouse handler
        polygonCommand = new PolygonCommand(); // Initialize the polygon command
        undoManager.beginCommand(polygonCommand); // Begin the command for undo/redo
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event)) {
                // Add a new point to the polygon
                polygonCommand.addPoint(View.mapPoint(event.getPoint()));
                view.refresh(); // Refresh the view to show the updated polygon
            } else if (SwingUtilities.isRightMouseButton(event)) {
                // Complete the polygon
                polygonCommand.completePolygon();
                undoManager.endCommand(polygonCommand); // Finalize the command
                drawingPanel.removeMouseListener(this); // Remove the mouse handler
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset cursor to default
                view.refresh(); // Refresh the view to show the completed polygon
            }
        }
    }
}
