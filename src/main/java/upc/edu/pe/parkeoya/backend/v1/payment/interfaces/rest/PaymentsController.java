package upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest;

import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.aggregates.ReservationPayment;
import upc.edu.pe.parkeoya.backend.v1.payment.domain.services.PaymentCommandService;
import upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.resources.CreatePaymentResource;
import upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.resources.PaymentResource;
import upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;
import upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/payments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Payment", description = "Payment endpoints for managing payments in the Smart Parking system.")
public class PaymentsController {
    private final PaymentCommandService paymentCommandService;

    public PaymentsController(PaymentCommandService paymentCommandService) {
        this.paymentCommandService = paymentCommandService;
    }

    @Operation(summary = "Create a new reservation payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/reservation/{reservationId}")
    public ResponseEntity<PaymentResource> createReservationPayment(@RequestBody CreatePaymentResource resource,
                                                                    @PathVariable Long reservationId) {

        Optional<ReservationPayment> payment = paymentCommandService.handleReservationPayment(
                CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource),
                reservationId);

        return payment.map(source ->
                        new ResponseEntity<>(PaymentResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


}
