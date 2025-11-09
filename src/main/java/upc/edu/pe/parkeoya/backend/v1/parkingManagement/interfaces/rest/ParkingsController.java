package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest;

import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.entities.ParkingSpot;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.queries.GetAllParkingQuery;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.queries.GetParkingByIdQuery;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.queries.GetParkingSpotsByParkingIdQuery;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.queries.GetParkingsByOwnerIdQuery;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.services.ParkingCommandService;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.services.ParkingQueryService;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.AddParkingSpotResource;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.CreateParkingResource;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.ParkingResource;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.ParkingSpotResource;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform.AddParkingSpotCommandFromResourceAssembler;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform.CreateParkingCommandFromResourceAssembler;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform.ParkingResourceFromEntityAssembler;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform.ParkingSpotResourceFromEntityAssembler;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.UpdateParkingResource;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform.UpdateParkingCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/parkings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Parking", description = "Parking management")
public class ParkingsController {
    private final ParkingCommandService parkingCommandService;
    private final ParkingQueryService parkingQueryService;

    public ParkingsController(ParkingCommandService parkingCommandService, ParkingQueryService parkingQueryService) {
        this.parkingCommandService = parkingCommandService;
        this.parkingQueryService = parkingQueryService;
    }

    @Operation(summary = "Create a new parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<ParkingResource> createParking(@RequestBody CreateParkingResource resource) {
        Optional<Parking> parking = this.parkingCommandService
                .handle(CreateParkingCommandFromResourceAssembler.toCommandFromResource(resource));

        return parking.map(source->
                        new ResponseEntity<>(ParkingResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Add a parking spot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parking spot added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/{parkingId}/spots")
    public ResponseEntity<ParkingSpotResource> addParkingSpot(@PathVariable Long parkingId,
                                                              @RequestBody AddParkingSpotResource resource) {
        Optional<ParkingSpot> parkingSpot = this.parkingCommandService
                .handle(AddParkingSpotCommandFromResourceAssembler.toCommandFromResource(resource, parkingId));

        return parkingSpot.map(source ->
                        new ResponseEntity<>(ParkingSpotResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get all parking spots by parking id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parkings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Parking not found")
    })
    @GetMapping("/{parkingId}/spots")
    public ResponseEntity<List<ParkingSpotResource>> getParkingSpotsByParkingId(@PathVariable Long parkingId) {
        var query = new GetParkingSpotsByParkingIdQuery(parkingId);
        var spots = this.parkingQueryService.handle(query);

        if (spots.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var parkingSpotResources = spots.stream()
                .map(ParkingSpotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(parkingSpotResources);
    }

    @Operation(summary = "Get parking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Parking not found")
    })
    @GetMapping("/{parkingId}")
    public ResponseEntity<ParkingResource> getParkingById(@PathVariable Long parkingId) {
        var query = new GetParkingByIdQuery(parkingId);
        var parking = this.parkingQueryService.handle(query);

        return parking.map(source ->
                        new ResponseEntity<>(ParkingResourceFromEntityAssembler.toResourceFromEntity(source), OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all parkings by owner id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parkings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Parking not found")
    })
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingResource>> getParkingsByOwnerId(@PathVariable Long ownerId) {
        var query = new GetParkingsByOwnerIdQuery(ownerId);
        var parkingList = this.parkingQueryService.handle(query);

        if (parkingList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var parkingResources = parkingList.stream()
                .map(ParkingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(parkingResources);
    }

    @Operation(summary = "Get all parkings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parkings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Parking not found")
    })
    @GetMapping
    public ResponseEntity<List<ParkingResource>> getAllParkings() {
        var query = new GetAllParkingQuery();
        var parkingList = this.parkingQueryService.handle(query);

        if (parkingList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var parkingResources = parkingList.stream().map(ParkingResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(parkingResources);
    }

        @Operation(summary = "Update a parking")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Parking updated successfully"),
                        @ApiResponse(responseCode = "404", description = "Parking not found"),
                        @ApiResponse(responseCode = "400", description = "Invalid request data")
        })
        @PatchMapping("/{parkingId}")
        public ResponseEntity<ParkingResource> updateParking(@PathVariable Long parkingId,
                                                                                                                 @RequestBody UpdateParkingResource resource) {

                var command = UpdateParkingCommandFromResourceAssembler.toCommandFromResource(resource, parkingId);
                Optional<Parking> updated = this.parkingCommandService.handle(command);

                return updated.map(p -> ResponseEntity.ok(ParkingResourceFromEntityAssembler.toResourceFromEntity(p)))
                                .orElseGet(() -> ResponseEntity.notFound().build());
        }
}
