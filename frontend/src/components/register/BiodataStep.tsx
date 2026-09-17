import { useState } from 'react'
import { AuthField } from '../AuthField'
import { IconIdCard, IconPhone, IconUser } from '../icons'
import type { RegisterBiodataPayload } from '../../types/register'

export function BiodataStep({ onSubmit }: { onSubmit: (payload: RegisterBiodataPayload) => Promise<void> }) {
    const [fullName, setFullName] = useState('')
    const [mobilePhone, setMobilePhone] = useState('')
    const [roleId, setRoleId] = useState('')
    const [submitting, setSubmitting] = useState(false)

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault()
        setSubmitting(true)
        try {
            await onSubmit({
                biodata: { fullName, mobilePhone },
                role: { id: Number(roleId) },
            })
        } finally {
            setSubmitting(false)
        }
    }

    return (
        <form className="auth-form" onSubmit={handleSubmit}>
            <AuthField
                id="fullName"
                label="Full Name"
                type="text"
                placeholder="Your full name"
                autoComplete="name"
                icon={<IconUser />}
                value={fullName}
                onChange={(event) => setFullName(event.target.value)}
                required
            />
            <AuthField
                id="mobilePhone"
                label="Mobile Phone"
                type="tel"
                placeholder="08xxxxxxxxxx"
                autoComplete="tel"
                icon={<IconPhone />}
                value={mobilePhone}
                onChange={(event) => setMobilePhone(event.target.value)}
                required
            />
            <AuthField
                id="roleId"
                label="Role ID"
                type="number"
                min="1"
                placeholder="e.g. 2"
                icon={<IconIdCard />}
                value={roleId}
                onChange={(event) => setRoleId(event.target.value)}
                required
            />
            <button type="submit" className="auth-submit" disabled={submitting || !fullName || !mobilePhone || !roleId}>
                {submitting ? 'Saving...' : 'Complete Registration'}
            </button>
        </form>
    )
}