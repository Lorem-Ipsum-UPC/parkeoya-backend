package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.acl;

public interface ProfilesContextFacade {


    Long createDriver(String fullName, String city, String country,
                      String phone, String dni, Long userId);

    Long createParkingOwner(String fullName, String city, String country,
                            String phone, String companyName, String ruc, Long userId);

    boolean exitsDriverByUserId(Long userId);

    boolean exitsParkingOwnerByUserId(Long userId);

    String getDriverFullNameByUserId(Long userId);

    String getDriverFullNameByDriverId(Long driverId);
}
