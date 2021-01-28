import {User} from './User';

export class Post {
  id: number | undefined;
  title: string | undefined;
  content: string | undefined;
  postCreationDate: any | undefined;
  user: User | undefined;
}
