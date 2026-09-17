import { useState } from 'react'
import AuthLayout from '../components/AuthLayout'
import { EmailStep } from '../components/register/EmailStep'
import { OtpStep } from '../components/register/OtpStep'
import { PasswordStep } from '../components/register/PasswordStep'
import { BiodataStep } from '../components/register/BiodataStep'
import {
    requestRegisterOtp,
    verifyRegisterOtp,
    setRegisterPassword,
    setRegisterBiodata,
} from '../services/register.service'
import type { RegisterBiodataPayload } from '../types/register'

type Step = 'email' | 'otp' | 'password' | 'biodata' | 'done'

function RegisterPage() {
    const [step, setStep] = useState<Step>('email')
    const [email, setEmail] = useState('')
    const [message, setMessage] = useState<string | null>(null)
    const [error, setError] = useState<string | null>(null)

    const handleRequestOtp = async (value: string) => {
        setError(null)
        try {
            const response = await requestRegisterOtp(value)
            if (!response?.data?.token) {
                throw new Error('No OTP data received from server')
            }
            setEmail(value)
            setMessage(`OTP sent to ${value}. It expires at ${response.data.expiredOn}.`)
            setStep('otp')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to send OTP')
        }
    }

    const handleVerifyOtp = async (token: string) => {
        setError(null)
        try {
            const response = await verifyRegisterOtp(email, token)
            if (response?.data !== 'success') {
                throw new Error(response?.message ?? 'OTP verification failed')
            }
            setMessage('OTP verified.')
            setStep('password')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to verify OTP')
        }
    }

    const handleSetPassword = async (password: string) => {
        setError(null)
        try {
            const response = await setRegisterPassword(email, password)
            if (!response?.data) {
                throw new Error(response?.message ?? 'Failed to set password')
            }
            setMessage('Password set.')
            setStep('biodata')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to set password')
        }
    }

    const handleSetBiodata = async (payload: RegisterBiodataPayload) => {
        setError(null)
        try {
            await setRegisterBiodata(email, payload)
            setMessage('Registration complete.')
            setStep('done')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to save biodata')
        }
    }

    return (
        <AuthLayout title="Register" subtitle="Buat akun baru dalam beberapa langkah singkat.">
            {message && <p className="auth-alert info">{message}</p>}
            {error && <p className="auth-alert error">{error}</p>}

            <ol className="steps">
                {(['email', 'otp', 'password', 'biodata'] as const).map((s) => (
                    <li key={s} className={step === s ? 'active' : step === 'done' || isAfter(step, s) ? 'done' : ''}>
                        {s}
                    </li>
                ))}
            </ol>

            {step === 'email' && <EmailStep onSubmit={handleRequestOtp} />}
            {step === 'otp' && <OtpStep email={email} onVerify={handleVerifyOtp} />}
            {step === 'password' && <PasswordStep onSubmit={handleSetPassword} />}
            {step === 'biodata' && <BiodataStep onSubmit={handleSetBiodata} />}
            {step === 'done' && (
                <div className="auth-done">
                    Pendaftaran berhasil. Anda sekarang dapat masuk dengan email dan password.
                </div>
            )}
        </AuthLayout>
    )
}

const stepOrder: Step[] = ['email', 'otp', 'password', 'biodata', 'done']

function isAfter(current: Step, candidate: Step): boolean {
    return stepOrder.indexOf(current) > stepOrder.indexOf(candidate)
}

export default RegisterPage