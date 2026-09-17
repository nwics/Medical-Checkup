import type { ReactElement } from 'react'

interface MenuCardProps {
    title: string
    description: string
    icon: ReactElement
    onClick?: () => void
}

function MenuCard({ title, description, icon, onClick }: MenuCardProps): ReactElement {
    return (
        <button type="button" className="menu-card" onClick={onClick}>
            <div className="menu-card-icon">{icon}</div>
            <div className="menu-card-text">
                <span className="menu-card-title">{title}</span>
                <span className="menu-card-desc">{description}</span>
            </div>
        </button>
    )
}

export default MenuCard