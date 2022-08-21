package sunmade.jcircuit;

import org.junit.jupiter.api.Test;
import sunmade.jcircuit.linker.PinLink;
import sunmade.jcircuit.element.pin.Pin;

import static org.junit.jupiter.api.Assertions.*;

public class PinLinkTest {

    @Test
    void pinLinkConstruction_ShouldResultInNullPointerException_IfAtLeastOnePinIsNull() {
        Pin pin = new Pin("R1");
        assertThrows(NullPointerException.class, () -> new PinLink(null, pin));
        assertThrows(NullPointerException.class, () -> new PinLink(pin, null));
        assertThrows(NullPointerException.class, () -> new PinLink(null, null));
    }

    @Test
    void pinLinkConstruction_ShouldResultInIllegalArgumentException_IfTheIdsOfThePinsAreTheSame() {
        assertThrows(IllegalArgumentException.class, () -> new PinLink(new Pin("0"), new Pin("0")));
    }

    @Test
    void getLinkedPin_ShouldReturnAnOptionalWithTheLinkedPin_IfSuchPinExists() {
        Pin firstPin = new Pin("A1");
        Pin secondPin = new Pin("A2");
        PinLink link = new PinLink(firstPin, secondPin);
        assertSame(firstPin, link.getLinkedPin(secondPin).orElseThrow());
        assertSame(secondPin, link.getLinkedPin(firstPin).orElseThrow());
    }

    @Test
    void getLinkedPin_ShouldReturnAnEmptyOptional_IfTheLinkDoesNotContainTheLinkedPin() {
        Pin firstPin = new Pin("A1");
        Pin secondPin = new Pin("A2");
        PinLink link = new PinLink(firstPin, secondPin);
        assertTrue(link.getLinkedPin(new Pin("Random Pin")).isEmpty());
    }

    @Test
    void getLinkedPin_ShouldThrowNullPointerException_IfThePinIsNull() {
        Pin firstPin = new Pin("A1");
        Pin secondPin = new Pin("A2");
        PinLink link = new PinLink(firstPin, secondPin);
        assertThrows(NullPointerException.class, () -> link.getLinkedPin(null));
    }
}
