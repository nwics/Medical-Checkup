import { http } from './http'
import type { ApiResponse } from '../types/api'
import type { LocationLevel } from '../types/location'

export async function getLocationLevels(): Promise<LocationLevel[]> {
    const response = await http.get<ApiResponse<LocationLevel[]>>('/location/level')
    const data = response.data?.data
    return Array.isArray(data) ? data : []
}