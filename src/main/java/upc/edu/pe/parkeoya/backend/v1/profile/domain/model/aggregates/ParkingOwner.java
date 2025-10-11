package upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates;



import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateParkingOwnerCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.valueobjects.Phone;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.valueobjects.Ruc;
import upc.edu.pe.parkeoya.backend.v1.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ParkingOwner extends AuditableAbstractAggregateRoot<ParkingOwner> {
    @NotBlank
    private String fullName;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @Embedded
    private Phone phone;

    @NotBlank
    private String companyName;

    @Embedded
    private Ruc ruc;

    @NotNull(message = "UserId cannot be null")
    @Positive(message = "UserId must be positive")
    private Long userId;

    public ParkingOwner(CreateParkingOwnerCommand command, Long userId) {
        this.fullName = command.fullName();
        this.city = command.city();
        this.country = command.country();
        this.phone = new Phone(command.phone());;
        this.companyName = command.companyName();
        this.ruc = new Ruc(command.ruc());
        this.userId = userId;
    }

    public ParkingOwner() {}

    public String getPhone(){
        return phone.phone();
    }

    public String getRuc(){
        return ruc.ruc();
    }
}
