import { useAuth } from "../../hooks/useAuth";
import PageLayout from "../../components/layouts/PageLayout";
import RegisterForm from "./RegisterForm";

export default function Register() {
    const auth = useAuth();

    return (
        <PageLayout pageTitle="Bookshelf Registration">
            <p>{(auth.isLoading) ? "Creating account..." : ""}</p>

            <RegisterForm />
        </PageLayout>
    );
}