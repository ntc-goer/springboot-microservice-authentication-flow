package ntcgoer.emailservice.code;

import lombok.Getter;

@Getter
public enum EmailTemplate {
    TESTING("For testing"),
    VERIFY_EMAIL_SIGN_UP("verifySignupEmailTemplate");

    public final String template;
    EmailTemplate(String t) {
        this.template = t;
    }

}
