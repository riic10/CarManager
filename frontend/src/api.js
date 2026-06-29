const BASE_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

async function request(path, options = {}) {
    const res = await fetch(`${BASE_URL}${path}`, options);
    if (res.ok) return res.status === 204 ? null : res.json();
    const body = await res.json().catch(() => null);
    const err = new Error(body?.message ?? `HTTP ${res.status}`);
    err.status = res.status;
    err.fieldErrors = body?.fieldErrors ?? [];
    throw err;
}

export function getCars(forSale, category) {
    const params = new URLSearchParams();
    if (forSale != null) params.set("forSale", forSale);
    if (category != null) params.set("category", category);
    const query = params.size ? `?${params}` : "";
    return request(`/cars${query}`);
}

export function addCar(car) {
    return request(`/cars`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(car),
    });
}

export function deleteCar(id) {
    return request(`/cars/${id}`, {method: "DELETE"});
}