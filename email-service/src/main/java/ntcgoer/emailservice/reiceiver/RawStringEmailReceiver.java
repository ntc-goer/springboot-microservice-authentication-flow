package ntcgoer.emailservice.reiceiver;

import lombok.AllArgsConstructor;
import ntcgoer.emailservice.service.impl.EmailServiceImpl;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RawStringEmailReceiver {
    EmailServiceImpl emailServiceImpl;
    public void sendMessage(byte[] message) {
        String messageAsString = new String(message);
        emailServiceImpl.sendSimpleMessage("cuongnt.dev.2501@gmail.com", "test", messageAsString);
    }
}
