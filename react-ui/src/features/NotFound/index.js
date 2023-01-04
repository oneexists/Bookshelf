import PageLayout from "../../components/layouts/PageLayout";

export default function NotFound() {
    return (
        <PageLayout pageTitle="That was strange...">
            <div className="row d-flex justify-content-center">
                <section className="mr-auto ml-auto text-center">
                    <img  
                        src="https://t3.ftcdn.net/jpg/01/67/37/06/240_F_167370626_zEV8195DIsvDnCJHIkBbBHgIotS4vPKl.jpg" 
                        alt="spilled coffee" 
                    />
                    <p>The page you were looking for could not be found.</p>
                </section>
            </div>
        </PageLayout>
    );
}