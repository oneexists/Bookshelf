import { useAuth } from "../../hooks/useAuth";
import PageLayout from "../../components/layouts/PageLayout";
import LoginForm from "./LoginForm";

export default function Login() {
    const auth = useAuth();

    return (
        <PageLayout pageTitle="Bookshelf Login">
            <section>
                <p>{(auth.isLoading) ? "Logging in..." : ""}</p>

                <LoginForm />

            </section>
        </PageLayout>
    );
}