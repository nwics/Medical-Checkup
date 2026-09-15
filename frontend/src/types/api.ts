interface ApiResponse<T> {
    message: string
    data: T
    timestamp: string
    statuscode: number
}

export type { ApiResponse }