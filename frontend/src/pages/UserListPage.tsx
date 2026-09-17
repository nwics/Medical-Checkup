import { useEffect, useState } from 'react'
import { http } from '../services/http'
import type { ApiResponse } from '../types/api'
import type { UserDTO } from '../types/auth'

function UserListPage() {
    const [users, setUsers] = useState<UserDTO[]>([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        let active = true
        http.get<ApiResponse<UserDTO[]>>('/users/')
            .then((response) => {
                if (!active) {
                    return
                }
                const data = response.data?.data
                setUsers(Array.isArray(data) ? data : [])
            })
            .catch((err: unknown) => {
                if (active) {
                    setError(err instanceof Error ? err.message : 'Failed to load users')
                }
            })
            .finally(() => {
                if (active) {
                    setLoading(false)
                }
            })
        return () => {
            active = false
        }
    }, [])

    return (
        <section>
            <h1>Users</h1>
            {error && <p className="error">{error}</p>}
            {loading ? (
                <p>Loading...</p>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Full Name</th>
                            <th>Last Login</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map((user) => (
                            <tr key={user.id}>
                                <td>{user.id}</td>
                                <td>{user.email}</td>
                                <td>{user.role?.name ?? '-'}</td>
                                <td>{user.biodata?.fullName ?? '-'}</td>
                                <td>{user.lastLogin ?? '-'}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </section>
    )
}

export default UserListPage