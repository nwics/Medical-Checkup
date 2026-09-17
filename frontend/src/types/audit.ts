interface AuditTrailDTO {
    id: number
    action: string
    entityType: string
    entityId: number
    description: string
    actor: string
    createdBy: number
    createdOn: string
}

export type { AuditTrailDTO }