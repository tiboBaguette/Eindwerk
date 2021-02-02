import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Comment} from '../model/Comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  public createComment(comment: Comment): Observable<Comment> {
    console.warn(comment);
    return this.http.post<Comment>('http://localhost:8080/api/comments/add', comment);
  }

  public getComments(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(('http://localhost:8080/api/comments/show/:' + id));
  }
}
