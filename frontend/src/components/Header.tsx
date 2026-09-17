import type { ReactElement } from 'react'
import { IconHeartPulse, IconLogOut, IconMenu, IconSearch, IconUser } from './icons'

interface HeaderProps {
    userName: string | null
    onOpenSidebar: () => void
    onLogout: () => void
}

function Header({ userName, onOpenSidebar, onLogout }: HeaderProps): ReactElement {
    return (
        <header className="header">
            <button type="button" className="header-burger" onClick={onOpenSidebar} aria-label="Buka menu">
                <IconMenu />
            </button>
            <div className="header-brand-mobile">
                <div className="sidebar-logo">
                    <IconHeartPulse />
                </div>
                <span className="sidebar-name">MediCheck</span>
            </div>
            <div className="header-search">
                <IconSearch />
                <input type="text" placeholder="Cari menu..." aria-label="Cari menu" />
            </div>
            <div className="header-right">
                <div className="header-user">
                    <div className="header-avatar">
                        <IconUser />
                    </div>
                    <div className="header-user-info">
                        <span className="header-user-name">{userName || 'Pengguna'}</span>
                        <span className="header-user-role">Member</span>
                    </div>
                </div>
                <button type="button" className="header-logout" onClick={onLogout} aria-label="Logout">
                    <IconLogOut />
                </button>
            </div>
        </header>
    )
}

export default Header