interface TokenDTO {
    id: number | null
    customerId: number | null
    token: string
    expiredOn: string
    isExpired: boolean
    userFor: string
}

interface RegisterBiodataPayload {
    biodata: {
        fullName: string
        mobilePhone: string
    }
    role: {
        id: number
    }
}

export type { TokenDTO, RegisterBiodataPayload }