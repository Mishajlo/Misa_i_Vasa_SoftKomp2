package com.example.Rezervacije_Servis.repository;

import com.example.Rezervacije_Servis.domain.entities.Reservation;
import com.example.Rezervacije_Servis.dto.reservationDTOs.FilterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    List<Reservation> findAllByUserData_UserIdAndDeleteFlagFalse(long id);

    List<Reservation> findAllByTable_Restaurant_IdAndDeleteFlagFalse(long id);

//    @Query("select r from Reservation r where " +
//            "r.table.restaurant.id = :#{#restaurantId} and " +
//            "r.deleteFlag = false and " +
//            "r.reserved = false and " +
//            "r.capacity = :#{#filter.capacity} and " +
//            "r.table.sitting_area = :#{#filter.outside} and " +
//            "r.table.smoking_area = :#{#filter.smoking} and " +
////            "r.timeslot = :#{#filter.timeslot} and " +
//            "r.table.restaurant.address = :#{#filter.address} and " +
//            "r.table.restaurant.kitchenType = :#{#filter.kitchenType}")
//    List<Reservation> filter(@Param("restaurantId") long restaurantId, @Param("filter") FilterDTO filter);

    default List<Reservation> filter(long restaurantId, FilterDTO filter) {
        return findAll((root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            predicates.getExpressions().add(criteriaBuilder.equal(root.get("table").get("restaurant").get("id"), restaurantId));
            predicates.getExpressions().add(criteriaBuilder.equal(root.get("deleteFlag"), false));
            predicates.getExpressions().add(criteriaBuilder.equal(root.get("reserved"), false));
            if (filter.getCapacity() != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("capacity"), filter.getCapacity()));
            }
            if (filter.getOutside() != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("table").get("sitting_area"), filter.getOutside()));
            }
            if (filter.getSmoking() != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("table").get("smoking_area"), filter.getSmoking()));
            }
            if (filter.getAddress() != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("table").get("restaurant").get("address"), filter.getAddress()));
            }
            if (filter.getKitchenType() != null) {
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("table").get("restaurant").get("kitchenType"), filter.getKitchenType()));
            }
            if(filter.getDate() != null){
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("date"), LocalDate.parse(filter.getDate())));
            }
            if(filter.getStartTime() != null){
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("startTime"), LocalTime.parse(filter.getStartTime())));
            }
            if(filter.getEndTime() != null){
                predicates.getExpressions().add(criteriaBuilder.equal(root.get("endTime"), LocalTime.parse(filter.getEndTime())));
            }
            System.out.println(predicates);
            return predicates;
        });
    }

    List<Reservation> findAllByTable_IdAndDeleteFlagFalse(long table_id);

}
