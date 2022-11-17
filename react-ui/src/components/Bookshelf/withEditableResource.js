import React, { useState, useEffect } from "react";

const capitalize = str => str.charAt(0).toUpperCase() + str.slice(1);

export const withEditableResource = (Component, resourcePath, resourceName) => {
    return props => {
        const [ originalData, setOriginalData ] = useState(null);
        const [ formData, setFormData ] = useState(null);

        useEffect(() => {
            fetch(resourcePath)
                .then(r => r.json())
                .then(d => {
                    setOriginalData(d);
                    setFormData(d);
                });
        }, []);

        const onChange = changes => {
            setFormData({ ...formData, ...changes });
        }

        const onSave = async () => {
            fetch(resourcePath, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem("bujo-bookshelf")}`
                }, 
                body: JSON.stringify({ [resourceName]: formData })
            })
                .then(r => r.json())
                .then(d => {
                    setOriginalData(d);
                    setFormData(d);
                });
        }

        const onReset = () => {
            setFormData(originalData);
        }

        const resourceProps = {
            [resourceName]: formData,
            [`onChange${capitalize(resourceName)}`]: onChange,
            [`onSave${capitalize(resourceName)}`]: onSave,
            [`onReset${capitalize(resourceName)}`]: onReset,
        }

        return <Component { ...props } { ...resourceProps } />
    }
}