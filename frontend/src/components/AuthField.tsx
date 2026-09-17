import type { InputHTMLAttributes, ReactElement } from 'react'

interface AuthFieldProps extends InputHTMLAttributes<HTMLInputElement> {
    label: string
    icon?: ReactElement
}

export function AuthField({ label, icon, id, className, ...rest }: AuthFieldProps): ReactElement {
    return (
        <div className="auth-field">
            <label htmlFor={id}>{label}</label>
            <div className={icon ? 'auth-field-control with-icon' : 'auth-field-control'}>
                {icon && <span className="auth-field-icon">{icon}</span>}
                <input id={id} className={className} {...rest} />
            </div>
        </div>
    )
}