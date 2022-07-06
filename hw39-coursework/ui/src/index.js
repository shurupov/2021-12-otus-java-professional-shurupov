import React from 'react';
import { createRoot } from 'react-dom/client';

const HelloWorld = () => {
    return (
        <h1>
            Hello World fdsfsdfs
        </h1>
    );
}

createRoot(document.getElementById("root")).render(<HelloWorld />);