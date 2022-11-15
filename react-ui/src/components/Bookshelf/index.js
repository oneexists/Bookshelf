import { SplitScreen } from "./SplitScreen";

const LeftComponent = ({ title }) => {
    return <h1>{title}</h1>;
};

const RightComponent = ({ message }) => {
    return <h2>{message}</h2>;
};

export default function Bookshelf() {
    return (
        <SplitScreen leftWeight={1} rightWeight={2} >
            <LeftComponent title="Bookshelf" />
            <RightComponent message="Book Details"/>
        </SplitScreen>
    );
}