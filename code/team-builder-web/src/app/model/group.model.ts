import { User } from "./user.model";

export class Group {
    id: string;
    name: string;
    users: User[];

    constructor(data: Partial<Group>) {
        this.id = data.id || '';
        this.name = data.name || '';
        this.users = data.users || [];
    }
}