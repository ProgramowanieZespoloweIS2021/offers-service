package com.pz.offersservice.offers.persistence.mapper;

import com.pz.offersservice.offers.order.OrderingCriteria;
import com.pz.offersservice.offers.order.OrderingType;
import org.jooq.SortField;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;

@Component
public class OrderingCriteriaToJooqMapper {

    private SortField<?> convertSingleOrderingCriteria(OrderingCriteria orderingCriteria) {
        return orderingCriteria.getOrderingType().equals(OrderingType.ASCENDING)
                ? field(orderingCriteria.getProperty()).asc()
                : field(orderingCriteria.getProperty()).desc();
    }

    public List<SortField<?>> convert(List<OrderingCriteria> orderingCriteria) {
        return orderingCriteria.stream().map(this::convertSingleOrderingCriteria).collect(Collectors.toList());
    }

}
