import {Category} from './Category';

export class Post {
  id: number | undefined;
  title: string | undefined;
  content: string | undefined;
  creationTime: any | undefined;
  user: string | undefined;
  category: Category | undefined;
  comment: Comment | undefined;
  likes: number | undefined;
}
