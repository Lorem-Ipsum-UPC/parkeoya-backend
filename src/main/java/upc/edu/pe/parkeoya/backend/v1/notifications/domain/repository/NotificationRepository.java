package upc.edu.pe.parkeoya.backend.v1.notifications.domain.repository;

import upc.edu.pe.parkeoya.backend.v1.notifications.domain.model.NotificationMessage;

public interface NotificationRepository {
    void send(NotificationMessage message);
}
