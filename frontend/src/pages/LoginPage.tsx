import { useState } from 'react'
import { AuthField } from '../components/AuthField'
import AuthLayout from '../components/AuthLayout'
import { IconLock, IconMail } from '../components/icons'
import { login } from '../services/auth.service'
import { setSession } from '../services/auth.store'

function LoginPage({ onLoggedIn }: { onLoggedIn: () => void }) {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [submitting, setSubmitting] = useState(false)
    const [error, setError] = useState<string | null>(null)

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setSubmitting(true)
        try {
            const result = await login(email, password)
            if (!result?.token) {
                throw new Error('Invalid credentials or unexpected response')
            }
            setSession(result.token, result.userInfo)
            onLoggedIn()
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Login failed')
        } finally {
            setSubmitting(false)
        }
    }

    return (
        <AuthLayout title="Login" subtitle="Masuk untuk mengakses dashboard MediCheck.">
            {error && <p className="auth-alert error">{error}</p>}
            <form className="auth-form" onSubmit={handleSubmit}>
                <AuthField
                    id="loginEmail"
                    label="Email"
                    type="email"
                    placeholder="anda@email.com"
                    autoComplete="email"
                    icon={<IconMail />}
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                    required
                />
                <AuthField
                    id="loginPassword"
                    label="Password"
                    type="password"
                    placeholder="Masukkan password"
                    autoComplete="current-password"
                    icon={<IconLock />}
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    required
                />
                <button type="submit" className="auth-submit" disabled={submitting}>
                    {submitting ? 'Logging in...' : 'Login'}
                </button>
            </form>
        </AuthLayout>
    )
}

export default LoginPage