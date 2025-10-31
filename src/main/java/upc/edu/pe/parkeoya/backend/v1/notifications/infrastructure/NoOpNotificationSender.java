package upc.edu.pe.parkeoya.backend.v1.notifications.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import upc.edu.pe.parkeoya.backend.v1.notifications.domain.model.NotificationMessage;
import upc.edu.pe.parkeoya.backend.v1.notifications.domain.repository.NotificationRepository;

@Component
@ConditionalOnProperty(prefix = "firebase", name = "enabled", havingValue = "false", matchIfMissing = true)
public class NoOpNotificationSender implements NotificationRepository {

    private static final Logger logger = LoggerFactory.getLogger(NoOpNotificationSender.class);

    @Override
    public void send(NotificationMessage message) {
        // No hace nada, solo loguea
        logger.info("Notificación simulada (Firebase desactivado) - Título: {}, Cuerpo: {}, Token: {}",
                message.getTitle(), message.getBody(), message.getToken());
    }
}

