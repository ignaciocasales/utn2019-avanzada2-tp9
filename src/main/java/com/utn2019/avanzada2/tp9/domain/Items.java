package com.utn2019.avanzada2.tp9.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Items<T> {
    private Long total;
    private List<T> items;
}
