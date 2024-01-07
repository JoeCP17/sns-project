package com.example.snsproject.util;

public record CusorRequest(
    Long key,
    Long size
) {
  public static final Long NONE_KEY = -1L;

  public Boolean hasKey() {
    return key != null;
  }

  public CusorRequest next(final Long key) {
    return new CusorRequest(key, size);
  }

}
