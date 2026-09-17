import { http } from './http'
import type { ApiResponse } from '../types/api'
import type { RegisterBiodataPayload, TokenDTO } from '../types/register'

export async function requestRegisterOtp(email: string): Promise<ApiResponse<TokenDTO> | null> {
    const response = await http.post<ApiResponse<TokenDTO>>('/register/create', null, {
        params: { email },
    })
    return response.data ?? null
}

export async function verifyRegisterOtp(email: string, token: string): Promise<ApiResponse<string> | null> {
    const response = await http.post<ApiResponse<string>>('/register/verify', null, {
        params: { email, token },
    })
    return response.data ?? null
}

export async function setRegisterPassword(email: string, password: string): Promise<ApiResponse<string> | null> {
    const response = await http.put<ApiResponse<string>>(`/register/setPassword/${email}`, null, {
        params: { password },
    })
    return response.data ?? null
}

export async function setRegisterBiodata(
    email: string,
    payload: RegisterBiodataPayload,
): Promise<ApiResponse<unknown> | null> {
    const response = await http.put<ApiResponse<unknown>>(`/register/setBiodata/${email}`, payload)
    return response.data ?? null
}