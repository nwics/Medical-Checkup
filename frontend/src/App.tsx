import { useState } from 'react'
import DashboardPage from './pages/DashboardPage'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import ForgetPasswordPage from './pages/ForgetPasswordPage'
import PasienPage from './pages/PasienPage'
import DoctorPage from './pages/DoctorPage'
import ArrivalHistoryPage from './pages/ArrivalHistoryPage'
import BalanceWithdrawPage from './pages/BalanceWithdrawPage'
import LocationPage from './pages/LocationPage'
import UserListPage from './pages/UserListPage'
import AuditTrailPage from './pages/AuditTrailPage'
import Sidebar from './components/Sidebar'
import Header from './components/Header'
import { clearSession, getUser } from './services/auth.store'
import type { UserDTO } from './types/auth'
import type { AuthPage, PublicPage } from './types/navigation'
import './App.css'

function App() {
    const [user, setUser] = useState<UserDTO | null>(getUser())
    const [page, setPage] = useState<PublicPage | AuthPage>(getUser() ? 'dashboard' : 'login')
    const [sidebarOpen, setSidebarOpen] = useState(false)

    const handleLoggedIn = () => {
        setUser(getUser())
        setPage('dashboard')
    }

    const handleLogout = () => {
        clearSession()
        setSidebarOpen(false)
        setUser(null)
        setPage('login')
    }

    const handleNavigate = (next: AuthPage) => {
        setPage(next)
    }

    if (!user) {
        return (
            <>
                <nav className="auth-tabs">
                    <button
                        type="button"
                        className={page === 'login' ? 'active' : ''}
                        onClick={() => setPage('login')}
                    >
                        Login
                    </button>
                    <button
                        type="button"
                        className={page === 'register' ? 'active' : ''}
                        onClick={() => setPage('register')}
                    >
                        Register
                    </button>
                    <button
                        type="button"
                        className={page === 'forgot' ? 'active' : ''}
                        onClick={() => setPage('forgot')}
                    >
                        Forgot Password
                    </button>
                </nav>
                {page === 'login' && <LoginPage onLoggedIn={handleLoggedIn} />}
                {page === 'register' && <RegisterPage />}
                {page === 'forgot' && <ForgetPasswordPage />}
            </>
        )
    }

    return (
        <div className="app-layout">
            <Sidebar
                open={sidebarOpen}
                page={page as AuthPage}
                userName={user.biodata?.fullName ?? null}
                userEmail={user.email}
                onNavigate={handleNavigate}
                onClose={() => setSidebarOpen(false)}
                onLogout={handleLogout}
            />
            <div className="app-main">
                <Header
                    userName={user.biodata?.fullName ?? null}
                    onOpenSidebar={() => setSidebarOpen(true)}
                    onLogout={handleLogout}
                />
                <main className="app-content">
                    {page === 'dashboard' && <DashboardPage onNavigate={handleNavigate} />}
                    {page === 'pasien' && <PasienPage />}
                    {page === 'doctor' && <DoctorPage />}
                    {page === 'arrival' && <ArrivalHistoryPage />}
                    {page === 'balance' && <BalanceWithdrawPage />}
                    {page === 'location' && <LocationPage />}
                    {page === 'users' && <UserListPage />}
                    {page === 'audit' && <AuditTrailPage />}
                </main>
            </div>
        </div>
    )
}

export default App