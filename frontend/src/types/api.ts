interface ApiResponse<T> {
    message: string
    data: T
    timestamp: string
    statuscode: number
}

interface Pagination {
    size: number
    total: number
    totalPages: number
    current: number
    filter: Record<string, string> | null
}

interface ApiResponsePagination<T> {
    message: string
    data: T[]
    timestamp: string
    statuscode: number
    pagination: Pagination
}

export type { ApiResponse, ApiResponsePagination, Pagination }