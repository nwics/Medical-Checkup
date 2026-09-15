import { useEffect, useState } from 'react'
import { LocationLevelList } from './components/LocationLevelList'
import { getLocationLevels } from './services/location.service'
import type { LocationLevel } from './types/location'
import './App.css'

function App() {
    const [levels, setLevels] = useState<LocationLevel[]>([])
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        getLocationLevels()
            .then(setLevels)
            .catch((err: unknown) =>
                setError(err instanceof Error ? err.message : 'Failed to load location levels'),
            )
    }, [])

    return (
        <main>
            <h1>Medical Checkup</h1>
            {error && <p className="error">{error}</p>}
            {levels.length > 0 && <LocationLevelList levels={levels} />}
            {!error && levels.length === 0 && <p>No location levels found.</p>}
        </main>
    )
}

export default App