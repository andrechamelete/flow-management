import { Company } from "./company";
import { User } from "./User";

export interface ClassOfService {
  id: number;
  serviceClass: string;
  company: Company;
  createdBy: User;
}