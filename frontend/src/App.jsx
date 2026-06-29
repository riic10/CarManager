import {useState, useEffect} from "react";
import {getCars, addCar, deleteCar} from "./api";
import "./App.css";

const CATEGORIES = ["RACECAR", "SUPERCAR", "SPORTSCAR", "LUXURY", "MUSCLE", "VINTAGE", "ECONOMY", "OTHER"];

export default function App() {
    const [cars, setCars] = useState([]);
    const [loading, setLoading] = useState(true);
    const [forSaleFilter, setForSaleFilter] = useState(null);
    const [categoryFilter, setCategoryFilter] = useState(null);
    const [form, setForm] = useState({year:"", make:"", model:"", category:"", forSale:false});
    const [fieldErrors, setFieldErrors] = useState([]);
    const [loadError, setLoadError] = useState(null);

    useEffect(() => {
        getCars(forSaleFilter, categoryFilter).then(data => {
            setCars(data);
            setLoadError(null);
            setLoading(false);
        }).catch(err => {
            setLoadError(err.message);
            setLoading(false);
        });
    }, [forSaleFilter, categoryFilter]);

    async function handleAdd(e) {
        e.preventDefault();
        setFieldErrors([]);
        try {
            await addCar({...form, year:Number(form.year)});
            setForm({year:"", make:"", model:"", category:"", forSale:false});
            setLoading(true);
            getCars(forSaleFilter, categoryFilter).then(data => {
                setCars(data);
                setLoading(false);
            });
        } catch (err) {
            setFieldErrors(err.fieldErrors ?? [err.message]);
        }
    }

    async function handleDelete(id) {
        try {
            await deleteCar(id);
            setCars(cars.filter(car => car.id !== id));
        } catch (err) {
            alert(err.message);
        }
    }

    return (
        <div className="app">
            <header>
                <h1>Car Collection Manager</h1>
                <p className="tagline">Your online garage</p>
                <p className="count">{cars.length} car{cars.length !== 1 ? "s" : ""}</p>
            </header>

            <div className="filters">
                <label>For Sale:</label>
                <select value={forSaleFilter ?? ""} onChange={e => {
                    const v = e.target.value;
                    setForSaleFilter(v === "" ? null : v === "true");
                    setLoading(true);
                }}>
                    <option value="">All</option>
                    <option value="true">For Sale</option>
                    <option value="false">Not For Sale</option>
                </select>

                <label> Category: </label>
                <select value={categoryFilter ?? ""} onChange={e => {
                    setCategoryFilter(e.target.value || null);
                    setLoading(true);
                }}>
                    <option value="">All</option>
                    {CATEGORIES.map(c => <option key={c} value={c}>{c[0] + c.slice(1).toLowerCase()}</option>)}
                </select>
            </div>

            {loading && <p>Loading...</p>}

            {loadError && <p className="field-error">Could not load cars: {loadError}</p>}

            {!loading && !loadError && cars.length === 0 && <p>No cars yet.</p>}

            {!loading && !loadError && cars.length > 0 && (
                <table className="car-table">
                    <thead>
                        <tr>
                            <th>Year</th>
                            <th>Make</th>
                            <th>Model</th>
                            <th>Category</th>
                            <th>For Sale</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {cars.map(car => (
                            <tr key={car.id}>
                                <td>{car.year}</td>
                                <td>{car.make}</td>
                                <td>{car.model}</td>
                                <td><span className="badge badge-category">{car.category}</span></td>
                                <td><span className={`badge ${car.forSale ? 'badge-yes' : 'badge-no'}`}>{car.forSale ? 'Yes' : 'No'}</span></td>
                                <td><button className="btn-delete" onClick={() => handleDelete(car.id)}>Delete</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
            <form className="add-form" onSubmit={handleAdd}>
                <h2>Add a Car</h2>
                <div className="form-row">
                    <input type="number" placeholder="Year" value={form.year}
                        onChange={e => setForm({...form, year: e.target.value})} />
                    <input type="text" placeholder="Make" value={form.make}
                        onChange={e => setForm({...form, make: e.target.value})} />
                    <input type="text" placeholder="Model" value={form.model}
                        onChange={e => setForm({...form, model: e.target.value})} />
                    <select value={form.category} onChange={e => setForm({...form, category: e.target.value})}>
                        <option value="">Select category</option>
                        {CATEGORIES.map(c => <option key={c} value={c}>{c[0] + c.slice(1).toLowerCase()}</option>)}
                    </select>
                </div>
                <label className="checkbox-row">
                    <input type="checkbox" checked={form.forSale}
                        onChange={e => setForm({...form, forSale: e.target.checked})} />
                    For Sale
                </label>
                <button className="btn-add" type="submit">Add</button>
                {fieldErrors.map((fe, i) => <p key={i} className="field-error">{fe}</p>)}
            </form>
        </div>
    );
}
