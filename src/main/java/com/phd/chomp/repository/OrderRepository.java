package com.phd.chomp.repository;

import com.phd.chomp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /*@Query("select o from Order o where o.member.id = :id order by o.orderDate desc ")
    List<Order> findOrders(@Param("id") String id, Pageable pageable);*/

}
