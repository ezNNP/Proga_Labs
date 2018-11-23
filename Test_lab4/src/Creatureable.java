import javafx.scene.control.Label;

public interface Creatureable {
	void sound(Sounds type, String message);
	Label update(Integer id, String shortName, int cellSize);
}