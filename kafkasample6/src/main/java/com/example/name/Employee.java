package com.example.name;


import lombok.Builder;
import lombok.With;

@With
@Builder
public record Employee (String EmpId, String EmpName) {
}
