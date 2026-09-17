import { useEffect, useState } from 'react'
import { Pagination } from '../components/ui/Pagination'
import {
    createPasien,
    deleteMultiplePasien,
    getAllPasien,
} from '../services/pasien.service'
import type { PasienCustomerDTO, PasienCustomerResDTO } from '../types/pasien'
import type { Pagination as PaginationType } from '../types/api'

const emptyForm: PasienCustomerDTO = {
    biodataId: null,
    pasienName: '',
    golonganDarahId: null,
    rhesusType: '',
    gender: '',
    height: null,
    weight: null,
    dob: null,
    relationId: null,
}

function PasienPage() {
    const [data, setData] = useState<PasienCustomerResDTO[]>([])
    const [pagination, setPagination] = useState<PaginationType | null>(null)
    const [keyword, setKeyword] = useState('')
    const [current, setCurrent] = useState(1)
    const [reload, setReload] = useState(0)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)
    const [deleteIds, setDeleteIds] = useState('')
    const [message, setMessage] = useState<string | null>(null)
    const [form, setForm] = useState<PasienCustomerDTO>(emptyForm)

    useEffect(() => {
        let active = true
        getAllPasien(keyword, current, 10)
            .then((response) => {
                if (!active) {
                    return
                }
                setData(response?.data ?? [])
                setPagination(response?.pagination ?? null)
            })
            .catch((err: unknown) => {
                if (active) {
                    setError(err instanceof Error ? err.message : 'Failed to load pasien')
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
    }, [keyword, current, reload])

    const handleCreate = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setMessage(null)
        try {
            const response = await createPasien(form)
            if (response?.statuscode !== 200) {
                throw new Error(response?.message ?? 'Failed to create pasien')
            }
            setMessage('Pasien created.')
            setForm(emptyForm)
            setReload((value) => value + 1)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to create pasien')
        }
    }

    const handleBatchDelete = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setMessage(null)
        const ids = deleteIds
            .split(',')
            .map((value) => Number(value.trim()))
            .filter((value) => Number.isFinite(value) && value > 0)
        if (ids.length === 0) {
            setError('Enter at least one customer ID')
            return
        }
        try {
            await deleteMultiplePasien(ids)
            setMessage(`Deleted: ${ids.join(', ')}`)
            setDeleteIds('')
            setReload((value) => value + 1)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to delete pasien')
        }
    }

    const updateForm = (field: keyof PasienCustomerDTO, value: string | number | null) => {
        setForm((previous) => ({ ...previous, [field]: value === '' ? null : value }))
    }

    return (
        <section>
            <h1>Pasien</h1>
            {message && <p className="info">{message}</p>}
            {error && <p className="error">{error}</p>}

            <h2>Create</h2>
            <form onSubmit={handleCreate}>
                <label htmlFor="biodataId">Biodata ID</label>
                <input
                    id="biodataId"
                    type="number"
                    value={form.biodataId ?? ''}
                    onChange={(event) => updateForm('biodataId', Number(event.target.value))}
                    required
                />
                <label htmlFor="pasienName">Name</label>
                <input
                    id="pasienName"
                    value={form.pasienName ?? ''}
                    onChange={(event) => updateForm('pasienName', event.target.value)}
                    required
                />
                <label htmlFor="gender">Gender</label>
                <select
                    id="gender"
                    value={form.gender ?? ''}
                    onChange={(event) => updateForm('gender', event.target.value)}
                    required
                >
                    <option value="">Select...</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                </select>
                <label htmlFor="golonganDarahId">Blood Group ID</label>
                <input
                    id="golonganDarahId"
                    type="number"
                    value={form.golonganDarahId ?? ''}
                    onChange={(event) => updateForm('golonganDarahId', Number(event.target.value))}
                    required
                />
                <label htmlFor="rhesusType">Rhesus</label>
                <input
                    id="rhesusType"
                    value={form.rhesusType ?? ''}
                    onChange={(event) => updateForm('rhesusType', event.target.value)}
                />
                <label htmlFor="height">Height</label>
                <input
                    id="height"
                    type="number"
                    step="0.01"
                    value={form.height ?? ''}
                    onChange={(event) => updateForm('height', Number(event.target.value))}
                />
                <label htmlFor="weight">Weight</label>
                <input
                    id="weight"
                    type="number"
                    step="0.01"
                    value={form.weight ?? ''}
                    onChange={(event) => updateForm('weight', Number(event.target.value))}
                />
                <label htmlFor="dob">Date of Birth</label>
                <input
                    id="dob"
                    type="datetime-local"
                    value={form.dob ?? ''}
                    onChange={(event) => updateForm('dob', event.target.value)}
                />
                <label htmlFor="relationId">Relation ID</label>
                <input
                    id="relationId"
                    type="number"
                    value={form.relationId ?? ''}
                    onChange={(event) => updateForm('relationId', Number(event.target.value))}
                />
                <button type="submit">Create Pasien</button>
            </form>

            <h2>List</h2>
            <form
                onSubmit={(event) => {
                    event.preventDefault()
                    setCurrent(1)
                }}
            >
                <label htmlFor="pasienKeyword">Search</label>
                <input
                    id="pasienKeyword"
                    value={keyword}
                    onChange={(event) => setKeyword(event.target.value)}
                    placeholder="Keyword"
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
                                <th>Name</th>
                                <th>Relation</th>
                                <th>Age</th>
                                <th>Appointments</th>
                                <th>Chats</th>
                            </tr>
                        </thead>
                        <tbody>
                            {data.map((item) => (
                                <tr key={item.biodataName + item.appointment}>
                                    <td>{item.biodataName}</td>
                                    <td>{item.relation ?? '-'}</td>
                                    <td>{item.dob ?? '-'}</td>
                                    <td>{item.appointment}</td>
                                    <td>{item.customerChat}</td>
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

            <h2>Delete (batch)</h2>
            <form onSubmit={handleBatchDelete}>
                <label htmlFor="deleteIds">Customer IDs (comma separated)</label>
                <input
                    id="deleteIds"
                    value={deleteIds}
                    onChange={(event) => setDeleteIds(event.target.value)}
                    placeholder="1,2,3"
                />
                <button type="submit">Delete Selected</button>
            </form>
        </section>
    )
}

export default PasienPage