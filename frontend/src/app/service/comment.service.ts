import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  public comment: Comment | undefined;

  constructor(private http: HttpClient) { }

  public createComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>('http://localhost:8080/api/comments/add', comment);
  }
}
