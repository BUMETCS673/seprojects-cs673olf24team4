import { Group } from "./group.model";
import { User } from "./user.model";

export class Team {
    id: string;
    teamNumber: number;
    group: Group;
    members: User[];

    constructor(data: Partial<Team>) {
        this.id = data.id || '';
        this.teamNumber = data.teamNumber || 0;
        this.group = data.group || new Group({});
        this.members = data.members || [];
    }

}
