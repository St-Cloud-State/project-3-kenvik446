import java.awt.*;
import java.util.ArrayList;

public class PolygonCommand extends Command {
    private ArrayList<Point> points; // Points defining the polygon
    private PolygonItem polygonItem; // The finalized polygon item
    private boolean isComplete; // Whether the polygon is complete

    public PolygonCommand() {
        points = new ArrayList<>();
        polygonItem = null;
        isComplete = false;
    }

    // Add a point to the polygon
    public void addPoint(Point point) {
        if (!isComplete) {
            points.add(point);
            model.setChanged(); // Notify that the model has changed
        }
    }

    // Complete the polygon
    public void completePolygon() {
        if (points.size() > 1 && !isComplete) {
            isComplete = true;
            polygonItem = new PolygonItem(points); // Finalize the polygon
            model.addItem(polygonItem); // Add to the model
            model.setChanged(); // Notify the view to refresh
        }
    }

    // Execute the command
    @Override
    public void execute() {
        if (isComplete && polygonItem != null) {
            model.addItem(polygonItem); // Add the polygon to the model
            model.setChanged(); // Notify the view to refresh
        }
    }

    // Undo the polygon creation
    @Override
    public boolean undo() {
        if (polygonItem != null) {
            model.removeItem(polygonItem); // Remove the polygon from the model
            model.setChanged(); // Notify the view to refresh
            return true;
        }
        return false;
    }

    // Redo the polygon creation
    @Override
    public boolean redo() {
        if (polygonItem != null) {
            model.addItem(polygonItem); // Re-add the polygon to the model
            model.setChanged(); // Notify the view to refresh
            return true;
        }
        return false;
    }
}
