import { http } from './http'
import type { ApiResponse, ApiResponsePagination } from '../types/api'
import type { LocationLevelResDTO, LocationReqDTO, LocationResDTO } from '../types/location'

export async function getAllLocation(
    keyword: string,
    current: number,
    size: number,
): Promise<ApiResponsePagination<LocationResDTO> | null> {
    const response = await http.get<ApiResponsePagination<LocationResDTO>>('/location/', {
        params: { keyword, current, size },
    })
    return response.data ?? null
}

export async function getLocationLevels(): Promise<LocationLevelResDTO[]> {
    const response = await http.get<ApiResponse<LocationLevelResDTO[]>>('/location/level')
    return Array.isArray(response.data?.data) ? response.data.data : []
}

export async function createLocation(payload: LocationReqDTO): Promise<ApiResponse<unknown> | null> {
    const response = await http.post<ApiResponse<unknown>>('/location/create/', payload)
    return response.data ?? null
}

export async function deleteLocation(locationId: number): Promise<ApiResponse<unknown> | null> {
    const response = await http.delete<ApiResponse<unknown>>(`/location/delete/${locationId}`)
    return response.data ?? null
}