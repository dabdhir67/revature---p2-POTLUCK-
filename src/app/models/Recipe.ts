import { Chef } from "./Chef";

export interface Recipe {
    id: number;
    title: string;
    body: string;
    date: string;
    chef: Chef;
}
