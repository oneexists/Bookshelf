import Background from "../Background";
import Title from "../Title";

export default function Home() {
    return (
        <Background>
            <Title text="Bullet Journal Bookshelf" />
            
            <section>
                <p>
                    Keep all of your reading notes together in a single place.
                    Add books to your bookshelf, track your reading habits, 
                    and save your favorite quotes. 
                </p>
                <p>
                    Inspired by the use of a Bullet Journal to keep track of 
                    reading, a journey with learning a new language, and the 
                    note taking system created by Ryan Holiday that 
                    encourages organizing notes from reading to actively 
                    engage in the process.
                </p>
            </section>
        </Background>
    );
}