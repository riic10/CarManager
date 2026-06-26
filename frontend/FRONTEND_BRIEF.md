# Car Collection Manager — Frontend Mockup Brief

Design a **minimal, clean, single-page web UI** for "Car Collection Manager" — a small app to
browse and manage a collection of cars. It is the frontend for an existing REST API (a Spring
Boot backend). The result will be implemented in **React (Vite)** and deployed to Vercel, so:
keep it to **one page**, use **plain/minimal styling** (no heavy component libraries), and use
standard web patterns that are easy to port into a hand-written React component.

---

## Design direction (vibe)
- Minimal, modern, uncluttered. Generous whitespace, simple typography, light theme.
- Feels like a tidy internal tool / dashboard — not a marketing landing page.
- One screen, responsive (good on a laptop; narrows gracefully on mobile).
- Subtle accents only; the **data is the focus**.

## What the user can do (the entire scope)
1. **See a list** of all cars.
2. **Filter** the list by "for sale" and/or category.
3. **Add** a new car via a form.
4. **Delete** a car.

> Important: there is intentionally **no edit/update** feature — the API supports create, read,
> and delete only. Also: no login, no pagination, no settings, no routing. Keep it genuinely small.

---

## The data — a "Car"
| Field | Type | Notes |
|---|---|---|
| `id` | number | assigned by the server; **displayed, never entered** |
| `year` | integer | must be 1885–2100 |
| `make` | text | required, up to 100 chars (e.g. "Porsche") |
| `model` | text | required, up to 100 chars (e.g. "911 GT3 RS") |
| `category` | enum | one of the 8 values below |
| `forSale` | boolean | true / false |

**Category values (exactly these strings):**
`RACECAR`, `SUPERCAR`, `SPORTSCAR`, `LUXURY`, `MUSCLE`, `VINTAGE`, `ECONOMY`, `OTHER`
*(You may display them title-cased — e.g. "Supercar" — but the underlying values are ALL-CAPS.)*

Example car as returned by the API:
```json
{ "id": 7, "year": 2025, "make": "Porsche", "model": "911 GT3 RS", "category": "SUPERCAR", "forSale": true }
```

---

## Suggested layout (one page)
- **Header** — app title "Car Collection Manager", a one-line tagline, and a count of cars shown.
- **Filter bar** — a "For sale" control (All / For sale / Not for sale) and a "Category" dropdown
  (All + the 8 categories). The two filters combine.
- **Car list** — a table or card grid showing year, make, model, category (as a small badge/chip),
  for-sale status (a badge), and a **delete** action per row.
- **Add car** — a compact form: year (number), make (text), model (text), category (dropdown),
  for sale (checkbox), and an "Add" button. Inline panel or a small modal.
- **States to design**:
  - **Empty** — "No cars yet" when the list is empty.
  - **Loading** — a simple spinner/placeholder while fetching.
  - **Validation errors** — shown near the add form (see below).

---

## How it talks to the backend (REST API)
Base URL is configurable (dev: `http://localhost:8080`). JSON over HTTP.

| Action | Method | Path | Body | Success |
|---|---|---|---|---|
| List (optional filters) | GET | `/cars?forSale=true&category=SUPERCAR` | — | 200 + array of cars |
| Get one | GET | `/cars/{id}` | — | 200 + car (404 if missing) |
| Add | POST | `/cars` | car **without** `id` | 201 + the created car |
| Delete | DELETE | `/cars/{id}` | — | 204 (404 if missing) |

Both query params are optional and may be combined.

**Add request body** (note: no `id`):
```json
{ "year": 1967, "make": "Shelby", "model": "GT500", "category": "MUSCLE", "forSale": false }
```

### Errors to design for
On bad form input the API returns **HTTP 400** with this shape:
```json
{
  "timestamp": "2026-06-22T19:55:23Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "fieldErrors": ["make: must not be blank", "year: must be greater than or equal to 1885"]
}
```
Form rules to respect and surface inline: **make** & **model** required (non-blank), **year**
between **1885 and 2100**, **category** required.

A "not found" error (e.g. deleting something already gone) returns **404** with the same shape and
`"message": "Car not found with id 7"`.

---

## Constraints for the mockup
- **Single page**, React-friendly, **minimal CSS** (avoid Tailwind/Material/etc. unless trivial)
  so it ports cleanly into a hand-written React + Vite app.
- Small dataset (tens of cars) — **no pagination** or virtualization needed.
- **No auth, no routing** (one screen).
- Keep it deliberately minimal — this frontend is a thin demo over a backend-focused project.

---

## Background (context for why this exists)
This is the capstone of a project that **modernized a legacy Java Swing desktop app into a
containerized Spring Boot + PostgreSQL REST API** (CRUD + filtering + validation + consistent
error handling, with unit/integration tests and CI). This frontend is an optional **full-stack
showcase**: a small React SPA that consumes the API, deployed **separately** (Vercel) from the
API (Render) — which is why CORS is configured on the backend.