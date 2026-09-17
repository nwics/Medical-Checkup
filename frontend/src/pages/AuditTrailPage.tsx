import { useEffect, useState } from 'react'
import { Pagination } from '../components/ui/Pagination'
import { getAllAuditTrail } from '../services/audit.service'
import type { Pagination as PaginationType } from '../types/api'
import type { AuditTrailDTO } from '../types/audit'

const ACTION_CLASS: Record<string, string> = {
    CREATE: 'create',
    UPDATE: 'update',
    DELETE: 'delete',
    LOGIN: 'login',
    WITHDRAW: 'withdraw',
}

function AuditTrailPage() {
    const [data, setData] = useState<AuditTrailDTO[]>([])
    const [pagination, setPagination] = useState<PaginationType | null>(null)
    const [keyword, setKeyword] = useState('')
    const [current, setCurrent] = useState(1)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        let active = true
        getAllAuditTrail(keyword, current, 10)
            .then((response) => {
                if (!active) {
                    return
                }
                setData(response?.data ?? [])
                setPagination(response?.pagination ?? null)
            })
            .catch((err: unknown) => {
                if (active) {
                    setError(err instanceof Error ? err.message : 'Failed to load audit trail')
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
    }, [keyword, current])

    return (
        <section>
            <h1>Audit Trail</h1>
            {error && <p className="error">{error}</p>}

            <form
                onSubmit={(event) => {
                    event.preventDefault()
                    setCurrent(1)
                }}
            >
                <label htmlFor="auditKeyword">Search</label>
                <input
                    id="auditKeyword"
                    value={keyword}
                    onChange={(event) => setKeyword(event.target.value)}
                    placeholder="Cari aksi, entitas, aktor..."
                />
                <button type="submit">Search</button>
            </form>

            {loading ? (
                <p>Loading...</p>
            ) : (
                <>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Action</th>
                                <th>Entity</th>
                                <th>Entity ID</th>
                                <th>Actor</th>
                                <th>Description</th>
                                <th>Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            {data.map((item) => (
                                <tr key={item.id}>
                                    <td>{item.id}</td>
                                    <td>
                                        <span className={`audit-badge ${ACTION_CLASS[item.action] ?? ''}`}>
                                            {item.action}
                                        </span>
                                    </td>
                                    <td>{item.entityType}</td>
                                    <td>{item.entityId ?? '-'}</td>
                                    <td>{item.actor ?? '-'}</td>
                                    <td>{item.description}</td>
                                    <td>{item.createdOn ? new Date(item.createdOn).toLocaleString() : '-'}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    {pagination && (
                        <Pagination
                            current={pagination.current}
                            totalPages={pagination.totalPages}
                            onPageChange={setCurrent}
                        />
                    )}
                </>
            )}
        </section>
    )
}

export default AuditTrailPage