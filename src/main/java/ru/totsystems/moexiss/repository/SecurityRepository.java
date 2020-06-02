package ru.totsystems.moexiss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.totsystems.moexiss.model.SecurityEntity;

public interface SecurityRepository extends JpaRepository<SecurityEntity, String> {
}
