package backend.v1.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInfo {
    private long currentPage;   // 当前页码
    private long totalPages;     // 总页数
    private long totalRecords;   // 总记录数
    private long pageSize;       // 每页大小
}
