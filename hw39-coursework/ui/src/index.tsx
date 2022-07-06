import React from 'react';
import { createRoot } from 'react-dom/client';
import {App} from "./App";

// @ts-ignore
const container: HTMLElement = document.getElementById("root");
createRoot(container).render(<App />);