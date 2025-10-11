package upc.edu.pe.parkeoya.backend.v1.profile.application.acl;



import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateDriverCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateParkingOwnerCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetDriverByUserIdAsyncQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetParkingOwnerByUserIdAsyncQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.DriverCommandService;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.DriverQueryService;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.ParkingOwnerCommandService;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.ParkingOwnerQueryService;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.acl.ProfilesContextFacade;
import upc.edu.pe.parkeoya.backend.v1.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import org.springframework.stereotype.Service;

@Service
public class ProfileContextFacadeImpl implements ProfilesContextFacade {
    private final DriverCommandService driverCommandService;
    private final DriverQueryService driverQueryService;
    private final ParkingOwnerCommandService parkingOwnerCommandService;
    private final ParkingOwnerQueryService parkingOwnerQueryService;

    public ProfileContextFacadeImpl(DriverCommandService driverCommandService, DriverQueryService driverQueryService,
                                    ParkingOwnerCommandService parkingOwnerCommandService, ParkingOwnerQueryService parkingOwnerQueryService) {
        this.driverCommandService = driverCommandService;
        this.driverQueryService = driverQueryService;
        this.parkingOwnerCommandService = parkingOwnerCommandService;
        this.parkingOwnerQueryService = parkingOwnerQueryService;
    }

    @Override
    public Long createDriver(String fullName, String city, String country,
                             String phone, String dni, Long userId) {

        CreateDriverCommand command = new CreateDriverCommand(
                fullName, city, country, phone, dni);

        var agriculturalProducer = driverCommandService.handle(command, userId);
        return agriculturalProducer.map(AuditableAbstractAggregateRoot::getId).orElse(0L);
    }

    @Override
    public Long createParkingOwner(String fullName, String city, String country,
                                   String phone, String companyName, String ruc, Long userId) {
        CreateParkingOwnerCommand command = new CreateParkingOwnerCommand(
                fullName, city, country, phone, companyName, ruc);

        var distributor = parkingOwnerCommandService.handle(command, userId);
        return distributor.map(AuditableAbstractAggregateRoot::getId).orElse(0L);
    }

    @Override
    public boolean exitsDriverByUserId(Long userId) {
        var query = new GetDriverByUserIdAsyncQuery(userId);
        var existingProducer = driverQueryService.handle(query);
        return existingProducer.isPresent();
    }

    @Override
    public boolean exitsParkingOwnerByUserId(Long userId) {
        var query = new GetParkingOwnerByUserIdAsyncQuery(userId);
        var existingDistributor = parkingOwnerQueryService.handle(query);
        return existingDistributor.isPresent();
    }
}