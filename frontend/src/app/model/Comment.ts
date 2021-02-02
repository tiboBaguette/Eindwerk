import {Post} from "./Post";

export class Comment {
  id: number | undefined;
  post: Post | undefined;
  content: string | undefined;
}
