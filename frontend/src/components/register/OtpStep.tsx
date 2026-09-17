import { useState } from 'react'
import { AuthField } from '../AuthField'
import { IconKey } from '../icons'

export function OtpStep({
    email,
    onVerify,
}: {
    email: string
    onVerify: (token: string) => Promise<void>
}) {
    const [token, setToken] = useState('')
    const [submitting, setSubmitting] = useState(false)

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault()
        setSubmitting(true)
        try {
            await onVerify(token)
        } finally {
            setSubmitting(false)
        }
    }

    return (
        <form className="auth-form" onSubmit={handleSubmit}>
            <p className="auth-note">
                Kode OTP dikirim ke <strong>{email}</strong>. Periksa email Anda.
            </p>
            <AuthField
                id="otp"
                label="OTP Code"
                type="text"
                inputMode="numeric"
                placeholder="123456"
                icon={<IconKey />}
                value={token}
                onChange={(event) => setToken(event.target.value)}
                required
            />
            <button type="submit" className="auth-submit" disabled={submitting || token.length === 0}>
                {submitting ? 'Verifying...' : 'Verify OTP'}
            </button>
        </form>
    )
}