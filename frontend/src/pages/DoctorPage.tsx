import { useEffect, useState } from 'react'
import { Pagination } from '../components/ui/Pagination'
import { getAllDoctor } from '../services/doctor.service'
import type { DoctorListItemDTO } from '../types/doctor'
import type { Pagination as PaginationType } from '../types/api'

function DoctorPage() {
    const [doctors, setDoctors] = useState<DoctorListItemDTO[]>([])
    const [pagination, setPagination] = useState<PaginationType | null>(null)
    const [keyword, setKeyword] = useState('')
    const [location, setLocation] = useState('')
    const [doctorName, setDoctorName] = useState('')
    const [treatment, setTreatment] = useState('')
    const [current, setCurrent] = useState(1)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        let active = true
        getAllDoctor({ keyword, location, doctorName, treatment, current, size: 10 })
            .then((response) => {
                if (!active) {
                    return
                }
                setDoctors(response?.data ?? [])
                setPagination(response?.pagination ?? null)
            })
            .catch((err: unknown) => {
                if (active) {
                    setError(err instanceof Error ? err.message : 'Failed to load doctors')
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
    }, [keyword, location, doctorName, treatment, current])

    return (
        <section>
            <h1>Doctors</h1>
            {error && <p className="error">{error}</p>}

            <form
                onSubmit={(event) => {
                    event.preventDefault()
                    setCurrent(1)
                }}
            >
                <label htmlFor="doctorKeyword">Keyword</label>
                <input
                    id="doctorKeyword"
                    value={keyword}
                    onChange={(event) => setKeyword(event.target.value)}
                />
                <label htmlFor="doctorName">Doctor Name</label>
                <input
                    id="doctorName"
                    value={doctorName}
                    onChange={(event) => setDoctorName(event.target.value)}
                />
                <label htmlFor="doctorLocation">Location</label>
                <input
                    id="doctorLocation"
                    value={location}
                    onChange={(event) => setLocation(event.target.value)}
                />
                <label htmlFor="doctorTreatment">Treatment</label>
                <input
                    id="doctorTreatment"
                    value={treatment}
                    onChange={(event) => setTreatment(event.target.value)}
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
                                <th>Specialization</th>
                                <th>Experience</th>
                                <th>Location</th>
                                <th>Availability</th>
                                <th>Hospitals</th>
                            </tr>
                        </thead>
                        <tbody>
                            {doctors.map((doctor) => (
                                <tr key={doctor.doctorId}>
                                    <td>{doctor.doctorName}</td>
                                    <td>{doctor.specialization}</td>
                                    <td>{doctor.yearsOfExperience} yrs</td>
                                    <td>{doctor.locationName}</td>
                                    <td>{doctor.availibility}</td>
                                    <td>{doctor.hospitalName.join(', ')}</td>
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

export default DoctorPage