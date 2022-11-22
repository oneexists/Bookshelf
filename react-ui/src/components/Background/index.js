import styles from "./index.module.css";

export default function Background({ children }) {
    return (
        <main className={`${styles.background} container p-3`}>
            {children}
        </main>
    );
}