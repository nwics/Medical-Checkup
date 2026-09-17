interface PaginationProps {
    current: number
    totalPages: number
    onPageChange: (page: number) => void
}

export function Pagination({ current, totalPages, onPageChange }: PaginationProps) {
    if (totalPages <= 1) {
        return null
    }

    const pages: number[] = []
    for (let page = 1; page <= totalPages; page += 1) {
        pages.push(page)
    }

    return (
        <nav className="pagination" aria-label="Pagination">
            <button type="button" disabled={current <= 1} onClick={() => onPageChange(current - 1)}>
                Previous
            </button>
            {pages.map((page) => (
                <button
                    key={page}
                    type="button"
                    className={page === current ? 'current' : ''}
                    onClick={() => onPageChange(page)}
                >
                    {page}
                </button>
            ))}
            <button type="button" disabled={current >= totalPages} onClick={() => onPageChange(current + 1)}>
                Next
            </button>
        </nav>
    )
}