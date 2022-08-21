package sunmade.jcircuit;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import sunmade.jcircuit.element.Element;

public class SampleElement extends Element {
    private static final WritableImage writableImage = new WritableImage(10, 10);

    public SampleElement(String name, Image image) {
        super(name, image);
    }

    @Override
    public void conduct() {
    }

    public static SampleElement getCopy() {
        return new SampleElement("SampleElement", writableImage);
    }
}
