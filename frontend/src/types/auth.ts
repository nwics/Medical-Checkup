interface MRole {
    id: number
    name: string | null
    code: string | null
}

interface MBiodata {
    id: number
    fullName: string | null
    mobilePhone: string | null
    imagePath: string | null
}

interface UserDTO {
    id: number
    lastLogin: string | null
    email: string
    role: MRole | null
    biodata: MBiodata | null
}

interface LoginResult {
    userInfo: UserDTO
    token: string
}

export type { MRole, MBiodata, UserDTO, LoginResult }