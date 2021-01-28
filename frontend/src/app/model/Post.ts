import {User} from './User';

export class Post {
  id: number | undefined;
  content: string | undefined;
  title: string | undefined;
  date: any | undefined;
  user: User | undefined;
}
