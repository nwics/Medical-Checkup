import { http } from './http'
import type { ApiResponse } from '../types/api'
import type { CustomNominalSaldoDTO, DefaultSaldoDTO } from '../types/balance'

export async function getDefaultSaldo(): Promise<DefaultSaldoDTO[]> {
    const response = await http.get<ApiResponse<DefaultSaldoDTO[]>>('/balance-withdraw/default-saldo')
    return Array.isArray(response.data?.data) ? response.data.data : []
}

export async function createCustomNominal(
    payload: CustomNominalSaldoDTO,
): Promise<ApiResponse<unknown> | null> {
    const response = await http.post<ApiResponse<unknown>>('/balance-withdraw/create-saldo', payload)
    return response.data ?? null
}

export async function balanceWithdraw(customerId: number, walletId: number): Promise<ApiResponse<unknown> | null> {
    const response = await http.post<ApiResponse<unknown>>(`/balance-withdraw/${customerId}/${walletId}`)
    return response.data ?? null
}

export async function checkPin(customerId: number, pin: string): Promise<ApiResponse<boolean> | null> {
    const response = await http.put<ApiResponse<boolean>>(`/balance-withdraw/check-pin/${customerId}`, null, {
        params: { pin },
    })
    return response.data ?? null
}