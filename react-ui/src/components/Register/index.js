import { useFetch } from "../../hooks/useFetch";

export default function Register() {
    const { loading, data, error } = useFetch(`http://localhost:8080/api/appUsers`);

    if (loading) {
        return (
            <main className="container mt-3">
                <h1>loading</h1>
            </main>
        );
    }
    if (error) {
        return (
            <main className="container mt-3">
                <pre>{JSON.stringify(error, null, 2)}</pre>
            </main>
        );
    }
    return (
        <main className="container mt-3">
            <p>Total Users: {data._embedded.appUsers.length}</p>
            <p><a href={data._links.self.href}>Self</a></p>
            <p><a href={data._links.profile.href}>Profile</a></p>
            <p><a href={data._links.search.href}>Search Link</a></p>
            <p>Total Pages: {data.page.totalPages}</p>
            <pre>{JSON.stringify(data, null, 2)}</pre>
        </main>
    );
}