package com.example.applocation.repos;

import com.example.applocation.domain.Massage;
import com.example.applocation.domain.User;
import com.example.applocation.domain.dto.MassageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MassageRepo extends CrudRepository<Massage, Long> {

    @Query("select new com.example.applocation.domain.dto.MassageDto(" +
            "m," +
            "count(ml), " +
            "sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from  Massage m left join m.likes ml " +
            "where m.tag = :tag " +
            "group by m"
    )
    Page<MassageDto> findByTag( @Param("tag") String tag, Pageable pageable,  @Param("user") User user);


    @Query("select new com.example.applocation.domain.dto.MassageDto(" +
    "m," +
    "count(ml), " +
    "sum(case when ml = :user then 1 else 0 end) > 0" +
        ") " +
    "from  Massage  m left join m.likes ml " +
    "group by m"
    )
    Page<MassageDto> findAll(Pageable pageable, @Param("user") User user);


    @Query("select new com.example.applocation.domain.dto.MassageDto(" +
            "m," +
            "count(ml), " +
            "sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from  Massage  m left join m.likes ml " +
            "where m.author = :author " +
            "group by m"
    )
    Page<MassageDto> findByAuthor(@Param("author") User author, Pageable pageable,@Param("user") User user);
}
