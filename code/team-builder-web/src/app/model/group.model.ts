import { User } from "./user.model";

export class Group {
    id: number;
    name: string;
    users: User[];

    constructor(data: Partial<Group>) {
        this.id = data.id || 0;
        this.name = data.name || '';
        this.users = data.users || [];
    }
}