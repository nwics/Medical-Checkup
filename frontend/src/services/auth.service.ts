import axios from 'axios'
import type { ApiResponse } from '../types/api'
import type { LoginResult } from '../types/auth'

const baseUrl = import.meta.env.VITE_API_BASE_URL ?? '/api'

export async function login(email: string, password: string): Promise<LoginResult | null> {
    const basic = btoa(`${email}:${password}`)
    const response = await axios.post<ApiResponse<LoginResult>>(
        `${baseUrl}/users/login`,
        null,
        { headers: { Authorization: `Basic ${basic}` } },
    )
    return response.data?.data ?? null
}