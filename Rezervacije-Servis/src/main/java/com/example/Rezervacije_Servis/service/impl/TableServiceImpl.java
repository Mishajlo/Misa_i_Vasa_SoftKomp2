package com.example.Rezervacije_Servis.service.impl;

import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.domain.entities.Table;
import com.example.Rezervacije_Servis.dto.tableDTOs.TableCreationDTO;
import com.example.Rezervacije_Servis.dto.tableDTOs.TableDTO;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import com.example.Rezervacije_Servis.repository.TableRepository;
import com.example.Rezervacije_Servis.service.spec.ReservationService;
import com.example.Rezervacije_Servis.service.spec.TableService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TableServiceImpl implements TableService {

    private final ModelMapper modelMapper;
    private TableRepository tableRepository;
    private RestaurantRepository restaurantRepository;
    private ReservationService reservationService;

    @Override
    public Table getTableById(long id) {
        return tableRepository.findById(id).orElse(null);
    }

    @Override
    public long createTable(long restaturantId, TableCreationDTO tableCreationDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaturantId).orElse(null);
        Table newTable = modelMapper.map(tableCreationDTO, Table.class);
        newTable.setRestaurant(restaurant);
        Table savedTable = tableRepository.save(newTable);
        return savedTable.getId();
    }

    @Override
    public List<TableDTO> getTables(long restaurantId) {
        List<Table> tables = tableRepository.findAllByDeleteFlagFalseAndRestaurant_Id(restaurantId);
        return List.of(modelMapper.map(tables, TableDTO.class));
    }

    @Override
    public long editTable(long id, TableCreationDTO tableCreationDTO) {
        Table table = tableRepository.findById(id).orElse(null);

        if (table == null) {
            return -1;
        }

        if (tableCreationDTO.getSitting_area() != null) {
            table.setSitting_area(tableCreationDTO.getSitting_area());
        }
        if (tableCreationDTO.getSmoking_area() != null) {
            table.setSmoking_area(tableCreationDTO.getSmoking_area());
        }

        tableRepository.save(table);

        return id;
    }

    @Override
    public long deleteTable(long id) {
        Table table = tableRepository.findById(id).orElse(null);

        if (table == null) {
            return -1;
        }

        reservationService.cancelAllReservationsForTable(table.getId());

        table.setDeleteFlag(true);
        tableRepository.save(table);

        return id;
    }
}
