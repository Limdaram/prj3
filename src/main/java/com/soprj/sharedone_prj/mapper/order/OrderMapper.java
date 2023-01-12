package com.soprj.sharedone_prj.mapper.order;

import com.soprj.sharedone_prj.domain.buyer.BuyerDto;
import com.soprj.sharedone_prj.domain.item.ItemDto;
import com.soprj.sharedone_prj.domain.order.ItemListVO;
import com.soprj.sharedone_prj.domain.order.OrderDto;
import com.soprj.sharedone_prj.domain.order.OrderItemDTO;
import com.soprj.sharedone_prj.domain.order.OrderPriceVO;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    List<BuyerDto> buyerSelect();

  BuyerDto buyerList(String m_Buyer_Id);

    List<ItemDto> itemSelect();

//    ItemDto itemList(String m_item_id);

    List<ItemListVO> itemListForDropDown(Object m_order_inserted, Object m_buyer_id);

    OrderPriceVO orderPrice(Object m_order_inserted, Object m_item_id, Object m_buyer_id);

    OrderDto orderInsert();

  int addDataHeader(Map<String, Object> stringObjectMap);

  int addDataItem(Map<String, Object> stringObjectMap);

  List<OrderDto> orderList();

  List<OrderItemDTO> orderDetail(int m_order_id);
}
