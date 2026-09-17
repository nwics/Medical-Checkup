interface MedicalItemPurchaseDTO {
    id: number | null
    medicalItemId: number
    medicalItemName: string
    medicalItemDosage: string
}

interface MedicalFacilityDTO {
    id: number | null
    facilityName: string
    address: string
    phone: string
    categoryName: string
    locationName: string
}

interface DoctorOfficeDTO {
    id: number | null
    doctorName: string
    specialization: string
    startDate: string | null
    endDate: string | null
    serviceUnit: string
    medicalFacility: MedicalFacilityDTO | null
}

interface PasienCustomerResDTO {
    biodataName: string
    relation: string | null
    dob: number | null
    appointment: number
    customerChat: number
}

interface ArrivalHistoryDTO {
    id: number
    pasienCustomerResDTO: PasienCustomerResDTO
    diagnosis: string
    appointmentDate: string | null
    medicalItemPurchaseDTOs: MedicalItemPurchaseDTO[] | null
    doctorOffice: DoctorOfficeDTO | null
}

export type { ArrivalHistoryDTO }