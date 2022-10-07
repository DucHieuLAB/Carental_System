package com.example.create_entity.Repository;

import com.example.create_entity.Entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> {
    @Query("SELECT DISTINCT(c.capacity)  FROM CarEntity c  ")
    public List<Integer> getListCapacity();
}
