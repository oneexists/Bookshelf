import { useState } from "react";
import { ControlledOnboardingFlow } from "./ControlledOnboardingFlow";

const StepOne = ({ goToNext }) => (
    <>
        <h1>Step 1</h1>
        <button onClick={() => goToNext({ first: "Step 1 Complete" })}>Next</button>
    </>
);
const StepTwo = ({ goToNext }) => (
    <>
        <h1>Step 2</h1>
        <button onClick={() => goToNext({ second: 7 })}>Next</button>
    </>
);
const StepThree = ({ goToNext }) => (
    <>
        <h1>Step 3</h1>
        <p>Congratulations!</p>
        <button onClick={() => goToNext({})}>Next</button>
    </>
);
const StepFour = ({ goToNext }) => (
    <>
        <h1>Step 4</h1>
        <button onClick={() => goToNext({ four: "Step 4 Complete" })}>Next</button>
    </>
);

export default function Bookshelf() {
    const [ onboardingData, setOnboardingData ] = useState({});
    const [ currentIndex, setCurrentIndex ] = useState(0);
    
    const onNext = (stepData) => {
        setOnboardingData({ ...onboardingData, ...stepData });
        setCurrentIndex(currentIndex + 1);
    }

    return (
        <main className="container mt-3">
            <ControlledOnboardingFlow currentIndex={currentIndex} onNext={onNext}>
                <StepOne />
                <StepTwo />
                {onboardingData.second === 2 && <StepThree />}
                <StepFour />
            </ControlledOnboardingFlow>
        </main>
    );
}