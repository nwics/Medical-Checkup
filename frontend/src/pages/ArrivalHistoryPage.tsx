import { useEffect, useState } from 'react'
import { downloadMedicalItemPdf, getArrivalHistory } from '../services/arrival.service'
import type { ArrivalHistoryDTO } from '../types/arrival'

function ArrivalHistoryPage() {
    const [data, setData] = useState<ArrivalHistoryDTO[]>([])
    const [appointmentId, setAppointmentId] = useState('')
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)
    const [message, setMessage] = useState<string | null>(null)

    useEffect(() => {
        let active = true
        getArrivalHistory()
            .then((response) => {
                if (active) {
                    setData(response)
                }
            })
            .catch((err: unknown) => {
                if (active) {
                    setError(err instanceof Error ? err.message : 'Failed to load arrival history')
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

    const handleDownload = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setMessage(null)
        const id = Number(appointmentId)
        if (!Number.isFinite(id) || id <= 0) {
            setError('Enter a valid appointment ID')
            return
        }
        try {
            await downloadMedicalItemPdf(id)
            setMessage('PDF downloaded.')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to download PDF')
        }
    }

    return (
        <section>
            <h1>Arrival History</h1>
            {message && <p className="info">{message}</p>}
            {error && <p className="error">{error}</p>}

            <form onSubmit={handleDownload}>
                <label htmlFor="appointmentId">Appointment ID</label>
                <input
                    id="appointmentId"
                    type="number"
                    value={appointmentId}
                    onChange={(event) => setAppointmentId(event.target.value)}
                    placeholder="e.g. 1"
                />
                <button type="submit">Download Medicine PDF</button>
            </form>

            {loading ? (
                <p>Loading...</p>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th>Patient</th>
                            <th>Diagnosis</th>
                            <th>Date</th>
                            <th>Doctor</th>
                            <th>Medicines</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((item) => (
                            <tr key={item.id}>
                                <td>{item.pasienCustomerResDTO?.biodataName ?? '-'}</td>
                                <td>{item.diagnosis}</td>
                                <td>{item.appointmentDate ?? '-'}</td>
                                <td>
                                    {item.doctorOffice
                                        ? `${item.doctorOffice.doctorName} (${item.doctorOffice.specialization})`
                                        : '-'}
                                </td>
                                <td>
                                    {(item.medicalItemPurchaseDTOs ?? [])
                                        .map((medicine) => medicine.medicalItemName)
                                        .join(', ') || '-'}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </section>
    )
}

export default ArrivalHistoryPage