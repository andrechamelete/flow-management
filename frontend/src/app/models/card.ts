import { Flow } from "./flow";
import { Stage } from "./stage";
import { User } from "./User";

export interface Card {
    id: number;
    name: string;
    description: string;
    created_at: Date;
    position: number;
    blocked: boolean;
    finished_at: Date | null;
    due_date: Date | null;
    flow: Flow;
    stage: Stage;
    created_by: User;
    assignedTo?: User;
    classofService: string | null;
    type: string | null;
}