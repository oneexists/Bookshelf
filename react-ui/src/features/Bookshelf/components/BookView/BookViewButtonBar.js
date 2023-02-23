import InfoNavLinkButton from "../../../../components/buttons/InfoNavLinkButton";
import WarningNavLinkButton from "../../../../components/buttons/WarningNavLinkButton";
import DangerButton from "../../../../components/buttons/DangerButton";
import { useNavigate } from "react-router-dom";
import { deleteBookById } from "../../../../services/bookService";
import { memo, useCallback } from "react";
import PrimaryNavLinkButton from "../../../../components/buttons/PrimaryNavLinkButton";

const DeleteButton = memo(({ onClick }) => (
    <DangerButton text="Delete Book" handleClick={onClick} />
));

export default function BookViewButtonBar({bookId}) {
    const navigate = useNavigate();

    const handleDelete = useCallback(() => {
        deleteBookById(bookId).then(navigate("/"));
    }, [bookId, navigate]);
    
    return (
        <ul className="nav navbar-nav me-4">
            <li className="nav-item mb-2">
                <PrimaryNavLinkButton url="logs/add" text="Add Dates Read" />
            </li>
            <li className="nav-item mb-2">
                <InfoNavLinkButton url="quotes/add" text="Add a Quote" />
            </li>            
            <li className="nav-item mb-2">
                <InfoNavLinkButton url="notes/add" text="Add a Note" />
            </li>            
            <li className="nav-item mb-2">
                <WarningNavLinkButton url="edit" text="Edit Book Details" />
            </li>            
            <li className="nav-item">
                <DeleteButton onClick={handleDelete} />
            </li>
        </ul>
    );
}