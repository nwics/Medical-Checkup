import type { ReactElement, ReactNode } from 'react'
import { IconClipboard, IconHeartPulse, IconShield, IconStethoscope, IconUsers } from './icons'

interface AuthLayoutProps {
    title: string
    subtitle: string
    children: ReactNode
}

const features = [
    { icon: <IconUsers />, text: 'Data pasien terpusat & mudah dicari' },
    { icon: <IconStethoscope />, text: 'Jadwal dokter dan medical checkup' },
    { icon: <IconClipboard />, text: 'Riwayat kedatangan lengkap' },
    { icon: <IconShield />, text: 'Akses aman dan terlindungi' },
]

function AuthLayout({ title, subtitle, children }: AuthLayoutProps): ReactElement {
    return (
        <div className="auth-page">
            <div className="auth-card">
                <div className="auth-hero">
                    <div className="auth-brand">
                        <div className="sidebar-logo">
                            <IconHeartPulse />
                        </div>
                        <span className="sidebar-name">MediCheck</span>
                    </div>
                    <h2 className="auth-hero-title">Sistem Medical Checkup Klinik</h2>
                    <p className="auth-hero-lead">
                        Kelola pasien, dokter, dan riwayat pemeriksaan dalam satu aplikasi yang sederhana dan rapi.
                    </p>
                    <ul className="auth-hero-list">
                        {features.map((feature) => (
                            <li key={feature.text}>
                                <span className="auth-hero-icon">{feature.icon}</span>
                                {feature.text}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="auth-panel">
                    <h1 className="auth-title">{title}</h1>
                    <p className="auth-subtitle">{subtitle}</p>
                    {children}
                </div>
            </div>
        </div>
    )
}

export default AuthLayout