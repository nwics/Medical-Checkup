import type { ReactElement } from 'react'
import MenuCard from '../components/MenuCard'
import {
    IconClipboard,
    IconGrid,
    IconHeartPulse,
    IconHistory,
    IconMapPin,
    IconShield,
    IconStethoscope,
    IconUsers,
    IconWallet,
} from '../components/icons'
import { getUser } from '../services/auth.store'
import type { AuthPage } from '../types/navigation'

interface DashboardPageProps {
    onNavigate: (page: AuthPage) => void
}

const menuItems: { title: string; description: string; page: AuthPage; icon: ReactElement }[] = [
    { title: 'Dashboard', description: 'Ringkasan data', page: 'dashboard', icon: <IconGrid /> },
    { title: 'Data Pasien', description: 'Kelola data pasien', page: 'pasien', icon: <IconUsers /> },
    { title: 'Data Dokter', description: 'Kelola data dokter', page: 'doctor', icon: <IconStethoscope /> },
    { title: 'Riwayat Kedatangan', description: 'Riwayat pasien datang', page: 'arrival', icon: <IconClipboard /> },
    { title: 'Saldo & Withdraw', description: 'Kelola saldo member', page: 'balance', icon: <IconWallet /> },
    { title: 'Location', description: 'Kelola lokasi klinik', page: 'location', icon: <IconMapPin /> },
    { title: 'Management User', description: 'Kelola pengguna sistem', page: 'users', icon: <IconShield /> },
    { title: 'Audit Trail', description: 'Lacak aktivitas pengguna', page: 'audit', icon: <IconHistory /> },
]

function DashboardPage({ onNavigate }: DashboardPageProps): ReactElement {
    const user = getUser()

    return (
        <section className="dashboard">
            <div className="dashboard-welcome">
                <div className="dashboard-welcome-icon">
                    <IconHeartPulse />
                </div>
                <div>
                    <h1 className="dashboard-title">
                        Selamat datang, {user?.biodata?.fullName?.split(' ')[0] || 'Pengguna'}
                    </h1>
                    <p className="dashboard-subtitle">Pilih menu di bawah untuk mulai bekerja.</p>
                </div>
            </div>
            <div className="dashboard-grid">
                {menuItems.map((item) => (
                    <MenuCard
                        key={item.page}
                        title={item.title}
                        description={item.description}
                        icon={item.icon}
                        onClick={() => onNavigate(item.page)}
                    />
                ))}
            </div>
        </section>
    )
}

export default DashboardPage