import type { UserDTO } from '../types/auth'

const TOKEN_KEY = 'medical.token'
const USER_KEY = 'medical.user'

export function getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY)
}

export function getUser(): UserDTO | null {
    const raw = localStorage.getItem(USER_KEY)
    if (!raw) {
        return null
    }
    try {
        return JSON.parse(raw) as UserDTO
    } catch {
        return null
    }
}

export function setSession(token: string, user: UserDTO): void {
    localStorage.setItem(TOKEN_KEY, token)
    localStorage.setItem(USER_KEY, JSON.stringify(user))
}

export function clearSession(): void {
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
}