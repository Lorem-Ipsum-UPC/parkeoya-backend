package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest;


import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.Driver;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.ParkingOwner;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetDriverByUserIdAsyncQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetParkingOwnerByUserIdAsyncQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.DriverCommandService;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.DriverQueryService;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.ParkingOwnerCommandService;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.ParkingOwnerQueryService;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.DriverResource;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.ParkingOwnerResource;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.UpdateDriverResource;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.UpdateParkingOwnerResource;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform.DriverResourceFromEntityAssembler;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform.ParkingOwnerResourceFromEntityAssembler;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform.UpdateDriverCommandFromResourceAssembler;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform.UpdateParkingOwnerCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profiles Management Endpoints")
public class ProfileController {
    private final DriverQueryService agriculturalProducerQueryService;
    private final DriverCommandService driverCommandService;
    private final ParkingOwnerQueryService distributorQueryService;
    private final ParkingOwnerCommandService parkingOwnerCommandService;

    public ProfileController(DriverQueryService agriculturalProducerQueryService,
                             DriverCommandService driverCommandService,
                             ParkingOwnerQueryService distributorQueryService,
                             ParkingOwnerCommandService parkingOwnerCommandService) {
        this.agriculturalProducerQueryService = agriculturalProducerQueryService;
        this.driverCommandService = driverCommandService;
        this.distributorQueryService = distributorQueryService;
        this.parkingOwnerCommandService = parkingOwnerCommandService;
    }

    @GetMapping(value = "/driver/{userId}")
    public ResponseEntity<DriverResource> getDriverByUserId(@PathVariable Long userId) {
        var getAgriculturalProducerByUserIdQuery = new GetDriverByUserIdAsyncQuery(userId);
        var agriculturalProducer = agriculturalProducerQueryService.handle(getAgriculturalProducerByUserIdQuery);
        if (agriculturalProducer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var agriculturalProducerResource = DriverResourceFromEntityAssembler
                .toResourceFromEntity(agriculturalProducer.get());

        return ResponseEntity.ok(agriculturalProducerResource);
    }

    @Operation(summary = "Update driver profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver profile updated successfully"),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PatchMapping("/driver/{driverId}")
    public ResponseEntity<DriverResource> updateDriver(@PathVariable Long driverId,
                                                        @RequestBody UpdateDriverResource resource) {
        var command = UpdateDriverCommandFromResourceAssembler.toCommandFromResource(resource, driverId);
        Optional<Driver> updated = this.driverCommandService.handle(command);

        return updated.map(d -> ResponseEntity.ok(DriverResourceFromEntityAssembler.toResourceFromEntity(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/parking-owner/{userId}")
    public ResponseEntity<ParkingOwnerResource> getParkingOwnerByUserId(@PathVariable Long userId) {
        var getDistributorByUserIdQuery = new GetParkingOwnerByUserIdAsyncQuery(userId);
        var distributor = distributorQueryService.handle(getDistributorByUserIdQuery);
        if (distributor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var distributorResource = ParkingOwnerResourceFromEntityAssembler.toResourceFromEntity(distributor.get());

        return ResponseEntity.ok(distributorResource);
    }

    @Operation(summary = "Update parking owner profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking owner profile updated successfully"),
            @ApiResponse(responseCode = "404", description = "Parking owner not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PatchMapping("/parking-owner/{parkingOwnerId}")
    public ResponseEntity<ParkingOwnerResource> updateParkingOwner(@PathVariable Long parkingOwnerId,
                                                                    @RequestBody UpdateParkingOwnerResource resource) {
        var command = UpdateParkingOwnerCommandFromResourceAssembler.toCommandFromResource(resource, parkingOwnerId);
        Optional<ParkingOwner> updated = this.parkingOwnerCommandService.handle(command);

        return updated.map(po -> ResponseEntity.ok(ParkingOwnerResourceFromEntityAssembler.toResourceFromEntity(po)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
