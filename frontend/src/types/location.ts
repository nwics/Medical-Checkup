interface LocationLevelResDTO {
    id: number
    name: string
    code: string
}

interface ParentLocationDTO {
    parentId: number | null
    parentName: string | null
}

interface LocationResDTO {
    locationId: number
    locationName: string
    locationLevel: LocationLevelResDTO | null
    parentLocationDTO: ParentLocationDTO | null
}

interface LocationReqDTO {
    locationName: string
    parentId: number
    locationLevelId: number
}

interface Location {
    id: number
    name: string
}

export type { LocationLevelResDTO, LocationResDTO, LocationReqDTO, Location }