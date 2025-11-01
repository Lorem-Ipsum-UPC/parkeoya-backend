package upc.edu.pe.parkeoya.backend.v1.shared.infrastructure.persistence.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing Configuration
 * <p>
 * This configuration enables JPA auditing for automatic management of
 * {@link org.springframework.data.annotation.CreatedDate} and
 * {@link org.springframework.data.annotation.LastModifiedDate} annotations
 * in entity classes that extend {@link upc.edu.pe.parkeoya.backend.v1.shared.domain.model.aggregates.AuditableAbstractAggregateRoot}.
 * </p>
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
