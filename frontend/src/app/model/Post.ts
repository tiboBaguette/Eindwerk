import {User} from './User';
import {Category} from './Category';

export class Post {
  id: number | undefined;
  title: string | undefined;
  content: string | undefined;
  postCreationDate: Date | undefined;
  user: User | undefined;
  category: Category | undefined;
  comment: Comment | undefined;
}
