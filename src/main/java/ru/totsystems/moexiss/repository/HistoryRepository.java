package ru.totsystems.moexiss.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.totsystems.moexiss.model.HistoryEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    List<HistoryEntity> findAllBySECID_Secid(String secid);

    @Query(value = "  from HistoryEntity h  inner join fetch h.SECID s")
    List<HistoryEntity> getAllForTable(Sort sort);

    @Query(value = "  from HistoryEntity h  " +
            "inner join fetch h.SECID s " +
            "where s.emitent_title like :pattern")
    List<HistoryEntity> getAllForTable(@Param("pattern") String pattern, Sort sort);

    @Query(value = "  from HistoryEntity h  " +
            "inner join fetch h.SECID s " +
            "where h.TRADEDATE >= :startDate " +
            "AND " +
            "h.TRADEDATE <= :endDate")
    List<HistoryEntity> getAllForTable(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       Sort sort);

    @Query(value = "  from HistoryEntity h" +
            "  inner join fetch h.SECID s " +
            "where (h.TRADEDATE >= :startDate " +
            "AND " +
            "h.TRADEDATE <= :endDate) " +
            "and" +
            " s.emitent_title like :pattern")
    List<HistoryEntity> getAllForTable(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       @Param("pattern") String pattern,
                                       Sort sort);


}
