import type { LocationLevel } from '../types/location'

export function LocationLevelList({ levels }: { levels: LocationLevel[] }) {
    return (
        <ul>
            {levels.map((level) => (
                <li key={level.id}>
                    {level.name} ({level.code})
                </li>
            ))}
        </ul>
    )
}