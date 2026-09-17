import type { ReactElement } from 'react'
import {
    IconClipboard,
    IconGrid,
    IconHeartPulse,
    IconHistory,
    IconLogOut,
    IconMapPin,
    IconShield,
    IconStethoscope,
    IconUsers,
    IconWallet,
} from './icons'
import type { AuthPage } from '../types/navigation'

interface SidebarProps {
    open: boolean
    page: AuthPage
    userName: string | null
    userEmail: string | null
    onNavigate: (page: AuthPage) => void
    onClose: () => void
    onLogout: () => void
}

const menuItems: { page: AuthPage; label: string; icon: ReactElement }[] = [
    { page: 'dashboard', label: 'Dashboard', icon: <IconGrid /> },
    { page: 'pasien', label: 'Data Pasien', icon: <IconUsers /> },
    { page: 'doctor', label: 'Data Dokter', icon: <IconStethoscope /> },
    { page: 'arrival', label: 'Riwayat Kedatangan', icon: <IconClipboard /> },
    { page: 'balance', label: 'Saldo & Withdraw', icon: <IconWallet /> },
    { page: 'location', label: 'Location', icon: <IconMapPin /> },
    { page: 'users', label: 'Management User', icon: <IconShield /> },
    { page: 'audit', label: 'Audit Trail', icon: <IconHistory /> },
]

function Sidebar({ open, page, userName, userEmail, onNavigate, onClose, onLogout }: SidebarProps): ReactElement {
    return (
        <>
            <div className={`sidebar-overlay${open ? ' open' : ''}`} onClick={onClose} />
            <aside className={`sidebar${open ? ' open' : ''}`}>
                <div className="sidebar-brand">
                    <div className="sidebar-logo">
                        <IconHeartPulse />
                    </div>
                    <span className="sidebar-name">MediCheck</span>
                </div>
                <p className="sidebar-greeting">Halo, {userName || 'Pengguna'}</p>
                <p className="sidebar-email">{userEmail}</p>
                <nav className="sidebar-nav">
                    {menuItems.map((item) => (
                        <button
                            key={item.page}
                            type="button"
                            className={`sidebar-link${page === item.page ? ' active' : ''}`}
                            onClick={() => {
                                onNavigate(item.page)
                                onClose()
                            }}
                        >
                            <span className="sidebar-link-icon">{item.icon}</span>
                            {item.label}
                        </button>
                    ))}
                </nav>
                <div className="sidebar-footer">
                    <button type="button" className="sidebar-link sidebar-logout" onClick={onLogout}>
                        <span className="sidebar-link-icon">
                            <IconLogOut />
                        </span>
                        Logout
                    </button>
                </div>
            </aside>
        </>
    )
}

export default Sidebar