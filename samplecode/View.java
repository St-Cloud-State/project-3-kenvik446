import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class View extends JFrame {
  private UIContext uiContext;
  private JPanel drawingPanel;
  private JPanel buttonPanel;
  private JPanel filePanel;
  private JButton lineButton;
  private JButton deleteButton;
  private JButton labelButton;
  private JButton selectButton;
  private JButton saveButton;
  private JButton openButton;
  private JButton undoButton;
  private JButton redoButton;
  private JButton polygonButton; // New button for drawing polygons
  private static UndoManager undoManager;
  private String fileName;
  private static Model model;

  public UIContext getUI() {
    return uiContext;
  }

  private void setUI(UIContext uiContext) {
    this.uiContext = uiContext;
  }

  public static void setModel(Model model) {
    View.model = model;
  }

  public static void setUndoManager(UndoManager undoManager) {
    View.undoManager = undoManager;
  }

  private class DrawingPanel extends JPanel {
    private MouseListener currentMouseListener;
    private KeyListener currentKeyListener;
    private FocusListener currentFocusListener;

    public DrawingPanel() {
      setLayout(null);
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      (NewSwingUI.getInstance()).setGraphics(g);
      g.setColor(Color.BLUE);

      // Render all items in the model
      Enumeration enumeration = model.getItems();
      while (enumeration.hasMoreElements()) {
        ((Item) enumeration.nextElement()).render(uiContext);
      }

      // Highlight selected items
      g.setColor(Color.RED);
      enumeration = model.getSelectedItems();
      while (enumeration.hasMoreElements()) {
        ((Item) enumeration.nextElement()).render(uiContext);
      }
    }

    public void addMouseListener(MouseListener newListener) {
      removeMouseListener(currentMouseListener);
      currentMouseListener = newListener;
      super.addMouseListener(newListener);
    }

    public void addKeyListener(KeyListener newListener) {
      removeKeyListener(currentKeyListener);
      currentKeyListener = newListener;
      super.addKeyListener(newListener);
    }

    public void addFocusListener(FocusListener newListener) {
      removeFocusListener(currentFocusListener);
      currentFocusListener = newListener;
      super.addFocusListener(newListener);
    }
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
    setTitle("Drawing Program 1.1  " + fileName);
  }

  public String getFileName() {
    return fileName;
  }

  public View() {
    super("Drawing Program 1.1  Untitled");
    fileName = null;

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        System.exit(0);
      }
    });

    this.setUI(NewSwingUI.getInstance());
    drawingPanel = new DrawingPanel();
    buttonPanel = new JPanel();
    Container contentPane = getContentPane();
    contentPane.add(buttonPanel, "North");
    contentPane.add(drawingPanel);

    // Initialize buttons
    lineButton = new LineButton(undoManager, this, drawingPanel);
    labelButton = new LabelButton(undoManager, this, drawingPanel);
    selectButton = new SelectButton(undoManager, this, drawingPanel);
    deleteButton = new DeleteButton(undoManager);
    saveButton = new SaveButton(undoManager, this);
    openButton = new OpenButton(undoManager, this);
    undoButton = new UndoButton(undoManager);
    redoButton = new RedoButton(undoManager);
    polygonButton = new PolygonButton(undoManager, this, drawingPanel); // Add the new Polygon button

    // Add buttons to the button panel
    buttonPanel.add(lineButton);
    buttonPanel.add(labelButton);
    buttonPanel.add(selectButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(openButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(redoButton);
    buttonPanel.add(polygonButton); // Add the Polygon button to the toolbar

    this.setSize(600, 400);
  }

  public void refresh() {
    // Refresh the drawing panel to reflect changes in the model
    drawingPanel.repaint();
  }

  public static Point mapPoint(Point point) {
    // Map a point on the drawing panel to a corresponding point in the figure
    return point;
  }
}
