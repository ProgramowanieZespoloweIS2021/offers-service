package com.pz.offersservice.offers.dao;

import com.pz.offersservice.offers.service.OrderingCriteria;
import org.jooq.Field;
import org.jooq.SortField;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;

@Component
public class OrderingCriteriaToSortFieldsConverter {

    private SortField<?> convertSingleOrderingCriteria(OrderingCriteria orderingCriteria) {
        Field<?> orderingField = field(orderingCriteria.getProperty());
        if(orderingCriteria.getDirection().equals("descending")) {
            return orderingField.desc();
        }
        else if(orderingCriteria.getDirection().equals("ascending")) {
            return orderingField.asc();
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ordering criteria specification");
        }
    }

    public List<SortField<?>> convert(List<OrderingCriteria> orderingCriteria) {
        return orderingCriteria.stream().map(this::convertSingleOrderingCriteria).collect(Collectors.toList());
    }

}
