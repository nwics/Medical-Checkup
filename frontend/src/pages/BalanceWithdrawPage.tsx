import { useEffect, useState } from 'react'
import {
    balanceWithdraw,
    checkPin,
    createCustomNominal,
    getDefaultSaldo,
} from '../services/balance.service'
import type { DefaultSaldoDTO } from '../types/balance'

function BalanceWithdrawPage() {
    const [saldoList, setSaldoList] = useState<DefaultSaldoDTO[]>([])
    const [customerId, setCustomerId] = useState('')
    const [nominal, setNominal] = useState('')
    const [pinCustomerId, setPinCustomerId] = useState('')
    const [pin, setPin] = useState('')
    const [withdrawCustomerId, setWithdrawCustomerId] = useState('')
    const [withdrawWalletId, setWithdrawWalletId] = useState('')
    const [message, setMessage] = useState<string | null>(null)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        let active = true
        getDefaultSaldo()
            .then((result) => {
                if (active) {
                    setSaldoList(result)
                }
            })
            .catch((err: unknown) => {
                if (active) {
                    setError(err instanceof Error ? err.message : 'Failed to load saldo')
                }
            })
        return () => {
            active = false
        }
    }, [])

    const handleCreateNominal = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setMessage(null)
        try {
            await createCustomNominal({
                customerId: Number(customerId),
                nominal,
            })
            setMessage('Custom nominal created.')
            setCustomerId('')
            setNominal('')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to create nominal')
        }
    }

    const handleCheckPin = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setMessage(null)
        try {
            const response = await checkPin(Number(pinCustomerId), pin)
            if (response?.data !== true) {
                throw new Error('PIN is incorrect')
            }
            setMessage('PIN is valid.')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to check PIN')
        }
    }

    const handleWithdraw = async (event: React.FormEvent) => {
        event.preventDefault()
        setError(null)
        setMessage(null)
        try {
            await balanceWithdraw(Number(withdrawCustomerId), Number(withdrawWalletId))
            setMessage('Withdrawal request sent.')
            setWithdrawCustomerId('')
            setWithdrawWalletId('')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to withdraw')
        }
    }

    return (
        <section>
            <h1>Balance & Withdraw</h1>
            {message && <p className="info">{message}</p>}
            {error && <p className="error">{error}</p>}

            <h2>Default Saldo</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Nominal</th>
                    </tr>
                </thead>
                <tbody>
                    {saldoList.map((item) => (
                        <tr key={item.id ?? item.type}>
                            <td>{item.id ?? '-'}</td>
                            <td>{item.type}</td>
                            <td>{String(item.saldo)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <h2>Create Custom Nominal</h2>
            <form onSubmit={handleCreateNominal}>
                <label htmlFor="nominalCustomerId">Customer ID</label>
                <input
                    id="nominalCustomerId"
                    type="number"
                    value={customerId}
                    onChange={(event) => setCustomerId(event.target.value)}
                    required
                />
                <label htmlFor="nominal">Nominal</label>
                <input
                    id="nominal"
                    value={nominal}
                    onChange={(event) => setNominal(event.target.value)}
                    placeholder="e.g. 50000"
                    required
                />
                <button type="submit">Create Custom Nominal</button>
            </form>

            <h2>Check PIN</h2>
            <form onSubmit={handleCheckPin}>
                <label htmlFor="pinCustomerId">Customer ID</label>
                <input
                    id="pinCustomerId"
                    type="number"
                    value={pinCustomerId}
                    onChange={(event) => setPinCustomerId(event.target.value)}
                    required
                />
                <label htmlFor="pin">PIN</label>
                <input
                    id="pin"
                    type="text"
                    value={pin}
                    onChange={(event) => setPin(event.target.value)}
                    required
                />
                <button type="submit">Check PIN</button>
            </form>

            <h2>Withdraw</h2>
            <form onSubmit={handleWithdraw}>
                <label htmlFor="withdrawCustomerId">Customer ID</label>
                <input
                    id="withdrawCustomerId"
                    type="number"
                    value={withdrawCustomerId}
                    onChange={(event) => setWithdrawCustomerId(event.target.value)}
                    required
                />
                <label htmlFor="withdrawWalletId">Wallet ID</label>
                <input
                    id="withdrawWalletId"
                    type="number"
                    value={withdrawWalletId}
                    onChange={(event) => setWithdrawWalletId(event.target.value)}
                    required
                />
                <button type="submit">Withdraw</button>
            </form>
        </section>
    )
}

export default BalanceWithdrawPage