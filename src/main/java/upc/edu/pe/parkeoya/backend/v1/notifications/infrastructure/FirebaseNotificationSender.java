package upc.edu.pe.parkeoya.backend.v1.notifications.infrastructure;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import upc.edu.pe.parkeoya.backend.v1.notifications.domain.model.NotificationMessage;
import upc.edu.pe.parkeoya.backend.v1.notifications.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "firebase", name = "enabled", havingValue = "true", matchIfMissing = false)
public class FirebaseNotificationSender implements NotificationRepository {

    @Override
    public void send(NotificationMessage message) {
        Message firebaseMessage = Message.builder()
                .setToken(message.getToken())
                .setNotification(
                        Notification.builder()
                                .setTitle(message.getTitle())
                                .setBody(message.getBody())
                                .build()
                )
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(firebaseMessage);
            System.out.println("Notificación enviada. ID: " + response);
        } catch (Exception e) {
            System.err.println("Error al enviar notificación:");
            e.printStackTrace();
        }
    }
}
