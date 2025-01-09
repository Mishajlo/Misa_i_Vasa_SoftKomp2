package com.example.Rezervacije_Servis.service.spec;

import com.example.Rezervacije_Servis.domain.entities.Table;
import com.example.Rezervacije_Servis.dto.tableDTOs.TableCreationDTO;
import com.example.Rezervacije_Servis.dto.tableDTOs.TableDTO;

import java.util.List;

public interface TableService {

    Table getTableById(long id);
    long createTable(long restaturantId, TableCreationDTO tableCreationDTO);
    List<TableDTO> getTables(long restaurantId);
    long editTable(long id, TableCreationDTO tableCreationDTO);
    long deleteTable(long id);

}
