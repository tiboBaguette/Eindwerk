import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Post} from '../model/Post';

@Injectable({
  providedIn: 'root'
})
export class CreatePostService {
  private readonly postsUrl = 'http://localhost:8080/api/posts';
  constructor(private http: HttpClient) { }

   save(post: Post) {
    return this.http.post<Post>(this.postsUrl, post);
  }
}
