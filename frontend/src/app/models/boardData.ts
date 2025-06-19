import { Card } from "./card";
import { ClassOfService } from "./ClassOfService";
import { Stage } from "./stage";

export interface BoardData {
  stages: Stage[];
  cards: Card[];
  serviceClasses: ClassOfService[];
}