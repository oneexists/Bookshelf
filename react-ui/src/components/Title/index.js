import styles from "./index.module.css";

export default function Title({ text }) {
    return <h2 className={styles.title}>{text}</h2>;
}