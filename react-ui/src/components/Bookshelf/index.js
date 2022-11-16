import { UncontrolledOnboardingFlow } from "./UncontrolledOnboardingFlow";

const StepOne = ({ goToNext }) => (
    <>
        <h1>Step 1</h1>
        <button onClick={() => goToNext({ first: "Step 1 Complete" })}>Next</button>
    </>
);
const StepTwo = ({ goToNext }) => (
    <>
        <h1>Step 2</h1>
        <button onClick={() => goToNext({ second: "Step 2 Complete" })}>Next</button>
    </>
);
const StepThree = ({ goToNext }) => (
    <>
        <h1>Step 3</h1>
        <button onClick={() => goToNext({ third: "Step 3 Complete" })}>Next</button>
    </>
);

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <UncontrolledOnboardingFlow onFinish={data => console.log(data)}>
                <StepOne />
                <StepTwo />
                <StepThree />
            </UncontrolledOnboardingFlow>
        </main>
    );
}