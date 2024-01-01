package com.common;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record Employee (String EmpId, String FirstName, String LastName, String Address, short Age) {
}

