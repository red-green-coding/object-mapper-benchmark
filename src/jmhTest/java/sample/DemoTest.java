package sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.ObjectMapperDtoBenchmark;
import org.junit.jupiter.api.Test;

public class DemoTest {

    @Test
    void canAccessJmhClasses() {
        var sut = new ObjectMapperDtoBenchmark();

        assertThat(sut).isNotNull();
    }
}
