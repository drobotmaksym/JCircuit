package sunmade.jcircuit;

import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sunmade.jcircuit.element.Element;
import sunmade.jcircuit.element.pin.Pin;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ElementTest {
    private Element element;

    /*
    Important details:
    We can not have pins with the same id in a single element
    We can not have pins with the same position in a single element
    We can not have pins situated outside element's image
    We can not have pins linked to themselves
     */

    @BeforeEach
    void init() { element = SampleElement.getCopy(); }

    @Test
    void elementConstruction_ShouldResultInNullPointerException_IfAtLeastOneOfTheParametersIsNull() {
        assertThrows(NullPointerException.class, () -> new SampleElement(null, new WritableImage(1, 1)));
        assertThrows(NullPointerException.class, () -> new SampleElement("SomeElement", null));
        assertThrows(NullPointerException.class, () -> new SampleElement(null, null));
    }

    @Test
    void addPin_ShouldThrowNullPointerException_IfThePinIsNull() {
        assertThrows(NullPointerException.class, () -> element.addPin(null));
    }

    @Test
    void addPin_ShouldAddThePinToTheSet_IfItHasNotBeenAddedYet() {
        Pin pin = new Pin("GMX35");

        element.addPin(pin);

        Optional<Pin> pinOptional1 = element.getPin(element.getX() + pin.getRelativeX(), element.getY() + pin.getRelativeY());
        Optional<Pin> pinOptional2 = element.getPin("GMX35");
        if (pinOptional1.isEmpty()) fail();
        if (pinOptional2.isEmpty()) fail();
        assertSame(pinOptional1.get(), pin);
        assertSame(pinOptional2.get(), pin);
        assertTrue(element.hasPin(pin));
    }

    @Test
    void addPin_ShouldDoNothing_IfThePinHasAlreadyBeenAdded() {
        Pin pin1 = new Pin("GMX35", 0, 0);
        Pin pin2 = new Pin("GMX35", 0, 1);
        Pin pin3 = new Pin("0", 1, 1);
        Pin pin4 = new Pin("1", 1, 1);

        element.addPin(pin1);
        element.addPin(pin2);
        element.addPin(pin3);
        element.addPin(pin4);

        assertTrue(element.hasPin(pin1));
        assertTrue(element.hasPin(pin3));
        assertFalse(element.hasPin(pin2));
        assertFalse(element.hasPin(pin4));
    }

    @Test
    void addPin_ShouldThrowIllegalArgumentException_IfThePinIsLocatedOutsideOfTheElementsImage() {
        Pin pin1 = new Pin("J1", 5, 6);
        Pin pin2 = new Pin("J1", 6, 5);
        Pin pin3 = new Pin("J1", -5, -6);
        Pin pin4 = new Pin("J1", -6, -5);

        assertThrows(IllegalArgumentException.class, () -> element.addPin(pin1));
        assertThrows(IllegalArgumentException.class, () -> element.addPin(pin2));
        assertThrows(IllegalArgumentException.class, () -> element.addPin(pin3));
        assertThrows(IllegalArgumentException.class, () -> element.addPin(pin4));
    }

    @Test
    void removePin_ShouldThrowNullPointerException_IfThePinIsNull() {
        assertThrows(NullPointerException.class, () -> element.removePin(null));
    }

    @Test
    void removePin_ShouldRemoveThePinFromTheSet_IfItHasBeenAdded() {
        Pin pin = new Pin("G2");

        element.addPin(pin);
        element.removePin(pin);

        assertFalse(element.hasPin(pin));
        assertFalse(element.getPin("G2").isPresent());
    }

    @Test
    void getPinById_ShouldThrowNullPointerException_IfTheIdIsNull() {
        assertThrows(NullPointerException.class, () -> element.getPin(null));
    }

    @Test
    void getPinById_ShouldReturnAnOptionalWithThePin_IfItHasBeenAdded() {
        Pin pin = new Pin("A1");

        element.addPin(pin);

        Optional<Pin> optionalPin = element.getPin("A1");
        assertTrue(optionalPin.isPresent());
        assertSame(optionalPin.get(), pin);
    }

    @Test
    void getPinById_ShouldReturnAnEmptyOptional_IfThePinHasNotBeenAdded() {
        Pin pin = new Pin("A1");

        Optional<Pin> optionalPin = element.getPin("A1");
        assertFalse(optionalPin.isPresent());
    }

    @Test
    void getPinByPosition_ShouldReturnAnOptionalWithThePinThatLiesByThatPosition_IfThePinHasBeenAdded() {
        Pin pin = new Pin("A1");

        element.addPin(pin);

        Optional<Pin> optionalPin = element.getPin(element.getX() + pin.getRelativeX(), element.getY() + pin.getRelativeY());
        assertTrue(optionalPin.isPresent());
        assertSame(optionalPin.get(), pin);
    }

    @Test
    void getPinByPosition_ShouldReturnAnEmptyOptional_IfThePinHasNotBeenAdded() {
        Pin pin = new Pin("A1");

        Optional<Pin> optionalPin = element.getPin(element.getX() + pin.getRelativeX(), element.getY() + pin.getRelativeY());
        assertTrue(optionalPin.isEmpty());
    }
}
