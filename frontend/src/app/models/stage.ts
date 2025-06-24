import { Flow } from "./flow";

export interface Stage {
    id: number;
    name: string;
    flow: Flow;
    position: number;
    wipLimit: number;
    type: String;
}