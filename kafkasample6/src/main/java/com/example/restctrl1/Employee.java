package com.example.restctrl1;


import lombok.Builder;
import lombok.With;

@With
@Builder
public record Employee (String EmpId) {
}
