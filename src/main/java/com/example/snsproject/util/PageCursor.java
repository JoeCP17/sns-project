package com.example.snsproject.util;

import java.util.List;

public record PageCursor<T>(
    CursorRequest cursorRequest,
    List<T> body
) {

}
