package sunmade.jcircuit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sunmade.jcircuit.linker.PinLink;
import sunmade.jcircuit.linker.PinLinker;
import sunmade.jcircuit.element.pin.Pin;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PinLinkerTest {
    private PinLinker linker;

    @BeforeEach
    void init() {
        linker = new PinLinker();
    }

    @Test
    void link_ShouldThrowNullPointerException_IfAtLeastOnePinIsNull() {
        Pin pin = new Pin("C1");

        assertThrows(NullPointerException.class, () -> linker.link(null, pin));
        assertThrows(NullPointerException.class, () -> linker.link(pin, null));
        assertThrows(NullPointerException.class, () -> linker.link(null, null));

        /*
         Test other methods and prove that above-called methods did not result in wrong behaviour
         */
        assertEquals(0, linker.getLinkCount());
        assertFalse(linker.isLinked(pin));
        assertTrue(linker.getLinks(pin).isEmpty());
    }

    @Test
    void link_ShouldThrowIllegalArgumentException_IfTwoPinsWithTheSameIdAreGettingLinked() {
        Pin pin1 = new Pin("C1");
        Pin pin2 = new Pin("C1");

        assertThrows(IllegalArgumentException.class, () -> linker.link(pin1, pin1));
        assertThrows(IllegalArgumentException.class, () -> linker.link(pin2, pin2));
        assertThrows(IllegalArgumentException.class, () -> linker.link(pin1, pin2));

        /*
         Test other methods and prove that above-called methods did not result in wrong behaviour
         */
        assertEquals(0, linker.getLinkCount());

        assertFalse(linker.isLinked(pin1));
        assertTrue(linker.getLinks(pin1).isEmpty());

        assertFalse(linker.isLinked(pin2));
        assertTrue(linker.getLinks(pin2).isEmpty());
    }

    @Test
    void link_ShouldIncreaseLinkCount() {
        Pin pin1 = new Pin("R1");
        Pin pin2 = new Pin("R2");

        int initialLinkCount = linker.getLinkCount();
        linker.link(pin1, pin2);
        int resultingLinkCount = linker.getLinkCount();

        assertEquals(1, linker.getLinks(pin1).size());
        assertEquals(1, linker.getLinks(pin2).size());

        assertEquals(0, initialLinkCount);
        assertEquals(1, resultingLinkCount);

        /*
         Test other methods and prove that above-called methods did not result in wrong behaviour
         */
        assertTrue(linker.isLinked(pin1));
        assertTrue(linker.isLinked(pin2));
        assertEquals(1, linker.getLinks(pin1).size());
        assertEquals(1, linker.getLinks(pin2).size());
        assertEquals(1, linker.getLinkCount());
    }

    @Test
    void link_ShouldCreateALinkWithThePins() {
        Pin pin1 = new Pin("R1");
        Pin pin2 = new Pin("R2");

        linker.link(pin1, pin2);

        // The fact, that the list of links in this case contains only one link, has been proven by the test above
        List<PinLink> links = linker.getLinks(pin1);
        PinLink link = links.get(0);

        assertTrue((link.getFirstPin() == pin1 & link.getSecondPin() == pin2) | (link.getFirstPin() == pin2 & link.getSecondPin() == pin1));
    }

    @Test
    void link_ShouldDoNothingIfThePinsHaveAlreadyBeenLinked() {
        Pin pin1 = new Pin("R1");
        Pin pin2 = new Pin("R2");

        linker.link(pin1, pin2);
        linker.link(pin1, pin2);

        assertEquals(1, linker.getLinkCount());
        assertEquals(1, linker.getLinks(pin1).size());
        assertEquals(1, linker.getLinks(pin2).size());
    }

    @Test
    void unlink_ShouldThrowNullPointerException_IfThePinIsNull() {
        assertThrows(NullPointerException.class, () -> linker.unlink(null));
    }

    @Test
    void unlink_ShouldDecreaseTheLinkCount_IfThePinHasBeenLinkedAtLeastOnce() {
        Pin pin1 = new Pin("R1");
        Pin pin2 = new Pin("R2");
        Pin pin3 = new Pin("R3");
        Pin pin4 = new Pin("R4");

        linker.link(pin1, pin2);
        linker.link(pin3, pin4);

        int initialLinkCount = linker.getLinkCount();

        linker.unlink(pin1);

        int intermediateLinkCount = linker.getLinkCount();

        linker.link(pin1, pin2);
        linker.unlink(pin2);

        int resultingLinkCount = linker.getLinkCount();

        assertEquals(2, initialLinkCount);
        assertEquals(1, intermediateLinkCount);
        assertEquals(1, resultingLinkCount);
    }

    @Test
    void unlink_ShouldRemoveOnlyTheLinksThatInvolveTheGivenPin() {
        Pin pin1 = new Pin("P1");
        Pin pin2 = new Pin("P2");
        Pin pin3 = new Pin("P3");
        Pin pin4 = new Pin("P4");

        linker.link(pin1, pin2);
        linker.link(pin3, pin4);

        linker.unlink(pin1);

        assertEquals(0, linker.getLinks(pin1).size());
        assertEquals(0, linker.getLinks(pin2).size());
        assertEquals(1, linker.getLinks(pin3).size());
        assertEquals(1, linker.getLinks(pin4).size());

        assertEquals(1, linker.getLinkCount());
    }

    @Test
    void getLinks_ShouldReturnAllLinksOfThePin_IfTheyHaveBeenMade() {
        Pin pin1 = new Pin("P1");
        Pin pin2 = new Pin("P2");
        Pin pin3 = new Pin("P3");
        Pin pin4 = new Pin("P4");
        Pin pin5 = new Pin("P5");

        linker.link(pin1, pin2);
        linker.link(pin3, pin4);
        linker.link(pin4, pin5);

        List<PinLink> pin1Links = linker.getLinks(pin1);
        List<PinLink> pin2Links = linker.getLinks(pin2);
        List<PinLink> pin3Links = linker.getLinks(pin3);
        List<PinLink> pin4Links = linker.getLinks(pin4);
        List<PinLink> pin5Links = linker.getLinks(pin5);

        // Only three links should have been created
        assertEquals(3, linker.getLinkCount());

        // Check if each pin participates in the expected number of links
        assertEquals(1, pin1Links.size());
        assertEquals(1, pin2Links.size());
        assertEquals(1, pin3Links.size());
        assertEquals(2, pin4Links.size());
        assertEquals(1, pin5Links.size());

        PinLink pin1Link = pin1Links.get(0);
        PinLink pin3Link = pin3Links.get(0);

        // Check whether the expected pins are inside the links or not
        assertTrue(
                (pin1Link.getFirstPin() == pin1 & pin1Link.getSecondPin() == pin2) |
                        (pin1Link.getFirstPin() == pin2 & pin1Link.getSecondPin() == pin1)
        );
        assertTrue(
                (pin3Link.getFirstPin() == pin3 & pin3Link.getSecondPin() == pin4) |
                        (pin3Link.getFirstPin() == pin4 & pin3Link.getSecondPin() == pin3)
        );
    }

    @Test
    void isLinked_ShouldReturnTrue_IfThePinIsLinked() {
        Pin pin1 = new Pin("L1");
        Pin pin2 = new Pin("L2");
        Pin pin3 = new Pin("L3");

        linker.link(pin1, pin2);
        linker.link(pin1, pin3);

        assertTrue(linker.isLinked(pin1));
        assertTrue(linker.isLinked(pin2));
        assertTrue(linker.isLinked(pin3));
    }

    @Test
    void isLinked_ShouldReturnFalse_IfThePinIsNotLinked() {
        Pin pin1 = new Pin("L1");
        Pin pin2 = new Pin("L2");

        assertFalse(linker.isLinked(pin1));
        assertFalse(linker.isLinked(pin2));
    }

    @Test
    void getLinkedPins_ShouldThrowNullPointerException_IfThePinIsNull() {
        assertThrows(NullPointerException.class, () -> linker.getLinkedPins(null));
    }

    @Test
    void getLinkedPins_ShouldReturnAnEmptyList_IfThereAreNoPinsThatAreLinkedWithTheSpecifiedPin() {
        Pin pin = new Pin("Lonely pin");
        assertTrue(linker.getLinkedPins(pin).isEmpty());
    }

    @Test
    void getLinkedPins_ShouldReturnAllPinsThatAreLinkedWithTheSpecifiedPin() {
        Pin pin1 = new Pin("L1");
        Pin pin2 = new Pin("L2");
        Pin pin3 = new Pin("L3");

        linker.link(pin1, pin2);
        linker.link(pin1, pin3);

        List<Pin> linkedPins1 = linker.getLinkedPins(pin1);
        List<Pin> linkedPins2 = linker.getLinkedPins(pin2);
        List<Pin> linkedPins3 = linker.getLinkedPins(pin3);

        assertSame(2, linkedPins1.size());
        assertSame(1, linkedPins2.size());
        assertSame(1, linkedPins3.size());

        assertTrue(linkedPins1.contains(pin2));
        assertTrue(linkedPins1.contains(pin3));

        assertTrue(linkedPins2.contains(pin1));
        assertTrue(linkedPins3.contains(pin1));

    }
}
