package com.mytaxi.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import com.mytaxi.datatransferobjects.SearchCriteria;
import com.mytaxi.entities.DriverEntity;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification implements Specification<DriverEntity> {

    private SearchCriteria searchData;

    public SearchSpecification(SearchCriteria searchData) {
        this.searchData = searchData;
    }

    @Override
    public Predicate toPredicate(Root<DriverEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) throws IllegalArgumentException {
        final List<Predicate> predicates = new ArrayList<>();


        //Add Driver related search criteria
        if (searchData.getDriverSearchData() != null) {
            if (searchData.getDriverSearchData().getOnlineStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("onlineStatus"), searchData.getDriverSearchData().getOnlineStatus()));
            }
            if (searchData.getDriverSearchData().getUsername() != null) {
                predicates.add(criteriaBuilder.equal(root.get("username"), searchData.getDriverSearchData().getUsername()));
            }
        }

        //Add Car related search criteria
        if (searchData.getCarSearchData() != null) {
            if (searchData.getCarSearchData().getEngineType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("car").get("engineType"), searchData.getCarSearchData().getEngineType()));
            }
            if (searchData.getCarSearchData().getConvertible() != null) {
                predicates.add(criteriaBuilder.equal(root.get("car").get("convertible"), searchData.getCarSearchData().getConvertible()));
            }
            if (searchData.getCarSearchData().getLicensePlate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("car").get("licensePlate"), searchData.getCarSearchData().getLicensePlate()));
            }
            if (searchData.getCarSearchData().getRating() != null) {
                predicates.add(criteriaBuilder.equal(root.get("car").get("rating"), searchData.getCarSearchData().getRating()));
            }
            if (searchData.getCarSearchData().getSeatCount() != null) {
                predicates.add(criteriaBuilder.equal(root.get("car").get("seatCount"), searchData.getCarSearchData().getSeatCount()));
            }
        }
        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
