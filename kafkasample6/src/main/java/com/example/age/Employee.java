package com.example.age;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record Employee (String EmpId, short EmpAge) {
}
