package com.example.snsproject.util;

public record CursorRequest(
    Long key,
    Long size
) {
  public static final Long NONE_KEY = -1L;

  public Boolean hasKey() {
    return key != null;
  }

  public CursorRequest next(final Long key) {
    return new CursorRequest(key, size);
  }

}
