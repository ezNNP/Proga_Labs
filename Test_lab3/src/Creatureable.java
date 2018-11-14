import javafx.scene.canvas.GraphicsContext;

public interface Creatureable {
	void sound(Sounds type, String message);
	void update(String shortName, int cellSize);
}