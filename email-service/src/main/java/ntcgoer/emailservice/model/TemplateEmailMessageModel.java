package ntcgoer.emailservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class TemplateEmailMessageModel {
    private String toEmail;
    private String subject;
    private String template;
    private Map<String,String> data;
}
