package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest;


import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetDriverByUserIdAsyncQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetParkingOwnerByUserIdAsyncQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.DriverQueryService;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.ParkingOwnerQueryService;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.DriverResource;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.ParkingOwnerResource;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform.DriverResourceFromEntityAssembler;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform.ParkingOwnerResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profiles Management Endpoints")
public class ProfileController {
    private final DriverQueryService agriculturalProducerQueryService;
    private final ParkingOwnerQueryService distributorQueryService;

    public ProfileController(DriverQueryService agriculturalProducerQueryService,
                             ParkingOwnerQueryService distributorQueryService) {
        this.agriculturalProducerQueryService = agriculturalProducerQueryService;
        this.distributorQueryService = distributorQueryService;
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
}
