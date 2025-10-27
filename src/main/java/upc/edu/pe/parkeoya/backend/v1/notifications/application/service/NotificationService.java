package upc.edu.pe.parkeoya.backend.v1.notifications.application.service;

import upc.edu.pe.parkeoya.backend.v1.notifications.domain.model.NotificationMessage;
import upc.edu.pe.parkeoya.backend.v1.notifications.domain.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(String token, String title, String body) {
        NotificationMessage message = new NotificationMessage(token, title, body);
        notificationRepository.send(message);
    }
}