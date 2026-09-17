interface DefaultSaldoDTO {
    id: number | null
    saldo: number | string
    type: string
}

interface CustomNominalSaldoDTO {
    customerId: number | null
    nominal: number | string
}

export type { DefaultSaldoDTO, CustomNominalSaldoDTO }