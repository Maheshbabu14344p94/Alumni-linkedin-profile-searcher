package com.assignment.alumni.repository;

import com.assignment.alumni.model.AlumniProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AlumniProfileRepository extends JpaRepository<AlumniProfile, Long> {
    @Query("""
      select a from AlumniProfile a
      where lower(a.university) like lower(concat('%', :university, '%'))
        and lower(a.currentRole) like lower(concat('%', :designation, '%'))
        and (:year is null or a.passoutYear = :year)
      order by a.name
      """)
    List<AlumniProfile> search(@Param("university") String university,
                               @Param("designation") String designation,
                               @Param("year") Integer year);
}
