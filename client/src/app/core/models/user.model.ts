export interface User {
  id: number;
  username: string;
  email: string;
  isActive: boolean;
  role?: string;
  createdAt?: string;
}
