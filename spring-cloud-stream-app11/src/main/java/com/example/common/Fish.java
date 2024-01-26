package com.example.common;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record Fish (int EmpId, String FishId, String FishName, short Age) {
}

