export class User {
    id: string;
    name: string;
    email: string;
    degree: string;
    concentration?: string;
  
    constructor(data: Partial<User>) {
      this.id = data.id || '';
      this.name = data.name || '';
      this.email = data.email || '';
      this.degree = data.degree || '';
      this.concentration = data.concentration || '';
    }

  }
  