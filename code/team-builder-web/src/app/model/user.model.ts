export class User {
  id: string;
  name: string;
  email: string;
  role: string;
  answers: string[];

  constructor(data: Partial<User>) {
    this.id = data.id || '';
    this.name = data.name || '';
    this.email = data.email || '';
    this.role = data.role || '';
    this.answers = data.answers || [];
  }

}
