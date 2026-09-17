interface DoctorListItemDTO {
    doctorId: number
    doctorName: string
    specialization: string
    yearsOfExperience: number
    locationName: string
    availibility: string
    hospitalName: string[]
}

export type { DoctorListItemDTO }