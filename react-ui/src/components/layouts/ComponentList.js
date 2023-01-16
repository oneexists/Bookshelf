export const ComponentList = ({ 
    items, 
    resourceName, 
    itemComponent: ItemComponent 
}) => {
    return (
        <>
            {items.map((item) => (
                <ItemComponent key={item} {...{ [resourceName]: item }} /> 
            ))}
        </>
    )
}