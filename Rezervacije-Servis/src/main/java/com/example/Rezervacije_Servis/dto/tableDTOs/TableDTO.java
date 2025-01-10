package com.example.Rezervacije_Servis.dto.tableDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    private long table_id;
    private Boolean smoking_area;
    private Boolean sitting_area;
}
