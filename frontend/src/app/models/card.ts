import { Flow } from "./flow";
import { Stage } from "./stage";
import { User } from "./User";

export interface Card {
    id: number;
    name: string;
    description: string;
    createdAt: Date;
    position: number;
    blocked: boolean;
    finishedAt: Date | null;
    dueDate: Date | null;
    flow: Flow;
    stage: Stage;
    createdBy?: User;
    assignedTo?: User | null;
    classofService: string | null;
    type: string | null;
}