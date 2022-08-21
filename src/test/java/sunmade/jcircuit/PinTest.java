package sunmade.jcircuit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sunmade.jcircuit.element.pin.Pin;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PinTest {
    private Pin pin;

    @BeforeEach
    void init() {
        pin = new Pin("RX20");
    }

    @Test
    void pinConstruction_ShouldResultInNullPointerException_IfTheIdIsNull() {
        assertThrows(NullPointerException.class, () -> new Pin(null));
    }

    @Test
    void setSignal_ShouldThrowNullPointerException_IfItIsNull() {
        assertThrows(NullPointerException.class, () -> pin.setCurrent(null));
    }
}
