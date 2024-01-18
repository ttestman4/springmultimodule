package com.common;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record Fish (String FishId, String FishName, short Age) {
}

