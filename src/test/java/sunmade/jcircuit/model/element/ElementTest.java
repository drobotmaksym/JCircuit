package sunmade.jcircuit.model.element;

import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sunmade.jcircuit.model.element.attribute.Attribute;
import sunmade.jcircuit.model.element.pin.Pin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ElementTest {
    private Element element;

    @BeforeEach
    public void init() {
        element = new Element("Id", new WritableImage(10, 8));
    }

    @Test
    public void addPin_ShouldAddANewPin_IfItHasNotBeenAdded() {
        Pin pin = new Pin("Newcomer");

        element.addPin(pin);

        assertTrue(element.hasPin(pin));
    }

    @Test
    public void addPin_ShouldNotAddANewPin_IfItHasBeenAdded() {
        Pin pin = new Pin("Newcomer");

        element.addPin(pin);
        element.addPin(pin);

        assertSame(1, element.getPins().size());
    }

    @Test
    public void addPin_ShouldThrowNullPointerException_IfThePinIsNull() {
        assertThrows(NullPointerException.class, () -> element.addPin(null));
    }

    @Test
    public void removePin_ShouldRemoveThePin_IfIsHasBeenAdded() {
        Pin pin = new Pin("I3");

        element.addPin(pin);
        element.removePin(pin);

        assertSame(0, element.getPins().size());
    }

    @Test
    public void removePin_ShouldThrowNullPointerException_IfThePinIsNull() {
        assertThrows(NullPointerException.class, () -> element.removePin(null));
    }

    @Test
    public void getPin_ByPoint_ShouldReturnAnOptionalWithThePin_IfItIsAtTheSpecifiedPoint() {
        Pin pin1 = new Pin("P1", 100, 100);
        Pin pin2 = new Pin("P2", -89, 3);

        element.addPin(pin1);
        element.addPin(pin2);

        Optional<Pin> optional1 = element.getPin(pin1.getRelativeX(), pin1.getRelativeY());
        Optional<Pin> optional2 = element.getPin(pin2.getRelativeX(), pin2.getRelativeY());

        assertSame(optional1.orElseThrow(), pin1);
        assertSame(optional2.orElseThrow(), pin2);
    }

    @Test
    public void getPin_ByPoint_ShouldReturnAnEmptyOptional_IfThereIsNoPinAtTheSpecifiedPoint() {
        Pin pin1 = new Pin("P1", 100, 100);
        Pin pin2 = new Pin("P2", -89, 3);

        element.addPin(pin1);
        element.addPin(pin2);

        assertFalse(element.getPin(pin1.getRelativeX(), 10).isPresent());
        assertFalse(element.getPin(-50, pin2.getRelativeY()).isPresent());
    }

    @Test
    public void getPin_ById_ShouldReturnAnOptionalWithThePin_IfItHasTheSpecifiedId() {
        Pin pin = new Pin("IM-RX2");

        element.addPin(pin);

        assertSame(element.getPin(pin.getIdentifier()).orElseThrow(), pin);
    }

    @Test
    public void getPin_ById_ShouldReturnAnEmptyOptional_IfThereIsNoPinWithTheSpecifiedId() {
        Pin pin = new Pin("IM-RX2");

        element.addPin(pin);

        assertTrue(element.getPin("XCR2").isEmpty());
    }

    @Test
    public void getPin_ByIf_ShouldThrowNullPointerException_IfTheIdIsNull() {
        assertThrows(NullPointerException.class, () -> element.getPin(null));
    }

    @Test
    public void hasPin_ShouldReturnTrue_IfThereIsAPinWithTheSpecifiedId() {
        Pin pin = new Pin("A1");

        element.addPin(pin);

        assertTrue(element.hasPin(pin));
    }

    @Test
    public void hasPin_ShouldReturnFalse_IfThereIsNoPinWithTheSpecifiedId() {
        assertFalse(element.hasPin(new Pin("IO")));
    }

    @Test
    public void hasPin_ShouldThrowNullPointerException_IfThePinIsNull() {
        assertThrows(NullPointerException.class, () -> element.hasPin(null));
    }

    @Test
    public void addAttribute_ShouldAddANewAttribute_IfItHasNotBeenAdded() {
        Attribute<Integer> attribute = new Attribute<>("Count", 8);

        element.addAttribute(attribute);

        assertSame(1, element.getAttributes().size());
    }

    @Test
    public void addAttribute_ShouldNotAddANewAttribute_IfItHasBeenAdded() {
        Attribute<Integer> attribute = new Attribute<>("Count", 8);

        element.addAttribute(attribute);
        element.addAttribute(attribute);

        assertSame(1, element.getAttributes().size());
    }

    @Test
    public void addAttribute_ShouldThrowNullPointerException_IfTheAttributeIsNull() {
        assertThrows(NullPointerException.class, () -> element.addAttribute(null));
    }

    @Test
    public void removeAttribute_ShouldRemoveTheAttribute_IfHasBeenAdded() {
        Attribute<Integer> attribute = new Attribute<>("Type", 2);

        element.addAttribute(attribute);
        element.removeAttribute(attribute);

        assertSame(0, element.getAttributes().size());
    }

    @Test
    public void removeAttribute_ShouldThrowNullPointerException_IfTheAttributeIsNull() {
        assertThrows(NullPointerException.class, () -> element.removeAttribute(null));
    }

    @Test
    public void getAttribute_ById_ShouldReturnAnOptionalWithTheAttribute_IfItHasTheSpecifiedId() {
        Attribute<String> attribute = new Attribute<>("Color", "Green");

        element.addAttribute(attribute);

        assertSame(element.getAttribute(attribute.getName()).orElseThrow(), attribute);
    }

    @Test
    public void getAttribute_ById_ShouldReturnAnEmptyOptional_IfThereIsNoAttributeWithTheSpecifiedId() {
        assertFalse(element.getAttribute("Facing").isPresent());
    }

    @Test
    public void getAttribute_ById_ShouldThrowNullPointerException_IfTheIdIsNull() {
        assertThrows(NullPointerException.class, () -> element.getAttribute(null));
    }

    @Test
    public void getAttribute_ByIdAndValueType_ShouldReturnAnOptionalWithTheAttribute_IfItHasTheSpecifiedArguments() {
        Attribute<Boolean> attribute = new Attribute<>("Active", true);

        element.addAttribute(attribute);

        assertSame(element.getAttribute(attribute.getName(), Boolean.class).orElseThrow(), attribute);
    }

    @Test
    public void getAttribute_ByIdAndValueType_ShouldReturnAnEmptyOptional_IfThereIsAnAttributeWithTheSpecifiedIdButWithADifferentType() {
        Attribute<Boolean> attribute = new Attribute<>("Active", true);

        element.addAttribute(attribute);

        assertTrue(element.getAttribute(attribute.getName(), String.class).isEmpty());
    }

    @Test
    public void getAttribute_ByIdAndValueType_ShouldReturnAnEmptyOptional_IfThereIsNoAttributeWithTheSpecifiedArguments() {
        assertFalse(element.getAttribute("Non-existing", Integer.class).isPresent());
    }

    @Test
    public void getAttribute_ByIdAndValueType_ShouldThrowNullPointerException_IfAtLeastOneOfTheArgumentsIsNull() {
        assertThrows(NullPointerException.class, () -> element.getAttribute(null, null));
        assertThrows(NullPointerException.class, () -> element.getAttribute("", null));
        assertThrows(NullPointerException.class, () -> element.getAttribute(null, String.class));
    }

    @Test
    public void getAttributes_ShouldReturnASetOfAllAttributesWithTheSpecifiedValueType() {
        Attribute<String> attribute1 = new Attribute<>("A1", "Attribute1");
        Attribute<String> attribute2 = new Attribute<>("A2", "Attribute2");
        Attribute<String> attribute3 = new Attribute<>("A3", "Attribute3");
        Attribute<Double> attribute4 = new Attribute<>("A4", 0.4);

        element.addAttribute(attribute1);
        element.addAttribute(attribute2);
        element.addAttribute(attribute3);
        element.addAttribute(attribute4);

        Set<Attribute<String>> expected = new HashSet<>();
        expected.add(attribute1);
        expected.add(attribute2);
        expected.add(attribute3);

        assertIterableEquals(expected, element.getAttributes(String.class));
    }

    @Test
    public void getAttributes_ShouldThrowNullPointerException_IfTheValueTypeIsNull() {
        assertThrows(NullPointerException.class, () -> element.getAttributes(null));
    }

    @Test
    public void getClone_ShouldReturnACloneOfTheElement() {
        Pin pin1 = new Pin("P1", 1, 2);
        Pin pin2 = new Pin("P1", 2, 1);
        Pin pin3 = new Pin("P1", -1, -1);
        Attribute<String> attribute1 = new Attribute<>("A1", "Hello, World!");
        Attribute<Double> attribute2 = new Attribute<>("A2", 3.1415);
        Attribute<Boolean> attribute3 = new Attribute<>("A3", false);

        element.addPin(pin1);
        element.addPin(pin2);
        element.addPin(pin3);
        element.addAttribute(attribute1);
        element.addAttribute(attribute2);
        element.addAttribute(attribute3);

        Element clone = element.getClone();

        assertEquals(element.getName(), clone.getName());                       // Names are copied
        assertIterableEquals(element.getPins(), clone.getPins());               // Pins  are cloned
        assertIterableEquals(element.getAttributes(), clone.getAttributes());   // Attributes are cloned

        // Pins should point to the previous memory address
        for (Pin pin : element.getPins()) {
            assertNotSame(
                    pin,
                    clone.getPin(pin.getIdentifier()).orElseThrow()
            );
        }

        // Attributes should point to the previous memory address
        for (Attribute<?> attribute : element.getAttributes()) {
            assertNotSame(
                    attribute,
                    clone.getAttribute(attribute.getName()).orElseThrow()
            );
        }
    }
}
