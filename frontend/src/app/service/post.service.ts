import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Post} from '../model/Post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>('http://localhost:8080/api/posts/create', post);
  }

  editPost(post: Post, id: number | undefined): Observable<Post> {
    return this.http.put<Post>('http://localhost:8080/api/posts/edit/:' + id, post);
  }

  deletePost(post: Post): Observable<Post> {
    return this.http.post<Post>('http://localhost:8080/api/posts/delete', post);
  }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>('http://localhost:8080/api/posts/show');
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>('http://localhost:8080/api/posts/detail/:' + id);
  }

  getPostByCategory(category: string): Observable<Post[]> {
    return this.http.get<Post[]>('http://localhost:8080/api/posts/get-by-category/:' + category);
  }

  likePost(postId: number, username: string | undefined): Observable<Post> {
    return this.http.post<Post>('http://localhost:8080/api/likes/like/:' + postId, username);
  }

  unLikePost(postId: number, username: string | undefined): Observable<Post> {
    return this.http.post<Post>('http://localhost:8080/api/likes/unlike/:' + postId, username);
  }

  getLikes(postId: number | undefined): Observable<number> {
    return this.http.get<number>('http://localhost:8080/api/likes/getLikes/:' + postId);
  }
}
