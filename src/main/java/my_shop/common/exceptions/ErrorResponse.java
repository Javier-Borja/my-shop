package my_shop.common.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ErrorResponse(
        String code,
        String message,
        Map<String, List<String>> details,
        LocalDateTime timestamp,
        String path
) { }
