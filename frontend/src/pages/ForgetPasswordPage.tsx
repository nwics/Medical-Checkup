import { useState } from 'react'
import { AuthField } from '../components/AuthField'
import AuthLayout from '../components/AuthLayout'
import { IconKey, IconLock, IconMail } from '../components/icons'
import { requestForgetOtp, setForgetPassword, verifyForgetOtp } from '../services/forget.service'

type ForgetStep = 'email' | 'otp' | 'password'

function ForgetPasswordPage() {
    const [email, setEmail] = useState('')
    const [otp, setOtp] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [step, setStep] = useState<ForgetStep>('email')
    const [message, setMessage] = useState<string | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [submitting, setSubmitting] = useState(false)

    const handleRequestOtp = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setSubmitting(true)
        try {
            await requestForgetOtp(email)
            setMessage('OTP sent to your email.')
            setStep('otp')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to send OTP')
        } finally {
            setSubmitting(false)
        }
    }

    const handleVerifyOtp = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setSubmitting(true)
        try {
            const response = await verifyForgetOtp(email, otp)
            if (response?.data !== 'success') {
                throw new Error(response?.message ?? 'OTP verification failed')
            }
            setMessage('OTP verified.')
            setStep('password')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to verify OTP')
        } finally {
            setSubmitting(false)
        }
    }

    const handleSetPassword = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        if (password !== confirmPassword) {
            setError('Passwords do not match')
            return
        }
        setSubmitting(true)
        try {
            const response = await setForgetPassword(email, password, confirmPassword)
            if (!response?.data) {
                throw new Error(response?.message ?? 'Failed to set password')
            }
            setMessage('Password updated. You can now log in.')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to set password')
        } finally {
            setSubmitting(false)
        }
    }

    return (
        <AuthLayout title="Forgot Password" subtitle="Reset kata sandi akun Anda.">
            {message && <p className="auth-alert info">{message}</p>}
            {error && <p className="auth-alert error">{error}</p>}

            <ol className="steps">
                {(['email', 'otp', 'password'] as const).map((s) => (
                    <li key={s} className={step === s ? 'active' : stepIndex(step) > stepIndex(s) ? 'done' : ''}>
                        {s}
                    </li>
                ))}
            </ol>

            {step === 'email' && (
                <form className="auth-form" onSubmit={handleRequestOtp}>
                    <AuthField
                        id="forgetEmail"
                        label="Email"
                        type="email"
                        placeholder="anda@email.com"
                        autoComplete="email"
                        icon={<IconMail />}
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        required
                    />
                    <button type="submit" className="auth-submit" disabled={submitting}>
                        {submitting ? 'Sending...' : 'Send OTP'}
                    </button>
                </form>
            )}

            {step === 'otp' && (
                <form className="auth-form" onSubmit={handleVerifyOtp}>
                    <p className="auth-note">
                        Kode OTP dikirim ke <strong>{email}</strong>. Periksa email Anda.
                    </p>
                    <AuthField
                        id="forgetOtp"
                        label="OTP Code"
                        type="text"
                        inputMode="numeric"
                        placeholder="123456"
                        icon={<IconKey />}
                        value={otp}
                        onChange={(event) => setOtp(event.target.value)}
                        required
                    />
                    <button type="submit" className="auth-submit" disabled={submitting}>
                        {submitting ? 'Verifying...' : 'Verify OTP'}
                    </button>
                </form>
            )}

            {step === 'password' && (
                <form className="auth-form" onSubmit={handleSetPassword}>
                    <AuthField
                        id="forgetPassword"
                        label="New Password"
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
                        id="forgetConfirmPassword"
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
                    <button type="submit" className="auth-submit" disabled={submitting}>
                        {submitting ? 'Saving...' : 'Reset Password'}
                    </button>
                </form>
            )}
        </AuthLayout>
    )
}

const forgetStepOrder: ForgetStep[] = ['email', 'otp', 'password']

function stepIndex(s: ForgetStep): number {
    return forgetStepOrder.indexOf(s)
}

export default ForgetPasswordPage