interface PasienCustomerDTO {
    biodataId?: number | null
    pasienName?: string | null
    golonganDarah?: string | null
    golonganDarahId?: number | null
    rhesusType?: string | null
    gender?: string | null
    height?: number | null
    weight?: number | null
    dob?: string | null
    relationId?: number | null
}

interface PasienCustomerResDTO {
    biodataName: string
    relation: string | null
    dob: number | null
    appointment: number
    customerChat: number
}

export type { PasienCustomerDTO, PasienCustomerResDTO }