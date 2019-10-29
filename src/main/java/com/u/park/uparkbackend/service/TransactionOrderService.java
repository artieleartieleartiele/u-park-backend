package com.u.park.uparkbackend.service;

import com.u.park.uparkbackend.dto.TransactionOrderDto;
import com.u.park.uparkbackend.model.ParkingLot;
import com.u.park.uparkbackend.model.TransactionOrder;
import com.u.park.uparkbackend.repository.ParkingLotRepository;
import com.u.park.uparkbackend.repository.TransactionOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionOrderService {

    @Autowired
    private TransactionOrderRepository transactionOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TransactionOrder saveTransactionOrder(TransactionOrder transactionOrder) {
        transactionOrder.setCheckIn(getCurrentTime());
        return transactionOrderRepository.save(transactionOrder);
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public List<TransactionOrderDto> getTransactionOrdersOfUser(Long userId) {
        List<TransactionOrder> transactionOrderList = transactionOrderRepository.findByUserId(userId);
        List<TransactionOrderDto> transactionOrderDtoList = new ArrayList<>();
        for (TransactionOrder transactionOrder : transactionOrderList) {
            TransactionOrderDto transactionOrderDto = modelMapper.map(transactionOrder, TransactionOrderDto.class);
            transactionOrderDto.setParkingLotName(getParkingLotNameById(transactionOrder.getParkingLotId()));
            transactionOrderDtoList.add(transactionOrderDto);
        }
        return transactionOrderDtoList;
    }

    private String getParkingLotNameById(Long parkingLotId) {
        return parkingLotRepository.findById(parkingLotId)
                .map(ParkingLot::getName)
                .orElse(null);
    }

    public TransactionOrder getActiveTransactionOrder(Long userId) {
        return transactionOrderRepository.findOneActiveTransactionByUserId(userId);
    }
}
