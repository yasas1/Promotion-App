package com.promotionservice.domain.util;

import java.util.List;

public record ResultSetResponse<T>(int pageNumber, int pageSize,  long totalElement, int totalPages, List<T> content) { }
