package upc.edu.pe.parkeoya.backend.v1.reviews.application.internal.outboundservices.acl;

import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

@Service("externalProfileServiceReview")
public class ExternalProfileService {
    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public String getDriverFullNameByUserId(Long userId) {
        return this.profilesContextFacade.getDriverFullNameByUserId(userId);
    }

    public String getDriverFullNameByDriverId(Long driverId) {
        return this.profilesContextFacade.getDriverFullNameByDriverId(driverId);
    }
}
