import { useState } from 'react'
import { AuthField } from '../AuthField'
import { IconLock } from '../icons'

export function PasswordStep({ onSubmit }: { onSubmit: (password: string) => Promise<void> }) {
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [submitting, setSubmitting] = useState(false)
    const [error, setError] = useState<string | null>(null)

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault()
        if (password !== confirmPassword) {
            setError('Passwords do not match')
            return
        }
        setError(null)
        setSubmitting(true)
        try {
            await onSubmit(password)
        } finally {
            setSubmitting(false)
        }
    }

    return (
        <form className="auth-form" onSubmit={handleSubmit}>
            {error && <p className="auth-alert error">{error}</p>}
            <AuthField
                id="password"
                label="Password"
                type="password"
                placeholder="At least 8 characters"
                autoComplete="new-password"
                icon={<IconLock />}
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                minLength={8}
                required
            />
            <AuthField
                id="confirmPassword"
                label="Confirm Password"
                type="password"
                placeholder="Repeat your password"
                autoComplete="new-password"
                icon={<IconLock />}
                value={confirmPassword}
                onChange={(event) => setConfirmPassword(event.target.value)}
                minLength={8}
                required
            />
            <button type="submit" className="auth-submit" disabled={submitting || password.length === 0}>
                {submitting ? 'Saving...' : 'Set Password'}
            </button>
        </form>
    )
}