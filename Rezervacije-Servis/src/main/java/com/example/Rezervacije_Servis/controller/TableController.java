package com.example.Rezervacije_Servis.controller;

import com.example.Rezervacije_Servis.dto.tableDTOs.TableCreationDTO;
import com.example.Rezervacije_Servis.dto.tableDTOs.TableDTO;
import com.example.Rezervacije_Servis.security.CheckSecurity;
import com.example.Rezervacije_Servis.service.spec.TableService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/table")
@AllArgsConstructor
public class TableController {

    private TableService tableService;

    @CheckSecurity(roles = {"MANAGER"})
    @PostMapping(value = "/{restaurant_id}")
    public ResponseEntity<Long> createTable(@PathVariable Long restaurant_id, @RequestBody TableCreationDTO TableCreationDTO) {
        return ResponseEntity.ok(tableService.createTable(restaurant_id, TableCreationDTO));
    }

    @CheckSecurity(roles = {"MANAGER"})
    @GetMapping(value = "/{restaurant_id}")
    public ResponseEntity<List<TableDTO>> getAllTables(@PathVariable Long restaurant_id) {
        return ResponseEntity.ok(tableService.getTables(restaurant_id));
    }

    @CheckSecurity(roles = {"MANAGER"})
    @PatchMapping(value = "/{table_id}")
    public ResponseEntity<Long> updateTable(@PathVariable Long table_id, @RequestBody TableCreationDTO TableCreationDTO) {
        return ResponseEntity.ok(tableService.editTable(table_id, TableCreationDTO));
    }

    @CheckSecurity(roles = {"MANAGER"})
    @DeleteMapping(value = "/{table_id}")
    public ResponseEntity<Long> deleteTable(@PathVariable Long table_id) {
        return ResponseEntity.ok(tableService.deleteTable(table_id));
    }

}
