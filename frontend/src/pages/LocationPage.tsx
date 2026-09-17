import { useEffect, useState } from 'react'
import { Pagination } from '../components/ui/Pagination'
import {
    createLocation,
    deleteLocation,
    getAllLocation,
    getLocationLevels,
} from '../services/location.service'
import type { Location, LocationLevelResDTO, LocationResDTO } from '../types/location'
import type { Pagination as PaginationType } from '../types/api'

function LocationPage() {
    const [data, setData] = useState<LocationResDTO[]>([])
    const [pagination, setPagination] = useState<PaginationType | null>(null)
    const [levels, setLevels] = useState<LocationLevelResDTO[]>([])
    const [keyword, setKeyword] = useState('')
    const [current, setCurrent] = useState(1)
    const [reload, setReload] = useState(0)
    const [name, setName] = useState('')
    const [levelId, setLevelId] = useState('')
    const [parentId, setParentId] = useState('')
    const [parents, setParents] = useState<Location[]>([])
    const [message, setMessage] = useState<string | null>(null)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        let active = true
        getAllLocation(keyword, current, 10)
            .then((response) => {
                if (!active) {
                    return
                }
                setData(response?.data ?? [])
                setPagination(response?.pagination ?? null)
            })
            .catch((err: unknown) => {
                if (active) {
                    setError(err instanceof Error ? err.message : 'Failed to load locations')
                }
            })
        return () => {
            active = false
        }
    }, [keyword, current, reload])

    useEffect(() => {
        let active = true
        getLocationLevels()
            .then((levelsResult) => {
                if (active) {
                    setLevels(levelsResult)
                }
            })
            .catch(() => {
                if (active) {
                    setLevels([])
                }
            })
        getAllLocation('', 1, 100)
            .then((response) => {
                if (active) {
                    setParents((response?.data ?? []).map((item) => ({ id: item.locationId, name: item.locationName })))
                }
            })
            .catch(() => {
                if (active) {
                    setParents([])
                }
            })
        return () => {
            active = false
        }
    }, [])

    const handleCreate = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setMessage(null)
        if (!levelId) {
            setError('Select a location level')
            return
        }
        try {
            await createLocation({
                locationName: name,
                locationLevelId: Number(levelId),
                parentId: parentId ? Number(parentId) : 0,
            })
            setMessage('Location created.')
            setName('')
            setLevelId('')
            setParentId('')
            setReload((value) => value + 1)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to create location')
        }
    }

    const handleDelete = async (locationId: number) => {
        setError(null)
        setMessage(null)
        try {
            await deleteLocation(locationId)
            setMessage('Location deleted.')
            setReload((value) => value + 1)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to delete location')
        }
    }

    return (
        <section>
            <h1>Locations</h1>
            {message && <p className="info">{message}</p>}
            {error && <p className="error">{error}</p>}

            <h2>Create</h2>
            <form onSubmit={handleCreate}>
                <label htmlFor="locationName">Name</label>
                <input
                    id="locationName"
                    value={name}
                    onChange={(event) => setName(event.target.value)}
                    required
                />
                <label htmlFor="locationLevel">Level</label>
                <select id="locationLevel" value={levelId} onChange={(event) => setLevelId(event.target.value)} required>
                    <option value="">Select level...</option>
                    {levels.map((level) => (
                        <option key={level.id} value={level.id}>
                            {level.name} ({level.code})
                        </option>
                    ))}
                </select>
                <label htmlFor="locationParent">Parent</label>
                <select id="locationParent" value={parentId} onChange={(event) => setParentId(event.target.value)}>
                    <option value="">None</option>
                    {parents.map((parent) => (
                        <option key={parent.id} value={parent.id}>
                            {parent.name}
                        </option>
                    ))}
                </select>
                <button type="submit">Create Location</button>
            </form>

            <h2>List</h2>
            <form
                onSubmit={(event) => {
                    event.preventDefault()
                    setCurrent(1)
                }}
            >
                <label htmlFor="locationKeyword">Search</label>
                <input
                    id="locationKeyword"
                    value={keyword}
                    onChange={(event) => setKeyword(event.target.value)}
                    placeholder="Keyword"
                />
                <button type="submit">Search</button>
            </form>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Level</th>
                        <th>Parent</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((item) => (
                        <tr key={item.locationId}>
                            <td>{item.locationId}</td>
                            <td>{item.locationName}</td>
                            <td>{item.locationLevel?.name ?? '-'}</td>
                            <td>{item.parentLocationDTO?.parentName ?? '-'}</td>
                            <td>
                                <button type="button" onClick={() => void handleDelete(item.locationId)}>
                                    Delete
                                </button>
                            </td>
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
        </section>
    )
}

export default LocationPage