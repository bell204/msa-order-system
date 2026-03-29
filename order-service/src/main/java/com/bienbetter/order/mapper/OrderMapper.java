package com.bienbetter.shop.order.mapper;

import com.bienbetter.shop.order.entitiy.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order); // 성공 시 1 반환
}