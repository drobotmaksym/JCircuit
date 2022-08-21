package sunmade.jcircuit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sunmade.jcircuit.element.Element;
import sunmade.jcircuit.linker.ElementLinker;
import sunmade.jcircuit.element.pin.Pin;

import static org.junit.jupiter.api.Assertions.*;

public class ElementLinkerTest {
    private Element element;
    private ElementLinker linker;

    @BeforeEach
    void init() {
        element = SampleElement.getCopy();
        linker = new ElementLinker();
    }

    @Test
    void link_ShouldThrowNullPointerException_IfAtLeastOneElementIsNull() {
        assertThrows(NullPointerException.class, () -> linker.link(null, element));
        assertThrows(NullPointerException.class, () -> linker.link(element, null));
        assertThrows(NullPointerException.class, () -> linker.link(null, null));
    }

    @Test
    void link_ShouldThrowIllegalArgumentException_IfTheElementsAreTheSame() {
        assertThrows(IllegalArgumentException.class, () -> linker.link(element, element));
    }

    @Test
    void link_ShouldLinkAllPinsThatIntersectTheElementsPins() {
        Pin pin1_1 = new Pin("P1_1", -5, 0);
        Pin pin1_2 = new Pin("P1_2", 0, -5);
        Pin pin1_3 = new Pin("P1_3", 5, -3);
        Pin pin1_4 = new Pin("P1_4", 5, 0);
        Pin pin1_5 = new Pin("P1_5", 5, 3);
        Pin pin2_1 = new Pin("P2_1", -5, 3);
        Pin pin2_2 = new Pin("P2_2", -5, 0);
        Pin pin2_3 = new Pin("P2_3", -5, -3);
        Pin pin2_4 = new Pin("P2_4", 5, 0);
        Pin pin2_5 = new Pin("P2_5", 0, 5);

        Element element2 = SampleElement.getCopy();
        element2.setX(10);

        element.addPin(pin1_1);
        element.addPin(pin1_2);
        element.addPin(pin1_3);
        element.addPin(pin1_4);
        element.addPin(pin1_5);
        element2.addPin(pin2_1);
        element2.addPin(pin2_2);
        element2.addPin(pin2_3);
        element2.addPin(pin2_4);
        element2.addPin(pin2_5);

        linker.link(element, element2);

        assertTrue(linker.getPinLinker().isLinked(pin1_3));
        assertTrue(linker.getPinLinker().isLinked(pin1_4));
        assertTrue(linker.getPinLinker().isLinked(pin1_5));
        assertTrue(linker.getPinLinker().isLinked(pin2_3));
        assertTrue(linker.getPinLinker().isLinked(pin2_2));
        assertTrue(linker.getPinLinker().isLinked(pin2_1));

        assertEquals(3, linker.getPinLinker().getLinkCount());
    }

    @Test
    void link_ShouldRelinkAllPinsIfTheElementHasLostLinksWithSomePinsOrObtainedNewOnes() {
        Pin pin1_1 = new Pin("P1_1", -5, 0);
        Pin pin1_2 = new Pin("P1_2", 0, -5);
        Pin pin1_3 = new Pin("P1_3", 5, -3);
        Pin pin1_4 = new Pin("P1_4", 5, 0);
        Pin pin1_5 = new Pin("P1_5", 5, 3);
        Pin pin2_1 = new Pin("P2_1", -5, 3);
        Pin pin2_2 = new Pin("P2_2", -5, 0);
        Pin pin2_3 = new Pin("P2_3", -5, -3);
        Pin pin2_4 = new Pin("P2_4", 5, 0);
        Pin pin2_5 = new Pin("P2_5", 0, 5);

        Element element2 = SampleElement.getCopy();
        element2.setX(10);
        element.setX(20);

        element.addPin(pin1_1);
        element.addPin(pin1_2);
        element.addPin(pin1_3);
        element.addPin(pin1_4);
        element.addPin(pin1_5);
        element2.addPin(pin2_1);
        element2.addPin(pin2_2);
        element2.addPin(pin2_3);
        element2.addPin(pin2_4);
        element2.addPin(pin2_5);

        linker.link(element, element2);

        assertTrue(linker.getPinLinker().isLinked(pin1_1));
        assertTrue(linker.getPinLinker().isLinked(pin2_4));
        assertEquals(1, linker.getPinLinker().getLinkCount());
    }

    @Test
    void unlink_ShouldThrowNullPointerException_IfTheElementIsNull() {
        assertThrows(NullPointerException.class, () -> linker.unlink(null));
    }

    @Test
    void unlink_ShouldUnlinkAllPinsOfTheElementThatAreCurrentlyLinked() {
        Element element2 = SampleElement.getCopy();
        element2.setX(10);

        element.addPin(new Pin("P1_1", -5, 0));
        element.addPin(new Pin("P1_2", 0, -5));
        element.addPin(new Pin("P1_3", 5, -3));
        element.addPin(new Pin("P1_4", 5, 0));
        element.addPin(new Pin("P1_5", 5, 3));
        element2.addPin(new Pin("P2_1", -5, 3));
        element2.addPin(new Pin("P2_2", -5, 0));
        element2.addPin(new Pin("P2_3", -5, -3));
        element2.addPin(new Pin("P2_4", 5, 0));
        element2.addPin(new Pin("P2_5", 0, 5));

        linker.link(element, element2);
        linker.unlink(element);

        assertEquals(0, linker.getPinLinker().getLinkCount());
    }
}

