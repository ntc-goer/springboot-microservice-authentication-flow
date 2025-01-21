package ntcgoer.emailservice.code;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailTemplateTest {
    @Test
    public void whenGetLabel_thenValueReturned() {
        String resp = EmailTemplate.TESTING.toString();
        assertThat(resp).isEqualTo("TESTING");
    }

    @Test
    public void whenGetLabelValueOf_thenValueReturned() {
        String resp = EmailTemplate.valueOf("TESTING").toString();
        assertThat(resp).isEqualTo("TESTING");
    }

    @Test
    public void whenGetValueOfLabel_thenValueReturned() {
        String label = "TESTING";
        String data = EmailTemplate.valueOf(label).getTemplate();
        assertThat(data).isEqualTo("For testing");
    }
}
