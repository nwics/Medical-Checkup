import { http } from './http'
import type { ApiResponsePagination } from '../types/api'
import type { AuditTrailDTO } from '../types/audit'

export async function getAllAuditTrail(
    keyword: string,
    current: number,
    size: number,
): Promise<ApiResponsePagination<AuditTrailDTO> | null> {
    const response = await http.get<ApiResponsePagination<AuditTrailDTO>>('/audittrail/', {
        params: { keyword, current, size },
    })
    return response.data ?? null
}