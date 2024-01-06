package com.example.snsproject.util;

import java.util.List;

public record PageCursor<T>(
    CusorRequest cursorRequest,
    List<T> body
) {

}
