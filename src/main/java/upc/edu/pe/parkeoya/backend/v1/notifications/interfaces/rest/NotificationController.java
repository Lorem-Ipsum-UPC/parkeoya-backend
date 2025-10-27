package upc.edu.pe.parkeoya.backend.v1.notifications.interfaces.rest;

import upc.edu.pe.parkeoya.backend.v1.notifications.application.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "Notification Management Endpoints")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(
            summary = "Send a push notification to a device",
            description = "Sends a push notification to a specific device using its token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    })
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam String token,
                                                   @RequestParam String title,
                                                   @RequestParam String body) {
        notificationService.sendNotification(token, title, body);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notification sent successfully");
    }
}