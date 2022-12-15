import Background from "../Background";
import Title from "../Title";

export default function Home() {
    return (
        <Background>
            <section>
                <Title text="Bullet Journal Bookshelf" />
            
                <p>
                    Keep all of your reading notes together in a single place.
                    Add books to your bookshelf, track your reading habits, 
                    save your favorite quotes and save your notes in one 
                    place. 
                </p>
                <p>
                    Get an overview of your reading with yearly and overall 
                    statistics based on books added to your bookshelf. Use 
                    note taking methods to remember what you've read 
                    recommended by people such as Ryan Holiday and Tiago Forte.
                </p>
                <h4>Want to learn more?</h4>
                <ul>
                    <li>
                        <a href="https://ryanholiday.medium.com/this-simple-note-taking-method-will-help-you-read-more-and-remember-what-youve-read-2cdf8010801">
                            Ryan Holiday's Note Taking System
                        </a>
                    </li>
                    <li>
                        <a href="https://youtu.be/fES9ZrLXY9s">
                            Taking Book Notes (How to Start) by Tiago Forte
                        </a>
                    </li>
                </ul>
            </section> 
        </Background>
    );
}