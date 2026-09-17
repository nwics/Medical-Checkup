import { http } from './http'
import type { ApiResponsePagination } from '../types/api'
import type { DoctorListItemDTO } from '../types/doctor'

interface DoctorFilter {
    location?: string
    doctorName?: string
    keyword?: string
    treatment?: string
    current: number
    size: number
}

export async function getAllDoctor(filter: DoctorFilter): Promise<ApiResponsePagination<DoctorListItemDTO> | null> {
    const response = await http.get<ApiResponsePagination<DoctorListItemDTO>>('/doctor/', { params: filter })
    return response.data ?? null
}