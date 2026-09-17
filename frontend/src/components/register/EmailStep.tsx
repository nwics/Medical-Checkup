import { useState } from 'react'
import { AuthField } from '../AuthField'
import { IconMail } from '../icons'

export function EmailStep({ onSubmit }: { onSubmit: (email: string) => Promise<void> }) {
    const [email, setEmail] = useState('')
    const [submitting, setSubmitting] = useState(false)

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault()
        setSubmitting(true)
        try {
            await onSubmit(email)
        } finally {
            setSubmitting(false)
        }
    }

    return (
        <form className="auth-form" onSubmit={handleSubmit}>
            <AuthField
                id="email"
                label="Email"
                type="email"
                placeholder="you@example.com"
                autoComplete="email"
                icon={<IconMail />}
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
            />
            <button type="submit" className="auth-submit" disabled={submitting || email.length === 0}>
                {submitting ? 'Sending OTP...' : 'Send OTP'}
            </button>
        </form>
    )
}