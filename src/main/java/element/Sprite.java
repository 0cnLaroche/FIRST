package element;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;


public abstract class Sprite extends Pane {

	protected double velocityX; 
	protected double velocityY; 
	private double width; 
	private double height; 
	protected Point2D[] connectors;
	public static final int TOP = 0;
	public static final int RIGHT = 1;
	public static final int BUTTOM = 2;
	public static final int LEFT = 3;
	protected int padding;

	
	public Sprite() {
		super();
		velocityX = 0;
		velocityY = 0;
		padding = 0;
		
		Point2D def = new Point2D(this.getLayoutX(),this.getLayoutY());

		connectors = new Point2D[] {def, def, def, def};
		
	}
	public Point2D getConnector(int side) {
		
		return this.localToScene(connectors[side]);
	}
	/*public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}*/
	public void setVelocity(double x, double y) {
		velocityX = x;
		velocityY = y;

	}
	public void setDimension(double width, double height) {
		this.width = width;
		this.height = height;
		connectors[TOP] = new Point2D(width/2 + this.getLayoutX(), this.getLayoutY());
		connectors[RIGHT] = new Point2D(this.getLayoutX() + width, height / 2 + this.getLayoutY());
		connectors[BUTTOM] = new Point2D(width/2 + this.getLayoutX(), this.getLayoutY() + height );
		connectors[LEFT] = new Point2D( this.getLayoutX(), height / 2 + this.getLayoutY());
		
		
	}
	public void update(double time) {
		this.setLayoutX(getLayoutX() + velocityX * time);
		this.setLayoutY(getLayoutY() + velocityY * time);
	}
	public abstract void render();
	
	public Rectangle2D getBoundary() {
		return new Rectangle2D(this.getLayoutX(), this.getLayoutY(), width, height);
	}
	public boolean intersects(Sprite s) {
		 return s.getBoundary().intersects( this.getBoundary() );
	}
	
}
