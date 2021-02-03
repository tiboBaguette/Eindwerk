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

  editPost(post: Post): Observable<Post> {
    return this.http.post<Post>('http://localhost:8080/api/posts/edit', post);
  }

  deletePost(post: Post): Observable<Post> {
    return this.http.post<Post>('http://localhost:8080/api/posts/delete', post);
  }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>('http://localhost:8080/api/posts/show');
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>('http://localhost:8080/api/posts/detail/' + id);
  }
}
