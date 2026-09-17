import { http } from './http'
import type { ApiResponse, ApiResponsePagination } from '../types/api'
import type { PasienCustomerDTO, PasienCustomerResDTO } from '../types/pasien'

export async function getAllPasien(
    keyword: string,
    current: number,
    size: number,
): Promise<ApiResponsePagination<PasienCustomerResDTO> | null> {
    const response = await http.get<ApiResponsePagination<PasienCustomerResDTO>>('/pasien/', {
        params: { keyword, current, size },
    })
    return response.data ?? null
}

export async function createPasien(payload: PasienCustomerDTO): Promise<ApiResponse<unknown> | null> {
    const response = await http.post<ApiResponse<unknown>>('/pasien/create/', payload)
    return response.data ?? null
}

export async function editPasien(
    customerId: number,
    payload: PasienCustomerDTO,
): Promise<ApiResponse<unknown> | null> {
    const response = await http.put<ApiResponse<unknown>>(`/pasien/edit/${customerId}`, payload)
    return response.data ?? null
}

export async function deleteMultiplePasien(customerIds: number[]): Promise<ApiResponse<unknown> | null> {
    const response = await http.delete<ApiResponse<unknown>>('/pasien/batch', { data: customerIds })
    return response.data ?? null
}