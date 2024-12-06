import java.awt.*;
import java.util.ArrayList;

public class PolygonItem extends Item {
    private ArrayList<Point> points; // List of points defining the polygon

    public PolygonItem(ArrayList<Point> points) {
        this.points = new ArrayList<>(points); // Copy points to prevent external modification
    }

    // Render the polygon
    @Override
    public void render(UIContext uiContext) {
        for (int i = 0; i < points.size() - 1; i++) {
            uiContext.drawLine(points.get(i), points.get(i + 1)); // Draw lines between consecutive points
        }
        if (points.size() > 1) {
            // Close the polygon by connecting the last point to the first
            uiContext.drawLine(points.get(points.size() - 1), points.get(0));
        }
    }

    @Override
    public boolean includes(Point point) {
        return false; // Optional: Define inclusion logic for selection
    }
}
