import SecondaryNavLinkButton from "../../../../components/buttons/SecondaryNavLinkButton";
import WarningNavLinkButton from "../../../../components/buttons/WarningNavLinkButton";
import ButtonBar from "../../../../components/layouts/ButtonBar";
import DangerButton from "../../../../components/buttons/DangerButton";
import { useNavigate } from "react-router-dom";
import { deleteBookById } from "../../../../services/bookService";
import { memo, useCallback } from "react";

const DeleteButton = memo(({ onClick }) => (
    <DangerButton text="Delete Book" handleClick={onClick} />
));

export default function BookViewButtonBar({bookId}) {
    const navigate = useNavigate();

    const handleDelete = useCallback(() => {
        deleteBookById(bookId).then(navigate("/"));
    }, [bookId, navigate]);

    return (
        <ButtonBar>
            <SecondaryNavLinkButton url="logs/add" text="Add Dates Read" marginEnd={2} />
            <SecondaryNavLinkButton url="quotes/add" text="Add a Quote" marginEnd={2} />
            <SecondaryNavLinkButton url="notes/add" text="Add a Note" marginEnd={2} />
            <WarningNavLinkButton url="edit" text="Edit Book Details" marginEnd={2} />
            <DeleteButton onClick={handleDelete} />
        </ButtonBar>
    );
}