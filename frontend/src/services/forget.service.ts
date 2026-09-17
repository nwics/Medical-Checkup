import { http } from './http'
import type { ApiResponse } from '../types/api'
import type { TokenDTO } from '../types/register'

export async function requestForgetOtp(email: string): Promise<ApiResponse<TokenDTO> | null> {
    const response = await http.post<ApiResponse<TokenDTO>>('/forgot/create', null, { params: { email } })
    return response.data ?? null
}

export async function verifyForgetOtp(email: string, otp: string): Promise<ApiResponse<string> | null> {
    const response = await http.post<ApiResponse<string>>('/forgot/verify', null, { params: { email, otp } })
    return response.data ?? null
}

export async function setForgetPassword(
    email: string,
    password: string,
    confirmPassword: string,
): Promise<ApiResponse<string> | null> {
    const response = await http.put<ApiResponse<string>>(
        `/forgot/setPassword/${email}`,
        null,
        { params: { password, setPassword: confirmPassword } },
    )
    return response.data ?? null
}