import { http } from './http'
import type { ApiResponse } from '../types/api'
import type { ArrivalHistoryDTO } from '../types/arrival'

export async function getArrivalHistory(): Promise<ArrivalHistoryDTO[]> {
    const response = await http.get<ApiResponse<ArrivalHistoryDTO[]>>('/arrivalhistory/all')
    return Array.isArray(response.data?.data) ? response.data.data : []
}

export async function downloadMedicalItemPdf(appointmentId: number): Promise<void> {
    const response = await http.get<Blob>('/arrivalhistory/pdf/obat', {
        params: { appoinmentId: appointmentId },
        responseType: 'blob',
    })
    const url = URL.createObjectURL(response.data)
    const link = document.createElement('a')
    link.href = url
    link.download = 'daftar_obat.pdf'
    link.click()
    URL.revokeObjectURL(url)
}